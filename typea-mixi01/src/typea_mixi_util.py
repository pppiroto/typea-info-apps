#!Python2.6
# -*- encoding: utf-8 -*-

import os
from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app
from google.appengine.ext.webapp import template

import amazon_ecs
import urllib2
import xml.parsers.expat

class SearchedItem:
    ''' Amazon ItemSearch Operation の結果格納  '''
    def __init__(self):
        self.asin = ''
        self.detailPageURL = ''
        self.smallImageURL = ''
        self.title = ''
        
class SAXTagHandler:
    def __init__(self):
        # XMLParser(SAX)の状態制御
        self.proc_start = False
        self.img_start = False
        self.now_key = ''
        self.item_list = []
        
    def startElementHandler(self, name, attributes):
        ''' xml.parsers.expat XMLParser のハンドラ
                    要素の開始を処理するごとに呼び出される
            @see http://www.python.jp/doc/release/lib/xmlparser-objects.html
        '''
        if name == 'Items':
            self.proc_start = True
        if name == 'SmallImage':
            self.img_start = True
        if self.proc_start:
            if name == 'Item':
                self.item_list.append(SearchedItem())
            else:
                self.now_key = name
    
    def endElementHandler(self, name):
        ''' xml.parsers.expat XMLParser のハンドラ
                    要素の終端を処理するごとに呼び出される
            @see http://www.python.jp/doc/release/lib/xmlparser-objects.html
        '''
        if name == 'Items':
            self.proc_start = False
        if name == 'SmallImage':
            self.img_start = False
    
    def characterDataHandler(self, data):
        ''' xml.parsers.expat XMLParser のハンドラ
                    文字データを処理するときに呼びだされる
            @see http://www.python.jp/doc/release/lib/xmlparser-objects.html
        '''
        if self.proc_start:
            idx = len(self.item_list)
            idx = idx -1
            if idx >= 0:
                if self.now_key == 'ASIN':
                    self.item_list[idx].asin = data
                if self.now_key == 'DetailPageURL':
                    self.item_list[idx].detailPageURL = data
                if self.img_start and self.now_key == 'URL':
                    self.item_list[idx].smallImageURL = data
                if self.now_key == 'Title':
                    self.item_list[idx].title = data    

class MainPage(webapp.RequestHandler):
    def get(self):
        self.redirect('/am_is?q=amazon')

class AmazonItemSearch(webapp.RequestHandler):
    ''' Amazon Product Advertising API を利用し、キーワード検索を行う
        example http://typea-mixi01.appspot.com/am_is?q=book
        parameters 
            q : query keyword
            s : view style 
                image : image list
                text  : text only
                ul    : unnumbered list
            c : keyword encoding
    '''
    def get(self):
        # エンコードした値を request.GET で取得すると　KEYError ？
        keyword = ''
        queries = self.request.query_string.split('&')
        for query in queries:
            pair = query.split('=')
            if pair[0] == 'q':
                keyword = pair[1]
                break;
        try:
            style = self.request.GET['s']  # style
        except KeyError:
            style = 'image'  
        
        try:
            coding = self.request.GET['c']  # style
        except KeyError:
            coding = None 

        #パラメータのデコード
        #@see http://www.findxfine.com/default/495.html
        #FireFox のアドレスバーに漢字を打つとUTF-8でないコードにエンコードされてしまう？
        keyword = urllib2.unquote(keyword) #.encode('utf-8')
        if keyword == '':
            keyword = 'amazon'
        if coding:
            keyword = unicode(keyword, coding).encode('utf-8')
    
        operation = amazon_ecs.ItemSearch()
        operation.keywords = keyword
        operation.search_index = 'Books'
        operation.response_group = 'Medium'
        request = operation.request()
        
        # XMLParserの生成とハンドラのセット
        h = SAXTagHandler()
        p = xml.parsers.expat.ParserCreate()
        p.StartElementHandler = h.startElementHandler
        p.EndElementHandler = h.endElementHandler
        p.CharacterDataHandler = h.characterDataHandler

        # リクエストの実行と解析
        f = urllib2.urlopen(request)
        p.Parse(f.read())

        template_values = {
            'style':style,
            'keyword':keyword,
            'item_list':h.item_list
        }
        
        path = os.path.join(os.path.dirname(__file__), 'amazon_ads.html')
        self.response.out.write(template.render(path,template_values))

        
application = webapp.WSGIApplication([
                                      ('/', MainPage),
                                      ('/am_is', AmazonItemSearch)
                                      ], debug=True)

def main():
    run_wsgi_app(application)

if __name__ == "__main__":
    main()

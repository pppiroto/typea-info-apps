#!Python2.6
# -*- coding: utf-8 -*-

from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app

import amazon_ecs
import urllib2
import xml.parsers.expat

# XMLParser(SAX)の状態制御
proc_start = False
img_start = False
now_key = ''
item_list = []

class SearchedItem:
    ''' Amazon ItemSearch Operation の結果格納  '''
    asin = ''
    detailPageURL = ''
    smallImageURL = ''

def StartElementHandler(name, attributes):
    ''' xml.parsers.expat XMLParser のハンドラ
                要素の開始を処理するごとに呼び出される
        @see http://www.python.jp/doc/release/lib/xmlparser-objects.html
    '''
    global proc_start
    global img_start
    global now_key
    global item_list
    
    if name == 'Items':
        proc_start = True
    if name == 'SmallImage':
        img_start = True
    if proc_start:
        if name == 'Item':
            item_list.append(SearchedItem())
        else:
            now_key = name

def EndElementHandler(name):
    ''' xml.parsers.expat XMLParser のハンドラ
                要素の終端を処理するごとに呼び出される
        @see http://www.python.jp/doc/release/lib/xmlparser-objects.html
    '''
    global proc_start
    global img_start
    
    if name == 'Items':
        proc_start = False
    if name == 'SmallImage':
        img_start = False

def CharacterDataHandler(data):
    ''' xml.parsers.expat XMLParser のハンドラ
                文字データを処理するときに呼びだされる
        @see http://www.python.jp/doc/release/lib/xmlparser-objects.html
    '''
    global proc_start
    global img_start
    global now_key
    global item_list

    if proc_start:
        idx = len(item_list)
        idx = idx -1
        if idx >= 0:
            if now_key == 'ASIN':
                item_list[idx].asin = data
            if now_key == 'DetailPageURL':
                item_list[idx].detailPageURL = data
            if img_start and now_key == 'URL':
                item_list[idx].smallImageURL = data

class MainPage(webapp.RequestHandler):
    def get(self):
        self.redirect('/am_is?q=amazon')

class AmazonItemSearch(webapp.RequestHandler):
    ''' Amazon Product Advertising API を利用し、キーワード検索を行う
        example http://typea-mixi01.appspot.com/am_is?q=book
    '''
    proc_start = False
    img_start = False
    now_key = ''
    item_list = []
    
    def get(self):
        del item_list[:]

        #パラメータの切り出し request.getが機能しない
        #keyword = self.request.get('q')
        keyword = ''
        queries = self.request.query_string.split('&')
        for query in queries:
            pair = query.split('=')
            if pair[0] == 'q':
                keyword = pair[1]
                break;

        #パラメータのデコード
        #@see http://www.findxfine.com/default/495.html
        #FireFox のアドレスバーに漢字を打つとUTF-8でないコードにエンコードされてしまう？
        keyword = urllib2.unquote(keyword) #.encode('utf-8')
        if keyword == '':
            keyword = 'amazon'
    
        operation = amazon_ecs.ItemSearch()
        operation.keywords(keyword)
        operation.search_index('Books')
        operation.response_group('Small')
        request = operation.request()

        # XMLParserの生成とハンドラのセット
        p = xml.parsers.expat.ParserCreate()
        p.StartElementHandler = StartElementHandler
        p.EndElementHandler = EndElementHandler
        p.CharacterDataHandler = CharacterDataHandler

        # リクエストの実行と解析
        f = urllib2.urlopen(request)
        p.Parse(f.read())
        
        for itm in item_list:
            self.response.out.write('<a href="%s" style="padding-left:2px" target="_blank"><img src="%s" border="0"/></a>' 
                                    % (itm.detailPageURL, itm.smallImageURL))
        
application = webapp.WSGIApplication([
                                      ('/', MainPage),
                                      ('/am_is', AmazonItemSearch)
                                      ], debug=True)

def main():
    run_wsgi_app(application)

if __name__ == "__main__":
    main()

#!Python2.6
# -*- encoding: utf-8 -*-

import os
import re
import urllib2
import amazon_ecs
import xml.parsers.expat

from google.appengine.ext import webapp
from google.appengine.ext.webapp import template
from amazon_search import AmazonRequest

class AmazonItemSearch(webapp.RequestHandler):
    ''' Amazon Product Advertising API を利用し、キーワード検索を行う
        example http://typea-mixi01.appspot.com/am_is?q=book
        parameters 
            q : query keyword
            s : view style 
                image : image list
                text  : text only
                ul    : unnumbered list
                image_text : image and text
                image_text_table : image and text table layout
            c : keyword encoding
    '''
    def get(self):
        try:
            keyword = self.request.GET['q']
            keyword = keyword.encode('utf-8')
        except:
            keyword = 'Amazon'
        
        try:
            style = self.request.GET['s']  # style
        except:
            style = 'image'  

        try:
            coding = self.request.GET['c']  # style
        except:
            coding = None 

        #パラメータのデコード
        #@see http://www.findxfine.com/default/495.html
        #FireFox のアドレスバーに漢字を打つとUTF-8でないコードにエンコードされてしまう？
        keyword = urllib2.unquote(keyword)
        if keyword == '':
            keyword = 'amazon'
        if coding:
            keyword = unicode(keyword, coding)
       
        amazon_request = AmazonRequest() 
        search_index = 'Books'

        item_list = amazon_request.request(keyword, search_index)

        # retry 
        if len(item_list) == 0:
            keyword = keyword.split(' ')[0]
            item_list = amazon_request.request(keyword, search_index)

        if len(item_list) == 0:
            m = re.match(r'(?P<kw>\w+)', keyword)
            if m:
                keyword = m.group('kw')
                item_list = amazon_request.request(keyword, search_index)

        if len(item_list) == 0:
            keyword = 'Computer'
            item_list = amazon_request.request(keyword, search_index)
            
        context = {
            'style':style,
            'keyword':keyword,
            'item_list':item_list
        }
        
        path = os.path.join(os.path.dirname(__file__), 'amazon_ads.html')
        self.response.out.write(template.render(path, context))

class CreateLinks(webapp.RequestHandler):
    '''Amazon へのリンクを作成する '''
    def post(self):
        keyword = self.request.POST['q']
        keyword = keyword.encode('utf-8')
        keyword = urllib2.unquote(keyword) 
        
        style = self.request.POST['style']
        search_index = self.request.POST['search_index']
        
        amazon_request = AmazonRequest() 
        item_list = amazon_request.request(keyword, search_index)
        links = ''
        if len(item_list) > 0:
            links_template_values = {
                    'style':style,
                    'keyword':keyword,
                    'item_list':item_list
            }
            links_path = os.path.join(os.path.dirname(__file__), 'amazon_ads.html')
            links =  template.render(links_path,links_template_values)
        
        template_values = {
                           'keyword':keyword,
                           'links':links,
                           'search_index':search_index,
                           'style':style,
        }
        path = os.path.join(os.path.dirname(__file__), 'create_links.html')
        self.response.out.write(template.render(path,template_values))

    def get(self):
        template_values = {
                           'keyword':'',
                           'links':'',
                           'search_index':'Books',
                           'style':'ul',
        }
        path = os.path.join(os.path.dirname(__file__), 'create_links.html')
        self.response.out.write(template.render(path,template_values))

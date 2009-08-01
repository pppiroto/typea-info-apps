#!Python2.6
# -*- coding: utf-8 -*-

from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app

import amazon_ecs
import urllib2
import xml.parsers.expat

proc_start = False
img_start = False
now_key = ''
item_list = []

class SearchedItem:
    asin = ''
    detailPageURL = ''
    smallImageURL = ''

def StartElementHandler(name, attributes):
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
    global proc_start
    global img_start
    
    if name == 'Items':
        proc_start = False
    if name == 'SmallImage':
        img_start = False

def CharacterDataHandler(data):
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
        self.response.headers['Content-Type'] = 'text/plain'

class AmazonItemSearch(webapp.RequestHandler):
    """
    example http://typea-mixi01.appspot.com/am_is?q=book
    """
    proc_start = False
    img_start = False
    now_key = ''
    item_list = []
    
    def get(self):
        del item_list[:]
        #keyword = self.request.get('q')
        keyword = ''
        queries = self.request.query_string.split('&')
        for query in queries:
            pair = query.split('=')
            if pair[0] == 'q':
                keyword = pair[1]
                break;
        
        keyword = urllib2.unquote(keyword).encode('utf-8')
    
        operation = amazon_ecs.ItemSearch()
        operation.keywords(keyword)
        operation.search_index('Books')
        operation.response_group('Small')
        request = operation.request()
        
        f = urllib2.urlopen(request)

        p = xml.parsers.expat.ParserCreate()
        p.StartElementHandler = StartElementHandler
        p.EndElementHandler = EndElementHandler
        p.CharacterDataHandler = CharacterDataHandler
        
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

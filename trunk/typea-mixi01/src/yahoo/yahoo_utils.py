#!Python2.6
# -*- encoding: utf-8 -*-
from yahoo import yahoo_search

import os
import urllib
import urllib2

from google.appengine.ext import webapp
from google.appengine.ext.webapp import template
from xml.etree import ElementTree

from yahoo_search import YahooSearch

class Search(webapp.RequestHandler):
    ''' Yahoo Developer Network Search API http://developer.yahoo.co.jp/webapi/search/websearch/v1/websearch.html
        example http://typea-mixi01.appspot.com/yh_s?q=book
        parameters 
            q: query strings
    '''
    def get(self):
        query = ''
        plain_query = ''
        encode = 'utf-8'
        try:
            query = self.request.GET['q']
            plain_query = query.encode(encode)
            query = urllib.quote(plain_query, self.__safe_chars)
        except:
            pass
        
        yahoo_sarch = YahooSearch()
        search_list = yahoo_sarch.search({'q':query})

        context = {
                   'query':plain_query,
                   'items':search_list,
        }
        path = os.path.join(os.path.dirname(__file__), 'yahoo_search.html')
        self.response.out.write(template.render(path, context))

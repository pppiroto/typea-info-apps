#!Python2.6
# -*- encoding: utf-8 -*-

import os
import urllib2

from google.appengine.ext import webapp
from google.appengine.ext.webapp import template
from xml.etree import ElementTree

class SearchResult(object):
    def __init__(self):
        title = ''
        summary = ''
        url = ''
        click_url = ''
        mime_type = ''
        mod_date = ''
        cache = ''
        
class Search(webapp.RequestHandler):
    ''' Yahoo Developer Network Search API http://developer.yahoo.co.jp/webapi/search/websearch/v1/websearch.html
        example http://typea-mixi01.appspot.com/yh_s?q=book
        parameters 
            q: query strings
    '''
    __base_url = r'http://search.yahooapis.jp/WebSearchService/V1/webSearch'
    __aid = r'8I71w2axg65DK0hVG7YvNoX21E_RuSIGIcQgIe0pndm2hC.ARV7AvM2Mw3M8UWE-'
    __ns  = r'urn:yahoo:jp:srch'
    def qn(self, tag):
        '''' xmlns を付加したタグ名を返す '''
        return ElementTree.QName(self.__ns, tag).text
    
    def get(self):
        query = ''
        try:
            query = self.request.GET['q']
            query = query.encode('utf-8')
        except:
            pass
        
        param_map = {'appid':self.__aid,
                     'query':query,
                     'results':'50',
                     }
        
        param_list = [key + '=' + param_map[key] 
                      for key in param_map.keys()
                      ]
        
        q_results   = r'.//{0}'.format(self.qn('Result'))
        q_title     = r'./{0}'.format(self.qn('Title'))
        q_summary   = r'./{0}'.format(self.qn('Summary'))
        q_url       = r'./{0}'.format(self.qn('Url'))
        q_click_url = r'./{0}'.format(self.qn('ClickUrl'))
        q_mime_type = r'./{0}'.format(self.qn('MimeType'))
        q_mod_date  = r'./{0}'.format(self.qn('ModificationDate'))
        q_cache     = r'./{0}/{1}'.format(self.qn('Cache'),self.qn('Url'))
        
        url = self.__base_url + '?' + ('&'.join(param_list))
        f = urllib2.urlopen(url)
        root = ElementTree.parse(f).getroot()
        results = root.findall(q_results)
        
        search_list = []
        for result in results:
            itm = SearchResult()
            itm.title = result.find(q_title).text
            itm.summary = result.find(q_summary).text
            itm.url = result.find(q_url).text
            itm.click_url = result.find(q_click_url).text
            itm.mime_type = result.find(q_mime_type).text
            itm.mod_date = result.find(q_mod_date).text
            #itm.cache = result.find(q_cache).text
            search_list.append(itm)
        
        context = {
                   'query':query,
                   'items':search_list,
        }
        path = os.path.join(os.path.dirname(__file__), 'yahoo_search.html')
        self.response.out.write(template.render(path, context))
# -*- encoding: utf-8 -*-
import urllib
import urllib2
from xml.etree import ElementTree
import logging 

import yahoo_dev_api_settings

class YahooRelatedWordSearchResult(object):
    def __init__(self):
        self.item_list = []
        
class YahooRelatedWordSearch(yahoo_dev_api_settings.YahooDevApiBase):
    ''' Yahoo Developer Network Related Word Search API http://developer.yahoo.co.jp/webapi/search/assistsearch/v1/webunitsearch.html
        parameters 
            q: query strings
    '''
    @property
    def base_url(self):
        return r'http://search.yahooapis.jp/AssistSearchService/V1/webunitSearch'
    
    @property
    def xmlns(self):
        return r'urn:yahoo:jp:srchunit'
    
    def search(self, query_map):
        query = ''
        plain_query = ''
        encode = 'utf-8'

        try:
            query = query_map['q']
            plain_query = query.encode(encode)
            query = urllib.quote(plain_query, self.safe_chars)
        except:
            pass
        
        param_map = {'appid':self.aid,
                     'query':query,
                     'results':'30',
                     }
        
        param_list = [key + '=' + param_map[key] 
                      for key in param_map.keys()
                      ]
        
        q_results = r'.//%s' % (self.qn('Result'))
        
        url = self.base_url + '?' + ('&'.join(param_list))
        f = urllib2.urlopen(url)
        root = ElementTree.parse(f).getroot()
      
        search_result = YahooRelatedWordSearchResult()
        results = root.findall(q_results)
        
        search_list = []
        logging.info(q_results)
        for result in results:
            search_list.append(result.text)
        
        search_result.item_list = search_list
        return search_result

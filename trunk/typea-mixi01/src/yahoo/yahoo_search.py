# -*- encoding: utf-8 -*-
import urllib
import urllib2
from xml.etree import ElementTree
import logging 

import yahoo_dev_api_settings

class YahooSearchResult(object):
    
    def __init__(self):
        self.default_item_par_page = 50;
        self.total_results_available = 0;
        self.total_results_returned = 0;
        self.first_result_position = 0;
        self.item_list = []
    
    @property
    def end_position(self):
        return self.first_result_position + self.total_results_returned - 1 
    
class YahooSearchResultItem(object):
    def __init__(self):
        self.title = ''
        self.summary = ''
        self.url = ''
        self.click_url = ''
        self.mime_type = ''
        self.mod_date = ''
        self.cache = ''
        
class YahooSearch(yahoo_dev_api_settings.YahooDevApiBase):
    ''' Yahoo Developer Network Search API http://developer.yahoo.co.jp/webapi/search/websearch/v1/websearch.html
        parameters 
            q: query strings
    '''
    @property
    def base_url(self):
        return r'http://search.yahooapis.jp/WebSearchService/V1/webSearch'
    @property
    def xmlns(self):
        return r'urn:yahoo:jp:srch'
    
    def search(self, query_map):
        query = ''
        plain_query = ''
        encode = 'utf-8'

        try:
            query = query_map['q']
            query = query.encode(encode)
        except:
            pass
        
        try:
            start = query_map['s']
        except:
            pass
        
        param_map = {'appid':self.aid,
                     'query':query,
                     'start':str(start),
                     'results':'50',
                     }
      
        url = self.base_url
        data =  urllib.urlencode(param_map)
        f = urllib2.urlopen(url, data)

        root = ElementTree.parse(f).getroot()
        
        q_results    = r'.//%s' % (self.qn('Result'))
        q_title      = r'./%s'  % (self.qn('Title'))
        q_summary    = r'./%s' % (self.qn('Summary'))
        q_url        = r'./%s' % (self.qn('Url'))
        q_click_url  = r'./%s' % (self.qn('ClickUrl'))
        q_mime_type  = r'./%s' % (self.qn('MimeType'))
        q_mod_date   = r'./%s' % (self.qn('ModificationDate'))
        q_cache      = r'./%s/%s' % (self.qn('Cache'),self.qn('Url'))
        
        
        search_result = YahooSearchResult()
        
        search_result.total_results_available = int(root.get('totalResultsAvailable'))
        search_result.total_results_returned = int(root.get('totalResultsReturned'))
        search_result.first_result_position = int(root.get('firstResultPosition'))
        results = root.findall(q_results)
        
        search_list = []
        for result in results:
            itm = YahooSearchResult()
            itm.title = result.find(q_title).text
            
            smry = result.find(q_summary).text
            if smry:
                itm.summary = smry
            else:
                itm.summary = ''

            itm.url = result.find(q_url).text
            itm.click_url = result.find(q_click_url).text
            itm.mime_type = result.find(q_mime_type).text
            itm.mod_date = result.find(q_mod_date).text
            #itm.cache = result.find(q_cache).text
            search_list.append(itm)
        
        search_result.item_list = search_list
        return search_result

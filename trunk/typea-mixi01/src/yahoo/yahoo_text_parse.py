# -*- encoding: utf-8 -*-
import urllib
import urllib2
from xml.etree import ElementTree
import logging 

import yahoo_dev_api_settings

class YahooTextParseResult(object):
    def __init__(self):
        self.item_list = []
        self.most_refer = []

class YahooTextParseItem(object):
    def __init__(self):
        self.word = ''
        self.times = 0
    @property
    def encoded_word(self):
        if self.word:
            try:
                return urllib.quote(self.word.encode('utf-8'), '-._~')
            except:
                return ''
        else:
            return ''
    @property
    def font_style(self):
        if self.times > 8:
            return 'font-size:xx-large;color:red;font-weight:bold;'
        elif self.times > 5:
            return 'font-size:x-large;color:red;font-weight:bold;'
        elif self.times > 4:
            return 'font-size:large;font-weight:bold;'
        elif self.times > 3:
            return 'font-size:medium;'
        elif self.times > 2:
            return 'font-size:x-small;'
        return 'xx-small'
            
class YahooTextParser(yahoo_dev_api_settings.YahooDevApiBase):
    ''' Yahoo Developer Network Related Word Search API http://developer.yahoo.co.jp/webapi/jlp/ma/v1/parse.html
        parameters 
            q: query strings
    '''
    @property
    def base_url(self):
        return r'http://jlp.yahooapis.jp/MAService/V1/parse'
    
    @property
    def xmlns(self):
        return r'urn:yahoo:jp:jlp'
    
    def search(self, query_map):
        sentence = ''
        try:
            sentence = query_map['sentence']
            sentence = sentence.encode('utf-8')
        except:
            pass
        
        param_map = {'appid':self.aid,
                     'sentence':sentence,
                     'filter':'9',   
                     }
        url = self.base_url
        data =  urllib.urlencode(param_map)
        f = urllib2.urlopen(url, data)
        root = ElementTree.parse(f).getroot()

        q_results = r'./%s/%s/%s/%s' % (self.qn('ma_result'), 
                                     self.qn('word_list'), 
                                     self.qn('word'), 
                                     self.qn('surface'))
        
        search_result = YahooTextParseResult()
        results = root.findall(q_results)
        
        search_map = {}
        for result in results:
            itm = YahooTextParseItem()
            itm.word = result.text
            if search_map.has_key(itm.word):
                itm = search_map[itm.word]
                itm.times = itm.times + 1
            else:
                search_map[itm.word] = itm
                
        search_result.item_list = search_map.values()

        times_list = [itm.times for itm in search_result.item_list]
        times_list.sort(reverse=True)
        times_list = times_list[0:8]
            
        logging.warn(times_list)
        most_refer = []
        for itm in search_result.item_list:
            for t in times_list:
                if itm.times == t:
                    most_refer.append(itm)
                    times_list.remove(itm.times)
                    break;
            if len(times_list) < 1:
                break;
            
        search_result.most_refer = most_refer       
        return search_result
    

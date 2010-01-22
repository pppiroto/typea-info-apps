#!Python2.6
# -*- encoding: utf-8 -*-

import os
import urllib2

from google.appengine.ext import webapp
from google.appengine.ext.webapp import template

from yahoo_search import YahooSearch
from yahoo_text_parse import YahooTextParser
from amazon.amazon_search import AmazonRequest
from datetime import date
import logging

from google.appengine.ext import db

class AmazonItemEntity(db.Model):
    #Text 値と Blob 値はインデックスされない ->クエリが効かない
    group_key = db.StringProperty()
    entry_date = db.DateProperty()
    asin = db.StringProperty()
    detailPageURL = db.TextProperty()
    smallImageURL = db.TextProperty()
    title = db.StringProperty()

class AmazonResults(object):
    def __init__(self):
        self.word = ''
        self.item_list = []
        
class Search(webapp.RequestHandler):
    ''' example http://typea-mixi01.appspot.com/yh_s?q=book
        parameters 
            q: query strings
            s: start position
    '''
    def get(self):
        query = ''
        encode = 'utf-8'
        try:
            query = self.request.GET['q']
            query = query.encode(encode)
        except:
            pass
        
        start = 1
        try:
            start = self.request.GET['s']
        except:
            pass
        
        # Create Base Yahoo Search
        searcher = YahooSearch()
        search_result = searcher.search({'q':query,'s':start})

        next_page_start_pos = search_result.first_result_position + search_result.total_results_returned
        next_page_url = r'/yh_s?q=' + query + '&s=' + str(next_page_start_pos)
        
        previouse_page_start_pos = search_result.first_result_position - search_result.default_item_par_page
        if previouse_page_start_pos < 0:
            previouse_page_start_pos = 1
        prev_page_url = r'/yh_s?q=' + query + '&s=' + str(previouse_page_start_pos)
        
        
        # Create yahoo Related word search
        #word_searcher = YahooRelatedWordSearch()
        #word_result = word_searcher.search({'q':query})
        
        # Create yahoo Text parse
        text_parser = YahooTextParser()
        summaries = [s.summary for s in search_result.item_list]
        parse_result = text_parser.search({'sentence':' '.join(summaries)})
        
        # Amazon
        #*** Delete All Entities ***
        is_delete_all = False
        del_entry = AmazonItemEntity.all()
        if is_delete_all:
            for de in del_entry:
                de.delete()

        amazon_request = AmazonRequest()
        search_index = 'All'
        amazon_results = []
        for itm in parse_result.most_refer:
            amazon_result = AmazonResults()
           
            group_key = itm.word
            
            gql_q = db.GqlQuery("SELECT * FROM AmazonItemEntity WHERE group_key=:1", group_key)
            amaitms = gql_q.fetch(20)    
            if not amaitms:
                tmp_ama_list = amazon_request.request(urllib2.unquote(itm.word.encode('utf-8')), search_index)
            
                today = date.today()
                amaitms = []
                for amaitm in tmp_ama_list:
                    entity = AmazonItemEntity(
                                              group_key = group_key,
                                              entry_date = today,
                                              asin = amaitm.asin,
                                              detailPageURL = amaitm.detailPageURL,
                                              smallImageURL = amaitm.smallImageURL,
                                              title = amaitm.title,
                                              )
                    if not is_delete_all:
                        entity.put()
                    amaitms.append(entity)
            
            amazon_result.word = itm.word
            amazon_result.item_list = amaitms
            amazon_results.append(amazon_result)
        
        context = {
                   # Base Search
                   'query':query,
                   'total_results_available':search_result.total_results_available,
                   'total_results_returned':search_result.total_results_returned,
                   'first_result_position':search_result.first_result_position,
                   'end_position':search_result.end_position,
                   'prev_page_url':prev_page_url,
                   'next_page_url':next_page_url,
                   'items':search_result.item_list,
                   # Related Word Search
                   # 'word_items':word_result.item_list,
                   # Text Parse
                   'parsed_items':parse_result.item_list,
                   'amazon_results':amazon_results,
        }
        
        path = os.path.join(os.path.dirname(__file__), 'yahoo_search.html')
        self.response.out.write(template.render(path, context))

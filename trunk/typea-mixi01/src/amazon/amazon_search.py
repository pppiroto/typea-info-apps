# -*- encoding: utf-8 -*-

import urllib2
import amazon_ecs
import xml.parsers.expat
from datetime import datetime

from google.appengine.ext import db
import logging

class AmazonItemCacheInfo(db.Model):
    hit_count = db.IntegerProperty()
    total_count = db.IntegerProperty()
    
class AmazonSearchedItemEntity(db.Model):
    #Text 値と Blob 値はインデックスされない ->クエリが効かない
    group_key = db.StringProperty()
    search_index = db.StringProperty()
    entry_date = db.DateTimeProperty()
    asin = db.StringProperty()
    detailPageURL = db.TextProperty()
    smallImageURL = db.TextProperty()
    title = db.StringProperty()

class AmazonSearchItem(object):
    ''' Amazon ItemSearch Operation の結果格納  '''
    def __init__(self):
        self.asin = ''
        self.detailPageURL = ''
        self.smallImageURL = ''
        self.title = ''
        
class SAXTagHandler(object):
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
                self.item_list.append(AmazonSearchItem())
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

class AmazonRequest(object):
    ''' Amazon Product Advertise API　(ItemSearch) を作成'''
    def request(self, keyword, search_index):
        
        cache_inf = None
        cache_infs = AmazonItemCacheInfo.all()
        if cache_infs:
            for itm in cache_infs.fetch(1):
                cache_inf = itm
        if not cache_inf:
            cache_inf = AmazonItemCacheInfo(hit_count = 0, total_count = 0)
                    
        cache_inf.total_count = cache_inf.total_count + 1
         
        gql_q = db.GqlQuery("SELECT * FROM AmazonSearchedItemEntity WHERE group_key=:1 and search_index=:2", 
                            keyword,
                            search_index)

        items = []
        stored_items = gql_q.fetch(100)
        for stored_item in stored_items:
            item = AmazonSearchItem()
            item.asin = stored_item.asin
            item.detailPageURL = stored_item.detailPageURL
            item.smallImageURL = stored_item.smallImageURL
            item.title = stored_item.title
            items.append(item)
        
        if not items:
            operation = amazon_ecs.ItemSearch()
            operation.keywords = urllib2.unquote(keyword.encode('utf-8'))
            operation.search_index = search_index
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

            today = datetime.today()
            for tmp_item in h.item_list:
                entity = AmazonSearchedItemEntity(
                                          group_key = keyword,
                                          search_index = search_index, 
                                          entry_date = today,
                                          asin = tmp_item.asin,
                                          detailPageURL = tmp_item.detailPageURL,
                                          smallImageURL = tmp_item.smallImageURL,
                                          title = tmp_item.title,
                                          )
                entity.put()

                item = AmazonSearchItem()
                item.asin = entity.asin
                item.detailPageURL = entity.detailPageURL
                item.smallImageURL = entity.smallImageURL
                item.title = entity.title
                items.append(item)
        else:
            cache_inf.hit_count = cache_inf.hit_count + 1
            
        cache_inf.put()
            
        return items 

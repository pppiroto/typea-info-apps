#!Python2.6
# -*- encoding: utf-8 -*-

from google.appengine.ext import webapp

from datetime import datetime, timedelta
from google.appengine.ext import db
#from amazon.amazon_search import AmazonItemEntity

class AmazonItemEntity(db.Model):
    #Text 値と Blob 値はインデックスされない ->クエリが効かない
    group_key = db.StringProperty()
    entry_date = db.DateProperty()
    asin = db.StringProperty()
    detailPageURL = db.TextProperty()
    smallImageURL = db.TextProperty()
    title = db.StringProperty()


import logging
        
class DeleteAmazonEntity(webapp.RequestHandler):
    ''' example http://typea-mixi01.appspot.com/am_del?range=all
        parameters 
            range:   delete range
    '''
    def get(self):
        template = "<html><head></head><body>%s</body></html>"
        items = None
        
        try:
            del_range = self.request.GET['range']
        except:
            pass
        
        if del_range == 'all':
            items = AmazonItemEntity.all()
        else:
            try:
                days = int(del_range)
                del_base_datetime = datetime.today() - timedelta(days=days)
    
                items = db.GqlQuery("SELECT * FROM AmazonItemEntity WHERE entry_date <= :1", 
                            del_base_datetime)
            except:
                items = None
        
        cnt = 0
        if items:
            for item in items:
                item.delete()
                cnt += 1
                if cnt > 100:
                    break
                
        msg = '%d amazon items were deleted.' % cnt 
        return self.response.out.write(template % msg)    
        
        
        
        

#!Python2.6
# -*- encoding: utf-8 -*-

from google.appengine.ext import webapp

from yahoo.yahoo_utils import AmazonItemEntity
import logging
        
class DeleteAmazonEntity(webapp.RequestHandler):
    ''' example http://typea-mixi01.appspot.com/am_del?range=all
        parameters 
            range:   delete range
    '''
    def get(self):
        template = "<html><head></head><body>%s</body></html>"

        del_range = 'all'
        try:
            del_range = self.request.GET['range']
        except:
            pass
        
        if del_range == 'all':
            items = AmazonItemEntity.all()
            for item in items:
                item.delete()
         
            return self.response.out.write(template % ('all amazon items were deleted.'))    
        
        
        
        

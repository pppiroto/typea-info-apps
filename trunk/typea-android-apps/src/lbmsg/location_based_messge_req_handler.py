#!Python2.7
# -*- encoding: utf-8 -*-

from google.appengine.api import users
from google.appengine.ext import webapp
from google.appengine.ext import db
import json

from datetime import datetime

from local_based_message import LocalBasedMessage

class InsertLocalBasedMessage(webapp.RequestHandler):
    def get(self):
        self.post()
    
    def post(self):
        lat = self.request.get('lat')
        lon = self.request.get('lon')
        message = self.request.get('message')
        
        lbmsg = LocalBasedMessage(geo_pt=db.GeoPt(lat, lon),
                                  message=message,
                                  create_date=datetime.now()
                                  )
        lbmsg.put()
        return self.response.out.write(json.write(lbmsg.to_dict()))
        
        
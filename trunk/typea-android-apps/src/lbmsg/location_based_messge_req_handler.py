#!Python2.7
# -*- encoding: utf-8 -*-

from google.appengine.api import users
from google.appengine.ext import webapp
from google.appengine.ext import db
import json

from datetime import datetime

from local_based_message import LocalBasedMessage

MAX_MESSAGES = 100

class AuthCheck(webapp.RequestHandler):
    def get(self):
        self.post()
    
    def post(self):
        user = users.get_current_user()
        if user:
            self.response.headers['Content-Type'] = 'text/html'
            ret = '<html><body><a href="%s">logout</a></body></html>' % (users.create_logout_url(self.request.uri))
            return self.response.out.write(ret)
        else:
            #Unauthorized
            #return self.response.set_status(401)
            self.redirect(users.create_login_url(self.request.uri))
        
class InsertLocalBasedMessage(webapp.RequestHandler):
    def get(self):
        self.post()
    
    def post(self):
        user = users.get_current_user()
        if user:
            lat = self.request.get('lat')
            lon = self.request.get('lon')
            message = self.request.get('message')
            
            lbmsg = LocalBasedMessage(geo_pt=db.GeoPt(lat, lon),
                                      message=message,
                                      create_date=datetime.now()
                                      )
            lbmsg.put()
            return self.response.out.write(json.write(lbmsg.to_dict()))
        else:
            #Unauthorized
            #return self.response.set_status(401)
            self.redirect(users.create_login_url(self.request.uri))
        
class LocalBasedMessageList(webapp.RequestHandler):
    def get(self):
        self.post()
    
    def post(self): 
        user = users.get_current_user()
        if user:
            from_geo_pt=db.GeoPt(self.request.get('from_lat'),
                                 self.request.get('from_lon'))
            to_geo_pt=db.GeoPt(  self.request.get('to_lat'),
                                 self.request.get('to_lon'))            

            q = LocalBasedMessage.gql("WHERE geo_pt >=:1 AND geo_pt <=:2"
                                 ,from_geo_pt
                                 ,to_geo_pt)
            
            messages = q.fetch(MAX_MESSAGES)
            
            ret = []
            for message in messages:
                ret.append(message.to_dict())
            return self.response.out.write(json.write(ret))
        
        else:
            #Unauthorized
            #return self.response.set_status(401)
            self.redirect(users.create_login_url(self.request.uri))
                
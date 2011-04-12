#!Python2.7
# -*- encoding: utf-8 -*-

from google.appengine.ext import db

class LocalBasedMessage(db.Model):
    owner = db.UserProperty(auto_current_user_add=True)
    geo_pt = db.GeoPtProperty()
    message = db.TextProperty()
    create_date = db.DateTimeProperty()
    
    def to_dict(self):
        return {'key':          str(self.key()),
                'owner':        str(self.owner),
                'lat':          str(self.geo_pt.lat),
                'lon':          str(self.geo_pt.lon),
                'message':      self.message.encode('utf-8'),
                'create_date':  str(self.create_date),
                }
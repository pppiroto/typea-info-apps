
from google.appengine.ext import db
import logging

class FunctionPointProject(db.Model):
    owner = db.UserProperty(auto_current_user_add=True)
    system_name = db.StringProperty()
    application_name = db.StringProperty()
    
    def to_dict(self):
        return { 'system_name':self.system_name,
                 'application_name':self.application_name,
                }
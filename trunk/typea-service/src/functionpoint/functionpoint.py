#!Python2.6
# -*- encoding: utf-8 -*-
from google.appengine.ext import db
import logging
import common

def mesurement_type(tuple=False):
    d = {'development':u'新規開発',    
         'enhancement':u'機能拡張',  
         'application':u'アプリケーション',
        }
    if tuple:
        return [t for t in d.items()]
    else:
        return d

class FunctionPointProject(db.Model):
    owner = db.UserProperty(auto_current_user_add=True)
    system_name = db.StringProperty()
    application_name = db.StringProperty()
    mesurement_type = db.StringProperty()
    
    def to_dict(self):
        return { 'id':str(self.key()),
                 'system_name':self.system_name,
                 'application_name':self.application_name,
                 'mesurement_type':self.mesurement_type,
                 'mesurement_type_name':mesurement_type()[self.mesurement_type],
                }
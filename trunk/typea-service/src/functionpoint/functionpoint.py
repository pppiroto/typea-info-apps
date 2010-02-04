#!Python2.6
# -*- encoding: utf-8 -*-
from google.appengine.ext import db
import logging
import common

def dict_or_tuple(d, tuple=False):
    if tuple:
        return [t for t in d.items()]
    else:
        return d
    
def mesurement_type(tuple=False):
    d = {'development':u'新規開発',    
         'enhancement':u'機能拡張',  
         'application':u'アプリケーション',
        }
    return dict_or_tuple(d,tuple)

def datafunction_type(tuple=False):
    d = {'ilf':u'内部論理ファイル(Internal Logical File:ILF)',
         'eif':u'外部インターフェースファイル(External Interface File: EIF)'
         }
    return dict_or_tuple(d,tuple)
    
class FunctionPointProject(db.Model):
    owner = db.UserProperty(auto_current_user_add=True)
    system_name = db.StringProperty()
    application_name = db.StringProperty()
    mesurement_type = db.StringProperty()
    
    def to_dict(self):
        return { 'key':str(self.key()),
                 'owner':str(self.owner),
                 'system_name':self.system_name,
                 'application_name':self.application_name,
                 'mesurement_type':self.mesurement_type,
                 'mesurement_type_name':mesurement_type()[self.mesurement_type],
                }
        
class DataFunction(db.Model):
    owner = db.UserProperty(auto_current_user_add=True)
    function_type = db.StringProperty()
    data_element_type = db.IntegerProperty()
    record_element_type = db.IntegerProperty()
    
    
class TransactionalFunction(db.Model):
    owner = db.UserProperty(auto_current_user_add=True)            
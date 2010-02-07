#!Python2.6
# -*- encoding: utf-8 -*-
from google.appengine.ext import db
import logging
import common

COMPLEXITY_TABLE = (('Low',    'Low',    'Average'),
                    ('Low',    'Average','High'),
                    ('Average','High',   'High'),
                    )

def dict_or_tuple(d, tuple=False):
    if tuple:
        return [t for t in d.items()]
    else:
        return d
    
def get_dict_value(d, k):
    v = ''
    try:
        v = d[k]
    except:
        v =''
    return v 
   
def mesurement_type(tuple=False):
    d = {'':u'',
         'development':u'新規開発',    
         'enhancement':u'機能拡張',  
         'application':u'アプリケーション',
        }
    return dict_or_tuple(d,tuple)

def datafunction_type(tuple=False):
    d = {'':u'',
         'ilf':u'内部論理ファイル(ILF)',
         'eif':u'外部インターフェースファイル(EIF)'
         }
    return dict_or_tuple(d,tuple)

def tranfunction_type(tuple=False):
    d = {'':u'',
         'ei':u'外部入力(EI)',
         'eo':u'外部出力(EO)',
         'eq':u'外部照会(EQ)',
         }    
    return dict_or_tuple(d,tuple)

class FunctionPointProject(db.Model):
    owner = db.UserProperty(auto_current_user_add=True)
    system_name = db.StringProperty()
    application_name = db.StringProperty()
    mesurement_type = db.StringProperty()
    sort_order = db.IntegerProperty()
    
    def to_dict(self):
        return { 'key':str(self.key()),
                 'owner':str(self.owner),
                 'system_name':self.system_name,
                 'application_name':self.application_name,
                 'mesurement_type':self.mesurement_type,
                 'mesurement_type_name':get_dict_value(mesurement_type(),self.mesurement_type),
                }
        
class FunctionEntity(db.Model):
    owner = db.UserProperty(auto_current_user_add=True)
    project_key = db.StringProperty()
    function_type = db.StringProperty()
    function_name = db.StringProperty()
    function_category = db.StringProperty()
    measurement_index1 = db.IntegerProperty()
    measurement_index2 = db.IntegerProperty()
    sort_order = db.IntegerProperty()
    
    def complexity(self):
        if self.function_type == 'data':
            return self.data_complexity()
        elif self.function_type == 'tran':
            return self.tran_complexity()
        return ''

    def function_point(self):
        if self.function_type == 'data':
            return self.data_function_point()
        elif self.function_type == 'tran':
            return self.tran_function_point()
        return ''
    
    def data_complexity(self):
        x = None    
        if 1 == self.measurement_index2:
            x = 0
        elif 2 <= self.measurement_index2 <=5:
            x = 1
        elif 6 <= self.measurement_index2:
            x = 2

        y = None
        if 1 <= self.measurement_index1 <= 19:
            y = 0
        elif 20 <= self.measurement_index1 <= 50:
            y = 1
        elif 51 <= self.measurement_index1: 
            y = 2
            
        if x <> None and y <> None:
            return COMPLEXITY_TABLE[x][y]
        else:
            return ''

    def tran_complexity(self):

        x = None    
        if 0 <= self.measurement_index2 <= 1:
            x = 0
        elif 2 == self.measurement_index2:
            x = 1
        elif 3 <= self.measurement_index2:
            x = 2

        y = None
        if self.function_category == 'ei':
            if 1 <= self.measurement_index1 <= 4:
                y = 0
            elif 5 <= self.measurement_index1 <= 15:
                y = 1
            elif 16 <= self.measurement_index1: 
                y = 2
        elif self.function_category in ('eo','eq'):
            if 1 <= self.measurement_index1 <= 5:
                y = 0
            elif 6 <= self.measurement_index1 <= 19:
                y = 1
            elif 20 <= self.measurement_index1: 
                y = 2
        
        if x <> None and y <> None:
            return COMPLEXITY_TABLE[x][y]
        else:
            return ''
    
    def data_function_point(self):
        fp = ((7,10,15),
              (5, 7,10),
              )
        complex = self.complexity()

        x = None
        if self.function_category == 'ilf':
            x = 0
        elif self.function_category == 'eif':
            x = 1
        
        y = None    
        if complex == 'Low':
            y = 0
        elif complex == 'Average':
            y = 1
        elif complex == 'High':
            y = 2
        
        if x <> None and y <> None:
            return fp[x][y]
        else:
            return ''

    def tran_function_point(self):
        fp = ((3,4,6),
              (4,5,7),
              (3,4,6),
              )
        
        complex = self.complexity()

        
        x = None
        if self.function_category == 'ei':
            x = 0
        elif self.function_category == 'eo':
            x = 1
        elif self.function_category == 'eq':
            x = 2
            
        y = None
        if complex == 'Low':
            y = 0
        elif complex == 'Average':
            y = 1
        elif complex == 'High':
            y = 2
            
        if x <> None and y <> None:
            return fp[x][y]
        else:
            return ''
    
    def to_dict(self):
        return { 'key':str(self.key()),
                 'owner':str(self.owner),
                 'project_key':self.project_key,
                 'function_type':self.function_type,
                 'function_name':self.function_name,
                 'function_category':self.function_category,
                 'measurement_index1':self.measurement_index1,
                 'measurement_index2':self.measurement_index2,
                 'complexity':self.complexity(),
                 'function_point':self.function_point(),
                }

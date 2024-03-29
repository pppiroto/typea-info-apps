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
    #
    owner = db.UserProperty(auto_current_user_add=True)
    system_name = db.StringProperty()
    application_name = db.StringProperty()
    mesurement_type = db.StringProperty()
    #
    data_communications = db.IntegerProperty()       
    distoributed_processing = db.IntegerProperty()   
    performance = db.IntegerProperty()
    heavily_used_configuration = db.IntegerProperty()
    transaction_rate = db.IntegerProperty()
    online_data_entry = db.IntegerProperty()
    enduser_efficiency = db.IntegerProperty()
    online_update = db.IntegerProperty()
    complex_processing = db.IntegerProperty()
    reusability = db.IntegerProperty()
    installation_ease = db.IntegerProperty()
    operational_ease = db.IntegerProperty()
    multiple_sites = db.IntegerProperty()
    facilitate_change = db.IntegerProperty()
    #
    sort_order = db.IntegerProperty()
    
    def mesurement_type_name(self):
        return get_dict_value(mesurement_type(), self.mesurement_type)
        
    def total_adjust_points(self):
        return sum( (self.data_communications ,
                    self.distoributed_processing ,
                    self.performance ,
                    self.heavily_used_configuration ,
                    self.transaction_rate ,
                    self.online_data_entry ,
                    self.enduser_efficiency ,
                    self.online_update ,
                    self.complex_processing ,
                    self.reusability ,
                    self.installation_ease ,
                    self.operational_ease ,
                    self.multiple_sites ,
                    self.facilitate_change ,
                    )
                )
        
    def to_dict(self):
        return {'key':                          str(self.key()),
                'owner':                        str(self.owner),
                'system_name':                  self.system_name,
                'application_name':             self.application_name,
                'mesurement_type':              self.mesurement_type,
                'mesurement_type_name':         get_dict_value(mesurement_type(),self.mesurement_type),
                'data_communications':          self.data_communications ,
                'distoributed_processing':      self.distoributed_processing ,
                'performance':                  self.performance ,
                'heavily_used_configuration':   self.heavily_used_configuration ,
                'transaction_rate':             self.transaction_rate ,
                'online_data_entry':            self.online_data_entry ,
                'enduser_efficiency':           self.enduser_efficiency ,
                'online_update':                self.online_update ,
                'complex_processing':           self.complex_processing ,
                'reusability':                  self.reusability ,
                'installation_ease':            self.installation_ease ,
                'operational_ease':             self.operational_ease ,
                'multiple_sites':               self.multiple_sites ,
                'facilitate_change':            self.facilitate_change ,
                'total_adjust_points':          self.total_adjust_points(),
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
    
    def function_category_name(self):
        if self.function_type == 'data':
            d = datafunction_type()
        elif self.function_type == 'tran':
            d = tranfunction_type()
        return get_dict_value(d,self.function_category)
        
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
        return '0'
       
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
        elif 2 <= self.measurement_index2 <=3:
            x = 1
        elif 4 <= self.measurement_index2:
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
            return '0'
    
    def to_dict(self):
        return { 'key':str(self.key()),
                 'owner':str(self.owner),
                 'project_key':self.project_key,
                 'sort_order':self.sort_order,
                 'function_type':self.function_type,
                 'function_name':self.function_name,
                 'function_category':self.function_category,
                 'measurement_index1':self.measurement_index1,
                 'measurement_index2':self.measurement_index2,
                 'complexity':self.complexity(),
                 'function_point':self.function_point(),
                }

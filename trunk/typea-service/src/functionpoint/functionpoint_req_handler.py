#!Python2.6
# -*- encoding: utf-8 -*-
import sys
#sys.path.insert(0, 'gdata.zip')

import os
from google.appengine.api import users
from google.appengine.ext import webapp
from google.appengine.ext.webapp import template
from google.appengine.ext import db

import common
from functionpoint import FunctionPointProject, FunctionEntity
import functionpoint
import re
import json

import cgi
import logging

MAX_PROJECT  = 20
MAX_FUNCTION = 100

def to_idx(s):
    if not s.isdigit():
        s = '0'
    return int(s)
    
def list_projects(user):
    projects = []
    q = FunctionPointProject.gql("WHERE owner=:1 ORDER BY sort_order", user)
    results = q.fetch(MAX_PROJECT)
    for result in results:
        projects.append(result.to_dict()) 
    return projects

def list_functions(project_key):
    functions = []
    q = FunctionEntity.gql("WHERE project_key=:1 ORDER BY sort_order", project_key)
    results = q.fetch(MAX_FUNCTION)
    sort_order = 1
    for result in results:
        result.sort_order = sort_order
        sort_order += 1
        functions.append(result.to_dict())
        result.put()
        
    return functions

class InitialFunctionPointPage(webapp.RequestHandler):
    def get(self):
        self.post()
        
    def post(self):
        context = common.default_context(self.request.uri)
        context['mesurement_type'] = functionpoint.mesurement_type(tuple=True) 
        context['datafunction_type'] = functionpoint.datafunction_type(tuple=True) 
        context['tranfunction_type'] = functionpoint.tranfunction_type(tuple=True) 
        
        
        path = 'templates/functionpoint.html'
        return self.response.out.write(template.render(path, context))

class LoadFunctionPointProject(webapp.RequestHandler):
    def get(self):
        self.post();
    
    def post(self):
        user = users.get_current_user()
        if user:
            projects = list_projects(user)
            return self.response.out.write(json.write({'items':projects}))
        else:
            err = {'error':common.message('login_err',common.AppSettings(self.request.uri))}
            
            return self.response.out.write(json.write(err))

class CreateFunctionPointProject(webapp.RequestHandler):
    def get(self):
        self.post();
    
    def post(self):
        user = users.get_current_user()
        if user:
            system_name = self.request.get('project_profile_system_name')
            application_name = self.request.get('project_profile_application_name')
            mesurement_type = self.request.get('project_profile_mesurement_type')
            
            project = FunctionPointProject(system_name=system_name,
                                           application_name=application_name,
                                           mesurement_type=mesurement_type,
                                           data_communications=0,       
                                           distoributed_processing=0,   
                                           performance=0,
                                           heavily_used_configuration=0,
                                           transaction_rate=0,
                                           online_data_entry=0,       
                                           enduser_efficiency=0,      
                                           online_update=0,     
                                           complex_processing=0,        
                                           reusability=0,     
                                           installation_ease=0,         
                                           operational_ease=0,      
                                           multiple_sites=0,       
                                           facilitate_change=0,         
                                           sort_order=0,
                                           )
            project.put();
            return self.response.out.write(json.write(project.to_dict()))
        else:
            err = {'error':common.message('login_err',common.AppSettings(self.request.uri))}
            return self.response.out.write(json.write(err))
        
        err = {'error':'Unknown Error'}
        return self.response.out.write(json.write(err))

class UpdateFunctionPointProject(webapp.RequestHandler):
    def get(self):
        self.post();
    
    def post(self):
        user = users.get_current_user()
        if user:
            key_str = self.request.get('project_profile_key')
            system_name = self.request.get('project_profile_system_name')
            application_name = self.request.get('project_profile_application_name')
            mesurement_type = self.request.get('project_profile_mesurement_type')
  
            key = db.Key(key_str)
            q = FunctionPointProject.gql("WHERE __key__ =:1", key)
            
            projects = q.fetch(1)
            for project in projects:
                project.system_name                =  system_name
                project.application_name           =  application_name
                project.mesurement_type            =  mesurement_type
                project.put()
            
            projects = list_projects(user) 
            return self.response.out.write(json.write({'items':projects}))
        else:
            err = {'error':common.message('login_err',common.AppSettings(self.request.uri))}
            return self.response.out.write(json.write(err))
        
        err = {'error':'Unknown Error'}
        return self.response.out.write(json.write(err))

class UpdateAdjustPoint(webapp.RequestHandler):
    def get(self):
        self.post();
    
    def post(self):
        user = users.get_current_user()
        if user:
            key_str = self.request.get('project_profile_key')
            if key_str.strip() == '':
                err = {'error':common.message('project_not_selected')}
                return self.response.out.write(json.write(err))

            #
            data_communications           =      self.request.get('data_communications')
            distoributed_processing       =      self.request.get('distoributed_processing')
            performance                   =      self.request.get('performance')
            heavily_used_configuration    =      self.request.get('heavily_used_configuration')
            transaction_rate              =      self.request.get('transaction_rate')
            online_data_entry             =      self.request.get('online_data_entry')
            enduser_efficiency            =      self.request.get('enduser_efficiency')
            online_update                 =      self.request.get('online_update')
            complex_processing            =      self.request.get('complex_processing')
            reusability                   =      self.request.get('reusability')
            installation_ease             =      self.request.get('installation_ease')
            operational_ease              =      self.request.get('operational_ease')
            multiple_sites                =      self.request.get('multiple_sites')
            facilitate_change             =      self.request.get('facilitate_change')
            #

            key = db.Key(key_str)
            q = FunctionPointProject.gql("WHERE __key__ =:1", key)
            
            projects = q.fetch(1)
            for project in projects:
                project.data_communications        =  to_idx(data_communications)
                project.distoributed_processing    =  to_idx(distoributed_processing)
                project.performance                =  to_idx(performance)
                project.heavily_used_configuration =  to_idx(heavily_used_configuration)
                project.transaction_rate           =  to_idx(transaction_rate)
                project.online_data_entry          =  to_idx(online_data_entry)
                project.enduser_efficiency         =  to_idx(enduser_efficiency)
                project.online_update              =  to_idx(online_update)
                project.complex_processing         =  to_idx(complex_processing)
                project.reusability                =  to_idx(reusability)
                project.installation_ease          =  to_idx(installation_ease)
                project.operational_ease           =  to_idx(operational_ease)
                project.multiple_sites             =  to_idx(multiple_sites)
                project.facilitate_change          =  to_idx(facilitate_change)
                
                project.put()
            
            return self.response.out.write(json.write(project.to_dict()))
        else:
            err = {'error':common.message('login_err',common.AppSettings(self.request.uri))}
            return self.response.out.write(json.write(err))
        
        err = {'error':'Unknown Error'}
        return self.response.out.write(json.write(err))

class DeleteFunctionPointProject(webapp.RequestHandler):
    def get(self):
        self.post();
    
    def post(self):
        user = users.get_current_user()
        if user:
            key_str = self.request.get('project_profile_key')
            key = db.Key(key_str)
            q_parent = FunctionPointProject.gql("WHERE __key__ =:1", key)
            
            projects = q_parent.fetch(1)
            for project in projects:
                q_child = FunctionEntity.gql("WHERE project_key =:1", key_str)
                entities = q_child.fetch(MAX_FUNCTION)
                for entity in entities:
                    entity.delete()
                project.delete()
            
            projects = list_projects(user) 
            return self.response.out.write(json.write({'items':projects}))
        else:
            err = {'error':common.message('login_err',common.AppSettings(self.request.uri))}
            return self.response.out.write(json.write(err))
        
        err = {'error':'Unknown Error'}
        return self.response.out.write(json.write(err))

class LoadFunction(webapp.RequestHandler):
    def get(self):
        self.post();
    
    def post(self):
        user = users.get_current_user()
        if user:
            project_key   = self.request.get('project_key')
            
            if project_key <> '':    
                key = db.Key(project_key)
                q = FunctionPointProject.gql("WHERE __key__ =:1", key)
                projects = q.fetch(1)
                project = None
                for p in projects:
                    project = p
                    break
                
                functions = list_functions(project_key)
                
                return self.response.out.write(json.write({'project':project.to_dict(),
                                                           'items':functions}))
            else:
                err = {'error':common.message('project_not_selected')}
                return self.response.out.write(json.write(err))
        else:
            err = {'error':common.message('login_err',common.AppSettings(self.request.uri))}
            return self.response.out.write(json.write(err))
        
        err = {'error':'Unknown Error'}
        return self.response.out.write(json.write(err))

class AddFunction(webapp.RequestHandler):
    def get(self):
        self.post();
    
    def post(self):
        user = users.get_current_user()
        if user:
            project_key   = self.request.get('project_key')
            function_type = self.request.get('function_type')
            
            if project_key <> '':    
                func_entity = FunctionEntity(
                                             project_key=project_key,
                                             function_type=function_type,
                                             function_name='',
                                             measurement_index1=0,
                                             measurement_index2=0,
                                             sort_order=99,
                                            );
                func_entity.put()
                
                return self.response.out.write(json.write(func_entity.to_dict()))
            else:
                err = {'error':common.message('project_not_selected')}
                return self.response.out.write(json.write(err))
        else:
            err = {'error':common.message('login_err',common.AppSettings(self.request.uri))}
            return self.response.out.write(json.write(err))
        
        err = {'error':'Unknown Error'}
        return self.response.out.write(json.write(err))

class UpdateFunction(webapp.RequestHandler):
    def get(self):
        self.post();
    
    def post(self):
        user = users.get_current_user()
        if user:
            key_str   = self.request.get('key')
            function_name = self.request.get('function_name')
            function_category = self.request.get('function_category')
            measurement_index1_str = self.request.get('measurement_index1')
            measurement_index2_str = self.request.get('measurement_index2')
            
            try:
                measurement_index1 = int(measurement_index1_str.strip())
                measurement_index2 = int(measurement_index2_str.strip())
            except:
                err = {'error':'Invalid number.'}
                return self.response.out.write(json.write(err))

            key = db.Key(key_str);
            
            func_entity = None
            q = FunctionEntity.gql("WHERE __key__ = :1", key)
            results = q.fetch(1)
            for result in results:
                result.function_name = function_name
                result.function_category = function_category
                result.measurement_index1 = measurement_index1
                result.measurement_index2 = measurement_index2
                result.put()
            
                func_entity = result
            
            if func_entity:
                return self.response.out.write(json.write(func_entity.to_dict()))
            else:
                err = {'error':'Entity is not found.'}
                return self.response.out.write(json.write(err))
                
        else:
            err = {'error':common.message('login_err',common.AppSettings(self.request.uri))}
            return self.response.out.write(json.write(err))
        
        err = {'error':'Unknown Error'}
        return self.response.out.write(json.write(err))

class DeleteFunction(webapp.RequestHandler):
    def get(self):
        self.post();
    
    def post(self):
        user = users.get_current_user()
        if user:
            key_str   = self.request.get('key')
            key = db.Key(key_str);
            
            func_entity = None
            q = FunctionEntity.gql("WHERE __key__ = :1", key)
            results = q.fetch(1)
            for result in results:
                result.delete()
                func_entity = result
            
            if func_entity:
                return self.response.out.write(json.write({'key':key_str}))
            else:
                err = {'error':'Entity is not found.'}
                return self.response.out.write(json.write(err))
                
        else:
            err = {'error':common.message('login_err',common.AppSettings(self.request.uri))}
            return self.response.out.write(json.write(err))
        
        err = {'error':'Unknown Error'}
        return self.response.out.write(json.write(err))


class ReOrderFunction(webapp.RequestHandler):
    def get(self):
        self.post();
    
    def post(self):
        user = users.get_current_user()
        if user:
            project_key   = self.request.get('project_key')
            key_str   = self.request.get('key')
            
            q = FunctionEntity.gql("WHERE project_key=:1 ORDER BY sort_order", project_key)
            
            functions = q.fetch(MAX_FUNCTION)
            for i, func in enumerate(functions):
                if key_str == str(func.key()):
                    if i > 0:
                        tmp = functions[i - 1]
                        functions[i - 1] = functions[i]
                        functions[i] = tmp
                        
                        functions[i - 1].put()
                        functions[i].put() 
                    break

            sort_order = 1
            for func in functions:
                func.sort_order = sort_order
                func.put()
                sort_order += 1
                
            result = list_functions(project_key)
             
            return self.response.out.write(json.write({'items':result}))
        else:
            err = {'error':common.message('login_err',common.AppSettings(self.request.uri))}
            return self.response.out.write(json.write(err))
        
        err = {'error':'Unknown Error'}
        return self.response.out.write(json.write(err))
            
            
            
            
            
            
            
            

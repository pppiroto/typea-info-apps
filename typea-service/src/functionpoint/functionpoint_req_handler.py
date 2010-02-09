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

def list_projects(user):
    projects = []
    q = FunctionPointProject.gql("WHERE owner=:1", user)
    results = q.fetch(MAX_PROJECT)
    for result in results:
        projects.append(result.to_dict()) 
    return projects

def list_functions(project_key):
    functions = []
    q = FunctionEntity.gql("WHERE project_key=:1", project_key)
    results = q.fetch(MAX_FUNCTION)
    for result in results:
        functions.append(result.to_dict())
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
            err = {'error':common.message('login_err')}
            
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
            err = {'error':common.message('login_err')}
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
                project.system_name = system_name
                project.application_name = application_name
                project.mesurement_type = mesurement_type
                project.put()
            
            projects = list_projects(user) 
            return self.response.out.write(json.write({'items':projects}))
        else:
            err = {'error':common.message('login_err')}
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
            err = {'error':common.message('login_err')}
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
                functions = list_functions(project_key)
                
                return self.response.out.write(json.write({'items':functions}))
            else:
                err = {'error':common.message('project_not_selected')}
                return self.response.out.write(json.write(err))
        else:
            err = {'error':common.message('login_err')}
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
                                             sort_order=0,
                                            );
                func_entity.put()
                
                return self.response.out.write(json.write(func_entity.to_dict()))
            else:
                err = {'error':common.message('project_not_selected')}
                return self.response.out.write(json.write(err))
        else:
            err = {'error':common.message('login_err')}
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
            err = {'error':common.message('login_err')}
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
            err = {'error':common.message('login_err')}
            return self.response.out.write(json.write(err))
        
        err = {'error':'Unknown Error'}
        return self.response.out.write(json.write(err))




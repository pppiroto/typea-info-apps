#!Python2.6
# -*- encoding: utf-8 -*-
import sys
#sys.path.insert(0, 'gdata.zip')

import os
from google.appengine.api import users
from google.appengine.ext import webapp
from google.appengine.ext.webapp import template
import common
from functionpoint import FunctionPointProject
import functionpoint
import re
import json

import cgi
import logging

def list_projects(user):
    projects = []
    q = FunctionPointProject.gql("WHERE owner=:1", user)
    results = q.fetch(20)
    for result in results:
        projects.append(result.to_dict()) 
    return projects

class InitialFunctionPointPage(webapp.RequestHandler):
    def get(self):
        self.post()
        
    def post(self):
        context = common.default_context(self.request.uri)
        context['mesurement_type'] = functionpoint.mesurement_type(tuple=True) 
        
        
        path = 'templates/functionpoint.html'
        return self.response.out.write(template.render(path, context))

class ListFunctionPointProject(webapp.RequestHandler):
    def get(self):
        self.post();
    
    def post(self):
        user = users.get_current_user()
        if user:
            projects = list_projects(user) 
            return self.response.out.write(json.write({'items':projects}))
        else:
            err = {'error':'Please login!'}
            return self.response.out.write(json.write(err))

class CreateFunctionPointProject(webapp.RequestHandler):
    def get(self):
        self.post();
    
    def post(self):
        user = users.get_current_user()
        if user:
            system_name = self.request.get('create_system_name')
            application_name = self.request.get('create_application_name')
            mesurement_type = self.request.get('create_mesurement_type')
            
            project = FunctionPointProject(system_name=system_name,
                                           application_name=application_name,
                                           mesurement_type=mesurement_type)
            project.put();
            
            projects = []
            q = FunctionPointProject.gql("WHERE owner=:1", user)
            results = q.fetch(20)
            for result in results:
                projects.append(result.to_dict()) 
            
            return self.response.out.write(json.write({'items':projects}))
        else:
            err = {'error':'Please login!'}
            return self.response.out.write(json.write(err))
        
        err = {'error':'Unknown Error'}
        return self.response.out.write(json.write(err))








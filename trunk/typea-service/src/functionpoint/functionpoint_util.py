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
import re
import json

import cgi
import logging

class InitialFunctionPointPage(webapp.RequestHandler):
    def get(self):
        self.post()
        
    def post(self):
        context = common.default_context(self.request.uri)
        
        path = 'templates/functionpoint.html'
        return self.response.out.write(template.render(path, context))

class CreateFunctionPointProject(webapp.RequestHandler):
    def get(self):
        self.post();
    
    def post(self):
        user = users.get_current_user()
        if user:
            system_name = self.request.get('create_system_name')
            application_name = self.request.get('create_application_name')
    
            project = FunctionPointProject(system_name=system_name,
                                           application_name=application_name)
            project.put();
            return self.response.out.write(json.write(project.to_dict()))
        else:
            err = {'error':'Please login!'}
            return self.response.out.write(json.write(err))
        
        err = {'error':'Unknown Error'}
        return self.response.out.write(json.write(err))








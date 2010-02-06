#!Python2.6
# -*- encoding: utf-8 -*-

from google.appengine.ext.webapp import template
from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app

from cocomo import cocomo_request_handler as ccm_handler
from functionpoint import functionpoint_req_handler as fp_handler
import common

class MainPage(webapp.RequestHandler):
    def get(self):
        path = 'templates/index.html'
        context = common.default_context(self.request.uri)
        return self.response.out.write(template.render(path, context))
    
application = webapp.WSGIApplication([
                                      ('/',                     MainPage),
                                      #Cocomo
                                      ('/cocomo',       ccm_handler.InitialCocomoPage),
                                      ('/cocomo/calc',  ccm_handler.CalcCocomoResponse),
                                      #FunctionPoint
                                      ('/fp',                  fp_handler.InitialFunctionPointPage),
                                      ('/fp/projects/load',    fp_handler.LoadFunctionPointProject),
                                      ('/fp/projects/create',  fp_handler.CreateFunctionPointProject),
                                      ('/fp/projects/update',  fp_handler.UpdateFunctionPointProject),
                                      ('/fp/projects/delete',  fp_handler.DeleteFunctionPointProject),
                                      ('/fp/function/load',    fp_handler.LoadFunction),
                                      ('/fp/function/add',     fp_handler.AddFunction),
                                      ('/fp/function/update',  fp_handler.UpdateFunction),
                                      ], debug=True)

def main():
    run_wsgi_app(application)

if __name__ == "__main__":
    main()

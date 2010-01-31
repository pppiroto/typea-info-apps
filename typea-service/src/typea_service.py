#!Python2.6
# -*- encoding: utf-8 -*-

from google.appengine.ext.webapp import template
from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app

from cocomo import cocomo_util
from functionpoint import functionpoint_util
import common

class MainPage(webapp.RequestHandler):
    def get(self):
        path = 'templates/index.html'
        context = common.default_context(self.request.uri)
        return self.response.out.write(template.render(path, context))
    
application = webapp.WSGIApplication([
                                      ('/',                     MainPage),
                                      #Cocomo
                                      ('/cocomo',       cocomo_util.InitialCocomoPage),
                                      ('/cocomo/calc',  cocomo_util.CalcCocomoResponse),
                                      #FunctionPoint
                                      ('/functionpoint',                functionpoint_util.InitialFunctionPointPage),
                                      ('/functionpoint/createproject',  functionpoint_util.CreateFunctionPointProject),
                                      ], debug=True)

def main():
    run_wsgi_app(application)

if __name__ == "__main__":
    main()

#!Python2.6
# -*- encoding: utf-8 -*-

from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app

from cocomo import cocomo_util

class MainPage(webapp.RequestHandler):
    def get(self):
        self.redirect('/cocomo')
    
application = webapp.WSGIApplication([
                                      ('/',         MainPage),
                                      ('/cocomo',       cocomo_util.InitialCocomoPage),
                                      ('/calccocomo',   cocomo_util.CalcCocomoResponse),
                                      ], debug=True)

def main():
    run_wsgi_app(application)

if __name__ == "__main__":
    main()

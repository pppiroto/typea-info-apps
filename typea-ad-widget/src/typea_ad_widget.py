#!Python2.6
# -*- encoding: utf-8 -*-

from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app

from amazon import amazon_utils
from job import exec_job
class MainPage(webapp.RequestHandler):
    def get(self):
        self.redirect('/am_is?q=amazon')
    
application = webapp.WSGIApplication([
                                      ('/',             MainPage),
                                      ('/am_is',        amazon_utils.AmazonItemSearch),
                                      ('/create_links', amazon_utils.CreateLinks),
                                      ('/am_del',       exec_job.DeleteAmazonEntity),
                                      ], debug=False)

def main():
    run_wsgi_app(application)

if __name__ == "__main__":
    main()

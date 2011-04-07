from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app

from lbmsg import location_based_messge_req_handler as lbmsg_hander

class MainPage(webapp.RequestHandler):
    def get(self):
        self.response.headers['Content-Type'] = 'text/plain'
        self.response.out.write('Hello, TYPEA.INFO APPS')

application = webapp.WSGIApplication([
                                      ('/', MainPage), 
                                      ('/lbmsg/insert', lbmsg_hander.InsertLocalBasedMessage),
                                     ], debug=True)
def main():
    run_wsgi_app(application)

if __name__ == "__main__":
    main()

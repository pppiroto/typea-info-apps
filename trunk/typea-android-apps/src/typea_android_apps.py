from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app

from cardroid import cardroid_request_handler as cardroid_handler

class MainPage(webapp.RequestHandler):
    def get(self):
        self.response.headers['Content-Type'] = 'text/plain'
        self.response.out.write('Hello, webapp World!')

application = webapp.WSGIApplication([
                                      ('/', MainPage), 
                                      #Cocomo
                                      ('/cardroid', cardroid_handler.InitialCardroidPage),
                                      #OpenID Auth
                                     ], debug=True)

def main():
    run_wsgi_app(application)

if __name__ == "__main__":
    main()

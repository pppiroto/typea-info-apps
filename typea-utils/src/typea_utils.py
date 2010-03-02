from google.appengine.ext.webapp import template
from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app

class MainPage(webapp.RequestHandler):
    def get(self):
        path = 'templates/index.html'
        context = {}
        return self.response.out.write(template.render(path, context))

class ConvChar(webapp.RequestHandler):
    def get(self):
        self.post();
    
    def post(self):
        path = 'templates/convchar.html'
        context = {}
        return self.response.out.write(template.render(path, context))
    
application = webapp.WSGIApplication([('/',          MainPage),
                                      ('/convchar',  ConvChar),
                                      ], debug=True)

def main():
    run_wsgi_app(application)

if __name__ == "__main__":
    main()

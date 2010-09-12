
from google.appengine.ext import webapp
from google.appengine.api import users

class InitialCardroidPage(webapp.RequestHandler):
    def get(self):
        self.post()
        
    def post(self):
        #login_cookies = self.request.cookies.get('dev_appserver_login', '')
        
        user = users.get_current_user()
        if user:
            user_logout_url = users.create_logout_url("/")
            return self.response.out.write("<html><body>%s <a href='%s'>logout</a></body></html>" % (user.nickname(), user_logout_url));
        else:
            user_login_url  = users.create_login_url('/cardroid')
            return self.response.out.write("<html><body><a href='%s'>login</a></body></html>" % (user_login_url));
        
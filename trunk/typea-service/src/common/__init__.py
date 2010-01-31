#!Python2.6
# -*- encoding: utf-8 -*-
from google.appengine.api import users

def default_context(request_uri='/'):
    context = {'settings':AppSettings(request_uri)}
    return context
    
    
class AppSettings(object):
    def __init__(self, request_uri='/'):
        user = users.get_current_user()
        
        if user:
            self.user_nickname = user.nickname()
        else:
            self.user_nickname = 'Guest'

        self.user_login_url = users.create_login_url(request_uri)
        self.user_logout_url = users.create_logout_url("/")
        self.user_is_admin = users.is_current_user_admin()


#!Python2.6
# -*- encoding: utf-8 -*-
from google.appengine.api import users

def message(key):
    msg_table = {'login_err':u'ログインしてください',
                 'project_not_selected':u'プロジェクトを選択してください',
                 
                 
                }
    
    if key in msg_table:
        return msg_table[key]
    else:
        return u'原因不明のエラー'

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

def dict_to_tuplelist(d):
    l = []
    for t in d.items():
        l.append(t)
    return l


#!Python2.6
# -*- encoding: utf-8 -*-

import os
from google.appengine.ext import webapp
from google.appengine.ext.webapp import template
from cocomo import Cocomo 
from json_util import json
import cgi
import logging

class CalcCocomo(webapp.RequestHandler):
    
    def get(self):
        return self.post()
    
    def post(self):
        
        calc_mode = self.request.get('calc_mode')
        num = self.request.get('num')
        
        cocomo = None
        if num.isdigit():
            cocomo = Cocomo(calc_mode,kdsi=int(num),effort=int(num))
            logging.info(json.write(cocomo.to_dict()))
            return self.response.out.write(json.write(cocomo.to_dict()))
        
        
        phase_title = ( u'要件分析',
                        u'製品設計',
                        u'プログラミング',
                        u'テスト計画',
                        u'検証',
                        u'プロジェクト管理 ',
                        u'構成管理と品質保障',
                        u'文書化',
                        u'合計',
                       )
        
        
        
        context = {'phase_title':phase_title}
        
        path = os.path.join(os.path.dirname(__file__), 'cocomo.html')
        return self.response.out.write(template.render(path, context))


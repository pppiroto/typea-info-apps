#!Python2.6
# -*- encoding: utf-8 -*-
import sys
#sys.path.insert(0, 'gdata.zip')

import os
from google.appengine.ext import webapp
from google.appengine.ext.webapp import template
from cocomo import Cocomo
import common
import re
import json

import cgi
import logging

class InitialCocomoPage(webapp.RequestHandler):
    def get(self):
        self.post()
        
    def post(self):

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
        
        context = common.default_context(self.request.uri)
        context['phase_title'] = phase_title
        
        #path = os.path.join(os.path.dirname(__file__), 'templates/cocomo.html')
        path = 'templates/cocomo.html'
        return self.response.out.write(template.render(path, context))

class CalcCocomoResponse(webapp.RequestHandler):
    
    def get(self):
        self.post()
        
    def post(self):
        calc_mode = self.request.get('calc_mode')
        num = self.request.get('num')
        
        cocomo = None
        if (calc_mode in ('kdsi','effort')) and re.match(r'^[0-9]+[\.]{0,1}[0-9]*$',num):
            cocomo = Cocomo(calc_mode,kdsi=float(num),effort=float(num))
            #logging.warn(json.write(cocomo.to_dict()))
            return self.response.out.write(json.write(cocomo.to_dict()))

        return self.response.out.write(json.write({'error':u'入力項目に誤りがあります.'}))

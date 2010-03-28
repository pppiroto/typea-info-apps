#!Python2.6
# -*- encoding: utf-8 -*-
import sys
#sys.path.insert(0, 'gdata.zip')
sys.path.insert(0, 'xlwt.zip')

import xlwt

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

class ExportResponse(webapp.RequestHandler):
    def get(self):
        self.post()
        
    def post(self):
        calc_mode = self.request.get('calc_mode')
        num = self.request.get('num')

        cocomo = None
        if (calc_mode in ('kdsi','effort')) and re.match(r'^[0-9]+[\.]{0,1}[0-9]*$',num):
            cocomo = Cocomo(calc_mode,kdsi=float(num),effort=float(num))

            self.response.headers.add_header("Content-Disposition", 'attachment; filename="cocomo.xls"' )
            
            #
            font_title = xlwt.Font()
            font_title.bold = True 
            style_title = xlwt.XFStyle()
            style_title.font = font_title

            #
            font_num = xlwt.Font()
            font_num.colour_index = 4
            style_num = xlwt.XFStyle()
            style_num.num_format_str = '0.00'
            style_num.font = font_num
            
            #
            font_link = xlwt.Font()
            font_link.colour_index = 4
            font_link.underline = xlwt.Font.UNDERLINE_DOUBLE
            style_link = xlwt.XFStyle()
            style_link.font = font_link
            #
            
            wb = xlwt.Workbook(encoding='utf-8')
            ws = wb.add_sheet(u'COCOMO')
            
            #
            base_col = 1
            r = 1
            
            col_w = (0x0a00,0x1e00,0x0a00,0x0a00,0x2800,0x0a00,0x0a00,)
            for i, v in enumerate(col_w):
                ws.col(i).width = v
                
            #
            ws.write(r, base_col, u'COCOMO による工数計算', style_title)
            
            #
            n = "HYPERLINK"
            ws.write(r, base_col+2, xlwt.Formula(n + '("http://typea-service.appspot.com/cocomo";"COCOMO")'), style_link)
            
            #
            r += 2
            ws.write(r, base_col, u'COCOMO計算式', style_title)
            dat = (
                (u'プログラムサイズ(KLOC)', cocomo.kdsi),  
                (u'工数(PM)',          cocomo.effort),
                (u'開発期間(M)',        cocomo.tdev),  
                (u'開発要員(P)',        cocomo.fsp),   
                (u'生産性(KLOC/PM)',    cocomo.prod),  
            )
            for d in dat:
                r += 1
                ws.write(r, base_col,     d[0])
                ws.write(r, base_col + 1, d[1], style_num)    

            #
            r += 2
            ws.write(r, base_col, u'工数の局面への分布', style_title)
            dat = (
                (u'計画と要件定義',        cocomo.plan_requirements),
                (u'製品設計',             cocomo.product_design),
                (u'詳細設計',             cocomo.detailed_design),
                (u'プログラミングと単体テスト',  cocomo.code_unit_test),
                (u'統合とテスト',           cocomo.integration_test),
                (u'合計',                cocomo.total),
            )
            for d in dat:
                r += 1
                ws.write(r, base_col,     d[0])
                ws.write(r, base_col + 1, d[1], style_num)    
            #
            r += 2
            ws.write(r, base_col, u'活動の局面ごとの分布', style_title)
            sub_title = ( u'', u'', u'', u'プログラミング', u'', u'' )
            r += 1
            c = base_col
            for t in sub_title:
                ws.write(r, c, t)
                c += 1 
            #    
            sub_title = ( u'局面', u'計画と要件定義', u'製品設計', u'詳細設計-プログラミングと単体テスト', u'統合とテスト', u'合計' )
            r += 1
            c = base_col
            for t in sub_title:
                ws.write(r, c, t)
                c += 1 
            #
            sub_title = (
                u'要件分析',
                u'製品設計',
                u'プログラミング',
                u'テスト計画',
                u'検証',
                u'プロジェクト管理',
                u'構成管理と品質保障',
                u'文書化',
                u'合計',
            )
            r2 = r
            for t in sub_title:
                r2 += 1
                ws.write(r2, base_col, t)
            
            #
            dat = (
                cocomo.phase_plan_requirements, 
                cocomo.phase_product_design,    
                cocomo.phase_programming,       
                cocomo.phase_integration_test,  
                cocomo.phase_total,             
            )
            c = base_col
            for d in dat:
                r3 = r
                c += 1
                for rd in d:
                    r3 += 1
                    ws.write(r3, c,  rd, style_num)
            
            wb.save(self.response.out)

            return None

        return self.response.out.write(json.write({'error':u'入力項目に誤りがあります.'}))
        
    
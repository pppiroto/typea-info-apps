# -*- encoding:utf-8 -*-
from xml.etree import ElementTree
import logging

class YahooDevApiBase(object):
    def __init__(self):
        pass
        
    @property
    def aid(self):
        return r'8I71w2axg65DK0hVG7YvNoX21E_RuSIGIcQgIe0pndm2hC.ARV7AvM2Mw3M8UWE-'
    @property
    def sefe_chars(self):
        return '-._~'
    @property
    def xmlns(self):
        return ''
    
    def qn(self, tag):
        '''' xmlns を付加したタグ名を返す '''
        return ElementTree.QName(self.xmlns, tag).text
    
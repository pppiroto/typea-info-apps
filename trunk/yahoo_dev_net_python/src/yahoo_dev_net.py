# -*- coding: utf-8 -*-

# http://developer.yahoo.co.jp/webapi/jlp/ma/v1/parse.html
# app_id : 8I71w2axg65DK0hVG7YvNoX21E_RuSIGIcQgIe0pndm2hC.ARV7AvM2Mw3M8UWE-
#
# 形態素解析（Morphological Analysis） http://ja.wikipedia.org/wiki/%E5%BD%A2%E6%85%8B%E7%B4%A0%E8%A7%A3%E6%9E%90
#
import urllib
import urllib2

class BaseRequest(object):
    __safe_chars = r'-._~'
    __app_id     = r'8I71w2axg65DK0hVG7YvNoX21E_RuSIGIcQgIe0pndm2hC.ARV7AvM2Mw3M8UWE-'
    
    def __init__(self, url):
        self.url = url
        self.parm_map = {}
        self.set_parameter('appid', self.__app_id)

    def get_param_data(self):
        return urllib.urlencode(self.parm_map)

    def request(self):
        return '{0}?{1}'.format(self.url, self.get_param_data())
   
    def get_parameter(self, key):
        return self.parm_map[key]
    def set_parameter(self, key, value):
        self.parm_map[key] = value
    def del_parameter(self, key):
        del self.parm_map[key]

class MorphologicAnalysisRequest(BaseRequest):
    def __init__(self):
        super(MorphologicAnalysisRequest,self).__init__(r'http://jlp.yahooapis.jp/MAService/V1/parse')
        self.result = ""
    @property
    def sentence(self):
        return self.get_parameter('sentence')
    @sentence.stter
    def sentence(self, value):
        self.set_parameter('sentence', value)
    @property
    def results(self):
        return self.get_parameter('results')
    @results.setter
    def results(self, value):
        '''ma | uniq'''
        self.set_parameter('results', value)
    @property
    def response(self):
        self.get_parameter('response')
    @response.setter
    def response(self, value):
        '''surface, reading, pos, baseform, feature'''
        self.set_parameter('response', value)
    @property
    def filter(self):
        self.get_parameter('filter')
    @filter.setter
    def filter(self, value):
        self.set_parameter('filter', value) 
    
if __name__ == '__main__':
    import re
    #url = r'http://typea.info/blg/glob/'
    url = r'http://yutori7.2ch.net/test/read.cgi/news4plus/1251810117/'
    html = [ re.compile(r'(<[^>]+>|[a-zA-Z0-9+-_&#(){}\'"!|$*%]+)').sub('', l) for l in urllib2.urlopen(url) if l.strip() != '']
    html = [ l.strip() for l in html if l.strip() != '']
    html = '\n'.join(html)
    
    size = 0
    sentence = ''
    for l in html:
        size += len(l)
        if (size > 20000):
            break
        sentence += l

    print len(sentence)
    
    req = MorphologicAnalysisRequest()
    req.results = 'uniq'
    req.filter = '9'
    req.sentence = sentence
    
    print len(req.get_param_data())
    # POST Request
    f = urllib2.urlopen(req.url, req.get_param_data())
    o = open(r'c:\work\test.xml', 'w')
    o.write(f.read())
    #print f.read()  
    
    
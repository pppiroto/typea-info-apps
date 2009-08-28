#!Python2.6
# -*- coding: utf-8 -*-

import amazon_ecs
import urllib2
from xml.etree import ElementTree

def qn(tag):
    return ElementTree.QName(ns, tag).text

ns = r'http://webservices.amazon.com/AWSECommerceService/2005-10-05'

# ./{http://webservices.amazon.com/AWSECommerceService/2005-10-05}ItemAttributes/{http://webservices.amazon.com/AWSECommerceService/2005-10-05}Title
q_items  = './/{0}'.format(qn('Item'))
q_title  = './{0}/{1}'.format(qn('ItemAttributes'), qn('Title'))
q_author = './{0}/{1}'.format(qn('ItemAttributes'), qn('Author'))
q_asin   = './{0}'.format(qn('ASIN'))
q_url    = './{0}'.format(qn('DetailPageURL'))
q_img    = './{0}/{1}'.format(qn('SmallImage'), qn('URL'))

operation = amazon_ecs.ItemSearch()
operation.keywords('手塚　治虫')
operation.search_index('Books')
operation.response_group('Large')
request = operation.request()
print 'REQUEST : {0}'.format(request)

root = ElementTree.parse(urllib2.urlopen(request)).getroot()
items =  root.findall(q_items)
for item in items:
    print '-' * 100
    print 'TITLE : {0}'.format(item.find(q_title).text)
    print 'AUTHOR : {0}'.format(item.find(q_author).text)
    print 'ASIN : {0}'.format(item.find(q_asin).text)
    print 'URL : {0}'.format(item.find(q_url).text)
    print 'IMG : {0}'.format(item.find(q_img).text)

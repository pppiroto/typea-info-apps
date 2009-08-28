#!Python2.6
# -*- coding: utf-8 -*-

import amazon_ecs
import urllib2
from xml.etree import ElementTree

operation = amazon_ecs.ItemSearch()
operation.keywords('手塚　治虫')
operation.search_index('Books')
operation.response_group('Large')

request = operation.request()
print 'REQUEST : %s' % request 
f = urllib2.urlopen(request)

dom = ElementTree.parse(f)

root = dom.getroot()
items =  root.findall('.//{http://webservices.amazon.com/AWSECommerceService/2005-10-05}Item')

for item in items:
    print item.tag
    
#!Python2.6
# -*- coding: utf-8 -*-

import amazon_ecs
import urllib2
import xml.parsers.expat

proc_start = False
img_start = False
now_key = ''
item_list = []

class SearchedItem:
    asin = ''
    detailPageURL = ''
    smallImageURL = ''

def StartElementHandler(name, attributes):
    global proc_start
    global img_start
    global now_key
    global item_list
    
    if name == 'Items':
        proc_start = True
    if name == 'SmallImage':
        img_start = True
    if proc_start:
        if name == 'Item':
            item_list.append(SearchedItem())
        else:
            now_key = name

def EndElementHandler(name):
    global proc_start
    global img_start
    
    if name == 'Items':
        proc_start = False
    if name == 'SmallImage':
        img_start = False

def CharacterDataHandler(data):
    global proc_start
    global img_start
    global now_key
    global item_list

    if proc_start:
        idx = len(item_list)
        idx = idx -1
        if idx >= 0:
            if now_key == 'ASIN':
                item_list[idx].asin = data
            if now_key == 'DetailPageURL':
                item_list[idx].detailPageURL = data
            if img_start and now_key == 'URL':
                item_list[idx].smallImageURL = data

operation = amazon_ecs.ItemSearch()
operation.keywords('手塚　治虫')
operation.search_index('Books')
operation.response_group('Small')

request = operation.request()
print request

f = urllib2.urlopen(request)

p = xml.parsers.expat.ParserCreate()
p.StartElementHandler = StartElementHandler
p.EndElementHandler = EndElementHandler
p.CharacterDataHandler = CharacterDataHandler

p.Parse(f.read())

for itm in item_list:
    print '<a href="%s"><img src="%s" border="0"/></a>' % (itm.detailPageURL, itm.smallImageURL)



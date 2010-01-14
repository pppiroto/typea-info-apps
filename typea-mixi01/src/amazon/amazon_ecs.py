#!Python2.6
# -*- coding: utf-8 -*-
import urllib
from datetime import datetime
import hashlib, hmac 
import base64 

#@see: http://d.hatena.ne.jp/niiyan/20090509/1241884365
#@see: http://typea.info/blg/glob/2009/07/amazon_product_advertising_api.html
#@see: http://typea.info/blg/glob/2009/07/google_app_engine.html
class Operation(object):
    __safe_chars        = '-._~' 
    __ecs_url           = 'http://ecs.amazonaws.jp/onca/xml'
    __service           = 'AWSECommerceService'
    __access_key_id     = '1498TGK1YPN1JATPXXG2'
    __associate_tag     = 'typea09-22' 
    __secret_access_key = 'DiHCermoiVMaJZtByxDeJac4M18+gnMTD7igJH8Z'
    __http_verb         = 'GET'
    __value_of_host_header_in_lowercase = '/onca/xml'
    __http_request_uri  = 'ecs.amazonaws.jp'

    def __init__(self):
        self.__param_map = {}
    def operation_name(self):
        return ''
    def request(self):
        self.set_parameter('Service', self.__service)
        self.set_parameter('AWSAccessKeyId', self.__access_key_id)
        self.set_parameter('AssociateTag', self.__associate_tag)
        self.set_parameter('Operation', self.operation_name)
        self.set_parameter('Timestamp', datetime.utcnow().isoformat() + 'Z')
        
        #Name-Value Pairs
        n_v_pair_list = [urllib.quote(key, self.__safe_chars) + '=' + 
                         urllib.quote(self.__param_map[key], self.__safe_chars)
                         for key in self.__param_map.keys()]

        #Sorted Pairs
        n_v_pair_list.sort()
        request_parm_str = '&'.join(n_v_pair_list)
        
        #String-To-Sign
        sing_part_list = [self.__http_verb, 
                          self.__http_request_uri, 
                          self.__value_of_host_header_in_lowercase, 
                          request_parm_str]
        
        str_to_sign = '\n'.join(sing_part_list) 
        
        hmac_digest = hmac.new(self.__secret_access_key, str_to_sign, hashlib.sha256).digest()
        base64_encoded = base64.b64encode(hmac_digest)
        signature = urllib.quote(base64_encoded);
        
        return '%s?%s&Signature=%s' % (self.__ecs_url, request_parm_str, signature)
    
    def get_parameter(self, key):
        return self.__param_map[key]
    
    def set_parameter(self, key, value=''):
        self.__param_map[key] = value 

    def del_parameter(self, key):
        del self.__param_map[key]
   
    def get_response_group(self):
        return self.get_parameter('ResponseGroup')
    def set_response_group(self, value='Medium'):
        self.set_parameter('ResponseGroup', value)
    response_group = property(get_response_group, set_response_group)
        

class ItemSearch(Operation):
    '''@see: http://docs.amazonwebservices.com/AWSECommerceService/latest/DG/index.html?ItemSearch.html'''
    @property
    def operation_name(self):
        return 'ItemSearch'
    
    def get_keywords(self):
        self.get_parameter('keywords')
    def set_keywords(self, value):
        self.set_parameter('Keywords', value)
    keywords = property(get_keywords, set_keywords)
    
    def get_search_index(self):
        return self.get_parameter('SearchIndex')
    def set_search_index(self, value='Books'):
        self.set_parameter('SearchIndex', value)
    search_index = property(get_search_index, set_search_index)

    

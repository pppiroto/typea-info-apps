#!Python2.6
# -*- coding: utf-8 -*-
import urllib
from datetime import datetime
import hashlib, hmac 
import base64 

#@see: http://d.hatena.ne.jp/niiyan/20090509/1241884365
class Operation(object):
    __param_map         = {}
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
        pass
    def operation_name(self):
        return ''
    def request(self):
        self.set_parameter('Service', self.__service)
        self.set_parameter('AWSAccessKeyId', self.__access_key_id)
        self.set_parameter('AssociateTag', self.__associate_tag)
        self.set_parameter('Operation', self.operation_name())
        self.set_parameter('Timestamp', datetime.utcnow().isoformat() + 'Z')
        
        #Name-Value Pairs
        n_v_pair_list = []
        for key in self.__param_map.keys():
            n_v_pair_list.append(urllib.quote(key, self.__safe_chars) + '=' + urllib.quote(self.__param_map[key], self.__safe_chars))

        #Sorted Pairs
        n_v_pair_list.sort()
        request_parm_str = '&'.join(n_v_pair_list)
        
        #String-To-Sign
        sing_part_list = [self.__http_verb, self.__http_request_uri, self.__value_of_host_header_in_lowercase, request_parm_str]
        str_to_sign = '\n'.join(sing_part_list) 
        
        hmac_digest = hmac.new(self.__secret_access_key, str_to_sign, hashlib.sha256).digest()
        base64_encoded = base64.b64encode(hmac_digest)
        signature = urllib.quote(base64_encoded);
        
        return '%s?%s&Signature=%s' % (self.__ecs_url, request_parm_str, signature)
    
    def set_parameter(self, key, value='', remove=False):
        if remove:
            del self.__param_map[key]
        else:
            self.__param_map[key] = value 
            
    def response_group(self, value='Medium', remove=False):
        self.set_parameter('ResponseGroup', value, remove=False)

class ItemSearch(Operation):
    '''@see: http://docs.amazonwebservices.com/AWSECommerceService/latest/DG/index.html?ItemSearch.html'''
    def operation_name(self):
        return 'ItemSearch'
    def keywords(self, value, remove=False):
        self.set_parameter('Keywords', value, remove)
    def search_index(self, value='Books', remove=False):
        self.set_parameter('SearchIndex', value, remove)


    

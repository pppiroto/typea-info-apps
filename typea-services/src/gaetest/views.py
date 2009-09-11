# Create your views here.

from google.appengine.ext import db
from django.shortcuts import render_to_response
from gaetest.models import GaeTest
import datetime

def index(request):
    g_list = GaeTest.gql('order by message')
    return render_to_response('gaetest/index.html', {'g_list':g_list})
    
def add(request):
    msg = request.POST['message']
    g = GaeTest(message=msg, create_date=datetime.datetime.now())
    g.save()
    g_list = GaeTest.gql('order by message')
    return render_to_response('gaetest/index.html', {'g_list':g_list})
    
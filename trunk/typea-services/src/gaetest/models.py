from appengine_django.models import BaseModel
from google.appengine.ext import db

# Create your models here.

class GaeTest(BaseModel):
    message = db.StringProperty()
    create_date = db.DateTimeProperty
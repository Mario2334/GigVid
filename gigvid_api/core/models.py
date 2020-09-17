import datetime

from django.db import models


# Create your models here.

def add_datetime():
    hours = 1
    current_date_and_time = datetime.datetime.now()
    hours_added = datetime.timedelta(hours=hours)
    return current_date_and_time + hours_added


class Gig(models.Model):
    name = models.CharField(null=False, max_length=300)
    description = models.TextField(null=False)
    user = models.ForeignKey("user.User",null=False,on_delete=models.RESTRICT)
    url = models.URLField(default="https://www.testvid.com")
    scheduled_time = models.DateTimeField(default=add_datetime)
    is_active = models.BooleanField(default=True)
    header_image = models.URLField(default="https://bit.ly/2FDitkQ")


class Ticket(models.Model):
    gig = models.ForeignKey(Gig, on_delete=models.RESTRICT)
    user = models.ForeignKey("user.User", on_delete=models.RESTRICT)
    created = models.DateTimeField(auto_now_add=True)


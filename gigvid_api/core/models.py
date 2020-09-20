import datetime

from django.db import models


# Create your models here.

def add_datetime():
    hours = 24
    current_date_and_time = datetime.datetime.now()
    hours_added = datetime.timedelta(hours=hours)
    return current_date_and_time + hours_added


class Gig(models.Model):
    id = models.AutoField(primary_key=True)
    name = models.CharField(null=False, max_length=300)
    description = models.TextField(null=False)
    user = models.ForeignKey("user.User", null=False, on_delete=models.RESTRICT)
    join_url = models.URLField(default="https://www.testvid.com")
    host_url = models.URLField(default="https://www.testvid.com")
    scheduled_time = models.DateTimeField(default=add_datetime)
    is_active = models.BooleanField(default=True)
    duration = models.IntegerField(help_text="Duration in Minutes")
    header_image = models.URLField(default="https://bit.ly/2FDitkQ")
    created_at = models.DateTimeField(auto_now_add=True)
    price = models.IntegerField()

    class Meta:
        ordering = ("-created_at",)


class Order(models.Model):
    id = models.AutoField(primary_key=True)
    payment_link_id = models.CharField(max_length=500)
    final_price = models.IntegerField()
    is_successful = models.BooleanField(default=False)


class Ticket(models.Model):
    id = models.AutoField(primary_key=True)
    gig = models.ForeignKey(Gig, on_delete=models.RESTRICT)
    user = models.ForeignKey("user.User", on_delete=models.RESTRICT)
    created_at = models.DateTimeField(auto_now_add=True)
    order = models.ForeignKey(Order,on_delete=models.CASCADE)
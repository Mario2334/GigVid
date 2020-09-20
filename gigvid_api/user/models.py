from django.contrib.auth.models import AbstractUser
from django.db import models
from django.utils.translation import gettext_lazy as _


# Create your models here.
class User(AbstractUser):
    hobby = models.ForeignKey("Hobby", null=True, on_delete=models.RESTRICT)
    email = models.EmailField(_('email address'), blank=True,unique=True)


class Hobby(models.Model):
    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=300)

    class Meta:
        verbose_name = 'hobby'
        verbose_name_plural = 'hobbies'


class BankAccount(models.Model):
    id = models.AutoField(primary_key=True)
    ifsc = models.CharField(max_length=200)
    account_no = models.CharField(max_length=200)
    contact_id = models.CharField(max_length=300)
    name = models.CharField(max_length=500)
    user = models.OneToOneField(User,on_delete=models.CASCADE)
    balance = models.IntegerField(default=0)
    fund_account_id = models.CharField(max_length=500,null=True,blank=True)

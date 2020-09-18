from django.contrib.auth.models import AbstractUser
from django.db import models


# Create your models here.
class User(AbstractUser):
    hobby = models.ForeignKey("Hobby", null=True, on_delete=models.RESTRICT)


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

# Generated by Django 3.1.1 on 2020-09-20 07:57

from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('user', '0006_auto_20200919_1939'),
    ]

    operations = [
        migrations.AlterField(
            model_name='bankaccount',
            name='user',
            field=models.OneToOneField(on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL),
        ),
    ]
# Generated by Django 3.1.1 on 2020-09-18 20:22

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('core', '0002_auto_20200918_0333'),
    ]

    operations = [
        migrations.AddField(
            model_name='gig',
            name='duration',
            field=models.IntegerField(default=60, help_text='Duration in Minutes'),
            preserve_default=False,
        ),
    ]

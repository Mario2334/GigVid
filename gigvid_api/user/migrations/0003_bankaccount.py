# Generated by Django 3.1.1 on 2020-09-18 22:31

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('user', '0002_auto_20200917_1803'),
    ]

    operations = [
        migrations.CreateModel(
            name='BankAccount',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('ifsc', models.CharField(max_length=200)),
                ('account_no', models.CharField(max_length=200)),
            ],
        ),
    ]

# Generated by Django 3.1.1 on 2020-09-20 07:49

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('core', '0008_auto_20200920_1140'),
    ]

    operations = [
        migrations.AddField(
            model_name='ticket',
            name='order',
            field=models.ForeignKey(default=1, on_delete=django.db.models.deletion.CASCADE, to='core.order'),
            preserve_default=False,
        ),
    ]

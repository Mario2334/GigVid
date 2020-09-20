# Generated by Django 3.1.1 on 2020-09-19 08:34

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('user', '0003_bankaccount'),
    ]

    operations = [
        migrations.AddField(
            model_name='bankaccount',
            name='contact_id',
            field=models.CharField(default='cont_FemzGkuKpweizi', max_length=300),
            preserve_default=False,
        ),
        migrations.AddField(
            model_name='bankaccount',
            name='user',
            field=models.ForeignKey(default=1, on_delete=django.db.models.deletion.CASCADE, to='user.user'),
            preserve_default=False,
        ),
    ]

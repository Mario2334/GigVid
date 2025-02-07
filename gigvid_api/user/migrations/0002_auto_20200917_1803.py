# Generated by Django 3.1.1 on 2020-09-17 18:03

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('user', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='Hobby',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('name', models.CharField(max_length=300)),
            ],
            options={
                'verbose_name': 'hobby',
                'verbose_name_plural': 'hobbies',
            },
        ),
        migrations.AddField(
            model_name='user',
            name='hobby',
            field=models.ForeignKey(null=True, on_delete=django.db.models.deletion.RESTRICT, to='user.hobby'),
        ),
    ]

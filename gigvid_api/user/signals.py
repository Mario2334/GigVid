from django.db.models.signals import pre_save
from django.dispatch import receiver
import bcrypt

from user.models import User


@receiver(pre_save, sender=User)
def create_user_profile(sender, instance, **kwargs):

    salt = bcrypt.gensalt()
    instance.password = bcrypt.hashpw(instance.password, salt)
    instance.save()

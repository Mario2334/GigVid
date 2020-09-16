from django.apps import AppConfig


class UserConfig(AppConfig):
    name = 'user'

    def ready(self):
        pass
        # import user.signals
"""
Django settings for gigvid_api project.

Generated by 'django-admin startproject' using Django 3.1.1.

For more information on this file, see
https://docs.djangoproject.com/en/3.1/topics/settings/

For the full list of settings and their values, see
https://docs.djangoproject.com/en/3.1/ref/settings/
"""
import pymysql
pymysql.install_as_MySQLdb()
# pymysql.version_info = (1, 3, 13, "final", 0)


import dotenv
dotenv.load_dotenv('.env')

import os
print(os.environ["DATABASE"])
from pathlib import Path

# Build paths inside the project like this: BASE_DIR / 'subdir'.
BASE_DIR = Path(__file__).resolve().parent.parent


# Quick-start development settings - unsuitable for production
# See https://docs.djangoproject.com/en/3.1/howto/deployment/checklist/

# SECURITY WARNING: keep the secret key used in production secret!
SECRET_KEY = '4hor-=vd^eb(zl87f=b!#u74e3r62c#x3=_3%ag($6(!x)@-i7'

# SECURITY WARNING: don't run with debug turned on in production!
DEBUG = True

ALLOWED_HOSTS = ["*"]
REST_FRAMEWORK = {
    'DATE_FORMAT': '%d %b %y %I:%M %p'
}

# Application definition

INSTALLED_APPS = [
    'django.contrib.auth',
    'django.contrib.contenttypes',
    'django.contrib.sessions',
    'django.contrib.messages',
    'django.contrib.staticfiles',
    "django.contrib.admin",
    'rest_framework',
    'rest_framework.authtoken',
    'zappa_django_utils',
    'django_s3_sqlite',
    'django_s3_storage',
    'drf_yasg',
    'user',
    'core'
]

MIDDLEWARE = [
    'django.middleware.security.SecurityMiddleware',
    'django.contrib.sessions.middleware.SessionMiddleware',
    'django.middleware.common.CommonMiddleware',
    'django.middleware.csrf.CsrfViewMiddleware',
    'django.contrib.auth.middleware.AuthenticationMiddleware',
    'django.contrib.messages.middleware.MessageMiddleware',
    'django.middleware.clickjacking.XFrameOptionsMiddleware',
]

ROOT_URLCONF = 'gigvid_api.urls'

TEMPLATES = [
    {
        'BACKEND': 'django.template.backends.django.DjangoTemplates',
        'DIRS': [BASE_DIR / 'templates']
        ,
        'APP_DIRS': True,
        'OPTIONS': {
            'context_processors': [
                'django.template.context_processors.debug',
                'django.template.context_processors.request',
                'django.contrib.auth.context_processors.auth',
                'django.contrib.messages.context_processors.messages',
            ],
        },
    },
]

WSGI_APPLICATION = 'gigvid_api.wsgi.application'


# Database
# https://docs.djangoproject.com/en/3.1/ref/settings/#databases
print(os.environ["ENV"])
if os.environ["ENV"] == "prod":
    DATABASES = {
        'default': {
            # "ENGINE": "django_s3_sqlite",
            # "NAME": "sqlite.db",
            # "BUCKET": "gigvid-db",
            'ENGINE': 'django.db.backends.mysql',
            "HOST":os.environ['DB_HOST'],
            "NAME":os.environ["DATABASE"],
            "PASSWORD":os.environ["PASSWORD"],
            "USER":os.environ["USER"]
        }
    }
else:
    DATABASES = {
        'default': {
            "ENGINE": "django.db.backends.sqlite3",
            "NAME": os.path.join(BASE_DIR,"../","local.db")
        }
    }

AUTH_USER_MODEL = "user.User"

# Password validation
# https://docs.djangoproject.com/en/3.1/ref/settings/#auth-password-validators

AUTH_PASSWORD_VALIDATORS = [
    {
        'NAME': 'django.contrib.auth.password_validation.UserAttributeSimilarityValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.MinimumLengthValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.CommonPasswordValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.NumericPasswordValidator',
    },
]


# Internationalization
# https://docs.djangoproject.com/en/3.1/topics/i18n/

LANGUAGE_CODE = 'en-us'

TIME_ZONE =  'Asia/Kolkata'

USE_I18N = True

USE_L10N = True

USE_TZ = True

EMAIL_BACKEND = 'django.core.mail.backends.smtp.EmailBackend'
EMAIL_HOST = 'smtp.yourserver.com'
EMAIL_PORT = '<your-server-port>'
EMAIL_HOST_USER = 'your@djangoapp.com'
EMAIL_HOST_PASSWORD = 'your-email account-password'
EMAIL_USE_TLS = True
EMAIL_USE_SSL = False

# Static files (CSS, JavaScript, Images)
# https://docs.djangoproject.com/en/3.1/howto/static-files/

# STATIC_URL = '/static/'

S3_BUCKET = "gigvid-api-static"

STATICFILES_STORAGE = "django_s3_storage.storage.StaticS3Storage"

AWS_S3_BUCKET_NAME_STATIC = S3_BUCKET

STATIC_URL = "https://%s.s3.amazonaws.com/" % S3_BUCKET
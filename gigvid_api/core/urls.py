from django.urls import path, include

from . import views

urlpatterns=[
    path("",views.GigListView.as_view()),
    path("create_verify/",views.CreateGigView.as_view())
]
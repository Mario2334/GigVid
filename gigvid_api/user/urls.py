from django.urls import path, include

from user import views

urlpatterns=[
    path("",views.CreateUserView.as_view()),
    path("login/",views.LoginView.as_view()),
    path("hobby/",views.HobbyView.as_view())
]
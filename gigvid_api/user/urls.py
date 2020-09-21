from django.urls import path, include

from user import views

urlpatterns=[
    path("",views.CreateUserView.as_view()),
    path("rud_user/",views.UserView.as_view()),
    path("login/",views.LoginView.as_view()),
    path("hobby/",views.HobbyView.as_view()),
    path("bank_account/",views.BankAccountView.as_view()),
    path("initiate_payout/", views.InitiatePayoutView.as_view())
]
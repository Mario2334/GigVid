from django.urls import path, include

from . import views

urlpatterns=[
    path("",views.GigListView.as_view()),
    path("create_verify/",views.CreateGigView.as_view()),
    path("create_payment/",views.CreatePaymentLinkView.as_view()),
    path("confirm_payment/",views.ConfirmPaymentLinkView.as_view()),
    path("list_tickets/",views.ListTicketView.as_view())
]
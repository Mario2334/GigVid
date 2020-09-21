from rest_framework.authentication import TokenAuthentication
from rest_framework.generics import CreateAPIView, ListAPIView
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.views import APIView
from . import serializers
from . import models
from django.utils import timezone
import os

from .razorpay import Razorpay
from .zoom import ZoomApi
from django.contrib.auth.models import AnonymousUser

class GigListView(ListAPIView):
    serializer_class = serializers.GigSerializer
    authentication_classes = [TokenAuthentication]

    def get_queryset(self):
        if isinstance(self.request.user, AnonymousUser):
            return models.Gig.objects.filter(scheduled_time__gt=timezone.now())
        return models.Gig.objects.filter(scheduled_time__gt=timezone.now()).exclude(user=self.request.user)


class CreateGigView(CreateAPIView):
    serializer_class = serializers.GigSerializer
    permission_classes = [IsAuthenticated]
    authentication_classes = [TokenAuthentication]

    def post(self, request, *args, **kwargs):
        serializer = self.serializer_class(data=self.request.data)
        if "user" not in serializer.initial_data:
            serializer.initial_data["user"] = request.user.id
        if serializer.is_valid():
            gig = serializer.save()
            params = {
                "topic": gig.name,
                "timezone": "Asia/Calcutta",
                "type": 2,
                "start_time": gig.scheduled_time.strftime("%Y-%m-%d T %H:%M:%S"),
                "duration": gig.duration,
                "settings": {
                    "in_meeting": True,
                    "join_before_host": False,
                    "mute_upon_entry": True,
                    "approval_type": 1,
                    "waiting_room": True,
                    "contact_name": gig.user.first_name,
                    "contact_email": gig.user.email
                }
            }
            response = ZoomApi().create(params)
            gig.host_url = response["start_url"]
            gig.join_url = response["join_url"]
            gig.save()
            print(gig)
            return Response(status=201, data={"message": "success"})
        else:
            return Response(status=400, data={"message": serializer.errors})


class CreatePaymentLinkView(APIView):
    permission_classes = [IsAuthenticated]
    authentication_classes = [TokenAuthentication]

    def post(self, request):
        payment_serializer = serializers.IssuePaymentSerializer(data=request.data)
        if payment_serializer.is_valid():
            gig = models.Gig.objects.get(id=payment_serializer.validated_data["gig"]["id"])
            gst_amount = round((int(os.environ.get("GST")) * gig.price) / 100)
            total_amount = gst_amount + gig.price
            params = {
                # "amount": str(total_amount),
                "type": "link",
                "currency": "INR",
                "line_items": [{
                    # "item_id": gig.id,
                    "name": gig.name,
                    "description": gig.description,
                    "quantity": 1,
                    "amount": total_amount * 100,
                }],
                "description": f"Payment for Gig no {gig.id} by user {request.user.id}",
            }

            response = Razorpay().create_payment_link(params)
            order_params = {
                "payment_link_id": response["id"],
                "final_price": total_amount
            }
            order_serializer = serializers.OrderSerializer(data=order_params)
            order_serializer.is_valid(raise_exception=True)
            order = order_serializer.save()
            return Response(data={
                "link": response["short_url"],
                "order_id": order.id
            }, status=200)
        else:
            return Response({"message": payment_serializer.errors}, status=400)


class ConfirmPaymentLinkView(APIView):
    permission_classes = [IsAuthenticated]
    authentication_classes = [TokenAuthentication]

    def post(self, request):
        payment_serializer = serializers.ConfirmPaymentSerializer(data=request.data)
        if payment_serializer.is_valid():
            order = models.Order.objects.get(id=payment_serializer.validated_data["order"]["id"])
            resp = Razorpay().get_payment(order.payment_link_id)
            if resp["status"] == "paid":
                ser_data = {
                    "gig": payment_serializer.validated_data["gig"]["id"],
                    "order": payment_serializer.validated_data["order"]["id"],
                    "user": request.user.id
                }
                ticket_serializer = serializers.TicketSerializer(data=ser_data)
                ticket_serializer.is_valid(raise_exception=True)
                ticket = ticket_serializer.save()
                ticket.gig.user.bankaccount.balance = ticket.gig.user.bankaccount.balance + ticket.gig.price
                ticket.gig.user.bankaccount.save()
                return Response(ticket_serializer.data)
            else:
                return Response(data={"message": "Payment Failed"}, status=503)
        else:
            return Response({"message": payment_serializer.errors}, status=400)


class MyGigsView(ListAPIView):
    permission_classes = [IsAuthenticated]
    authentication_classes = [TokenAuthentication]
    serializer_class = serializers.GigSerializer

    def get_queryset(self):
        return models.Gig.objects.filter(user=self.request.user)


class ListTicketView(ListAPIView):
    permission_classes = [IsAuthenticated]
    authentication_classes = [TokenAuthentication]
    serializer_class = serializers.TicketSerializer

    def get_queryset(self):
        return models.Ticket.objects.filter(user=self.request.user)

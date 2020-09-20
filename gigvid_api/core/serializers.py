from rest_framework import serializers
from . import models


class GigSerializer(serializers.ModelSerializer):
    url = serializers.URLField(read_only=True)
    is_active = serializers.BooleanField(read_only=True)
    scheduled_time = serializers.DateTimeField(format="%d %b %y at %I:%M %p")

    class Meta:
        model = models.Gig
        fields = "__all__"


class IssuePaymentSerializer(serializers.Serializer):
    gig = serializers.IntegerField(source="gig.id")


class ConfirmPaymentSerializer(serializers.Serializer):
    gig = serializers.IntegerField(source="gig.id")
    order_id = serializers.CharField(source="order.id")


class TicketSerializer(serializers.ModelSerializer):
    id = serializers.IntegerField(read_only=True)
    created_at = serializers.DateTimeField(read_only=True,format="%d %b %y at %I:%M %p")
    gig = GigSerializer(read_only=True)

    class Meta:
        model = models.Ticket
        fields = ["gig","user","order","id","created_at"]


class OrderSerializer(serializers.ModelSerializer):
    class Meta:
        model = models.Order
        fields = "__all__"

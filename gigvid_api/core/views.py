from drf_yasg import openapi
from drf_yasg.utils import swagger_auto_schema
from rest_framework.authentication import TokenAuthentication
from rest_framework.generics import CreateAPIView, ListAPIView
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response

from . import serializers
from . import models
from django.utils import timezone

from .zoom import ZoomApi


class GigListView(ListAPIView):
    serializer_class = serializers.GigSerializer
    queryset = models.Gig.objects.filter(scheduled_time__gt=timezone.now())


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
            return Response(status=400, data=serializer.errors)

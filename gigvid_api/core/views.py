from drf_yasg import openapi
from drf_yasg.utils import swagger_auto_schema
from rest_framework.authentication import TokenAuthentication
from rest_framework.generics import CreateAPIView, ListAPIView
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response

from . import serializers
from . import models
from django.utils import timezone


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
            user = serializer.save()
            return Response(self.serializer_class(user).data)
        else:
            return Response(status=400,data=serializer.errors)

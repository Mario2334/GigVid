from rest_framework import serializers
from . import models


class GigSerializer(serializers.ModelSerializer):
    url = serializers.URLField(read_only=True)
    is_active = serializers.BooleanField(read_only=True)
    class Meta:
        model = models.Gig
        fields = "__all__"

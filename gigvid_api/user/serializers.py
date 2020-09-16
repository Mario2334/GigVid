# import bcrypt
from rest_framework import serializers

from user.models import User


class UserSerializer(serializers.ModelSerializer):
    password = serializers.CharField(write_only=True)

    class Meta:
        model = User
        fields = "__all__"

    def create(self, validated_data):
        # salt = bcrypt.gensalt()
        # validated_data["password"] = bcrypt.hashpw(str.encode(validated_data["password"]), salt)
        user = User.objects.create_user(**validated_data)
        return user


class LoginSerializer(serializers.Serializer):
    password = serializers.CharField(required=True)
    username = serializers.CharField(required=True)
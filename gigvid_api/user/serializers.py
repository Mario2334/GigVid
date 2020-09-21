# import bcrypt
from rest_framework import serializers

from user.models import User, Hobby, BankAccount


class UserSerializer(serializers.ModelSerializer):
    password = serializers.CharField(write_only=True)
    first_name = serializers.CharField(required=True)

    class Meta:
        model = User
        fields = ["username", "first_name", "last_name", "email", "hobby", "password"]

    def create(self, validated_data):
        # salt = bcrypt.gensalt()
        # validated_data["password"] = bcrypt.hashpw(str.encode(validated_data["password"]), salt)
        user = User.objects.create_user(**validated_data)
        return user


class RUDUserSerializer(serializers.ModelSerializer):
    hobby = serializers.CharField(source="hobby.name")

    class Meta:
        model = User
        fields = ["username", "first_name", "last_name", "email", "hobby"]


class LoginSerializer(serializers.Serializer):
    password = serializers.CharField(required=True)
    username = serializers.CharField(required=True)


class BankAccountSerializer(serializers.ModelSerializer):
    contact_id = serializers.CharField(max_length=300, required=False)

    class Meta:
        model = BankAccount
        fields = "__all__"


class HobbySerializer(serializers.ModelSerializer):
    class Meta:
        model = Hobby
        fields = ["id", "name"]

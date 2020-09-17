from rest_framework.authtoken.models import Token
from rest_framework.response import Response

from . import serializers
from rest_framework.generics import ListCreateAPIView, ListAPIView
from rest_framework.views import APIView
# Create your views here.
from . import models


class CreateUserView(ListCreateAPIView):
    serializer_class = serializers.UserSerializer

    def get_queryset(self):
        return models.User.objects.filter()


class LoginView(APIView):
    def post(self, request):
        serializer = serializers.LoginSerializer(data=request.data)
        if serializer.is_valid(raise_exception=True):

            try:
                user = models.User.objects.get(username=serializer.validated_data["username"])
                if user.check_password(serializer.validated_data["password"]):
                    token = Token.objects.get_or_create(user=user)
                    return Response(status=200, data={"token": token[0].key})

                return Response(status=400, data={"message": "Username and Password is incorrect"})
            except models.User.DoesNotExist as e:
                return Response(status=400, data={"message": "Username and Password is incorrect"})

            except Exception as e:
                return Response(status=500, data={"message": e})


class HobbyView(ListAPIView):
    serializer_class = serializers.HobbySerializer
    queryset = models.Hobby.objects.filter()

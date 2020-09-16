from rest_framework.authtoken.models import Token
from rest_framework.response import Response

from . import serializers
from rest_framework.generics import ListCreateAPIView
from rest_framework.views import APIView
# Create your views here.
from .models import User

class CreateUserView(ListCreateAPIView):
    serializer_class = serializers.UserSerializer

    def get_queryset(self):
        return User.objects.filter()

class LoginView(APIView):
    def post(self,request):
        serializer = serializers.LoginSerializer(data=request.data)
        if serializer.is_valid(raise_exception=True):

            try:
                user = User.objects.get(username=serializer.validated_data["username"])
                if user.check_password(serializer.validated_data["password"]):
                    token = Token.objects.get_or_create(user=user)
                    return Response(status=200,data={"token":token[0].key})

                return Response(status=400,data={"message":"Username and Password is incorrect"})
            except User.DoesNotExist as e:
                return Response(status=400,data={"message":"Username and Password is incorrect"})

            except Exception as e:
                return Response(status=500 , data={"message":e})


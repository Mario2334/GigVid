from rest_framework.authentication import TokenAuthentication
from rest_framework.authtoken.models import Token
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response

from core.razorpay import Razorpay
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


class BankAccountView(APIView):
    permission_classes = [IsAuthenticated]
    authentication_classes = [TokenAuthentication]

    def get(self,request):
        try:
            account = models.BankAccount.objects.get(user=request.user)
            account = serializers.BankAccountSerializer(account)
            return Response(account.data)
        except models.BankAccount.DoesNotExist as e:
            return Response({"message":"Not Found"} , status=404)

    def post(self, request, *args, **kwargs):
        serializer = serializers.BankAccountSerializer(data=request.data)
        serializer.initial_data["user"] = request.user.id
        if serializer.is_valid():
            contact_params = {
                "name":request.user.first_name,
                "email":request.user.email,
                "type":"customer",
                "reference_id":f"User ID {request.user.id}",
            }
            razorpay = Razorpay()
            resp = razorpay.create_contact(contact_params)
            serializer.validated_data["contact_id"] = resp["id"]
            fund_account_params = {
                "contact_id":serializer.validated_data["contact_id"],
                "account_type":"bank_account",
                "bank_account":{
                    "name": serializer.validated_data["name"],
                    "ifsc":serializer.validated_data["ifsc"],
                    "account_number":serializer.validated_data["account_no"]
                }
            }
            razorpay_fund = razorpay.create_fund_account(fund_account_params)
            serializer.validated_data["fund_account_id"] = razorpay_fund["id"]
            account = serializer.save()
            return Response(serializers.BankAccountSerializer(account).data)

        else:
            return Response({"message":serializer.errors},status=400)
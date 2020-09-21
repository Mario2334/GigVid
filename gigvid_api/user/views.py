from rest_framework.authentication import TokenAuthentication
from rest_framework.authtoken.models import Token
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response

from core.razorpay import Razorpay
from . import serializers
from rest_framework.generics import ListCreateAPIView, ListAPIView, RetrieveUpdateDestroyAPIView
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


class UserView(RetrieveUpdateDestroyAPIView):
    serializer_class = serializers.RUDUserSerializer
    permission_classes = [IsAuthenticated]
    authentication_classes = [TokenAuthentication]

    def get_object(self):
        return self.request.user


class HobbyView(ListAPIView):
    serializer_class = serializers.HobbySerializer
    queryset = models.Hobby.objects.filter()


class BankAccountView(APIView):
    permission_classes = [IsAuthenticated]
    authentication_classes = [TokenAuthentication]

    def get(self, request):
        try:
            account = models.BankAccount.objects.get(user=request.user)
            account = serializers.BankAccountSerializer(account)
            return Response(account.data)
        except models.BankAccount.DoesNotExist as e:
            return Response({"message": "Not Found"}, status=404)

    def post(self, request, *args, **kwargs):
        serializer = serializers.BankAccountSerializer(data=request.data)
        serializer.initial_data["user"] = request.user.id
        if serializer.is_valid():
            contact_params = {
                "name": request.user.first_name,
                "email": request.user.email,
                "type": "customer",
                "reference_id": f"User ID {request.user.id}",
            }
            razorpay = Razorpay()
            resp = razorpay.create_contact(contact_params)
            serializer.validated_data["contact_id"] = resp["id"]
            fund_account_params = {
                "contact_id": serializer.validated_data["contact_id"],
                "account_type": "bank_account",
                "bank_account": {
                    "name": serializer.validated_data["name"],
                    "ifsc": serializer.validated_data["ifsc"],
                    "account_number": serializer.validated_data["account_no"]
                }
            }
            razorpay_fund = razorpay.create_fund_account(fund_account_params)
            serializer.validated_data["fund_account_id"] = razorpay_fund["id"]
            account = serializer.save()
            return Response(serializers.BankAccountSerializer(account).data)

        else:
            return Response({"message": serializer.errors}, status=400)


class InitiatePayoutView(APIView):
    permission_classes = [IsAuthenticated]
    authentication_classes = [TokenAuthentication]

    def post(self,request):
        bank_account = request.user.bankaccount
        if bank_account.balance > 10:
            params = {
                "account_number":"2323230051699679",
                "fund_account_id":bank_account.fund_account_id,
                "amount" : bank_account.balance*100,
                "currency": "INR",
                "mode": "IMPS",
                "purpose": "refund",
                "queue_if_low_balance": True,
                "reference_id": "Acme Transaction ID 12345",
            }
            response = Razorpay().initiate_payout(params)
            bank_account.balance = 0
            bank_account.save()
            return Response(status=200 , data={"message":"Payout Successful"})
        else:
            return Response(status=400,data={"message":"Balance more than 10 rs needed"} )
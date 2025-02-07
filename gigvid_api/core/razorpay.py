import requests
import base64
import json

from core.exceptions import BadRequest


class Razorpay():
    def __init__(self):
        self.api_key = "rzp_test_2YOMyazqXuU6zk"
        self.secret_key = "UVvR54yimi5bJpbbQ15BZgXP"
        token = f"{self.api_key}:{self.secret_key}".encode()
        token = base64.b64encode(token).decode()
        self.headers = {"Authorization":f"Basic {token}", "Content-Type": "application/json"}
        self.base_url = "https://api.razorpay.com/v1/"

    def create_contact(self,params):
        url = self.base_url + "contacts"
        resp = requests.post(url , json=params,headers = self.headers)
        if resp.status_code == 201 or resp.status_code == 200:
            return resp.json()

        else:
            raise Exception(f"Status Code:{resp.status_code}\n{resp.content}")

    def create_fund_account(self,params):
        url = self.base_url + "fund_accounts"
        resp = requests.post(url, json=params, headers=self.headers)
        if resp.status_code == 201 or resp.status_code == 200:
            return resp.json()
        else:
            raise Exception(f"{resp.content}")

    def create_payment_link(self,params):
        url = self.base_url + "invoices/"
        resp = requests.post(url,data=json.dumps(params),headers = self.headers)
        if resp.status_code == 201 or resp.status_code == 200:
            return resp.json()
        else:
            raise Exception(f"{resp.content}")

    def get_payment(self , id):
        url = self.base_url + f"payments/{id}"
        resp = requests.get(url,headers=self.headers)
        if resp.status_code == 200:
            return resp.json()
        elif resp.status_code == 400:
            raise BadRequest("Payment Not Found")
        else:
            raise Exception("${resp.content}")

    def initiate_payout(self,params):
        url = self.base_url + "payouts"
        resp = requests.post(url, data=json.dumps(params), headers=self.headers)
        if resp.status_code == 201 or resp.status_code == 200:
            return resp.json()
        else:
            raise Exception(f"{resp.content}")
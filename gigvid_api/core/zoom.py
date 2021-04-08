import requests
import json


class ZoomApi():
    def __init__(self):
        self.jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOm51bGwsImlzcyI6IlFjR0luX2xkUmxDd1NDWDEyQVUyR1EiLCJleHAiOjE2MTk4NTA2MDAsImlhdCI6MTYxNzg4NTM1Mn0.a3DPhWac6cDL_LIJtLxL-rrWCPp9qeJB_HMxXncWxow"
        self.headers = {"Authorization": f"Bearer {self.jwt}", "Content-Type": "application/json"}
        self.base = "https://api.zoom.us/v2/"

    def create(self, params):
        url = self.base + "users/eLE0ofBiTBuuh5h3TVGVxQ/meetings"
        resp = requests.post(url, json=params, headers=self.headers)
        if resp.status_code == 201:
            return resp.json()
        else:
            raise Exception(f"{resp.content}")

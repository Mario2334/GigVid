import requests
import json


class ZoomApi():
    def __init__(self):
        self.jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOm51bGwsImlzcyI6IlFjR0luX2xkUmxDd1NDWDEyQVUyR1EiLCJleHAiOjE2MDEwNzE2NzAsImlhdCI6MTYwMDQ2Njg3MX0.2oi-y7bP-DWZgVYBs3vrXilMjHXLw-Neprw9SeNW4ss"
        self.headers = {"Authorization": f"Bearer {self.jwt}", "Content-Type": "application/json"}
        self.base = "https://api.zoom.us/v2/"

    def create(self, params):
        url = self.base + "users/eLE0ofBiTBuuh5h3TVGVxQ/meetings"
        resp = requests.post(url, json=params, headers=self.headers)
        if resp.status_code == 201:
            return resp.json()
        else:
            raise Exception(f"{resp.content}")

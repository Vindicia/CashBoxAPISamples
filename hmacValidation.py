import hashlib
import base64
import hmac

class Authoriser:

    hmac_key = ''
    signature = ''
    debug = True

    def __init__(self, request_signature, hmac_key, debug=False):
        self.signature = bytes(request_signature).encode("utf-8")
        self.hmac_key = bytes(hmac_key).encode("utf-8")
        self.debug = debug

    def validate(self, body):
        body = bytes(str(body).encode("utf-8"))
        body_hash = base64.b64encode(hmac.new(self.hmac_key, body, hashlib.sha256).digest())
        if self.debug == True:
            print(body_hash)

        if body_hash == self.signature:
            return True

        return False
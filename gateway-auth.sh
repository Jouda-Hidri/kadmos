# creates consumer bob with credentials user:pass

curl -X POST http://localhost:8001/plugins/ \
  --data "name=basic-auth" \
  --data "config.hide_credentials=true"

curl -i -X POST \
  --url http://localhost:8001/consumers/ \
  --data "username=bob"

curl -X POST http://localhost:8001/consumers/bob/basic-auth \
  --data "username=user" \
  --data "password=pass"
# dXNlcjpwYXNz

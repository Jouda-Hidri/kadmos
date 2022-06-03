# setup saving account a
curl -i -X POST \
  --url http://localhost:8001/services/ \
  --data 'name=accounta' \
  --data 'url=http://accounta:8081'

curl -i -X POST \
  --url http://localhost:8001/services/accounta/routes \
  --data 'paths[]=/saving/a'

# setup saving account b
curl -i -X POST \
  --url http://localhost:8001/services/ \
  --data 'name=accountb' \
  --data 'url=http://accountb:8082'

curl -i -X POST \
  --url http://localhost:8001/services/accountb/routes \
  --data 'paths[]=/saving/b'

# set timeout to 5 seconds
curl -i -X PATCH 'http://localhost:8001/services/accounta' \
  --header 'Content-Type: application/json' \
  --data '{"read_timeout": 5000}'

curl -i -X PATCH 'http://localhost:8001/services/accountb' \
  --header 'Content-Type: application/json' \
  --data '{"read_timeout": 5000}'

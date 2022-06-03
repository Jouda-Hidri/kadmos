# Saving account

## Setup

Make sure you have `Docker`, `Maven 3.8` and `jdk 11` installed.

To run this project after cloning the repository:
* `mvn clean package`
* `docker-compose up --build`

When Docker containers are up and running, set up the API Gateway:
* `sh gateway-setup.sh`

To secure the API Gateway (credentials username: `user` & password: `pass`):
* `sh gateway-auth.sh`

To generate log file:
* `docker logs kadmos_kong_1 > info.log`

## API usage
Fetch saving-account-a current balance:
```
curl -i -X GET 'http://localhost:8080/saving/a/balance' \
-H 'Authorization: Basic dXNlcjpwYXNz'
```

Fetch saving-account-b current balance:
```
curl -i -X GET 'http://localhost:8080/saving/b/balance' \
-H 'Authorization: Basic dXNlcjpwYXNz'
```

Update saving-account-a balance:
```
curl -i -X PUT 'http://localhost:8080/saving/a/balance' \
-H 'Authorization: Basic dXNlcjpwYXNz' \
-H 'Content-Type: application/json' \
-d '{"amount": 1000 }' 
```

Update saving-account-b balance:
```
curl -i -X PUT 'http://localhost:8080/saving/b/balance' \
-H 'Authorization: Basic dXNlcjpwYXNz' \
-H 'Content-Type: application/json' \
-d '{"amount": 500 }' 
```

## Choice of technologies

Apart from the basic requirements like Docker and Spring Boot, I decided to use Kong as the API Gateway. 
Kong is based on top of Ingress NGINX and is very straightforward to set up.

It provides the properties `proxy_access_log` and `read_timeout` for logging and timeout configuration out of the box.

Basic Auth is also provided as a plugin.

## Design decisions

`saving-account-a` and `saving-account-b` are two instances of the same service, running with two different configurations. With the help of `Spring Profiles`, each container is connected to its own Postgres Database.

As a Database schema, I created an `accounts` table with two columns `ID` and `balance`. For the purpose of this exercise, I decided
to hardcode the `ACCOUNT_ID` as long as we're not creating any new accounts. However, we could easily adjust the code to handle many accounts per service in a real world use-case.

In the diagrams of the exercise `POST` is used to increase/decrease the balance. In my solution I've used `PUT` as I consider it to be a better 
fit for updates of the balance. `POST` would make sense semantically in the case where we want to keep a history of the balance and e.g. create
a new row for every transaction, but this was not indicated by the exercise.

Regarding my testing strategy, I chose to do one integration test covering all layers from API to persistence.
I believe there was no need for a unit test given there was no business logic that needed to be tested.

For the purposes of this exercise, I decided to use Basic Auth to secure the Gateway. Kong offers easy integration with different authentication tools. In a real life scenario I would use OAuth 2. 

* **Testing the timeout**:
In order to test the timeout, I reduced `read_timeout` to 1ms, and I used the Chrome developer tools to set the network to slow (`Chrome -> Developer Tool -> Network -> Slow`).
Doing a `GET` requests generated a timeout error in this case. Another alternative, is to keep the timeout to 5s and use a proxy to simulate
a slow internet connection (e.g. `Fiddler`).
* **Scaling the Gateway**:
To scale the Gateway we could add more nodes to the cluster. If the database is scaled then the data would be re-balanced.
* **Monitoring the Gateway**:
To monitor the Gateway I would use a service like Prometheus, which can be easily integrated with Kong. Prometheus allows you to
define KPIs for your service. Then, we can use a tool like Grafana which would enable us to visualize these metrics. Prometheus also allows you to define alerts which you could integrate with OpsGenie or PagerDuty in case of an emergency.

# storeapi-customer
Customer microservice till StoreAPI.

För att kunna köra containern måste variabler för MYSQL_HOST, DB_DATABASE, DB_USER och DB_PASSWORD anges i kommandot efter docker run.

KOMMANDON

NETWORK:

docker network create storenetwork

DB:

docker run --name customer-db -p 3306:3306 -d -e MYSQL_DATABASE=customerdb -e MYSQL_USER=user -e MYSQL_PASSWORD=password -e MYSQL_ROOT_PASSWORD=rpwd --mount type=bind,source="${PWD}"/sql,target=/docker-entrypoint-initdb.d --network=storenetwork mysql

APP:

docker build --no-cache --build-arg DB_PASSWORD=$(cat .env | grep DB_PASSWORD | cut -d '=' -f2) -t customer-image .

docker run -d --name customer-app -p 8081:8081 --network=storenetwork -e MYSQL_ROOT_PASSWORD=rpwd -e MYSQL_HOST=customer-db -e DB_DATABASE=customerdb -e DB_USER=user -e DB_PASSWORD=password customer-image

COMPOSE:

docker-compose up -d

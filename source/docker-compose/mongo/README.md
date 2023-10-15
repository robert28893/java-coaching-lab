<!-- TOC -->
* [Start mongodb](#start-mongodb)
* [Stop mongodb](#stop-mongodb)
* [Connect to mongodb](#connect-to-mongodb)
  * [Option 1: Sử dụng mongodb shell](#option-1-sử-dụng-mongodb-shell)
  * [Option 2: Sử dụng mongo compass](#option-2-sử-dụng-mongo-compass)
* [Tham khảo](#tham-khảo)
<!-- TOC -->

# Start mongodb

```bash
docker compose up -d
```

Lệnh này sẽ start mongodb

Connect tới mongodb sử dụng port là `27017`, `username` root và `password` là giá trị của
biến `MONGO_INITDB_ROOT_PASSWORD` trong file `docker-compose.yml`

# Stop mongodb

```bash
docker compose stop
```

# Connect to mongodb

## Option 1: Sử dụng mongodb shell

Exec vào trong container của mongodb

```shell
docker exec -ti mongo-mongodb-1 sh
```

Run mongosh

```shell
mongosh --port 27017 --username root --authenticationDatabase admin
```

Test command

```shel
db.runCommand(
   {
      hello: 1
   }
)
```

## Option 2: Sử dụng mongo compass

Xem hướng dẫn tại link sau [link](https://www.mongodb.com/try/download/compass)

# Tham khảo

- [https://www.mongodb.com/compatibility/docker](https://www.mongodb.com/compatibility/docker)
- [https://www.mongodb.com/docs/mongodb-shell/](https://www.mongodb.com/docs/mongodb-shell/)

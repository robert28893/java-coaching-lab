<!-- TOC -->
* [Start redis](#start-redis)
* [Stop redis](#stop-redis)
* [Một số lệnh cơ bản của redis](#một-số-lệnh-cơ-bản-của-redis)
  * [1. Exec vào trong redis container:](#1-exec-vào-trong-redis-container)
  * [2. Run `redis-cli`](#2-run-redis-cli)
  * [3. List các keys](#3-list-các-keys)
  * [4. Lệnh SET](#4-lệnh-set)
  * [5. Lệnh GET](#5-lệnh-get)
  * [6. Lệnh DEL](#6-lệnh-del)
<!-- TOC -->

# Start redis

```bash
docker compose up -d
```

# Stop redis

```bash
docker compose stop
```

# Một số lệnh cơ bản của redis

## 1. Exec vào trong redis container:

```bash
docker compose exec -ti redis sh # `redis` là tên service trong docker-compose 
```

## 2. Run `redis-cli`

```bash
redis-cli -a `PASS_WORD` # PASSWORD được set trong biến môi trường `REDIS_PASSWORD` của `docker-compose.yml`
```

## 3. List các keys

```bash
keys *
```

[Tham khảo](https://redis.io/commands/keys/)

## 4. Lệnh SET

```sh
set foo bar
```

List các keys để xem kết quả:

```sh
keys *
```

[Tham khảo](https://redis.io/commands/set/)

## 5. Lệnh GET

```sh
get foo
```

[Tham khảo](https://redis.io/commands/get/)

## 6. Lệnh DEL

```sh
del foo
```

List các keys để xem kết quả:

```sh
keys *
```

[Tham khảo](https://redis.io/commands/del/)
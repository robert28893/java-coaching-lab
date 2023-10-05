# Sample Project

Đây là project mẫu

## Link swagger doc

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Link prometheus metrics

[http://localhost:8080/actuator/prometheus](http://localhost:8080/actuator/prometheus)

## Link console prometheus

[http://localhost:9090/](http://localhost:9090/)

## Link console grafana

[http://localhost:3000/](http://localhost:3000/)

## Generate rsa key

Sử dụng lệnh sau

```sh
openssl genrsa -out private.pem 2048
openssl rsa -in private.pem -outform PEM -pubout -out public.pem
```

Hoặc dùng tool online

[https://mkjwk.org/](https://mkjwk.org/)

## Build docker image and run

**Build**

```shell
docker build -t sample-project:latest .
```

**Run**

```shell
docker run --network=host -p 8080:8080 sample-project:latest
```

## Link các cấu hình của Spring

[Spring Application Properties](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html)

# Part 9

<!-- TOC -->
* [Part 9](#part-9)
  * [Overview](#overview)
  * [Mongodb](#mongodb)
    * [Cài đặt `Mongodb`](#cài-đặt-mongodb)
    * [Kết nối `Spring` với `Mongodb`](#kết-nối-spring-với-mongodb)
    * [Lưu log request, response của API và trong `mongodb`](#lưu-log-request-response-của-api-và-trong-mongodb)
  * [Docker: build and run](#docker-build-and-run)
    * [Dockerfile and build image](#dockerfile-and-build-image)
    * [Run image](#run-image)
      * [Sử dụng command line](#sử-dụng-command-line)
      * [Sử dụng docker compose file](#sử-dụng-docker-compose-file)
  * [Tham khảo](#tham-khảo)
<!-- TOC -->

## Overview

Trong phần này bạn sẽ tìm hiểu cách thức kết nối `spring` với `mongodb` và thực hành với việc lưu log request, response
của api vào trong `mongodb`.

Tiếp theo, cùng tìm hiểu việc đóng gói chương trình thành image docker và chạy chương trình của bạn như một container
trong `docker`.

## Mongodb

### Cài đặt `Mongodb`

Bạn thực hiện việc cài đặt Mongodb server và Mongo compass theo hướng dẫn trong thư mục sau
[docker-compose/mongodb](../../source/docker-compose/mongo).

Tiếp theo bạn thêm mới 1 database tên là `sample_db` và tạo một user trên database này với các tham số sau
`user: 'user', pwd: 'User123', role: 'dbOwner'`

### Kết nối `Spring` với `Mongodb`

Thêm dependencies cho module spring-data-mongodb

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

Bổ sung cấu hình trong file `application.yml`

```yml
spring:
  data:
    mongodb:
      host: "localhost"
      port: 27017
      username: "user" # use have to create this user
      password: "User123"
      database: "sample_db"
```

Tìm hiểu về cách làm việc với `spring-data-mongo`:

- [spring-data-mongodb-tutorial](https://www.baeldung.com/spring-data-mongodb-tutorial)

### Lưu log request, response của API và trong `mongodb`

Tìm hiểu cách logging request, response trong `Spring` thông qua hướng dẫn sau:

- [Logging request and response](https://frandorado.github.io/spring/2018/11/15/log-request-response-with-body-spring.html)

Tiếp theo bạn cần định nghĩa document trong mongodb và lưu request, response vào trong mongodb.

Tham khảo:

- [Sample Project](../../source/sample-project)

## Docker: build and run

### Dockerfile and build image

### Run image

#### Sử dụng command line

#### Sử dụng docker compose file

## Tham khảo
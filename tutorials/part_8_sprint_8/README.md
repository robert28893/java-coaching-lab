# Sprint 8

<!-- TOC -->
* [Sprint 8](#sprint-8)
  * [Overview](#overview)
  * [Prometheus](#prometheus)
  * [Grafana](#grafana)
  * [Spring Actuator](#spring-actuator)
<!-- TOC -->

## Overview

Trong sprint này, bạn sẽ thêm `monitoring` cho dự án của bạn. Các tham số monitoring gồm các thông số như memory, cpu,
threads, received requests, ...

Công cụ sử dụng bao gồm: `prometheus`, `grafana` và module `spring-actuator`

## Prometheus

Thực hiện việc start prometheus bằng docker compose theo hướng dẫn tại thư mục sau
[docker-compose/prometheus](../../source/docker-compose/prometheus)

Trong đó lưu ý file config `prometheus.yml` có nội dung như sau

```yml
scrape_configs:
  - job_name: 'Spring Boot Application'
    metrics_path: '/actuator/prometheus'
    scrape_interval: 15s
    static_configs:
      - targets: [ 'host.docker.internal:8080' ]
        labels:
          application: 'My Spring Boot Application'
```

Trường `targets` trỏ vào địa chỉ service của bạn.

Sau khi start prometheus, bạn vào giao diện console được kết quả như sau:

![](img/prometheus.png)

## Grafana

Với `grafana`, các bạn làm theo hướng dẫn trong thư mục [docker-compose/grafana](../../source/docker-compose/grafana)

Sau đó, tại trang console của `grafana` bạn thêm data source là prometheus như sau:

![](img/grafana.png)

Tham khảo:

[Grafana Dashboards for Spring](https://grafana.com/grafana/dashboards/?search=spring)

## Spring Actuator

Trong phần này bạn sẽ sử dụng module `spring-actuator` để monitor ứng dụng của bạn. Spring Actuator cung cấp các thông
số về auditing, health và metrics.

Công việc của bạn bao gồm:

- [ ] Thêm dendencies cho module `spring-actuator` và `micrometer-prometheus`

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

- [ ] Cấu hình expose endpoints trong file `application.yml`

```yml
management:
  endpoints:
    web:
      exposure:
        include: '*'
```

- [ ] Start lại project và truy cập vào link sau để xem các metrics được expose cho prometheus

[http://localhost:8080/actuator/prometheus](http://localhost:8080/actuator/prometheus)

- [ ] Tiếp theo bạn vào giao diện console của `grafana` và thêm dashboard grafana cho spring. Kết quả thu được như sau:

![](img/grafana_dashboard_1.png)

![](img/grafana_dashboard_2.png)

Tham khảo:

- [https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
- [https://www.baeldung.com/spring-boot-actuators](https://www.baeldung.com/spring-boot-actuators)
- [https://www.masterspringboot.com/spring-boot-actuator/monitoring-spring-boot-with-micrometer-and-prometheus/](https://www.masterspringboot.com/spring-boot-actuator/monitoring-spring-boot-with-micrometer-and-prometheus/)
- [Sample Project](../../source/sample-project)

## Sentry

Sentry là một nền tảng theo dõi error, exception nhằm giúp các developers giám sát ứng dụng. Trong phần này bạn sẽ cài 
đặt sentry và kết nối với spring để theo dõi và giám sát các exception.

### 1. Cài đặt sentry

Download sentry source tại [link](https://github.com/getsentry/self-hosted/archive/refs/tags/23.9.1.tar.gz)

Thực hiện giải nén và cài đặt:

```shell
tar xvfz self-hosted-23.9.1.tar.gz
cd self-hosted-23.9.1
./install.sh
```
Lưu ý cài đặt `username/password` và ghi nhớ 2 thông tin này.

Sau khi cài đặt xong, bạn chạy lệnh sau để start sentry

```shell
docker compose up -d
```

Truy cập vào địa chỉ sau:

Sử dụng `username/password` đã set ở bước trên để đăng nhập

[http://127.0.0.1:9000/](http://127.0.0.1:9000/)

### 2. Kết nối spring vói sentry


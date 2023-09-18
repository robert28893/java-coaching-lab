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
threads,
received requests, ...

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

Với `grafana`, các bạn làm theo hướng dẫn trong thư mực [docker-compose/grafana](../../source/docker-compose/grafana)

Sau đó, tại trang console của `grafana` bạn thêm data source là prometheus như sau:

![](img/grafana.png)

## Spring Actuator


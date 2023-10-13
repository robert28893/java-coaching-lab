# Requirements of project

<!-- TOC -->
* [Requirements of project](#requirements-of-project)
  * [Overview](#overview)
  * [Bài toán](#bài-toán)
  * [Mô tả](#mô-tả)
  * [Yêu cầu chung](#yêu-cầu-chung)
    * [1. Quy trình làm việc](#1-quy-trình-làm-việc)
    * [2. Response của API](#2-response-của-api)
    * [3. Source code](#3-source-code)
  * [Objectives](#objectives)
<!-- TOC -->

## Overview

Trong bài hướng dẫn này chúng ta sẽ làm một project về lĩnh vực tuyển dụng.
Yêu cầu đặt ra là thiết kế và triển khai các API cho hệ thống.

## Bài toán

Đầu vào:

- Database, tables: Chúng ta sẽ làm việc trên một database với các bảng có sẵn và đã có một ít dữ liệu sẵn có.
- Mô tả API: Các mô tả cho từng yêu cầu ở dạng non-technical

Đầu ra:

- Thiết kế API
- API implementation
- API docs
- Linter
- Monitoring service

## Mô tả

Hệ thống bao gồm các entities (có thể hiểu là các bảng) và quan hệ như sau:

```mermaid
---
title: Entity Relationship Diagram
---
erDiagram
    EMPLOYER 1--0+ JOB: post
    JOB 0+--1+ JOB_FIELD: in
    JOB 0+--1+ JOB_PROVINCE: in
    SEEKER 0+--1 JOB_PROVINCE: search-in
    SEEKER 1--0+ RESUME: has
    RESUME 0+--0+ JOB_FIELD: in
    RESUME 0+--0+ JOB_PROVINCE: search-in

    JOB {
        bigint id PK
        string name
        bigint employer_id
        string fields
        string provinces
    }

    EMPLOYER {
        bigint id PK
        string name
    }

    JOB_FIELD {
        bigint id PK
        string name
    }

    JOB_PROVINCE {
        int id PK
        string name
    }

    SEEKER {
        bigint id PK
        string name
        int province
    }

    RESUME {
        bigint id PK
        bigint seeker_id
        string title
    }
```

**EMPLOYER**

- Người đăng tuyển các công việc
- Một `employer` có thể đăng 0 hoặc nhiều `job`

**JOB**

- Thông tin về công việc
- Một job phải nằm ở 1 hoặc nhiều lĩnh vực `job_field`
- Một job khi đăng cần chỉ định rõ ở 1 hoặc nhiều tỉnh thành (khu vực) `job_province`

**SEEKER**

- Ngưới tìm việc
- Một `seeker` có thể tạo 0 hoặc nhiều `resume`
- Một `seeker` đang làm việc hoặc mong muốn tìm việc tại một `job_province`

**RESUME**

- CV xin việc
- Một `resume` phải được sở hữu bởi duy nhất một `seeker`
- Một `resume` có thể nằm ở 0 hoặc nhiều `job_field`
- Một `resume` có thể được dùng để ứng tuyển công việc tại 0 hoặc nhiều `job_province`

**JOB_FIELD**

- Thông tin về các lĩnh vực

**JOB_PROVINCE**

- Thông tin về khu vực, tỉnh thành

Các entities `job_field` và `job_province` chứa các metadata của hệ thống. Dữ liệu trong 2 bảng này đã được tạo sẵn.
Bạn sẽ không cần thao tác thêm, sửa, xóa trong 2 bảng này.

Các entities `employer`, `job`, `seeker`, `resume` liên quan chính đến nghiệp vụ. Các API của chúng ta sẽ xoay quanh các
entities này.

## Yêu cầu chung

### 1. Quy trình làm việc

- Dự án được chia thành các sprints.
- Mỗi sprint sẽ kéo dài từ 1 - 2 tuần.
- Mỗi sprint sẽ gồm nhiều tasks. Chúng ta follow theo hướng dẫn trong thư mục của từng phần tương ứng.
- Mỗi bạn cần tạo một `repository` trên github cho dự án và add mình vào `repository` này.
- Kết thúc mỗi sprint các bán tiến hành tạo merge request và merge vào nhánh `main`

### 2. Response của API

Response của API trả về dạng json và chúng ta thống nhất dùng format chung như sau:

```
{
  "errorCode": int,
  "statusCode": int,
  "message": string,
  "object": {}
}
```

| Trường     | Mô tả                      |
|------------|----------------------------|
| errorCode  | Mã lỗi                     |
| statusCode | Http status code           |
| message    | Message mô tả lỗi          |
| object     | Object chứa dữ liệu trả về |

Với dữ liệu paging thì `object` cần có format chung như sau:

```
{
  "page": int,
  "pageSize": int,
  "totalElements": long,
  "totalPages": long,
  "data": []
}
```

| Trường        | Mô tả                     |
|---------------|---------------------------|
| page          | Page index                |
| pageSize      | Số phần tử trên một page  |
| totalElements | Tổng số phần tử           |
| totalPages    | Tổng số trang             |
| data          | Dữ liệu trả về dạng array |

### 3. Source code

Với mỗi sprint, bạn cần tạo một nhánh với tên `feature/sprint-X`. Trong đó `X` là só thứ tự tương ứng với sprint làm. Ví
dụ: sprint-1, sprint-2, ...

Sau khi hoàn thành sprint, bạn tiến hành merge nhánh đã tạo vào branch `main`.

## Objectives

Những kiến thức bạn sẽ thu được sau khi hoàn thành project này:

- Spring configuration using Java Configuration and Annotations
- Testing Spring applications using JUnit 5
- Spring Data Access - JDBC, JPA and Spring Data
- Simplifying application development with Spring Boot
- Spring Boot auto-configuration, starters and properties
- Build a simple REST application using Spring Boot
- Spring Security
- Enable and extend metrics and monitoring capabilities using Spring Boot actuator
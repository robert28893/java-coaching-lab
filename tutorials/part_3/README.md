# Part 3

<!-- TOC -->
* [Part 3](#part-3)
  * [Overview](#overview)
  * [Mô tả](#mô-tả)
    * [Employer](#employer)
      * [1. Thêm mới `employer`](#1-thêm-mới-employer)
      * [2. Chỉnh sửa `employer`](#2-chỉnh-sửa-employer)
      * [3. Lấy thông tin một `employer`](#3-lấy-thông-tin-một-employer)
      * [4. Lấy danh sách `employer`](#4-lấy-danh-sách-employer)
      * [5. Xóa `employer`](#5-xóa-employer)
  * [Convention](#convention)
  * [Dummy API](#dummy-api)
  * [Khởi tạo database](#khởi-tạo-database)
  * [Xử lý logic](#xử-lý-logic)
  * [Testing](#testing)
<!-- TOC -->

## Overview

Trong phần này, bạn sẽ thiết kế bộ API cho nghiệp vụ xoay quanh enitity `employer`.

Từ mô tả non-technical bạn sẽ định nghĩa và triền khai các API: in/output, resource naming.

Sau khi đã khai báo xong api dummy, bạn sẽ thêm các layer service, repository để xử lý các logic cho API.

Cuối cùng, bạn sẽ viết unit test cho chương trình của bạn.

## Mô tả

### Employer

| Field       | Type         | Key | Comment                       |
|-------------|--------------|-----|-------------------------------|
| id          | bigint       | PRI |                               |
| email       | varchar(255) | UNI |                               |
| name        | text         |     | Tên doanh nghiệp/cá nhân      |
| province    | int          |     | Mã tỉnh thành/ khu vực        |
| description | text         |     | Mô tả về doanh nghiệp/cá nhân |
| created_at  | datetime     |     | Thời điểm tạo bản ghi         |
| updated_at  | datetime     |     | Thời điểm cập nhật bản ghi    |

- **PRI**: Primary
- **UNI**: Unique

#### 1. Thêm mới `employer`

**Input**

| Trường      | Bắt buộc | Ràng buộc                                | Mô tả                         |
|-------------|----------|------------------------------------------|-------------------------------|
| email       | x        | Độ dài không quá 255 ký tự, format email | Email                         |
| name        | x        | Độ dài không quá 255 ký tự               | Tên doanh nghiệp/cá nhân      |
| provinceId  | x        |                                          | Mã tỉnh thành/ khu vực        |
| description |          |                                          | Mô tả về doanh nghiệp/cá nhân |

**Output**

Output trả về có format như sau [response](../part_0_requirements/README.md#2-response-của-api)

Với trường hợp thành công thì `statusCode` = 201 và `object` = {}

**Description**

Nếu `email` đã tồn tại cần báo lỗi.

Nếu `provinceId` không tồn tại cần báo lỗi.

Mỗi `employer` cần có một `id` duy nhất.

Khi thêm mới cần lưu thời gian tạo mới và cập nhật vào `created_at` và `updated_at`.

#### 2. Chỉnh sửa `employer`

**Input**

| Trường      | Bắt buộc | Ràng buộc                  | Mô tả                         |
|-------------|----------|----------------------------|-------------------------------|
| id          | x        |                            | Mã id của employer            |
| name        | x        | Độ dài không quá 255 ký tự | Tên doanh nghiệp/cá nhân      |
| provinceId  | x        |                            | Mã tỉnh thành/ khu vực        |
| description |          |                            | Mô tả về doanh nghiệp/cá nhân |

**Output**

Output trả về có format như sau [response](../part_0_requirements/README.md#2-response-của-api)

Với trường hợp thành công thì `statusCode` = 200 và `object` = {}

**Description**

Nếu mã `id` không tồn tại cần báo lỗi.

Khi chỉnh sửa cần lưu lại thời điểm chỉnh sửa `updated_at`.

#### 3. Lấy thông tin một `employer`

**Input**

| Trường | Ràng buộc | Bắt buộc | Mô tả              |
|--------|-----------|----------|--------------------|
| id     |           | x        | Mã id của employer |

**Output**

Output trả về có format như sau [response](../part_0_requirements/README.md#2-response-của-api)

Với trường hợp thành công thì `statusCode` = 200 và `object` bao gồm các trường sau:

| Trường       | Mô tả                         |
|--------------|-------------------------------|
| id           | Mã id của employer            |
| email        | Email                         |
| name         | Tên doanh nghiệp/cá nhân      |
| provinceId   | Mã tỉnh thành/ khu vực        |
| provinceName | Tên tỉnh thành/ khu vực       |
| description  | Mô tả về doanh nghiệp/cá nhân |

**Description**

Nếu mã `id` không tồn tại cần báo lỗi.

#### 4. Lấy danh sách `employer`

**Input**

| Trường   | Bắt buộc | Ràng buộc                      | Mô tả                     |
|----------|----------|--------------------------------|---------------------------|
| page     | x        | page phải lớn hơn 0            | Page index                | 
| pageSize | x        | pageSize không quá 500 phần tử | Số phần tử trên một trang |   

**Output**

Output trả về có format như sau [response](../part_0_requirements/README.md#2-response-của-api)

Với trường hợp thành công thì `statusCode` = 200 và `object` gồm các thông tin về paging như
sau [paging](../part_0_requirements/README.md#2-response-của-api)

`data` trong paging là 1 mảng các phần tử bao gồm các trường sau:

| Trường       | Mô tả                    |
|--------------|--------------------------|
| id           | Mã id của employer       |
| email        | Email                    |
| name         | Tên doanh nghiệp/cá nhân |
| provinceId   | Mã tỉnh thành/ khu vực   |
| provinceName | Tên tỉnh thành/ khu vực  |

**Description**

Danh sách sắp xếp theo `name` của `employer`.

#### 5. Xóa `employer`

**Input**

| Trường | Bắt buộc | Ràng buộc | Mô tả           |
|--------|----------|-----------|-----------------|
| id     | x        |           | Id của employer |

**Output**

Output trả về có format như sau [response](../part_0_requirements/README.md#2-response-của-api)

Với trường hợp thành công thì `statusCode` = 200 và `object` = {}

**Description**

Nếu `id` không tồn tại thì báo lỗi.

## Convention

Trước khi bắt tay vào coding, chúng ta thống nhất các conventions sau để đồng nhất trong quá trình phát triển dự án.

- Các class `controller` cần đặt trong package `vn.unigap.api.controller`.
- Các class cho request input cần đặt trong package `vn.unigap.api.dto.in`.
- Các class cho request output cần đặt trong package `vn.unigap.api.dto.out`.
- Các class `service` cần đặt trong package `vn.unigap.api.service`.
- Các class `repository` cần đặt trong package `vn.unigap.api.repository`.
- Các class `entity` cần đặt trong package `vn.unigap.api.entity`.

Bạn có thể tham khảo source code tại thư mục [sample-project](../../source/sample-project).
Hãy sử dụng nó một cách sáng tạo :)

## Dummy API

Bạn cần tiến hành bổ sung các class `Controller`, input/output cho API.

Bổ sung các `validations` cho input.

Thực hiện việc testing để đảm bảo việc mapping và validating đúng.

## Khởi tạo database

Bạn thực hiện việc start database mySQL và import database từ file `job_db.sql`.

**Lưu ý**: Nếu bạn đã cài mySQL server thì không cần khởi chạy docker, chỉ cần import database từ file `job_db.sql` vào
database server mà bạn đã cài đặt.

Xem hướng dẫn tại thư mục [docker-compose/mysql](../../source/docker-compose/mysql).

## Xử lý logic

Sau khi đã start và import database, công việc tiếp theo của bạn là:

- Thêm dependency trong `pom.xml`

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

- Thêm kết nối database
- Khai báo các `entity`
- Khai báo các `repository`
- Khai báo các `service`
- Viết code xử lý logic

## Testing

Tham khảo tutorial sau:

[Testing web](https://spring.io/guides/gs/testing-web/)

Thực hiện viết testing cho từng API


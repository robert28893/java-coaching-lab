# Sprint 5

<!-- TOC -->
* [Sprint 5](#sprint-5)
  * [Overview](#overview)
  * [Mô tả](#mô-tả)
    * [1. Xem chỉ số chung của hệ thống theo ngày](#1-xem-chỉ-số-chung-của-hệ-thống-theo-ngày)
    * [2. Danh sách `job` và các `seeker` phù hợp.](#2-danh-sách-job-và-các-seeker-phù-hợp)
  * [Exception Handler](#exception-handler)
    * [1. Khai báo exception](#1-khai-báo-exception)
    * [2. Handling exception với `ControlAdvice`](#2-handling-exception-với-controladvice)
    * [3. Throwing exception](#3-throwing-exception)
  * [Caching](#caching)
<!-- TOC -->

## Overview

Trong sprint này bạn sẽ triển khai một số API liên quan đến báo cáo, chỉ số. Những API này sẽ yêu cầu các truy vấn phức,
xử lý logic phức tạp hơn.

Tiếp theo bạn sẽ khai báo một `Exception` dùng chung cho dự án, cũng như triển khai exception handler dựa
trên `ControlAdvice`.

Bạn cũng sẽ tìm hiểu và triển khai thử caching một số API thông qua module `Spring Cache`.

## Mô tả

### 1. Xem chỉ số chung của hệ thống theo ngày

API này nhằm thống kê số lượng `employer`, `job`, `seeker`, `resume` được tạo mới trên từng ngày cũng như tổng số lượng
trong một khoảng thời gian.

**Input**

| Trường   | Bắt buộc | Ràng buộc              | Mô tả    |
|----------|----------|------------------------|----------|
| fromDate | x        | Định dạng `yyyy-MM-dd` | Từ ngày  |
| toDate   | x        | Định dạng `yyyy-MM-dd` | Đến ngày |

**Output**

| Trường      | Mô tả                                       |
|-------------|---------------------------------------------|
| numEmployer | Tổng số `employer` trong khoảng ngày        |
| numJob      | Tổng số `job` trong khoảng ngày             |
| numSeeker   | Tổng số `seeker` trong khoảng ngày          |
| numResume   | Tổng số `resume` trong khoảng ngày          |
| chart       | Array<Element>. Danh sách dữ liệu theo ngày |

**Element**

| Trường      | Mô tả                        |
|-------------|------------------------------|
| date        | Ngày                         |
| numEmployer | Tổng số `employer` trên ngày |
| numJob      | Tổng số `job` trên ngày      |
| numSeeker   | Tổng số `seeker` trên ngày   |
| numResume   | Tổng số `resume` trên ngày   |

**Description**

Chart sắp xếp các element theo `date` tăng dần.

### 2. Danh sách `job` và các `seeker` phù hợp.

Lấy danh sách các `job` của một employer và các `seeker` phù hợp với `job` đó.

**Input**

| Trường     | Bắt buộc | Ràng buộc                      | Mô tả                     |
|------------|----------|--------------------------------|---------------------------|
| page       | x        | page phải lớn hơn 0            | Page index                | 
| pageSize   | x        | pageSize không quá 500 phần tử | Số phần tử trên một trang |
| employerId | x        |                                | Lọc theo employerId       |

**Output**

| Trường       | Mô tả                                                        |
|--------------|--------------------------------------------------------------|
| id           | Mã id của job                                                |
| title        | Tiêu đề của job                                              |
| quantity     | Số lượng vị trí cần tuyển                                    |
| fields       | Danh sách {id, name} của `job_field` liên quan đến công việc |
| provinces    | Danh sách {id, name} của `job_province` đăng tuyển công việc |
| salary       | Mức lương                                                    |
| expiredAt    | Ngày hết hạn                                                 |
| employerId   | Mã id của employer                                           |
| employerName | Tên của employer                                             |
| seekers      | Danh sách {id, name} của các `seeker` liên quan đến `job`    |

**Description**

Nếu `employerId` không tồn tại thì báo lỗi.

Danh sách sắp xếp theo thứ tự giảm dần `expiredAt` của `job`.

Các `seeker` sẽ matching với `job` nếu `seeker` có một `resume` thỏa mãn điểu kiện sau:

- `resume` có `salary` nhỏ hơn hoặc bằng `salary` của job

- `resume` có 1 `field` nằm trong `fields` của `job`

- `resume` có 1 `province` nằm trong `provinces` của `job`

## Exception Handler

### 1. Khai báo exception

Khai báo class exception dùng chung cho dự án.

```java
public class ApiException extends RuntimeException {
    private Integer errorCode;
    private HttpStatus httpStatus;
}
```

### 2. Handling exception với `ControlAdvice`

```java

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
}
```

Các việc cần làm:

- [ ] Khai báo `@ControllerAdvice` cho việc handle exception
- [ ] Override các method của lớp `ResponseEntityExceptionHandler`
- [ ] Viết thêm method cho việc xử lý exception `ApiException`

Tham khảo:

[Exception Handling for Rest with Spring](https://www.baeldung.com/exception-handling-for-rest-with-spring)

### 3. Throwing exception

Throwing exception khi gặp các ngoại lệ trong khi xử lý logic.

## Caching

Tham khảo turotial sau:

[Caching Data with Spring](https://spring.io/guides/gs/caching/)

Hãy thử vận dụng vào việc caching 1 số API như xem thông tin `employer`, `job`, ...

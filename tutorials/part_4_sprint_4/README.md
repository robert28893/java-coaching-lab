# Sprint 4

<!-- TOC -->
* [Sprint 4](#sprint-4)
  * [Overview](#overview)
  * [Mô tả](#mô-tả)
    * [Job](#job)
      * [1. Thêm mới `job`](#1-thêm-mới-job)
      * [2. Chỉnh sửa `job`](#2-chỉnh-sửa-job)
      * [3. Lấy thông tin `job`](#3-lấy-thông-tin-job)
      * [4. Lấy danh sách `job`](#4-lấy-danh-sách-job)
      * [5. Xóa `job`](#5-xóa-job)
    * [Seeker](#seeker)
      * [1. Thêm mới `seeker`](#1-thêm-mới-seeker)
      * [2. Chỉnh sửa `seeker`](#2-chỉnh-sửa-seeker)
      * [3. Lấy thông tin `seeker`](#3-lấy-thông-tin-seeker)
      * [4. Lấy danh sách `seeker`](#4-lấy-danh-sách-seeker)
      * [5. Xóa `seeker`](#5-xóa-seeker)
    * [Resume](#resume)
      * [1. Thêm mới `resume`](#1-thêm-mới-resume)
      * [2. Chỉnh sửa `resume`](#2-chỉnh-sửa-resume)
      * [3. Lấy thông tin `resume`](#3-lấy-thông-tin-resume)
      * [4. Lấy danh sách `resume`](#4-lấy-danh-sách-resume)
      * [5. Xóa `resume`](#5-xóa-resume)
<!-- TOC -->

## Overview

Trong sprint này bạn sẽ tiếp tục triển khai các bộ API liên quan đến các entities `job`, `seeker`, `resume`.

## Mô tả

### Job

| Field       | Type     | Key | Comment                                              |
|-------------|----------|-----|------------------------------------------------------|
| id          | bigint   | PRI |                                                      |
| employer_id | bigint   |     | Id của `employer`                                    |
| title       | text     |     | Tiêu đề của job                                      |
| quantity    | int      |     | Số lượng vị trí cần tuyển                            |
| description | text     |     | Mô tả công việc                                      |
| salary      | int      |     | Mức lương, đơn vị triệu                              |
| fields      | text     |     | Danh sách id các `job_field` liên quan đến công việc |
| provinces   | text     |     | Danh sách id các `job_province` đăng tuyển công việc |
| created_at  | datetime |     |                                                      |
| updated_at  | datetime |     |                                                      |
| expired_at  | datetime |     | Ngày hết hạn                                         |

#### 1. Thêm mới `job`

**Input**

| Trường      | Bắt buộc | Ràng buộc | Mô tả                                                |
|-------------|----------|-----------|------------------------------------------------------|
| title       | x        |           | Tiêu đề của job                                      |
| employerId  | x        |           | Mã id của employer                                   |  
| quantity    | x        |           | Số lượng vị trí cần tuyển                            |
| description | x        |           | Mô tả công việc                                      |
| fieldIds    | x        |           | Danh sách id các `job_field` liên quan đến công việc |
| provinceIds | x        |           | Danh sách id các `job_province` đăng tuyển công việc |
| salary      | x        |           | Mức lương                                            |
| expiredAt   | x        |           | Ngày hết hạn                                         |

**Output**

Thông báo thành công hay thất bại.

**Description**

Khi thêm mới cần lưu thời gian tạo mới và cập nhật vào `created_at` và `updated_at`.

Nếu `employerId`, `fieldId`, `provinceId` không tồn tại thì báo lỗi.

#### 2. Chỉnh sửa `job`

**Input**

| Trường      | Bắt buộc | Ràng buộc | Mô tả                                                |
|-------------|----------|-----------|------------------------------------------------------|
| id          | x        |           | Mã id của job                                        |
| title       | x        |           | Tiêu đề của job                                      |
| quantity    | x        |           | Số lượng vị trí cần tuyển                            |
| description | x        |           | Mô tả công việc                                      |
| fieldIds    | x        |           | Danh sách id các `job_field` liên quan đến công việc |
| provinceIds | x        |           | Danh sách id các `job_province` đăng tuyển công việc |
| salary      | x        |           | Mức lương                                            |
| expiredAt   | x        |           | Ngày hết hạn                                         |

**Output**

Thông báo thành công hay thất bại.

**Description**

Khi chỉnh sửa cần lưu thời gian cập nhật vào `updated_at`.

Nếu `employerId`, `fieldId`, `provinceId` không tồn tại thì báo lỗi.

#### 3. Lấy thông tin `job`

**Input**

| Trường | Bắt buộc | Ràng buộc | Mô tả         |
|--------|----------|-----------|---------------|
| id     | x        |           | Mã id của job |

**Output**

| Trường       | Mô tả                                                        |
|--------------|--------------------------------------------------------------|
| id           | Mã id của job                                                |
| title        | Tiêu đề của job                                              |
| quantity     | Số lượng vị trí cần tuyển                                    |
| description  | Mô tả công việc                                              |
| fields       | Danh sách {id, name} của `job_field` liên quan đến công việc |
| provinces    | Danh sách {id, name} của `job_province` đăng tuyển công việc |
| salary       | Mức lương                                                    |
| expiredAt    | Ngày hết hạn                                                 |
| employerId   | Mã id của employer                                           |
| employerName | Tên của employer                                             |

**Description**

Nếu `id` không tồn tại thì báo lỗi.

#### 4. Lấy danh sách `job`

**Input**

| Trường     | Bắt buộc | Ràng buộc                      | Mô tả                             |
|------------|----------|--------------------------------|-----------------------------------|
| page       | x        | page phải lớn hơn 0            | Page index                        | 
| pageSize   | x        | pageSize không quá 500 phần tử | Số phần tử trên một trang         |
| employerId | x        |                                | Lọc theo employerId. <br/>-1: All |

**Output**

| Trường       | Mô tả                     |
|--------------|---------------------------|
| id           | Mã id của job             |
| title        | Tiêu đề của job           |
| quantity     | Số lượng vị trí cần tuyển |
| salary       | Mức lương                 |
| expiredAt    | Ngày hết hạn              |
| employerId   | Mã id của employer        |
| employerName | Tên của employer          |

**Description**

Danh sách sắp xếp theo thứ tự giảm dần `expiredAt` của `job`, tăng dần `name` của `employer`.

#### 5. Xóa `job`

**Input**

| Trường | Bắt buộc | Ràng buộc | Mô tả      |
|--------|----------|-----------|------------|
| id     | x        |           | Id của job |

**Output**

Thông báo thành công hoặc thất bại.

**Description**

Nếu `id` không tồn tại thì báo lỗi.

### Seeker

| Field      | Type     | Key | Comment                                |
|------------|----------|-----|----------------------------------------|
| id         | bigint   | PRI |                                        |
| name       | text     |     | Họ tên                                 |
| birthday   | text     |     | Ngày tháng năm sinh. Dạng `yyyy-MM-dd` |
| address    | text     |     | Địa chỉ                                |
| province   | int      |     | Mã id tỉnh thành, khu vực              |
| created_at | datetime |     |                                        |
| updated_at | datetime |     |                                        |

#### 1. Thêm mới `seeker`

**Input**

| Trường     | Bắt buộc | Ràng buộc | Mô tả                                  |
|------------|----------|-----------|----------------------------------------|
| name       | x        |           | Họ tên                                 |
| birthday   | x        |           | Ngày tháng năm sinh. Dạng `yyyy-MM-dd` |  
| address    |          |           | Địa chỉ                                |
| provinceId | x        |           | Mã id tỉnh thành, khu vực              |

**Output**

Thông báo thành công hay thất bại.

**Description**

Khi thêm mới cần lưu thời gian tạo mới và cập nhật vào `created_at` và `updated_at`.

Nếu `provinceId` không tồn tại thì báo lỗi.

#### 2. Chỉnh sửa `seeker`

**Input**

| Trường     | Bắt buộc | Ràng buộc | Mô tả                                  |
|------------|----------|-----------|----------------------------------------|
| id         | x        |           | Mã id của seeker                       |
| name       | x        |           | Họ tên                                 |
| birthday   | x        |           | Ngày tháng năm sinh. Dạng `yyyy-MM-dd` |  
| address    |          |           | Địa chỉ                                |
| provinceId | x        |           | Mã id tỉnh thành, khu vực              |

**Output**

Thông báo thành công hay thất bại.

**Description**

Khi chỉnh sửa cần lưu thời gian cập nhật vào `updated_at`.

Nếu `provinceId` không tồn tại thì báo lỗi.

#### 3. Lấy thông tin `seeker`

**Input**

| Trường | Bắt buộc | Ràng buộc | Mô tả            |
|--------|----------|-----------|------------------|
| id     | x        |           | Mã id của seeker |

**Output**

| Trường       | Mô tả                                  |
|--------------|----------------------------------------|
| id           | Mã id của seeker                       |
| name         | Họ tên                                 |
| birthday     | Ngày tháng năm sinh. Dạng `yyyy-MM-dd` |  
| address      | Địa chỉ                                |
| provinceId   | Mã id tỉnh thành, khu vực              |
| provinceName | Tên tỉnh thành, khu vực                |

**Description**

Nếu `id` không tồn tại thì báo lỗi.

#### 4. Lấy danh sách `seeker`

**Input**

| Trường     | Bắt buộc | Ràng buộc                      | Mô tả                                      |
|------------|----------|--------------------------------|--------------------------------------------|
| page       | x        | page phải lớn hơn 0            | Page index                                 | 
| pageSize   | x        | pageSize không quá 500 phần tử | Số phần tử trên một trang                  |
| provinceId | x        |                                | Lọc theo tỉnh thành/ khu vực. <br/>-1: All |

**Output**

| Trường       | Mô tả                                  |
|--------------|----------------------------------------|
| id           | Mã id của seeker                       |
| name         | Họ tên                                 |
| birthday     | Ngày tháng năm sinh. Dạng `yyyy-MM-dd` |  
| address      | Địa chỉ                                |
| provinceId   | Mã id tỉnh thành, khu vực              |
| provinceName | Tên tỉnh thành, khu vực                |

**Description**

Danh sách sắp xếp theo `name` của `seeker`.

#### 5. Xóa `seeker`

**Input**

| Trường | Bắt buộc | Ràng buộc | Mô tả         |
|--------|----------|-----------|---------------|
| id     | x        |           | Id của seeker |

**Output**

Thông báo thành công hoặc thất bại.

**Description**

Nếu `id` không tồn tại thì báo lỗi.

### Resume

| Field      | Type     | Key | Comment                                                |
|------------|----------|-----|--------------------------------------------------------|
| id         | bigint   | PRI |                                                        |
| seeker_id  | bigint   |     | Mã id của seeker                                       |
| career_obj | text     |     | Mục tiêu nghề nghiệp                                   |
| title      | text     |     | Tiêu đề                                                |
| salary     | int      |     | Mức lương mong muốn. Đơn vị triệu                      |
| fields     | text     |     | Danh sách id các `job_field` liên quan đến `resume`    |
| provinces  | text     |     | Danh sách id các `job_province` liên quan đến `resume` |
| created_at | datetime |     |                                                        |
| updated_at | datetime |     |                                                        |

#### 1. Thêm mới `resume`

**Input**

| Trường      | Bắt buộc | Ràng buộc | Mô tả                                                  |
|-------------|----------|-----------|--------------------------------------------------------|
| seekerId    | x        |           | Mã id của seeker                                       |
| careerObj   | x        |           | Mục tiêu nghề nghiệp                                   |
| title       | x        |           | Tiêu đề                                                |
| salary      | x        |           | Mức lương mong muốn. Đơn vị triệu                      |
| fieldIds    | x        |           | Danh sách id các `job_field` liên quan đến `resume`    |
| provinceIds | x        |           | Danh sách id các `job_province` liên quan đến `resume` |

**Output**

Thông báo thành công hay thất bại.

**Description**

Khi thêm mới cần lưu thời gian tạo mới và cập nhật vào `created_at` và `updated_at`.

Nếu `seekerId`, `fieldId`, `provinceId` không tồn tại thì báo lỗi.

#### 2. Chỉnh sửa `resume`

**Input**

| Trường      | Bắt buộc | Ràng buộc | Mô tả                                                  |
|-------------|----------|-----------|--------------------------------------------------------|
| id          | x        |           | Mã id của resume                                       |
| careerObj   | x        |           | Mục tiêu nghề nghiệp                                   |
| title       | x        |           | Tiêu đề                                                |
| salary      | x        |           | Mức lương mong muốn. Đơn vị triệu                      |
| fieldIds    | x        |           | Danh sách id các `job_field` liên quan đến `resume`    |
| provinceIds | x        |           | Danh sách id các `job_province` liên quan đến `resume` |

**Output**

Thông báo thành công hay thất bại.

**Description**

Khi chỉnh sửa cần lưu thời gian cập nhật vào `updated_at`.

Nếu `seekerId`, `fieldId`, `provinceId` không tồn tại thì báo lỗi.

#### 3. Lấy thông tin `resume`

**Input**

| Trường | Bắt buộc | Ràng buộc | Mô tả            |
|--------|----------|-----------|------------------|
| id     | x        |           | Mã id của resume |

**Output**

| Trường     | Mô tả                                                          |
|------------|----------------------------------------------------------------|
| id         | Mã id của resume                                               |
| seekerId   | Mã id của seeker                                               |
| seekerName | Họ tên của seeker                                              |
| careerObj  | Mục tiêu nghề nghiệp                                           |
| title      | Tiêu đề                                                        |
| salary     | Mức lương mong muốn. Đơn vị triệu                              |
| fields     | Danh sách {id, name} của `job_field` liên quan đến `resume`    |
| provinces  | Danh sách {id, name} của `job_province` liên quan đến `resume` |

**Description**

Nếu `id` không tồn tại thì báo lỗi.

#### 4. Lấy danh sách `resume`

**Input**

| Trường   | Bắt buộc | Ràng buộc                      | Mô tả                         |
|----------|----------|--------------------------------|-------------------------------|
| page     | x        | page phải lớn hơn 0            | Page index                    | 
| pageSize | x        | pageSize không quá 500 phần tử | Số phần tử trên một trang     |
| seekerId | x        |                                | Lọc theo seeker. <br/>-1: All |

**Output**

| Trường     | Mô tả                             |
|------------|-----------------------------------|
| id         | Mã id của resume                  |
| seekerId   | Mã id của seeker                  |
| seekerName | Họ tên của seeker                 |
| careerObj  | Mục tiêu nghề nghiệp              |
| title      | Tiêu đề                           |
| salary     | Mức lương mong muốn. Đơn vị triệu |

**Description**

Danh sách sắp xếp theo `title` của `resume` và `name` của `seeker`.

#### 5. Xóa `resume`

**Input**

| Trường | Bắt buộc | Ràng buộc | Mô tả         |
|--------|----------|-----------|---------------|
| id     | x        |           | Id của resume |

**Output**

Thông báo thành công hoặc thất bại.

**Description**

Nếu `id` không tồn tại thì báo lỗi.
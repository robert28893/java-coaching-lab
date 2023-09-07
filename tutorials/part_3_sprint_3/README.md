# Sprint 3

<!-- TOC -->
* [Sprint 3](#sprint-3)
  * [Overview](#overview)
  * [Yêu cầu](#yêu-cầu)
<!-- TOC -->

## Overview

Trong sprint này, bạn sẽ thiết kế bộ API cho nghiệp vụ xoay quanh enitity `employer`.

Từ mô tả non-technical bạn sẽ định nghĩa và triền khai các API: in/output, resource naming.

Sau khi đã khai báo xong api dummy, bạn sẽ thêm các layer service, repository để xử lý các logic cho API.

## Yêu cầu

1. Thêm mới `employer`

**Input**

| Trường | Ràng buộc                                | Bắt buộc | Mô tả                    |
|--------|------------------------------------------|----------|--------------------------|
| email  | Độ dài không quá 255 ký tự, format email | x        | Email                    |
| name   | Độ dài không quá 255 ký tự               | x        | Tên doanh nghiệp/cá nhân |

**Output**

**Description**

Nếu `email` đã tồn tại cần báo lỗi.

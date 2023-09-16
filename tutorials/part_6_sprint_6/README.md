# Sprint 6

<!-- TOC -->
* [Sprint 6](#sprint-6)
  * [Overview](#overview)
  * [Logging](#logging)
  * [Security](#security)
<!-- TOC -->

## Overview

Trong sprint này bạn sẽ tìm hiểu về 2 module `logging` và `security`.

Với `logging`, bạn sẽ học cách khai báo `logger`, ghi logs, cấu hình logging level, ...

Với `security`, bạn sẽ tìm hiểu cách `authenticate` API bằng việc sử dụng JWT cơ bản.

## Logging

Trong phần này bạn cần thực hiện những tasks sau:

- [ ] Thêm một `logger` cho class `ApiExceptionHandler` đã khai báo
  ở [sprint 5](../part_5_sprint_5/README.md#2-handling-exception-với-controladvice)

- [ ] Bổ sung in log `debug` cho các `exception` trả về mã lỗi `4xx`. Với mã lỗi `5xx` thì in log `error`.

- [ ] Sử dụng cấu hình `logging.level` ở file `application.yml` để điêù chỉnh level logging cho
  class `ApiExceptionHandler`.

- [ ] Thực hành thêm bằng việc khai báo logger ở controller và log các request/response.

Tham khảo:

- [Spring Boot Logging](https://www.baeldung.com/spring-boot-logging)

## Security

Trong phần này, bạn sẽ `authenticate` API của bạn bằng việc sử dụng 2 module về security của spring là:
`spring-boot-starter-security` và `spring-boot-starter-oauth2-resource-server`.

Khi bạn thêm module `spring-boot-starter-oauth2-resource-server`, spring sẽ tự thêm layer để check `authentication` cho
các
API của bạn. Điều đó có nghĩa là bạn sẽ cần truyền thêm một `token` khi gọi các API để xác thực.

Bạn sẽ cấu hình `resource-server` cũng như token encoder/decoder để phục vụ cho việc tạo/xác thực access token
dạng `jwt`.

Bạn sẽ viết 1 api login trả về access token dạng `jwt`.

Sau đó bạn sẽ sử dụng `jwt` này để thực hiện viêc gọi các API.

Các tasks bao gồm:

- [ ] Cấu hình security và resource server
- [ ] Tiến hành gọi thử các API và thấy đã yêu cầu authentication (Các api sẽ báo lỗi vì đã check thêm authen)
- [ ] Bổ sung api login. Api login trả về `access token`
- [ ] Sử dụng `access token` truyền vào header `Authorization` và gọi lên các API. Xác nhận các api trả về kết quá (đã
  xác thực thành công).

Tham khảo:

- [JWT](https://jwt.io/)
- [Sample Project](../../source/sample-project): package `authentication`
- [Spring OAuth 2.0 Resource Server](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/index.html)
- [spring-security-oauth-resource-server](https://www.baeldung.com/spring-security-oauth-resource-server)
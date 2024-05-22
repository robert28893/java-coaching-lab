# Part 7

<!-- TOC -->
* [Part 7](#part-7)
  * [Overview](#overview)
  * [API docs](#api-docs)
  * [Readme](#readme)
  * [Linter](#linter)
<!-- TOC -->

## Overview

Trong phần này bạn sẽ bổ sung API docs cho project của bạn bằng việc sử dụng module `springdoc-openapi`
kết hợp cùng `swagger`.

Tiếp theo, bạn sẽ học cách viết một file `README.md` mô tả dự án của bạn.

Cuối cùng, bạn sẽ tìm hiểu về công cụ `linter` của java để cấu hình các conventions/rules cho project của bạn. Công cụ
bạn
sẽ sử dụng là `checkstyle`.

## API docs

Công việc của bạn bao gồm:

- [ ] Thêm dependency `springdoc-openapi`

```xml

<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.1.0</version>
</dependency>
```

- [ ] Bổ sung các `annotation` mô tả cho các API
- [ ] Kiểm tra kết quả trên giao diện `swagger-ui`

Tham khảo:

- [https://www.baeldung.com/spring-rest-openapi-documentation](https://www.baeldung.com/spring-rest-openapi-documentation)
- [https://springdoc.org/](https://springdoc.org/)
- [Sample Project](../../source/sample-project)

## Readme

Bạn hãy viết một file README mô tả project của bạn bao gồm các thông tin sau:

- Thông tin chung, tổng quan về project
- Các tính năng có trong project của bạn
- Cách chạy project của bạn bằng `maven`

Tham khảo:

- [https://bulldogjob.com/readme/how-to-write-a-good-readme-for-your-github-project](https://bulldogjob.com/readme/how-to-write-a-good-readme-for-your-github-project)
- [https://www.makeareadme.com/](https://www.makeareadme.com/)

## Linter

Trong phần này bạn sẽ sử dụng công cụ `spotless` để cấu hình các conventions của project.

Công việc của bạn bao gồm:

- [ ] Thêm build plugin của spotless

```xml

<plugin>
    <groupId>com.diffplug.spotless</groupId>
    <artifactId>spotless-maven-plugin</artifactId>
    <version>${spotless.version}</version>
    <configuration>
        <java>
            <toggleOffOn>
                <off>formatter:off</off>
                <on>formatter:on</on>
            </toggleOffOn>
            <eclipse>
                <file>eclipse-java-formatter.xml</file>
                <version>4.26</version>
            </eclipse>
            <removeUnusedImports/>
        </java>
    </configuration>
    <executions>
        <execution>
            <id>spotless-check</id>
            <phase>validate</phase>
            <goals>
                <goal>check</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

- [ ] Thêm file cấu hình conventions `eclipse-java-formatter.xml`
- [ ] Thực hiện việc build lại dự án bằng maven và kiểm tra các vi phạm. Nếu có hãy sửa lại các vi phạm đó.

Tham khảo:

- [https://github.com/diffplug/spotless](https://github.com/diffplug/spotless)
- [https://github.com/diffplug/spotless/tree/main/plugin-maven#java](https://github.com/diffplug/spotless/tree/main/plugin-maven#java)
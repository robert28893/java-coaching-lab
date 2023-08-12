package vn.unigap.java.docs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Hoàng Phương",
                        email = "hoangphuong@email.com",
                        url = "http://hoangphuong.demo.vn"
                ),
                version = "1.0.0",
                title = "API doc for sample project"
        ),
//        tags = {
//                @Tag(
//                        name = "Users",
//                        description = "Quản lý user"
//                )
//        },
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Localhost"
                )
        }
)
@Configuration
public class ApiDocConfiguration {
}

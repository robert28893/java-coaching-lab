package vn.unigap.reqres;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "reqres")
@Data
public class ReqresProperties {
    private String url;
}

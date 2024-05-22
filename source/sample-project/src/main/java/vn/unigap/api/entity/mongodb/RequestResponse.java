package vn.unigap.api.entity.mongodb;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(value = "request_response")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestResponse {
    @MongoId(value = FieldType.OBJECT_ID)
    // @Field(value = "_id")
    private String id;

    private String uuidRequest;

    private String method;

    private String path;

    private Map<String, String> parameters;

    private Map<String, List<String>> requestHeaders;

    private Map<String, Object> requestBody;

    private Integer statusCode;

    private Map<String, List<String>> responseHeaders;

    private Map<String, Object> responseBody;

    private LocalDateTime requestAt;

    private LocalDateTime responseAt;
}

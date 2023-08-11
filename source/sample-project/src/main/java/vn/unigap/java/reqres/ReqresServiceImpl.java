package vn.unigap.java.reqres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@EnableConfigurationProperties(value = {ReqresProperties.class})
public class ReqresServiceImpl implements ReqresService {

    private final RestTemplate restTemplate;
    private final ReqresProperties properties;

    @Autowired
    public ReqresServiceImpl(
            RestTemplate restTemplate,
            ReqresProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    @Override
    public ReqresPageDtoOut<ReqresUserDtoOut> listUsers(int page, int perPage) {
        String url = UriComponentsBuilder.fromHttpUrl(this.properties.getUrl())
                .path("/api/users")
                .queryParam("page", page)
                .queryParam("per_page", perPage)
                .build().toString();

        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null),
                new ParameterizedTypeReference<ReqresPageDtoOut<ReqresUserDtoOut>>() {
                }).getBody();
    }
}

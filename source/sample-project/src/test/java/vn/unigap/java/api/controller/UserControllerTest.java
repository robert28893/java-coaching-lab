package vn.unigap.java.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.UriComponentsBuilder;
import vn.unigap.java.api.dto.in.AuthLoginDtoIn;
import vn.unigap.java.api.dto.in.PageDtoIn;
import vn.unigap.java.api.dto.out.AuthLoginDtoOut;
import vn.unigap.java.api.dto.out.PageDtoOut;
import vn.unigap.java.api.dto.out.UserDtoOut;
import vn.unigap.java.common.response.ApiResponse;

@SpringBootTest()
@AutoConfigureMockMvc
//@WebMvcTest(controllers = {UserController.class})
//@MockBean(classes = {SampleDataGenerationService.class})
class UserControllerTest {

//    @Value(value="${local.server.port}")
//    private int port;

//    @Autowired
//    private TestRestTemplate restTemplate;
//    @MockBean
//    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String accessToken;

    @BeforeEach
    void getAccessToken() throws Exception {
        var uri = UriComponentsBuilder.fromUriString("/auth/login")
                .toUriString();

        AuthLoginDtoIn loginDtoIn = new AuthLoginDtoIn();
        loginDtoIn.setUsername("user");
        loginDtoIn.setPassword("password");

        mockMvc.perform(
                        MockMvcRequestBuilders.post(uri)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(loginDtoIn))
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(result -> {
                    var response = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<ApiResponse<AuthLoginDtoOut>>() {
                            }
                    );
                    accessToken = response.getObject().getAccessToken();
                });
    }

    @Test
    void list() throws Exception {
//        String res = restTemplate.getForObject("http://localhost:" + port + "/users", String.class);
//        System.out.println(res);

//        Mockito.when(userService.list(new PageDtoIn())).thenReturn(PageDtoOut.<UserDtoOut>builder()
//                        .page(1)
//                        .totalPages(2L)
//                        .totalElements(12L)
//                .build());

        PageDtoIn pageDtoIn = new PageDtoIn();
        pageDtoIn.setPage(2);
        pageDtoIn.setPageSize(100);

        var uri = UriComponentsBuilder.fromUriString("/users")
                .queryParam("page", pageDtoIn.getPage())
                .queryParam("pageSize", pageDtoIn.getPageSize())
                .toUriString();

        System.out.println("--ACCESS_TOKEN--: " + accessToken);

        mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(result -> {
                    var response = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<ApiResponse<PageDtoOut<UserDtoOut>>>() {
                            });
                    Assertions.assertNotNull(response);
                    Assertions.assertNotNull(response.getObject());
                    Assertions.assertNotNull(response.getObject().getData());
                    Long totalElements = response.getObject().getTotalElements();
                    Long totalPages = response.getObject().getTotalPages();

                    long expectedTotalPages = totalElements / pageDtoIn.getPageSize();
                    if (totalElements % pageDtoIn.getPageSize() != 0) {
                        expectedTotalPages += 1;
                    }

                    Assertions.assertEquals(expectedTotalPages, totalPages);

                    long expectedDataSize = pageDtoIn.getPageSize();

                    if (pageDtoIn.getPage() > totalPages) {
                        expectedDataSize = 0L;
                    }
                    if (pageDtoIn.getPage() < totalPages) {
                        expectedDataSize = pageDtoIn.getPageSize();
                    }

                    if ((long) pageDtoIn.getPage() == totalPages) {
                        if (totalElements % pageDtoIn.getPageSize() == 0) {
                            expectedDataSize = pageDtoIn.getPageSize();
                        } else {
                            expectedDataSize = totalElements % pageDtoIn.getPageSize();
                        }
                    }

                    Assertions.assertEquals(expectedDataSize, response.getObject().getData().size());
                });
    }


}
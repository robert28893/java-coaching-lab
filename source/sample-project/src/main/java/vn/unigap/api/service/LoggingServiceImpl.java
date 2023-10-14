package vn.unigap.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.unigap.api.entity.mongodb.RequestResponse;
import vn.unigap.api.repository.mongodb.reqres.RequestResponseRepository;
import vn.unigap.common.Common;
import vn.unigap.common.Constants;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@Log
public class LoggingServiceImpl implements LoggingService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RequestResponseRepository requestResponseRepository;

    @Override
    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        RequestResponse reqres;
        if (httpServletRequest.getAttribute(Constants.REQUEST_RESPONSE_ATTRIBUTE) == null) {
            String uuidRequest = Common.uuid();

            reqres = RequestResponse.builder()
                    .uuidRequest(uuidRequest)
                    .requestAt(Common.currentDateTime())
                    .build();

            httpServletRequest.setAttribute(Constants.REQUEST_RESPONSE_ATTRIBUTE, reqres);
        } else {
            reqres = (RequestResponse) httpServletRequest.getAttribute(Constants.REQUEST_RESPONSE_ATTRIBUTE);
        }

        reqres.setMethod(httpServletRequest.getMethod());
        reqres.setPath(httpServletRequest.getRequestURI());
        reqres.setParameters(buildParametersMap(httpServletRequest));
        reqres.setRequestHeaders(buildHeadersMap(httpServletRequest));

//        StringBuilder stringBuilder = new StringBuilder();
//        Map<String, String> parameters = buildParametersMap(httpServletRequest);

//        stringBuilder.append("REQUEST ");
//        stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] ");
//        stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
//        stringBuilder.append("headers=[").append(buildHeadersMap(httpServletRequest)).append("] ");

//        if (!parameters.isEmpty()) {
//            stringBuilder.append("parameters=[").append(parameters).append("] ");
//        }

        if (body != null) {
            try {
                reqres.setRequestBody(objectMapper.readValue(
                        objectMapper.writeValueAsString(body),
                        new TypeReference<>() {
                        }));
            } catch (JsonProcessingException e) {
                log.warning("could not parse request body: " + body);
            }
//            stringBuilder.append("body=[" + objectMapper.convertValue(body, new TypeReference<Map<String, Object>>() {
//            }) + "]");
        }

        log.info("REQUEST: " + reqres);
    }

    @Override
    public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body) {
        if (httpServletRequest.getAttribute(Constants.REQUEST_RESPONSE_ATTRIBUTE) == null) {
            log.warning("Illegal state, no have request response attribute");
            return;
        }

        RequestResponse reqres = (RequestResponse) httpServletRequest.getAttribute(Constants.REQUEST_RESPONSE_ATTRIBUTE);
        reqres.setResponseAt(Common.currentDateTime());
        reqres.setStatusCode(httpServletResponse.getStatus());
        reqres.setResponseHeaders(buildHeadersMap(httpServletResponse));
        try {
            reqres.setResponseBody(objectMapper.readValue(objectMapper.writeValueAsString(body), new TypeReference<Map<String, Object>>() {
            }));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("RESPONSE ");
//        stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] ");
//        stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
//        stringBuilder.append("responseHeaders=[").append(buildHeadersMap(httpServletResponse)).append("] ");
//        try {
//            stringBuilder.append("responseBody=[")
//                    .append(objectMapper.readValue(objectMapper.writeValueAsString(body), new TypeReference<Map<String, Object>>() {
//                    }))
//                    .append("] ");
//        } catch (JsonProcessingException e) {
//            log.warning("could not parse response body: " + body);
//        }

//        log.info(stringBuilder.toString());
        log.info("RESPONSE: " + reqres);
        CompletableFuture.runAsync(() -> {
            try {
                requestResponseRepository.save(reqres);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {
        Map<String, String> resultMap = new HashMap<>();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = httpServletRequest.getParameter(key);
            resultMap.put(key, value);
        }

        return resultMap;
    }

    private Map<String, List<String>> buildHeadersMap(HttpServletRequest request) {
        Map<String, List<String>> map = new HashMap<>();

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            List<String> values = map.getOrDefault(key, new ArrayList<>());
            String value = request.getHeader(key);
            values.add(value);
            map.put(key, values);
        }

        return map;
    }

    private Map<String, List<String>> buildHeadersMap(HttpServletResponse response) {
        Map<String, List<String>> map = new HashMap<>();

        Collection<String> headerNames = response.getHeaderNames();
        for (String header : headerNames) {
            List<String> values = map.getOrDefault(header, new ArrayList<>());
            values.add(response.getHeader(header));
            map.put(header, values);
        }

        return map;
    }
}

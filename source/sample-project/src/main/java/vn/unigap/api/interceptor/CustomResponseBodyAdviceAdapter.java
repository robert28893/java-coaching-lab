package vn.unigap.api.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import vn.unigap.api.service.LoggingService;

@ControllerAdvice(basePackages = {"vn.unigap.api.controller", "vn.unigap.common.exception"})
public class CustomResponseBodyAdviceAdapter implements ResponseBodyAdvice<Object> {
    
    private final LoggingService loggingService;

    @Autowired
    public CustomResponseBodyAdviceAdapter(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }
    
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
            Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (serverHttpRequest instanceof ServletServerHttpRequest && serverHttpResponse instanceof ServletServerHttpResponse) {
            HttpServletRequest request = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
            loggingService.logResponse(request,
                    ((ServletServerHttpResponse) serverHttpResponse).getServletResponse(), o);
        }
        
        return o;
    }
}

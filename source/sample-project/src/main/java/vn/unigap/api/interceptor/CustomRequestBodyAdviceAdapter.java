package vn.unigap.api.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import jakarta.servlet.http.HttpServletRequest;
import vn.unigap.api.service.LoggingService;

import java.lang.reflect.Type;

@ControllerAdvice(basePackages = {"vn.unigap.api.controller"})
public class CustomRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {
    
    private final LoggingService loggingService;
    
//    @Autowired
//    HttpServletRequest httpServletRequest;

    @Autowired
    public CustomRequestBodyAdviceAdapter(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }
    
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String logId = (String) request.getAttribute("REQUEST_ID");
        System.out.println("REQUEST_ID: " + logId);
        loggingService.logRequest(request, body);
        
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}

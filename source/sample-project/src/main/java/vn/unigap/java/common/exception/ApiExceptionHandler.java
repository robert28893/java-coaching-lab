package vn.unigap.java.common.exception;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.MimeType;
import org.springframework.validation.ObjectError;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.unigap.java.common.errorcode.ErrorCode;
import vn.unigap.java.common.response.ApiResponse;

import java.util.stream.Collectors;


@ControllerAdvice
@Order
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException e) {
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .errorCode(e.getErrorCode())
                        .statusCode(e.getHttpStatus().value())
                        .message(e.getMessage())
                        .build(),
                e.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String supportedMethods = ex.getSupportedMethods() == null ? null : String.join(",", ex.getSupportedMethods());

        String msg = String.format("Method not supported: %s, only support %s",
                ex.getMethod(), supportedMethods);
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .errorCode(ErrorCode.METHOD_NOT_ALLOWED)
                        .statusCode(status.value())
                        .message(msg)
                        .build(),
                status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String supportedContentTypes = ex.getSupportedMediaTypes().stream().map(MimeType::toString)
                .collect(Collectors.joining(", "));

        String msg = String.format("MediaType not supported: %s, only support %s",
                ex.getContentType(), supportedContentTypes);
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .errorCode(ErrorCode.UNSUPPORTED_MEDIA_TYPE)
                        .statusCode(status.value())
                        .message(msg)
                        .build(),
                status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String supportedContentTypes = ex.getSupportedMediaTypes().stream().map(MimeType::toString)
                .collect(Collectors.joining(", "));

        String msg = String.format("MediaType not acceptable: only support %s", supportedContentTypes);
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .errorCode(ErrorCode.NOT_ACCEPTABLE)
                        .statusCode(status.value())
                        .message(msg)
                        .build(),
                status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ex.printStackTrace();

        String msg = String.format("MissingPathVariable: variable name %s, parameter %s", ex.getVariableName(),
                ex.getParameter().getParameterName());
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .errorCode(ErrorCode.INTERNAL_ERR)
                        .statusCode(status.value())
                        .message(msg)
                        .build(),
                status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String msg = String.format("MissingServletRequestParameter: parameter name %s", ex.getParameterName());
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .errorCode(ErrorCode.BAD_REQUEST)
                        .statusCode(status.value())
                        .message(msg)
                        .build(),
                status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String msg = String.format("MissingServletRequestPart: request part name %s", ex.getRequestPartName());
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .errorCode(ErrorCode.BAD_REQUEST)
                        .statusCode(status.value())
                        .message(msg)
                        .build(),
                status);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String msg = String.format("ServletRequestBinding: detail message code %s", ex.getDetailMessageCode());
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .errorCode(ErrorCode.BAD_REQUEST)
                        .statusCode(status.value())
                        .message(msg)
                        .build(),
                status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String fieldErrors = ex.getFieldErrors().stream().map(
                fieldError -> String.format("%s:%s", fieldError.getObjectName(), fieldError.getField())
        ).collect(Collectors.joining(","));

        String glObjectErrors = ex.getGlobalErrors().stream().map(ObjectError::getObjectName)
                .collect(Collectors.joining(","));

        String msg = String.format("MethodArgumentNotValid field errors: %s, global errors: %s",
                fieldErrors, glObjectErrors);

        return new ResponseEntity<>(
                ApiResponse.builder()
                        .errorCode(ErrorCode.BAD_REQUEST)
                        .statusCode(status.value())
                        .message(msg)
                        .build(),
                status);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String msg = String.format("NoHandlerFound: method %s, url %s", ex.getHttpMethod(), ex.getRequestURL());

        return new ResponseEntity<>(
                ApiResponse.builder()
                        .errorCode(ErrorCode.NOT_FOUND)
                        .statusCode(status.value())
                        .message(msg)
                        .build(),
                status);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .errorCode(ErrorCode.SERVICE_UNAVAILABLE)
                        .statusCode(status.value())
                        .message("AsyncRequestTimeout")
                        .build(),
                status);
    }

    @Override
    protected ResponseEntity<Object> handleErrorResponseException(ErrorResponseException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .errorCode(status.value())
                        .statusCode(status.value())
                        .message(ex.getDetailMessageCode())
                        .build(),
                status);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ex.printStackTrace();
        String requiredType = ex.getRequiredType() == null ? null : ex.getRequiredType().getSimpleName();
        String msg = String.format("ConversionNotSupported: required type %s", requiredType);
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .errorCode(ErrorCode.INTERNAL_ERR)
                        .statusCode(status.value())
                        .message(msg)
                        .build(),
                status);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String requiredType = ex.getRequiredType() == null ? null : ex.getRequiredType().getSimpleName();
        String msg = String.format("ConversionNotSupported: property %s, required type %s", ex.getPropertyName(), requiredType);
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .errorCode(ErrorCode.BAD_REQUEST)
                        .statusCode(status.value())
                        .message(msg)
                        .build(),
                status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .errorCode(ErrorCode.BAD_REQUEST)
                        .statusCode(status.value())
                        .message("HttpMessageNotReadable")
                        .build(),
                status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .errorCode(ErrorCode.INTERNAL_ERR)
                        .statusCode(status.value())
                        .message("HttpMessageNotWritable")
                        .build(),
                status);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleUnknownException(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .errorCode(ErrorCode.INTERNAL_ERR)
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

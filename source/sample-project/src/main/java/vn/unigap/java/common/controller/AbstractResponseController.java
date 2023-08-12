package vn.unigap.java.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.unigap.java.common.response.ApiResponse;

public abstract class AbstractResponseController {
    public <T> ResponseEntity<ApiResponse<T>> responseEntity(CallbackFunction<T> callback) {
        return responseEntity(callback, HttpStatus.OK);
    }

    public <T> ResponseEntity<ApiResponse<T>> responseEntity(CallbackFunction<T> callback, HttpStatus status) {
        T result = callback.execute();
        return ResponseEntity.status(status).body(ApiResponse.success(result));
    }
}

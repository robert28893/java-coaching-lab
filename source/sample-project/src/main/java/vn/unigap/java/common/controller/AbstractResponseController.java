package vn.unigap.java.common.controller;

import org.springframework.http.ResponseEntity;
import vn.unigap.java.common.response.ApiResponse;

public abstract class AbstractResponseController {
	public <T> ResponseEntity<ApiResponse<T>> responseEntity(CallbackFunction<T> callback) {
		T result = callback.execute();
		return ResponseEntity.ok(ApiResponse.success(result));
	}
}

package com.sertifikasi.learn.dto.response;

import org.springframework.http.HttpStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiSignInResponseBuilder {
    private Object data;
    private String message;
    private int statusCode;
    private HttpStatus status;

    @Builder
    public ApiSignInResponseBuilder(Object data, String message, int statusCode, HttpStatus status) {
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
        this.status = status;
    }
}

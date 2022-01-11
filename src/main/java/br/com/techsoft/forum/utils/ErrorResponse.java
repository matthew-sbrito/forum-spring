package br.com.techsoft.forum.utils;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ErrorResponse<T> {

    private HttpStatus httpStatus;
    private String message;
    private List<T> errors;

    public ErrorResponse(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public ErrorResponse(HttpStatus httpStatus, String message, List<T> errors) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.errors = errors;
    }

    public Integer getHttpStatus() {
        return httpStatus.value();
    }

    public String getMessage() {
        return message;
    }

    public List<T> getErrors() {
        return errors;
    }
}

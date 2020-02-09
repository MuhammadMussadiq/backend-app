package com.assignemnt.demo.dto;

import java.util.List;

public class ErrorResponse {

    private int statusCode;
    private List<String> message;

    public ErrorResponse(int statusCode, List<String> message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }
}

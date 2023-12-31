package com.clone.config;

import java.time.LocalDateTime;

@SuppressWarnings({"unused"})
public class ErrorDetail {

    private final String message;
    private final String error;
    private final LocalDateTime timestamp;

    public ErrorDetail(String error, String message, LocalDateTime timestamp) {
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
    }

}

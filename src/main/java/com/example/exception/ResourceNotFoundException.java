package com.example.exception;

import lombok.Data;
import lombok.experimental.Accessors;

public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    @Data
    @Accessors(fluent = true, chain = true)
    public static class Builder {
        private String message;

        public ResourceNotFoundException build() {
            return new ResourceNotFoundException(message);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
    
}

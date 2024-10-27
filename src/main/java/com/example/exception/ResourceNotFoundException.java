package com.example.exception;

public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public static class Builder {
        private String message;

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public ResourceNotFoundException build() {
            return new ResourceNotFoundException(message);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
    
}

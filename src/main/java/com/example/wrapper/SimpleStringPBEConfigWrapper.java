package com.example.wrapper;

import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

public class SimpleStringPBEConfigWrapper extends SimpleStringPBEConfig {
    
    public static class Builder {
        private String password;
        private String poolSize;

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder poolSize(String poolSize) {
            this.poolSize = poolSize;
            return this;
        }

        public SimpleStringPBEConfig build() {
            final var config = new SimpleStringPBEConfig();
            config.setPassword(password);
            config.setPoolSize(poolSize);
            return config;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}

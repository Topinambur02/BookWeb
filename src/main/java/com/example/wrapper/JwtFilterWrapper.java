package com.example.wrapper;

import com.example.filter.JwtFilter;
import com.example.security.JwtUtils;
import com.example.service.UserService;

public class JwtFilterWrapper extends JwtFilter {
    
    @SuppressWarnings("unused")
    public static class Builder {

        private JwtUtils utils;
        private UserService service;

        public Builder jwtUtils(JwtUtils utils) {
            this.utils = utils;
            return this;
        }

        public Builder userService(UserService service) {
            this.service = service;
            return this;
        }
        
        public JwtFilter build() {
            return new JwtFilter();
        }
        
    }

    public static Builder builder() {
        return new Builder();
    }
}

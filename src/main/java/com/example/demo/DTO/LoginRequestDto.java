    package com.example.demo.DTO;

    import lombok.Data;

    // @Data from Lombok provides getters, setters, toString, etc. automatically.
    @Data
    public class LoginRequestDto {
        private String email;
        private String password;
    }
package com.example.demo.DTO;

import lombok.Data;

@Data
public class CreateFacultyRequestDto {
    private String fullName;
    private String email;
    private String password;
}
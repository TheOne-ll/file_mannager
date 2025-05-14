package com.example.file_mannager.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class User {
   private Integer id;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime lastLoginTime;
    private LocalDateTime updateTime;
    private String pic;
}

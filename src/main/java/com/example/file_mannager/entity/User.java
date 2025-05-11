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
    private LocalDateTime create_time;
    private LocalDateTime last_login_time;
    private LocalDateTime update_time;
    private String pic;
}

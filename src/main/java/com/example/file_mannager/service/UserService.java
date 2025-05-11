package com.example.file_mannager.service;

import com.example.file_mannager.entity.User;

public interface UserService {
    User findbyname(String username);

    void register(String username, String md5Password);
}

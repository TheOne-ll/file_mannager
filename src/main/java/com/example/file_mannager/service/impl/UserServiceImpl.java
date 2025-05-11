package com.example.file_mannager.service.impl;

import com.example.file_mannager.DAO.UserMapper;
import com.example.file_mannager.entity.User;
import com.example.file_mannager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User findbyname(String username) {
        return userMapper.findByname(username);
    }

    @Override
    public void register(String username, String md5Password) {
        userMapper.adduser(username,md5Password);
    }
}

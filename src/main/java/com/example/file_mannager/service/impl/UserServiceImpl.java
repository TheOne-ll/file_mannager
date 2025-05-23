package com.example.file_mannager.service.impl;

import com.example.file_mannager.DAO.UserMapper;
import com.example.file_mannager.entity.LoginUserDetails;
import com.example.file_mannager.entity.User;
import com.example.file_mannager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Override
    public void updateLogintime(Integer id) {
        userMapper.updatelogintime(id);
    }

    @Override
    public void updateemail(String email) {
        //上下文定位用户

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null&&authentication.getPrincipal() instanceof LoginUserDetails){
            LoginUserDetails loginUserDetails=(LoginUserDetails) authentication.getPrincipal();
            Integer id=loginUserDetails.getId();
            userMapper.updateEmail(email,id);
        }

    }
}

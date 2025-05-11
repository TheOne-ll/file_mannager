package com.example.file_mannager.controller;

import com.example.file_mannager.entity.User;
import com.example.file_mannager.entity.result;
import com.example.file_mannager.service.impl.UserServiceImpl;
import com.example.file_mannager.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @PostMapping("/register")
    public result addUser(String username,String password){
        //首先查询用户对象名是否已在数据库中
        String md5Password= Md5Util.getMD5String(password);
        User u=userService.findbyname(username);
        if(u==null){
            //不存在，调用数据库操作并返回success
            userService.register(username,md5Password);
            return result.success();
        }
        //存在返回success
        else{
            return result.error("用户已存在");
        }
    }
}

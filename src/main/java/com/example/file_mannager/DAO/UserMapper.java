package com.example.file_mannager.DAO;

import com.example.file_mannager.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select *from user where username=#{username}")
    User findByname(String username);
    @Insert("insert into user (username)")
    void adduser(String username, String md5Password);
}

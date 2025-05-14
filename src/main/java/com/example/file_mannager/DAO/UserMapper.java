package com.example.file_mannager.DAO;

import com.example.file_mannager.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("select *from user where username=#{username}")
    User findByname(String username);
    @Insert("insert into user (username,password,create_time,update_time)values(#{username},#{password},now(),now())")
    void adduser(@Param("username") String username, @Param("password") String md5Password);
    @Update("update user set last_login_time=now() where id=#{id}")
    void updatelogintime(Integer id);
    @Update("update user set email=#{email} where id=#{id}")
    void updateEmail(@Param("email") String email,@Param("id")Integer id);
}

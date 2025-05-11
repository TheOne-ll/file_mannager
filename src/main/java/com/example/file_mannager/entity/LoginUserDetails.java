package com.example.file_mannager.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class LoginUserDetails extends User {
    private Integer id;
    public LoginUserDetails(String username,
                            String password,
                            Integer id,
                            Collection<?extends GrantedAuthority>authorities){
        super(username,password,authorities);
        this.id=id;
    }
    public Integer getId(){
        return id;
    }
}

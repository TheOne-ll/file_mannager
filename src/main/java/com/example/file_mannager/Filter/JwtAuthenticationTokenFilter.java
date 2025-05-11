package com.example.file_mannager.Filter;

import com.example.file_mannager.entity.LoginUserDetails;
import com.example.file_mannager.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static sun.net.ftp.FtpDirEntry.Permission.USER;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    //OncePerRequestFilter用于创建在每一个请求仅执行一次的过滤器
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request,//请求
                                    HttpServletResponse response, //响应
                                    FilterChain filterChain) //过滤器
            throws ServletException, IOException {
        try{
            //获取token,请求头命名为header
            String token=request.getHeader("header");
            if(token==null){
                filterChain.doFilter(request,response);
                return ;
            }
            // 检查redis中是否存在对应的token，防止用户更改密码时客户端仍有权限访问服务端
            ValueOperations<String,String>valueOperations=stringRedisTemplate.opsForValue();
            String redisToken=valueOperations.get(token);
            if(redisToken==null){
                throw new RuntimeException("token 已失效");
            }
            //解析token
            Map<String,Object>map= JwtUtil.parseToken(token);
            String username=(String)map.get("username");
            Integer id=(Integer) map.get("id");
            //创建授权对象
            UserDetails userDetails= new LoginUserDetails(username,
                    "",
                    id,
                    AuthorityUtils.createAuthorityList("ROLE_USER"));
            Authentication authentication=new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            //保存在上下文中
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (Exception e){
            response.setStatus(401);
            return ;
        }
                filterChain.doFilter(request,response);
    }
}

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
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;



@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 定义放行路径列表
    private static final List<String>white_list=Arrays.asList(
            "/user/login",
            "user/register"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
            String rUrl=request.getRequestURI();
            if(white_list.contains(rUrl)){
                //跳过白名单路径
                filterChain.doFilter(request,response);
                return ;
            }
            // 1. 获取Token
            String token = request.getHeader("Authorization");
            if (!StringUtils.hasText(token)) {
                throw new RuntimeException("令牌为空");
            }
            try{
                // 2. 检查Redis中的Token有效性
                ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
                String redisToken = valueOperations.get(token);
                if (redisToken == null) {
                    throw new RuntimeException("Token已失效");
                }

                // 3. 解析Token并设置认证信息
                Map<String, Object> map = JwtUtil.parseToken(token);
                String username = (String) map.get("username");
                Integer id = (Integer) map.get("id");

                UserDetails userDetails = new LoginUserDetails(
                        username,
                        "",
                        id,
                        AuthorityUtils.createAuthorityList("ROLE_USER")
                );

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // 4. 继续执行过滤器链
                filterChain.doFilter(request, response);
            }catch (Exception e){
                e.printStackTrace();
            }

    }
}
package com.example.ptms.interceptor;

import com.example.ptms.common.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtUtil jwtUtil;
    public JwtInterceptor(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String auth = request.getHeader("Authorization");

        if (auth == null || !auth.startsWith("Bearer ")) {
            reject(response);
            return false;
        }
        String token = auth.substring(7);
        try {
            Long userId = jwtUtil.parseUserId(token);
            UserContext.set(userId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            reject(response);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request,HttpServletResponse response,Object handler,Exception ex){
        UserContext.clear();
    }

    private void reject(HttpServletResponse response) throws IOException{
        response.setStatus(401);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":401,\"data\":null,\"message\":\"未登陆或登陆已过期\"}");
    }
}

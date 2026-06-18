
// LoginInterceptor.java
package com.example.aulabd.config;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        
        if (uri.equals("/login") || uri.equals("/registrar") || uri.equals("/favicon.ico") || uri.startsWith("/css") || uri.startsWith("/js")) {
            return true;
        }
        
        if (session.getAttribute("usuario") == null) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}

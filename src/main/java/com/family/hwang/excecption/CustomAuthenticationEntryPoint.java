package com.family.hwang.excecption;

import com.family.hwang.controller.response.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import static com.family.hwang.excecption.ErrorCode.INVALID_TOKEN;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setStatus(INVALID_TOKEN.getStatus().value());
        response.getWriter().write(Response.error(INVALID_TOKEN.name()).toStream());
    }
}

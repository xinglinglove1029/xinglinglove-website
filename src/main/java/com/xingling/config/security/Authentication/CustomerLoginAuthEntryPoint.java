package com.xingling.config.security.Authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权认证失败后调用
 */
@Component("customerLoginAuthEntryPoint")
@Slf4j
public class CustomerLoginAuthEntryPoint implements AuthenticationEntryPoint {

    private String loginUrl = "/login";


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if(isAjaxRequest(request)){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authException.getMessage());
        }else{
            response.sendRedirect(loginUrl);
        }
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
    }

}
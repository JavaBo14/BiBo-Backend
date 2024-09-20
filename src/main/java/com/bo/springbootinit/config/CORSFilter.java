package com.bo.springbootinit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跨域
 */
@Component
@Configuration
public class CORSFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        res.addHeader("Access-Control-Allow-Credentials", "true");
        res.addHeader("Access-Control-Allow-Origin",req.getHeader("origin"));
        res.addHeader("Access-Control-Allow-Methods","GET,POST,DELETE,PUT");
        res.addHeader("Access-Control-Allow-Headers","Content-Type,X-CAF-Authorization-Token,sessionToken,X-TOKEN,customercoderoute,authorization,conntectionid,Cookie");
        if (((HttpServletRequest)request).getMethod().equals("OPTIONS")){
            response.getWriter().println("ok");
            return;
        }
        chain.doFilter(request, response);
    }
}
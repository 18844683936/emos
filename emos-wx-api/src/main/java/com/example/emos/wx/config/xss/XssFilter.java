package com.example.emos.wx.config.xss;

import com.example.emos.wx.config.xss.XssHttpRequestWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class XssFilter  implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        XssHttpRequestWrapper wrapper = new XssHttpRequestWrapper((HttpServletRequest) servletRequest);
        filterChain.doFilter(wrapper,servletResponse);
    }
}

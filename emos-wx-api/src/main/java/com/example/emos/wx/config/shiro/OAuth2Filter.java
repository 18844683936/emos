package com.example.emos.wx.config.shiro;

import cn.hutool.core.util.StrUtil;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Component
@Scope("prototype")
public class OAuth2Filter extends AuthenticatingFilter {

    @Autowired
    private ThreadLocalToken token;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${emos.jwt.cache-expire}")
    private int cacheExpire;

    /**
     * 拦截请求之后，用于把令牌字符串封装成令牌对象
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        //获取token
        String token = getRequestToken(req);
        if (StrUtil.isBlank(token)){
            return null;
        }
        return new OAuth2Token(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest req = (HttpServletRequest) request;
        // Ajax提交application/json数据的时候，会先发出Options请求
        // 这里要放行Options请求，不需要Shiro处理
        if (req.getMethod().equals(HttpMethod.OPTIONS)){
            return true;
        }
        // 除了Options请求之外，所有请求都要被Shiro处理
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }

    private String getRequestToken(HttpServletRequest request){
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)){
            token = request.getParameter("token");
        }
        return token;
    }
}

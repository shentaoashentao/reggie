package com.shentao.reggie.filter;

//检查用户是否已经完成登录

import com.alibaba.fastjson.JSON;
import com.shentao.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    //路径匹配
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;



        //1.获取本次请求的URL
        String requestURL = request.getRequestURI();

        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"

        };

        //2.判断本次请求是否需要处理
        boolean check = check(urls,requestURL);

        //3.不需要处理的话直接放行
        if(check){
            filterChain.doFilter(request,response);
            return;
        }

        //4.判断登陆状态 如果已经登录就直接放行
        if(request.getSession().getAttribute("employee")!=null){
            filterChain.doFilter(request,response);
            return;
        }

        //5.如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    //路径匹配，检查本次请求
    public boolean check(String[] urls,String requestURL){
        for (String url : urls) {
           boolean match =  PATH_MATCHER.match(url,requestURL);
           if (match){
               return true;
           }
        }
        return false;
    }
}

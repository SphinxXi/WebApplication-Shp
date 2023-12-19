package com.Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author SphinxXi
 * @classname S10_illegalAccessBlocking
 * @createTime: 2023/12/18 14:42
 *
 * 非法访问拦截
 *      拦截的资源
 *          拦截所有资源  /*
 *      需要放行的资源
 *          1. 放行指定页面（无需登录即可访问的页面 例如：登录、注册等）
 *          2. 放行静态资源（image,js,css文件等）
 *                          -- 与设定的目录一致，目录下所有资源都放行 -- js目录，image目录.etc
 *          3. 放行指定操作（无需登录即可执行的操作 例如：登录、注册操作）
 *          4. 放行登录状态 -- 判断session中的用户信息是否为空
 *
 *          其他请求需要被拦截跳转到登录页面
 */
//@WebFilter("/*")
public class illegalAccess implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 基于HTTP请求
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 获取请求路径
        String uri = request.getRequestURI();
        // 1. 放行指定页面（无需登录即可访问的页面 例如：登录、注册等）
        if(uri.contains("/user_login.jsp") || uri.contains("/user_register.jsp") || uri.contains("/user_logout.jsp")) {
            filterChain.doFilter(request,response);
            return;
        }
        // 2. 静态资源放行
        if (uri.contains("/js")||uri.contains("/image")||uri.contains("/css")){
            filterChain.doFilter(request,response);
            return;
        }
        // 3. 指定操作 -- servlet
        if(uri.contains("/login") || uri.contains("/register") || uri.contains("/logout")){
            filterChain.doFilter(request,response);
            return;
        }
        // 4. 登录状态 ---  登录成功后的操作： request.getSession().setAttribute("user",messageModel.getObject());
        Object user = request.getSession().getAttribute("user");
        // 判断session是否为空
        if(user!=null){
            filterChain.doFilter(request,response);
            return;
        }
        // 当用户未登录时，拦截请求跳转到登录页面
        response.sendRedirect("/user_login.jsp");
    }
    @Override
    public void destroy() {

    }

}

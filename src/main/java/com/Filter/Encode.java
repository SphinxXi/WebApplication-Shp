package com.Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author SphinxXi
 * @classname S10_AEncodingFilter
 * @createTime: 2023/12/17 21:38
 */

/*
 *2.1 请求乱码处理
 *   post请求： 仅需要 request.setCharacterEncoding("UTF-8");
 *   get请求：  Tomcat8以下，需要 new String(request.getParameter("参数名").getBytes("ISO-8859-1"),"UTF-8");
 *      ----- 最终乱码源于getParameter()方法
 */
@WebFilter("/*")
public class Encode implements Filter {
    public Encode(){

    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void destroy() {

    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 基于HTTP
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 处理请求乱码（POST请求）
        request.setCharacterEncoding("UTF-8");
        // 处理Get请求，且服务器版本在Tomcat8以下
        String method = request.getMethod();
        // 如果是Get请求
        if("GET".equalsIgnoreCase(method)){
            // 服务器版本在Tomcat8以下的Apach Tomcat/8.0.45
            String serverInfo = request.getServletContext().getServerInfo();
            //得到具体版本号 -- 8
            String versionStr = serverInfo.substring(serverInfo.indexOf("/")+1,serverInfo.indexOf("."));
            //判断服务器版本是否小于8
            if(Integer.parseInt(versionStr)<8){
                // 得到自定义内部类
                // （MyWapper继承了HttpServletRequestWapper对象，而HttpServletRequestWapper对象实现了HttpServletRequest接口，
                //  所以Wapper本质也是requeset对象）
                HttpServletRequest myRequest = new MyWrapper(request);  // 将当前request对象交给包装类，获取参数并解决乱码
                // 放行资源
                filterChain.doFilter(myRequest,response);
                return;
            }
        }
        filterChain.doFilter(request,response);
    }
    // 定义内部类，继承HttpServletRequestWrapper包装类对象，重写getParameter()方法
    class MyWrapper extends HttpServletRequestWrapper{
        // 定义成员变量，提升构造器中request对象的范围
        private HttpServletRequest request;
        //带参构造，传递request
        public MyWrapper(HttpServletRequest request){
            super(request);
            this.request=request;
        }
        // 重写getParameter()方法
        @Override
        public String getParameter(String name) {
            String value = request.getParameter(name);

            if(value!=null && !"".equals(value.trim())){
                try{
                    // 将ISO-8859-1编码的字符转换成UTF-8
                    value =new String(value.getBytes("ISO-8859-1"),"UTF-8");
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
            }
            return value;
        }
    }

}

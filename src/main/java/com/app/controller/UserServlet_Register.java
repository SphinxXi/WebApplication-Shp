package com.app.controller;

import com.app.entity.vo.MessageModel;
import com.app.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author SphinxXi
 * @classname UserServlet_Register
 * @createTime: 2023/12/18 15:49
 */
@WebServlet("/register")
public class UserServlet_Register extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 接收客户端的请求（接收参数：姓名、密码）
        String uname=request.getParameter("uname");
        String upwd=request.getParameter("upwd");
        Integer uage = Integer.parseInt(request.getParameter("uage"));
        // 2. 调用service层方法，返回消息模型对象
        MessageModel messageModel= null;
        try {
            messageModel = userService.userRegister(uname,upwd,uage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 3. 判断消息模型的状态码
        if(messageModel.getCode()==1){  // 成功
            // 将消息模型对象设置到session作用域中，重定向跳转到user_login.jsp
            request.getSession().setAttribute("messageModel",messageModel);
            request.getRequestDispatcher("user_login.jsp").forward(request,response);
        }else{  // 失败
            // 将消息模型对象设置到request作用域中，请求转发跳转到login.jsp
            request.setAttribute("messageModel",messageModel);
            request.getRequestDispatcher("user_register.jsp").forward(request,response);
        }
    }
}

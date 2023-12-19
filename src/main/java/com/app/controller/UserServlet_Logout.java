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
 * @classname User_Logout
 * @createTime: 2023/12/18 17:46
 */
@WebServlet("/logout")
public class UserServlet_Logout extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 接收客户端的请求（接收参数：姓名、密码）
        String uname=request.getParameter("uname");
        String upwd=request.getParameter("upwd");
        // 2. 调用service层方法，返回消息模型对象
        MessageModel messageModel= null;
        try {
            messageModel = userService.userLogout(uname,upwd);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 3. 判断消息模型的状态码
        if(messageModel.getCode()==1){  // 成功
            // 将消息模型对象设置到session作用域中，重定向跳转到index.jsp
            request.getSession().setAttribute("messageModel",messageModel);
            response.sendRedirect("user_login.jsp");
        }else{  // 失败
            // 将消息模型对象设置到request作用域中，请求转发跳转到login.jsp
            request.setAttribute("messageModel",messageModel);
            request.getRequestDispatcher("user_logout.jsp").forward(request,response);
        }
    }
}

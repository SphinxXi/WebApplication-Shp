package com.app.controller;

import com.app.entity.vo.MessageModel;
import com.app.service.ProductInfoService;
import com.app.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author SphinxXi
 * @classname UserServlet
 * @createTime: 2023/12/12 16:05
 */
@WebServlet("/login")
public class UserServlet_Login extends HttpServlet {
    // 实例化UserService对象
    private UserService userService = new UserService();
    /*
     *  用户登录
     * 1. 接收客户端的请求（接收参数：姓名、密码）
     * 2. 调用service层方法，返回消息模型对象
     * 3. 判断消息模型的状态码
     *      如果状态码是失败
                将消息模型对象设置到request作用域中，请求转发跳转到login.jsp
            如果状态码是成功
                将消息模型对象设置到session作用域中，请求转发跳转到index.jsp     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 接收客户端的请求（接收参数：姓名、密码）
        String uname=request.getParameter("uname");
        String upwd=request.getParameter("upwd");
        // 2. 调用service层方法，返回消息模型对象
        MessageModel messageModel= null;
        try {
            messageModel = userService.userLogin(uname,upwd);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 3. 判断消息模型的状态码
        if(messageModel.getCode()==1){  // 成功
            // 将用户消息模型对象设置到session作用域中，重定向跳转到index.jsp
            request.getSession().setAttribute("user",messageModel.getObject());
            // 登录成功，则读取商品信息
            MessageModel messageModel1_ProductInfo = null;
            try {
                messageModel1_ProductInfo = ProductInfoService.getProductInfo();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            // 将商品消息模型对象设置到session作用域中
            request.getSession().setAttribute("ProductInfo",messageModel1_ProductInfo.getObject());
            response.sendRedirect("index.jsp");
        }else{  // 失败
            // 将消息模型对象设置到request作用域中，请求转发跳转到login.jsp
            request.setAttribute("messageModel",messageModel);
            request.getRequestDispatcher("user_login.jsp").forward(request,response);
        }
    }
}

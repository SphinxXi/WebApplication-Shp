package com.app.controller;

import com.app.entity.vo.MessageModel;
import com.app.service.ProductInfoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author SphinxXi
 * @classname Product_Del
 * @createTime: 2023/12/19 19:41
 */
@WebServlet("/product_Del")
public class Product_Del extends HttpServlet {
    private ProductInfoService productInfoService = new ProductInfoService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 接收客户端的请求（接收参数：姓名、密码）
        String pName=request.getParameter("productNameDel");
        // 2. 调用service层方法，返回消息模型对象
        MessageModel messageModel= null;
        try {
            messageModel = productInfoService.ProductDel(pName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 3. 判断消息模型的状态码
        if(messageModel.getCode()==1){  // 成功
            // 设定为 messageModel
            request.getSession().setAttribute("messageModel",messageModel);
            response.sendRedirect("index.jsp");
        }else{  // 失败
            // 设定为 messageModel2 ---- 【与上messageModel不同】
            request.setAttribute("messageModel2",messageModel);
            request.getRequestDispatcher("product_update.jsp").forward(request,response);
        }
    }
}

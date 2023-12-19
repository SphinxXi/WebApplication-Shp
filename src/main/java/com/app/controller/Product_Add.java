package com.app.controller;

import com.app.entity.ProductInfo;
import com.app.entity.vo.MessageModel;
import com.app.service.ProductInfoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author SphinxXi
 * @classname Product_Add
 * @createTime: 2023/12/19 19:41
 */
@WebServlet("/product_Add")
public class Product_Add extends HttpServlet {
    private ProductInfoService productInfoService = new ProductInfoService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 接收客户端的请求（接收参数：姓名、密码）
        String pName=request.getParameter("productNameAdd");
        Integer pNum=Integer.parseInt(request.getParameter("productNumAdd"));
        Double pPrice=Double.parseDouble(request.getParameter("productPriceAdd"));
        // 2. 调用service层方法，返回消息模型对象
        MessageModel messageModel= null;
        try {
            messageModel = productInfoService.ProductAdd(pName,pNum,pPrice);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 3. 判断消息模型的状态码
        if(messageModel.getCode()==1){  // 成功
            // 设定为 messageModel
            request.getSession().setAttribute("messageModel",messageModel);
            response.sendRedirect("index.jsp");
        }else{  // 失败
            // 设定为 messageModel1 ---- 【与上messageModel不同】
            request.setAttribute("messageModel1",messageModel);
            request.getRequestDispatcher("product_update.jsp").forward(request,response);
        }
    }
}

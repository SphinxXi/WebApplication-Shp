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
 * @classname product_NumAdd
 * @createTime: 2023/12/19 19:46
 */
@WebServlet("/product_NumAdd")
public class product_NumAdd extends HttpServlet {
    private ProductInfoService productInfoService = new ProductInfoService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 实例化消息模型对象
        MessageModel messageModel= new MessageModel();

        ArrayList<ProductInfo> productInfos = null;
        try {
            productInfos = ProductInfoService.getProductInfo2();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Map<String,Integer> productAdd = new HashMap<String,Integer>();
        Map<String,Integer> productAfter = new HashMap<String,Integer>();
        for(ProductInfo productInfo: productInfos){
            String name = productInfo.getProductName();     // 商品名称
            Integer numOri = productInfo.getProductNum();   // 商品原始数量
            String nameBuyStr = request.getParameter(name);
            Integer numAdd = 0;     // 商品添加量
            // 空参数判断
            if(!(nameBuyStr==null || "".equals(nameBuyStr.trim()))){
                numAdd = Integer.parseInt(nameBuyStr);
            }

            productAfter.put(name,numOri + numAdd);   // 更新后的商品信息
            productAdd.put(name,numAdd);         // 存储添加商品信息
        }
        // 遍历比较 商品数量的合理性
        for(String key:productAfter.keySet()){
            // 未选择购买
            if(productAdd.get(key)==null){
                continue;
            }
            // 选择购买，购买数量超过剩余数量，返回错误提示
            if(productAfter.get(key) < 0){
                messageModel.setCode(0);
                messageModel.setMsg("数量修改不合理，请重新修改");
                messageModel.setObject(productAdd);

                // 及时清空
                productAdd.clear();
                productAfter.clear();

                // 请求转发到购买界面   --- 【出现parameter依然存在的问题 --> 重定向】
                request.setAttribute("messageModel",messageModel);
                request.getRequestDispatcher("product_update.jsp").forward(request,response);
            }
        }

        // 添加商品，更新数据库
        try {
            messageModel = productInfoService.AddNumAndRefresh(productAfter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 更新 obj
        messageModel.setObject(productAdd);
        // 及时清空
        productAdd.clear();
        productAfter.clear();
        // 重定向回到商品界面
        request.getSession().setAttribute("messageModel",messageModel);
        response.sendRedirect("index.jsp");
    }
}

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
 * @classname Product_Buy
 * @createTime: 2023/12/18 22:24
 *  【如果出现不能读取param，很可能因为乱码，商品id传递编码不一致  ---->  过滤器处理乱码】
 */
@WebServlet("/product_buy")
public class Product_Buy extends HttpServlet {
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

        Map<String,Integer> productBought = new HashMap<String,Integer>();
        Map<String,Integer> productAfter = new HashMap<String,Integer>();
        for(ProductInfo productInfo: productInfos){
            String name = productInfo.getProductName();     // 商品名称
            Integer numOri = productInfo.getProductNum();   // 商品原始数量
            String nameBuyStr = request.getParameter(name);
            Integer numBuy = 0;     // 商品购买量
            // 空参数判断
            if(!(nameBuyStr==null || "".equals(nameBuyStr.trim()))){
                numBuy = Integer.parseInt(nameBuyStr);
            }

            productAfter.put(name,numOri-numBuy);   // 更新后的商品信息
            productBought.put(name,numBuy);         // 存储购买商品信息
        }
        // 遍历比较 商品数量的合理性
        for(String key:productAfter.keySet()){
            // 未选择购买
            if(productBought.get(key)==null){
                continue;
            }
            // 选择购买，购买数量超过剩余数量，返回错误提示
            if(productAfter.get(key) < 0){
                messageModel.setCode(0);
                messageModel.setMsg("购买数量超出剩余商品数量，请重新选择数量");
                messageModel.setObject(productBought);

                // 清空
                productBought.clear();
                productAfter.clear();

                // 请求转发到购买界面
                request.setAttribute("messageModel",messageModel);
                request.getRequestDispatcher("index.jsp").forward(request,response);
            }
        }

        // 选择购买，数量合理，更新数据库
        try {
            messageModel = productInfoService.buyAndRefresh(productAfter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 更新 obj
        messageModel.setObject(productBought);
        // 清空
        productBought.clear();
        productAfter.clear();
        // 重定向回到购买界面
        request.getSession().setAttribute("messageModel",messageModel);
        response.sendRedirect("index.jsp");
    }
}

package com.app.service;

import com.app.entity.ProductInfo;
import com.app.entity.UserInfo;
import com.app.entity.vo.MessageModel;
import com.app.mapper.ProductMapper;
import com.app.mapper.UserMapper;
import com.app.util.GetSqlSession;
import com.app.util.StringUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author SphinxXi
 * @classname ProductInfoService
 * @createTime: 2023/12/17 19:37
 */
public class ProductInfoService {
    // 获取所有商品信息，返回 MessageModel
    public static MessageModel getProductInfo() throws Exception {
        MessageModel messageModel = new MessageModel();

        SqlSession session = GetSqlSession.createSqlSession();
        ProductMapper productMapper = session.getMapper(ProductMapper.class);

        ArrayList<ProductInfo> productInfos = productMapper.selectAllP();   // 存在ArrayList，所以修改为静态方法？？？

        messageModel.setCode(1);
        messageModel.setMsg("读取商品信息成功");
        messageModel.setObject(productInfos);
        return  messageModel;
    }

    // 获取所有商品信息，返回 ArrayList<ProductInfo>
    public static ArrayList<ProductInfo> getProductInfo2() throws Exception {
        SqlSession session = GetSqlSession.createSqlSession();
        ProductMapper productMapper = session.getMapper(ProductMapper.class);

        ArrayList<ProductInfo> productInfos = productMapper.selectAllP();   // 存在ArrayList，所以修改为静态方法

        return productInfos;
    }

    // 购买商品后，更新数据库
    public MessageModel buyAndRefresh(Map<String,Integer> productRemain) throws Exception {
        MessageModel messageModel = new MessageModel();

        SqlSession session = GetSqlSession.createSqlSession();
        ProductMapper productMapper = session.getMapper(ProductMapper.class);

        ArrayList<ProductInfo> productInfos = productMapper.selectAllP();   // 存在ArrayList，所以修改为静态方法？？？

        for(int i=0;i<productInfos.size();i++){
            ProductInfo productInfo = productInfos.get(i);
            // 获取商品名
            String name = productInfo.getProductName();
            // 获取剩余商品数量
            int num = productRemain.get(name);
            if(productInfo.getProductNum()!=num) {
                // 更新 Num
                productInfo.setProductNum(productRemain.get(name));
                // 更新数据库
                productMapper.updatePData(productInfo);
            }
        }
        // 提交，关闭
        session.commit();
        session.close();
        // 返回
        messageModel.setCode(1);
        messageModel.setMsg("购买数量合理，购买完成！");
        return messageModel;
    }

    // 更改商品数量后，更新数据库
    public MessageModel AddNumAndRefresh(Map<String,Integer> productAfterAdd) throws Exception {
        MessageModel messageModel = new MessageModel();

        SqlSession session = GetSqlSession.createSqlSession();
        ProductMapper productMapper = session.getMapper(ProductMapper.class);

        ArrayList<ProductInfo> productInfos = productMapper.selectAllP();   // 存在ArrayList，所以修改为静态方法？？？

        for(int i=0;i<productInfos.size();i++){
            ProductInfo productInfo = productInfos.get(i);
            // 获取商品名
            String name = productInfo.getProductName();
            // 获取商品数量
            int num = productAfterAdd.get(name);
            if(productInfo.getProductNum()!=num) {
                // 更新 Num
                productInfo.setProductNum(productAfterAdd.get(name));
                // 更新数据库
                productMapper.updatePData(productInfo);
            }
        }
        // 提交，关闭
        session.commit();
        session.close();
        // 返回
        messageModel.setCode(1);
        messageModel.setMsg("商品数量修改完成！");
        return messageModel;
    }
    // 商品添加
    public MessageModel ProductAdd(String pName,Integer pNum, Double pPrice) throws Exception{
        MessageModel messageModel = new MessageModel();

        // 回显数据
        ProductInfo p = new ProductInfo();
        p.setProductName(pName);
        p.setProductNum(pNum);
        p.setProductPrice(pPrice);
        messageModel.setObject(p);

        // 1. 参数的非空判断
        if(StringUtil.isEmpyt(pName) || pNum==-1 || pPrice==-1.0){
            // 将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
            messageModel.setCode(0);
            messageModel.setMsg("商品名、数量、价格不能为空");

            return messageModel;
        }
        // 2. 调用dao层的查询方法，通过商品名查询商品对象
        SqlSession session = GetSqlSession.createSqlSession();
        ProductMapper productMapper = session.getMapper(ProductMapper.class);
        ProductInfo productInfo = productMapper.selectByPName(pName);
        // 3. 判断商品对象是否已经存在
        if(productInfo != null){
            // 将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
            messageModel.setCode(0);
            messageModel.setMsg("该商品已存在！");
            return messageModel;
        }
        if (pNum<0){
            // 将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
            messageModel.setCode(0);
            messageModel.setMsg("商品数量不能小于0！");
            return messageModel;
        }
        if (pPrice<=0){
            // 将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
            messageModel.setCode(0);
            messageModel.setMsg("商品价格不能<=0！");
            return messageModel;
        }

        // 5. 添加成功，连接数据库，添加商品信息
        productMapper.insertPData(p);
        session.commit();
        session.close();
        messageModel.setCode(1);
        messageModel.setMsg("商品添加成功");
        return messageModel;
    }
    // 商品删除
    public MessageModel ProductDel(String pName) throws Exception{
        MessageModel messageModel = new MessageModel();
        // 回显数据
        ProductInfo p = new ProductInfo();
        p.setProductName(pName);
        // 1. 参数的非空判断
        if(StringUtil.isEmpyt(pName)){
            // 将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
            messageModel.setCode(0);
            messageModel.setMsg("注销商品名称不能为空");

            return messageModel;
        }
        // 2. 调用dao层的查询方法，通过用户名查询用户对象
        SqlSession session = GetSqlSession.createSqlSession();
        ProductMapper productMapper = session.getMapper(ProductMapper.class);
        ProductInfo productInfo = productMapper.selectByPName(pName);
        // 3. 判断用户对象是否为空
        if(productInfo == null){
            // 将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
            messageModel.setCode(0);
            messageModel.setMsg("注销用户不存在！");
            messageModel.setObject(p);
            return messageModel;
        }
        // 5. 注销成功，将用户信息设置到消息模型中
        productMapper.deleteByPName(pName);
        session.commit();
        session.close();
        messageModel.setCode(1);
        messageModel.setMsg("商品删除成功");
        messageModel.setObject(null);   // 不显示已删除商品信息
        return messageModel;
    }
}

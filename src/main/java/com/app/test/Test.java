package com.app.test;

import com.app.entity.ProductInfo;
import com.app.entity.UserInfo;
import com.app.mapper.ProductMapper;
import com.app.mapper.UserMapper;
import com.app.util.GetSqlSession;
import org.apache.ibatis.session.SqlSession;
import com.app.service.ProductInfoService;

import java.util.ArrayList;

/**
 * @author SphinxXi
 * @classname Test
 * @createTime: 2023/12/12 15:37
 */
public class Test {
    public static void main(String[] args) throws Exception {
//        // 获取sqlSession对象
//        SqlSession session= GetSqlSession.createSqlSession();
//        // 获取对应Mapper --- 找到对应Mapper后，调用接口
//        UserMapper userMapper = session.getMapper(UserMapper.class);
//        // 调用方法，返回用户对象
//        UserInfo userinfo=userMapper.selectByName("qqq");
//        System.out.println(userinfo);

        SqlSession session = GetSqlSession.createSqlSession();
        ProductMapper productMapper = session.getMapper(ProductMapper.class);

        ArrayList<ProductInfo> productInfos = productMapper.selectAllP();
        System.out.println(productInfos);

//        UserInfo u1=new UserInfo("qqq","aaa",20);
//        userMapper.insertData(u1);
//        // 插入后提交
//        session.commit();
//        session.close();
    }
}

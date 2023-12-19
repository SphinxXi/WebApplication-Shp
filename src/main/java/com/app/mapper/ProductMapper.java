package com.app.mapper;

import com.app.entity.ProductInfo;

import java.util.ArrayList;

/**
 * @author SphinxXi
 * @classname ProductMapper
 * @createTime: 2023/12/17 19:38
 */
public interface ProductMapper {
    //新增记录
    public int insertPData(ProductInfo product) throws Exception;

    //更新记录
    public int updatePData(ProductInfo product) throws Exception;

    //删除记录
    public void deletePData(int id) throws Exception;

    // 根据name删除记录
    public void deleteByPName(String name) throws Exception;

    //根据ID查询记录
    public ProductInfo selectByPID(int id) throws Exception;

    // 根据Name查询记录
    public ProductInfo selectByPName(String name) throws Exception;

    //查询所有记录
    public ArrayList<ProductInfo> selectAllP() throws Exception;
}

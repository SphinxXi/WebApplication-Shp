package com.app.mapper;

import com.app.entity.UserInfo;

import java.util.ArrayList;

/**
 * @author SphinxXi
 * @classname UserMapper
 * @createTime: 2023/12/11 22:44
 */
public interface UserMapper {
    //新增记录
    public int insertData(UserInfo product) throws Exception;

    //更新记录
    public int updateData(UserInfo product) throws Exception;

    //删除记录
    public void deleteData(int id) throws Exception;

    // 根据Name删除记录
    public void deleteByName(String name) throws Exception;

    //根据ID查询记录
    public UserInfo selectByID(int id) throws Exception;

    // 根据Name查询记录
    public UserInfo selectByName(String name) throws Exception;

    //查询所有记录
    public ArrayList<UserInfo> selectAll() throws Exception;
}

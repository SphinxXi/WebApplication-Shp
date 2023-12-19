package com.app.service;

import com.app.entity.UserInfo;
import com.app.entity.vo.MessageModel;
import com.app.mapper.UserMapper;
import com.app.util.GetSqlSession;
import com.app.util.StringUtil;
import org.apache.ibatis.session.SqlSession;

import javax.xml.registry.infomodel.User;


/**
 * @author SphinxXi
 * @classname UserService
 * @createTime: 2023/12/12 16:05
 *  业务逻辑
 *
   service层（业务逻辑）
      1. 参数的非空判断
          如果参数为空
              将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
      2. 调用dao层的查询方法，通过用户名查询用户对象
      3. 判断用户对象是否为空
          如果为空
              将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
      4. 判断数据库中查询到的用户密码与前台传递的密码作比较
          如果不相等
              将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
      5. 登录成功，将成功状态、提示信息、用户对象设置到消息模型对象对象，并return
 */
/*
 *  1. userLogin
 *  2. userRegister
 */
public class UserService {
    public MessageModel userLogin(String uname,String upwd) throws Exception {
        MessageModel messageModel = new MessageModel();

        // 回显数据
        UserInfo u = new UserInfo();
        u.setUserName(uname);
        u.setUserPwd(upwd);
        messageModel.setObject(u);

        // 1. 参数的非空判断
        if(StringUtil.isEmpyt(uname) || StringUtil.isEmpyt(upwd)){
            // 将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
            messageModel.setCode(0);
            messageModel.setMsg("用户名和密码不能为空");

            return messageModel;
        }
        // 2. 调用dao层的查询方法，通过用户名查询用户对象
        SqlSession session = GetSqlSession.createSqlSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);
        UserInfo userInfo = userMapper.selectByName(uname);
        // 3. 判断用户对象是否为空
        if(userInfo == null){
            // 将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
            messageModel.setCode(0);
            messageModel.setMsg("用户不存在！");
            return messageModel;
        }
        // 4. 判断数据库中查询到的用户密码与前台传递的密码作比较
        if(!upwd.equals(userInfo.getUserPwd())){
            // 将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
            messageModel.setCode(0);
            messageModel.setMsg("用户密码不正确！");
            return messageModel;
        }
        // 5. 登录成功，将用户信息设置到消息模型中
        messageModel.setCode(1);
        messageModel.setObject(userInfo);
        return messageModel;
    }

    public MessageModel userRegister(String uname, String upwd, Integer uage) throws Exception {
        MessageModel messageModel = new MessageModel();

        // 回显数据
        UserInfo u = new UserInfo();
        u.setUserName(uname);
        u.setUserPwd(upwd);
        u.setUserAge(uage);
        messageModel.setObject(u);

        // 1. 参数的非空判断
        if(StringUtil.isEmpyt(uname) || StringUtil.isEmpyt(upwd) || uage==-1){
            // 将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
            messageModel.setCode(0);
            messageModel.setMsg("注册名、密码、年龄不能为空");

            return messageModel;
        }
        // 2. 调用dao层的查询方法，通过用户名查询用户对象
        SqlSession session = GetSqlSession.createSqlSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);
        UserInfo userInfo = userMapper.selectByName(uname);
        // 3. 判断用户对象是否已经存在
        if(userInfo != null){
            // 将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
            messageModel.setCode(0);
            messageModel.setMsg("用户名已存在！");
            return messageModel;
        }
        // 4. 判断用户名是否合法（长度、内容）
        if (uname.length()<8 || uname.length()>20){
            // 将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
            messageModel.setCode(0);
            messageModel.setMsg("用户名长度不合法，请控制长度在8-20！");
            return messageModel;
        }
        for(int i=0;i<uname.length();i++){
            if (!Character.isLetterOrDigit(uname.charAt(i))){
                // 将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
                messageModel.setCode(0);
                messageModel.setMsg("用户名不合法，请输入字母或数字！");
                return messageModel;
            }
        }
        // 5. 判断用户密码是否合法（长度、内容）
        if (upwd.length()<8 || upwd.length()>20){
            // 将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
            messageModel.setCode(0);
            messageModel.setMsg("用户密码长度不合法，请控制长度在8-20！");
            return messageModel;
        }
        for(int i=0;i<upwd.length();i++){
            if (!Character.isLetterOrDigit(upwd.charAt(i))){
                // 将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
                messageModel.setCode(0);
                messageModel.setMsg("用户密码不合法，请输入字母或数字！");
                return messageModel;
            }
        }
        // 6. 判断用户年龄是否合法
        if (uage<0 || uage>150){
            messageModel.setCode(0);
            messageModel.setMsg("用户年龄不合法！");
            return messageModel;
        }

        // 5. 登录成功，连接数据库，添加用户信息
        userMapper.insertData(u);
        session.commit();
        session.close();
        messageModel.setMsg("用户注册成功");
        messageModel.setCode(1);
        return messageModel;
    }

    public MessageModel userLogout(String uname,String upwd) throws Exception {
        MessageModel messageModel = new MessageModel();

        // 回显数据
        UserInfo u = new UserInfo();
        u.setUserName(uname);
        u.setUserPwd(upwd);
        messageModel.setObject(u);

        // 1. 参数的非空判断
        if(StringUtil.isEmpyt(uname) || StringUtil.isEmpyt(upwd)){
            // 将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
            messageModel.setCode(0);
            messageModel.setMsg("注销用户名和密码不能为空");

            return messageModel;
        }
        // 2. 调用dao层的查询方法，通过用户名查询用户对象
        SqlSession session = GetSqlSession.createSqlSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);
        UserInfo userInfo = userMapper.selectByName(uname);
        // 3. 判断用户对象是否为空
        if(userInfo == null){
            // 将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
            messageModel.setCode(0);
            messageModel.setMsg("注销用户不存在！");
            return messageModel;
        }
        // 4. 判断数据库中查询到的用户密码与前台传递的密码作比较
        if(!upwd.equals(userInfo.getUserPwd())){
            // 将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
            messageModel.setCode(0);
            messageModel.setMsg("注销用户密码不正确！");
            return messageModel;
        }
        // 5. 注销成功，将用户信息设置到消息模型中
        userMapper.deleteByName(uname);
        session.commit();
        session.close();
        messageModel.setCode(1);
        messageModel.setMsg("用户注销成功");
        messageModel.setObject(null);   // 不显示注销用户的 userName,userPwd
        return messageModel;
    }

}

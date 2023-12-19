package com.app.entity;

/**
 * @author SphinxXi
 * @classname UserInfo
 * @createTime: 2023/12/11 22:36
 *  User实体类
 */
public class UserInfo {
    // 字段名与类型，与数据库保持一致
    private Integer userId; // 用户编号
    private String userName;
    private String userPwd;
    private Integer userAge = -1;   // 设置默认值检验

    public UserInfo(){
        super();
    }
    public UserInfo(int id, String name, String pwd, int Age){
        userId = new Integer(id);
        userName = name;
        userPwd = pwd;
        userAge = Age;
    }
    public UserInfo(String name, String pwd, int Age){
        userName = name;
        userPwd = pwd;
        userAge = Age;
    }
    // Fn+Alt+Insert -> Get Set方法
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }
}

<%--
  Created by IntelliJ IDEA.
  User: SphinxXi
  Date: 2023/12/11
  Time: 22:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>用户登录</title>
</head>
<body>
<div style="text-align: center">
    <form action="/login" method="post" id="loginForm">     <!-- action+ '/' 表示绝对路径，直接跟在localhost:8080后，  不加'/'为相对路径，访问需要站点名 -->
        姓名：<input type="text" name="uname" id="uname" value="${messageModel.object.userName}"> <br>     <!--value用于回显-->
        密码：<input type="password" name="upwd" id="upwd" value="${messageModel.object.userPwd}"> <br>
        <span id="msg" style="font-size: 12px;color: red">${messageModel.msg}</span> <br>       <!--msg为登录提示信息-->
        <button type="button" id="loginBtn">登录</button>
        <button type="button" id="registerBtn" onclick="window.location.href='user_register.jsp';">注册</button>
        <button type="button" id="logoutBtn" onclick="window.location.href='user_logout.jsp';">注销</button>
    </form>
</div>
</body>
<%--引入JQuery的js文件--%>
<script type="text/javascript" src="js/jquery-3.5.1.js"></script>
<script type="text/javascript">
    /*
        登录表单验证
    1. 登录按钮绑定点击事件（通过id选择器绑定）
    2. 获取用户姓名和密码的值
    3. 判断姓名是否为空
        如果姓名为空，提示用户（span标签赋值），并且return
    4. 判断密码是否为空
        如果密码为空，提示用户（span标签赋值），并且return
    5. 如果都不为空，则手动提交表单
     */
    $("#loginBtn").click(function(){
        //获取用户姓名和密码的值
        var uname=$("#uname").val();
        var upwd=$("#upwd").val();

        if(isEmpty(uname)){
            // 如果姓名为空，提示用户（span标签赋值），并且return html()
            $("#msg").html("用户姓名不可为空！");
            return;
        }
        if(isEmpty(upwd)){
            // 如果姓名为空，提示用户（span标签赋值），并且return html()
            $("#msg").html("用户密码不可为空！");
            return;
        }
        // 如果都不为空，则手动提交表单
        $("#loginForm").submit();
    })

    // 判断字符串是否为空
    function isEmpty(str){
        if(str==null || str.trim()==""){
            return true;
        }
        return false;
    }
</script>
</html>

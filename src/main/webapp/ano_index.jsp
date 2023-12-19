<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         import="java.sql.*"
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Insert title here</title></head>
<body><%
  //star
  Class.forName("com.mysql.cj.jdbc.Driver");
  String url = "jdbc:mysql://localhost:8080/WebApp?useSSL=false&serverTimezone=UTC";
  //    8.0以后版本加载驱动
  //    3306为端口号根据自己数据库端口设置--
  //    ...3306/"数据库名称"？useSSL......
  //    com.mysql.jdbc.Driver 更换为 com.mysql.cj.jdbc.Driver。
  //    MySQL 8.0 以上版本不需要建立 SSL 连接的，需要显示关闭。
  //end
  //

  //star
  //    Class.forName("com.mysql.jdbc.Driver");
  //    String url = "jdbc:mysql://localhost:3306/world";
  //    8.0以前版本加载驱动方式
  //end

  String un = "TestDB";         //MySql用户名
  String pa = "qwerasdf";       //MySql密码
  Connection con = DriverManager.getConnection(url, un, pa);
  Statement st = con.createStatement();
  String sql = "select * from ProductInfo";
  ResultSet rs = st.executeQuery(sql);
%>
<table border="1" cellpadding="0" cellspacing="0">
  <caption>用户列表</caption>
  <tr>
    <td>id</td>
    <td>用户名</td>
    <td>数量</td>
  </tr>
  <%while (rs.next()) {
  %>
  <tr>
    <td><%=rs.getInt("userId") %>
    </td>
    <td><%=rs.getString("userName") %>
    </td>
    <td><%=rs.getString("userNum") %>
    </td>
  </tr>
  <%} %></table>
</body>
</html>
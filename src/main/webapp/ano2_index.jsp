<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>

<html>
<head>
  <title>通过JSP打开数据表</title>
</head>
<body>

<%
  try {
    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  // 驱动程序名    ---- 与sqlserver.properties一致
    String url = "jdbc:sqlserver://localhost:1433;useUnicode=true;databaseName=WebApp"; //数据库名
    String username = "TestDB";  //数据库用户名
    String password = "qwerasdf";  //数据库用户密码
    Connection conn = DriverManager.getConnection(url, username, password);  //连接状态

    if(conn != null){
      out.print("数据库连接成功！");
      out.print("<br />");
      Statement stmt = null;
      ResultSet rs = null;
      String sql = "select *from ProductInfo";  //查询语句
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);


      out.print("查询结果：");
      out.print("<br/>");
      out.println("productName"+"   "+"productNum"+"   "+"productPrice");
      out.print("<br/>");
      while (rs.next()) {
        out.println(rs.getString("productName")+"   &nbsp  "+rs.getString("productNum")+"  &nbsp "+rs.getInt("productPrice")); //将查询结果输出
        out.print("<br/>");
      }
    }
    else{
      out.print("连接失败！");
    }
  }catch (Exception e) {
    out.print("数据库连接异常！");
  }
%>

<div class="clean"></div>

<div id="content2">
  <div class="con2_title">
    <b><a href="#"><img src="images/ico_jt.jpg" border="0" /></a></b>
    <span><a href="#">新品速递</a> | <a href="#">畅销排行</a> | <a href="#">特价抢购</a> | <a href="#">男士护肤</a>&nbsp;&nbsp;
    </span>
    <img src="images/con2_title.jpg" /></div>
  <div class="line1"></div>
  <div class="con2_content"><a href="#"><img src="images/con2_content.jpg" width="981" height="405" border="0" /></a></div>
  <div class="scroll_brand"><a href="#"><img src="images/scroll_brand.jpg" border="0" /></a></div>
  <div class="gray_line"></div>
</div>

<div id="content4">
  <div class="con2_title"><b><a href="#"><img src="images/ico_jt.jpg" border="0" /></a></b><span><a href="#">新品速递</a> | <a href="#">畅销排行</a> | <a href="#">特价抢购</a> | <a href="#">男士护肤</a>&nbsp;&nbsp;</span><img src="images/con4_title.jpg" width="27" height="13" /></div>
  <div class="line3"></div>
  <div class="con2_content"><a href="#"><img src="images/con4_content.jpg" width="980" height="207" border="0" /></a></div>
  <div class="gray_line"></div>
</div>


</body>
</html>

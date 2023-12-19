<%@ page import="java.util.ArrayList" %>
<%@ page import="com.app.entity.ProductInfo" %>
<%@ page import="java.sql.*"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
</head>
<body>

<%
  ResultSet rs = null;
  try {
    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  // 驱动程序名    ---- 与sqlserver.properties一致
    String url = "jdbc:sqlserver://localhost:1433;useUnicode=true;databaseName=WebApp"; //数据库名
    String username = "TestDB";  //数据库用户名
    String password = "qwerasdf";  //数据库用户密码
    Connection conn = DriverManager.getConnection(url, username, password);  //连接状态

    if(conn != null){
//      out.print("数据库连接成功！");
      out.print("<br />");
      Statement st = conn.createStatement();
      String sql = "select *from ProductInfo";  //查询语句
      rs = st.executeQuery(sql);
    }
    else{
      out.print("连接失败！");
    }
  }catch (Exception e) {
    out.print("数据库连接异常！");
  }
%>

<div style="text-align: center">
  <h1><%= "当前展示商品列表" %>
  </h1>
  <form action="/product_NumAdd" method="post" id="productNumAddForm">     <!-- action+ '/' 表示绝对路径，直接跟在localhost:8080后，  不加'/'为相对路径，访问需要站点名 -->
    <%--        <%while (rs.next()) {--%>
    <%--        %>--%>
    <%--        <span style="display:inline-block;width:200px;font-weight:bold;">--%>
    <%--            <%=rs.getString("ProductName") %> | <%=rs.getInt("ProductNum") %> | <%=rs.getInt("ProductPrice") %> | --%>
    <%--            <input type="number" name="${rs.getString("ProductName")}" id="${rs.getString("ProductName")}"> <br>--%>
    <%--            <%} %>--%>
    <%--            --%>
    <%--        </span>--%>
    <table width="90%" align="center" cellpadding="0" cellspacing="0" border="1">
      <%--            <caption>商品列表</caption>--%>
      <tr>
        <td>商品ID</td>
        <td>商品名称</td>
        <td>商品价格</td>
        <td>商品剩余数量</td>
        <td>商品数量修改（正数为上架，负数为下架）</td>
      </tr>
      <%while (rs.next()) {
      %>
      <tr>
        <% String rs_name=rs.getString("ProductName"); %>
        <td><%=rs.getInt("ProductId") %>
        </td>
        <td><%=rs_name %>
        </td>
        <td><%=rs.getDouble("ProductPrice") %>
        </td>
        <td><%=rs.getInt("ProductNum") %>
        </td>
        <td>请输入添加数量<input type="number" name="<%=rs_name%>" id="<%=rs_name%>">
        </td>
      </tr>
      <%} %>
    </table> <br>
    <span id="msg" style="font-size: 12px;color: red">${messageModel.msg}</span> <br>       <!--msg为登录提示信息-->
    <button type="button" id="AddNumBtn">确认修改</button>
    <button type="button" id="AddNumCancelBtn" onclick="javascript:location.reload();">取消勾选</button>
  </form> <br> <br>

  <h1><%= "需要添加的商品信息" %>
  </h1>
  <form action="/product_Add" method="post" id="productAddForm">     <!-- action+ '/' 表示绝对路径，直接跟在localhost:8080后，  不加'/'为相对路径，访问需要站点名 -->
    商品名称：<input type="text" name="productNameAdd" id="productNameAdd" value="${messageModel1.object.productName}"> &nbsp;&nbsp;     <!--不同的 messageModel-->
    商品数量：<input type="number" name="productNumAdd" id="productNumAdd" value="${messageModel1.object.productNum}"> &nbsp;&nbsp;     <!--value用于回显-->
    商品价格：<input type="number" min="0.00" step="0.01" name="productPriceAdd" id="productPriceAdd" value="${messageModel1.object.productPrice}"> <br>     <!--value用于回显-->
    <span id="msg1" style="font-size: 12px;color: red">${messageModel1.msg}</span> <br>       <!--msg为登录提示信息-->
    <button type="button" id="AddBtn">确认添加</button>
    <button type="button" id="AddCancelBtn" onclick="javascript:location.reload();">取消添加</button>
  </form>

  <h1><%= "需要删除的商品信息" %>
  </h1>
  <form action="/product_Del" method="post" id="productDelForm">     <!-- action+ '/' 表示绝对路径，直接跟在localhost:8080后，  不加'/'为相对路径，访问需要站点名 -->
    商品名称：<input type="text" name="productNameDel" id="productNameDel" value="${messageModel2.object.productName}"> <br>     <!--value用于回显-->
    <span id="msg2" style="font-size: 12px;color: red">${messageModel2.msg}</span> <br>       <!--msg为登录提示信息-->
    <button type="button" id="DelBtn">确认删除</button>
    <button type="button" id="DelCancelBtn" onclick="javascript:location.reload();">取消删除</button>
  </form>

</div>

</body>
<%--引入JQuery的js文件--%>
<script type="text/javascript" src="js/jquery-3.5.1.js"></script>
<script type="text/javascript">
  // 商品购买
  // 后续在servlet检查 购买数量的合理性
  $("#AddNumBtn").click(function (){
    $("#productNumAddForm").submit();
  })

  // 商品添加
  $("#AddBtn").click(function (){
    $("#productAddForm").submit();
  })

  // 商品删除
  $("#DelBtn").click(function (){
    $("#productDelForm").submit();
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
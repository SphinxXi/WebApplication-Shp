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
//            out.print("数据库连接成功！");
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
    <h1><%= "可选购商品列表" %>
    </h1>
    <form action="/product_buy" method="post" id="productForm">     <!-- action+ '/' 表示绝对路径，直接跟在localhost:8080后，  不加'/'为相对路径，访问需要站点名 -->
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
                <td>商品名称</td>
                <td>商品价格</td>
                <td>商品剩余数量</td>
                <td>商品选购数量</td>
            </tr>
            <%while (rs.next()) {
            %>
            <tr>
                <% String rs_name=rs.getString("ProductName"); %>
                <td><%=rs_name %>
                </td>
                <td><%=rs.getDouble("ProductPrice") %>
                </td>
                <td><%=rs.getInt("ProductNum") %>
                </td>
                <td>请输入选购数量<input type="number" name="<%=rs_name%>" id="<%=rs_name%>">
                </td>
            </tr>
            <%} %></table> <br>
        <span id="msg" style="font-size: 12px;color: red">${messageModel.msg}</span> <br>       <!--msg为登录提示信息-->
        <button type="button" id="buyBtn">确认购买</button>
        <button type="button" id="reinBtn" onclick="javascript:location.reload();">刷新当前页面</button>
        <button type="button" id="anoBtn" onclick="window.location.href='product_update.jsp';">修改商品信息</button>
    </form>
</div>

</body>
<%--引入JQuery的js文件--%>
<script type="text/javascript" src="js/jquery-3.5.1.js"></script>
<script type="text/javascript">

    // 后续在servlet检查 购买数量的合理性
    $("#buyBtn").click(function (){
        $("#productForm").submit();
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
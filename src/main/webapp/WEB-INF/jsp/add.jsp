<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
    <title>员工列表</title>
    <%
        pageContext.setAttribute("APP_PATH", request.getContextPath());
    %>

    <script type="text/javascript" src="${APP_PATH}/statics/js/jquery-1.8.3.min.js"></script>
    <%--<link rel="stylesheet" href="../../statics/bootstrap-3.3.7-dist/css/bootstrap.min.css">--%>
    <%--<link rel="stylesheet" href="../../statics/bootstrap-3.3.7-dist/js/bootstrap.min.js">--%>
    <link rel="stylesheet" href="${APP_PATH}/statics/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/statics/bootstrap-3.3.7-dist/js/bootstrap.min.js">

</head>
<body>
<h1 style="text-align: center">添加员工信息 </h1>
<form action="/add1" method="post">
    <label for="empName">员工姓名</label><input id="empName" type="text" name="empName" ><br>
    <label for="email">邮箱</label><input id="email" type="text" name="email" ><br>
    <label for="gender">性别</label><input id="gender" type="text" name="gender" ><br>
    <label for="dId">部门</label><input id="dId" type="text" name="dId" ><br>

    <input type="submit" value="添加">

</form>

</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>员工列表</title>
<%
pageContext.setAttribute("APP_PATH",request.getContextPath());
%>

    <script type="text/javascript" src="${APP_PATH}/statics/js/jquery-1.8.3.min.js"></script>
    <%--<link rel="stylesheet" href="../../statics/bootstrap-3.3.7-dist/css/bootstrap.min.css">--%>
    <%--<link rel="stylesheet" href="../../statics/bootstrap-3.3.7-dist/js/bootstrap.min.js">--%>
    <link rel="stylesheet" href="${APP_PATH}/statics/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/statics/bootstrap-3.3.7-dist/js/bootstrap.min.js">

</head>
<body>
<%--搭建显示页面--%>
<div class="container ">
    <%--显示标题和删除，添加--%>
    <div class="row">
        <div class="col-md-12">
            <h1>SSM-CURD</h1>
        </div>
    </div>
    <div class="row">
        <div class="col-md-4 col-md-offset-8">
            <button class="btn btn-primary" id="emp_add_modal_btn">新增</button>
            <button class="btn btn-danger" id="emp_delete_all_btn">删除</button>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover" id="emps table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>empName</th>
                        <th>gender</th>
                        <th>email</th>
                        <th>deptName</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${pageInfo.list}" var="emp">
                        <tr>
                            <th>${emp.empId}</th>
                            <th>${emp.empName}</th>
                            <th>${emp.gender=="M"?"男":"女"}</th>
                            <th>${emp.email}</th>
                            <th>${emp.department.deptName}</th>
                            <th>
                                <button class="btn btn-primary btn-sm">
                                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                    编辑
                                </button>
                                <button  class="btn btn-danger btn-sm" >
                                    <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                    删除
                                </button>
                            </th>
                        </tr>
                    </c:forEach>


                </tbody>
            </table>
        </div>
    </div>
        <%--显示分页信息--%>
    <div class="row">
        <div class="col-md-6">
            当前第${pageInfo.pageNum}页/总${pageInfo.pages}页码，共（${pageInfo.total}）条记录；
        </div>
        <div class="col-md-6">
            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <li><a href="/emps?pn=1">首页</a></li>
                    <c:if test="${1 !=pageInfo.pageNum}">
                        <li>
                            <a href="${APP_PATH}/emps?pn=${pageInfo.pageNum-1}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                    </c:if>

                    <c:forEach items="${pageInfo.navigatepageNums}" var="page_Num">
                        <c:if test="${page_Num ==pageInfo.pageNum}">
                            <li class="active"><a href="#">${page_Num}</a></li>
                        </c:if>
                        <c:if test="${page_Num !=pageInfo.pageNum}">
                            <li ><a href="/emps?pn=${page_Num}">${page_Num}</a></li>
                        </c:if>

                    </c:forEach>
                    <c:if test="${pageInfo.pages != pageInfo.pageNum}">
                        <li>
                            <a href="${APP_PATH}/emps?pn=${pageInfo.pageNum+1}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </c:if>
                    <li><a href="/emps?pn=${pageInfo.pages}">末页</a></li>

                </ul>
            </nav>
        </div>
    </div>
</div>

</body>
</html>

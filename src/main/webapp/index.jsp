<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>员工列表</title>
    <%
        pageContext.setAttribute("APP_PATH", request.getContextPath());
    %>
    <script type="text/javascript" src="${APP_PATH}/statics/js/jquery.min.js"></script>
    <link rel="stylesheet" href="${APP_PATH}/statics/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <script type="text/javascript" src="statics/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

</head>
<body>


<!-- 员工添加模态框 -->
<div class="modal fade" id="empAndModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">empName</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="empName" id="empName_add_input"
                                   placeholder="empName">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">email</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="email_add_input" name="email"
                                   placeholder="email:123@qq.com">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">gender</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender1_add_input" value="M" checked="checked"> 男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender2_add_input" value="F"> 女
                            </label>
                        </div>

                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">department</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="dept_add_select" name="dId">
                                <%-- <option>1</option>
                                 <option>2</option>
                                 <option>3</option>
                                 <option>4</option>
                                 <option>5</option>--%>
                            </select>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="emp_save_btn">保存</button>
            </div>
        </div>
    </div>
</div>
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
            <table class="table table-hover" id="emps_table">
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

                </tbody>
            </table>
        </div>
    </div>
    <%--显示分页信息--%>
    <div class="row">
        <%--显示分页的信息--%>
        <div class="col-md-6" id="page_info_area">

        </div>
        <%--显示分页条--%>
        <div class="col-md-6" id="page_nav_area">

        </div>
    </div>
</div>


<script type="text/javascript">
    var totalRecord, currentPage;
    //页面加载完成以后，直接发出一个ajxa请求得到数据
    $(function () {
        to_page(1);
    });

    //把用页码显示全部信息
    function to_page(pn) {
        $.ajax({
            url: "/emps",
            data: "pn=" + pn,
            type: "GET",
            success: function (result) {
                //console.log(result);
                //1、解析并显示员工信息
                build_emps_table(result);
                //2、解析显示分页信息
                build_page_info(result);
                //3、解析并显示分页条信息
                build_page_nav(result);
            }
        });
    }

    //1、解析并显示员工信息
    function build_emps_table(result) {
        $("#emps_table tbody").empty();
        var emps = result.extend.pageInfo.list;
        $.each(emps, function (index, item) {
            var empIdTd = $("<td></td>").append(item.empId);
            var empNameTd = $("<td></td>").append(item.empName);
            var genderTd = $("<td></td>").append(item.gender == "M" ? "男" : "女");
            var emailTd = $("<td></td>").append(item.email);
            var deptNameTd = $("<td></td>").append(item.department.deptName);
            var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm")
                .append("<span></span>").addClass("glyphicon glyphicon-pencil").append("编辑");
            var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm")
                .append("<span></span>").addClass("glyphicon glyphicon-trash").append("删除");
            var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn);
            $("<tr></tr>").append(empIdTd).append(empNameTd)
                .append(genderTd).append(emailTd).append(deptNameTd).append(btnTd)
                .appendTo("#emps_table tbody");
        });
    }

    //2、解析显示分页信息
    function build_page_info(result) {
        $("#page_info_area").empty();
        $("#page_info_area").append("当前第" + result.extend.pageInfo.pageNum + "页/共" + result.extend.pageInfo.pages + "页码，" +
            "共（" + result.extend.pageInfo.total + "）条记录；");
        totalRecord = result.extend.pageInfo.total;
        currentPage = result.extend.pageInfo.pageNum;

    }

    //3、解析显示分页条信息
    function build_page_nav(result) {
        $("#page_nav_area").empty();
        var ul = $("<ul></ul>").addClass("pagination");

        var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href", "#"));
        var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));
        if (result.extend.pageInfo.hasPreviousPage == false) {
            firstPageLi.addClass("disabled");
            prePageLi.addClass("disabled");
        } else {
            firstPageLi.click(function () {
                to_page(1);
            });
            prePageLi.click(function () {
                to_page(currentPage - 1);
            });
        }
        var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
        var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href", "#"));
        if (result.extend.pageInfo.hasNextPage == false) {
            nextPageLi.addClass("disabled");
            lastPageLi.addClass("disabled");
        } else {
            nextPageLi.click(function () {
                to_page(currentPage + 1);
            });
            lastPageLi.click(function () {
                to_page(totalRecord);
            });
        }
        ul.append(firstPageLi).append(prePageLi);
        //添加1、2、3遍历ul中添加页码
        $.each(result.extend.pageInfo.navigatepageNums, function (index, item) {

            var numLi = $("<li></li>").append($("<a></a>").append(item));
            if (result.extend.pageInfo.pageNum == item) {
                numLi.addClass("active");
            }
            numLi.click(function () {
                to_page(item);
            });
            ul.append(numLi);
        });
        ul.append(nextPageLi).append(lastPageLi);

        var navEml = $("<nav></nav>").append(ul);

        $(navEml).appendTo("#page_nav_area");
        //$("#page_nav_area").append(navEml);一样

    }
    //清空表单样式及内容
    function reset_form(ele){
        $(ele)[0].reset();
        $(ele).find("*").removeClass("has-error has-success");
        $(ele).find(".help-block").text("");

    }
    //点击新增按钮弹出模态框
    $("#emp_add_modal_btn").click(function () {
        //清除表单数据（表单完整重置（表单的数据，表单的样式））
         reset_form("#empAndModal form");
        //$("#empAndModal form")[0].reset();
        //发送ajax请求，查出部门信息，显示在下拉列表中
        getDepts();
        //弹出模态框
        $("#empAndModal").modal({
            backdrop: "static"
        });
    });

    //查出所有的部门信息并显示在下拉列表中
    function getDepts() {

        $.ajax({
            url: "${APP_PATH}/depts",
            type: "GET",
            success: function (result) {
                //console.log(result);
                /* {"code":100,"msg":"处理成功！","extend":
                     {"depts":
                         [{"deptId":1,"deptName":"開發部門"},
                             {"deptId":2,"deptName":"测试部"}]}}*/
                result.extend.depts
                $.each(result.extend.depts, function () {
                    var optionEle = $("<option></option>").append(this.deptName).attr("value", this.deptId);
                    optionEle.appendTo("#dept_add_select");
                })

            }
        });

    }

    //校验方法
    function validate_add_form() {
        //校验名字和邮箱
        var empName = $("#empName_add_input").val();
        var regName = /(^[a-zA-Z0-9_-]{6,16}$)|([\u2E80-\u9FFF]{2,5})/;
        if (!regName.test(empName)) {
            show_validate_msg("#empName_add_input", "error", "用户名应为6-16位英文和数字的组合或者2-5位中文");
            return false;
        } else {
            show_validate_msg("#empName_add_input", "success", "");
        }
        var email = $("#email_add_input").val();
        var regeEail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        if (!regeEail.test(email)) {
            alert("邮箱格式不正确");
            show_validate_msg("#email_add_input", "error", "邮箱格式不正确");
            return false;
        } else {
            show_validate_msg("#email_add_input", "success", "");
        }
        return true;
    }

    //写一个公用的校验方法
    function show_validate_msg(ele, status, msg) {
        $(ele).parent().removeClass("has-success has-error");
        $(ele).next("span").text("");
        if (status == "success") {
            $(ele).parent().addClass("has-success");
        } else {
            $(ele).parent().addClass("has-error");
            $(ele).next("span").text(msg);
        }
    }


    //校验用户名是否可用
    $("#empName_add_input").change(function () {
        //发送ajax请求校验用户名是否可用
        var empName = this.value;
        $.ajax({
            url:"${APP_PATH}/checkuser",
            data:"empName="+empName,
            type:"POST",
            success:function (result) {
                if(result.code == 100){
                    show_validate_msg("#empName_add_input","success","用户名可用");
                    $("#emp_save_btn").attr("ajax-va","success");
                }else{
                    show_validate_msg("#empName_add_input","error",result.extend.va_msg);
                    $("#emp_save_btn").attr("ajax-va","error")
                }
            }

        });
    });

    //保存员工
    $("#emp_save_btn").click(function () {
        //1、模态框中填写的表单数据进行保存
        //1、先校验，数据正确才提交
        if (!validate_add_form()) {
            return false;
        }
        if ($(this).attr("ajax-va") == "error") {
            return false;
        }
        //2、发送ajax请求保存员工
        $.ajax({
            url: "emp",
            type: "POST",
            data: $("#empAndModal form").serialize(),
            success: function (result) {
                if(result.code==100){
                    //1\关闭模态框
                    $("#empAndModal").modal("hide");
                    //2、来到最后一页，显示刚才保存的数据
                    //发送ajax跳到最后一页
                    to_page(totalRecord);
                }else{
                    //console.log(result);
                    if(undefined != result.extend.errorFields.email){
                        //在页面显示后台传过来的错误邮箱信息
                        show_validate_msg("#email_add_input","error",result.extend.errorFields.email);
                    }
                    if(undefined != result.extend.errorFields.empName){
                        //在页面显示后台传过来的错误邮箱信息
                        show_validate_msg("#empName_add_input","error",result.extend.errorFields.empName);
                    }
                }

            }
        })

    });


</script>

</body>
</html>

















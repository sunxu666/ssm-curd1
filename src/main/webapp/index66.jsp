<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="${APP_PATH}/statics/js/jquery-1.8.3.min.js"></script>

<c:import url="index66.jsp">

</c:import>

<script type="text/javascript">
    //页面加载完成以后，直接发出一个ajxa请求得到数据
    $(function(){
        $.ajax({
           url:"/emps",
           data:"pn=1",
           type:"GET",
           success:function (result) {
               //console.log(result);
               //1、解析并显示员工信息
               build_emps_table(result);
               //2、解析显示页面信息
           }
        });
    });
    //1、解析并显示员工信息
    function build_emps_table(result){
        var emps = result.extend.pageInfo.list;
        $.each(emps,function (index,item) {
           alert(item.empName);
        });
    }
    //2、解析显示页面信息
    function build_page_nav(result){

    }

</script>

















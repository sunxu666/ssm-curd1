<%@page import="com.ssm.util.TmDownImgUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>下载页面</title>
    <script type="text/javascript" src="statics/js/jquery-1.8.3.min.js"></script>
</head>
<body>
<%
//获取url
    String url = request.getParameter("url");
    String html = TmDownImgUtil.htmlSource(url,"utf-8");
    pageContext.setAttribute("htmlsource",html);
%>
<div id="box" style="width:1120px;margin: 0 auto;text-align: center; ">
    <h1>实现图片下载</h1>
    <textarea id="source" cols="30" rows="10"
              style="width:1120px;height:460px;overflow: auto; ">${htmlsource}</textarea>

</div>

<hr>
<form action="download.jsp" method="post" id="form">
    <input type="submit" value="下载" style="width: 180px;height: 35px;">
</form>
<hr>
<h3>您一共找到符合需求的图片有：<span id="count"></span>张</h3>
<script type="text/javascript">
    $(function () {
        var source = $("#source").val();
        var $source = $(source);
        //第二部;获取内容里面所有的图片
        var i = 0;
        $source.find("img").each(function () {
            var src = $(this).attr("src");
            if(src!=""&&src.length>0/*&&src.indexOf("jpg")!=-1*/){
                $("body").append("<div style='float: left;margin: 5px;'><img src='"+src+"' width='180' height='180'></div>")
                $("#from").prepend("<input type='hidden' name='img' value='"+src+"' >");
                i++;

            }

        });

        $("#count").text(i);

    });
</script>


</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>图片下载</title>
</head>
<body>
<div>
    <img src="#" alt="图片首页">
    <h1>梦想集群*下载图片</h1>
    <form action="source.jsp" method="post" onsubmit="return validator(); ">
        <input type="text" placeholder="请输入URL：ru;http://www.qq.com" name="url" id="url">
        <input type="submit" value="获取网页的代码">
    </form>
</div>

<script type="text/javascript">

    function validator() {
        //判断网址的合法性
        //获取到输入框里面的值
        var url = document.getElementById("url").value;
        alert(url);
        //判断是不是空值
        if(url==""||url.length==0){
            alert("请输入url")
            //获取焦点
            document.getElementById("url").focus();
            return false;
        }

        if(url!=""&&url.indexof("http://")==-1){
            alert("请输入正确的url")
            //获取焦点  用户体验，减少用户操作 别让我等，别让我想，别让我烦
            document.getElementById("url").focus();
            return false;
        }
        return true;
    }


</script>

</body>
</html>
















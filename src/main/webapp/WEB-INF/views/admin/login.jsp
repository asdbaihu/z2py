<%--
  Created by Mr.Xu.
  Date: 2017/4/8
  Time: 11:39
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>最爱片源网 - 后台登录</title>
    <head><jsp:include page="../common/include.jsp" />
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/zui.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/zui-theme.css">
    <script src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath }/js/zui.min.js"></script>
    <style>
        body {
            background: url("${pageContext.request.contextPath }/img/admin-login-bg.jpg");
            color: #fff;
        }
        header{
            padding-top: 80px;
        }
        .login-box {
            width:400px;
            height:300px;
            position:absolute;
            left:50%;
            top:50%;
            margin:-150px 0 0 -200px;
            padding: 60px;
            filter:alpha(Opacity=30);
            -moz-opacity:0.3;
            opacity: 0.3;
            background:#000;
            color: #fff;
            border-radius: 8px;
        }
        .login-box input {
            background: transparent;
            color: #fff;
        }
    </style>
</head>
<body>

    <div class="login-box">
        <form action="" method="post">
            <div class="form-group">
                <label for="username">用户名</label>
                <input type="text" class="form-control" name="username" id="username" placeholder="电子邮件/手机号/用户名">
            </div>
            <div class="form-group">
                <label for="password">密码</label>
                <input type="password" class="form-control" name="password" id="password" placeholder="">
            </div>
            <button type="submit" class="btn btn-primary">登录</button>
        </form>
    </div>
</body>
</html>

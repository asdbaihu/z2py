<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <head>
        <jsp:include page="../common/include.jsp"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath }/css/zui.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath }/css/zui-theme.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath }/css/admin/style.css">
        <script src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath }/js/zui.min.js"></script>
        <title>影视列表</title>
    </head>
<body>
<jsp:include page="common/header.jsp"></jsp:include>
<jsp:include page="common/slidebar.jsp"></jsp:include>
<jsp:include page="view/movie-list.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/admin/admin.js"></script>
<script src="${pageContext.request.contextPath }/js/admin/movie-list.js"></script>
</body>
</html>
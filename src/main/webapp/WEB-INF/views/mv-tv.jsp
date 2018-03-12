<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="common/include.jsp"/>
	<meta name="keywords" content="最爱片源网电影列表"/>
	<meta name="description" content="最爱片源,电影下载,美剧下载,韩剧下载,片源下载,高清电影,BT下载"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/zui.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/zui-theme.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/app.css">
	<script src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath }/js/zui.min.js"></script>
	<title>最爱片源网电影列表 - 高清片源 | 蓝光片源 | 原盘片源 | 最新片源 - 海量资源应有尽有</title>
</head>
<body>
<jsp:include page="common/header.jsp"></jsp:include>
<div class="main-content">
	<jsp:include page="common/opt-filters.jsp"></jsp:include>
	<div class="container">
		<div class="col-md-10 cards">
			<c:forEach items="${pageInfo.list }" var="movie">
				<div class="col-md-2 col-sm-2 col-xs-2">
					<div class="card">
						<span class="score">${movie.m_score }分</span>
						<a class="preview pic" href="${pageContext.request.contextPath }/movie/${movie.m_id }" target="_blank" title="${movie.m_name }"><img src="/poster${movie.m_poster }"/></a>
						<p class="text-ellipsis"><a class="text-info" href="${pageContext.request.contextPath }/movie/${movie.m_id }" title="${movie.m_name }">${movie.m_name }</a></p>
						<p class="text-ellipsis" title="${movie.m_type }">类型：${movie.m_type }</p>
					</div>
				</div>
			</c:forEach>
			<c:set var="halfPage" value="3"/>
			<c:set var="beginPage"
						 value="${pageInfo.pageNum-halfPage>0?(pageInfo.pageNum+halfPage<=pageInfo.pages?pageInfo.pageNum-halfPage:pageInfo.pageNum-halfPage-(halfPage - (pageInfo.pages-pageInfo.pageNum))):1 }"/>
			<ul class="pager pull-right">
				<c:if test="${pageInfo.pageNum - halfPage > 0 }">
					<li><a href="${pageContext.request.contextPath }/mv/page/1${filter }">首页</a></li>
				</c:if>
				<c:if test="${pageInfo.hasPreviousPage }">
					<li class="previous"><a
									href="${pageContext.request.contextPath }/mv/page/${pageInfo.pageNum-1 }${filter }">«</a>
					</li>
				</c:if>
				<c:forEach begin="${beginPage }" end="${beginPage+halfPage*2 }" var="p">
					<c:if test="${p<=pageInfo.pages }">
						<li ${pageInfo.pageNum==p?'class="active"':'' }><a
										<c:if test="${pageInfo.pageNum!=p}">href="${pageContext.request.contextPath }/mv/page/${p }${filter }"</c:if>>${p }</a>
						</li>
					</c:if>
				</c:forEach>
				<c:if test="${pageInfo.hasNextPage }">
					<li class="next"><a
									href="${pageContext.request.contextPath }/mv/page/${pageInfo.pageNum+1 }${filter }">»</a>
					</li>
				</c:if>
				<c:if test="${pageInfo.pageNum + halfPage < pageInfo.pages }">
					<li><a href="${pageContext.request.contextPath }/mv/page/${pageInfo.pages }${filter }">尾页</a></li>
				</c:if>
			</ul>
		</div>
	</div>
</div>
<jsp:include page="common/footer.jsp"></jsp:include>
<script src="${pageContext.request.contextPath }/js/imgPreview.js"></script>
<script type="text/javascript">imagePreview();</script>
</body>
</html>
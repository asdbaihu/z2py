<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://www.inspuruptec.com/jsp/jstl/util" %>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="common/include.jsp"/>
	<meta name="keywords" content="最爱片源,电影下载,美剧下载,韩剧下载,片源下载,高清电影"/>
	<meta name="description" content="最爱片源,电影下载,美剧下载,韩剧下载,片源下载,高清电影,BT下载"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/zui.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/zui.datatable.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/zui-theme.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/app.css">
	<script src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath }/js/zui.min.js"></script>
	<title>最爱片源网 - 高清片源 | 蓝光片源 | 原盘片源 | 最新片源 - 海量资源应有尽有</title>
</head>
<body>
<jsp:include page="common/header.jsp"></jsp:include>
<div class="main-content">
	<jsp:include page="common/opt-filters.jsp"></jsp:include>
	<div class="container recent-box">
		<ul class="pic-list nobr">
			<c:forEach items="${recommendMovies}" var="movie">
				<li><a href="${pageContext.request.contextPath}/movie/${movie.m_id}" title="${movie.m_name}" target="_blank"><img src="/poster${movie.m_poster}"/></a></li>
			</c:forEach>
		</ul>
		<table class="table datatable table-condensed table-striped table-fixed table-responsive table-hover">
			<thead>
			<tr>
				<th>资源名称</th>
				<th class="sort-disabled">大小</th>
				<th>时间</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${pageInfo.list }" var="item">
				<tr>
					<td class="nobr">
						<a class="pull-left preview" href="${pageContext.request.contextPath }/movie/${item.m_id }" target="_blank"><img width="32px" height="45px" src="/poster${item.m_poster }"></a>
						<a data-toggle="tooltip" data-tip-class="tooltip" class="tb-icon tb-icon-bt" href="${pageContext.request.contextPath }/resource/${item.r_id }" target="_blank" title="${item.r_name }">${item.r_name }</a>
						<span class="text-ellipsis">《${item.m_name }》 ${item.m_performer!=null?'主演：':'' }${item.m_performer }</span>
					</td>
					<td>${item.r_size }</td>
					<td>${fn:getTimeFormatText(item.r_time) }</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<c:set var="halfPage" value="3"/>
		<c:set var="beginPage"
					 value="${pageInfo.pageNum-halfPage>0?(pageInfo.pageNum+halfPage<=pageInfo.pages?pageInfo.pageNum-halfPage:pageInfo.pageNum-halfPage-(halfPage - (pageInfo.pages-pageInfo.pageNum))):1 }"/>
		<ul class="pager pull-right">
			<c:if test="${pageInfo.pageNum - halfPage > 0 }">
				<li><a href="${pageContext.request.contextPath }/page/1${filter }">首页</a></li>
			</c:if>
			<c:if test="${pageInfo.hasPreviousPage }">
				<li class="previous"><a
								href="${pageContext.request.contextPath }/page/${pageInfo.pageNum-1 }${filter }">«</a></li>
			</c:if>
			<c:forEach begin="${beginPage }" end="${beginPage+halfPage*2 }" var="p">
				<c:if test="${p<=pageInfo.pages }">
					<li ${pageInfo.pageNum==p?'class="active"':'' }><a
									<c:if test="${pageInfo.pageNum!=p}">href="${pageContext.request.contextPath }/page/${p }${filter }"</c:if>>${p }</a>
					</li>
				</c:if>
			</c:forEach>
			<c:if test="${pageInfo.hasNextPage }">
				<li class="next"><a
								href="${pageContext.request.contextPath }/page/${pageInfo.pageNum+1 }${filter }">»</a></li>
			</c:if>
			<c:if test="${pageInfo.pageNum + halfPage < pageInfo.pages }">
				<li><a href="${pageContext.request.contextPath }/page/${pageInfo.pages }${filter }">尾页</a></li>
			</c:if>
		</ul>
	</div>
</div>
<jsp:include page="common/footer.jsp"></jsp:include>
<script src="${pageContext.request.contextPath }/js/zui.datatable.min.js"></script>
<script src="${pageContext.request.contextPath }/js/imgPreview.js"></script>
<script src="${pageContext.request.contextPath }/js/index.js"></script>
</body>
</html>
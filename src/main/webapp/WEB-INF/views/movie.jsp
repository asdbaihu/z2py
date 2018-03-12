<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="mfn" uri="http://www.inspuruptec.com/jsp/jstl/util" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="common/include.jsp"/>
	<meta name="keywords" content="${movie.m_name } 高清磁力下载"/>
	<meta property="og:image" content="/poster${movie.m_poster }" />
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/zui.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/zui-theme.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/zui.datatable.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/app.css">
	<script src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath }/js/zui.min.js"></script>
	<script src="${pageContext.request.contextPath }/js/zui.datatable.min.js"></script>
	<title>${movie.m_name } 资源列表 - 最爱片源网</title>
</head>
<body>
<jsp:include page="common/header.jsp"></jsp:include>
<div class="main-content">
	<div class="movie-top">
		<div class="container">
			<h1>${movie.m_name }
				<c:if test="${movie.m_total_episode>0 }">
					<span class="label label-badge label-info">总集数(${movie.m_total_episode })</span>
				</c:if>
				<span class="label label-info">资源数(${totalResources })</span>
			</h1>
		</div>
	</div>
	<div class="container clearfix movie-detail">
		<div class="col-md-2 col-sm-4 col-xs-4">
			<a href="${pageContext.request.contextPath }/movie/${movie.m_id }" title="${movie.m_name }"><img
							alt="${movie.m_name }" src="/poster${movie.m_poster }"></a>
		</div>
		<ul class="col-md-6 col-sm-8 col-xs-8">
			<c:if test="${movie.m_director!=null && movie.m_director!='' }">
				<li><strong>导演：</strong><c:forEach items="${fn:split(fn:trim(movie.m_director),',') }" var="director"><a data-toggle="tooltip" data-tip-class="tooltip" href="${pageContext.request.contextPath }/search/${mfn:urlEncode(director) }" title="${director }">${director }</a></c:forEach></li>
			</c:if>
			<c:if test="${movie.m_scenarist!=null && movie.m_scenarist!='' }">
				<li><strong>编剧：</strong><c:forEach items="${fn:split(fn:trim(movie.m_scenarist),',') }" var="scenarist"><a data-toggle="tooltip" data-tip-class="tooltip" href="${pageContext.request.contextPath }/search/${mfn:urlEncode(scenarist) }" title="${scenarist }">${scenarist }</a> </c:forEach></li>
			</c:if>
			<c:if test="${movie.m_performer!=null && movie.m_performer!='' }">
				<li><strong>主演：</strong><c:forEach items="${fn:split(fn:trim(movie.m_performer),',') }" var="performer"><a data-toggle="tooltip" data-tip-class="tooltip" href="${pageContext.request.contextPath }/search/${mfn:urlEncode(performer) }" title="${performer }">${performer }</a> </c:forEach></li>
			</c:if>
			<c:if test="${movie.m_type!=null && movie.m_type!='' }">
				<li><strong>类型：</strong><span>${fn:trim(movie.m_type) }</span></li>
			</c:if>
			<c:if test="${movie.m_area!=null && movie.m_area!='' }">
				<li><strong>地区：</strong><span>${fn:trim(movie.m_area) }</span></li>
			</c:if>
			<c:if test="${movie.m_alias!=null && movie.m_alias!='' }">
				<li><strong>又名：</strong><span>${fn:trim(movie.m_alias) }</span></li>
			</c:if>
			<c:if test="${movie.m_imdb!=null && movie.m_imdb!='' }">
				<li><strong>imdb：</strong><span><a href="//www.imdb.com/title/${movie.m_imdb }" target="_blank" rel="nofollow">${movie.m_imdb }</a></span></li>
			</c:if>
			<c:if test="${movie.m_douban!=null && movie.m_douban!='' }">
				<li><strong>豆瓣：</strong><span><a class="icon icon-external-link" href="//movie.douban.com/subject/${movie.m_douban }" target="_blank" rel="nofollow"></a></span></li>
			</c:if>
		</ul>
		<div class="col-md-2 visible-md visible-lg">
			<strong>评分：${movie.m_score }</strong>
		</div>
	</div>
	<div class="container resource-list">
		<c:forEach items="${resources }" var="entry">
			<ul class="nav nav-tabs">
				<li class="active"><a data-tab><Strong class="text-info">${entry.key }</Strong></a></li>
			</ul>
			<div class="tab-content torrent-treeview">
				<table class="table datatable table-condensed table-striped table-fixed table-responsive table-hover table-resource-relative">
					<thead>
					<tr class="active">
						<th>资源名</th>
						<th class="sort-disabled">大小</th>
						<th class="sort-disabled visible-md visible-lg">时间</th>
					</tr>
					</thead>
					<tbody class="text-nowrap">
					<c:forEach items="${entry.value }" var="resource">
						<tr>
							<td class="text-ellipsis"><a data-toggle="tooltip" data-tip-class="tooltip" href="${pageContext.request.contextPath }/resource/${resource.r_id }" title="${resource.r_name }">${resource.r_name }<span class="text-success visible-sm visible-xs inline pull-right">(${resource.r_size})</span></a></td>
							<td class="visible-md visible-lg text-ellipsis">${resource.r_size }</td>
							<td class="visible-md visible-lg"><fmt:formatDate value="${resource.r_time }" pattern="yyyy-MM-dd"/></td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</c:forEach>
	</div>
</div>
<jsp:include page="common/footer.jsp"></jsp:include>
<script>$('table.datatable').datatable({sortable: true});
$('[data-toggle="tooltip"]').tooltip();</script>
</body>
</html>
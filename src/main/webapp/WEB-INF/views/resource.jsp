<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mfn" uri="http://www.inspuruptec.com/jsp/jstl/util" %>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="common/include.jsp"/>
	<meta name="keywords" content="${movieAndResource.m_name },${movieAndResource.r_name }"/>
	<meta name="description" content="${movieAndResource.m_name } - ${movieAndResource.r_name } 磁力链接,Torrent下载"/>
	<meta property="og:image" content="/poster${movieAndResource.m_poster }" />
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/zui.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/zui-theme.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/app.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/jquery.treeview.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/canvas/component.css"/>
	<script src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath }/js/zui.min.js"></script>
	<title>${movieAndResource.m_name } - ${movieAndResource.r_name } 下载 - 最爱片源网</title>
</head>
<body>
<jsp:include page="common/header.jsp"></jsp:include>
<div class="main-content">
	<div class="container-fluid resource-top">
		<canvas id="rtop-canvas"></canvas>
		<div class="container clearfix resource-top-body">
			<!-- resource-top left -->
			<div class="col-md-2 nopl">
				<div class="visible-md visible-lg movie-info">
					<a href="${pageContext.request.contextPath }/movie/${movieAndResource.m_id }" title="${movieAndResource.r_name}"><img src="/poster${movieAndResource.m_poster }" alt="${movieAndResource.r_name}"/></a>
					<ul class="detail">
						<c:if test="${movieAndResource.m_director!=null && movieAndResource.m_director!='' }">
							<li><strong>导演：</strong><span>${movieAndResource.m_director }</span></li>
						</c:if>
						<c:if test="${movieAndResource.m_scenarist!=null && movieAndResource.m_scenarist!='' }">
							<li><strong>编剧：</strong><span>${movieAndResource.m_scenarist }</span></li>
						</c:if>
						<c:if test="${movieAndResource.m_performer!=null && movieAndResource.m_performer!='' }">
							<li><strong>主演：</strong><span>${movieAndResource.m_performer }</span></li>
						</c:if>
						<c:if test="${movieAndResource.m_type!=null && movieAndResource.m_type!='' }">
							<li><strong>类型：</strong><span>${movieAndResource.m_type }</span></li>
						</c:if>
						<c:if test="${movieAndResource.m_area!=null && movieAndResource.m_area!='' }">
							<li><strong>地区：</strong><span>${movieAndResource.m_area }</span></li>
						</c:if>
						<c:if test="${movieAndResource.m_alias!=null && movieAndResource.m_alias!='' }">
							<li><strong>又名：</strong><span>${movieAndResource.m_alias }</span></li>
						</c:if>
						<c:if test="${movieAndResource.m_douban!=null && movieAndResource.m_douban!='' }">
							<li><strong>豆瓣：</strong><span><a class="icon icon-external-link" href="//movie.douban.com/subject/${movieAndResource.m_douban }"></a></span></li>
						</c:if>
					</ul>
				</div>
			</div>
			<!-- resource-top right -->
			<div class="col-md-10 resource-top-body-right">
				<h1>${movieAndResource.r_name }</h1>
				<h2><a data-toggle="tooltip" data-tip-class="tooltip-info" href="${pageContext.request.contextPath }/movie/${movieAndResource.m_id }" title="${movieAndResource.m_name }">${movieAndResource.m_name }</a></h2>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="col-md-8 col-md-offset-2 resource-info">
			<div class="resource-info-header">
				<i class="icon icon-film"></i><span>${movieAndResource.r_quality }</span>
				<i class="icon icon-file"></i><span>${movieAndResource.r_size }</span>
				<i class="icon icon-time"></i><span><fmt:formatDate value="${movieAndResource.r_time }" pattern="yyyy年MM月dd日 hh时mm分ss秒"/></span>
			</div>
			<hr>
			<ul class="nav nav-tabs">
				<li class="active"><a data-tab href="#tabContent1">种子下载</a></li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active" id="tabContent1">
					<a class="btn btn-danger" type="button" href="/upload${movieAndResource.r_torrent }" rel="nofollow">种子文件</a>
					<a class="btn btn-success" type="button" href="${movieAndResource.r_magnet}" rel="nofollow">磁力链接</a>
					<a class="btn btn-warning" type="button" href="http://www.zimuku.net/search?q=${movieAndResource.m_imdb!=null?movieAndResource.m_imdb:mfn:urlEncode(movieAndResource.m_name)}" target="_blank" rel="nofollow">字幕下载</a>
				</div>
			</div>
			<ul class="nav nav-tabs">
				<li class="active"><a data-tab>文件信息</a></li>
			</ul>
			<div class="tab-content torrent-info">
				${treeview }
			</div>
			<c:if test="${relativePage.total>0 }">
				<ul class="nav nav-tabs">
					<li class="active"><a data-tab>相关资源</a></li>
				</ul>
				<div class="tab-content torrent-info">
					<table class="table table-condensed table-fixed table-resource-relative">
						<thead>
						<tr class="active">
							<th>资源名</th>
							<th class="visible-md visible-lg">画质</th>
							<th class="visible-md visible-lg">大小</th>
						</tr>
						</thead>
						<tbody class="text-nowrap">
						<c:forEach items="${relativePage.list }" var="resource">
							<tr>
								<td class="text-ellipsis"><a data-toggle="tooltip" data-tip-class="tooltip" href="${pageContext.request.contextPath }/resource/${resource.r_id }" title="${resource.r_name }">${resource.r_name }<span class="text-success visible-sm visible-xs inline pull-right">(${resource.r_size})</span></a></td>
								<td class="visible-md visible-lg text-ellipsis">${resource.r_quality }</td>
								<td class="visible-md visible-lg">${resource.r_size }</td>
							</tr>
						</c:forEach>
						<c:if test="${!relativePage.isLastPage }">
							<tr>
								<td class="text-center" colspan="3"><a data-toggle="tooltip" data-tip-class="tooltip" class="resource-more" href="${pageContext.request.contextPath }/movie/${movieAndResource.m_id }" title="${movieAndResource.m_name }">还有(${relativePage.total - relativePage.pageSize })个资源，点击查看</a>
								</td>
							</tr>
						</c:if>
						</tbody>
					</table>
				</div>
			</c:if>
		</div>
	</div>
</div>
<jsp:include page="common/footer.jsp"></jsp:include>
<script src="${pageContext.request.contextPath }/js/jquery.treeview.js"></script>
<script src="${pageContext.request.contextPath }/js/canvas/rAF.js"></script>
<script src="${pageContext.request.contextPath }/js/canvas/r_canvas.js"></script>
<script type="text/javascript">
    $('.treeview').treeview();
    $('[data-toggle="tooltip"]').tooltip();
</script>
</body>
</html>
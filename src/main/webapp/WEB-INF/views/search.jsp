<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="mfn" uri="http://www.inspuruptec.com/jsp/jstl/util" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="common/include.jsp"/>
	<meta name="keywords" content="${keyword } 全部资源,BT下载,磁力链接"/>
	<meta name="description" content="最爱片源,电影下载,美剧下载,韩剧下载,片源下载,高清电影,BT下载"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/zui.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/zui-theme.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/app.css">
	<script src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath }/js/zui.min.js"></script>
	<title>${keyword } 搜索结果 - 最爱片源网</title>
	<c:set var="hlkeyword" value='<strong class="text-danger">${keyword }</strong>'></c:set>
</head>
<body>
<jsp:include page="common/header.jsp"></jsp:include>
<div class="main-content search-result">
	<c:if test="${pageInfo.total==0 }">
		<div class="container alert alert-info with-icon">
			<i class="icon-frown"></i>
			<div class="content">抱歉！
				<hr/>
				关键词“${hlkeyword }”没有找到相关纪录！
			</div>
		</div>
	</c:if>
	<c:if test="${pageInfo.total!=0 }">
		<div class="container">
			<div class="alert alert-success with-icon">
				<i class="icon-ok-sign"></i>
				<div class="content">关键词“${hlkeyword }”共找到<strong class="text-danger">${pageInfo.total }</strong>条相关纪录！
				</div>
			</div>
		</div>
	</c:if>
	<c:if test="${type==0 }">
		<c:forEach items="${pageInfo.list }" var="movie">
			<div class="container clearfix movie-detail">
				<div class="col-md-2 col-sm-4 col-xs-4 search-list-title">
					<a href="${pageContext.request.contextPath }/movie/${movie.m_id }" title="${movie.m_name }"
						 target="_blank"><img alt="${movie.m_name }" src="/poster${movie.m_poster }"></a>
				</div>
				<ul class="col-md-10 col-sm-8 col-xs-8">
					<li><h2><a href="${pageContext.request.contextPath }/movie/${movie.m_id }" title="${movie.m_name }"
										 target="_blank">${fn:replace(movie.m_name, keyword, hlkeyword) }</a></h2></li>
					<c:if test="${movie.m_director!=null && movie.m_director!='' }">
						<li><strong>导演：</strong>${fn:replace(movie.m_director, keyword, hlkeyword) }</li>
					</c:if>
					<c:if test="${movie.m_scenarist!=null && movie.m_scenarist!='' }">
						<li><strong>编剧：</strong>${fn:replace(movie.m_scenarist, keyword, hlkeyword) }</li>
					</c:if>
					<c:if test="${movie.m_performer!=null && movie.m_performer!='' }">
						<li><strong>主演：</strong>${fn:replace(movie.m_performer, keyword, hlkeyword) }</li>
					</c:if>
					<c:if test="${movie.m_alias!=null && movie.m_alias!='' }">
						<li><strong>又名：</strong><span>${fn:replace(movie.m_alias, keyword, hlkeyword) }</span></li>
					</c:if>
					<li class="visible-md visible-lg">
						<table class="table table-fixed table-resource-relative table-search-relative">
							<thead>
							<tr class="active">
								<th>资源名</th>
								<th class="visible-md visible-lg">画质</th>
								<th class="visible-md visible-lg">大小</th>
							</tr>
							</thead>
							<tbody class="text-nowrap">
							<c:forEach items="${movie.resources }" var="resource">
								<tr>
									<td class="text-ellipsis"><a data-toggle="tooltip" data-tip-class="tooltip"
																							 href="${pageContext.request.contextPath }/resource/${resource.r_id }"
																							 title="${resource.r_name }"
																							 target="_blank">${resource.r_name }</a></td>
									<td class="visible-md visible-lg text-ellipsis">${resource.r_quality }</td>
									<td class="visible-md visible-lg">${resource.r_size }</td>
								</tr>
							</c:forEach>
							<tr>
								<td class="text-center" colspan="3"><a data-toggle="tooltip" data-tip-class="tooltip"
																											 class="resource-more"
																											 href="${pageContext.request.contextPath }/movie/${movie.m_id }"
																											 title="${movie.m_name }" target="_blank">更多资源，点击查看</a>
								</td>
							</tr>
							</tbody>
						</table>
					</li>
				</ul>
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${type==1 }">
		<div class="container">
			<table class="table datatable table-condensed table-striped table-fixed table-responsive table-hover">
				<thead>
				<tr>
					<th>资源名称</th>
					<th class="sort-disabled">画质</th>
					<th>时间</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${pageInfo.list }" var="resource">
					<tr>
						<td class="nobr">
							<a data-toggle="tooltip" data-tip-class="tooltip" class="tb-icon tb-icon-bt"
								 href="${pageContext.request.contextPath }/resource/${resource.r_id }" target="_blank"
								 title="${resource.r_name }">${fn:replace(resource.r_name, keyword, hlkeyword) }</a>
						</td>
						<td>${resource.r_quality }</td>
						<td><fmt:formatDate value="${resource.r_time }" pattern="yyyy-MM-dd"/></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
	<c:if test="${pageInfo.pages > 1}">
		<div class="container">
			<c:set var="halfPage" value="3"/>
			<c:set var="beginPage"
						 value="${pageInfo.pageNum-halfPage>0?(pageInfo.pageNum+halfPage<=pageInfo.pages?pageInfo.pageNum-halfPage:pageInfo.pageNum-halfPage-(halfPage - (pageInfo.pages-pageInfo.pageNum))):1 }"/>
			<ul class="pager pull-right">
				<c:if test="${pageInfo.pageNum - halfPage > 0 }">
					<li><a href="${pageContext.request.contextPath }/search/${keyword }?type=${type }&p=1">首页</a></li>
				</c:if>
				<c:if test="${pageInfo.hasPreviousPage }">
					<li class="previous"><a
									href="${pageContext.request.contextPath }/search/${keyword }?type=${type }&p=${pageInfo.pageNum-1 }">«</a>
					</li>
				</c:if>
				<c:forEach begin="${beginPage }" end="${beginPage+halfPage*2 }" var="p">
					<c:if test="${p<=pageInfo.pages }">
						<li ${pageInfo.pageNum==p?'class="active"':'' }><a
										<c:if test="${pageInfo.pageNum!=p}">href="${pageContext.request.contextPath }/search/${keyword }?type=${type }&p=${p }"</c:if>>${p }</a>
						</li>
					</c:if>
				</c:forEach>
				<c:if test="${pageInfo.hasNextPage }">
					<li class="next"><a
									href="${pageContext.request.contextPath }/search/${keyword }?type=${type }&p=${pageInfo.pageNum+1 }">»</a>
					</li>
				</c:if>
				<c:if test="${pageInfo.pageNum + halfPage < pageInfo.pages }">
					<li>
						<a href="${pageContext.request.contextPath }/search/${keyword }?type=${type }&p=${pageInfo.pages }">尾页</a>
					</li>
				</c:if>
			</ul>
		</div>
	</c:if>
</div>
<jsp:include page="common/footer.jsp"></jsp:include>
</body>
</html>
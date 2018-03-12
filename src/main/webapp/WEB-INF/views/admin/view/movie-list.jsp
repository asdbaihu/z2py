<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="main-content">
	<div class="wrapper">
		<ol class="breadcrumb">
			<li><i class="icon icon-home"></i> 主页</li>
			<li>影视管理</li>
			<li class="active">影视列表</li>
		</ol>
		<form action="">
			<div class="search">
				搜索名称：
					<input type="text" class="text" name="name" value="${filters.name }" placeholder="搜索名称" style="width:150px;">
				&nbsp;<button type="submit" value="查询" class="btn btn-primary-outline btn-xs"><i class="icon-search"></i>&nbsp;查询</button>
				&nbsp;
				<div class="btn-group" action="${pageContext.request.contextPath}/admin/recommend">
					<button type="button" value="1" class="btn btn-primary-outline btn-xs"><i class="icon-circle-arrow-up"></i>&nbsp;批量置顶</button>
					<button type="button" value="0" class="btn btn-primary-outline btn-xs"><i class="icon-circle-arrow-down"></i>&nbsp;批量取消</button>
				</div>
			</div>
			<div class="table-responsive">
				<table class="table">
					<thead>
						<tr>
							<th width=40><input type="checkbox" id="check-ids" /></th>
							<th width=50>ID</th>
							<th>影视名称</th>
							<th>类型</th>
							<th width=150>更新时间</th>
							<th width=240>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageInfo.list }" var="movie" varStatus="status">
						<tr>
							<td><input type="checkbox" name="m_id" value="${movie.m_id}" /></td>
							<td>${(pageInfo.pageNum-1)*pageInfo.pageSize+status.index + 1 }</td>
							<td class="name"><a href="${pageContext.request.contextPath }/admin/movie/${movie.m_id }">${movie.m_name }</a></td>
							<td>${movie.m_cat=='mv'?'电影':'剧集' }</td>
							<td><fmt:formatDate value="${movie.m_update_date }" pattern="yyyy-MM-dd hh:mm"/></td>
							<td>
								<a class="btn btn-success btn-sm" href="#"><i class="icon-edit"></i>&nbsp;修改</a>
								<a class="btn ${movie.m_recommend==0?'btn-warning':'btn-danger'} btn-sm recommend" action="${pageContext.request.contextPath}/admin/recommend?ids=${movie.m_id}&recommend=${-(movie.m_recommend-1)}"><i class="${movie.m_recommend==0?'icon-circle-arrow-up':'icon-circle-arrow-down'}"></i>&nbsp;${movie.m_recommend==0?'置顶':'取消'}</a>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</form>
		
		<c:set var="halfPage" value="3" />
		<c:set var="beginPage" value="${pageInfo.pageNum-halfPage>0?(pageInfo.pageNum+halfPage<=pageInfo.pages?pageInfo.pageNum-halfPage:pageInfo.pageNum-halfPage-(halfPage - (pageInfo.pages-pageInfo.pageNum))):1 }" />
		<ul class="pager pull-right">
			<c:if test="${pageInfo.pageNum - halfPage > 0 }">
			<li><a href="${pageContext.request.contextPath }/admin/movies/page/1${filter }">首页</a></li>
			</c:if>
			<c:if test="${pageInfo.hasPreviousPage }">
			<li class="previous"><a href="${pageContext.request.contextPath }/admin/movies/page/${pageInfo.pageNum-1 }${filter }">«</a></li>
			</c:if>
			<c:forEach begin="${beginPage }" end="${beginPage+halfPage*2 }" var="p">
			<c:if test="${p<=pageInfo.pages }">
			<li ${pageInfo.pageNum==p?'class="active"':'' }><a <c:if test="${pageInfo.pageNum!=p}">href="${pageContext.request.contextPath }/admin/movies/page/${p }${filter }"</c:if>>${p }</a></li>
			</c:if>
			</c:forEach>
			<c:if test="${pageInfo.hasNextPage }">
			<li class="next"><a href="${pageContext.request.contextPath }/admin/movies/page/${pageInfo.pageNum+1 }${filter }">»</a></li>
			</c:if>
			<c:if test="${pageInfo.pageNum + halfPage < pageInfo.pages }">
			<li><a href="${pageContext.request.contextPath }/admin/movies/page/${pageInfo.pages }${filter }">尾页</a></li>
			</c:if>
		</ul>
	</div>
</div>
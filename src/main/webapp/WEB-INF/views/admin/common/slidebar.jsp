<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<aside class="sidebar">
	<input type="hidden" id="position" value="${position }" />
	<div class="sidebar-menu">
		<div class="item home">
			<a href="#"><i class="icon icon-dashboard"></i><span>主页</span></a>
		</div>
		<div class="item spider">
			<a href="${pageContext.request.contextPath }/admin/spider"><i class="icon icon-coffee"></i><span>爬虫设置</span></a>
		</div>
		<div class="item vertical">
			<a href="#"> <i class="icon icon-cogs"></i> <span>网站设置</span> <span class="arrow"></span>
			</a>
			<div class="vertical-menu">
				<a href="#">基础设置</a>
			</div>
		</div>
		<div class="item vertical moviemanager">
			<a href="#"> <i class="icon icon-film"></i> <span>影视管理</span> <span class="arrow"></span>
			</a>
			<div class="vertical-menu">
				<a class="movies" href="${pageContext.request.contextPath }/admin/movies">影视列表</a>
				<a href="#">录入影视</a>
			</div>
		</div>
		<div class="item vertical">
			<a href="#"> <i class="icon icon-file-movie"></i> <span>资源管理</span> <span class="arrow"></span>
			</a>
			<div class="vertical-menu">
				<a href="#">资源列表</a> <a href="#">录入资源</a>
			</div>
		</div>
	</div>
</aside>
<script src="${pageContext.request.contextPath }/js/admin/slidebar.js"></script>
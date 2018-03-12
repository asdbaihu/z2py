<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header class="header">
	<div class="header-left">
		<div class="sidebar-toggle">
			<i class="icon icon-bars"></i>
		</div>
		<div class="logo">
			<span class="text-primary">高清片源后台</span>
		</div>
	</div>
	<div class="header-user">
		<div class="item dropdown">
			<span data-toggle="dropdown"> <i class="icon icon-user"></i> <span>管理员</span> <span class="caret"></span>
			</span>
			<ul class="dropdown-menu pull-right">
				<li><a href="${pageContext.request.contextPath}/admin/logout">注销</a></li>
			</ul>
		</div>
	</div>
</header>
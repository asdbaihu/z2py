<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<nav class="navbar navbar-inverse" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse-example">
				<span class="sr-only">切换导航</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${pageContext.request.contextPath }/" title="最爱片源网"></a>
		</div>
		<div class="collapse navbar-collapse navbar-collapse-example">
			<ul class="nav navbar-nav">
				<li ${uri==null?'class="active"':'' }><a href="${pageContext.request.contextPath }/">片源</a></li>
				<li ${uri=='mv/'?'class="active"':'' }><a href="${pageContext.request.contextPath }/mv">电影</a></li>
				<li ${uri=='tv/'?'class="active"':'' }><a href="${pageContext.request.contextPath }/tv">电视剧</a></li>
				<li><a href="http://www.zimuku.net/" target="_blank" rel="nofollow">字幕下载</a></li>
			</ul>
			<form id="search-form" class="col-md-12 navbar-form" action="${pageContext.request.contextPath }/search/">
				<div class="input-group">
					<input type="hidden" name="type"/>
					<input id="keyword" type="text" class="form-control input-sm" value="${keyword }">
					<span class="input-group-btn">
						<button class="btn btn-default btn-sm search" rel="0" type="button">搜影视</button>
						<button class="btn btn-default btn-sm search" rel="1" type="button">搜资源</button>
					</span>
				</div>
			</form>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="#">登录</a></li>
				<li><a href="#">注册</a></li>
			</ul>
		</div>
	</div>
</nav>
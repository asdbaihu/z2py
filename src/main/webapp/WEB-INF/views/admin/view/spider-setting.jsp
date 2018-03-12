<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="main-content">
	<div class="wrapper">
		<ol class="breadcrumb">
			<li><i class="icon icon-home"></i> 主页</li>
			<li class="active">爬虫设置</li>
		</ol>
		<div class="panel panel-primary">
			<div class="panel-heading">爬虫信息</div>
			<div class="panel-body">
				<p>封面路径：${spiderConfig.posterPath }</p>
				<p>torrent路径：${spiderConfig.uploadPath }</p>
			</div>
		</div>
		<div class="panel panel-primary">
			<div class="panel-heading">爬虫状态</div>
			<div class="panel-body">
				<p><strong>状态：</strong>${webSpider.spider==null?'<span class="text-danger">未运行<span>':webSpider.spider.status }
				<p><strong>线程数：</strong>${webSpider.threads }</p>
				<p><strong>抓取周期：</strong>${webSpider.schedulerTime }分钟</p>
				<c:if test="${webSpider.spider!=null }">
				<p><strong>开始时间：</strong><fmt:formatDate value="${webSpider.spider.startTime }" pattern="yyy-MM-dd hh:mm:ss" /></p>
				</c:if>
			</div>
		</div>
		<div class="panel panel-primary">
			<div class="panel-heading">爬虫控制</div>
			<div class="panel-body">
				<form class="col-md-8 form-horizontal form-condensed">
					<div class="form-group">
						<label for="threads" class="col-sm-2">线程数:</label>
						<div class="col-md-6 col-sm-10">
							<input type="text" class="form-control" id="threads" name="threads" value="${webSpider.threads }">
						</div>
					</div>
					<div class="form-group">
						<label for="schedulerTime" class="col-sm-2">抓取周期:</label>
						<div class="col-md-6 col-sm-10">
							<input type="text" class="form-control" id="schedulerTime" name="schedulerTime" value="${webSpider.schedulerTime }">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="submit" class="btn btn-primary">保存</button>
							<a href="${pageContext.request.contextPath }/admin/spider/start" style="margin-left:20px" class="btn btn-success ${webSpider.spider!=null?'disabled':'' }">启动爬虫</a>
							<a href="${pageContext.request.contextPath }/admin/spider/stop" class="btn btn-danger ${webSpider.spider==null?'disabled':'' }">停止爬虫</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
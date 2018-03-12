<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="main-content">
	<div class="wrapper">
		<ol class="breadcrumb">
			<li><a href="#"><i class="icon icon-home"></i> 主页</a></li>
		</ol>
		<table class="table table-bordered">
			<thead><tr><th colspan=2>系统信息</th></tr></thead>
			<tbody>
				<tr><td style="width:120px">系统名：</td><td>${sys.os_name }</td></tr>
				<tr><td>系统架构：</td><td>${sys.os_arch }</td></tr>
				<tr><td>系统版本号：</td><td>${sys.os_version }</td></tr>
				<tr><td>系统IP：</td><td>${sys.os_ip }</td></tr>
				<tr><td>系统MAC地址：</td><td>${sys.os_mac }</td></tr>
				<tr><td>系统时间：</td><td>${sys.os_date }</td></tr>
				<tr><td>系统CPU个数：</td><td>${sys.os_cpus }</td></tr>
				<tr><td>系统用户名：</td><td>${sys.os_user_name }</td></tr>
				<tr><td>工作目录：</td><td>${sys.os_user_dir }</td></tr>
				<tr><td>操作系统：</td><td>${sys.sun_desktop }</td></tr>
				<tr><td>JAVA版本：</td><td>${sys.java_version }</td></tr>
				<tr><td>服务器名：</td><td>${sys.server_name }</td></tr>
				<tr><td>服务器端口：</td><td>${sys.server_port }</td></tr>
				<tr><td>内存：</td><td>${sys.memory }</td></tr>
			</tbody>
		</table>
	</div>
</div>
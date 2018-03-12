<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="mfn" uri="http://www.inspuruptec.com/jsp/jstl/util" %>
<div class="container-fluid bg-default opt-box">
	<div class="container with-padding padding-top-lg opt-top">
		<c:if test="${uri==null }">
			<dl class="clearfix">
				<dt>资源分类：</dt>
				<dd>
					<a ${filters.cat==''?'class="active"':'' } href="${mfn:removeFilter(filter, 'cat') }">不限</a>
					<a ${filters.cat=='mv'?'class="active"':'' } href="${pageContext.request.contextPath }/${mfn:addFilter(filter, 'cat', 'mv') }">电影</a>
					<a ${filters.cat=='tv'?'class="active"':'' } href="${pageContext.request.contextPath }/${mfn:addFilter(filter, 'cat', 'tv') }">电视剧</a>
				</dd>
			</dl>
		</c:if>
		<dl class="clearfix">
			<dt>影视类型：</dt>
			<c:set var="types" value="动作,科幻,喜剧,爱情,剧情,惊悚,恐怖,犯罪,悬疑,冒险,纪录片,家庭,动画,奇幻,历史,传记,歌舞,西部,战争"></c:set>
			<c:if test="${!fn:contains(types, filters.type) }"><c:set var="types" value="${types },${filters.type }"></c:set></c:if>
			<dd>
				<a ${filters.type==''?'class="active"':'' } href="${pageContext.request.contextPath }/${uri}page/1${mfn:removeFilter(filter, 'type') }">不限</a>
				<c:forEach items="${types }" var="type">
					<a ${filters.type==type?'class="active"':'' } href="${pageContext.request.contextPath }/${uri}page/1${mfn:addFilter(filter, 'type', type) }">${type }</a>
				</c:forEach>
				<a href="javascript:;" class="extend-link">&gt;&gt;</a>
				<span class="hide extend-box"><input class="extend-input"/><button class="extend-search"
																																					 data-type="type">搜索</button></span>
			</dd>
		</dl>
		<div class="opt-more-box ${(filter=='' && uri==null)?'hide':'' }">
			<dl class="clearfix">
				<dt>制片地区：</dt>
				<c:set var="areas" value="大陆,香港,美国,英国,日本,韩国,法国,印度,德国,西班牙,意大利,澳大利亚,比利时,瑞典,荷兰,丹麦,加拿大,俄罗斯"></c:set>
				<c:if test="${!fn:contains(areas, filters.area) }"><c:set var="areas"
																																	value="${areas },${filters.area }"></c:set></c:if>
				<dd>
					<a ${filters.area==''?'class="active"':'' } href="${pageContext.request.contextPath }/${uri}page/1${mfn:removeFilter(filter, 'area') }">不限</a>
					<c:forEach items="${areas }" var="area">
						<a ${filters.area==area?'class="active"':'' } href="${pageContext.request.contextPath }/${uri}page/1${mfn:addFilter(filter, 'area', area) }">${area }</a>
					</c:forEach>
					<a href="javascript:;" class="extend-link">&gt;&gt;</a>
					<span class="hide extend-box"><input class="extend-input"/><button class="extend-search"
																																						 data-type="area">搜索</button></span>
				</dd>
			</dl>
			<dl class="clearfix">
				<dt>上映时间：</dt>
				<c:set var="years"
							 value="2017,2016,2015,2014,2013,2012,2011,2010,2009,2008,2007,2006,2005,2004,2003,2002,2001,2000"></c:set>
				<dd>
					<a ${filters.year==''?'class="active"':'' } href="${pageContext.request.contextPath }/${uri}page/1${mfn:removeFilter(filter, 'year') }">不限</a>
					<c:if test="${!fn:contains(years, filters.year) }"><c:set var="years"
																																		value="${years },${filters.year }"></c:set></c:if>
					<c:forEach items="${years }" var="year">
						<a ${filters.year==year?'class="active"':'' } href="${pageContext.request.contextPath }/${uri}page/1${mfn:addFilter(filter, 'year', year) }">${year }</a>
					</c:forEach>
					<a href="javascript:;" class="extend-link">&gt;&gt;</a>
					<span class="hide extend-box"><input class="extend-input"/><button class="extend-search"
																																						 data-type="year">搜索</button></span>
				</dd>
			</dl>
			<c:if test="${uri==null }">
				<dl class="clearfix">
					<dt>资源画质：</dt>
					<c:set var="qualities"
								 value="蓝光原盘,Remux,BluRay-3D,BluRay-1080P,BluRay-720P,4K,WEB-1080P,WEB-720P,HDTV/HDRip/DVDRip,TS/CAM/HC/DVDScr"></c:set>
					<dd>
						<a ${filters.quality==''?'class="active"':'' } href="${pageContext.request.contextPath }/${uri}page/1${mfn:removeFilter(filter, 'quality') }">不限</a>
						<c:forEach items="${qualities }" var="quality">
							<a ${filters.quality==quality?'class="active"':'' } href="${pageContext.request.contextPath }/${uri}page/1${mfn:addFilter(filter, 'quality', quality) }">${quality }</a>
						</c:forEach>
					</dd>
				</dl>
			</c:if>
			<c:if test="${uri!=null }">
				<dl class="clearfix">
					<dt>影视排序：</dt>
					<dd>
						<a ${(filters.order=='' || filters.order=='update')?'class="active"':'' } href="${pageContext.request.contextPath }/${uri}page/1${mfn:addFilter(filter, 'order', 'update') }">更新时间</a>
						<a ${filters.order=='score'?'class="active"':'' } href="${pageContext.request.contextPath }/${uri}page/1${mfn:addFilter(filter, 'order', 'score') }">豆瓣评分</a>
					</dd>
				</dl>
			</c:if>
		</div>
	</div>
	<c:if test="${uri==null }">
		<div class="opt-more">
			<span class="btn-switch">更多选项（制片地区，上映时间，资源画质）<i></i></span>
		</div>
	</c:if>
</div>
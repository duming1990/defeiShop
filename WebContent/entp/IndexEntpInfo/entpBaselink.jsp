<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<title>${app_name}</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" type="text/css" href="${ctx}/scripts/swiper/swiper.min.css"  />
<link href="${ctx}/styles/public.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/main_all.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/index.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/category-list.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/category-nav.css?v=20180427"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/customer.css?v=20161130"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/serviceIndex/css/public.css?20180605"  />
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<style type="">
.search-box__input{box-sizing: unset}
.site-mast{background-color: #ffffff;}
.mt-cates{box-sizing: unset;}
.bannermain{clear:both;}
.forIndex .site-mast__site-nav-w{box-sizing: unset!important;}
.bannermain .banner{z-index: 10;}
.home_categorys .dd{z-index:999;}
.categorys .subitems dt{box-sizing: unset}
.navbar__item-w a{box-sizing: unset}
.search-box__tabs-container{box-sizing: unset;}
.categorys .subitems dt{width:52px!important;}
.container{width: 1050px!important;left:55%!important;z-index:10;}
.guan .swiper-slide{background:none;}
</style>
</head>
<script type="text/javascript" src="${ctx}/scripts/swiper/swiper.min.js"></script>
<c:set var="bodyBackgroudImage" value="" /> 
<c:if test="${not empty baseLinkBg}">
<c:set var="bodyBackgroudImage" value="background-image: url(${ctx}/${baseLinkBg.image_path})" /> 
</c:if>
<body style="${bodyBackgroudImage};position: static;background-position: center 168px;" class="pg-floor-new-v2 forIndex">
<jsp:include page="../../_header.jsp" flush="true" />
		<div class="bannermain">
			<div class="banner">
				<div class="swiper-container">
					<div class="swiper-wrapper">				
					<c:forEach var="cur" items="${baseLink10List}">
					    <c:if test="${not empty cur.image_path}">
									<div class="swiper-slide"><img src="${ctx}/${cur.image_path}" /></div>
			    		</c:if>
			    		</c:forEach>
					<!-- 如果需要分页器 -->
					<div class="swiper-pagination"></div>
				</div>
			</div>
			</div>
		</div>
		<script>
			var mySwiper = new Swiper('.swiper-container', {
				direction: 'horizontal',
				loop: true,
				autoplay: true,
				delay: 3000, //1秒切换一次
				// 如果需要分页器
				pagination: {
					el: '.swiper-pagination',
				}
			})
		</script>
		<div class="main">
			<div class="container">
				<div class="guan">
					<div class="swiper-container swiperguan">
						<div class="swiper-wrapper">
							 <c:forEach var="cur" items="${baseLink100List}">
							    <c:if test="${(not empty cur.image_path) and (cur.main_type eq 10)}">
									<a href="${ctx}/IndexTsg.do?method=index&Link_id=${cur.link_id}" target="_blank"" title="${cur.title}" target="_blank"><img src="${ctx}/${cur.image_path}"/></a>
					    		</c:if>
							</c:forEach>
						</div>
					</div>
				</div>
				
				<script>
					var swiper = new Swiper('.swiperguan', {
						slidesPerView: 5,
						spaceBetween: 10,
						freeMode: true,
						loop: true,
						autoplay: true,
						delay: 1000, //1秒切换一次
					});
				</script>
				
				<c:forEach var="cur" items="${baseLink20List}" varStatus="vs">
				<c:if test="${cur.pre_number eq 1}">
				<div class="lanmuhe">
					<img src="${ctx}/${cur.image_path}"/>
				</div>
				<div class="r1r1">
				    <c:forEach var="curSon" items="${cur.map.baseLink101List}">
					    <c:if test="${not empty curSon.image_path}">
							<a href="${curSon.link_url}" title="${curSon.title}" target="_blank"><img src="${ctx}/${curSon.image_path}"/></a>
			    		</c:if>
					</c:forEach>
				</div>
				</c:if>
				
				<c:if test="${cur.pre_number eq 2}">
				<div class="lanmuhe">
					<img src="${ctx}/${cur.image_path}" />
				</div>
				<div class="r12r1">
					<c:forEach var="curSon" items="${cur.map.baseLink201List}">
					    <c:if test="${not empty curSon.image_path}">
							<a href="${curSon.link_url}" title="${curSon.title}" target="_blank"><img src="${ctx}/${curSon.image_path}"/></a>
			    		</c:if>
					</c:forEach>
					<c:forEach var="curSon" items="${cur.map.baseLink202List}">
					    <c:if test="${not empty curSon.image_path}">
							<a href="${curSon.link_url}" title="${curSon.title}" target="_blank"><img src="${ctx}/${curSon.image_path}"/></a>
			    		</c:if>
					</c:forEach>
					<c:forEach var="curSon" items="${cur.map.baseLink203List}">
					    <c:if test="${not empty curSon.image_path}">
							<a href="${curSon.link_url}" title="${curSon.title}" target="_blank"><img src="${ctx}/${curSon.image_path}"/></a>
			    		</c:if>
					</c:forEach>
				</div>
				</c:if>
				<c:if test="${cur.pre_number eq 3}">
				<div class="lanmuhe">
					<img src="${ctx}/${cur.image_path}"/>
				</div>
				<div class="r1r3">
					<c:forEach var="curSon" items="${cur.map.baseLink301List}">
					    <c:if test="${not empty curSon.image_path}">
							<a href="${curSon.link_url}" title="${curSon.title}" target="_blank"><img src="${ctx}/${curSon.image_path}"/></a>
			    		</c:if>
					</c:forEach>
				</div>
				</c:if>
				<c:if test="${cur.pre_number eq 4}">
				<div class="lanmuhe">
					<img src="${ctx}/${cur.image_path}" />
				</div>
				<div class="r1r2">
					<c:forEach var="curSon" items="${cur.map.baseLink401List}">
					    <c:if test="${not empty curSon.image_path}">
							<a href="${curSon.link_url}" title="${curSon.title}" target="_blank"> <img src="${ctx}/${curSon.image_path}"/></a>
			    		</c:if>
					</c:forEach>
				</div>
				</c:if>
				
				<c:if test="${cur.pre_number eq 5}">
				<div class="lanmuhe">
					<img src="${ctx}/${cur.image_path}" />
				</div>
				<div class="r1r4">
					<c:forEach var="curSon" items="${cur.map.baseLink501List}">
					    <c:if test="${not empty curSon.image_path}">
							<a href="${curSon.link_url}" title="${curSon.title}" target="_blank"><img src="${ctx}/${curSon.image_path}"/></a>
			    		</c:if>
					</c:forEach>
				</div>
				</c:if>
				
				</c:forEach>
				<div style="height: 750px"></div>
			</div>
		</div>
<!-- <div class="clear"></div> -->
<%-- <jsp:include page="../../_footer.jsp" flush="true" /> --%>
		<script type="text/javascript">//<![CDATA[
		//]]></script>
</body>
</html>

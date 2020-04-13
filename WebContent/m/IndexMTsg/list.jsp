<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<title>扶贫馆 - ${app_name}</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<link rel="stylesheet" type="text/css" href="${ctx}/styles/mserviceIndex/css/public.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/mserviceIndex/css/index.css?20180627"  />
<link rel="stylesheet" type="text/css" href="${ctx}/scripts/swiper/swiper.min.css"  />
</head>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/scripts/swiper/swiper.min.js"></script>
<c:set var="bodyBackgroudImage" value="background-color: white;" /> 
<c:if test="${not empty baseLinkBg}">
<c:set var="bodyBackgroudImage" value="background-image: url(${ctx}/${baseLinkBg.image_path})" /> 
</c:if>
<body style="${bodyBackgroudImage};background-size: cover;background-repeat: repeat-x;">
<div class="container index">
    <div class="swiper-container">
        <div class="swiper-wrapper">
   	 	 <c:forEach var="cur" items="${baseLink10List}">
		    <c:if test="${not empty cur.image_path}">
			<div class="swiper-slide"><a href="${cur.link_url}" title="${cur.title}" target="_blank"><img src="${ctx}/${cur.image_path}"  class ="guanStyle"/> </a></div>
    		</c:if>
    	 </c:forEach>
        </div>
        <!-- 如果需要分页器 -->
        <div class="swiper-pagination"></div>
    </div>
    <script>
		<c:if test="${fn:length(baseLink10List) gt 1}">
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
		</c:if>
	</script>
	<c:if test="${not empty baseLink100List}">
    <div class="div1">
        <div class="swiper-container swiperguan">
			<div class="swiper-wrapper">
			  <c:forEach var="cur" items="${baseLink100List}">
				    <c:if test="${(not empty cur.image_path) and (cur.main_type eq 10)}">
						<div class="swiper-slide imgs ">
						 	<a href="${ctx}/m/IndexMTsg.do?method=index&link_id=${cur.link_id}"title="${cur.title}" target="_blank"><img src="${ctx}/${cur.image_path}" class = "guanStyle img"/></a>
						</div>
		    		</c:if>
			 </c:forEach>
			</div>
        </div>
    </div>
    </c:if>
		<script>
			var swiper = new Swiper('.swiperguan', {
				slidesPerView: 5,
				freeMode: true,
				loop: true,
				autoplay: true,
				delay: 1000, //1秒切换一次
			});
		</script>
    
   	<c:forEach var="cur" items="${baseLink20List}" varStatus="vs">
   	<c:if test="${cur.pre_number eq 1}">
    <div class="div2">
    	<a href="${cur.link_url}" title="${cur.title}" target="_blank"><img src="${ctx}/${cur.image_path}" class="guanStyle"/></a>
  	    <c:forEach var="curSon" items="${cur.map.baseLink101List}">
		    <c:if test="${not empty curSon.image_path}">
				<a href="${curSon.link_url}" title="${curSon.title}" target="_blank"><img src="${ctx}/${curSon.image_path}" class="guanStyle mr1"/></a>
    		</c:if>
		</c:forEach>
    </div>
    </c:if>
     <c:if test="${cur.pre_number eq 2}">
     <div class="div2">
     <a href="${cur.link_url}" title="${cur.title}" target="_blank"><img src="${ctx}/${cur.image_path}" class=" guanStyle mr1"/></a>
     </div>
    <div class="div3">
        <div class="left">
        <c:forEach var="curSon" items="${cur.map.baseLink201List}">
		    <c:if test="${not empty curSon.image_path}">
				<a href="${curSon.link_url}" title="${curSon.title}" target="_blank"><img src="${ctx}/${curSon.image_path}" class = "guanStyle"/></a>
    		</c:if>
		</c:forEach>
        </div>
        <div class="right">
    	<c:forEach var="curSon" items="${cur.map.baseLink202List}">
		    <c:if test="${not empty curSon.image_path}">
				<a href="${curSon.link_url}" title="${curSon.title}" target="_blank"><img src="${ctx}/${curSon.image_path}" class = "guanStyle"/></a>
    		</c:if>
		</c:forEach>
        </div>
    </div>
    <div class="div2">
 		<c:forEach var="curSon" items="${cur.map.baseLink203List}">
		    <c:if test="${not empty curSon.image_path}">
				<a href="${curSon.link_url}" title="${curSon.title}" target="_blank"><img src="${ctx}/${curSon.image_path}" class = " guanStyle mr2"/></a>
    		</c:if>
		</c:forEach>
    </div> 
    </c:if>
    
    
    <c:if test="${cur.pre_number eq 3}">
    <div class="div2">
      <a href="${cur.link_url}" title="${cur.title}" target="_blank"> <img src="${ctx}/${cur.image_path}" class="mr1"/></a>
    </div>
    <div class="div4">
 		<c:forEach var="curSon" items="${cur.map.baseLink301List}">
		    <c:if test="${not empty curSon.image_path}">
				<a href="${curSon.link_url}" title="${curSon.title}" target="_blank" class="style"><img src="${ctx}/${curSon.image_path}" class = "guanStyle"/></a>
    		</c:if>
		</c:forEach>
    </div>
    </c:if>
    
    <c:if test="${cur.pre_number eq 4}">
    <div class="div2">
  	<a href="${cur.link_url}" title="${cur.title}" target="_blank">	<img src="${ctx}/${cur.image_path}"/></a>
    </div>
    <div class="div5">
		<c:forEach var="curSon" items="${cur.map.baseLink401List}">
		    <c:if test="${not empty curSon.image_path}">
				<a href="${curSon.link_url}" title="${curSon.title}" target="_blank" class="style"><img src="${ctx}/${curSon.image_path}" class = "guanStyle"/></a>
		  	</c:if>
		</c:forEach>
    </div>
    </c:if>
    
    
    <c:if test="${cur.pre_number eq 5}">
    <div class="div2">
 	<a href="${cur.link_url}" title="${cur.title}" target="_blank">	<img src="${ctx}/${cur.image_path}"/></a>
    </div>
    <div class="div5">
		<c:forEach var="curSon" items="${cur.map.baseLink501List}">
		    <c:if test="${not empty curSon.image_path}">
				<a href="${curSon.link_url}" title="${curSon.title}" target="_blank" class="style"><img src="${ctx}/${curSon.image_path}" class = "guanStyle"/></a>
		  	</c:if>
		</c:forEach>
    </div>
    </c:if>
  </c:forEach>
</div>
</body>
</html>

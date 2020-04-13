<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name_min}触屏版</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/m/scripts/owl-carousel/owl.carousel.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/m/scripts/owl-carousel/owl.theme.css"/>
<link rel="apple-touch-icon-precomposed" href="${ctx}/m/styles/images/apple-touch-icon.png">
<style type="text/css">
.floorUl {
	display: flex;
	display: -webkit-box;
    display: -webkit-flex;
	width: 100%;
}
.floorUl li {
	flex:1;
	-webkit-box-flex: 1; 
    -webkit-flex: 1;
}
.col-title a {
	margin:0px;
}
.col-title .cur {
	color: #cc0000;
	border-bottom: 2px solid #cc0000;
}
div#wrap3 li{
	height: 0.80rem; 
}
.citynone{font-size: .28rem;
    padding: .14rem .2rem 0 .1rem;
}
.c-hd section.citynone a {
    position: relative;
    display: block;
    color: #fff;
    border-radius: .04rem;
    padding: .06rem .08rem;
}
.fanxianrule {
    background-color: #cc0000;
    color: #fff;
    font-size: 12px;
    padding: 2px 4px;
}
</style>
</head>
<body id="body" style="background: rgb(213, 213, 214);">
<div id="wrap">
  <header class="index app_hide">
  <div class="c-hd">
      <section class="citynone"><a>商城</a></section>
      <c:url var="url" value="/m/MSearch.do" />
      <section class="hd-search">
      <span class="sl" onClick="goUrl('${url}')">请输入商品名</span></section>
      <section class="hd-nav">
        <c:url var="loginUrl" value="/m/MIndexLogin.do"/>
        <c:if test="${not empty userInfo}">
          <c:url var="loginUrl" value="/m/MMyHome.do"/>
        </c:if>
        <a onClick="goUrl('${loginUrl}')" class="user"><i></i></a> </section>
    </div>
    
  </header>
  <section class="sec-warp">
    <c:if test="${not empty mBaseLink20List}">
      <div class="banner-view">
        <div class="sliderwrap">
          <ul id="banner_list">
            <c:forEach var="cur" items="${mBaseLink20List}" varStatus="vs">
              <li>
              <c:url var="url" value="${cur.link_url}" />
              <a onclick="goUrl('${url}')" title="${cur.title}">
                <c:set var="imgurl" value="${ctx}/${cur.image_path}"/>
                <img src="${imgurl}" width="100%"></a></li>
            </c:forEach>
          </ul>
        </div>
      </div>
    </c:if>
    <div class="sort-nav">
      <ul>
        <c:forEach var="cur" items="${mBaseLink30List}" varStatus="vs">
          <li> 
          <c:url var="url" value="${cur.link_url}" />
          <a onclick="goUrl('${url}')" title="${cur.title}"> <span class="sort-circle sortid_${cur.title_color}"></span> <span class="sort-desc">${fn:escapeXml(fnx:abbreviate(cur.title, 2 * 4, ""))}</span> </a> </li>
        </c:forEach>
      </ul>
    </div>
   <div class="channel-nav" style="padding-bottom:8px;margin-bottom:0;"></div>
  </section>

  <div class="contersort-conter" id="list_data">
      <div class="list-view">
        <div class="col-title">
          <ul class="floorUl">
          <c:set var="liClass" value="cur" />
          <li id="floorli" class="${liClass}"> 
          <a href="javascript:;">所有商品</a></li>
          </ul>
        </div>
        <ul class="list-ul">
          <c:forEach var="cur" items="${commInfoList}">
            <li> 
             <c:url var="url" value="/m/MEntpInfo.do?id=${cur.id}"/>
            <a onclick="goUrl('${url}')" title="${cur.comm_name}">
              <div class="list-item">
                <div class="pic">
                 <c:set var="imgurl" value="${ctx}/styles/imagesPublic/no_image_index.jpg"/>
	           <c:if test="${not empty cur.main_pic}">
                  <c:set var="imgurl" value="${ctx}/${cur.main_pic}"/></c:if>
                  <img class="lazy" data-original="${imgurl}" alt="${cur.comm_name}" style="display: inline;"></div>
                <div class="info">
                  <h2 class="title">${fn:escapeXml(cur.comm_name)}</h2>
                  <h3 class="title">${fn:escapeXml(cur.sub_title)}</h3>
                  <div class="main">
                    <p class="price"> <em>&yen;
                      <fmt:formatNumber pattern="0.########" value="${cur.sale_price}" />
                      </em></p>
<!--                       <p class="fanxianrule"> -->
<%--                       <c:forEach var="curSon" items="${baseData700List}"> --%>
<%--                   			<c:if test="${curSon.id eq cur.fanxian_rule}">${curSon.type_name}</c:if> --%>
<%--                 		</c:forEach></p> --%>
<!--                 		&nbsp;&nbsp;&nbsp;&nbsp; -->
                    <p class="num bought">已售${cur.sale_count_update}</p>
                  </div>
                </div>
              </div>
              </a> 
             </li>
          </c:forEach>
          <c:url var="url" value="/m/MSearch.do?htype=0" />
         <div onclick="goUrl('${url}')" class="box submit-btn-more">查看更多</div>
        </ul>
      </div>
  </div>
  <jsp:include page="../_footer.jsp" flush="true"/>
</div>
<script type="text/javascript" src="${ctx}/m/scripts/owl-carousel/owl.carousel.min.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/tabs/tabs.switch.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lazyload/min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	$("img.lazy").lazyload({
	    effect : "fadeIn"
	});
	
	$("#banner_list").owlCarousel({
		  autoPlay : true, 
		  slideSpeed : 200
	  });
	
	
});
                                          
//]]></script>
</body>
</html>

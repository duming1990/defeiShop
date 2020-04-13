<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link rel="stylesheet" href="${ctx}/m/scripts/dropload/css/dropload.css" />
<style type="text/css">
.dropload-down{padding-bottom: 1rem;}
header.index {
	height: .88rem;
	background: #fff;
	color: #fff;
	border-bottom: 1px solid #eee;
	position: fixed;
	display: block;
	width: 100%;
}
.saleCount{
	text-align: right;
    position: relative;
    font-size: .24rem;
}
.goods_list ul{padding-bottom:0!important;}
.goods_list li{
height:auto;
width: 49%;
margin-left: 0.5%;
margin-right:0.5%;
margin-top:0.1rem;
margin-bottom: 0!important;
box-shadow: 0 0 150px rgba(0,0,0,.1);
}
.name_my{
	height:30px;
	line-height:16px;
}
.goods_list li a .name_my{margin-left:0.13rem;}
.commInfo_from{margin-left:0.13rem;}
</style>
</head>
<body>
<div id="wrap">
  <header class="index app_hide">
    <div class="c-hd s-hd">
      <section class="back mui-action-back"><a><i></i></a> </section>
      <section class="hd-search">
        <c:url var="url" value="MSearch.do" />
        <html-el:form action="${url}" styleClass="box attrForm">
          <html-el:hidden property="method" value="listPd" />
          <html-el:hidden property="htype"/>
          <html-el:hidden property="p_index" styleId="p_index"/>
          <html-el:hidden property="root_cls_id" styleId="root_cls_id"/>
          <html-el:hidden property="par_cls_id" styleId="par_cls_id"/>
          <html-el:hidden property="searchScope" styleId="searchScope"  />
          <html-el:hidden property="orderByParam" styleId="orderByParam"/>
          <html-el:hidden property="is_zingying" styleId="is_zingying"/>
          <html-el:hidden property="is_aid" styleId="is_aid"/>
          <div class="box-flex c-form-search"> <em style="display:none;"></em>
            <input type="text" name="keyword" class="search-form c-ipunt-txt" placeholder="请输入商品名..." autocomplete="off" id="search_input" value="${af.map.keyword}" />
          </div>
		 <input type="submit" class="searchbtn" onClick="return putInHistoryKeywords_all();" name="dosearch" value="搜索" />
        </html-el:form>
      </section>
    </div>
  </header>
  <div style="padding-top:0.88rem;"></div>
  <jsp:include page="../_slide_nav.jsp" flush="true" />
  <div id="wrap">
    <c:if test="${not empty keyWordNavg}">
      <section class="com-search-tips"> <i></i>
        <p>${keyWordNavg}</p>
      </section>
    </c:if>
    <div class="conter">
      <div class="list-view">
        <c:if test="${empty entityList}">
          <div id="no_data" style="background:#fff; padding:15px; margin-bottom:10px;">当前筛选条件下没有产品哦，换个条件试试吧~</div>
        </c:if>
       <div class="list-view">
        <div class="goods_list" > 
	        <ul class="p_left" data-page="1" id="ul_data">
	        <c:forEach items="${entityList}" var="cur" varStatus="vs"> 
	        <li>
	          <div>
	            <c:url var="url" value="/m/MEntpInfo.do?id=${cur.id}" />
		        <a onclick="goUrl('${url}')">
		        <p class="img">
		        <c:set var="imgurl" value="${ctx}/styles/imagesPublic/no_image.jpg"/>
                <c:if test="${not empty cur.main_pic}">
                  <c:set var="imgurl" value="${ctx}/${cur.main_pic}@s400x400"/>
                </c:if>
		        <img data-original="${imgurl}" class="lazy" /></p>
		        <p class="name_my">${fn:escapeXml(cur.comm_name)}</p>
		        <h6 class="title">
                 <c:if test="${not empty cur.map.commZyName}">
                   <span class="commInfo_from">${fn:escapeXml(cur.map.commZyName)}</span>
                  </c:if>
                </h6>
		        <p class="price">
		            <span class="red">¥<fmt:formatNumber value="${cur.sale_price}" pattern="0.00" /></span>
		            <c:set var="saleCount" value="${cur.sale_count_update}"/>
                    <c:if test="${saleCount gt 10000}">
                      <fmt:formatNumber var="saleCount" value="${saleCount/10000}" pattern="#.##万"/>
                    </c:if>
		      		 <span  class ="saleCount">已售${saleCount}</span>
		        </p>
		        </a>
		       </div>
		     </li>
		     </c:forEach>
	       </ul>
        </div> 
       </div>
      </div>
      <div class="pop-shade hide" style="display: none; height: 100%;"></div>
    </div>
    <jsp:include page="../_footer.jsp" flush="true" />
  </div>
</div>
<script type="text/javascript" src="${ctx}/m/scripts/dropload/js/dropload.min.js?v20160725"></script>
<script type="text/javascript" src="${ctx}/m/js/search.m.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lazyload/min.js"></script>
<script type="text/javascript">//<![CDATA[
var ctx = "${ctx}";

$(document).ready(function(){
	
	$("img.lazy").lazyload({
	    effect : "fadeIn"
	});	
	getData();
});



function putInHistoryKeywords_all(keyword){
	$("#searchScope").val("1");
	Common.loading();
	_putInHistoryKeywords(keyword);
	return true;
	
}

function _putInHistoryKeywords(keyword){

	if(!keyword){
		keyword = $("#search_input").val();
	}
	if(keyword){
		var search_history_keywords = $.cookie("search_history_keywords");
		var history_words = search_history_keywords, history_words = history_words ? search_history_keywords.split(",") : [];
		history_words.push(keyword);
		history_words = uniqArray(history_words);
		history_words.length = 25 > history_words.length ? history_words.length : 25;
		if ($.isFunction($.cookie)) $.cookie("search_history_keywords", history_words.join(","), { path: '/' });
	}
}

function putInHistoryKeywords(keyword){
	$("#searchScope").val("0");
	Common.loading();	
	_putInHistoryKeywords(keyword);
	return true;
}
//]]></script>
<script type="text/javascript" src="${ctx}/m/js/list_data.js?v20180323"></script>
</body>
</html>

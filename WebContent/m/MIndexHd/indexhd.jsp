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
#wrap {margin-top: 2.38rem;}
.c-hd{background-color: #fff;}
.index .s-input-tab-nav{top:100px;}
</style>
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />
<div id="wrap">
  <header class="topfixed index app_hide" id="topfixed" style="position: static;">
	<ul class="tap-list">
	<c:forEach var="cur" items="${baseData2100List}" varStatus="vs">
      <c:set var="liClass" value="" />
      <c:set var="setName" value="即将开始" />
      <c:if test="${cur.map.is_current}">
          <c:set var="liClass" value="cur" />
          <c:set var="setName" value="进行中" />
      </c:if>
      <li class="${liClass}" style="width: 20%">
		<p>${fn:substring(cur.pre_varchar_1, 0, 5)}</p>
		<p>${setName}</p>
	  </li>
	  </c:forEach>
	</ul>
 <div class="new-skill-wrap">
  <header class="list-head">
	<span class="buy-txt" id="seckillBuyTxt">抢购中 先下单先得哦</span>
	<span class="time">
		<span class="static-txt-end" id="staticTxtEnd">距结束</span>
		<div id="seckill_time" class="timeText">
			<span class="seckill-time" id="t_d">00</span>
			<span class="time-separator">:</span>
			<span class="seckill-time" id="t_m">00</span>
			<span class="time-separator">:</span>
			<span class="seckill-time" id="t_s">00</span>
		</div>
	</span>
  </header>
  </div>
    <div class="c-hd s-hd">
      <section class="city" id="changeSearchTypeHd">
        <a class="arrowleft atop" id="typeShowName">搜活动</a>
      </section>
      
      <section class="hd-search">
        <c:url var="url" value="MIndexHd.do" />
         <html-el:form action="${url}" styleClass="box attrForm">
          <html-el:hidden property="hdtype" styleId="hdtype" value="0"/>
          <div class="box-flex c-form-search"> <em style="display:none;"></em>
            <input type="text" name="keyword" class="search-form c-ipunt-txt" placeholder="请输入商品名..." autocomplete="off" id="search_input" value="${af.map.keyword}" />
          </div>
          <input type="submit" class="searchbtn" name="dosearch" value="搜索">
        </html-el:form>
      </section>
      <div class="s-input-tab-nav off" id="showSearchTypeHd">
         <ul>
          <li class="all" data-flag="0">搜活动</li>
          <li class="shop" data-flag="1">搜全场</li>
         </ul>
      </div>
    </div>
  </header>
  <div id="wrap">
    <div class="conter">
      <div class="list-view">
        <c:if test="${empty entityList}">
          <div id="no_data" style="background:#fff; padding:15px; margin-bottom:10px;">当前筛选条件下没有产品哦，换个条件试试吧~</div>
        </c:if>
        <ul class="list-ul" id="ul_data" data-page="1">
          <c:forEach items="${entityList}" var="cur" varStatus="vs">
            <li>
              <div class="list-item">
                <div class="pic">
                  <c:set var="imgurl" value="${ctx}/styles/imagesPublic/no_image.jpg"/>
                  <c:if test="${not empty cur.main_pic}">
                    <c:set var="imgurl" value="${ctx}/${cur.main_pic}@s400x400"/>
                  </c:if>
                  <c:url var="url" value="/m/MEntpInfo.do?id=${cur.id}" />
                  <a onClick="goUrl('${url}')" ><img class="lazy" data-original="${imgurl}" style="display: inline;"></a> </div>
                <div class="info"> <a onClick="goUrl('${url}')">
                  <h2 class="title">${fn:escapeXml(cur.comm_name)}</h2>
                  <h3 class="title">${fn:escapeXml(cur.sub_title)}</h3>
                  </a>
                  <div class="main">
                    <p class="price"> <em>¥
                      <fmt:formatNumber value="${cur.sale_price}" pattern="0.00" />
                      </em>
                      <del><fmt:formatNumber value="${cur.price_ref}" pattern="0.00" /></del>
                      </p>
                    <c:set var="saleCount" value="${cur.sale_count_update}"/>
                    <c:if test="${saleCount gt 10000}">
                      <fmt:formatNumber var="saleCount" value="${saleCount/10000}" pattern="#.##万"/>
                    </c:if>
                    <p class="num bought">已售${saleCount}</p>
                  </div>
                </div>
              </div>
            </li>
          </c:forEach>
        </ul>
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
	
	GetRTime();
	setInterval(GetRTime,1000);
	
	getData(true);
});


function GetRTime(){
    var EndTime= new Date('${endTime}');
    var NowTime = new Date();
    var t =EndTime.getTime() - NowTime.getTime();
    var h=00;
    var m=00;
    var s=00;
    if(t>=0){
	      h=Math.floor(t/1000/60/60%24);
	      m=Math.floor(t/1000/60%60);
	      s=Math.floor(t/1000%60);
	      if(h < 10){
	    	  h = "0" + h;
	      }
	      if(m < 10){
	    	  m = "0" + m;
	      }
	      if(s < 10){
	    	  s = "0" + s;
	      }
    }
    $("#t_h").html(h);
    $("#t_m").html(m);
    $("#t_s").html(s);
}


//]]></script>
<script type="text/javascript" src="${ctx}/m/js/list_data.js?v201701182"></script>
</body>
</html>

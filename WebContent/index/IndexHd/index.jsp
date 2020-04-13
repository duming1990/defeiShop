<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/hd/css/common.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/pages.css"  />
</head>
<body class="pg-index forIndex" style="position: static;">
<jsp:include page="../../_header.jsp" flush="true" />
<div class="mod_container">
  <div class="seckill_container">
    <div class="timeline" id="timeline">
      <div class="grid_c1">
        <ul class="timeline_list">
          <c:forEach var="cur" items="${baseData2100List}" varStatus="vs">
            <c:set var="liClass" value="" />
            <c:set var="setName" value="即将开始" />
            <c:if test="${cur.map.is_current}">
             <c:set var="liClass" value="timeline_item_selected" />
             <c:set var="setName" value="进行中" />
            </c:if>
            <li class="timeline_item ${liClass}"> 
             <a class="timeline_item_link">
              <div class="timeline_item_link_skew">
               <i class="timeline_item_link_skew_time">${cur.pre_varchar_1}</i>
               <i class="timeline_item_link_skew_processtips">${setName}</i></div>
             </a>
            </li>
           </c:forEach> 
        </ul>
      </div>
    </div>
    <div class="skwrap">
      <div class="seckillbanner" id="seckillbanner">
        <div class="grid_c1">
          <div class="seckillbanner_slider">
            <ul class="slider_ctn">
              <c:forEach var="cur" items="${base2100LinkList}" varStatus="vs">
               <c:set var="imgurl" value="${ctx}/${cur.image_path}"/>
               <a><img src="${imgurl}" /></a>
              </c:forEach>
            </ul>
          </div>
        </div>
      </div>
      <div class="timecount" id="timecount">
        <div class="grid_c1">
          <div class="timecount_container">
            <div class="timecount_container_skew"> 
            <span id="J-seckilling" class="seckilling">抢购中</span> 
            <span id="J-timeContainer" class="time_container"> 
            <b id="J-text">先下单先得哦！</b>
            <b id="J-endDef">距结束</b> 
            <i class="hour" id="t_d">00</i>时 <i class="minutes" id="t_m">00</i>分 <i class="seconds" id="t_s">00</i>秒 </span> </div>
          </div>
        </div>
      </div>
      <div class="spsk" id="super_seckill">
        <div class="grid_c1">
          <ul class="seckill_mod_goodslist clearfix">
           <c:forEach var="cur" items="${commmInfoList}">
            <li class="seckill_mod_goods"> 
              <c:url var="url" value="/entp/IndexEntpInfo.do?method=getCommInfo&id=${cur.id}" />
              <a class="seckill_mod_goods_link" href="${url}" target="_blank"> 
              <img class="seckill_mod_goods_link_img" alt="${fn:escapeXml(cur.comm_name)}" src="${ctx}/${cur.main_pic}@s400x400" />
              <h4 class="seckill_mod_goods_title">${fn:escapeXml(cur.comm_name)}</h4>
              <span class="seckill_mod_goods_info"> 
              <span class="seckill_mod_goods_info_txt"> 
              <span class="seckill_mod_goods_price"> 
              <i class="seckill_mod_goods_price_now"><em>¥</em><fmt:formatNumber value="${cur.sale_price}" pattern="0.##" /></i> 
              <span class="seckill_mod_goods_price_pre">¥<del><fmt:formatNumber value="${cur.price_ref}" pattern="0.##" /></del></span> </span> 
              </span> 
              <span class="seckill_mod_goods_info_btn"> <i></i> </span> <i class="seckill_mod_goods_info_i">立即抢购</i> </span> </a> 
            </li>
            </c:forEach>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <div class="pages">
	<c:url var="url" value="/IndexHd.do" />
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="${url}">
    <ul id="pagination" class="pagination"></ul>
     <script type="text/javascript" src="${ctx}/commons/pager/pagination.home.js">;</script>
       <script type="text/javascript">
      	  $("#pagination").pagination({
      		pageForm : document.bottomPageForm,
      		recordCount : parseFloat("${af.map.pager.recordCount}"),
      		pageSize : parseFloat("${af.map.pager.pageSize}"),
      		currentPage : parseFloat("${af.map.pager.currentPage - 1}")
      	 });
       </script>
    </form>
  </div>
</div>
<jsp:include page="../../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	GetRTime();
	setInterval(GetRTime,1000);
	
	$(window).scroll(function(e){
		scrollDetail();		
	});
	
});

function scrollDetail(){
	var t = $(document).scrollTop();
	var navbarTop = $("#timeline").offset().top;
	if(t > 135){
		$("#timeline").addClass("fixed");
	}else{
		$("#timeline").removeClass("fixed");
	}
}

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
</body>
</html>
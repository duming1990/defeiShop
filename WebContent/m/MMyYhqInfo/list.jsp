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
<link href="${ctx}/m/styles/css/my/yhq.css?v01" rel="stylesheet" type="text/css" />
<style type="text/css">
.list-ul li{border-bottom:none;}
</style>
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />	
<div class="content"> 

  <c:set var="cl_all" value="cur" />
  <c:set var="cl_0" value="" />
  <c:set var="cl_10" value="" />
  <c:if test="${empty af.map.order_state}">
  	  <c:set var="cl_0" value="cur" />
	  <c:set var="cl_1" value="" />
	  <c:set var="cl_2" value="" />
  </c:if>
  <c:if test="${af.map.is_used eq 1}">
  	  <c:set var="cl_0" value="" />
	  <c:set var="cl_1" value="cur" />
	  <c:set var="cl_2" value="" />
  </c:if>
  <c:if test="${af.map.is_used eq 2}">
  	  <c:set var="cl_0" value="" />
	  <c:set var="cl_1" value="" />
	  <c:set var="cl_2" value="cur" />
  </c:if>
  <div class="myorder-ors">
  	<a href="${ctx}/m/MMyYhqInfo.do?method=list&is_used=0" class="${cl_0}">可使用</a>
	<a href="${ctx}/m/MMyYhqInfo.do?method=list&is_used=1" class="${cl_1}">已使用</a>
	<a href="${ctx}/m/MMyYhqInfo.do?method=list&is_used=2" class="${cl_2}">已过期</a>
  </div>
  <div class="list-view">
    <ul class="list-ul" id="ul_data">
      <!-- 未付款、已付款、待评价、物流单、退款单  start -->
      <c:if test="${empty entityList}">
	          <div id="no_data" style="background:#fff; padding:15px; margin-bottom:10px;">暂无数据~</div>
	 </c:if>
	  <c:forEach var="cur" items="${entityList}" varStatus="vs">
      	<li>
      	<div class="over">
      		<div style="margin: 0px 15px">
	      		<div class="red-texture user_type_${af.map.is_used}"></div>
	      		<img src="${ctx}/m/styles/img/rp-bg.png" width="100%" height="120px">
	      		<div class="red_money_title color${af.map.is_used}">
	      		<span style="font-size:15px;margin-right:5px">¥</span>
	      		<fmt:formatNumber value="${cur.amount}" pattern="0.##"/></div>
	      		<div class="red-msg color${af.map.is_used}">订单满￥<fmt:formatNumber value="${cur.min_money}" pattern="0.##"/>可用</div>
	      		<div class="redDetail">
	      		<div class="redOrder">
	      		购物满<fmt:formatNumber value="${cur.min_money}" pattern="0.##"/>减
	      		<fmt:formatNumber value="${cur.amount}" pattern="0.##"/></div>
	      		<div class="redArea"><span style="color:#808080">限本人使用</span></div>
	      		<div class="redTime ng-binding" style="color: #cdcdcd;">
	      		<fmt:formatDate value="${cur.effect_start_date}" pattern="yyyy-MM-dd" />
	      		~
	      		<fmt:formatDate value="${cur.effect_end_date}" pattern="yyyy-MM-dd" />
	      		</div>
	      		</div>
	      		<c:if test="${af.map.is_used eq 1}">
	      		<div class="red_status_tab">
	      		<img src="${ctx}/m/styles/img/red-used.png" width="80px" height="80px"/></div>
	      		</c:if>
	      		<c:if test="${af.map.is_used eq 2}">
	      		<div class="red_status_tab">
	      		<img src="${ctx}/m/styles/img/red-over.png" width="80px" height="80px"/></div>
	      		</c:if>
      		</div>
      	 </div>
      	</li>
      	</c:forEach>
    </ul>
  </div>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[


//]]></script>
</body>
</html>
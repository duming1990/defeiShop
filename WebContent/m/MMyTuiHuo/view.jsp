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
<link href="${ctx}/m/styles/css/my/order-details.css" rel="stylesheet" type="text/css" />
</head>
<body >
<jsp:include page="../_header.jsp" flush="true" />
<div class="content pad55">
  <div class="m step1 border-1px">
    <div class="order-info-box">
      <div class="mt  cf">
        <div class="floatL">订单号:<span class="s1-num">${trade_index}</span></div>
<!--         <div class="rightRedF"> -->
<!--           <script type="text/javascript">showOrderState(${af.map.order_state},${af.map.pay_type},${af.map.order_type})</script> -->
<!--         </div> -->
      </div>
    </div>
  </div>
  
<!--   <div class="step2 border-1px"> -->
<!--     <div class="m step2-in "> -->
<!--       <div class="mt"> -->
<%--         <div class="s2-name"><i></i>${af.map.return_link_man}</div> --%>
<%--         <div class="s2-phone"><i></i>${af.map.return_tel}</div> --%>
<!--       </div> -->
<%--       <div class="mc step2-in-con">${af.map.hh_wl_company}&nbsp;${af.map.hh_wl_no}</div> --%>
<!--     </div> -->
<!--    <b class="s2-borderT"></b> <b class="s2-borderB"></b> </div> -->
   
   
  <div class="m step3 border-1px">
    <div class="mt cf"> <span class="shop-name">商品信息</span> </div>
    <div class="mc" id="commDetails"> 
<%--     <c:forEach items="${orderInfoDetailList}" var="cur" varStatus="vs"> --%>
     <c:url var="url" value="/m/MEntpInfo.do?id=${af.map.comm_id}" />
     <a onclick="goUrl('${url}')" class="a-link">
<%--      <c:set var="divClass" value="display:none;" /> --%>
<%--      <c:if test="${vs.count le 3}"> --%>
<%--      <c:set var="divClass" value="display:block;" /> --%>
<%--      </c:if> --%>
     
      <div class="s-item bdt-1px" style="display:block;">
        <div class="pdiv">
          <div class="sitem-l">
            <div class="sl-img-box">
              <div class="sl-img">
              <c:set var="imgSrc" value="${ctx}/styles/imagesPublic/user_header.png" />
		      <c:if test="${not empty main_pic}">
		         <c:set var="imgSrc" value="${ctx}/${main_pic}@s400x400" />
		      </c:if>
              <img src="${imgSrc}"></div>
            </div>
          </div>
          <div class="sitem-m">
            <p class="sitem-m-txt">
            ${orderInfoDetails.comm_name}
           <c:if test="${not empty orderInfoDetails.comm_tczh_name}"> &nbsp;[${orderInfoDetails.comm_tczh_name}] </c:if>
            </p>
            <p class="s3-num">x${orderInfoDetails.good_count}</p>
          </div>
          <div class="sitem-r">¥<fmt:formatNumber pattern="0.##" value="${orderInfoDetails.good_price}" /></div>
        </div>
      </div>
      </a>
<%--      </c:forEach> --%>
<%--      <c:set var="hideDetailsSize" value="${fn:length(orderInfoDetailList) - 3}" /> --%>
<%--      <c:if test="${fn:length(orderInfoDetailList) > 3}"> --%>
<%--       <div class="step3-more" id="step3-more" style="display: block;"><strong id="more_tip">还有<i>${fn:length(orderInfoDetailList) - 3}</i>件</strong><span class="s3-down" id="more_tip_class"></span> </div> --%>
<%-- 	</c:if> --%>

    </div>
  </div>
  <div class="m step4 border-1px">
    <div class="mt bdb-1px cf">
      <h2 class="invoice-left">售后状态</h2>
      <span class="invoice-right">
       <c:choose>
           <c:when test="${af.map.audit_state eq 0}">待审核</c:when>
           <c:when test="${af.map.audit_state eq 1}">商家通过</c:when>
           <c:when test="${af.map.audit_state eq 2}">平台审核通过</c:when>
           <c:when test="${af.map.audit_state eq -1}">商家审核不通过</c:when>
           <c:when test="${af.map.audit_state eq -2}">平台审核不通过</c:when>
       </c:choose>
      </span>
      </div>
<!--     <div class="mt bdb-1px cf"> -->
<!--       <h2 class="invoice-left">换货成功确认</h2> -->
<!--       <span class="invoice-right"> -->
<%--        <c:choose> --%>
<%--                   <c:when test="${af.map.is_confirm eq 0}">未确认</c:when> --%>
<%--                   <c:when test="${af.map.is_confirm eq 1}">已确认</c:when> --%>
<%--                 </c:choose> --%>
<!--       </span> -->
<!--       </div> -->
    <div class="mc">
      <div class="send01 bdb-1px change-p">
        <div class="distribe cf">
          <h3 class="invoice-left">售后订单信息</h3> </div>
        <div class="s4-con">
          <div class="s4-l">
            <p>售后订单添加时间：<fmt:formatDate value="${af.map.add_date}" pattern="yyyy-MM-dd HH:mm:ss" /></p>
          </div>
        </div>
        <div class="s4-con">
          <div class="s4-l">
            <p>退换货原因：${returnTypeName}</p>
          </div>
        </div>
        <c:if test="${not empty imgsList}">
        <div class="s4-con">
          <div class="s4-l"  >
            <p>
            <c:forEach items="${imgsList}" var="cur">
            <c:set var="imgSrc" value="${ctx}/${cur.file_path}@s400x400" />
            	<img width="45%" src="${imgSrc}">
            	</c:forEach>
            </p>
          </div>
        </div>
        </c:if>
      </div>
    </div>
  </div>
  <div class="btn-bar" id="btnBar">
    <div class="bb-info" id="bbInfo">
    
    
<%--      <c:choose> --%>
<%--       	<c:when test="${af.map.order_state eq 0}"> --%>
<%--        		<c:if test="${af.map.order_type eq 10 or af.map.order_type eq 11}"> --%>
<%--        		  <c:url var="payUrl" value="MMyCartInfo.do?method=selectPayType&trade_index=${af.map.trade_index}&pay_type=${af.map.pay_type}" /> --%>
<%--        		  <div class="bottom-but"><a href="javascript:goUrl('${payUrl}');" class="bb-btn1-red">付款</a></div> --%>
<%--        		</c:if> --%>
<%--        		<div class="bottom-but"><a href="javascript:updateState('MMyOrder.do', 'updateState','${af.map.id}', -10, this);" class="bb-btn1-box"><div class="bb-btn1">取消订单</div></a></div> --%>
<%--       	</c:when> --%>
<%--       	<c:when test="${af.map.order_state eq 10}"> --%>
<%--       		<c:if test="${af.map.order_type eq 10 or af.map.order_type eq 11}"> --%>
<%--       		 <div class="bottom-but"><a href="javascript:updateState('MMyOrder.do', 'updateState', '${af.map.id}', -20, this);" class="bb-btn1-red">售后</a></div> --%>
<%--       		</c:if> --%>
<%--       	</c:when> --%>
<%--       	<c:when test="${af.map.order_state eq 20}"> --%>
<%--       	    <div class="bottom-but"><a href="javascript:updateState('MMyOrder.do', 'updateState', '${af.map.id}', 40, this);" class="bb-btn1-box"><div class="bb-btn1">确认收货</div></a></div> --%>
<%--       	</c:when> --%>
<%--       	<c:when test="${af.map.order_state eq -10}"> --%>
<%--       	    <div class="bottom-but"><a href="javascript:confirmDelete(null,'MMyOrder.do','id=${af.map.id}&order_type=${af.map.order_type}&mod_id=${af.map.mod_id}&order_state=${af.map.order_state}');" class="bb-btn1-box"><div class="bb-btn1">删除订单</div></a></div> --%>
<%--       	</c:when> --%>
<%--       	<c:when test="${(af.map.order_state ge 20) && (af.map.order_type eq 11)}"> --%>
<%--       		<c:url var="returnUrl" value="MMyOrderReturn.do?method=list&id=${af.map.id}" /> --%>
<%--       		<div class="bottom-but"><a href="javascript:goUrl('${returnUrl}');" class="bb-btn1-red">售后</a></div> --%>
<%--       	</c:when> --%>
<%--       </c:choose> --%>
    </div>
  </div>
</div>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
// 	$("#step3-more").click(function(){
// 	  var obj = $(this).find("#more_tip_class");
	  
// 	  if($(obj).hasClass("s3-up")){
// 		  $(obj).removeClass("s3-up");
// 		  $("#more_tip").text("还有${hideDetailsSize}件");
// 		  $("#commDetails a").each(function(index){
// 			 if($(this).hasClass("a-link") && index > 2){
// 				 $(this).find("div:first-child").hide();
// 			 }
// 		  });
		  
// 	  }else{
// 		  $(obj).addClass("s3-up");
// 		  $("#more_tip").text("收起");
// 		  $("#commDetails a").each(function(){
// 			 if($(this).hasClass("a-link")){
// 				 $(this).find("div:first-child").show();
// 			 }
// 		  });
// 	  }
// 	});
	
});
//]]></script>
</body>
</html>

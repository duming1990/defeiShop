<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${header_title}-${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link href="${ctx}/m/styles/css/cp_style_v15.11.min.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.common-items .common-item{padding: .24rem .2rem .2rem .1rem;}
</style>
</head>
<body id="body">
<div id="wrap">
  <jsp:include page="../_header.jsp" flush="true" />
  <div id="notEmptyCart" style="margin-bottom: 55px;">
    <c:if test="${not empty cartInfoList}">
      <c:url var="url" value="MMyCartInfo.do" />
      <html-el:form action="${url}" styleClass="formOrder" method="post" >
        <html-el:hidden property="method" value="step1"/>
        <html-el:hidden property="isLeader" />
	    <html-el:hidden property="leaderOrderId" />
	    <html-el:hidden property="isPt" />
	    <html-el:hidden property="comm_id" />
        <div class="order-conent">
          <c:forEach items="${cartInfoList}" var="cart">
            <section class="common-items carts_tr">
              <div class="common-item"> 
                <div class="shp-chk">
                <c:if test="${cart.id eq cart_id}"> 
                  <span onclick="changeSelected(this,'${cart.id}');" id="cart_id" class="cart-checkbox checked" >
                  <input type="hidden" name="cart_ids" id="cart_ids" value="${cart.id}"/>
                </c:if>
                <c:if test="${!(cart.id eq cart_id) && not empty cart_id}"> 
                  <span onclick="changeSelected(this,'${cart.id}');" id="cart_id" class="cart-checkbox" >
                  <input type="hidden" name="cart_ids" id="cart_ids" value=""/>
                </c:if>
                <c:if test="${empty cart_id}"> 
                  <span onclick="changeSelected(this,'${cart.id}');" id="cart_id" class="cart-checkbox checked" >
                  <input type="hidden" name="cart_ids" id="cart_ids" value="${cart.id}"/>
                </c:if>
                   
                  </span> 
                </div>
              <span class="item-title" style="margin-left:0.2rem;"><a>${fn:escapeXml(cart.comm_name)}
                <c:if test="${not empty cart.map.commTip}">&nbsp;${cart.map.commTip}</c:if>
                <c:if test="${not empty cart.map.inventoryTip}"> <br/>
                  <span style="color:#FF621D;">${cart.map.inventoryTip}</span> </c:if>
                </a></span>
                <div class="item-content pro-total"> <a class="shp-cart-icon-remove" onClick="delCart(this,${cart.id});"></a> </div>
              </div>
              <div class="common-item">
              <span class="item-label">数量：</span>
                <div class="item-content pro-numsele">
                   <c:set var="buttonClass" value="amount_dis" />
                  <c:if test="${cart.pd_count gt 1}">
                    <c:set var="buttonClass" value="amount_do" />
                  </c:if>
                  <c:if test="${af.map.isPt ne 1 }">
	                  <button class="control_amount_sub ${buttonClass}" type="button" onClick="calcCartMoney($('#${cart.id}pd_count'),${cart.pd_price},${cart.id}, -1);">-</button>
                  </c:if>
                  <c:if test="${af.map.isPt ne 1}">
	                  <input class="buy_input" type="text" disabled="disabled" size="4" maxlength="4" value="${cart.pd_count}" id="${cart.id}pd_count" onBlur="calcCartMoney($('#${cart.id}pd_count'),${cart.pd_price},${cart.id},null);" />
                  </c:if>
                  <c:if test="${af.map.isPt eq 1}">
	                  <text>1</text>
                  </c:if>
                  <input type="hidden" name="pd_stock" id="pd_stock" value="${cart.map.pd_max_count}" id="max${cart.id}"/>
                  <input type="hidden" class="minSumPrice" id="${cart.id}minSumPrice" value="${(cart.pd_count * cart.pd_price)}"/>
                  <c:if test="${af.map.isPt ne 1}">
                  	<button class="control_amount_add amount_do" type="button" onClick="calcCartMoney($('#${cart.id}pd_count'),${cart.pd_price},${cart.id},1);">+</button>
                  </c:if>
                  <input type="hidden" name="comm_type" id="comm_type_${cart.id}" value="${cart.map.commInfo.comm_type}" />
                  <input type="hidden" name="cart_id" comm_type="${cart.map.commInfo.comm_type}" id="cart_id_${cart.id}" value="${cart.id}" />
                  <i></i> </div>
              </div>
              <div class="common-item"> <span class="item-label">单价：</span>
                <div class="item-content pro-total"> <b id="total_fee">¥
                  <fmt:formatNumber value="${cart.pd_price}" pattern="0.00"/>
                  </b> </div>
              </div>
            </section>
          </c:forEach>
          <div id="buybox">
            <div class="section-buybox" style="border-bottom: 1px solid #C6C6C6;padding-left:0.1rem;">
              <div class="shp-chk"> 
                <span onclick="selectAll(this);" class="cart-checkbox checked" id="checkIcon-1"></span> 
              </div>
              <div class="deal-buyatt">
                <p class="price">商品总额：<font>¥<b id="cart_oriPrice">
                <c:if test="${not empty cart_id}">
                  <fmt:formatNumber value="${chooseCart_money}" pattern="0.00"/>
                </c:if>
                <c:if test="${empty cart_id}">
                  <fmt:formatNumber value="${totalMoney}" pattern="0.00"/>
                </c:if>
                  </b></font></p>
              </div>
              <div class="deal-pay">
                 <input type="button" value="去结算" class="pay" onClick="submitThisForm();" id="submitForm"/>
              </div>
            </div>
          </div>
        </div>
      </html-el:form>
    </c:if>
  </div>
  <div id="bd" class="cf cart_empty" style="display:none;">
   <div>
	   <div class="allItemv2">
	   <div class="o-t-error">
	   <div class="img">
	   <img src="${ctx}/m/styles/img/cart_empty.png" />
	   </div>
	   <div class="info">
	   <h3 class="title">购物车快饿瘪了T.T</h3>
	   <p class="sub">主人快给我挑点宝贝吧</p>
	   <p class="btn">
	   <c:url var="url" value="/m/Index.do" />
	   <a onclick="goUrl('${url}')">去逛逛</a></p>
	   </div>
	   </div>
	   </div>
   </div>
 </div>
  <jsp:include page="../_footer.jsp" flush="true" />
</div>
<c:url var="urllist" value="/m/MMyCartInfo.do?method=step1" />
<script type="text/javascript" src="${ctx}/scripts/cart/cart.sourceMobile.js?20180315"></script>
<script type="text/javascript">//<![CDATA[
$(function(){
	
	 var topBtnUrl = "${urllist}";
	 setTopBtnUrl(topBtnUrl);
	
	 <c:if test="${empty cartInfoList}">
	 	$(".cart_empty").show();
	 </c:if>
	 
});
function submitThisForm(){
	$(".formOrder").get(0).submit();
}
//]]></script>
</body>
</html>

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
<link href="${ctx}/m/styles/css/cp_style_v15.11.min.css?20160305" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/commons/font-awesome/css/font-awesome.min.css"  />
<style type="text/css">
input.j_submit {
font-size: .3rem;
	font-weight:normal;
}
</style>
</head>
<body id="body">
<div id="wrap">
  <jsp:include page="../_header.jsp" flush="true" />
  <div id="notEmptyCart">
    <c:if test="${not empty cartInfoList}">
      <c:url var="url" value="MMyCartInfo.do" />
      <html-el:form action="${url}" styleClass="formOrder" method="post" >
        <html-el:hidden property="method" value="step1"/>
        <c:set var="commInfoCanBuy" value="1" />
        <div class="order-conent">
          <c:forEach items="${cartInfoList}" var="cart">
            <section class="common-items carts_tr">
              <div class="common-item"> <span class="item-title"><a>${fn:escapeXml(cart.comm_name)}
                <c:if test="${not empty cart.map.commTip}">&nbsp;${cart.map.commTip}</c:if>
                <c:if test="${not empty cart.map.inventoryTip}"> <br/>
                  <span style="color:#FF621D;">${cart.map.inventoryTip}</span> </c:if>
                </a></span>
                <div class="item-content pro-total"> <a class="shp-cart-icon-remove" onClick="delCart(this,${cart.id});"></a> </div>
              </div>
              <div class="common-item"> <span class="item-label">数量：</span>
                <div class="item-content pro-numsele">
                  <c:set var="buttonClass" value="amount_dis" />
                  <c:if test="${cart.pd_count gt 1}">
                    <c:set var="buttonClass" value="amount_do" />
                  </c:if>
                  <button class="control_amount_sub ${buttonClass}" type="button" onClick="calcCartMoney($('#${cart.id}pd_count'),${cart.pd_price},${cart.id}, -1);">-</button>
                  <input class="buy_input" type="text" size="4" maxlength="4" value="${cart.pd_count}" id="${cart.id}pd_count" onBlur="calcCartMoney($('#${cart.id}pd_count'),${cart.pd_price},${cart.id},null);" />
                  <input type="hidden" name="pd_stock" id="pd_stock" value="${cart.map.pd_max_count}" id="max${cart.id}"/>
                  <input type="hidden" class="minSumPrice" id="${cart.id}minSumPrice" value="${(cart.pd_count * cart.pd_price)}"/>
                  <button class="control_amount_add amount_do" type="button" onClick="calcCartMoney($('#${cart.id}pd_count'),${cart.pd_price},${cart.id},1);">+</button>
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
            <div class="section-buybox">
              <div class="deal-buyatt">
                <p class="price">商品总额：<font>¥<b id="cart_oriPrice">
                  <fmt:formatNumber value="${totalMoney}" pattern="0.00"/>
                  </b></font></p>
              </div>
              <div class="deal-pay">
                <c:if test="${lack_inventorty}">
                  <input type="button" value="去结算" class="pay" onClick="submitThisForm();"/>
                </c:if>
                <c:if test="${!lack_inventorty}">
                  <input type="button" value="去结算" class="pay disabled"/>
                </c:if>
              </div>
            </div>
          </div>
        </div>
      </html-el:form>
    </c:if>
  </div>
  <div id="bd" class="cf cart_empty" style="display:none;">
    <div class="message" style="text-align:center;padding: 0.2rem;margin: 0.2rem;">您的购物车还是空的</div>
    <div class="deal-btn" id="buybox">
      <c:url var="url" value="/m/Index.do" />
      <a onClick="goUrl('${url}')">
      <label>去逛逛</label>
      </a></div>
  </div>
  <jsp:include page="../_footer.jsp" flush="true" />
</div>
<c:url var="urllist" value="/m/MMyCartInfo.do" />
<script type="text/javascript" src="${ctx}/scripts/cart/cart.sourceMobile.js"></script>
<script type="text/javascript">//<![CDATA[
$(function(){
	
	 var topBtnUrl = "${urllist}";
	 setTopBtnUrl(topBtnUrl);
	
	 <c:if test="${empty cartInfoList}">
	 	$(".cart_empty").show();
	 	//Common.loading();
	 	//window.setTimeout(function () { 
	 	//	location.href="${ctx}/m/index.shtml";
	 	//}, 500);
	 </c:if>
	 
});

$("#chooseAddr .chooAddrli").each(function(index){
	$(this).click(function(){
		$(this).find("label").addClass("checked");
		$(this).siblings().find("label").removeClass("checked");
		$("#shipping_address_id").val($(this).attr("data-id")); 
		var shipping_address_id = $("#shipping_address_id").val();
	});
});


function submitThisForm(){ 
	$(".formOrder").get(0).submit();
}


//]]></script>
</body>
</html>

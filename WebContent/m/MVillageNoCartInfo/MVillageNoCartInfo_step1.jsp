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
<link href="${ctx}/m/styles/css/cp_style_v15.11.min.css?20160305" rel="stylesheet" type="text/css" />
<link href="${ctx}/m/styles/css/step1.css?20180314" rel="stylesheet" type="text/css" />
</head>
<body id="body">
<div id="wrap">
<jsp:include page="../_header.jsp" flush="true" />
<div id="notEmptyCart">
 <c:url var="url" value="MVillageNoCartInfo.do" />
  <html-el:form action="${url}" styleClass="formOrder" method="post" >
    <html-el:hidden property="method" value="step2"/>
    <html-el:hidden property="comm_id" />
    <html-el:hidden property="comm_tczh_id" />
    <html-el:hidden property="village_id" />
    <html-el:hidden property="shipping_address_id" styleId="shipping_address_id" value="${shipping_address_id}"/>
	<div class="order-conent">
	<c:if test="${not empty dftAddress}">
   <div class="order-address mui-flex align-center" onclick="getAddressList('${dftAddress.id}');">
	<div class="cell fixed align-center"><div class="icon"></div></div>
	<div class="cell content">
	 <div class="info">
	 <span>${fn:escapeXml(dftAddress.rel_name)}</span>
	 <span class="tel">${fn:escapeXml(dftAddress.rel_phone)}</span>
	 </div>
	  <div class="detail">
		 <span>${fn:escapeXml(dftAddress.map.full_addr)}</span>
	   </div>
	  </div>
	  <div class="cell fixed align-center"><div class="nav"></div></div>
	 </div>
	</c:if>
	<c:if test="${empty dftAddress}">
	 <div class="common-item" style="padding:.3rem 0;width:80%;margin:0 auto;">
		<c:url var="url" value="/m/MVillageNoCartInfo.do?method=addAddr&village_id=${af.map.village_id}&comm_id=${af.map.comm_id}&comm_tczh_id=${af.map.comm_tczh_id}"/>
		<input type="button" class="j_submit" value="增加收货地址"  name="sub" onclick="goUrl('${url}');"></div>
	</c:if>
	
   <c:url var="url" value="/m/MEntpInfo.do?id=${af.map.comm_id}" />
	<section class="common-items carts_tr">
			<div class="common-item">
				<span class="item-title" style="max-width:100%;"><a href="${url}">${fn:escapeXml(af.map.comm_name)}
				<c:if test="${not empty commTczhAttribute.attr_name}">(${commTczhAttribute.attr_name})</c:if>
				</a></span>
			</div>
		     <div class="common-item">
				<span class="item-label">数量：</span>
				<div class="item-content pro-numsele">
				
					    <c:set var="buttonClass" value="amount_dis" />
						<c:if test="${af.map.pd_count gt 1}">
					     <c:set var="buttonClass" value="amount_do" />
						</c:if>	
						<button class="control_amount_sub ${buttonClass}" type="button" onclick="calcCartMoney($('#${af.map.id}pd_count'),${af.map.pd_price},${af.map.id}, -1);">-</button>
						<c:if test="${not empty af.map.pd_count}"><input class="buy_input" type="text" size="4" maxlength="4" value="${af.map.pd_count}" id="${af.map.id}pd_count" name="pd_count" onblur="calcCartMoney($('#${af.map.id}pd_count'),${af.map.pd_price},${af.map.id},null);" /></c:if>
						<c:if test="${empty af.map.pd_count}"><input class="buy_input" type="text" size="4" maxlength="4" value="1" id="${af.map.id}pd_count" name="pd_count" onblur="calcCartMoney($('#${af.map.id}pd_count'),${af.map.pd_price},${af.map.id},null);" /></c:if>
						<input type="hidden" name="pd_stock" id="pd_stock" value="${af.map.map.pd_max_count}" id="max${af.map.id}"/> 
              	        <input type="hidden" class="minSumPrice" id="${af.map.id}minSumPrice" value="${(af.map.pd_count * af.map.pd_price)}"/> 
						<button class="control_amount_add amount_do" type="button" onclick="calcCartMoney($('#${af.map.id}pd_count'),${af.map.pd_price},${af.map.id},1);">+</button>
						<i></i>
				</div>
			</div>
			<div class="common-item">
				<span class="item-label">单价：</span>
				<div class="item-content pro-total">
					<b id="total_fee">¥<fmt:formatNumber value="${af.map.pd_price}" pattern="0.00"/></b>
					<c:if test="${pay_type_is_audit_success}">
					<c:if test="${dianzibi_to_rmb lt order_money}">
		               <p class="pay-total" style="font-size:12px;font-weight:normal;">余额不足，请 
		               <span class="label label-danger" style="cursor: pointer;" onclick="gotoChongZhi(this);" data-mod-id="1100400400">充值</span></p>
		            </c:if>
		            </c:if>
					<c:if test="${(!pay_type_is_audit_success) and (userInfo.is_entp eq 1 or userInfo.is_fuwu eq 1)}">
					<c:if test="${dianzibi_to_rmb lt order_money}">
		               <p class="pay-total" style="font-size:12px;font-weight:normal;">余额不足，请 
		               <span class="label label-danger" style="cursor: pointer;" onclick="gotoChongZhi(this);" data-mod-id="1100400400">充值</span></p>
		            </c:if>
		            </c:if>
				</div>
			</div>
	</section>
	<section class="common-items">
		<div class="common-item" id="bind_change">
			<a href="javascript:void(0);">
				<span class="item-label">您绑定的手机：</span>
				<div class="item-content">${userInfo.mobile}</div>
			</a>
		</div>
	</section>
		
	<div id="buybox">
				<div class="section-buybox">
				<div class="deal-buyatt">
					<p class="price">
						商品总额：<font>¥<b id="cart_oriPrice"><fmt:formatNumber value="${totalMoney}" pattern="0.00"/></b></font></p>
				</div>
				<div class="deal-pay">
					<input type="button" value="提交订单" id="submitOrder" class="pay" onclick="submitThisForm();"/>
				</div>
			</div>
	</div>
	</div>
 </html-el:form>
 </div>
<jsp:include page="../_footer.jsp" flush="true" />	
</div>
<script type="text/javascript" src="${ctx}/scripts/cart/cart.sourceMobileType3.js"></script>
<script type="text/javascript">//<![CDATA[
$(function(){
	<c:if test="${empty dftAddress}">
	   $("#submitOrder").val("添加地址").addClass("disabled").removeAttr("onclick");
	 </c:if>
});

function submitThisForm(){ 
	$("#submitOrder").val("提交中").addClass("disabled").removeAttr("onclick");
	$(".formOrder").get(0).submit();
}
function getAddressList(shipping_address_id){
	var village_id="${af.map.village_id}";
	var comm_id="${af.map.comm_id}";
	var comm_tczh_id="${af.map.comm_tczh_id}";
	Common.loading();
	window.setTimeout(function () {
		location.href="${ctx}/m/MVillageNoCartInfo.do?method=addressList&shipping_address_id=" + shipping_address_id+"&comm_id="+comm_id+"&village_id="+village_id+"&comm_tczh_id="+comm_tczh_id;
	}, 1000);
}

//]]></script>
</body>
</html>
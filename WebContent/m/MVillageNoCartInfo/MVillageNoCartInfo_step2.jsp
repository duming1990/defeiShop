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
<link href="${ctx}/m/styles/css/cp_style_v15.11.min.css" rel="stylesheet" type="text/css" />
</head>
<body id="body">
<div id="wrap">
<jsp:include page="../_header.jsp" flush="true" />
 <c:url var="url" value="MVillageNoCartInfo.do" />
  <html-el:form action="${url}" styleClass="formOrder" method="post" >
   <jsp:include page="../_public_pay_pass.jsp" flush="true" />
    <input type="hidden" name="method" value="step3" />
	<input type="hidden" name="out_trade_no" value="${out_trade_no}" />
	<input type="password" name="pay_password" id="pay_password" style="display:none;"/>  
	<div class="order-conent">
		<div class="pay-methods">
		<div class="normal-fieldset">
			<section class="common-items common-radio-box" id="choosePayTpye">
			     <c:if test="${is_open_pay_alipay && !isWeixin}">
					<div class="common-item">
						<label>
							<font class="o-icon o-alipay" color="#333">支付宝支付</font>
							<input type="radio" name="pay_type" value="1"/>
							<i></i>
						</label>
					</div>
				  </c:if>
				  <c:if test="${is_open_pay_weixin}">
				  <c:if test="${isWeixin  or isApp}">
					<div class="common-item">
						<label class="checked">
						<font class="o-icon o-weixin" color="#333">微信支付</font>
							<input type="radio" name="pay_type" value="3" checked="checked" />
							<i></i>
						</label>
					</div>
				   </c:if>
				   </c:if>
				</section>
		</div>
	</div>
	<div id="showBiDianzi" style="display:none;">
	<section class="common-items">
		<div class="common-item sel" id="bind_change">
			<a href="javascript:void(0);">
				<span class="item-label">余额：</span>
				<div class="item-content">
				<b id="total_fee">¥<fmt:formatNumber value="${dianzibi_to_rmb}" pattern="0.00"/></b>
				</div>
			</a>
		</div>
	</section>
	</div>
	<div id="buybox">
		<div class="section-buybox">
		<div class="deal-buyatt">
			<p class="price">
				商品总额：<font>¥<b id="cart_oriPrice"><fmt:formatNumber value="${order_money}" pattern="0.00"/></b></font></p>
		</div>
		<div class="deal-pay">
		<c:if test="${canPay}">
			<input type="button" value="去支付" class="pay" onclick="showPayPassTip();" id="J-order-pay-button"/></c:if>
		<c:if test="${!canPay}">
			<input type="button" value="去支付" class="pay disabled" id="J-order-pay-button" />
	    </c:if>  
		</div>
	</div>
	</div>
	</div>
 </html-el:form>
<jsp:include page="../_footer.jsp" flush="true" />	
</div>
<c:set var="tip_msg" value=""/>
<c:url var="tip_url" value=""/>
<c:if test="${empty userInfo.password_pay}">
<c:set var="tip_msg" value="尊敬的用户，为了您的资金支付安全，请前往安全中心维护支付密码"/>
<c:url var="tip_url" value="/m/MMySecurityCenter.do?mod_id=1100620100"/>
</c:if>
<script type="text/javascript" src="${ctx}/scripts/cart/cart.sourceMobile.js"></script>
<script type="text/javascript">//<![CDATA[
$(function(){
	
	<c:if test="${not empty tip_msg}">
		Common.confirm("${tip_msg}",["确定","取消"],function(){
			location.href="${tip_url}";
		},function(){
		});	
    </c:if>
    <c:if test="${!isWeixin}">
	   $("#payType1").find("label").addClass("checked");
	   $("#payType1").siblings().find("label").removeClass("checked");
	   $("#payType1").find("input").attr("checked","checked");
	   $("#payType1").siblings().find("input").removeAttr("checked");
	</c:if>	
    <c:if test="${isWeixin}">
	   $("#payType3").find("label").addClass("checked");
	   $("#payType3").siblings().find("label").removeClass("checked");
	   $("#payType3").find("input").attr("checked","checked");
	   $("#payType3").siblings().find("input").removeAttr("checked");
	</c:if>	
	
	$("#choosePayTpye .common-item").each(function(index){
		$(this).click(function(){
			var orderMoney = parseFloat("${order_money}");
			var hasdzbMoney = parseFloat("${dianzibi_to_rmb}");
			$(this).find("label").addClass("checked");
			$(this).siblings().find("label").removeClass("checked");
			$(this).find("input").attr("checked","checked");
			$(this).siblings().find("input").removeAttr("checked");
			var inputVal = $(this).find("input").val();
			if(inputVal == 0){
				$("#showBiDianzi").show();
				if(hasdzbMoney < orderMoney){
					$("#J-order-pay-button").addClass("disabled").removeAttr("onclick");
				}else{
					$("#J-order-pay-button").removeClass("disabled").attr("onclick","showPayPassTip()");
				}
			}else{
				$("#showBiDianzi").hide();
				$("#J-order-pay-button").removeClass("disabled").attr("onclick","showPayPassTip()");
			}
		});
	});
	
	
});

function showPayPassTip(){
	$(".ftc_wzsf").show();
	initPass();
}

function submitThisForm(){ 
	var pay_password = $("#pay_password").val();
	if(null == pay_password || '' == pay_password){
		mui.toast("请输入支付密码",1000);
		return false;
	}
	Common.loading();
	window.setTimeout(function () {
	  $("#J-order-pay-button").addClass("btn-disabled").removeAttr("onclick");
	  $(".formOrder").get(0).submit();
	}, 1000);
}

//]]></script>
</body>
</html>
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
 <c:url var="url" value="MActivity.do" />
  <html-el:form action="${url}" styleClass="formOrder" method="post" >
    <jsp:include page="../_public_pay_pass.jsp" flush="true" />
    <input type="hidden" name="method" value="pay" />
	<input type="hidden" name="out_trade_no" value="${out_trade_no}" /> 
	<input type="hidden" name="trade_index" value="${trade_index}" /> 
	<input type="hidden" name="openid" value="${openid}" /> 
	<input type="hidden" name="order_money" id="order_money" value="${order_money}" /> 
	<input type="hidden" id="pay_type" name="pay_type" value="1" />
	<input type="password" name="pay_password" id="pay_password" style="display:none;"/>
	<html-el:hidden property="use_yue_dikou" styleId="use_yue_dikou" value="0"/>
	<div class="order-conent">
		<div class="pay-methods">
		<div class="normal-fieldset">
			<section class="common-items common-radio-box" id="choosePayTpye">
			
			<c:if test="${not empty userInfo }">
			<div class="common-item" id="payType0">
					<label id="pay_type_0">
						<font class="o-icon o-chuxu" color="#333">余额   </font>
<%-- 						<sapn> ${bi_dianzi}￥</sapn> --%>
						&nbsp;&nbsp;<font class="" color="grey">￥	${bi_dianzi}</font>
						<input type="radio"  value="0" checked="checked"/>
						<i></i>
					</label>
				</div>
				
				
				</c:if>
				
				<c:if test="${!isWeixin }">
				<div class="common-item" id="payType1">
					<label id="pay_type_1">
						<font class="o-icon o-alipay" color="#333">支付宝支付</font>
						<input type="radio"  value="1" checked="checked"/>
						<i></i>
					</label>
				</div>
				</c:if>
				
				<c:if test="${isWeixin}">
				<div class="common-item" id="payType3">
					<label id="pay_type_4">
					<font class="o-icon o-weixin" color="#333">微信支付</font>
						<input type="radio" value="4"/>
						<i></i>
					</label>
				</div>
				</c:if>
				</section>
		</div>
	</div>
	
	<div id="buybox">
		<div class="section-buybox">
		<div class="deal-buyatt">
			<p class="price">
				支付金额：<font>¥<b id="cart_oriPrice"><fmt:formatNumber value="${order_money}" pattern="0.00"/></b></font></p>
		</div>
		<div class="deal-pay">
			<input type="button" value="去支付" class="pay" onclick="submitThisForm();" id="J-order-pay-button"/>
		</div>
	</div>
	</div>
	</div>
 </html-el:form>
<%-- <jsp:include page="../_footer.jsp" flush="true" />	 --%>
</div>
<c:set var="tip_msg" value=""/>
<c:url var="tip_url" value=""/>
<c:if test="${empty userInfo.password_pay}">
<c:set var="tip_msg" value="尊敬的用户，为了您的资金支付安全，请前往安全中心维护支付密码"/>
<c:url var="tip_url" value="/m/MMySecurityCenter.do?method=setPasswordPay"/>
</c:if>
<script type="text/javascript" src="${ctx}/scripts/cart/cart.sourceMobile.js"></script>
<script type="text/javascript">//<![CDATA[
                                          
var isWeixin = "${isWeixin}";
if(isWeixin ==  "true"){
	$("#pay_type_4").addClass("checked");
	$("#pay_type").val(4);
}else{
	$("#pay_type_1").addClass("checked");
	
	$("#pay_type").val(1);
}
                                          
$(function(){
	
	$("#choosePayTpye .common-item").each(function(index){
		$(this).click(function(){
			var orderMoney = parseFloat("${order_money}");
			$(this).find("label").addClass("checked");
			$(this).siblings().find("label").removeClass("checked");
			$(this).find("input").attr("checked","checked");
			$(this).siblings().find("input").removeAttr("checked");
			var inputVal = $(this).find("input").val();
			$("#pay_type").val(inputVal);
		
				$("#showBiDianzi").hide();
				$("#showBiHuokuan").hide();
// 				$("#J-order-pay-button").removeClass("disabled").attr("onclick","showPayPassTip()");
		});
	});
});
function submitThisForm(){
	var pay_type = $("#pay_type").val();
	if(pay_type == 0){
		var pay_money = parseFloat("${order_money}");
		var bi_dianzi = parseFloat("0");
		
		<c:if test="${not empty userInfo}">
			bi_dianzi =parseFloat("${userInfo.bi_dianzi}");
		</c:if>
		
		if(bi_dianzi < pay_money){
			mui.alert("余额不足");
			return false;
			
		}
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
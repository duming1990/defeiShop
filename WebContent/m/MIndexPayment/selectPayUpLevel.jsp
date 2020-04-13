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
 <c:url var="url" value="MIndexPayment.do" />
  <html-el:form action="${url}" styleClass="formOrder" method="post" >
     <input type="hidden" name="method" value="${pay_method}" />
     <input type="hidden" name="link_id" value="${af.map.link_id}"/>
     <input type="hidden" id="pay_type" value="3" />
	<div class="order-conent">
		<div class="pay-methods">
		<div class="normal-fieldset">
			<section class="common-items common-radio-box" id="choosePayTpye">
			     <c:if test="${is_open_pay_alipay && !isWeixin}">
					<div class="common-item" id="payType1">
						<label>
							<font class="o-icon o-alipay" color="#333">支付宝支付</font>
							<input type="radio" name="pay_type" value="1"/>
							<i></i>
						</label>
					</div>
				</c:if>
				<c:if test="${is_open_pay_weixin}">
					<c:if test="${isWeixin or isApp}">
					<div class="common-item">
						<label class="checked">
						<font class="o-icon o-weixin" color="#333">微信支付</font>
							<input type="radio" name="pay_type" value="3" checked="checked"/>
							<i></i>
						</label>
					</div>
					</c:if>
				</c:if>
				</section>
		</div>
	</div>
	<div id="buybox">
		<div class="section-buybox">
		<div class="deal-buyatt">
			<p class="price">
				应付金额：<font>¥<b id="cart_oriPrice"><fmt:formatNumber value="${order_money}" pattern="0.00"/></b></font></p>
		</div>
		<div class="deal-pay">
			<input type="button" value="去支付" class="pay" onclick="submitThisForm();" id="J-order-pay-button"/>
		</div>
	</div>
		
	</div>
	</div>
 </html-el:form>
<jsp:include page="../_footer.jsp" flush="true" />	
</div>
<script type="text/javascript">//<![CDATA[
$(function(){
	
	<c:if test="${!isWeixin}">
	   $("#pay_type").val(1);
	   $("#payType1").find("label").addClass("checked");
	   $("#payType1").siblings().find("label").removeClass("checked");
	   $("#payType1").find("input").attr("checked","checked");
	   $("#payType1").siblings().find("input").removeAttr("checked");
	</c:if>	
	
	<c:if test="${isWeixin}">
	   $("#pay_type").val(3);
	   $("#payType3").find("label").addClass("checked");
	   $("#payType3").siblings().find("label").removeClass("checked");
	   $("#payType3").find("input").attr("checked","checked");
	   $("#payType3").siblings().find("input").removeAttr("checked");
	</c:if>	
	
	$("#choosePayTpye .common-item").each(function(index){
		$(this).click(function(){
			$(this).find("label").addClass("checked");
			$(this).siblings().find("label").removeClass("checked");
			$(this).find("input").attr("checked","checked");
			$(this).siblings().find("input").removeAttr("checked");
			var inputVal = $(this).find("input").val();
			$("#pay_type").val(inputVal);
			$("#J-order-pay-button").removeClass("disabled").attr("onclick","submitThisForm()");
		});
	});
	
});
function submitThisForm(){ 
	if(${isApp eq true}){//app
		//这个地方为了不更新app payType =4修改成为payType = 3
		if(pay_type == 4){
			pay_type = 3;
		}
		var pay_type=$("#pay_type").val();
		var trade_no="${out_trade_no}";
		location.href="/login.html?method=forAppPay&"+${userInfo.id}+"&"+trade_no+"&"+pay_type+"&"+${order_money};	
	}else{
		Common.loading();
		window.setTimeout(function () {
		  $("#J-order-pay-button").addClass("btn-disabled").removeAttr("onclick");
		  $(".formOrder").get(0).submit();
		}, 1000);
	}
}

//]]></script>
</body>
</html>
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
 <c:url var="url" value="MMyCartInfo.do" />
  <html-el:form action="${url}" styleClass="formOrder" method="post" >
    <jsp:include page="../_public_pay_pass.jsp" flush="true" />
    <input type="hidden" name="method" value="step3" />
	<input type="hidden" name="out_trade_no" value="${out_trade_no}" /> 
	<input type="hidden" name="trade_index" value="${trade_index}" /> 
	<input type="hidden" name="order_money" id="order_money" value="${order_money}" /> 
	<input type="hidden" id="pay_type" value="1" />
	<input type="password" name="pay_password" id="pay_password" style="display:none;"/>
	<html-el:hidden property="use_yue_dikou" styleId="use_yue_dikou" value="0"/>
	<div class="order-conent">
		<div class="pay-methods">
		<div class="normal-fieldset">
			<section class="common-items common-radio-box" id="choosePayTpye">
		        <c:if test="${is_open_pay_alipay && !isWeixin}">
				<div class="common-item" id="payType1">
					<label>
						<font class="o-icon o-alipay" color="#333">支付宝支付</font>
						<input type="radio" name="pay_type" value="1" checked="checked"/>
						<i></i>
					</label>
				</div>
				</c:if>
			   <c:if test="${is_open_pay_weixin}">
			    <c:if test="${isWeixin or isApp}">
<!-- 				<div class="common-item" id="payType3"> -->
<!-- 					<label> -->
<!-- 					<font class="o-icon o-weixin" color="#333">微信支付</font> -->
<!-- 						<input type="radio" name="pay_type" value="3"/> -->
<!-- 						<i></i> -->
<!-- 					</label> -->
<!-- 				</div> -->
				<!-- 通联支付 -->
				<div class="common-item" id="payType4">
					<label>
					<font class="o-icon o-weixin" color="#333">支付</font>
						<input type="radio" name="pay_type" value="4"/>
						<i></i>
					</label>
				</div>
			   </c:if>
			   </c:if>
				<div class="common-item" id="payType0" style="display: none;">
					<label>
					<font class="o-icon o-chuxu" color="#333">余额</font>
						<input type="radio" name="pay_type" value="0" />
						<i></i>
					</label>
				</div>
				</section>
		</div>
	</div>
	<c:if test="${userInfo.bi_dianzi gt 0}">
	<div style="margin-top: -7px;">
	<section class="common-items">
		<div class="common-item sel" id="bind_change">
			<a href="javascript:void(0);">
				<span class="item-label">账户总额：</span>
				<div class="item-content" style="text-align: left;">
					<b>¥<fmt:formatNumber value="${dianzibi_to_rmb}" pattern="0.00"/></b>
				</div>
				<span class="item-label">含福利金：</span>
				<div class="item-content" style="text-align: left;">
					<b>¥<fmt:formatNumber value="${userInfo.bi_welfare}" pattern="0.00"/></b>
				</div>
			</a>
		</div>
		<fmt:formatNumber var="yue_dikou" value="${userInfo.bi_dianzi}" pattern="0.##"/>
		<c:if test="${(userInfo.bi_dianzi - order_money) ge 0}">
		<fmt:formatNumber var="yue_dikou" value="${order_money}" pattern="0.##"/>
		</c:if>
		<div class="common-item sel" id="bind_change">
			<a href="javascript:void(0);">
				<span class="item-label">可抵扣（优先使用福利金）：</span>
				<div class="item-content" style="text-align: left;">
				<b>¥<span id="kedi_money"><fmt:formatNumber value="${yue_dikou}" pattern="0.00"/></span></b>
				</div>
			</a>
		</div>
	</section>
	</div>
    <div style="display: -webkit-box;float: right;">
    <span style="line-height: 34px;min-width: 1.3rem;color: #8D8D8D;font-size: .25rem;">是否使用抵扣：</span>
     <div class="yu_e" style="margin-right: 5px;">
       <div class="mui-switch mui-switch-mini" style="box-sizing: border-box;">
		<div class="mui-switch-handle"></div>
	 </div>
     </div>
    </div>
    </c:if>
	<div id="buybox">
		<div class="section-buybox">
		<div class="deal-buyatt">
			<p class="price">
				支付金额：<font>¥<b id="cart_oriPrice"><fmt:formatNumber value="${order_money}" pattern="0.00"/></b></font></p>
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
<c:url var="tip_url" value="/m/MMySecurityCenter.do?method=setPasswordPay"/>
</c:if>
<script type="text/javascript" src="${ctx}/scripts/cart/cart.sourceMobile.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript">//<![CDATA[
$(function(){
	updateOrder();
	
	<c:if test="${order_money le 0}">
	$("#pay_type").val(0);
	</c:if>
	<c:if test="${!isWeixin}">
	   $("#pay_type").val(1);
	   $("#payType1").find("label").addClass("checked");
	   $("#payType1").siblings().find("label").removeClass("checked");
	   $("#payType1").find("input").attr("checked","checked");
	   $("#payType1").siblings().find("input").removeAttr("checked");
	</c:if>	
	
	<c:if test="${isWeixin}">
	   $("#pay_type").val(4);
	   $("#payType4").find("label").addClass("checked");
	   $("#payType4").siblings().find("label").removeClass("checked");
	   $("#payType4").find("input").attr("checked","checked");
	   $("#payType4").siblings().find("input").removeAttr("checked");
	</c:if>	
	
	<c:if test="${not empty tip_msg}">
		Common.confirm("${tip_msg}",["确定","取消"],function(){
			location.href="${tip_url}";
		},function(){
		});	
    </c:if>
    <c:if test="${empty userInfo.mobile}">
	Common.confirm("请先绑定手机号码",["确定","取消"],function(){
		location.href="${ctx}/m/MMySecurityCenter.do?method=setMobile";
	},function(){
	});	
</c:if>
	
	
	
	$("#choosePayTpye .common-item").each(function(index){
		$(this).click(function(){
			var orderMoney = parseFloat("${order_money}");
			var hashkbMoney = parseFloat("${huoKuanBi_to_rmb}");
			var hasdzbMoney = parseFloat("${dianzibi_to_rmb}");
			$(this).find("label").addClass("checked");
			$(this).siblings().find("label").removeClass("checked");
			$(this).find("input").attr("checked","checked");
			$(this).siblings().find("input").removeAttr("checked");
			var inputVal = $(this).find("input").val();
			$("#pay_type").val(inputVal);
			if(inputVal == 4){
				$("#showBiHuokuan").show();
				$("#showBiDianzi").hide();
				if(hashkbMoney < orderMoney){
					$("#J-order-pay-button").addClass("disabled").removeAttr("onclick");
				}else{
					$("#J-order-pay-button").removeClass("disabled").attr("onclick","showPayPassTip()");
				}
			}
			if(inputVal == 0){
				$("#showBiDianzi").show();
				$("#showBiHuokuan").hide();
				if(hasdzbMoney < orderMoney){
					$("#J-order-pay-button").addClass("disabled").removeAttr("onclick");
				}else{
					$("#J-order-pay-button").removeClass("disabled").attr("onclick","showPayPassTip()");
				}
			}else{
				$("#showBiDianzi").hide();
				$("#showBiHuokuan").hide();
				$("#J-order-pay-button").removeClass("disabled").attr("onclick","showPayPassTip()");
			}
		});
	});
	
	
});


function showPayPassTip(){
	var trade_index="${trade_index}";
	$.ajax({
		type: "POST" , 
		url: "${ctx}/m/MMyCartInfo.do", 
		data:"method=havingReloadPay&trade_index="+trade_index,
		dataType: "json", 
        error: function (request, settings) {alert(" 数据加载请求失败！ ");	$(".pay").attr("disabled", "true");}, 
        success: function (data) {
        	if(data.code == 0){
        		var pay_type=$("#pay_type").val();
        		if(pay_type == 0){
        			$(".ftc_wzsf").show();
        			initPass();
        		}else{
        			submitThisForm();
        		}
        	}else if(data.code == 2){
        		$("#J-order-pay-button").addClass("disabled").removeAttr("onclick");
        		Common.confirm(data.msg, ["我的订单"], function() {
					var url = "${ctx}"+"/m/MMyOrder.do?method=list&order_type=100";
					window.location.href = url;
				}, function() {});
        	}else{
        		$("#J-order-pay-button").addClass("disabled").removeAttr("onclick");
        		Common.confirm(data.msg, ["重新支付"], function() {
					var url = "${ctx}"+"/m/MMyOrder.do";
					window.location.href = url;
				}, function() {});
        	}
        }
	});
}

mui('.yu_e .mui-switch').each(function() {
	this.addEventListener('toggle', function(event) {
		var yu_e=parseFloat("${userInfo.bi_dianzi}");
		var order_money=$("#order_money").val();
		var trade_index="${trade_index}";
		if(event.detail.isActive){//使用余额抵扣
			
			$.ajax({
				type: "POST" , 
				url: "${ctx}/CsAjax.do", 
				data:"method=userYuE&trade_index="+trade_index+"&order_money="+order_money,
				dataType: "json", 
		        async: true, 
		        error: function (request, settings) {alert(" 数据加载请求失败！ ");	$(".pay").attr("disabled", "true");}, 
		        success: function (data) {
					if (data.ret == 1) {
						$("#use_yue_dikou").val(1);
						if(yu_e >= order_money){//用户余额大于订单金额
							$("#pay_type").val(0);//支付方式调整为余额支付
							$("#payType0").show();
							$("#payType1").hide();
							$("#payType3").hide();
							$("#payType4").hide();
							$("#cart_oriPrice").text("0.00");
							
							$("#payType0").find("label").addClass("checked");
							$("#payType0").siblings().find("label").removeClass("checked");
							$("#payType0").find("input").attr("checked","checked");
							$("#payType0").siblings().find("input").removeAttr("checked");
						}else{
							$("#cart_oriPrice").text(parseFloat(order_money - yu_e).toFixed(2));
						}
					} else {
						mui.toast(data.msg,1000);
						$("#J-order-pay-button").addClass("disabled").removeAttr("onclick");
					} 
		        }
			});
			
		}else{//不使用余额抵扣
			$.ajax({
				type: "POST" , 
				url: "${ctx}/CsAjax.do", 
				data:"method=notUserYuE&trade_index="+trade_index+"&order_money="+order_money,
				dataType: "json", 
		        async: true, 
		        error: function (request, settings) {alert(" 数据加载请求失败！ ");	$(".pay").attr("disabled", "true");}, 
		        success: function (data) {
					if (data.ret == 1) {
						$("#use_yue_dikou").val(0);
						$("#cart_oriPrice").text(parseFloat(order_money).toFixed(2));
						if(yu_e >= order_money){//用户余额大于订单金额
							
							$("#payType0").hide();
							$("#payType1").show();
							$("#payType3").show();
							$("#payType4").show();
							
							<c:if test="${!isWeixin}">
							$("#pay_type").val(1);//支付方式调整为支付宝支付
							$("#payType1").find("label").addClass("checked");
							$("#payType1").siblings().find("label").removeClass("checked");
							$("#payType1").find("input").attr("checked","checked");
							$("#payType1").siblings().find("input").removeAttr("checked");
							</c:if>	
							
							<c:if test="${isWeixin}">
							$("#pay_type").val(4);//支付方式调整为微信支付
							$("#payType4").find("label").addClass("checked");
							$("#payType4").siblings().find("label").removeClass("checked");
							$("#payType4").find("input").attr("checked","checked");
							$("#payType4").siblings().find("input").removeAttr("checked");
							</c:if>	
						}
					} else {
						mui.toast(data.msg,1000);
						$("#J-order-pay-button").addClass("disabled").removeAttr("onclick");
					} 
		        }
			});
		}
	});
});
function updateOrder(){
	var yu_e=parseFloat("${userInfo.bi_dianzi}");
	var trade_index="${trade_index}";
	$.ajax({
		type: "POST" , 
		url: "${ctx}/CsAjax.do", 
		data:"method=notUserYuE&trade_index="+trade_index,
		dataType: "json", 
        async: true, 
        error: function (request, settings) {alert(" 数据加载请求失败！ ");	$(".pay").attr("disabled", "true");}, 
        success: function (data) {
			if (data.ret = 1) {
				var order_money=parseFloat(data.order_money);
				$("#use_yue_dikou").val(0);
				$("#order_money").val(data.order_money);
				$("#cart_oriPrice").text(order_money.toFixed(2));
				
				if(yu_e >= order_money){
					$("#kedi_money").text(order_money.toFixed(2));
				}else{
					$("#kedi_money").text(yu_e.toFixed(2));
				}
			} else {
				mui.toast(data.msg,1000);
				$("#J-order-pay-button").addClass("disabled").removeAttr("onclick");
			} 
        }
	});
}

function submitThisForm(){ 
	var pay_type=$("#pay_type").val();
	var pay_password = $("#pay_password").val();
	
	if(pay_type == 0){
		if(null == pay_password || '' == pay_password){
			mui.toast("请输入支付密码",1000);
			return false;
		}
	}
	
	
	var trade_no="${out_trade_no}";
	if(pay_type == 0){
		Common.loading();
		window.setTimeout(function () {
		  $("#J-order-pay-button").addClass("btn-disabled").removeAttr("onclick");
		  $(".formOrder").get(0).submit();
		}, 1000);
	}else{
		if(${not empty isApp}){
			//这个地方为了不更新app payType =4修改成为payType = 3
			if(pay_type == 4){
				pay_type = 3;
			}
			location.href="/login.html?method=forAppPay&"+${userInfo.id}+"&"+trade_no+"&"+pay_type+"&"+${order_money};	
		}else{
			Common.loading();
			window.setTimeout(function () {
			  $("#J-order-pay-button").addClass("btn-disabled").removeAttr("onclick");
			  $(".formOrder").get(0).submit();
			}, 1000);
		}
	}
}

//]]></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="MSThemeCompatible" content="no" />
<meta name="MSSmartTagsPreventParsing" content="true" />
<meta name="Description" content="${app_name}" />
<meta name="Keywords" content="${app_name}," />
<title>在线支付 - ${app_name}</title>
<link href="${ctx}/styles/payment/main.css" rel="stylesheet" type="text/css" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<div class="p-header">
  <div class="w">
    <div id="logo"> <img width="170" src="${ctx}/styles/imagesPublic/logo_m.png" /> </div>
  </div>
</div>
<div class="main">
  <div class="w">
    <div class="order">
      <div class="o-left">
        <h3 class="o-title"> 请您及时付款，以便订单尽快处理！    		           	订单号：${out_trade_no} </h3>
        <p class="o-tips"> 请您在提交订单后<span class="font-red">24小时</span>内完成支付，否则订单会自动取消。 </p>
      </div>
      <div class="o-right">
        <div class="o-price"> <em>应付金额</em><strong>${order_money}</strong><em>元</em> </div>
      </div>
      <div class="clr"></div>
    </div>
    <!-- order 订单信息 end -->
    <!-- order 订单信息 end -->
    <!-- payment 支付方式选择 -->
    <div class="payment">
      <!-- 微信支付 -->
      <div class="pay-weixin">
        <div class="p-w-hd">微信支付</div>
        <div class="p-w-bd">
          <div class="p-w-box">
            <div class="pw-box-hd"> <img src="${ctx}/QrCodeForWeixin.jpg?code_url=${code_url}" width="300" height="300" /> </div>
            <div class="pw-box-ft">
              <p>请使用微信扫一扫</p>
              <p>扫描二维码支付</p>
            </div>
          </div>
          <div class="p-w-sidebar"></div>
        </div>
      </div>
      <!-- 微信支付 end -->
    </div>
    <!-- payment 支付方式选择 end -->
  </div>
</div>
 <c:set var="userType" value="${userInfo.user_type}" />
 <c:url var="urlgo" value="/manager/customer/index.shtml" />
<script src="${ctx}/scripts/jquery.timers.js" type="text/javascript" ></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	$(document).ready(function(){
		$(document).everyTime(5000, "waitIsPay", function(i) {// 5秒，检测一次
			$.post("${ctx}/CsAjax.do?method=waitIsPay",{out_trade_no:"${out_trade_no}",order_type:"${order_type}"},function(data){
				if (data.ret == "0") {
					$.jBox.tip(data.msg, 'error');
					$(document).stopTime("waitIsPay");
				} else if (data.ret == "1") {
					$.jBox.tip(data.msg, 'success');
					window.setTimeout(function () { 
						window.location.href="${urlgo}";
					}, 1500);
				}
			});
			if(i>100)$(document).stopTime("waitIsPay");//TODO 待会改
		});
	});   
});                                         
//]]></script>
</body>
</html>

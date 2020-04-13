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
<title>购物成功 - ${app_name}</title>
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/buy.css"  />
<style type="">
#bd{
    width: 1200px;
    top:0px;
}
</style>
</head>
<body class="pg-buy-process" id="cart-check">
<jsp:include page="../../_header_order.jsp" flush="true" />
<c:set var="pay_state_tip" value="您已支付完成，感谢您在${app_name}消费。"/>
<c:if test="${pay_state eq -1}">
<c:set var="pay_state_tip" value="支付失败，余额不足！"/>
</c:if>
<c:if test="${pay_state eq -2}">
<c:set var="pay_state_tip" value="支付失败，订单状态已改变！"/>
</c:if>
<div class="bdw">
 <div id="bd" class="cf">
 <div id="content" class="pg-check pg-cart-check">
        <div class="mainbox">
              <div class="finish-box">
              <p style=" font-size:18px;line-height:40px;font-weight:bold;display:block;">${pay_state_tip}</p>
              <p>订单号：${af.map.out_trade_no}</p>
              </div>
            <div class="form-submit" style="text-align:center;">
               <input id="J-order-pay-button" data-mod-id="1100500100" type="button" class="btn btn-large btn-pay" name="commit" value="查看订单" />
            </div>
      <div>
    </div>
 </div>
</div>
 </div>
</div>
<jsp:include page="../../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[
$("#J-order-pay-button").click(function(){
	var modId = $(this).attr("data-mod-id");
    $.post("${ctx}/CsAjax.do?method=getUrlLinkModId",{mod_id:modId},function(data){
		if(data.ret == 1){
			var parId_cookie = data.par_id + "," + data.data_url;
			if ($.isFunction($.cookie)) $.cookie("parId_cookie", parId_cookie, { path: '/' });
			location.href= "${ctx}/manager/customer/index.shtml";
		}
	});
});
//]]></script>
</body>
</html>
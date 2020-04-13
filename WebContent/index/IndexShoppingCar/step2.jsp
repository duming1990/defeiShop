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
<title>购物核对信息 - ${app_name}</title>
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/buy.css"  />
<style type="">
#bd{
    width: 1200px;
    top:0px;
}
.mui-switch.mui-active{border-color: #ec5051;background-color: #ec5051;}
</style>
</head>
<body id="order-check" class="pg-buy-process">
<jsp:include page="../../_header_order.jsp" flush="true" />
<div class="bdw">
  <div id="bd" class="cf">
    <div id="content" class="pg-check pg-cart-check">
      <div class="mainbox">
        <div class="orders-summary">
          <div class="order-list">
            <ul>
            <c:forEach items="${orderInfoList}" var="cur" varStatus="vs">
              <li class="order-item">
                <div class="order-name">订单号：${cur.trade_index}</div>
                <div class="order-number">${cur.order_num}份&nbsp;<fmt:formatNumber value="${cur.order_money}" pattern="0.##"/>元</div>
              </li>
             </c:forEach>
            </ul>
          </div>
          <div class="total-money">
          	应付金额：<span class="money" style="margin-right: 50px;" id="yinfu_money"><fmt:formatNumber value="${order_money}" pattern="0.##"/></span>
          </div>
        </div>
        <html-el:form action="/IndexShoppingCar.do" styleClass="order-check-type-form common-form J-wwwtracker-for" method="post" >
         <input type="hidden" name="method" value="step3" />
	     <input type="hidden" name="out_trade_no" value="${out_trade_no}" /> 
	     <input type="hidden" name="trade_index" value="${trade_index}" /> 
	     <input type="hidden" name="order_money" id="order_money" value="${order_money}" /> 
	     <input type="hidden" name="is_test" value="" /> 
          <div id="J-pay-types" class="blk-item paytype">
            <div class="component-payment-list mt-component--booted">
              <div id="order-check-typelist" class="paytype-list">
                <ul class="nav-tabs--large cf" id="selectPayType">
                  <li class="payment-cat current" id="selectPayType1"><a href="javascript:void(0);">第三方支付</a></li>
                </ul>
                <ul>
                <li class="payment-content current showPayType" id="showPayType1">
                    <ul class="bank-list bank-list--xpay">
                    <li class="item item left" style="margin-top: 10px;">
                        <input id="check-huodaofukuan" class="radio ui-radio" type="radio" name="pay_type" value="0" checked="checked" />
                        <label for="check-huodaofukuan" class="bank-logo" title="货到付款"><span>货到付款</span></label>
                      </li>
                      <c:if test="${is_open_pay_alipay}">
                      <li class="item item left">
                        <input id="check-alipay" class="radio ui-radio" type="radio" name="pay_type" value="1" checked="checked" disabled="disabled"/>
                        <label for="check-alipay" class="bank-logo" title="支付宝"><span class="bank bank--alipay">支付宝</span></label>
                      </li>
                      </c:if>
                      <c:if test="${is_open_pay_weixin}">
                      <li class="item item left">
                        <input id="check-wxqrpay" class="radio ui-radio" type="radio" name="pay_type" value="3" disabled="disabled"/>
                        <label for="check-wxqrpay" class="bank-logo" title="微信"><span class="bank bank--wxqrpay">微信</span></label>
                      </li>
                      </c:if>
                     </ul>
                 </li>
                </ul>
              </div>
            </div>
          </div>
          <div class="pay-password" style="display: none;">
                <jsp:include page="../_public_pay_pass.jsp"  flush="true"/>
          </div>
          <p class="pay-total" id="J-pay-total">
         	 支付金额：<span class="money">¥ <span id="pay_money"><fmt:formatNumber value="${order_money}" pattern="0.##"/></span></span>
          </p>
          <div class="form-submit">
            <input id="J-order-pay-button" type="button" class="btn btn-large btn-pay " name="commit" value="去付款"  onclick="gotoBuy();"/>
          </div>
        </html-el:form>
        <div> </div>
      </div>
    </div>
  </div>
</div>
<c:set var="tip_msg" value=""/>
<c:set var="tip_url" value=""/>
<c:if test="${empty userInfo.password_pay}">
<c:set var="tip_msg" value="请先前往账户中心维护支付密码"/>
<c:set var="tip_url" value="/manager/customer/MySecurityCenter.do?par_id=1100600000&mod_id=1100620100"/>
</c:if>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.js"></script> 
<script type="text/javascript" src="${ctx}/styles/mui/mui.min.js"></script>
<jsp:include page="../../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[
var pay_form = $(".order-check-type-form").get(0);
$(document).ready(function(){
	
// 	<c:if test="${not empty tip_msg}">
// 	var submit = function (v, h, f) {
// 	    if (v == true) {
// 	    	gotoChongZhi(null,1100620100);
// 	    } 
// 	    return true;
// 	};
// 	myConfirm("${tip_msg}",submit);
// 	</c:if>
	
	<c:if test="${not empty af.map.pay_type}">
	var pay_type = "${af.map.pay_type}";
		$("input[name='pay_type']").each(function(){
			if($(this).val() == pay_type){
				$(this).attr("checked",true);
				if($(this).val() == 0){
					$("#selectPayType2").addClass("current").siblings().removeClass("current");
					$("#showPayType2").addClass("current").siblings().removeClass("current");
				}
				return false;
			}
		});
	</c:if>
	
	
	$("#selectPayType li").each(function(index){
		// 默认选中第一个
		if(index == 0){
			$("input[name='pay_type']").eq(0).attr('checked', 'true'); 
		}
	});
	
	
	$("input[type=radio][name=pay_type]").eq(0).attr("datatype","Group").attr("msg","请选择支付方式"); 
	
	
});

function gotoBuy(){
	var trade_index="${trade_index}";
	$.ajax({
		type: "POST" , 
		url: "${ctx}/m/MMyCartInfo.do", 
		data:"method=havingReloadPay&trade_index="+trade_index,
		dataType: "json", 
        error: function (request, settings) {alert(" 数据加载请求失败！ ");	$(".pay").attr("disabled", "true");}, 
        success: function (data) {
        	if(data.code == 0){
        		if(Validator.Validate(pay_form, 3)){
        			$("#J-order-pay-button").addClass("btn-disabled").removeAttr("onclick");
        			pay_form.submit();
        		}
        	}else{
        		$("#J-order-pay-button").addClass("disabled").removeAttr("onclick");
        		$.jBox.tip(data.msg, 'info');
        		window.setTimeout(function () {
        			var url = "${ctx}"+"/manager/customer/IndexCustomer.do";
					window.location.href = url;
      			}, 3000);
        	}
        }
	});
}

function myConfirm(tip, submit){ 
	$.jBox.confirm(tip, "${app_name}", submit, { buttons: { '确定': true} });
}
//]]></script>
</body>
</html>
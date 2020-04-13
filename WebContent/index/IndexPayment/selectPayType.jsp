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
<title>购物服务 - ${app_name}</title>
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/buy.css"  />
<style type="">
#bd{
    width: 1200px;
    top:0px;
}
</style>
</head>
<body id="order-check" class="pg-buy-process">
<jsp:include page="../../_header.jsp" flush="true" />
<div class="bdw">
  <div id="bd" class="cf">
    <div id="content" class="pg-check pg-cart-check">
      <div class="mainbox">
        <div class="orders-summary">
          <div class="order-list">
            <ul>
              <li class="order-item">
                <div class="order-name">订单名称：${order_name}</div>
              </li>
            </ul>
          </div>
          <div class="total-money">应付金额：<span class="money">
            <fmt:formatNumber value="${order_money}" pattern="0.##"/>
            </span></div>
        </div>
        <form id="order-check-type-form" class="common-form J-wwwtracker-form" action="<c:url value='/IndexPayment.do' />" method="post">
          <input type="hidden" name="method" value="${pay_method}" />
          <input type="hidden" name="link_id" value="${af.map.link_id}"/>
          <div id="J-pay-types" class="blk-item paytype">
            <div class="component-payment-list mt-component--booted">
              <div id="order-check-typelist" class="paytype-list">
                <ul class="nav-tabs--large cf" id="selectPayType">
                  <li class="payment-cat current" id="selectPayType1"><a href="javascript:void(0);">第三方支付</a></li>
                  <c:if test="${can_user_dianzibi}">
                    <li class="payment-cat" id="selectPayType2"><a href="javascript:void(0);">余额支付</a></li>
                  </c:if>
                </ul>
                <ul>
                  <li class="payment-content current showPayType" id="showPayType1">
                    <ul class="bank-list bank-list--xpay">
                      <c:if test="${is_open_pay_alipay}">
                      <li class="item item left">
                        <input id="check-alipay" class="radio ui-radio" type="radio" name="pay_type" value="1" checked="checked" />
                        <label for="check-alipay" class="bank-logo" title="支付宝"><span class="bank bank--alipay">支付宝</span></label>
                      </li>
                      </c:if>
                      <c:if test="${is_open_pay_weixin}">
                      <li class="item item left">
                        <input id="check-wxqrpay" class="radio ui-radio" type="radio" name="pay_type" value="3" />
                        <label for="check-wxqrpay" class="bank-logo" title="微信"><span class="bank bank--wxqrpay">微信</span></label>
                      </li>
                      </c:if>
                    </ul>
                  </li>
                  
                  <li class="payment-content showPayType" id="showPayType2">
                    <ul class="bank-list bank-list--xpay">
                      <li class="item item left">
                        <input id="check-dzb" class="radio ui-radio" type="radio" name="pay_type" value="0" />
                        <label for="check-dzb" class="bank-logo" title="余额"><span class="bank bank--dzb">余额</span></label>
                      </li>
                       <p class="pay-total" id="J-pay-total" style="padding-top:20px;">支付<span class="money">¥${order_money}</span></p>
		               <p class="pay-total" style="font-weight:normal;">余额：¥<fmt:formatNumber value="${dianzibi_to_rmb}" pattern="0.##"/></p>
		               
		               
		               <c:if test="${((!pay_type_is_audit_success) and (userInfo.is_entp eq 1 or userInfo.is_fuwu eq 1)) or (pay_type_is_audit_success)}">
		               <c:if test="${dianzibi_to_rmb lt order_money}">
		               <p class="pay-total" style="font-size:12px;font-weight:normal;">余额不足，请 
		               <span class="label label-danger" style="cursor: pointer;" onclick="gotoChongZhi(this);" data-mod-id="1100400400">充值</span></p>
		               </c:if>
		               </c:if>
                    </ul>
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <div class="pay-password">
            <jsp:include page="../_public_pay_pass.jsp"  flush="true"/>
          </div>
          <p class="pay-total" id="J-pay-total">支付 <span class="money">¥
            <fmt:formatNumber value="${order_money}" pattern="0.##"/>
            </span></p>
          <div class="form-submit">
            <input id="J-order-pay-button" type="button" class="btn btn-large btn-pay " name="commit" value="去付款"  onclick="gotoBuy();"/>
          </div>
        </form>
        <div> </div>
      </div>
    </div>
  </div>
</div>
<c:set var="tip_msg" value=""/>
<c:set var="tip_url" value=""/>
<c:if test="${empty userInfo.password_pay}">
  <c:set var="tip_msg" value="请先前往账户中心维护支付密码"/>
  <c:set var="tip_url" value="/manager/customer/MySecurityCenter.do?par_id=1100620000&mod_id=1100620100"/>
</c:if>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.js"></script>
<jsp:include page="../../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[
var pay_form = $("#order-check-type-form").get(0);
$(document).ready(function(){
	
	<c:if test="${not empty tip_msg}">
	var submit = function (v, h, f) {
	    if (v == true) {
	    	gotoChongZhi(null,1100620100);  
	    } 
	    return true;
	};
	myConfirm("${tip_msg}",submit);  
	</c:if>
	
	
	
	
	$("#selectPayType li").each(function(index){
		
		// 默认选中第一个
		if(index == 0){
			$("input[name='pay_type']").eq(0).attr('checked', 'true'); 
		}
		
		$(this).click(function(){
			$(this).addClass("current").siblings().removeClass("current");
			var orderMoney = parseFloat("${order_money}");
			var hasdzbMoney = parseFloat("${dianzibi_to_rmb}");
			
			$("#showPayType" + (index + 1)).find("li:first input").attr('checked', 'true'); 
			
			if(index == 0){
				$("#J-order-pay-button").removeClass("btn-disabled").attr("onclick","gotoBuy()");
			}
			if(index == 1){
				if(hasdzbMoney < orderMoney){
					$("#J-order-pay-button").addClass("btn-disabled").removeAttr("onclick");
					$("#notcan-pay-total_hkb").show();
				}else{
					$("#J-order-pay-button").removeClass("btn-disabled").attr("onclick","gotoBuy()");
				}
			}
			$("#showPayType" + (index+1)).addClass("current").siblings().removeClass("current");
		});
	});
	
	$("input[type=radio][name=pay_type]").eq(0).attr("datatype","Group").attr("msg","请选择支付方式"); 
	$("#pay_password").attr("datatype","Require").attr("msg","请输入支付密码"); 
});


function gotoBuy(){
	if(Validator.Validate(pay_form, 3)){
		$("#J-order-pay-button").addClass("btn-disabled").removeAttr("onclick");
		pay_form.submit();
	}
}

function gotoChongZhi(obj,no_obj_mod_id){
	var modId = no_obj_mod_id;
	if(null != obj){
		var modId = $(obj).attr("data-mod-id");
	}
    $.post("${ctx}/CsAjax.do?method=getUrlLinkModId",{mod_id:modId},function(data){
		if(data.ret == 1){
			var parId_cookie = data.par_id + "," + data.data_url;
			if ($.isFunction($.cookie)) $.cookie("parId_cookie", parId_cookie, { path: '/' });
			location.href= "${ctx}/manager/customer/index.shtml";
		}
	});
}

function myConfirm(tip, submit){ 
	$.jBox.confirm(tip, "${app_name}", submit, { buttons: { '确定': true} });
}

//]]></script>
</body>
</html>

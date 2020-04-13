<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>触屏版-${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../../m/_public_in_head.jsp" flush="true" />
<link href="${ctx}/m/styles/css/cp_style_v15.11.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/m/styles/css/my/my-v1.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.new-mg-b10 {
	margin-bottom: 15px;
}
.submit-btn {
	height:auto;
 margin: .4rem .0rem;
}
.info-details {
	border-top: 1px #CFCFCF solid;
	border-bottom:none;
 padding: .2rem 0;
}
</style>
</head>
<body id="body">
<jsp:include page="../_header.jsp" flush="true" />
<html-el:form action="/MPayScan.do?entp_id=${af.map.entp_id}" method="post" styleClass="ajaxForm0">
  <html-el:hidden property="method" value="saveOrderInfoAndPay"/>
  <html-el:hidden property="entp_id" />
   <html-el:hidden property="rule" />
  <input type="password" name="pay_password" id="pay_password" style="display:none;"/>
  <jsp:include page="../_public_pay_pass.jsp" flush="true" />
  <div class="order-conent" style="margin-top: 5px">
    <section class="common-items">
      <div class="common-item label-warning" style="color: #fff;"> 店铺：${fn:escapeXml(entp_name)} </div>
      <div class="common-item label-warning" style="color: #fff;"> 比例：${fn:escapeXml(rule_name)} </div>
      
      <div class="common-item" id="bind_change">
        <div class="item-content">
          <input name="pay_money" type="text" class="codebox-input zhaoinput" id="pay_money" placeholder="请输入支付金额" />
        </div>
      </div>
    </section>
    <div class="pay-methods">
      <div class="normal-fieldset">
        <section class="common-items common-radio-box" id="choosePayTpye">
        <c:if test="${is_open_pay_alipay}">
          <div class="common-item" id="payType1">
            <label> <font class="o-icon o-alipay" color="#333">支付宝支付</font>
            <input type="radio" name="pay_type" value="1"/>
            <i></i> </label>
          </div>
          </c:if>
          <c:if test="${is_open_pay_union}">
			<div class="common-item" id="payType2">
				<label>
					<font class="o-icon o-upay" color="#333">银联支付</font>
					<input type="radio" name="pay_type" value="2" />
					<i></i>
				</label>
			</div>
		  </c:if>
          <c:if test="${is_open_pay_weixin}">
          <c:if test="${isWeixin or isApp}">
            <div class="common-item">
              <label class="checked"> <font class="o-icon o-weixin" color="#333">微信支付</font>
              <input type="radio" name="pay_type" value="3" checked="checked"/>
              <i></i> </label>
            </div>
          </c:if>
          </c:if>
          <c:if test="${(not empty userInfo) and (userInfo.is_entp eq 1)}">
          <div class="common-item">
            <label> <font class="o-icon o-chuxu" color="#333">货款</font>
            <input type="radio" name="pay_type" value="4" />
            <i></i> </label>
          </div>
          </c:if>
          <div class="common-item">
            <label> <font class="o-icon o-chuxu" color="#333">账户余额</font>
            <input type="radio" name="pay_type" value="0" />
            <i></i> </label>
          </div>
        </section>
      </div>
    </div>
    <div id="showBiDianzi" style="display:none;">
      <section class="common-items">
        <div class="common-item sel" id="bind_change"> <a href="javascript:void(0);"> <span class="item-label">货款余额：</span>
          <div class="item-content"> <b id="total_fee">¥
            <fmt:formatNumber value="${huoKuanBi_to_xiaoFeiBi}" pattern="0.00"/>
            </b> </div>
          </a> </div>
      </section>
      <section class="common-items"  id="showBiDianzi_tip" style="display:none;">
        <div class="common-item" id="bind_change"> <a> <span class="item-label">货款不足：</span> </a> </div>
      </section>
    </div>
    <div id="showBiDianzi_2" style="display:none;">
      <section class="common-items">
      <div class="common-item sel" id="bind_change">
			<a href="javascript:void(0);">
				<span class="item-label" style="color: red;font-size: 0.3rem;">复销券可抵用：</span>
				<div class="item-content" style="text-align: left;">
					<b id="total_fee">¥<fmt:formatNumber value="${userInfo.bi_fuxiao}" pattern="0.###"/></b>
				</div>
			</a>
		</div>
        <div class="common-item sel" id="bind_change"> <a href="javascript:void(0);"> <span class="item-label">账户余额</span>
          <div class="item-content"> <b id="total_fee">¥
            <fmt:formatNumber value="${dianzibi_to_rmb}" pattern="0.###"/>
            </b> </div>
          </a> </div>
          
          <div class="common-item sel" id="bind_change">
			<a href="javascript:void(0);">
				<span class="item-label">需支付余额：</span>
				<div class="item-content" style="text-align: left;">
				<b id="total_fee" class="need_money">¥</b>	
				</div>
			</a>
		  </div>
		
      </section>
      <section class="common-items" id="showBiDianzi_2_tip" style="display:none;">
        <div class="common-item" id="bind_change"> <a><span class="item-label">账户余额不足</span></a> </div>
      </section>
    </div>
  </div>
  <div style="text-align: center;padding-right: 10px;padding-top: 10px">当前登陆账户：${fn:escapeXml(userInfo.user_name)}</div>
  <div class="box submit-btn" style="padding-bottom: 0;padding-top: 1px;">
    <input type="button" class="com-btn" id="bs" value="确认提交" onclick="showPayPassTip();"/>
  </div>
  <div class="box submit-btn" style="padding-bottom: 0;padding-top: 1px;">
    <c:url var="url" value="/m/MIndexLogin.do" />
    <input type="button" class="com-btn" style="background-color: #5cb85c;" value="切换账户" onclick="location.href='${url}?returnUrl=' + escape('${return_url}');return true;" />
  </div>
</html-el:form>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.m.js"></script>
<script type="text/javascript">//<![CDATA[

var f = $(".ajaxForm0").get(0);                                        
                                          
$(document).ready(function(){
	$("#pay_money").attr("dataType", "Currency").attr("msg", "请填写支付金额！");
	
	$("#pay_money").change(function(){
		var fuxiao = parseFloat("${fuxiao}");
		var orderMoney = parseFloat($("#pay_money").val());
		if(orderMoney <= fuxiao){
			$(".need_money").text("￥ 0");
		}else{
			var Money = parseFloat(orderMoney-fuxiao).toFixed(3);
// 			var Money = Math.floor((orderMoney-fuxiao) * 100) / 100 ;
			$(".need_money").text(Money);
		}
	});
	
	<c:if test="${!isWeixin}">
	   $("#payType1").find("label").addClass("checked");
	   $("#payType1").siblings().find("label").removeClass("checked");
	   $("#payType1").find("input").attr("checked","checked");
	   $("#payType1").siblings().find("input").removeAttr("checked");
	</c:if>	
	
	$("#choosePayTpye .common-item").each(function(index){ 
		$(this).click(function(){
			var orderMoney = parseFloat($("#pay_money").val());
			if(isNaN(orderMoney) || null == orderMoney || "" == orderMoney){
				 mui.toast("请输入支付金额");
				 return false;
			}
			var hasdzbMoney = parseFloat("${huoKuanBi_to_xiaoFeiBi}");
			var hasdzbMoney2 = parseFloat("${dianzibi_to_rmb}");
			$(this).find("label").addClass("checked");
			$(this).siblings().find("label").removeClass("checked");
			$(this).find("input").attr("checked","checked");
			$(this).siblings().find("input").removeAttr("checked");
			var inputVal = $(this).find("input").val();
			if(inputVal == 4){
				$("#showBiDianzi_2").hide();
				$("#showBiDianzi").show();
				if(hasdzbMoney < orderMoney){
					$("#bs").addClass("disabled").removeAttr("onclick");
					$("#showBiDianzi_tip").show();
					$("#showBiDianzi_2_tip").hide();
				}else{
					$("#bs").removeClass("disabled").attr("onclick","showPayPassTip()");
					$("#showBiDianzi_tip").hide();
				}
			}else if(inputVal == 0){
				var fuxiao = parseFloat("${fuxiao}");
				hasdzbMoney2+=fuxiao;
				$("#showBiDianzi_2").show();
				$("#showBiDianzi").hide();
				if(hasdzbMoney2 < orderMoney){
					//账户余额小于订单金额
					$("#bs").addClass("disabled").removeAttr("onclick");
					$("#showBiDianzi_tip").hide();
					$("#showBiDianzi_2_tip").show();
				}else{
					$("#bs").removeClass("disabled").attr("onclick","showPayPassTip()");
					$("#showBiDianzi_2_tip").hide();
				}
			}else{
				$("#showBiDianzi").hide();
				$("#showBiDianzi_2").hide();
				$("#bs").removeClass("disabled").attr("onclick","showPayPassTip()");
			}
		});
	});
	
});

function toDecimal(x) {    
    var f = parseFloat(x);    
    if (isNaN(f)) {    
        return;    
    }    
    f = Math.round(x*100)/100;    
    return f;    
}    
function showPayPassTip(){
	$(".ftc_wzsf").show();
	initPass();
}

function submitThisForm(){ 
	if(Validator.Validate(f, 1)){
		var pay_money = $("#pay_money").val();
		if(pay_money <= 0){
			 mui.toast("支付金额必须大于0");
			 return false;
		}
		var pay_password = $("#pay_password").val();
		if(null == pay_password || '' == pay_password){
			mui.toast("请输入支付密码",1000);
			return false;
		}
		
		Common.loading();
		window.setTimeout(function () {
			$("#bs").addClass("btn-disabled").removeAttr("onclick");
			f.submit();
		},2000);
		return true;
	}
}

//]]></script>
</body>
</html>

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
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />
<div class="content">
  <html-el:form action="/PayOffline.do" enctype="multipart/form-data" styleClass="ajaxForm0">
    <jsp:include page="./_public_pay_pass.jsp" flush="true" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="order_id" />
    <input type="password" name="pay_password" id="pay_password" style="display:none;"/> 
    <html-el:hidden property="need_order_audit" styleId="need_order_audit" value="0"/>
    <html-el:hidden property="method" value="save"/>
    <div class="set-site">
      <ul class="formUl">
        <li><span class="grey-name">用户/手机号：</span>
        <div class="max-width">
        <input name="user_name" id="user_name" maxlength="20"  readonly="true" value="${orderInfo.rel_name}"/>
<%--         <html-el:text property="user_name" styleId="user_name" maxlength="20" autocomplete="off" /> --%>
        </div>
        </li>
        <li> <span class="grey-name">折扣规则：</span>
        <html-el:select property="fanxian_rule" styleId="fanxian_rule" style="width:250px;">
        <option value="${orderInfoDetail.huizhuan_rule}">${basedata.type_name}</option>
          </html-el:select>
        </li>
        <li> <span class="grey-name">成交金额：</span>
        <div>
        <input name="get_money" id="get_money" maxlength="10" readonly="true" value="${orderInfo.order_money}"/></div>
        </li>
        <li><span class="grey-name">商家增值券：</span>
        <span title="商家增值券" id="fanxian_money" class="grey-name" >0</span>
        </li>
        <li><span class="grey-name">商家服务费：</span>
        <span title="商家服务费" id="entp_service_money" class="grey-name">0</span>
        </li>
        
        <fmt:formatNumber var="fxb"  pattern="#0.########" value="${userInfoTemp.leiji_money_entp/rmb_to_fanxianbi_rate}" />
        <fmt:formatNumber var="fxb_to_rmb"  pattern="#0.########" value="${userInfoTemp.leiji_money_entp}" />
        <li style="border-bottom: none;padding-bottom: 0"> <span class="grey-name" style="width: 45%">我的增值券>人民币：<br>今日已派送:${orderInfoSum}</span>
        <span id="last_fanxian_money" class="grey-name" style="width: 55%">
          ${fxb}增值券 = ${fxb_to_rmb}元
         </span>
        </li>
        <li style="padding-top: 0;style="display:none;"" id="need_chongzhiTip">
           <span  class="label label-danger"></span>
        </li>
        <li style="border-bottom:none;padding-bottom:0;display:none;" id="tr_main_pic"> 
        <span class="grey-name" style="width: 36%">交易凭证：</span>
        <span id="last_fanxian_money" class="grey-name" style="width: 64%;color: red;">
                  单日单笔超5万，需提交交易凭证<br>
          <c:set var="img" value="${ctx}/${af.map.main_pic}" />
          <img src="${img}" height="80" id="main_pic_img" />
          <html-el:hidden property="main_pic" styleId="main_pic" />
          <div class="files-warp" id="main_pic_warp">
            <div class="btn-files"> <span>上传主图</span>
              <input id="main_pic_file" type="file" name="main_pic_file" accept="image/*" capture/>
            </div>
            <div class="progress"> <span class="bar"></span><span class="percent">0%</span></div>
          </div>
         </span>
        </li>
      </ul>
    </div>
    <div style="text-align: right;padding: 10px;"> <span class="">说明：</span>
        <span class="item-content tip-danger">100增值券=${rmb_to_fanxianbi_rate*100}元</span>
    </<div>
    <div class="box submit-btn"> 
     <input type="button" class="com-btn" id="btn_submit" value="发送" onclick="showPayPassTip(1);" />
     <input type="button" class="com-btn" value="点击充值" id="btn_submit_not_engouh" style="display:none;"/>
     &nbsp;
     <c:if test="${app_keywords eq '仁义联盟商城'}">
     <input type="button" class="com-btn" value="付款并发送" id="btn_reset" onclick="submitAndPaySend(this);" style="display: none;" />
     </c:if>
    </div>
  </html-el:form>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<c:url var="gotoUrl" value="/m/MMyOrderEntp.do?method=list&order_type=40&mod_id=1300300100" />
<c:url var="chongzhi_url" value="/m/MChongZhiEntpFxMoney.do?par_id=1300510000&mod_id=1300510130" />
<script type="text/javascript" src="${ctx}/commons/scripts/validator.m.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript">//<![CDATA[
var f = $(".ajaxForm0").get(0);

$(document).ready(function() {
	$("#user_name").val();
	
	 var topBtnUrl = "${gotoUrl}";
	 setTopBtnUrl(topBtnUrl);
	 
	 
	 var btn_name = "上传凭证";
	 upload("main_pic", "image", btn_name, "${ctx}");
	 
	 var orderInfoSum = Number("${orderInfoSum}");
	 var everyDayMaxSend = Number("${everyDayMaxSend}");
	 
	 if(orderInfoSum >= everyDayMaxSend){
		 	$("#tr_main_pic").show();
		 	$("#main_pic").attr("dataType", "Filter" ).attr("msg", "请上传格式为（bmp, gif, jpeg, jpg, png）的主图地址！").attr("require", "true").attr("accept", "bmp, gif, jpeg, jpg, png");
		    $("#need_order_audit").val(1);
	 }
	
	
	$("#user_name").attr("dataType", "Require").attr("msg", "请填写用户名/手机号！");
	$("#get_money").attr("dataType", "Currency").attr("msg", "请填写成交金额！");
	$("#fanxian_rule").attr("datatype","Require").attr("msg","请选择返现规则！");
	
	<c:if test="${userInfoTemp.leiji_money_entp le 0}">
	   $("#btn_submit").hide();
	   $("#btn_submit_not_engouh").show();
	   $("#btn_reset").show();
	 </c:if>
	 
	$("#btn_submit_not_engouh").click(function(){
		location.href="${chongzhi_url}";
	});
	
	 
// 	$("#get_money").blur(function(){
		var thisVal = $("#get_money").val();
		if(null != thisVal && "" != thisVal){
			
			if((orderInfoSum  + Number(thisVal)) >= everyDayMaxSend){
			 	$("#tr_main_pic").show();
			 	$("#main_pic").attr("dataType", "Filter" ).attr("msg", "请上传格式为（bmp, gif, jpeg, jpg, png）的交易凭证！").attr("require", "true").attr("accept", "bmp, gif, jpeg, jpg, png");
			 	$("#need_order_audit").val(1);
			}else{
			 	$("#tr_main_pic").hide();
			 	$("#main_pic").removeAttr("dataType").attr("require", "false");
			 	$("#need_order_audit").val(0);
			}
			
			var fanxian_rule = $("#fanxian_rule").val();
			if(null == fanxian_rule || "" == fanxian_rule){
				mui.toast("请选择返现规则！");
				return false;
			}
			$.ajax({
				type: "POST",
				url: "${ctx}/CsAjax.do?method=queryLinkInfo",
				data: $(f).serialize(),
				dataType: "json",
				error: function(request, settings) {},
				success: function(data) {
					if (data.code == "1") {
					   $("#fanxian_money").text(data.datas.fanxian_money);
					   $("#entp_service_money").text(data.datas.entp_service_money);
					   //查询余额是否足够
					   if(data.datas.is_engouh == "0"){
						   $("#btn_submit").hide();
						   $("#btn_submit_not_engouh").show();
						   $("#btn_reset").show();
						   $("#need_chongzhiTip").find("span").html("余额不足，需再充值" + data.datas.need_chongzhi_money+"元");
						   $("#need_chongzhiTip").show();
					   }else{
						   $("#btn_submit").show();
						   $("#btn_submit_not_engouh").hide();
						   $("#btn_reset").hide(); 
						   $("#need_chongzhiTip").hide(); 
						 
					   }
					} else {
						mui.toast(data.msg);
					}
				}
			});
		}
// 	});
	
	
	$("#user_name").blur(function(){
		var value = $.trim(this.value);
		if (value != "") {
			$("#user_name").val(value);
			$.ajax({
				type: "POST",
				url: "${ctx}/CsAjax.do",
				data: "method=queryUserHasExist&user_name=" + value,
				dataType: "json",
				error: function(request, settings) {flag = false;},
				success: function(data) {
					if (data.code == 0) {
						mui.toast(data.msg);
						$("#btn_submit").removeAttr("onclick").attr("disabled",true);
						$("#btn_reset").removeAttr("onclick").attr("disabled",true);
					}else{
						$("#btn_submit").attr("onclick","showPayPassTip(1);").removeAttr("disabled");
						$("#btn_reset").attr("onclick","submitAndPaySend(this);").removeAttr("disabled");
					}
				}
			});
		}
	});
	
	
	$("#fanxian_rule").change(function(){
	    var thisVal = $(this).val();
		 if(null != thisVal && "" != thisVal){
			 $("#get_money").focus();
		}
	});
});

function showPayPassTip(type){
	$(".ftc_wzsf").show();
	initPass(type);
}

function submitAndSend(){
	if(Validator.Validate(f, 1)){
		
		var html = "确定要派送给该用户吗？<br/>";
		html += "用户名/手机号：" + $("#user_name").val() + "<br/>";
		html += "返现比例：" + $("#fanxian_rule").find("option:selected").text() + "<br/>";
		html += "成交金额：" + $("#get_money").val() + "<br/>";
		html += "商家服务费：" + $("#entp_service_money").text() + "<br/>";
		
		Common.confirm(html,["确定","取消"],function(){
			Common.loading();
			$.ajax({
				type: "POST",
				url: "?method=submitAndSend",
				data: $(f).serialize(),
				dataType: "json",
				error: function(request, settings) {},
				success: function(data) {
					Common.hide();
					if (data.code == "1") {
						mui.toast(data.msg);
						window.setTimeout(function () {
							location.href="${gotoUrl}";
						},2000);
					} else {
						mui.toast(data.msg);
					}
				}
			});	
		},function(){
		});	
	}
	return false;
}

function submitAndPaySend(){
	if(Validator.Validate(f, 1)){
		
		var html = "确定要派送给你该用户吗？<br/>";
		html += "用户名/手机号：" + $("#user_name").val() + "<br/>";
		html += "返现比例：" + $("#fanxian_rule").find("option:selected").text() + "<br/>";
		html += "成交金额：" + $("#get_money").val() + "<br/>";
		html += "商家服务费：" + $("#entp_service_money").text() + "<br/>";
		
		
		Common.confirm(html,["确定","取消"],function(){

			Common.loading();
				$.ajax({
					type: "POST",
					url: "?method=submitAndPaySend",
					data: $(f).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						Common.hide();
						if (data.code == "1") {
							mui.toast(data.msg);
							$("#btn_reset").attr("value", "正在提交...").attr("disabled", "true");
							window.setTimeout(function () {
								location.href=data.datas.gotUrl;
							},2000);
						} else {
							mui.toast(data.msg);
						}
					}
				});	
				
		},function(){
		});	
	}
	return false;
}
//]]></script>
</body>
</html>

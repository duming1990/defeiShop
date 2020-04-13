<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <%@ include file="/commons/pages/messages.jsp" %>
  <div class="all">
      <c:url var="url" value="/manager/customer/MyOrderEntp.do?method=list&order_type=40&mod_id=1300300100&par_id=1300100000" />
      <button class="bgButtonFontAwesome" type="button" onclick="location.href='${url}'"><i class="fa fa-history"></i>查看历史 </button>
      <span class="label label-info">已有增值券
      <fmt:formatNumber var="bi" value="${userInfoTemp.leiji_money_entp/rmb_to_fanxianbi_rate}" pattern="0.########"/>${bi}
      </span>
      <div class="label label-danger">今日已派送
      <fmt:formatNumber var="bi" value="${orderInfoSum}" pattern="0.########"/>${bi}
      </div>
  </div>
  <html-el:form action="/customer/PayOffline.do" enctype="multipart/form-data" styleClass="ajaxForm0">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="order_id" />
    <html-el:hidden property="need_order_audit" styleId="need_order_audit" value="0"/>
    
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>用户名/手机号：</td>
        <td colspan="3" id="city_div">
        <input name="user_name" maxlength="20" styleClass="webinput"  id="user_name" style="width:200px" readonly="redonly" value="${orderInfo.rel_name}"/>
        <span class="label label-danger" style="margin-left:5px;display:none;" id="danger_tip"></span>
       </td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>折扣规则：</td>
        <td colspan="3">
        <select name="fanxian_rule" id="fanxian_rule" >
           <option value="${orderInfoDetail.huizhuan_rule}">${basedata.type_name}</option>
        </select>
        </td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>成交金额：</td>
        <td colspan="3">
        <input name="get_money" id="get_money" maxlength="10" style="width:120px" styleClass="webinput" readonly="redonly" value="${orderInfo.order_money}"/>
        </td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item">商家增值券：</td>
        <td colspan="3">
        <span id="fanxian_money">0</span>
        </td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item">商家服务费：</td>
        <td colspan="3">
        <span id="entp_service_money" >0</span>
        </td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item">增值券=>人民币：</td>
        <td colspan="3">
         <span id="last_fanxian_money">
          <fmt:formatNumber var="leiji_money_entp" value="${userInfoTemp.leiji_money_entp}" pattern="0.########"/>${leiji_money_entp}元
         </span>
         &nbsp;<span id="need_chongzhiTip" class="label label-danger" style="display:none;"></span>
        </td>
      </tr>
      <tr>
      <td nowrap="nowrap" class="title_item"><i class="fa fa-info-circle"></i> 说明：</td>
      <td colspan="3"><span class="tip-danger">${rmb_to_fanxianbi_rate*100}元=100增值券</span></td>
      </tr>
      
      <tr id="tr_main_pic" style="display:none;">
        <td nowrap="nowrap" class="title_item"><span style="color: #F00" id="span_main_pic">*</span>交易凭证：</td>
        <td colspan="2">
          <c:set var="img" value="${ctx}/${af.map.main_pic}" />
          
          <img src="${img}" height="80" id="main_pic_img" />
          <html-el:hidden property="main_pic" styleId="main_pic" />
          <div class="files-warp" id="main_pic_warp">
            <div class="btn-files"> <span>上传主图</span>
              <input id="main_pic_file" type="file" name="main_pic_file" />
            </div>
            <div class="progress"> <span class="bar"></span><span class="percent">0%</span></div>
          </div>
         <span class="label label-danger">说明：每日最大派送金额${everyDayMaxSend},如果大于该金额，则需要上传交易凭证,并且后台进行审核！</span> 
        </td>
      </tr>
      <tbody id="need_pass" style="display:none;">
	      <tr>
	      <td nowrap="nowrap" class="title_item">支付密码：</td>
	      <td colspan="3">
	       <jsp:include page="../../../index/_public_pay_pass.jsp"  flush="true"/>
	      </td>
	      </tr>
      </tbody>
      <tr>
        <td colspan="4" style="text-align:center">
          <html-el:button property="" value="发送" styleClass="bgButton" styleId="btn_submit"  onclick="submitAndSend(this);" />
          <html-el:button property="" value="余额不足,点击充值" styleClass="bgButton" styleId="btn_submit_not_engouh" style="display: none;" />
          &nbsp;
          <c:if test="${app_keywords eq '仁义联盟商城'}">
          <html-el:button property="" value="付款并发送" styleClass="bgButton" styleId="btn_reset" onclick="submitAndPaySend(this);" style="display: none;" />
          </c:if>
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>
<c:set var="tip_msg" value=""/>
<c:set var="tip_url" value=""/>
<c:if test="${empty userInfo.password_pay}">
<c:set var="tip_msg" value="请先前往账户中心维护支付密码"/>
<c:set var="tip_url" value="/manager/customer/MySecurityCenter.do?par_id=1100600000&mod_id=1100620100"/>
</c:if>
<c:url var="gotoUrl" value="/manager/customer/MyOrderEntp.do?par_id=1300100000&mod_id=1300300100&order_type=40" />
<c:url var="chongzhi_url" value="/manager/customer/ChongZhiEntpFxMoney.do?par_id=1300510000&mod_id=1300510130" />
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript">//<![CDATA[

var f = $(".ajaxForm0").get(0);

$(document).ready(function() {
	
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
	$("#get_money").attr("dataType", "Currency").attr("msg", "请填写成交金额，且格式正确！");
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
				$.jBox.tip("请选择返现规则！", "error",{timeout:2000});
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
						   $("#need_chongzhiTip").html("余额不足，需再充值" + data.datas.need_chongzhi_money+"元").show();
						   $("#need_pass").hide();
						   $("#pay_password").removeAttr("datatype"); 
					   }else{
						   $("#btn_submit").show();
						   $("#btn_submit_not_engouh").hide();
						   $("#btn_reset").hide(); 
						   $("#need_chongzhiTip").hide(); 
						   $("#need_pass").show();
						   $("#pay_password").attr("datatype","Require").attr("msg","请输入支付密码"); 
					   }
					} else {
						$.jBox.tip(data.msg, "error",{timeout:2000});
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
						$("#danger_tip").text(data.msg).show();
						$("#btn_submit").removeAttr("onclick").attr("disabled",true);
						$("#btn_reset").removeAttr("onclick").attr("disabled",true);
					}else{
						$("#danger_tip").hide();
						$("#btn_submit").attr("onclick","submitAndSend(this);").removeAttr("disabled");
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


function myConfirm(tip, submit){ 
	$.jBox.confirm(tip, "${app_name}", submit, { buttons: { '确定': true, '取消': false} });
}


function submitAndSend(obj){
	if(Validator.Validate(f, 1)){
		
		var submit = function (v, h) {
		    if (v == true) {
		    	$.jBox.tip("加载中...", "loading");
				$.ajax({
					type: "POST",
					url: "?method=submitAndSend",
					data: $(f).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						if (data.code == "1") {
							$.jBox.tip(data.msg, "success",{timeout:1000});
							window.setTimeout(function () {
								location.href="${gotoUrl}";
							},2000);
						} else {
							$.jBox.tip(data.msg, "error",{timeout:2000});
						}
					}
				});	
		    } 
		    return true;
		};
		var html = "<p>确定要派送给该用户吗？</p>";
		html += "<p>用户名/手机号：" + $("#user_name").val() + "</p>";
		html += "<p>返现比例：" + $("#fanxian_rule").find("option:selected").text() + "</p>";
		html += "<p>成交金额：" + $("#get_money").val() + "</p>";
		html += "<p>商家服务费：" + $("#entp_service_money").text() + "</p>";
		var need_order_audit = $("#need_order_audit").val();
		if(need_order_audit == 1){
		 html += "<p>订单派送完之后需要管理员审核之后才能正式发放！</p>";
		}
		myConfirm(html,submit);
    	return false;
	}
	return false;
}
function submitAndPaySend(obj){
	if(Validator.Validate(f, 1)){
		var submit = function (v, h) {
		    if (v == true) {
		    	var w = window.open();
				$.jBox.tip("加载中...", "loading");
					$.ajax({
						type: "POST",
						url: "?method=submitAndPaySend",
						async:false,
						data: $(f).serialize(),
						dataType: "json",
						error: function(request, settings) {},
						success: function(data) {
							if (data.code == "1") {
								$.jBox.tip(data.msg, "success",{timeout:1000});
								$("#btn_reset").attr("value", "正在提交...").attr("disabled", "true");
								window.setTimeout(function () {
									 w.location = data.datas.gotUrl;
									 var submit = function (v, h, f) {
										    location.href="${gotoUrl}";
										};
										// 自定义按钮
										$.jBox.confirm("请您在新打开的页面进行支付,支付完成前请不要关闭此窗口!", "在线支付", submit, { width: 400,height: 160,buttons: { '支付成功': true, '支付失败': false}});
								},1500);
							} else {
								$.jBox.tip(data.msg, "error",{timeout:2000});
							}
						}
					});		
		    } 
		    return true;
		};
		var html = "<p>确定要派送给该用户吗？</p>";
		html += "<p>用户名/手机号：" + $("#user_name").val() + "</p>";
		html += "<p>返现比例：" + $("#fanxian_rule").find("option:selected").text() + "</p>";
		html += "<p>成交金额：" + $("#get_money").val() + "</p>";
		html += "<p>商家服务费：" + $("#entp_service_money").text() + "</p>";
		var need_order_audit = $("#need_order_audit").val();
		if(need_order_audit == 1){
		 html += "<p>订单派送完之后需要管理员审核之后才能正式发放！</p>";
		}
		myConfirm(html,submit);
    	return false;
	}
	return false;
}

function getCommInfo() {
	var url = "${ctx}/BaseCsAjax.do?method=chooseCommInfo&dir=customer&own_entp_id=${userInfoTemp.own_entp_id}&t=" + new Date().getTime();
	$.dialog({
		title:  "选择商品",
		width:  800,
		height: 600,
        lock:true ,
        zIndex:"10000",
		content:"url:"+url
	});
};
                                          
//]]></script>
</body>
</html>

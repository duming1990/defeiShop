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
<div class="mainbox mine" style="min-height: 500px">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <%@ include file="/commons/pages/messages.jsp" %>
  <html-el:form action="/customer/MyBank" styleClass="ajax_form">
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="par_id" />
    <c:set var="is_add" value="true"/>
    <c:set var="readonly" value="false"/>
    <c:if test="${not empty af.map.bank_account}">
    <c:set var="readonly" value="true"/>
    <c:set var="is_add" value="false"/>
    </c:if>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th colspan="4">银行账号</th>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>开户银行：</td>
        <td colspan="3"><html-el:text property="bank_name" maxlength="50" styleClass="webinput hide_element" styleId="bank_name" style="width:200px" readonly="${readonly}"/>
        <html-el:select property="bank_select" styleId="bank_select"></html-el:select>
        
        </td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>开户账号：</td>
        <td colspan="3"><html-el:text property="bank_account" maxlength="50" styleClass="webinput hide_element" styleId="bank_account" style="width:200px" readonly="${readonly}"/>
        </td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>开户名：</td>
        <td colspan="3"><html-el:text property="bank_account_name" maxlength="50" styleClass="webinput hide_element" styleId="bank_account_name" style="width:200px" readonly="${readonly}"/>
        </td>
      </tr>
       <tr class="hide_tr">
         <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>验证码：</td>
         <td><html-el:text property="verificationCode" styleId="verificationCode" maxlength="4" style="width:100px" styleClass="webinput" />
          &nbsp;<img height="22" width="72" class="signup-captcha-img" id="veri_img" style="vertical-align: bottom;"/> 
          <a tabindex="-1" class="captcha-refresh inline-link" href="javascript:updateVerCode();">看不清楚？换一张</a>
         </td>
       </tr>
       <tr class="hide_tr">
         <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>手机号：</td>
         <td><html-el:text property="mobile" styleId="mobile" maxlength="11" style="width:100px" styleClass="webinput" readonly="true" />&nbsp;<button class="bgButtonFontAwesome" type="button" onclick="sendSms()" id="sendMobileCode"><i class="fa fa-send"></i>发送验证码</button></td>
       </tr>
       <tr class="hide_tr">
         <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>手机动态码：</td>
         <td><html-el:text property="ver_code" styleId="ver_code" maxlength="6" style="width:100px" styleClass="webinput" /></td>
       </tr>
      <tr>
        <td width="14%" nowrap="nowrap">&nbsp;</td>
        <td colspan="3" style="text-align:left"><button class="bgButtonFontAwesome" type="button" id="btn_edit" onclick="edit()"><i class="fa fa-edit"></i>修 改</button>
        <button class="bgButtonFontAwesome" type="button" id="btn_submit"><i class="fa fa-save"></i>保 存</button></td>
      </tr>
    </table>
  </html-el:form>
</div>
<c:set var="bank_account" value="${af.map.bank_account }"/>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/bank/bank.js"></script>
<script type="text/javascript">//<![CDATA[
function edit(){
	$(".hide_tr").show();
	$("#btn_submit").show();
	$("#btn_edit").hide();
	$(".hide_element").removeAttr("readonly");
	$("#mobile").attr("dataType", "Mobile").attr("msg", "请正确填写手机号码");
	$("#ver_code").attr("dataType", "Require").attr("msg", "请填写手机动态码");
	$("#verificationCode").attr("dataType", "Require").attr("msg", "请填写验证码！");
	$("#captcha").attr("datatype","Require").attr("msg","请填写验证码！");
}
function init(){
	$(".hide_tr").hide();
	$("#btn_submit").hide();
	$("#btn_edit").show();
	$(".hide_element").attr("readonly","true");
}
$(document).ready(function(){
	init();
	<c:if test="${is_add}">
	$(".hide_tr").hide();
	$("#btn_submit").show();
	$("#btn_edit").hide();
	$(".hide_element").removeAttr("readonly");
	</c:if>

	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
	
	$("#bank_name").attr("datatype","Require").attr("msg","请填写开户银行！");
	$("#bank_account").attr("datatype","Require").attr("msg","请填写开户账号！");
	$("#bank_account_name").attr("datatype","Require").attr("msg","请填写开户名");
	
	var banks = datas.banks;
	for (var i = 0, len = banks.length; i < len; i++) {
		var name = banks[i]["name"];
		$('#bank_select').append('<option value="' + name + '">' + name + '</option>');
	}
	
	var $form  = $(".ajax_form");
	var f = $form.get(0);
	$("#btn_submit").click(function(){

		if(Validator.Validate(f, 3)){
			//$.jBox.tip("数据提交中...", 'loading');
			$.dialog.tips("数据提交中...",1, "loading.gif"); 
			$("#btn_submit").attr("disabled", "true");
			window.setTimeout(function () { 
				$.ajax({
					type: "POST",
					url: "MyBank.do?method=save",
					data: $form.serialize(),
					dataType: "json",
					error: function(request, settings) {$.jBox.tip("数据请求失败", "error");},
					success: function(data) {
						$("#btn_submit").removeAttr("disabled");
						if(data.ret == "0"){
							//$.jBox.tip(data.msg, "success");
							$.dialog.tips(data.msg,2, "tips.gif");
						} else {
							//$.jBox.tip(data.msg, "info");
							$.dialog.tips(data.msg,2, "success.gif");
							init();
						}
					}
				});	
	    	}, 1000);
		
		} 
		
	});
	
	
});
var state = 0;        

function sendSms(){
	var mobile = $("#mobile").val();
	if ("" == mobile) {
		alert("请先输入手机号！");
		$("#mobile").focus();
		return false;
	}
	if("" == $("#verificationCode").val()){
		alert("请先验证码！");
		$("#veri_code").focus();
		return false;
	}
	
	if (state == 0) {
		state = "1"; 
		i = 120; 
		$("#mobile").attr("readonly", "true");
		$.ajax({
			type: "POST" , 
			url: "${ctx}/CsAjax.do" , 
			data:"method=sendMobileMessage&mobile=" + mobile + "&veri_code=" + $("#verificationCode").val() + "&type=3&bdType=2" + "&t=" + new Date(),
			dataType: "json" , 
	        async: true, 
	        error: function (request, settings) {alert(" 数据加载请求失败！ "); },
	        success: function (result) {
	        	state= "0";
	        	if (result.ret == 1) {
	        		$("#sendMobileCode").removeAttr("onclick");
					clock();
				}else{
					alert("发送短信失败，请联系管理员！");
				}
	        } 
		});
	}
}

function clock() {
	i--;
	$("#sendMobileCode").html("<i class='fa fa-send'></i>" + i + "秒后获取验证码</button>"); 
	if(i > 0) {
		setTimeout("clock();", 1000);
	} else {
		state = "0"; 
		$("#sendMobileCode").html("获取短信验证码");
		$("#sendMobileCode").attr("onclick","sendSms();");
		$("#mobile").removeAttr("readonly");
	}
}

function updateVerCode(){
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
}


//]]></script>
</body>
</html>

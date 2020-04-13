<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />

</head>
<body>
<div class="mainbox mine">
<html-el:form action="/customer/MySecurityCenter.do" styleClass="ajaxForm">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <c:if test="${not empty user.email}">
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">原邮箱：</td>
         <td>${user.email}</td>
       </tr>
       </c:if>
       <tr>
         <td  nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>新邮箱：</td>
         <td><html-el:text property="email" styleId="email" maxlength="20" style="width:200px" styleClass="webinput"  onblur="validEmail($(this).val());"/>&nbsp;<button class="bgButtonFontAwesome" type="button" onclick="sendEmailSms()" id="sendEmail"><i class="fa fa-send"></i>发送邮件</button></td>
       </tr>
       <tr>
         <td  nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>动态码：</td>
         <td><html-el:text property="ver_code" styleId="ver_code" maxlength="6" style="width:100px" styleClass="webinput" /></td>
       </tr>
       <tr>
        <td style="text-align:center" colspan="2">
          <button class="bgButtonFontAwesome" type="button" id="btn_submit"><i class="fa fa-save"></i>保 存</button>
       </td>
      </tr>
    </table>
</html-el:form>
</div>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#email").attr("dataType", "Email").attr("msg", "请正确填写新邮箱");
	$("#ver_code").attr("dataType", "Require").attr("msg", "请填写动态码");
	
	var f0 = $(".ajaxForm").get(0);
	$("#btn_submit").click(function(){
		if (Validator.Validate(f0, 3)) {
			$.jBox.tip("数据提交中...", 'loading');
			window.setTimeout(function () {
				$.ajax({
					type: "POST",
					url: "?method=modifyEmail",
					data: $(f0).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						returnTo(data.ret,data.msg);
					}
				});	
			}, 1000);
			return true;
		}
		return false;
	});
});

function returnTo(ret,msg){
	var api = frameElement.api, W = api.opener;
	W.refreshPage(ret,msg);
	api.close();
}
var state = 0;
//验证邮箱
function validEmail(email){
	if ("" != email && $("#email").attr("readonly") != "readonly") {
		var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
		if (email.match(reg)) {
			$.ajax({
				type: "POST" , 
				url: "${ctx}/CsAjax.do" , 
				data:"method=validateEmail&email=" + email + "&t=" + new Date(),
				dataType: "json", 
		        async: true, 
		        error: function (request, settings) {alert(" 数据加载请求失败！ "); }, 
		        success: function (result) {
					if (result == 0) {
						$.jBox.alert("参数丢失！", '提示');
						state = "-1";
						return false;
					} else if (result == 1) {
						state = "0";
					} else if (result == 2) {
						$.jBox.alert("该邮箱地址已被占用！", '提示');
						state = "-2";
						return false;
					}
		        }
			});
		} else {
			$.jBox.alert("邮箱格式不正确！", '提示');
			state = "-3";
			return false;
		}
	}
}

function sendEmailSms(){
	var email = $("#email").val();
	if ("" == email) {
		$.jBox.alert("请先输入邮箱地址！", '提示');
		$("#mobile").focus();
		return false;
	}
	
	if (state == 0) {
		state = "1"; 
		i = 120; 
		$("#email").attr("readonly", "true");
		$.ajax({
			type: "POST" , 
			url: "${ctx}/CsAjax.do" , 
			data:"method=sendEmail&email=" + email + "&t=" + new Date(),
			dataType: "json" , 
	        async: true, 
	        error: function (request, settings) {alert(" 数据加载请求失败！ "); },
	        success: function (result) {
	        	if (result.state == 1) {
	        		$("#sendEmail").removeAttr("onclick");
					clock();
				}else{
					$.jBox.alert("发送短信失败，请联系管理员！", '提示');
				}
	        } 
		});
	}
}

function clock() {
	i--;
	$("#sendEmail").html("<i class='fa fa-send'></i>" + i + "秒后获取再次发送</button>"); 
	if(i > 0) {
		setTimeout("clock();", 1000);
	} else {
		state = "0"; 
		$("#sendEmail").html("发送邮件");
		$("#sendEmail").attr("onclick","sendEmail();");
		$("#mobile").removeAttr("readonly");
	}
}
//]]></script>
</body>
</html>

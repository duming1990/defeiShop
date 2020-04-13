<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="MSThemeCompatible" content="no" />
<meta name="MSSmartTagsPreventParsing" content="true" />
<meta name="Description" content="${app_name}" />
<meta name="Authorsite" content="1" />
<meta name="Robots" content="all" />
<title>重置密码 - ${app_name}</title>
<link href="${ctx}/styles/indexv3/css/top.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv3/css/global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv3/css/fonts.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv3/css/bottom.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv2/css/left_sort.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv2/css/nmnetwork.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/index/css/login_register.css" rel="stylesheet" type="text/css"  />
<link href="${ctx}/styles/index/css/register.css" rel="stylesheet" type="text/css"  />
<style type="text/css">
/**********----------get pw----------**********/
.step{overflow:hidden;margin-bottom:30px;height:25px;}
.step li{float:left;background:url(${ctx}/styles/index/images/p1.jpg) no-repeat top right #ededed;height:25px;line-height:25px;color:#999999;padding:0 86px 0 85px;font-weight:bold;}
.step .one{background:url(${ctx}/styles/index/images/p5.jpg) no-repeat top right #bbbbbb;color:#FFF;}
.step .one1{background:url(${ctx}/styles/index/images/p9.jpg) no-repeat top right #bbbbbb;color:#FFF;}
.step .two{background:url(${ctx}/styles/index/images/p10.jpg) no-repeat top right #bbbbbb;color:#FFF;}
.step .two1{background:url(${ctx}/styles/index/images/p9.jpg) no-repeat top right #bbbbbb;color:#FFF;}
.step .three{background:url(${ctx}/styles/index/images/p5.jpg) no-repeat top right #bbbbbb;color:#FFF;}
.step .cur{background:url(${ctx}/styles/index/images/p2.jpg) no-repeat top right #ffe6bc;color:#ff6600;}
.step .four{background:none #ededed;}
.step .four1{background:#ffe6bc;}
</style>
</head>
<body>
<jsp:include page="./_header.jsp" flush="true" />
<div class="login_center">
	<div class="login_head">
		<ul id="register">
			<li class="Personal" id="Personal">找回密码</li>
  		</ul>
	</div>
	<div class="login_content">
		<div class="step">
			<ul>
        		<li class="two1">填写账户名</li>
        		<li class="two">验证身份</li>
				<li class="three cur">设置新密码</li>
				<li class="four">完成</li>
			</ul>
		</div>
		<div class="login_form">
			<html-el:form action="/GetPwBack.do" method="post">
				<html-el:hidden property="method" value="stepThree" />
				<html-el:hidden property="id" styleId="id" value="${af.map.id}" />
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="rtable3">
					<tr>
						<td align="right" width="30%" style="height:60px;font-family: 微软雅黑,宋体;">新密码：</td>
						<td colspan="2" style="color:#F60;"><input type="password" name="password" id="password" class="sms" style="width:200px;" maxlength="50" /></td>
					</tr>
					<tr>
						<td align="right" style="height:60px;font-family: 微软雅黑,宋体;">重复新密码：</td>
						<td colspan="2" style="color:#F60;"><input type="password" name="repeat_password" id="repeat_password" class="sms" style="width:200px;" maxlength="50" /></td>
					</tr>
					<tr>
						<td align="right" style="height:60px;">&nbsp;</td>
						<td colspan="2">
							<input type="button" name="btn_submit" id="btn_submit" class="login_btn" style="width:94px;" value="下一步" />
						</td>
					</tr>
				</table>
			</html-el:form>
		</div>
		<div class="form-right">
			<div><img src="${ctx}/styles/imagesPublic/ico3.png" width="45" height="47" /><p>已有帐号？请登陆！</p></div>
			<div class="form-login" onclick="location.href='${ctx}/login.shtml';">用户登录</div>
		</div>
	</div>
</div>
<jsp:include page="./_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 

<script type="text/javascript">//<![CDATA[
var f = document.forms[0];

$(document).ready(function(){
	$("#password").attr("datatype","User_name").attr("Require","true").attr("msg","请填写密码,且必须在4-16个字符之间！");
	$("#repeat_password").attr("datatype","Repeat").attr("to","password").attr("msg","密码不一致！");
	// 提交
	$("#btn_submit").click(function(){
		if(Validator.Validate(this.form, 3)){
			 $("#btn_submit").attr("disabled", "true");
			 this.form.submit();
		}
	});
});
//]]></script>
</body>
</html>
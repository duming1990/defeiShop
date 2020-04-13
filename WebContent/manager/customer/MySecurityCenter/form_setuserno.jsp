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
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">原会员卡：</td>
         <td>${user.user_no}</td>
       </tr>
       <tr>
         <td  nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>用户密码：</td>
         <td><html-el:password property="password" styleId="password" style="width:200px" styleClass="webinput"  /></td>
       </tr>
       <tr>
         <td  nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>新会员卡：</td>
         <td><html-el:text property="card_no" styleId="card_no" style="width:200px" styleClass="webinput" /></td>
       </tr>
       <tr>
         <td  nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>激活码：</td>
         <td><html-el:text property="card_pwd" styleId="card_pwd" style="width:100px" styleClass="webinput" /></td>
       </tr>
       <tr>
         <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>验证码：</td>
         <td><html-el:text property="verificationCode" styleId="verificationCode" maxlength="4" style="width:100px" styleClass="webinput" />
          &nbsp;<img height="22" width="72" class="signup-captcha-img" id="veri_img" style="vertical-align: bottom;"/> 
          <a tabindex="-1" class="captcha-refresh inline-link" href="javascript:updateVerCode();">看不清楚？换一张</a>
         </td>
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
	$("#verificationCode").attr("dataType", "Require").attr("msg", "请填写验证码！");
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
	$("#password").attr("datatype","Require").attr("msg","请填写用户密码");
	$("#card_no").attr("datatype","Require").attr("msg","请填写新会员卡");
	$("#card_pwd").attr("datatype","Require").attr("msg","请填写激活码");
	$("#captcha").attr("datatype","Require").attr("msg","请填写验证码");
	
	var f0 = $(".ajaxForm").get(0);
	$("#btn_submit").click(function(){
		if (Validator.Validate(f0, 3)) {
			$.jBox.tip("数据提交中...", 'loading');
			window.setTimeout(function () {
				$.ajax({
					type: "POST",
					url: "?method=modifyUserNo",
					data: $(f0).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						if(data.ret == "1"){
							$.jBox.tip(data.msg, "success");
							window.setTimeout(function () {
								returnTo();
							}, 1000);
						} else {
							$.jBox.tip(data.msg, "info");
						}
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

function updateVerCode(){
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
}
//]]></script>
</body>
</html>

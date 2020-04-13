<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form styleClass="ajaxForm">
    <html-el:hidden property="own_entp_id" styleId="own_entp_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <td width="15%" class="title_item">店名：</td>
        <td>${fn:escapeXml(entpInfo.entp_name)}</td>
      </tr>
      <tr>
        <td width="15%" class="title_item"><span style="color: #F00;">*</span>手机号码：</td>
        <td width="85%"><html-el:text property="user_name" maxlength="20" styleClass="webinput" styleId="user_name" style="width:200px" onblur="validMobile($(this).val());"/>
          &nbsp;<span style="color:red;">将作为登录账号</span></td>
      </tr>
      <tr>
        <td width="15%" class="title_item"><span style="color: #F00;">*</span>真实姓名：</td>
        <td width="85%">
          <html-el:text property="real_name" maxlength="20" styleClass="webinput" styleId="real_name" style="width:200px"/>
        </td>
      </tr>
      <tr> 
         <td class="title_item"><span style="color: #F00;">*</span>密码：</td>
         <td><html-el:password property="password" maxlength="40" styleClass="webinput" styleId="password" style="width:200px" /></td> 
      </tr> 
      <tr> 
         <td class="title_item"><span style="color: #F00;">*</span>确认密码：</td>
         <td><html-el:password property="_password" maxlength="40" styleClass="webinput" styleId="_password" style="width:200px" /></td> 
      </tr> 
      <tr>
        <td colspan="2" style="text-align: center">
          <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
        </td>
      </tr>
    </table>
  </html-el:form>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript">//<![CDATA[

var api = frameElement.api, W = api.opener;                                  
		var f = document.forms[0];

		$("#user_name").attr("datatype", "Mobile").attr("msg", "请正确填写手机号");
		$("#real_name").attr("datatype", "Require").attr("msg", "真实姓名必须填写");
		$("#password").attr("datatype","LimitB").attr("min","4").attr("max","20").attr("msg","请填写密码,且必须在4-20个字符之间！");
		$("#_password").attr("datatype","Repeat").attr("to","password").attr("msg","密码不一致！");

		// 提交
		$("#btn_submit").click(function() {
			if (Validator.Validate(f, 3)) {
				
				$.jBox.tip("正在操作...", 'loading');
				
				$.ajax({
					type: "POST",
					url: "EntpApply.do",
					data: "method=saveEntpLinkUserInfo&" + $(".ajaxForm").serialize(),
					dataType: "json",
					error: function(request, settings) {flag = false;},
					success: function(data) {
						if (data.code == 1) {
							$.jBox.tip("添加成功", "success",{timeout:1000});
							window.setTimeout(function(){
								W.refreshPage();	
							},2000);
						} else {
							$.jBox.tip(data.msg, "error",{timeout:1000});
						}
					}
				});
			}
		});

  function validMobile(mobile){
	if ("" != mobile && $("#mobile").attr("readonly") != "readonly") {
		var reg = /^((\(\d{2,3}\))|(\d{3}\-))?((1[3-9]\d{9}))$/;
		if (mobile.match(reg)) {
			$.ajax({
				type: "POST" , 
				url: "${ctx}/CsAjax.do" , 
				data:"method=validateMobile&mobile=" + mobile + "&t=" + new Date(),
				dataType: "json", 
		        async: true, 
		        error: function (request, settings) {alert(" 数据加载请求失败！ ");	$("#btn_submit").attr("disabled", "true");}, 
		        success: function (result) {
					if (result == 0) {
						alert('参数丢失！', '提示');
						return false;
					}else if (result == 2) {
						$.jBox.tip('该手机号码已被注册！', "error");
						return false;
					}
		        }
			});
		} else {
			alert('手机格式不正确！', '提示');
			$("#btn_submit").attr("disabled", "true");
			return false;
		}
	}
 }		
//]]>
</script>
</body>
</html>

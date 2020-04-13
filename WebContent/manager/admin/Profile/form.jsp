<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
   <%@ include file="/commons/pages/messages.jsp" %>
  <html-el:form action="/admin/Profile" enctype="multipart/form-data" styleClass="formSave">
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="queryString" styleId="queryString" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">用户个人信息修改</th>
      </tr>
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item" >登录名：</td>
        <td width="88%"><c:out value="${af.map.user_name}" /></td>
      </tr>
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item" ><span style="color: #F00;">*</span>真实姓名：</td>
        <td width="88%"><html-el:text property="real_name" maxlength="20" styleClass="webinput" styleId="real_name" style="width:200px" /></td>
      </tr>
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>原密码：</td>
        <td width="88%"><html-el:password property="old_password" styleId="old_password" maxlength="16" style="width:200px" styleClass="webinput" /></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>新密码：</td>
        <td>
        <html-el:password property="new_password" styleId="new_password" maxlength="16" style="width:200px" styleClass="webinput" />
         &nbsp;6-20个大小写英文字母、符号或数字的组合
        </td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>重复新密码：</td>
        <td><html-el:password property="repeat" styleId="repeat" maxlength="16" style="width:200px" styleClass="webinput" /></td>
      </tr>
      <tr>
        <td colspan="2" align="center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 置" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function() {
	
	$("#real_name").attr("dataType", "Require").attr("msg", "真实姓名不能为空");
	$("#old_password").attr("dataType", "Require").attr("msg", "原密码不能为空");
	$("#new_password").attr("dataType", "Require").attr("msg", "新密码不能为空");
	$("#repeat").attr("datatype", "Repeat").attr("to", "new_password").attr("msg", "两次输入的密码不一致");
	$("#btn_submit").click(function() {
		if (Validator.Validate(this.form, 3) && check_password_regx()) {
			$.jBox.tip("数据提交中...", 'loading');
			$("#btn_submit").attr("disabled", "true");
			window.setTimeout(function () { 
				$.ajax({
					type: "POST",
					url: "Profile.do?method=save",
					data: $('.formSave').serialize(),
					dataType: "json",
					error: function(request, settings) {$.jBox.tip("数据请求失败", "error");},
					success: function(data) {
						$("#btn_submit").removeAttr("disabled");
						if(data.ret == "0"){
							$.jBox.tip(data.msg, "info");
						} else {
							$.jBox.tip(data.msg, "success");
							window.setTimeout(function () { 
							 $.cookie("parParId_parId_sonId_cookie", null);
							 location.href='../login.do?method=logout';
							},1500);
						}
					}
				});	
	    	}, 1000);
		}
	});
});
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>

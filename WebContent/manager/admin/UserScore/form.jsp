<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/UserScore.do">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="list"/>
    <html-el:hidden property="id" styleId="id"/>
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">信息查询 </th>
      </tr>
      <tr>
        <td width="15%" class="title_item"><span style="color: #F00;">*</span> 用户名： </td>
        <td width="85%"><html-el:text property="user_name" maxlength="20" styleClass="webinput" styleId="user_name" style="width:200px" />
          &nbsp;<span id="user_name_tip" style="display:none;"></span></td>
      </tr>
      <tr>
        <td width="15%" class="title_item"><span style="color: #F00;">*</span> 手机号码： </td>
        <td width="85%"><html-el:text property="mobile" maxlength="20" styleClass="webinput" styleId="mobile" style="width:200px" />
          &nbsp;<span id="mobile_tip" style="display:none;"></span></td>
      </tr>
      <tr>
        <td colspan="2" style="text-align:center"><html-el:button property="" value="查 询" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];

var user_not_exits = '<span id="tip" style="color:#33CC33;">对不起，该用户不存在</span>';


$("#btn_submit").click(function(){
	if(Validator.Validate(this.form, 3)){
		var u_name = $.trim($("#user_name").val());
		var mobile = $.trim($("#mobile").val());
		
		if("" == u_name && "" == mobile) {
			alert('用户名和密码不能同时为空');
			return false;
		}
		$(":submit").attr("disabled", "disabled");
		$("#tip").remove();
		$.post("UserScore.do?method=checkUserExist",{user_name : u_name, mobile : mobile},function(data){
			if(data == "1"){
				$("#user_name_tip").show().append(user_not_exits);
				return false;
			} else {
				 $("#btn_submit").attr("value", "正在查询...").attr("disabled", "true");
			        $("#btn_reset").attr("disabled", "true");
			        $("#btn_back").attr("disabled", "true");
					f.submit();
			}
		});
	}
});
function setOnlyNum() {
	$(this).css("ime-mode", "disabled");
	$(this).attr("t_value", "");
	$(this).attr("o_value", "");
	$(this).bind("dragenter",function(){
		return false;
	});
	$(this).keypress(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).keyup(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).blur(function (){
		if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value;}
		if(this.value.length == 0) this.value = 0;
	});
}
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>

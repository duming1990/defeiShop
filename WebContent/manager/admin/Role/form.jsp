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
  <html-el:form action="/admin/Role.do">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="id" styleId="id"/>
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">角色信息 </th>
      </tr>
      <tr>
        <td width="15%" class="title_item"><span style="color: #F00;">*</span> 角色名称： </td>
        <td width="85%"><html-el:text property="role_name" maxlength="20" styleClass="webinput" styleId="role_name" style="width:200px" />
          &nbsp;<span id="role_name_tip" style="display:none;"></span></td>
      </tr>
      <tr>
        <td class="title_item">排序值：</td>
        <td><html-el:text property="order_value" styleClass="webinput" styleId="order_value" size="4" maxlength="4"/>
          值越大，显示越靠前，范围：0-9999 </td>
      </tr>
      <c:if test="${af.map.is_del eq 1}">
        <tr>
          <td class="title_item">是否删除：</td>
          <td><html-el:select property="is_del" styleId="is_del">
              <html-el:option value="0">否</html-el:option>
              <html-el:option value="1">是</html-el:option>
            </html-el:select></td>
        </tr>
      </c:if>
      <tr>
        <td colspan="2" style="text-align:center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
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

$("#role_name").attr("datatype","Require").attr("msg","角色名称必须填写");
$("#order_value").attr("datatype","Number").attr("msg","排序值必须在0~9999之间的正整数");
// 提交
//$("#btn_submit").click(function(){
//	if(Validator.Validate(this.form, 3)){
//        $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
 //       $("#btn_reset").attr("disabled", "true");
 //       $("#btn_back").attr("disabled", "true");
//		this.form.submit();
//	}
//});

var role_name_exits = '<span id="tip" style="color:red;">对不起，此角色已被注册</span>';
var role_name_not_exits = '<span id="tip" style="color:#33CC33;">恭喜你，该角色名可以使用</span>';

$("#btn_submit").click(function(){
	if(Validator.Validate(this.form, 3)){
	var r_name = $.trim($("#role_name").val());
	var r_id = $("#id").val();
	if("" == r_name) {
		return false;
	}
	$(":submit").attr("disabled", "disabled");
	$("#tip").remove();
	//var val = this.value;
	$.post("Role.do?method=checkRoleName",{role_name : r_name, id : r_id},function(data){
		if(data == "1"){
			$("#role_name_tip").show().append(role_name_exits);
			return false;
		} else {
			 $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
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

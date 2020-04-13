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
  <html-el:form action="/customer/EntpMsg.do" enctype="multipart/form-data">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="id" styleId="id"/>
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="p_index" styleId="p_index" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th colspan="4">申请留言</th>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">所属区域：</td>
        <td><html-el:select property="province" styleId="province" style="width:120px;" disabled="disabled">
            <html-el:option value="">请选择...</html-el:option>
           </html-el:select>
	       &nbsp;
	       <html-el:select property="city" styleId="city" style="width:120px;">
	         <html-el:option value="">请选择...</html-el:option>
	       </html-el:select>
	       &nbsp;
	       <html-el:select property="country" styleId="country" style="width:120px;">
	         <html-el:option value="">请选择...</html-el:option>
	       </html-el:select></td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>标题：</td>
        <td><html-el:text property="msg_title" maxlength="50" styleId="msg_title" style="width:300px"/></td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>留言内容：</td>
        <td><html-el:text property="msg_content" maxlength="50" styleId="msg_content" style="width:300px"/></td>
      </tr>
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
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript">//<![CDATA[

var f = document.forms[0];

$(document).ready(function(){
	
	$("#msg_title").attr("datatype", "Require").attr("msg", "请填写标题！");
	$("#msg_content").attr("datatype", "Require").attr("msg", "请填写内容！");
	
	$("#province").citySelect({
        data:getAreaDic(),
        province:"${af.map.province}",
        city:"${af.map.city}",
        country:"${af.map.country}",
        province_required : true,
        city_required : false,
        country_required : false,
        callback:function(selectValue,selectText){
        	if(null != selectValue && "" != selectValue){
        		var p_indexs = selectValue.split(",");
        		if(null != p_indexs && p_indexs.length > 0){
        			$("#p_index").val(p_indexs[p_indexs.length - 1]);
        		}
        	}
        }
 });

	
	$("#province").change(function(){
		if (this.value.length != 0) {
			this.form.p_index.value = this.value;
			$("#city").val("");
			$("#country").val("");
		}
	});
	$("#city").change(function(){
		if (this.value.length != 0) {
			this.form.p_index.value = this.value;
			$("#country").val("");
		} else {
			this.form.p_index.value = this.form.province.value;
		}
	});
	$("#country").change(function(){
		if (this.value.length != 0) {
			this.form.p_index.value=this.value;
		} else {
			this.form.p_index.value = this.form.city.value;
		}
	});
});     

// 提交
$("#btn_submit").click(function(){
	if(Validator.Validate(f, 3)){
		 $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
		 $("#btn_reset").attr("disabled", "true");
		 $("#btn_back").attr("disabled", "true");
		 f.submit();
	}
});
                                          
//]]></script>
</body>
</html>

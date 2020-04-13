<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/admin/css/admin.css"  />
<link href="${ctx}/scripts/jquery-ui/themes/base/jquery-ui.custom.css" rel="stylesheet" type="text/css" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script> 
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/customer/MBaseLink" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="pre_number" styleId="pre_number" />
    <html-el:hidden property="link_type" styleId="link_type" />
    <html-el:hidden property="type" styleId="type" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="par_id" styleId="par_id" />
    <html-el:hidden property="par_son_type" styleId="par_son_type" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="4">基本信息</th>
      </tr>
       <tr>
        <td class="title_item"><font color="red">*</font>基本信息：</td>
        <td colspan="3" width="93%">
         <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
         <tr id="addTr">
          <td align="center" width="15%">标题</td>
          <td align="center" width="33%">链接地址</td>
          <td align="center" width="8%">排序值</td>
           <td align="center" width="7%"><div id="divFile"> <img src="../../images/+.gif" style="vertical-align:middle; cursor: pointer;" id="WlAddTr" title="再添加一个" /></div></td>
          </tr>
          </table></td>
      </tr>
      <tr>
        <td colspan="4" align="center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript"><!--//<![CDATA[

$(document).ready(function(){
	$("#btn_submit").click(function(){
		if(Validator.Validate(this.form, 1)){
            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
			this.form.submit();
		}
	})

});
	
var i=0;
$("#WlAddTr").click(function (){
	var html = '<tr id="tBodyHidden_'+i+'">';
	html +='<td  align="center" nowrap="nowrap"><input name="titles" maxlength="60" id="titles'+i+'" type="text" class="webinput" style="width: 100px;""/>';
	html +='<input name="title_hidden" maxlength="60" id="title_hidden'+i+'" type="hidden" class="webinput" style="width: 70px;"/></td>';
	html +='<td  align="center" nowrap="nowrap"><input name="link_urls" maxlength="200" id="link_urls'+i+'" type="text" class="webinput" style="width: 300px;"/></td>';
	html +='';
	html +='<td align="center"><input name="order_values" maxlength="60" id="order_values'+i+'" type="text" class="webinput" value="0" style="width: 40px;"/></td>';
	html +='<td align="center"><img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" id="DelTr2" title="删除" onclick="deleteRow('+i+')"/></td>';
	html +='</tr>';
    $("#addTr").after(html);
    	$("#titles"+i).attr("dataType", "Require").attr("msg", "标题描述不能为空");
    	$("#order_values"+i).attr("dataType", "Require").attr("msg", "排序值不能为空");
		$("#link_urls"+i).attr("datatype","Url2").attr("msg","请正确填写链接url地址");
	i++;
});
function deleteRow(i){
	$("#tBodyHidden_"+i).remove();
}
//]]></script>
</body>
</html>
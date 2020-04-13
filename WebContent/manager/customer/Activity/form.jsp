<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/scripts/jquery-ui/themes/base/jquery-ui.custom.css" rel="stylesheet" type="text/css" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/Activity" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="3">基本信息</th>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>活动名称：</td>
        <td colspan="2" width="85%"><html-el:text property="title" styleId="title" maxlength="125" style="width:480px" styleClass="webinput" />
		</td>
      </tr>
      
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>开始时间：</td>
        <td  colspan="2" height="24" width="85%"><fmt:formatDate value="${af.map.start_date}" pattern="yyyy-MM-dd HH:mm:ss" var="_start_date" />
          <html-el:text property="start_date" styleId="start_date" size="10" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});" style="cursor:pointer;width:200px;" value="${_start_date}" /></td>
      </tr>
      
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>结束时间：</td>
        <td  colspan="2" height="24" width="85%"><fmt:formatDate value="${af.map.end_date}" pattern="yyyy-MM-dd HH:mm:ss" var="_end_date" />
          <html-el:text property="end_date" styleId="end_date" size="10" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});" style="cursor:pointer;width:200px;" value="${_end_date}" /></td>
      </tr>
      <tr>
        <td colspan="3" align="center"><html-el:submit property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
    <jsp:include page="../../../public/public_color_select.jsp" flush="true"/>
  </html-el:form>
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jquery-ui/external/jquery.bgiframe.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jquery-ui/ui/minified/jquery-ui.custom.min.js"></script> 

<script type="text/javascript"><!--//<![CDATA[

$(document).ready(function(){

	


	$("#title").attr("dataType", "Require").attr("msg", "标题必须填写");
	$("#start_date").attr("dataType", "Require").attr("msg", "开始时间必须填写").attr("require", "false");
	$("#end_date").attr("dataType", "Require").attr("msg", "结束时间必须填写").attr("require", "false");
	
	

	var f = document.forms[0];
	f.onsubmit = function () {
// 		var start_date = $("#start_date").val();
// 		var end_date = $("#end_date").val();
		
// 		console.info(start_date)
// 		console.info(end_date)
		
// 		if (start_date != "" && end_date != "") {
// 			if (start_date < end_date) {
// 				alert("结束日期不得早于开始日期,请重新选择!")
// 				return false;
// 			}
// 		}
		

		if(Validator.Validate(this, 1)){
            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
			f.submit();
		}
		return false;
	}
});



//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>
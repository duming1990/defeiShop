<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>收货地址信息 - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../../public_page_in_head.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
 <div style="padding-top: 20px;" align="center">
      <html-el:form action="/customer/MyOrderEntp">
        <html-el:hidden property="method" value="saveDelay" />
        <html-el:hidden property="id" styleId="id" /> 
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
          <tr>
            <th colspan="2">延迟收货时间</th>
          </tr> 
          <tr>
            <td width="20%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span><strong>自动确认收货时间：</strong></td>
            <td align="left">
             <input type="text" name="finish_date" value="<fmt:formatDate value="${af.map.finish_date}" pattern="yyyy-MM-dd" />" id="finish_date" size="10" maxlength="10" readonly="true" class="webinput" onclick="WdatePicker();" />
     		</td>
          </tr> 
          <tr>
            <td colspan="2" align="center">
            <html-el:button property="" value="提交" styleClass="bgButton" styleId="btn_submit" />
             &nbsp;
            <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="window.parent.$.colorbox.close();" /></td>
          </tr>
        </table>
      </html-el:form>
</div>
<!-- main end -->
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/rate/min.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript">//<![CDATA[
var f = document.forms[0]; 
var today = '<fmt:formatDate value="${af.map.finish_date}" pattern="yyyy-MM-dd" />';
$(document).ready(function(){ 
	//提交
	$("#btn_submit").click(function(){
		if($("#finish_date").val() <=today){
			alert("延迟收货时间不能小于原时间！");
			return false;
		}
		if(Validator.Validate(f, 3)){
			f.submit();
		}
	});
});
//]]></script>
</body>
</html>
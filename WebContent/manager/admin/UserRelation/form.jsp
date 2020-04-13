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
  <html-el:form action="/admin/UserRelation.do">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="list"/>
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">信息查询 </th>
      </tr>
      <tr>
        <td width="15%" class="title_item"><span style="color: #F00;">*</span>用户名： </td>
        <td width="85%">
        <html-el:hidden property="user_id" styleId="user_id" />
        <html-el:text property="user_name" maxlength="20" styleClass="webinput" styleId="user_name" style="width:200px" readonly="true"/>
        &nbsp;
        <a class="butbase" onclick="getUserInfo()" ><span class="icon-search">选择</span></a>
        </td>
      </tr>
      <tr>
        <td colspan="2" align="center">
        <html-el:button property="" value="查 询" styleClass="bgButton" styleId="btn_submit" /></td>
      </tr>
    </table>
  </html-el:form>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];

$("#user_name").attr("datatype", "Require").attr("msg", "请选择用户！");

$("#btn_submit").click(function(){
	if(Validator.Validate(this.form, 3)){
		 $("#btn_submit").attr("value", "正在查询...").attr("disabled", "true");
		 f.submit();
	}
});

function getUserInfo(){
	var url = "${ctx}/BaseCsAjax.do?method=getUserInfo";
	$.dialog({
		title:  "选择用户",
		width:  770,
		height: 550,
        lock:true ,
		content:"url:"+url
	});
}

//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>

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
    <html-el:hidden property="method" value="saveScore"/>
    <html-el:hidden property="id" styleId="id" value="${af.map.id}"/>
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">积分赠送 </th>
      </tr>
      <tr>
        <td width="15%" class="title_item">当前总积分： </td>
        <td width="85%">
        <fmt:formatNumber var="score_bi" value="${score + user_score_union}" pattern="0.########"/>
        ${score_bi}
        </td>
      </tr>
      <tr>
        <td width="15%" class="title_item">个人积分： </td>
        <td width="85%">
        <fmt:formatNumber var="score_bi" value="${score}" pattern="0.########"/>
        ${score_bi}
        </td>
      </tr>
      <tr>
        <td width="15%" class="title_item"><span style="color: #F00;">*</span>积分： </td>
        <td width="85%"><html-el:text property="score" maxlength="20" styleClass="webinput" styleId="user_name" style="width:200px" />
          &nbsp;<span id="user_name_tip" style="display:none;"></span>[赠送的都是个人积分]</td>
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

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];

$("#btn_submit").click(function(){
	if(Validator.Validate(f, 3)){
	        $("#btn_submit").attr("value", "正在提交").attr("disabled", "true");
	        $("#btn_reset").attr("disabled", "true");
	        $("#btn_back").attr("disabled", "true");
			f.submit();
	}
});
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>

<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
  <html-el:form action="/admin/UserInfo.do">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="savePinkunjin" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="p_index" styleId="p_index" />
    <html-el:hidden property="edit_bi_welfare" styleId="edit_bi_welfare" value="1" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">用户基本信息</th>
      </tr>
      
      <tr>
      <td width="15%" class="title_item">登录名：</td>
      <td width="85%">${fn:escapeXml(af.map.user_name)}</td>
    </tr>
    <tr>
      <td class="title_item">真实姓名：</td>
      <td>${fn:escapeXml(af.map.real_name)}</td>
    </tr>
    <tr>
      <td class="title_item">扶贫金：</td>
      <td><fmt:formatNumber var="bi" value="${af.map.bi_aid}" pattern="0.########"/>
        ${bi}</td>
    </tr>
      
      <tr>
        <td class="title_item">发放扶贫金额：</td>
        <td width="85%">
        <html-el:text property="update_bi_aid" maxlength="80" styleClass="webinput" styleId="update_bi_aid" style="width:200px;" value="" />
        </td>
      </tr>
      
      <tr>
        <td colspan="2" style="text-align: center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript">//<![CDATA[

		var f = document.forms[0];

		
		$("#update_bi_aid").attr("datatype", "Double").attr("msg", "修改福利金额参数不正确,");

		// 提交
		$("#btn_submit").click(function() {
			
			if (Validator.Validate(f, 3)) {

				$("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
				$("#btn_reset").attr("disabled", "true");
				$("#btn_back").attr("disabled", "true");
				f.submit();

			}
		});
	
//]]>
</script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>

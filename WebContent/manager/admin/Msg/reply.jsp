<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<c:set var="type_name" value="商家"/>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/Msg.do" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="is_audit" styleId="is_audit" value="true"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
    <tr>
      <th colspan="4">${type_name}申请信息</th>
    </tr>
    <tr>
      <td width="15%" class="title_item">${type_name}名称：</td>
      <td colspan="3">${real_name}</td>
    </tr>
    <tr>
      <td width="15%" class="title_item">标题：</td>
      <td colspan="3">${af.map.msg_title}</td>
    </tr>
    <tr>
      <td width="15%" class="title_item">内容：</td>
      <td colspan="3">${af.map.msg_content}</td>
    </tr>
    <tr>
      <td width="15%" class="title_item">申请时间：</td>
      <td colspan="3"><fmt:formatDate value="${af.map.send_time}" pattern="yyyy-MM-dd" /></td>
    </tr>
   
    <tr>
      <td width="15%" class="title_item">留言状态：</td>
      <td colspan="3">
		<c:choose>
		    <c:when test="${af.map.info_state eq 0}">未发送</c:when>
		    <c:when test="${af.map.info_state eq 1}">已发送</c:when>
		    <c:when test="${af.map.info_state eq 1}">已删除</c:when>
		    <c:otherwise>未知</c:otherwise>
		</c:choose>
	  </td>
    </tr>
    <tr>
      <td width="15%" class="title_item">回复内容：</td>
      	<td>
      	  <html-el:text property="reply_content" styleId="reply_content" maxlength="200" style="width:90%" />
        </td>
    </tr>
   
      <tr>
        <td colspan="4" align="center">
        <html-el:button property="" value="审 核" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jquery-ui/external/jquery.bgiframe.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jquery-ui/ui/minified/jquery-ui.custom.min.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#audit_state").attr("dataType", "Require").attr("msg", "请选择审核状态！");
	$("#order_value").attr("datatype","Number").attr("msg","排序值必须在0~9999之间的正整数");
	var f = document.forms[0];

	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 3)){
	            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
	            $("#btn_reset").attr("disabled", "true");
	            $("#btn_back").attr("disabled", "true");
				f.submit();
		}
		return false;
	});
});

//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>
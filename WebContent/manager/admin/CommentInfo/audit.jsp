<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/scripts/jquery-ui/themes/base/jquery-ui.custom.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/CommentInfo" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="link_id" styleId="link_id" />
    <html-el:hidden property="id" styleId="id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">评论信息</th>
      </tr>
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item">评论人：</td>
        <td width="88%">${fn:escapeXml(af.map.comm_uname)}</td>
      </tr>
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item">评论人IP：</td>
        <td width="88%">${fn:escapeXml(af.map.comm_ip)}</td>
      </tr>
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item">所评商品名称：</td>
        <td width="88%">${fn:escapeXml(pdInfoName)}</td>
      </tr>
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item">评论心得：</td>
        <td width="88%">${fn:escapeXml(af.map.comm_experience)}</td>
      </tr>
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item">评论图片：</td>
        <td width="88%">
       	<c:forEach items="${baseFilesList}" var="cur">
        <a href="${ctx}/${cur.save_path}"  class="cb">
        <img height="100" src="${ctx}/${cur.save_path}@s400x400" /></a>
        </c:forEach>
        </td>
      </tr>
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item">回复内容：</td>
        <td width="88%">${fn:escapeXml(af.map.reply_content)}</td>
      </tr>
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item">评论时间：</td>
        <td width="88%"><fmt:formatDate value="${af.map.comm_time}" pattern="yyyy-MM-dd" var="_comm_time" />
          <html-el:text property="comm_time" styleId="comm_time" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" style="cursor:pointer;" value="${_comm_time}" /></td>
      </tr>
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item">评论状态：</td>
        <td width="88%"><html-el:select property="comm_state" styleId="comm_state">
            <html-el:option value="0">待审核</html-el:option>
            <html-el:option value="1">审核通过</html-el:option>
            <html-el:option value="-1">审核未通过</html-el:option>
          </html-el:select></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">排序值：</td>
        <td><html-el:text property="order_value" size="4" maxlength="4" value="${empty af.map.order_value ? '0' : af.map.order_value}" styleId="order_value" />
          <span style="color:#ff0000;margin-left:5px;">*</span> 值越大，显示越靠前，范围：0-9999</td>
      </tr>
      <tr>
        <td colspan="3" align="center"><html-el:submit property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	$(".cb").colorbox({rel:'cb'});
	var f = document.forms[0];
	
	f.comm_reply.setAttribute("dataType", "Limit");
	f.comm_reply.setAttribute("max", "1000");
	f.comm_reply.setAttribute("msg", "评论回复字数必须在1000个以内");
	
	f.onsubmit = function(){
		if(Validator.Validate(f, 3)){
	        $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
	        $("#btn_reset").attr("disabled", "true");
	        $("#btn_back").attr("disabled", "true");
			f.submit();
		}
	return false;
	};

});
//]]></script>
</body>
</html>

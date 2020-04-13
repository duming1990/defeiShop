<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <%@ include file="/commons/pages/messages.jsp" %>
   <html-el:form action="/customer/MyShopComment.do" enctype="multipart/form-data">
    <input type="hidden" name="method" value="save" />
    <html-el:hidden property="queryString" />
    <input type="hidden" name="mod_id" value="${af.map.mod_id}" />
    <input type="hidden" name="par_id" value="${af.map.par_id}" />
    <input type="hidden" name="id" value="${af.map.id}" />
    <input type="hidden" name="comm_type" value="${af.map.comm_type}" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th colspan="2">评论回复</th>
      </tr>
      <tr>
        <td  width="15%" nowrap="nowrap" class="title_item"><strong>商品评分：</strong></td>
        <td nowrap="nowrap" valign="middle"><div id="comm_good_rate" style="padding-bottom: 6px;float: left;"></div>
          &nbsp;
          <div style="float: left;padding-left: 10px;padding-top: 4px;color: #FF7B22;" id="hint">${af.map.comm_score}分</div></td>
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
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>回复内容：</td>
        <td width="88%"><textarea name="reply_content" id="reply_content" style="width:400px;height:100px;">${af.map.reply_content}</textarea></td>
      </tr>
      <tr>
        <td align="center" colspan="2"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/rate/min.js"></script>
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];
$(document).ready(function(){
	$(".cb").colorbox({rel:'cb'});
	$("#reply_content").attr("datatype","Limit").attr("min","1").attr("max","256").attr("msg","请填写回复内容，回复内容不能超过256个字！");
	$("#comm_good_rate").raty({
		path:"${ctx}/scripts/rate/img/",
		start:${af.map.comm_score},
		readOnly: true
	});
	
});
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

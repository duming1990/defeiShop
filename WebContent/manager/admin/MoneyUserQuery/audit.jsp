<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<c:set var="type_name" value="用户"/>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/MoneyUserQuery.do" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" value="${af.map.id}"/>
    <html-el:hidden property="user_id" styleId="user_id" value="${af.map.user_id}"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="4">${type_name}提现基本信息</th>
      </tr>
      <tr>
        <td width="15%" class="title_item">${type_name}名称：</td>
        <td colspan="3">${fn:escapeXml(real_name)}&nbsp;&nbsp;<a href="UserInfo.do?method=view&amp;mod_id=${af.map.mod_id}&amp;id=${af.map.add_uid}" class="beautybg">查看会员信息</a></td>
      </tr>
      <tr>
        <td width="15%" class="title_item">${type_name}手机号码：</td>
        <td colspan="3">${fn:escapeXml(mobile)}</td>
      </tr>
      <tr>
        <td width="15%" class="title_item">提现金额：</td>
        <td colspan="3">${fn:escapeXml(af.map.cash_count)}</td>
      </tr>
      <tr>
        <td width="15%" class="title_item">手续费：</td>
        <td colspan="3">${fn:escapeXml(af.map.cash_rate)}</td>
      </tr>
      <tr>
        <td width="15%" class="title_item tip-danger">应付金额：</td>
        <td colspan="3" class="tip-danger">${fn:escapeXml(af.map.cash_pay)}</td>
      </tr>
      <tr>
        <td width="15%" class="title_item">申请时间：</td>
        <td colspan="3"><fmt:formatDate value="${af.map.add_date}" pattern="yyyy-MM-dd HH:mm" /></td>
      </tr>
      <tr>
        <td width="15%" class="title_item">申请说明：</td>
        <td colspan="3">${fn:escapeXml(af.map.add_memo)}</td>
      </tr>
      <tr>
        <th colspan="4">审核信息</th>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>审核状态：</td>
        <td colspan="3"><html-el:select property="info_state" styleId="info_state">
            <html-el:option value="">请选择...</html-el:option>
            <html-el:option value="0">待审核</html-el:option>
            <html-el:option value="1">审核通过</html-el:option>
            <html-el:option value="-1">审核不通过</html-el:option>
          </html-el:select>
          &nbsp;&nbsp;<span class="tip-danger">如果审核不通过，则对应的钱会直接打回给消费者！</span>
        </td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>审核说明:</td>
        <td colspan="3"><html-el:text property="audit_memo" styleId="audit_memo" maxlength="125" style="width:480px" styleClass="webinput" /></td>
      </tr>
      <tr>
        <td colspan="4" align="center"><html-el:button property="" value="审 核" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
<script type="text/javascript">//<![CDATA[
$("a.beautybg").colorbox({width:"90%", height:"80%", iframe:true});

$(document).ready(function(){
	$("#audit_state").attr("dataType", "Require").attr("msg", "请选择审核状态！");
	$("#audit_memo").attr("dataType", "Require").attr("msg", "请填写审核说明！");
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

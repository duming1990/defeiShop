<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心 - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/commons/swfupload/style/default.css" type="text/css" />
<style type="text/css">
.file_hidden_class {
	height:24px;
	border-top:1px #a8a8a8 solid;
	line-height:24px;
	padding-left:6px;
	border-left:1px #a8a8a8 solid;
	border-right:1px #e6e6e6 solid;
	border-bottom:1px #e6e6e6 solid;
}
</style>
</head>
<body style="height: 2000px;">
<div class="mainbox mine">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
      <tr>
       <th colspan="4">福利卡信息</th>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item">标题：</td>
        <td colspan="2" width="88%">${fn:escapeXml(af.map.title)}</td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item">额度：</td>
        <td colspan="2" width="88%">${fn:escapeXml(af.map.card_amount)}</td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item">数量：</td>
        <td colspan="2" width="88%">${fn:escapeXml(af.map.card_count)}</td>
      </tr>
      <tr id="comm_tr">
        <td width="14%" nowrap="nowrap" class="title_item">所选县域：</td>
        <td colspan="2" width="88%">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left"  id="append_table">
                <tr id="comm_list_tr">
                	<th nowrap="nowrap" width="40%">县域名称</th>
                	<th nowrap="nowrap" width="50%">所属区域</th>
                </tr>
                <c:forEach items="${list}" var="cur">
                 <tr class="html">
	    			 <input type="hidden" name="service_id" id="service_id" value="${cur.service_id}" />
	    			 <input type="hidden" name="service_name" id="service_name" value="${cur.service_name}" />
	    			 <input type="hidden" name="p_index" id="p_index" value="${cur.p_index}" />
	    			 <input type="hidden" name="p_name" id="p_name" value="${cur.p_name}" />
	    			 <td align="center">${cur.service_name}</td>
	    			 <td align="center">${cur.p_name}</td>
    			 </tr>
                </c:forEach>
          </table>
        </td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item">是否需要实体卡：</td>
        <td colspan="2" width="88%">
	        <c:if test="${af.map.is_entity eq 1}">是</c:if>
	        <c:if test="${af.map.is_entity eq 0}">否</c:if>
        </td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">有效期开始时间：</td>
        <td  colspan="2" height="24"><fmt:formatDate value="${af.map.start_date}" pattern="yyyy-MM-dd"/></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">优惠卷结束时间：</td>
        <td  colspan="2" height="24"><fmt:formatDate value="${af.map.end_date}" pattern="yyyy-MM-dd"/></td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item">收货地址：</td>
        <td colspan="2" width="88%">${fn:escapeXml(af.map.rel_address)}</td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item">联系人：</td>
        <td colspan="2" width="88%">${fn:escapeXml(af.map.rel_name)}</td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item">联系电话：</td>
        <td colspan="2" width="88%">${fn:escapeXml(af.map.rel_mobile)}</td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">支付凭证：</td>
        <td colspan="2">
         <c:set var="img" value="${ctx}/${af.map.main_pic}@s400x400" />
         <a href="${ctx}/${af.map.main_pic}" target="_blank"><img src="${img}" height="80" id="main_pic_img"/></a>
        </td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item">备注：</td>
        <td colspan="2" width="88%">${fn:escapeXml(af.map.remark)}</td>
      </tr>
      <tr>
       <th colspan="4">审核信息</th>
      </tr>
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item">审核状态：</td>
        <td colspan="2" width="88%">
        	<c:if test="${af.map.audit_state eq 1}">审核通过</c:if>
	        <c:if test="${af.map.audit_state eq 0}">待审核</c:if>
	        <c:if test="${af.map.audit_state eq -1}">审核不通过</c:if>
        </td>
      </tr>
      <tr>
      	<td width="12%" nowrap="nowrap" class="title_item">审核说明：</td>
      	<td colspan="2">${fn:escapeXml(af.map.audit_desc)}</td>
      </tr>
       <tr>
        <td colspan="3" align="center">
            &nbsp;
            <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  <div class="clear"></div>
</div>
</body>
</html>

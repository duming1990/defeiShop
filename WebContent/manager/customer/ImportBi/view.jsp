<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心 - ${app_name}</title>
<meta content="${app_name}会员中心关键字" name="keywords" />
<meta content="${app_name}会员中心介绍" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">编号：</td>
      <td colspan="2">${fn:escapeXml(af.map.index_no)}</td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">数量：</td>
      <td colspan="2">${fn:escapeXml(af.map.sum_count)}</td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">福利金总数：</td>
      <td colspan="2"><fmt:formatNumber pattern="#,##0.00" value="${af.map.bi_sum}"/>&nbsp;元</td>
    </tr>
   
    <tr>
      <td nowrap="nowrap" class="title_item">支付凭证：</td>
      <td colspan="2"><c:if test="${not empty fn:substringBefore(af.map.file_path, '.')}"> <img src="${ctx}/${af.map.file_path}@s400x400" height="100" /> </c:if>
        <c:if test="${empty fn:substringBefore(af.map.file_path, '.')}"> <img src="${ctx}/images/no_img.gif" /> </c:if></td>
    </tr>
    
      <tr>
        <td nowrap="nowrap" class="title_item">添加时间：</td>
        <td  colspan="2" height="24"><fmt:formatDate value="${af.map.add_date}" pattern="yyyy-MM-dd hh:mm:ss" var="_up_date" />
          <c:out value="${_up_date}"/></td>
      </tr>
      
      <tr>
        <td nowrap="nowrap" class="title_item">备注：</td>
        <td  colspan="2" height="24">${af.map.remark}</td>
      </tr>
      
    
    <tr>
        <th colspan="3">明细</th>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">福利金明细：</td>
        <td colspan="2" width="88%"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left" >
            <tr>
              <td align="center" width="5%">序号</td>
              <td align="center" width="33%">手机号码</td>
              <td align="center" width="33%">用户名称</td>
              <td align="center" width="15%">导入名称</td>
              <td align="center" width="33%">福利金金额</td>
            </tr>
            <tbody>
              <c:forEach var="cur" items="${sonList}" varStatus="vs" >
                <tr>
                  <td align="center" id="edit_son" >${vs.count }</td>
                  <td align="center" >${cur.mobile}</td>
                  <td align="center" >${cur.user_name}</td>
                  <td align="center" >${cur.import_user_name}</td>
                  <td align="center" ><fmt:formatNumber pattern="#,##0.00" value="${cur.bi_no}"/></td>
                </tr>
              </c:forEach>
            </tbody>
          </table></td>
      </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">审核状态：</td>
      <td colspan="2">
          <c:if test="${af.map.audit_state eq -1}"> <span style="color:#F00;">
              <c:out value="审核不通过"/>
              </span> </c:if>
            <c:if test="${af.map.audit_state eq 0}">
              <c:out value="待审核"/>
            </c:if>
            <c:if test="${af.map.audit_state eq 1}"> <span style="color:#060;">
              <c:out value="审核通过"/>
              </span> </c:if>
      </td>
    </tr>
        <c:if test="${(not empty af.map.audit_desc)}">
          <tr>
            <td nowrap="nowrap" class="title_item">审核说明：</td>
            <td colspan="2">${fn:escapeXml(af.map.audit_desc)}</td>
          </tr>
        </c:if>
    <tr>
      <td colspan="3" style="text-align:center"><html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
    </tr>
  </table>
  <div class="clear"></div>
</div>
<!-- main end -->
</body>
</html>

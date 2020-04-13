<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
  <html-el:form action="/admin/Link" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="is_audit" styleId="is_audit" value="true"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">新闻基本信息</th>
      </tr>
      <c:if test="${af.map.title_is_strong eq 1}" var="is_strong">
        <tr>
          <td width="12%" nowrap="nowrap" class="title_item">标题：</td>
          <td width="88%"><span style="color:${af.map.title_color}; font-weight:bold;">
            <c:out value="${af.map.title}" />
            </span></td>
        </tr>
      </c:if>
      <c:if test="${not is_strong}">
        <tr>
          <td width="12%" nowrap="nowrap" class="title_item">标题：</td>
          <td width="88%"><span style="color:${af.map.title_color}">
            <c:out value="${af.map.title}" />
            </span></td>
        </tr>
      </c:if>
      <c:if test="${not empty (af.map.image_path)}">
        <tr>
          <td nowrap="nowrap" class="title_item">新闻主图：</td>
          <td><img src="${ctx}/${af.map.image_path}@s400x400" height="100" title="${fn:escapeXml(af.map.image_desc)}" /></td>
        </tr>
      </c:if>
      <tr>
        <td nowrap="nowrap" class="title_item">链接地址：</td>
        <td><c:out value="${af.map.direct_uri}" /></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">排序值：</td>
        <td><c:out value="${af.map.order_value}" /></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">信息状态：</td>
        <td><html-el:select property="info_state" styleId="info_state" >
            <html-el:option value="0">默认（待审核）</html-el:option>
            <html-el:option value="3">已审核（已发布）</html-el:option>
            <html-el:option value="-3">已审核（未发布）</html-el:option>
          </html-el:select></td>
      </tr>
      <c:if test="${isEnabledNewsCustomFields eq 1}">
        <c:if test="${not empty newsInfoCustomFieldContentList}">
          <tr>
            <th colspan="2">自定义字段基本信息</th>
          </tr>
          <c:forEach var="cur" items="${newsInfoCustomFieldContentList}">
            <tr>
              <td nowrap="nowrap" class="title_item">${cur.custom_field_name}：</td>
              <td><c:if test="${cur.type eq 1 || cur.type eq 2}"> ${cur.custom_field_content} </c:if>
                <c:if test="${cur.type eq 3 || cur.type eq 5}">
                  <c:forEach var="cur_son" items="${cur.newsInfoCustomFieldsSonList}">
                    <c:if test="${cur_son.id eq cur.custom_field_content}"> ${cur_son.type_name} </c:if>
                  </c:forEach>
                </c:if>
                <c:if test="${cur.type eq 4}">
                  <c:set var="fieldContent" value=",${cur.custom_field_content}," />
                  <c:forEach items="${cur.newsInfoCustomFieldsSonList}" var="cur_son">
                    <c:set var="fieldsSonId" value=",${cur_son.id}," />
                    <c:if test="${fn:contains(fieldContent, fieldsSonId)}" var="isContains"> ${cur_son.type_name}&nbsp; </c:if>
                  </c:forEach>
                </c:if></td>
            </tr>
          </c:forEach>
        </c:if>
      </c:if>
      <tr>
        <td colspan="2" align="center"><input type="submit" value="审  核" class="bgButton" />
          &nbsp;&nbsp;
          <input type="button" value="返 回" class="bgButton" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>

<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>
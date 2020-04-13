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
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
    <tr>
      <th colspan="${ not empty af.map.newsInfoCustomFieldsSonList ? 3:2 }">自定义字段基本信息</th>
    </tr>
    <tr>
      <td width="12%" nowrap="nowrap" class="title_item">字段名称：</td>
      <td width="88%" colspan="${ not empty af.map.newsInfoCustomFieldsSonList ? 2:1 }"><c:out value="${af.map.title_name}" /></td>
    </tr>
    <tr>
      <td width="12%" nowrap="nowrap" class="title_item">字段类型：</td>
      <td width="${ not empty af.map.newsInfoCustomFieldsSonList ? 34:88 }%" ><c:if test="${af.map.type eq 1}">简单文本框</c:if>
        <c:if test="${af.map.type eq 2}">可编辑文本框</c:if>
        <c:if test="${af.map.type eq 3}">单选</c:if>
        <c:if test="${af.map.type eq 4}">多选</c:if>
        <c:if test="${af.map.type eq 5}">下拉列表框</c:if>
        <c:if test="${ not empty af.map.newsInfoCustomFieldsSonList}">
      <td width="54%"><table width="100%" border="0"  cellpadding="0" cellspacing="0" frame="void">
          <tr>
            <th width="23%">序号</th>
            <th width="43%" >选项名称</th>
            <th width="34%" >排序值</th>
          </tr>
          <c:forEach items="${af.map.newsInfoCustomFieldsSonList}" var="cur" varStatus="vs">
            <tr>
              <td align="center">${vs.index+1}</td>
              <td align="center">${fn:escapeXml(cur.type_name)}</td>
              <td align="center">${cur.order_value }</td>
            </tr>
          </c:forEach>
        </table></td>
      </c:if>
      </td>
    </tr>
    <tr>
      <td width="12%" nowrap="nowrap" class="title_item">是否必填：</td>
      <td width="88%" colspan="${ not empty af.map.newsInfoCustomFieldsSonList ? 2:1 }"><c:if test="${af.map.is_required eq 0}">否</c:if>
        <c:if test="${af.map.is_required eq 1}">是</c:if></td>
    </tr>
    <tr>
        <td nowrap="nowrap" class="title_item">备注：</td>
        <td colspan="${ not empty af.map.newsInfoCustomFieldsSonList ? 2:1 }"><c:out value="${af.map.remark}" /></td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">排序值：</td>
      <td colspan="${ not empty af.map.newsInfoCustomFieldsSonList ? 2:1 }"><c:out value="${af.map.order_value}" /></td>
    </tr>
    <tr>
      <td align="center" colspan="${ not empty af.map.newsInfoCustomFieldsSonList ? 3:2 }"><input type="button" value="返 回" class="bgButton" onclick="history.back();" /></td>
    </tr>
  </table>
</div>

<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>
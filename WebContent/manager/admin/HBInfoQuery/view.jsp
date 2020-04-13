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
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">红包基本信息</th>
      </tr>
      <tr>
        <td width="15%" class="title_item">标题：</td>
        <td width="85%">${fn:escapeXml(af.map.title)}</td>
      </tr>
      <tr>
        <td class="title_item">红包大类：</td>
        <td>
        	<c:forEach items="${hbClass}" var="keys">
        		 <c:if test="${af.map.hb_class eq  keys.index}">${keys.name}</c:if>
            </c:forEach>
		</td>
      </tr>
      <tr>
        <td class="title_item">红包类型：</td>
        <td>
        <c:forEach items="${hbTypes}" var="keys">
        		 <c:if test="${af.map.hb_type eq  keys.index}">${keys.name}</c:if>
        </c:forEach>
		</td>
      </tr>
      <tr>
        <td width="15%" class="title_item">分享人获得额度：</td>
        <td width="85%">${fn:escapeXml(af.map.share_user_money)}元</td>
      </tr>
      <tr>
        <td width="15%" class="title_item">定额红包额度：</td>
        <td width="85%">${fn:escapeXml(af.map.hb_money)}元</td>
      </tr>
      <tr>
        <td width="15%" class="title_item">最小红包额度：</td>
        <td width="85%">${fn:escapeXml(af.map.min_money)}元</td>
      </tr>
      <tr>
        <td width="15%" class="title_item">最大红包额度：</td>
        <td width="85%">${fn:escapeXml(af.map.max_money)}元</td>
      </tr>
      <tr>
        <td width="15%" class="title_item">红包总量：</td>
        <td width="85%">${fn:escapeXml(af.map.hb_max_count)}个</td>
      </tr>
      <tr>
        <td width="15%" class="title_item">已领取红包量：</td>
        <td width="85%">${fn:escapeXml(af.map.hb_rec_count)}个</td>
      </tr>
      <tr>
        <td class="title_item">是否领完：</td>
        <td>
        <c:choose>
			<c:when test="${af.map.is_closed eq 0}">未领完</c:when>
			<c:when test="${af.map.is_closed eq 1}">已领完</c:when>
			</c:choose></td>
      </tr>
      <tr>
        <td class="title_item">是否删除：</td>
        <td>
        <c:choose>
			<c:when test="${af.map.is_del eq 0}">未删除</c:when>
			<c:when test="${af.map.is_del eq 1}">已删除</c:when>
			</c:choose></td>
      </tr>
      <tr>
        <td class="title_item">备注：</td>
        <td>${af.map.remark}</td>
      </tr>
      <tr>
        <td class="title_item">添加时间：</td>
        <td><fmt:formatDate value="${af.map.add_date}" pattern="yyyy-M-d" /></td>
      </tr>
      <tr>
        <td colspan="2" style="text-align:center">
          <input type="button" class="bgButton" value="返 回" onclick="history.back();" /></td>
      </tr>
    </table>
</div>
<script type="text/javascript"><!--//<![CDATA[
$(document).ready(function(){

});

//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>

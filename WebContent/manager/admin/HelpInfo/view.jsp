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
<div class="mainbox mine">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <br />
  <html-el:form action="/admin/HelpInfo">
    <table  width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">帮助中心文档信息</th>
      </tr>
      <tr>
        <td class="title_item">标题：</td>
        <td><span style="color:#${af.map.title_color}">${af.map.title}</span></td>
      </tr>
      <tr>
        <td width="15%" class="title_item">关键字：</td>
        <td>${af.map.key_word}</td>
      </tr>
      <tr>
        <td class="title_item">内容：</td>
        <td>${af.map.content}</td>
      </tr>
      <c:if test="${not empty af.map.pub_date}">
        <tr>
          <td class="title_item">发布时间：</td>
          <td><fmt:formatDate value="${af.map.pub_date}" pattern="yyyy-MM-dd" /></td>
        </tr>
      </c:if>
      <c:if test="${not empty af.map.modify_date}">
        <tr>
          <td class="title_item">最后修改时间：</td>
          <td><fmt:formatDate value="${af.map.modify_date}" pattern="yyyy-MM-dd" /></td>
        </tr>
      </c:if>
      <tr>
        <td class="title_item">排序值：</td>
        <td>${af.map.order_value}</td>
      </tr>
      <tr>
        <td class="title_item">信息状态：</td>
        <td><c:choose>
            <c:when test="${af.map.is_del eq 0}">未删除</c:when>
            <c:when test="${af.map.is_del eq 1}">已删除</c:when>
          </c:choose></td>
      </tr>
      <tr>
        <td colspan="2" align="center"><input type="button" name="return" value=" 返回 " class="bgButton" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>
 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("input[type='text'][readonly],textarea[readonly]").css("color","#999");
	$("input[type='text'],input[type='password']").css("border","1px solid #ccc");
    $("input[type='text'],textarea").not("[readonly]").focus(function(){
    	$(this).addClass("row_focus");
    }).blur(function(){
    	$(this).removeClass("row_focus");
    });
	$("textarea").each(function(){
		this.onpropertychange = function () {
			if (this.scrollHeight > 16) this.style.posHeight = this.scrollHeight;
		};
	});	

});





//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>
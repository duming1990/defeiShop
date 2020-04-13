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
<div class="mainbox mine">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/HelpInfo">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="h_mod_id" />
    <table width="100%" border="0" cellpadding="1" class="tableClassSearch">
      <tr>
        <td width="50%" nowrap="nowrap"> 标题：
          <html-el:text property="title" maxlength="40" style="width:120px;" />
          &nbsp;关键字：
          <html-el:text property="key_word" maxlength="40" style="width:120px;" />
          &nbsp;信息状态：
          <html-el:select property="is_del">
            <html-el:option value="">全部</html-el:option>
            <html-el:option value="0">未删除</html-el:option>
            <html-el:option value="1">已删除</html-el:option>
          </html-el:select>
          &nbsp;
          <html-el:submit value="快速搜索" styleClass="bgButton" /></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <html-el:form action="/admin/HelpInfo?method=delete">
    <div style="text-align: left;padding: 5px;">
      <input type="button" name="delete"  class="bgButton"  id="delete" value="删除所选" onclick="confirmDeleteAll(this.form);" />
      <input type="button" name="add" class="bgButton"  value="添加文档" onclick="location.href='HelpInfo.do?method=add&h_mod_id=${af.map.h_mod_id}';" />
      <html-el:hidden property="h_mod_id" value="${af.map.h_mod_id}" />
      <html-el:hidden property="method" value="delete" />
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%" nowrap="nowrap"><input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /></th>
        <th nowrap="nowrap">标题</th>
        <th width="6%" align="center" nowrap="nowrap">浏览次数</th>
        <th width="6%" align="center" nowrap="nowrap">排序值</th>
        <th width="7%" nowrap="nowrap">状态</th>
        <th width="8%" nowrap="nowrap">发布人</th>
        <th width="8%" nowrap="nowrap">发布时间</th>
        <th width="5%" align="center">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center"><input name="pks" type="checkbox" id="pks" value="${cur.id}" /></td>
          <td align="left"><a style="text-decoration:none;" href="HelpInfo.do?method=view&id=${cur.id}&h_mod_id=${af.map.h_mod_id}">${fn:escapeXml(cur.title)}</a></td>
          <td align="center">${cur.view_count}</td>
          <td align="center">${cur.order_value}</td>
          <td align="center"><c:choose>
              <c:when test="${cur.is_del eq 0}">未删除</c:when>
              <c:when test="${cur.is_del eq 1}"><span style="color:#FF0000;">已删除</span></c:when>
            </c:choose></td>
          <td align="center" nowrap="nowrap">${cur.pub_user_name}</td>
          <td align="center" nowrap="nowrap"><fmt:formatDate value="${cur.pub_date}" pattern="yyyy-MM-dd" /></td>
          <td align="center" nowrap="nowrap">
             <a class="butbase" onclick="confirmUpdate('null', 'HelpInfo.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><span class="icon-edit">修改</span></a>
             <a class="butbase" onclick="confirmDelete(null, 'HelpInfo.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><span class="icon-remove">删除</span></a>
          </td>
        </tr>
      </c:forEach>
    </table>
  </html-el:form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="../admin/HelpInfo.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js"></script> 
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
			pager.addHiddenInputs("h_mod_id", "${af.map.h_mod_id}");
            pager.addHiddenInputs("key_word", "${fn:escapeXml(af.map.key_word)}");
			pager.addHiddenInputs("info_state", "${af.map.info_state}");			
			pager.addHiddenInputs("title", "${fn:escapeXml(af.map.title)}");
            document.write(pager.toString());
        </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
 
<script type="text/javascript" src="${ctx}/scripts/autocomplete/autocomplete.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
});
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>

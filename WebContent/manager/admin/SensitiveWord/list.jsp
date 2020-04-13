<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
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
  <html-el:form action="/admin/SensitiveWord">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="6%" nowrap="nowrap">敏感词：
                <html-el:text property="content_like" styleId="content_like" style="width:120px;" styleClass="webinput"/>
                &nbsp;&nbsp;
                <html-el:submit value="查 询" styleClass="bgButton" /></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="SensitiveWord.do?method=delete">
    <div style="text-align: left; padding: 5px;">
      <logic-el:match name="popedom" value="+4+">
        <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" />
      </logic-el:match>
      <logic-el:match name="popedom" value="+1+">
        <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='SensitiveWord.do?method=add&mod_id=${af.map.mod_id}';" />
      </logic-el:match>
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="7%" nowrap="nowrap"> <c:if test="${fn:contains(popedom, '+4+')}" var="isDelete">
            <input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" />
          </c:if>
          <c:if test="${not isDelete}"> 序号 </c:if>
        </th>
        <th nowrap="nowrap">敏感词</th>
        <c:if test="${fn:contains(popedom, '+2+') or fn:contains(popedom, '+4+')}" var="isContains">
          <th width="10%" nowrap="nowrap">操作</th>
        </c:if>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center" nowrap="nowrap"><c:if test="${fn:contains(popedom, '+4+')}" var="isDelete">
               <input name="pks" type="checkbox" id="pks" value="${cur.id}" />
               </c:if>
            <c:if test="${not isDelete}">${vs.count } </c:if></td>
          <td align="left" nowrap="nowrap"><a title="查看" href="SensitiveWord.do?method=view&amp;mod_id=${af.map.mod_id}&amp;id=${cur.id}">${fn:escapeXml(cur.content )}</a></td>
          <td align="center" nowrap="nowrap">
            <%-- <logic-el:match name="popedom" value="+2+">
            	<a class="butbase" onclick="confirmUpdate(null, 'SensitiveWord.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><span class="icon-edit">修改</span></a>
            </logic-el:match> --%>
            <logic-el:match name="popedom" value="+4+">
            	<a class="butbase" onclick="confirmDelete(null, 'SensitiveWord.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><span class="icon-remove">删除</span></a></span>
            </logic-el:match></td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="SensitiveWord.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
        <tr>
          <td height="10" ><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("content_like", "${fn:escapeXml(af.map.content_like)}");
            document.write(pager.toString());
        </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>

<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
</body>
<jsp:include page="../public_page.jsp" flush="true" />
</html>

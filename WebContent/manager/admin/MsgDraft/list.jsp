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
  <html-el:form action="/admin/MsgDraft">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="6%" nowrap="nowrap"> 主题：
                <html-el:text property="msg_title_like" styleClass="webinput"/>
                &nbsp;&nbsp;发送时间 从：
                <html-el:text property="st_pub_date" styleClass="webinput" size="10" maxlength="10" readonly="true" onclick="WdatePicker();" />
                至：
                <html-el:text property="en_pub_date" styleClass="webinput" size="10" maxlength="10" readonly="true" onclick="WdatePicker();" />
                &nbsp;&nbsp;
                <html-el:submit value="查 询" styleClass="bgButton" /></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="MsgDraft.do?method=delete&mod_id=${af.map.mod_id}">
    <div style="text-align: left; padding: 5px;">
      <logic-el:match name="popedom" value="+4+">
        <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" />
      </logic-el:match>
      <logic-el:match name="popedom" value="+1+">
        <input type="button" name="add" id="add" class="bgButton" value="写信" onclick="location.href='MsgDraft.do?method=add&mod_id=${af.map.mod_id}';" />
      </logic-el:match>
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="7%" nowrap="nowrap"> <c:if test="${fn:contains(popedom, '+4+')}" var="isDelete">
            <input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" />
          </c:if>
          <c:if test="${not isDelete}"> 序号 </c:if>
        </th>
        <th width="15%" nowrap="nowrap">接收人</th>
        <th nowrap="nowrap">主题</th>
        <th width="15%" nowrap="nowrap">发送时间</th>
        <th width="8%" nowrap="nowrap">信息状态</th>
        <c:if test="${fn:contains(popedom, '+2+') or fn:contains(popedom, '+4+')}" var="isContains">
          <th width="10%" nowrap="nowrap">操作</th>
        </c:if>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center" nowrap="nowrap"><c:if test="${fn:contains(popedom, '+4+')}" var="isDelete">
              <c:set var="isDel" value="${cur.is_closed eq 1}"></c:set>
              <c:if test="${isDel}">
                <input name="pks" type="checkbox" id="pks" value="${cur.id}" disabled="disabled"/>
              </c:if>
              <c:if test="${not isDel}">
                <input name="pks" type="checkbox" id="pks" value="${cur.id}" />
              </c:if>
            </c:if>
            <c:if test="${not isDelete}"> ${vs.count } </c:if></td>
          <td align="left"><c:forEach var="x" items="${cur.userInfoList}">${x.user_name}&nbsp;</c:forEach></td>
          <td align="left"><a href="MsgDraft.do?method=edit&amp;mod_id=${af.map.mod_id}&amp;id=${cur.id}">${fn:escapeXml(cur.msg_title)}</a></td>
          <td align="center"><fmt:formatDate value="${cur.send_time}" pattern="yyyy-MM-dd HH:mm" /></td>
          <td align="center"><c:choose>
              <c:when test="${cur.info_state eq 0}"> 草稿 </c:when>
              <c:when test="${cur.info_state eq 1}"> 已发送 </c:when>
              <c:otherwise> 已删除 </c:otherwise>
            </c:choose></td>
          <c:if test="${isContains}" >
            <td align="center" nowrap="nowrap"><logic-el:match name="popedom" value="+4+">
                <c:if test="${isDel}"> <a class="butbase but-disabled" href="javascript:void(0);"><span title="已删除或已锁定，不能删除" class="icon-remove">删除</span></a> </c:if>
                <c:if test="${not isDel}"> <a class="butbase" href="javascript:void(0);"><span class="icon-remove" onclick="confirmDelete(null, 'MsgDraft.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())">删除</span></a> </c:if>
              </logic-el:match></td>
          </c:if>
        </tr>
      </c:forEach>
    </table>
  </form>
  <form id="bottomPageForm" name="bottomPageForm" method="post" action="MsgDraft.do">
    <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="40" align="center"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
          <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("msg_title_like", "${fn:escapeXml(af.map.msg_title_like)}");
            pager.addHiddenInputs("st_pub_date", "${af.map.st_pub_date}");
            pager.addHiddenInputs("en_pub_date", "${af.map.en_pub_date}");
            pager.addHiddenInputs("info_state", "${af.map.info_state}");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            document.write(pager.toString());
            </script></td>
      </tr>
    </table>
  </form>
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>

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
  <html-el:form action="/admin/MsgReceiver">
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
  <form id="listForm" name="listForm" method="post" action="MsgReceiver.do?method=delete&mod_id=${af.map.mod_id}">
    <div style="text-align: left; padding: 5px;">
      <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" />
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="7%" nowrap="nowrap"> <input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" />
        </th>
        <th nowrap="nowrap">主题</th>
        <th width="15%" nowrap="nowrap">发送人</th>
        <th width="15%" nowrap="nowrap">发送时间</th>
        <th width="8%" nowrap="nowrap">是否已读</th>
        <th width="10%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center" nowrap="nowrap"><c:if test="${fn:contains(popedom, '+4+')}" var="isDelete">
              <input name="pks" type="checkbox" id="pks" value="${cur.id}" />
            </c:if>
            <c:if test="${not isDelete}"> ${vs.count } </c:if></td>
          <td align="left"><a href="MsgReceiver.do?method=view&amp;mod_id=${af.map.mod_id}&amp;msg_rec_id=${cur.id}&amp;msg_id=${cur.msg_id}&amp;send_user_id=${cur.user_id}">${fn:escapeXml(cur.msg_title)}</a></td>
          <td align="left">${cur.user_name}
            <input type="hidden" name="user_name" id="user_name" value="${cur.user_name}" /></td>
          <td align="center"><fmt:formatDate value="${cur.send_time}" pattern="yyyy-MM-dd HH:mm" /></td>
          <td align="center"><c:choose>
              <c:when test="${cur.is_read eq 0 }">未读</c:when>
              <c:otherwise>已读</c:otherwise>
            </c:choose></td>
          <td align="center" nowrap="nowrap"><c:if test="${cur.is_send_all eq 1}" var="isall">群发，不能删除</c:if>
            <c:if test="${not isall}"> <a class="butbase" href="javascript:void(0);"><span class="icon-remove" onclick="confirmDelete(null, 'MsgReceiver.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())">删除</span></a> </c:if></td>
        </tr>
        <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
  </c:forEach>
  <c:forEach begin="${i}" end="${af.map.pager.pageSize - 1}">
        <tr align="center">
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <form id="bottomPageForm" name="bottomPageForm" method="post" action="MsgReceiver.do">
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

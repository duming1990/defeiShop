<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<c:set var="type_name" value="商家"/>
<body>
<div style="width: 99%" class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/SendMessage">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td>标题：
          <html-el:text property="title" maxlength="40" style="width:150px;" styleClass="webinput" />
          &nbsp;是否删除：
          <html-el:select property="is_del" styleClass="webinput" >
            <html-el:option value="">全部</html-el:option>
            <html-el:option value="0">未删除</html-el:option>
            <html-el:option value="1">已删除</html-el:option>
          </html-el:select>
          &nbsp;发送时间 从:
          <html-el:text property="st_date" styleId="st_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
          至：
          <html-el:text property="en_date" styleId="en_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
          &nbsp;
          &nbsp;
          <html-el:submit value="查 询" styleClass="bgButton"  /></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="SendMessage.do?method=delete">
  <div style="text-align: left; padding: 5px;">
    <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" />
    <input type="button" name="add" id="add" class="bgButton" value="写信" onclick="location.href='SendMessage.do?method=add&mod_id=${af.map.mod_id}';" />
  </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%"> <input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" />
        </th>
        <th width="10%">发件人</th>
        <th width="30%">标题</th>
        <!--         <th>内容</th> -->
       <!--  <th width="10%">状态</th> -->
        <th width="12%">发送时间</th>
        <th width="16%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}">
        <tr>
          <td align="center"><input name="pks" type="checkbox" id="pks" value="${cur.id}" /></td>
          <td align="center">${fn:escapeXml(cur.map.add_name)}</td>
          <td align="center">${fn:escapeXml(cur.title)}</td>
          <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
          <td align="center"><span style="cursor: pointer;" onclick="doNeedMethod(null, 'SendMessage.do', 'view','mod_id=${af.map.mod_id}&id=${cur.id}')"> 查看 </span></td>
        </tr>
         <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
  </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="SendMessage.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
			pager.addHiddenInputs("msg_title_like", "${fn:escapeXml(af.map.msg_title_like)}");
			pager.addHiddenInputs("info_state", "${fn:escapeXml(af.map.info_state)}");
			pager.addHiddenInputs("st_date", "${af.map.st_date}");
			pager.addHiddenInputs("en_date", "${af.map.en_date}");
            document.write(pager.toString());
            </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
 
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
});
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>

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
  <html-el:form action="/admin/Msg">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td>${type_name}名：
              <html-el:text property="real_name_like" maxlength="40" style="width:150px;" styleClass="webinput" />
             	&nbsp;留言状态：
              <html-el:select property="audit_state" styleClass="webinput" >
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="0">未发送</html-el:option>
                  <html-el:option value="1">已发送</html-el:option>
                  <html-el:option value="2">已删除</html-el:option>
                </html-el:select>
              	&nbsp;添加时间：
              <html-el:text property="send_time_like" styleClass="webinput"  styleId="send_time" size="9" maxlength="9" readonly="true" onclick="WdatePicker();" style="width:80px;"/>
             	&nbsp;<html-el:submit value="查 询" styleClass="bgButton"  />
               </td>
            </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="Msg.do?method=delete">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%"> <input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" />
        </th>
        <th width="12%">申请区域</th>
        <th width="12%">标题</th>
        <th>内容</th>
        <th width="10%">申请人</th>
        <th width="10%">申请时间</th>
        <th width="10%">申请人手机</th>
         <th width="8%">是否回复</th>
        <th width="10%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}">
        <tr>
        <td align="center"><input name="pks" type="checkbox" id="pks" value="${cur.id}" /></td>
		  <td align="center">${fn:escapeXml(cur.p_name)}</td>
          <td align="center">${fn:escapeXml(cur.msg_title)}</td>     
          <td align="center">${fn:escapeXml(cur.msg_content)}</td>     
          <td align="center">${fn:escapeXml(cur.user_name)}</td>     
          <td align="center"><fmt:formatDate value="${cur.send_time}" pattern="yyyy-MM-dd" /></td>     
		  <td align="center">${fn:escapeXml(cur.map.mobile)}</td>
        <td align="center"><c:choose>
            <c:when test="${cur.info_state eq 0}"><span class="label label-danger">未回复</span></c:when>
            <c:when test="${cur.info_state eq 1}"><span class="label label-success">已回复</span></c:when>
          </c:choose>
        </td>
          <td align="center">
			<span style="cursor: pointer;" onclick="doNeedMethod(null, 'Msg.do', 'reply','mod_id=${af.map.mod_id}&id=${cur.id}')">
          	 	<img src="${ctx}/styles/images/huifu.gif" width="55" height="18" />
          	 </span>
		  </td>     
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
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
  <form id="bottomPageForm" name="bottomPageForm" method="post" action="Msg.do">
    <table width="98%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
          <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
			pager.addHiddenInputs("send_time_like", "${fn:escapeXml(af.map.send_time_like)}");
			pager.addHiddenInputs("real_name_like", "${fn:escapeXml(af.map.real_name_like)}");
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

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/scripts/colorbox/style4/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div align="center" class="divContent">
<c:if test="${empty flag}">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/SysOperLog">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="60%" nowrap="nowrap">
              	操作类型：
                <html-el:select property="oper_type">
                  <html-el:option value="">全部</html-el:option>
                  <c:forEach var="cur" items="${sysOperTypeList}">
                   <html-el:option value="${cur.index}">${cur.name}</html-el:option>
                  </c:forEach>
                </html-el:select>
                &nbsp;操作时间：<html-el:text property="s_date" styleId="s_date" size="9" maxlength="9" readonly="true" onclick="WdatePicker();" styleClass="webinput"/>&nbsp;至&nbsp;<html-el:text property="e_date" styleId="e_date" size="9" maxlength="9" readonly="true" onclick="WdatePicker();" styleClass="webinput" />
                &nbsp;操作用户名：
                <html-el:text property="oper_uname" styleId="oper_uname" style="width:150px;" styleClass="webinput"/>
                &nbsp;操作者IP：
                <html-el:text property="ip" styleId="ip" style="width:150px;" styleClass="webinput"/>
                &nbsp;
                <html-el:submit value="查 询" styleClass="bgButton" /></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
   </c:if>
  <form id="listForm" name="listForm" method="post" action="SysOperLog.do?method=delete">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="10%" nowrap="nowrap">操作类型</th>
        <th width="10%" nowrap="nowrap">操作时间</th>
        <th width="5%" nowrap="nowrap">操作人ID</th>
        <th width="12%" nowrap="nowrap">操作人用户名</th>
        <th width="13%" nowrap="nowrap">操作人IP</th>
        <th nowrap="nowrap">操作备注</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          <td>
     		${cur.oper_name}	
          </td>
          <td align="center"><fmt:formatDate value="${cur.oper_time}"  pattern="yyyy-MM-dd"></fmt:formatDate></td>
          <td align="center">${cur.oper_uid}</td>
          <td align="center">${cur.oper_uname}</td>
          <td align="center">${cur.oper_uip} &nbsp;&nbsp;<a href="http://ip.taobao.com/ipSearch.php?ipAddr=${cur.oper_uip}"  style="text-decoration:none" class="look_ip">查看</a></td>
          <td align="left" style="word-break:break-all"><c:out value="${cur.oper_memo}"></c:out></td>
        </tr>
         <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
      </c:forEach>
       <c:forEach begin="${i}" end="${af.map.pager.pageSize - 1}">
        <tr align="center">
          <td>&nbsp;</td>
          <td nowrap="nowrap">&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <c:if test="${empty flag}">
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="SysOperLog.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "list");
					pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
					pager.addHiddenInputs("s_date", "${af.map.s_date}");
					pager.addHiddenInputs("e_date", "${af.map.e_date}");
					pager.addHiddenInputs("oper_type", "${af.map.oper_type}");
					pager.addHiddenInputs("oper_uname", "${af.map.oper_uname}");
					pager.addHiddenInputs("ip", "${af.map.ip}");
					document.write(pager.toString());
	            	</script></td>
        </tr>
      </table>
    </form>
  </div>
  </c:if>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
<script type="text/javascript">//<![CDATA[
$("a.look_ip").colorbox({width:"80%", height:"100%", iframe:true});
//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>

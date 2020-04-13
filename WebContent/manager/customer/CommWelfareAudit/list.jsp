<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心 - ${app_name}</title>
<meta content="${app_name}会员中心关键字" name="keywords" />
<meta content="${app_name}会员中心介绍" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body style="height:2500px;">
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/CommWelfareAudit.do" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td>
          <div style="margin-top: 5px;">&nbsp;申请人：
            <html-el:text property="entp_name_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
           &nbsp;&nbsp;
            <button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button>
          </div></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="">
    
    
  <table width="" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr class="tite2">
        <th width="20%">申请人</th>
        <th width="10%">申请人身份</th>
        <th width="10%">申请时间</th>
        <th width="10%">审核状态</th>
        <th width="10%">审核说明</th>
        <th width="10%">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          <td>${fn:escapeXml(cur.entp_name)}</td>
          <td align="center">
	          <c:choose>
		          <c:when test="${cur.type eq 1}">商家</c:when>
		          <c:when test="${cur.type eq 2}">代理</c:when>
	          </c:choose>
          </td>
          <td><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
           <td align="center">
	          <c:choose>
		          <c:when test="${cur.audit_state eq 0}" ><span class="label label-default">未审核</span></c:when>
		          <c:when test="${cur.audit_state eq 1}"><span class="label label-success">审核通过</span></c:when>
		          <c:when test="${cur.audit_state eq -1}"><span class="label label-danger label-block">审核不通过</span></c:when>
	          </c:choose>
          </td>
          <td>${fn:escapeXml(cur.audit_desc)}</td>
          <td>
           <a class="label label-info label-block" href="../customer/CommWelfareAudit.do?id=${cur.id}&method=view">审核</a>
          </td>
        </tr>
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
  <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="CommWelfareAudit.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "list");
					pager.addHiddenInputs("entp_name_like", "${fn:escapeXml(af.map.entp_name_like)}");
					document.write(pager.toString());
	            	</script>
	            	</td>
        </tr>
      </table>
    </form>
  </div>
  <div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript">//<![CDATA[

//]]></script>
</body>
</html>

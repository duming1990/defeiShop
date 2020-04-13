<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/commons/styles/nav.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/public.js"></script>
<div align="center" class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/OrderAudit"  styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
               <td nowrap="nowrap">下单时间 从：
                <html-el:text property="st_add_date" styleId="st_add_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker()" />
               	 至：
                <html-el:text property="en_add_date" styleId="en_add_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker()" />
               	  &nbsp;${app_name_min}订单号：
                  <html-el:text property="trade_index_like" maxlength="25" style="width:140px;" styleClass="webinput" />
                  <input type="submit" class="bgButton" value="查 询" />
               </td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%" align="center"><strong>序号</strong></th>
        <th nowrap="nowrap" align="center"><strong>订单号</strong></th>
        <th width="12%" align="center"><strong>派送商家</strong></th>
        <th width="12%" align="center"><strong>收货人</strong></th>
        <th width="8%" align="center"><strong>下单时间</strong></th>
        <th width="8%" align="center"><strong>订单金额</strong></th>
        <th width="8%" align="center"><strong>交易凭证</strong></th>
        <th width="8%">审核时间</th>
        <th width="8%" align="center"><strong>审核状态</strong></th>
        <th width="8%" align="center"><strong>审核说明</strong></th>
        <th width="12%" align="center"><strong>操作</strong></th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center">${vs.count}</td>
          <td align="center">${cur.trade_index}</td>
          <td align="center"><a title="查看" href="EntpInfoAudit.do?method=view&mod_id=1003001000&id=${cur.entp_id}">${cur.entp_name}</a></td>
          <td align="center"><a title="查看" href="UserInfo.do?method=view&amp;mod_id=${af.map.mod_id}&amp;id=${cur.add_user_id}">${cur.add_user_name}</a></td>
          <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd" /></td>
          <td align="center"><fmt:formatNumber value="${cur.order_money}" pattern="0.00"/></td>
          <td align="center">
          <c:set var="img" value="${ctx}/${cur.main_pic}@s400x400" />
           <a href="${ctx}/${cur.main_pic}" title="交易凭证" class="viewImgMain"> <img src="${img}" height="50" /> </a>
          </td>
          <td align="center"><fmt:formatDate value="${cur.audit_date}" pattern="yyyy-MM-dd" /></td>
          <td align="center"><c:choose>
              <c:when test="${cur.audit_state eq -1}"><span style=" color:#F00;">审核不通过</span></c:when>
              <c:when test="${cur.audit_state eq 0}"><span>待审核</span></c:when>
              <c:when test="${cur.audit_state eq 1}"><span style=" color:#060;">审核通过</span></c:when>
            </c:choose>
          </td>
          <td align="center">${cur.audit_desc}</td>
          <td align="center">
           <c:if test="${cur.audit_state eq 0}">
            <a class="butbase"><span onclick="orderAudit('${cur.id}');" class="icon-ok">审核</span></a>
           </c:if>
           <c:if test="${cur.audit_state ne 0}">
            <a class="butbase but-disabled" title="已经进行过审核操作了，不能再进行审核！"><span class="icon-ok">审核</span></a>
           </c:if>
          </td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="${ctx}/manager/admin/OrderAudit.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
				var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
				pager.addHiddenInputs("method", "list");
				pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
				pager.addHiddenInputs("st_add_date", "${af.map.st_add_date}");
				pager.addHiddenInputs("en_add_date", "${af.map.en_add_date}");
				pager.addHiddenInputs("trade_index_like", "${af.map.trade_index_like}");
		        document.write(pager.toString());
            	</script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<div class="clear"></div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];

$(document).ready(function(){
	$("a.viewImgMain").colorbox({transition:"none", width:"85%"});
});

function orderAudit(id){
	$.dialog({
		title:  "订单审核",
		width:  800,
		height: 600,
        lock:true ,
		content:"url:${ctx}/manager/admin/OrderAudit.do?method=orderAudit&id=" +id + "&" + $('#bottomPageForm').serialize()
	});
}
function windowReload(){
	window.location.reload();
}
//]]>
</script>
</body>
</html>

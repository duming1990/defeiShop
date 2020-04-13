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
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
</head>
<body style="height:2500px;">
<div class="mainbox mine">
  <html-el:form action="/admin/ImportBi" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="comm_type" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td>
        &nbsp;运行中心名称：
                <html-el:text property="servicecenter_name_like" styleClass="webinput" />
         &nbsp;时间从：
                <html-el:text property="st_add_date" styleId="st_add_date" styleClass="webinput" size="9" maxlength="9" readonly="true" onclick="WdatePicker();" />
                - <html-el:text property="en_add_date" styleId="en_add_date" styleClass="webinput" size="9" maxlength="9" readonly="true" onclick="WdatePicker();" />
                
            &nbsp;&nbsp;
            <button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button>
           &nbsp;
          </div></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="ImportBi.do?method=delete">
    <div style="text-align: left;padding:5px;">
      <button class="bgButtonFontAwesome" type="button" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);"><i class="fa fa-minus-square"></i>删除所选</button>
<%--       <button class="bgButtonFontAwesome" type="button" onclick="location.href='ImportBi.do?method=add&par_id=${af.map.par_id}&mod_id=${af.map.mod_id}&comm_type=${af.map.comm_type}';" ><i class="fa fa-plus-square"></i>添加</button> --%>
<!--     <input id="import" type="button" value="导入" class="bgButton" /> -->
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr class="tite2">
        <th width="5%"><input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /></th>
        <th nowrap="nowrap">编号</th>
        <th nowrap="nowrap">运营中心</th>
        <th width="8%">数量</th>
        <th width="6%">总额</th>
        <th width="6%">凭证</th>
        <th width="6%">添加时间</th>
        <th width="8%">审核状态</th>
        <th width="6%">审核时间</th>
        <th width="10%">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          <td>
          <c:if test="${cur.is_del eq 1}">
              <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}" disabled="disabled" />
            </c:if>
            <c:if test="${cur.is_del eq 0}">
              <c:if test="${cur.audit_state eq 0}">
                <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}" />
              </c:if>
              <c:if test="${cur.audit_state ne 0}">
                <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}" disabled="disabled" />
              </c:if>
            </c:if></td>
          <td >
            <c:url var="url" value="/manager/admin/ImportBi.do?method=view&amp;id=${cur.id}&amp;mod_id=${af.map.mod_id}&amp" />
            <a href="${url}" title="${fn:escapeXml(cur.index_no)}">
            <c:out value="${fnx:abbreviate(cur.index_no, 60, '...')}" />
            </a>
          </td>
          <td>${fn:escapeXml(cur.map.servicecenter_name)}</td>
          <td>${fn:escapeXml(cur.sum_count)}</td>
          <td><fmt:formatNumber pattern="#,##0.00" value="${cur.bi_sum}"/></td>
          <td><a target="_blank" href="${ctx}/${cur.file_path}"><img src="${ctx}/${cur.file_path}" width="100%" /></a></td>
          <td><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
          <td>
          	<c:choose>
              <c:when test="${cur.audit_state eq -1}">
              <span class="label label-danger label-block">审核不通过</span>
              <c:if test="${(not empty cur.audit_desc)}">
	            <a title="${fn:escapeXml(cur.audit_desc)}" class="label label-warning label-block" onclick="viewAuditDesc('${cur.audit_desc}');">
	            <i class="fa fa-info-circle"></i>查看原因</a> 
	          </c:if>
              </c:when>
              <c:when test="${cur.audit_state eq 0}"><span class="label label-default">待审核</span></c:when>
              <c:when test="${cur.audit_state eq 1}"><span class="label label-success">审核通过</span></c:when>
            </c:choose>
          </td>
          <td><fmt:formatDate value="${cur.audit_date}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
          <td>
          
          <c:if test="${cur.audit_state ne 1 }">
	          <logic-el:match name="popedom" value="+8+"> 
	            <a class="butbase" onclick="doNeedMethod(null, 'ImportBi.do', 'audit','id=${cur.id}&mod_id=${af.map.mod_id}&'+$('#bottomPageForm').serialize())"><span class="icon-ok">审核</span></a><br/>
	          </logic-el:match>
          </c:if>
          
              
          <c:url var="url" value="/manager/admin/ImportBi.do?method=view&amp;id=${cur.id}&amp;mod_id=${af.map.mod_id}" />
              <a class="label label-info label-block" href="${url}"><span id="${cur.id}">查看</span></a>
              <c:if test="${cur.audit_state ne 1}"><a class="label label-danger label-block" id="remove" onclick="confirmDelete(null, 'ImportBi.do', 'id=${cur.id}&mod_id=${af.map.mod_id}&' + $('#bottomPageForm').serialize())"><span id="${cur.id}">删除</span></a></c:if>
          </td>
        </tr>
        <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
      </c:forEach>
      
    </table>
  </form>
  <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="ImportBi.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "list");
					pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
					pager.addHiddenInputs("st_add_date", "${af.map.st_add_date}");
			        pager.addHiddenInputs("en_add_date", "${af.map.en_add_date}");
			        pager.addHiddenInputs("servicecenter_name_like", "${af.map.servicecenter_name_like}");
					document.write(pager.toString());
	            	</script></td>
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
$(document).ready(function(){
	var f = document.forms[0];
	
	$("#import").click(function(){
		location.href = "${ctx}/manager/admin/ImportBi.do?method=importExcel";
	});
	
});

function viewAuditDesc(audit_desc){
	$.dialog({
		title:  "审核不通过原因",
		width:  250,
		height: 100,
        lock:true ,
		content:audit_desc
	});
}



//]]></script>
</body>
</html>

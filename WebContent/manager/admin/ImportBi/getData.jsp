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
    <html-el:hidden property="method" value="getData" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="comm_type" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td>
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
<!--       <button class="bgButtonFontAwesome" type="button" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);"><i class="fa fa-minus-square"></i>删除所选</button> -->
<%--       <button class="bgButtonFontAwesome" type="button" onclick="location.href='ImportBi.do?method=add&par_id=${af.map.par_id}&mod_id=${af.map.mod_id}&comm_type=${af.map.comm_type}';" ><i class="fa fa-plus-square"></i>添加</button> --%>
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr class="tite2">
        <th width="3%">序号</th>
        <th width="8%">用户名</th>
        <th width="8%">导入名称</th>
        <th width="8%">手机号码</th>
        <th width="8%">所属运营中心</th>
        <th width="8%">所属区域</th>
        <th width="6%">余额</th>
        <th width="6%">当前时段充值福利金</th>
        <th width="6%">总充值福利金</th>
        <th width="6%">剩余福利金</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          
          <td>${vs.count}</td>
          <td>${cur.user_name}</td>
          <td>${cur.import_user_name}</td>
          <td>${fn:escapeXml(cur.mobile)}</td>
          <td>${fn:escapeXml(cur.map.servicecenter_name)}</td>
          <td>${fn:escapeXml(cur.map.full_name)}</td>
          <td><fmt:formatNumber pattern="#,##0.00" value="${cur.map.bi_dianzi}"/></td>
          <td><fmt:formatNumber pattern="#,##0.00" value="${cur.map.sum_bi_no}"/></td>
          <td><fmt:formatNumber pattern="#,##0.00" value="${cur.map.all_sum_bi_no}"/></td>
          <td><fmt:formatNumber pattern="#,##0.00" value="${cur.map.bi_welfare}"/></td>
          
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
					pager.addHiddenInputs("method", "getData");
					pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
					pager.addHiddenInputs("st_add_date", "${af.map.st_add_date}");
			        pager.addHiddenInputs("en_add_date", "${af.map.en_add_date}");
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

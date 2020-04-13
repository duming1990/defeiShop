<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
   <html-el:form action="/admin/ActivityApply">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="5%" nowrap="nowrap">&nbsp;活动名称：
                <html-el:text property="title_like" styleId="title_like" styleClass="webinput" />
                &nbsp;发布时间 从：
                <html-el:text property="st_start_date" styleId="st_start_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                至:
                <html-el:text property="en_end_date" styleId="en_end_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                &nbsp;
                <html-el:button value="查 询" styleClass="bgButton" property="" styleId="btn_submit" />
                
                &nbsp;<input id="downloadQrcode" type="button" value="导出二维码" class="bgButton" />
                
                </td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="Activity.do?method=delete">
    <div style="padding-bottom:5px;">
      <logic-el:match name="popedom" value="+4+">
<!--         <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" /> -->
      </logic-el:match>
      <logic-el:match name="popedom" value="+1+">
<%--         <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='Activity.do?method=add&mod_id=${af.map.mod_id}';" /> --%>
      </logic-el:match>
      <input type="hidden" name="method" id="method" value="delete" />
      <input type="hidden" name="mod_id" id="mod_id" value="${af.map.mod_id}" />
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%" nowrap="nowrap"> <c:if test="${fn:contains(popedom, '+4+')}" var="isDelete">
            <input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" />
          </c:if>
          <c:if test="${not isDelete}"> 序号 </c:if>
        </th>
        <th >活动名称</th>
        <th width="10%" >商家</th>
        <th width="10%" >用户名</th>
        <th width="10%" >活动二维码</th>
        <th width="10%" >支付类型</th>
        <th width="10%" >添加时间</th>
        <th width="10%" >申请结果</th>
          <th width="12%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          <td><c:if test="${fn:contains(popedom, '+4+')}" var="isDelete">
           <c:set var="isDel" value="true"></c:set>
             <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}" />
            </c:if>
            <c:if test="${not isDelete}"> ${vs.count } </c:if></td>
          <td align="left" > <c:out value="${fnx:abbreviate(cur.map.title, 50, '...')}" /></td>
          <td align="center">${cur.entp_name }</td>
          <td align="center">${cur.map.user_name }</td>
          <td>
            <c:if test="${ not empty cur.qrcode_path}">
           <img src="${ctx}/${cur.qrcode_path}" width="100%"/>
            </c:if>
            <a target="_blank" href="${ctx}/m/MActivity.do?id=${cur.id}">链接</a>
          </td>
          <td>
          	<c:choose>
              <c:when test="${cur.pay_type eq 0}"><span class="label label-default">选商品</span></c:when>
              <c:when test="${cur.pay_type eq 1}"><span class="label label-success">直接支付</span></c:when>
            </c:choose>
          </td>
          <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
           <c:if test="${empty admin }">
          <td>
          	<c:choose>
              <c:when test="${cur.audit_state eq -1}">
              <span class="label label-danger label-block">审核不通过</span>
              <c:if test="${(not empty cur.remark)}">
	            <a title="${fn:escapeXml(cur.remark)}" class="label label-warning label-block" onclick="viewAuditDesc('${cur.remark}');">
	            <i class="fa fa-info-circle"></i>查看原因</a> 
	          </c:if>
              </c:when>
              <c:when test="${cur.audit_state eq 0}"><span class="label label-default">待审核</span></c:when>
              <c:when test="${cur.audit_state eq 1}"><span class="label label-success">审核通过</span></c:when>
            </c:choose>
            <c:if test="${empty cur.audit_state }">
            <span class="label label-danger label-block">未申请</span>
            </c:if>
          </td>
          </c:if>
            <td nowrap="nowrap">
            	<a class="butbase" onclick="doNeedMethod(null, 'ActivityApply.do', 'audit','id=${cur.id}&mod_id=${af.map.mod_id}&'+$('#bottomPageForm').serialize())">
            	<span class="icon-ok">审核</span></a>
            </td>
        </tr>
        <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="ActivityApply.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
            <script type="text/javascript">
				var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
				pager.addHiddenInputs("method", "list");
				pager.addHiddenInputs("title_like", "${fn:escapeXml(af.map.title_like)}");
				pager.addHiddenInputs("st_start_date", "${af.map.st_start_date}");
				pager.addHiddenInputs("en_end_date", "${af.map.en_end_date}");
				pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
				document.write(pager.toString());
            	</script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/cs.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	var f = document.forms[0];

 	$("#btn_submit").click(function(){
		if (this.form.st_start_date.value != "" && this.form.en_end_date.value != "") {
 			if (this.form.en_end_date.value < this.form.st_start_date.value) {
				alert("结束日期不得早于开始日期,请重新选择!")
				return false;
			}
		}
	this.form.submit();
	});
});

$("#downloadQrcode").click(function(){
	var submit = function (v, h, f) {
	    if (v == true) {
	    	location.href = "/manager/admin/ActivityApply.do?method=downloadPoorInfoQrcode&" + $('.searchForm').serialize();
	    }
	    return true;
	};
	var tip = "确定导出二维码图片吗？";
	$.jBox.confirm(tip, "系统提示", submit, { buttons: { '确定': true, '取消': false} });
});

//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>

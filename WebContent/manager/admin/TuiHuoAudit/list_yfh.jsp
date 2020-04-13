<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div style="width: 99%" class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/TuiHuoAudit">
    <html-el:hidden property="method" value="list_yfh" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td><div> 用户名：
            <html-el:text property="user_name_like" maxlength="40" style="width:150px;" styleClass="webinput" />
            &nbsp;订单类型：
            <html-el:select property="order_type" styleClass="webinput" >
              <html-el:option value="">全部</html-el:option>
              <html-el:option value="10">消费商品订单</html-el:option>
              <html-el:option value="11">实物商品订单</html-el:option>
              <html-el:option value="140">团购订单</html-el:option>
            </html-el:select>
            &nbsp;审核状态：
            <html-el:select property="audit_state" styleClass="webinput" >
              <html-el:option value="">全部</html-el:option>
              <html-el:option value="0">未审核</html-el:option>
              <html-el:option value="2">商家审核通过</html-el:option>
              <html-el:option value="-2">商家审核不通过</html-el:option>
            </html-el:select>
            &nbsp;是否删除：
            <html-el:select property="is_del" styleClass="webinput" >
              <html-el:option value="">全部</html-el:option>
              <html-el:option value="0">否</html-el:option>
              <html-el:option value="1">是</html-el:option>
            </html-el:select>
<!--             &nbsp;换货原因： -->
<%--             <html-el:select property="return_type" styleClass="webinput" > --%>
<%--               <html-el:option value="">请选择...</html-el:option> --%>
<%--               <c:forEach items="${baseDataList}" var="cur"> --%>
<%--                 <html-el:option value="${cur.id}">${cur.type_name}</html-el:option> --%>
<%--               </c:forEach> --%>
<%--             </html-el:select> --%>
          </div>
          <div style="margin-top:5px;">添加时间：
            从
            <html-el:text property="st_add_date" styleClass="webinput"  styleId="st_add_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
            至
            <html-el:text property="en_add_date" styleClass="webinput"  styleId="en_add_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
            &nbsp;审核时间：
            从
            <html-el:text property="st_audit_date" styleClass="webinput"  styleId="st_audit_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
            至
            <html-el:text property="en_audit_date" styleClass="webinput"  styleId="en_audit_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
            &nbsp;
            <html-el:submit value="查 询" styleClass="bgButton"  />
          </div></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="TuiHuoAudit.do?method=delete">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%">序号</th>
        <th width="8%">用户名</th>
        <th>商品名称</th>
         <th width="12%">退货原因</th>
        <th width="12%">期望处理方式</th>
        <th width="8%">添加时间</th>
        <th width="8%">审核状态</th>
        <th width="8%">审核时间</th>
        <th width="8%">审核人</th>
        <th width="12%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center">${vs.count}</td>
          <td align="center">${fn:escapeXml(cur.user_name)}</td>
          <td align="center">${fn:escapeXml(cur.comm_name)}</td>
          <td align="center"><script type="text/javascript">showTuiHuoReasone(${cur.return_type})</script></td>
          <td align="center"><script type="text/javascript">showTuiHuoCause(${cur.expect_return_way})</script></td>
          <fmt:formatDate var="add_date" value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm:ss" />
          <td align="center" title="添加时间：${add_date}"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
          <td align="center"><c:choose>
              <c:when test="${cur.audit_state eq -2}"><span style=" color:#F00;">审核不通过</span></c:when>
              <c:when test="${cur.audit_state eq 0}"><span>待审核</span></c:when>
              <c:when test="${cur.audit_state eq 2}"><span style=" color:#060;">审核通过</span></c:when>
            </c:choose></td>
          <td align="center"><fmt:formatDate value="${cur.audit_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
          <td align="center">${cur.map.audit_user_info.user_name}</td>
          <td align="center" nowrap="nowrap">
            <a class="butbase"><span onclick="doNeedMethod(null, 'TuiHuoAudit.do', 'view','id=${cur.id}&mod_id=${af.map.mod_id}&'+$('#bottomPageForm').serialize())" class="icon-ok">查看</span></a>
            </td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="TuiHuoAudit.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list_yfh");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("user_name_like", "${fn:escapeXml(af.map.user_name_like)}");
			pager.addHiddenInputs("st_add_date", "${af.map.st_add_date}");
			pager.addHiddenInputs("en_add_date", "${af.map.en_add_date}");
			pager.addHiddenInputs("st_audit_date", "${af.map.st_audit_date}");
			pager.addHiddenInputs("en_audit_date", "${af.map.en_audit_date}");
            pager.addHiddenInputs("audit_date_st", "${af.map.audit_date_st}");
			pager.addHiddenInputs("audit_date_en", "${af.map.audit_date_en}");
			pager.addHiddenInputs("audit_state", "${af.map.audit_state}");
			pager.addHiddenInputs("order_type", "${af.map.order_type}");
			pager.addHiddenInputs("tk_status", "${af.map.tk_status}");
			pager.addHiddenInputs("is_del", "${af.map.is_del}");
			pager.addHiddenInputs("return_type", "${af.map.return_type}");
            document.write(pager.toString());
            </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("a.viewImgMain").colorbox();
});
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>

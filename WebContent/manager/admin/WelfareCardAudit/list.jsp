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
</head>
<body>
<div class="divContent">
  <html-el:form action="/admin/WelfareCardAudit" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td>
          <div style="margin-top: 5px;">申请批次：
            <html-el:text property="apply_no" styleClass="webinput" maxlength="50" style="width:200px;"/>
            &nbsp;&nbsp;标题：
            <html-el:text property="title_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
           	 审核状态：
            <html-el:select property="audit_state" styleId="audit_state" styleClass="webinput" >
              <html-el:option value="">请选择...</html-el:option>
              <html-el:option value="-1">审核不通过</html-el:option>
              <html-el:option value="0">待审核</html-el:option>
              <html-el:option value="1">审核通过</html-el:option>
            </html-el:select>
              &nbsp;&nbsp;申请时间
                            从:
          <html-el:text property="st_add_date" styleId="st_add_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
         	 至：
          <html-el:text property="en_add_date" styleId="en_add_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
            &nbsp;&nbsp;<button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button>
          </div></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="WelfareCardAudit.do?method=delete">
    <div style="text-align: left;padding:5px;">
<!--       <button class="bgButtonFontAwesome" type="button" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);"><i class="fa fa-minus-square"></i>删除所选</button> -->
<%--       <button class="bgButtonFontAwesome" type="button" onclick="location.href='WelfareCardAudit.do?method=add&mod_id=${af.map.mod_id}&';" ><i class="fa fa-plus-square"></i>添加</button> --%>
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr class="tite2">
        <th width="5%"><input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /></th>
        <th width="nowrap">申请县域</th>
        <th width="8%">标题</th>
        <th width="8%">申请批次</th>
        <th width="5%">额度</th>
        <th width="5%">数量</th>
        <th width="5%">是否实体卡</th>
        <th width="8%">有效期开始时间</th>
        <th width="8%">有效期结束时间</th>
        <th width="8%">申请时间</th>
        <th width="8%">审核状态</th>
        <th width="8%">审核说明</th>
        <th width="8%">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
      	  <td align="center">
              <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}" />
          </td>
          <td><a class="viewDetail" href="${ctx}/manager/admin/ServiceInfoAudit.do?method=view&mod_id=1003002000&id=${cur.sevice_center_info_id}">${fn:escapeXml(cur.service_name)}</a></td>
          <td>${fn:escapeXml(cur.title)}</td>
          <td>${fn:escapeXml(cur.apply_no)}</td>
          <td>${fn:escapeXml(cur.card_amount)}</td>
          <td>${fn:escapeXml(cur.card_count)}</td>
          <td>
	          <c:if test="${cur.is_entity eq 0}">否</c:if>
	          <c:if test="${cur.is_entity eq 1}">是</c:if>
          </td>
          <td><fmt:formatDate value="${cur.start_date}" pattern="yyyy-MM-dd" /></td>
          <td><fmt:formatDate value="${cur.end_date}" pattern="yyyy-MM-dd" /></td>
          <td><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd" /></td>
          <td>
          	<c:if test="${cur.audit_state eq 0}"><span>待审核</span> </c:if>
            <c:if test="${cur.audit_state eq 1}"> <span style="color:#060;">审核通过</span> </c:if>
            <c:if test="${cur.audit_state eq -1}"> <span style="color:red;">审核不通过</span> </c:if>
          </td>
          <td>${fn:escapeXml(cur.audit_desc)}</td>
          <td>
          
          <c:if test="${cur.audit_state ne 1}">
<%--              <a class="butbase" id="edit" onclick="doNeedMethod('null', 'WelfareCardAudit.do','add', 'id=${cur.id}&mod_id=${af.map.mod_id}&' + $('#bottomPageForm').serialize())"><span class="icon-edit">修改</span></a>  --%>
<%--              <a class="butbase" id="remove" onclick="confirmDelete(null, 'WelfareCardAudit.do', 'id=${cur.id}&mod_id=${af.map.mod_id}&' + $('#bottomPageForm').serialize())"><span class="icon-remove">删除</span></a> --%>
          </c:if>
          <c:if test="${cur.audit_state eq 1}">
<!--              <a class="butbase but-disabled" id="edit" title="已审核，不能修改"><span class="icon-edit">修改</span></a>  -->
<!--              <a class="butbase but-disabled" id="remove" title="已审核，不能删除"><span class="icon-remove">删除商品</span></a> -->
          </c:if>
          <logic-el:match name="popedom" value="+8+"> 
         		<a class="butbase" onclick="doNeedMethod(null, 'WelfareCardAudit.do', 'audit','id=${cur.id}&mod_id=${af.map.mod_id}&'+$('#bottomPageForm').serialize())"><span class="icon-ok">审核</span></a>
          </logic-el:match>
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
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
   <form id="bottomPageForm" name="bottomPageForm" method="post" action="WelfareCardAudit.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "list");
					pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
					pager.addHiddenInputs("apply_no", "${fn:escapeXml(af.map.apply_no)}");
					pager.addHiddenInputs("title_like", "${fn:escapeXml(af.map.title_like)}");
					pager.addHiddenInputs("st_add_date", "${fn:escapeXml(af.map.st_add_date)}");
					pager.addHiddenInputs("en_add_date", "${fn:escapeXml(af.map.en_add_date)}");
					document.write(pager.toString());
	            	</script></td>
        </tr>
      </table>
    </form>
  </div>
  <div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript">//<![CDATA[
function getService(id){
	var url="${ctx}/manager/admin/ServiceInfoAudit.do?method=view&mod_id=1003002000&id="+id;
	$.dialog({
		title:  "县域信息",
		width:  800,
		height: 600,
        lock:true ,
        zIndex: 9999,
		content:"url:"+url
	});
}
//]]></script>
</body>
</html>

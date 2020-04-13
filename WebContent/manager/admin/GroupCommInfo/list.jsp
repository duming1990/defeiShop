<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${app_name}</title>
<meta content="${app_name}会员中心关键字" name="keywords" />
<meta content="${app_name}会员中心介绍" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/GroupCommInfo" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td>
          <div style="margin-top: 5px;">企业名称：
            <html-el:hidden property="own_entp_id" styleId="own_entp_id" />
            <html-el:text property="entp_name" styleId="entp_name" maxlength="125" styleClass="webinput" value="${entp_name}" readonly="true" onclick="openEntpChild()"/>
             &nbsp; 活动名称：
            <html-el:text property="comm_title_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
            &nbsp;审核状态：
            <html-el:select property="audit_state" styleId="audit_state" styleClass="webinput" >
              <html-el:option value="">请选择...</html-el:option>
              <html-el:option value="-1">审核不通过</html-el:option>
              <html-el:option value="0">待审核</html-el:option>
              <html-el:option value="1">审核通过</html-el:option>
            </html-el:select>
            &nbsp;是否开启限购：
            <html-el:select property="is_buyer_limit" >
              <html-el:option value="">全部</html-el:option>
              <html-el:option value="0">未开启</html-el:option>
              <html-el:option value="1">已开启</html-el:option>
            </html-el:select>
            &nbsp;&nbsp;
            <html-el:submit value="查 询" styleClass="bgButton" styleId="bgButton" />
          </div></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="GroupCommInfo.do?method=delete">
  <div style="text-align: left;padding: 5px;">
        <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" />
        <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='GroupCommInfo.do?method=add&mod_id=${af.map.mod_id}&comm_type=${af.map.comm_type}';" />
<!--         <input type="button" name="download" id="download" class="bgButton" value="导出Excel" onclick="toExcel()" /> -->
    </div>
    <input type="hidden" name="method" id="method" value="delete" />
    <input type="hidden" name="mod_id" id="mod_id" value="${af.map.mod_id}" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr class="tite2">
        <th width="5%" ><input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /></th>
        <th nowrap="nowrap">活动名称</th>
        <th width="12%">企业名称</th>
        <th width="8%">活动价格</th>
        <th width="14%">时间区间</th>
        <th width="10%">是否开启限购</th>
        <th width="10%">每人限购数量</th>
        <th width="10%">活动数量</th>
        <th width="8%">审核状态</th>
        <th width="10%">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
      
       <tr align="center">
          <td><c:if test="${cur.is_del eq 1}">
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
          <td align="left">
            <c:if test="${cur.audit_state eq 1}"><i class="fa fa-globe preview"></i></c:if>
            <a title="${fn:escapeXml(cur.comm_title)}">
            <c:out value="${fnx:abbreviate(cur.comm_title, 60, '...')}" />
            </a>
          </td>
          <td>${fn:escapeXml(cur.map.entp_name)}</td>
          <td><fmt:formatNumber pattern="#,##0.00" value="${cur.prom_price}"/></td>
          <td>
             <fmt:formatDate value="${cur.start_time}" pattern="yyyy-MM-dd HH:mm:SS" /><br/>
                                            至<br/>
             <fmt:formatDate value="${cur.end_time}" pattern="yyyy-MM-dd HH:mm:SS" />
         </td>
          <td>
            <c:if test="${cur.is_buyer_limit eq 0}"><span class="label label-default">未开启</span></c:if>
            <c:if test="${cur.is_buyer_limit eq 1}"><span class="label label-success">已开启</span></c:if>
          </td>
          <td>
            <c:if test="${cur.is_buyer_limit eq 0}">不限制数量</c:if>
            <c:if test="${(cur.is_buyer_limit eq 1) and (empty cur.buyer_limit_num)}">未设置</c:if>
            <c:if test="${(cur.is_buyer_limit eq 1) and (not empty cur.buyer_limit_num)}">${cur.buyer_limit_num}&nbsp;个</c:if>
          </td>
          <td>
          	<c:if test="${empty cur.buyer_limit_num}">未设置</c:if>
          	<c:if test="${not empty cur.buyer_limit_num}">${cur.prom_inventory}&nbsp;个</c:if>
          	</td>
          <td>
            <c:choose>
              <c:when test="${cur.audit_state eq -1}"><span class="label label-danger">审核不通过</span></c:when>
              <c:when test="${cur.audit_state eq 0}"><span class="label label-default">待审核</span></c:when>
              <c:when test="${cur.audit_state eq 1}"><span class="label label-success">审核通过</span></c:when>
            </c:choose>
          </td>
          <td>
            <a class="butbase" onclick="doNeedMethod(null, 'GroupCommInfo.do', 'audit','id=${cur.id}&'+$('#bottomPageForm').serialize())"><span class="icon-ok">审核</span></a>
            <a class="butbase" id="edit" onclick="confirmUpdate('null', 'GroupCommInfo.do', 'id=${cur.id}&mod_id=${af.map.mod_id}&' + $('#bottomPageForm').serialize())"><span id="${cur.id}" class="icon-edit">修改</span></a><br/>
            <a class="butbase" id="edittcfw" onclick="doNeedMethod('null', 'GroupCommInfo.do', 'edittcfw', 'comm_id=${cur.id}&' + $('#bottomPageForm').serialize())"><span id="${cur.id}" class="icon-edit">修改套餐</span></a><br/>
          	<c:if test="${cur.audit_state ne 1}"><a class="butbase" id="remove" onclick="confirmDelete(null, 'GroupCommInfo.do', 'id=${cur.id}&mod_id=${af.map.mod_id}&' + $('#bottomPageForm').serialize())"><span id="${cur.id}" class="icon-remove">删除</span></a></c:if>
             <c:if test="${cur.audit_state eq 1}"><a class="butbase but-disabled" id="remove" onclick="javascript:void(0);"  title="已审核，不能删除"><span id="${cur.id}" class="icon-remove">删除</span></a></c:if>
          
          </td>
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
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td width="8%" nowrap="nowrap">&nbsp;</td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="GroupCommInfo.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "list");
					pager.addHiddenInputs("own_entp_id", "${af.map.own_entp_id}");
					pager.addHiddenInputs("comm_title_like", "${fn:escapeXml(af.map.comm_title_like)}");
					pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
					pager.addHiddenInputs("audit_state", "${af.map.audit_state}");
					pager.addHiddenInputs("is_buyer_limit", "${af.map.is_buyer_limit}");
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
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	$(".qtip").quicktip();
	
	var f = document.forms[0];

	$("#btn_submit").click(function(){
		this.form.submit();
	});
});


function openEntpChild(){
	
	var url = "${ctx}/BaseCsAjax.do?method=chooseEntpInfo&dir=admin";
	$.dialog({
		title:  "选择企业",
		width:  770,
		height: 550,
        lock:true ,
		content:"url:"+url
	});
}

//]]></script>
<jsp:include page="../../../_public_page.jsp" flush="true"/>
</body>
</html>

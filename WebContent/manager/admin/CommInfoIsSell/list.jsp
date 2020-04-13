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
            <html-el:form action="/admin/CommInfoIsSell" styleClass="searchForm">
              <html-el:hidden property="method" value="list" />
              <html-el:hidden property="mod_id" />
               <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
               <tr>
        	<td>审核状态： 
                 <html-el:select property="audit_state" styleId="audit_state" styleClass="webinput" >
                      <html-el:option value="">请选择...</html-el:option>
                      <html-el:option value="-1">审核不通过</html-el:option>
                      <html-el:option value="0">待审核</html-el:option>
                      <html-el:option value="1">审核通过</html-el:option>
                    </html-el:select>
                  &nbsp;操作类型：
              <html-el:select property="opt_type">
                  <html-el:option value="2">上架审核</html-el:option>
                  <html-el:option value="3">下架审核</html-el:option>
                </html-el:select>
              &nbsp;&nbsp;
                <html-el:submit value="查 询" styleClass="bgButton" styleId="bgButton" />
                </div>
                </td>
      		</tr>
      </table>
    </html-el:form>
      <%@ include file="/commons/pages/messages.jsp" %>
      <form id="listForm" name="listForm" method="post" action="CommInfo.do?method=delete">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
          <tr class="tite2">
        <th width="5%">序号</th>
        <th>上下架申请人</th>
        <th>操作类型</th>
        <th>商品名称</th>
        <th width="12%">商品类别</th>
        <th width="10%">商品编号</th>
        <th width="10%">销售价格</th>
        <th width="10%">审核状态</th>
        <th width="10%">上下架时间</th>
        <th width="10%">上下架状态</th>
        <th width="10%">操作</th>
      </tr>
      <c:forEach var="cur" items="${baseAuditRecordList}" varStatus="vs">
        <tr align="center">
          <td>${vs.count}</td>
          <td>${fn:escapeXml(cur.add_user_name)}</td>
          <td><c:forEach var="ot" items="${optTypes}">
              <c:if test="${ot.index eq  cur.opt_type}">${ot.name}</c:if>
           </c:forEach></td>
          <c:url var="url" value="/manager/customer/CommInfo.do?method=view&amp;id=${cur.map.commInfo.id}&amp;mod_id=${af.map.mod_id}&amp;par_id=${af.map.par_id}" />
          <td align="left"><a href="${url}" title="${fn:escapeXml(cur.map.commInfo.comm_name)}">
            <c:out value="${fnx:abbreviate(cur.map.commInfo.comm_name, 60, '...')}" />
            </a></td>
          <td>${fn:escapeXml(cur.map.commInfo.cls_name)}</td>
          <td>${fn:escapeXml(cur.map.commInfo.comm_no)}</td>
          <td><fmt:formatNumber pattern="#,##0.00" value="${cur.map.commInfo.sale_price}"/></td>
          <td><c:if test="${cur.audit_state eq -1}"> <span style="color:#F00;">
              <c:out value="审核不通过"/>
              </span> </c:if>
            <c:if test="${cur.audit_state eq 0}">
              <c:out value="待审核"/>
            </c:if>
            <c:if test="${cur.audit_state eq 1}"> <span style="color:#060;">
              <c:out value="审核通过"/>
              </span> </c:if>
          <td><c:if test="${cur.map.commInfo.is_sell eq 1}">
              <fmt:formatDate value="${cur.map.commInfo.up_date}" pattern="yyyy-MM-dd" />
              至
              <fmt:formatDate value="${cur.map.commInfo.down_date}" pattern="yyyy-MM-dd" />
            </c:if>
            <c:if test="${cur.map.commInfo.is_sell eq 0}"><span style="color:#F00;">暂不上架</span></c:if>
          </td>
          <td nowrap="nowrap"><c:choose>
              <c:when test="${cur.map.commInfo.is_sell eq 1}"><span class="label label-success">已上架</span></c:when>
              <c:when test="${cur.map.commInfo.is_sell eq 0}"><span class="label label-danger">已下架</span></c:when>
              <c:otherwise>未上架 </c:otherwise>
            </c:choose></td>
            <td align="center" nowrap="nowrap">
          	 <span style="cursor: pointer;" onclick="doNeedMethod(null, 'CommInfoIsSell.do', 'audit','id=${cur.id}&opt_type=${af.map.opt_type }&mod_id=${af.map.mod_id}&'+$('#bottomPageForm').serialize())">
          	 	<img src="${ctx}/styles/images/shenhe.gif" width="55" height="18" />
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
          <td nowrap="nowrap">&nbsp;</td>
          <td>&nbsp;</td>
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
      <form id="bottomPageForm" name="bottomPageForm" method="post" action="CommInfoIsSell.do">
        <table width="98%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
              <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "list");
					pager.addHiddenInputs("audit_state", "${af.map.audit_state}");
					pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
					pager.addHiddenInputs("opt_type", "${af.map.opt_type}");
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
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	var f = document.forms[0];

	$("#btn_submit").click(function(){
		this.form.submit();
	});


});


//]]></script>
<jsp:include page="../../../_public_page.jsp" flush="true"/>
</body>
</html>
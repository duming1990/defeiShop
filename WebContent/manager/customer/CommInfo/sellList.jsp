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
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/CommInfo" styleClass="searchForm">
    <html-el:hidden property="method" value="sellList" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td>产品类别：
          <html-el:hidden property="cls_id" styleId="cls_id"/>
          <html-el:text property="cls_name" styleId="cls_name" readonly="true" onclick="getParClsInfo();" styleClass="webinput" maxlength="50"  style="width:258px;"/>
          <div style="margin-top: 5px;"> 商品名称：
            <html-el:text property="comm_name_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
            &nbsp;审核状态：
            <html-el:select property="audit_state" styleId="audit_state" styleClass="webinput" >
              <html-el:option value="">请选择...</html-el:option>
               <html-el:option value="-1">管理员审核不通过</html-el:option>
              <html-el:option value="-2">合伙人审核不通过</html-el:option>
              <html-el:option value="0">待审核</html-el:option>
              <html-el:option value="2">合伙人审核通过</html-el:option>
              <html-el:option value="1">管理员审核通过</html-el:option>
            </html-el:select>
            &nbsp;是否删除：
            <html-el:select property="is_del" >
              <html-el:option value="">全部</html-el:option>
              <html-el:option value="0">未删除</html-el:option>
              <html-el:option value="1">已删除</html-el:option>
            </html-el:select>
            &nbsp;&nbsp;
            <button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button>
          </div></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="CommInfo.do?method=delete">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr class="tite2">
        <th width="5%">序号</th>
        <th>商品名称</th>
        <th width="12%">商品类别</th>
        <th width="10%">商品编号</th>
        <th width="10%">销售价格</th>
        <th width="10%">审核状态</th>
        <th width="10%">上下架时间</th>
        <th width="10%">上下架状态</th>
        <th width="10%">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          <td>${vs.count}</td>
          <c:url var="url" value="/manager/customer/CommInfo.do?method=view&amp;id=${cur.id}&amp;mod_id=${af.map.mod_id}&amp;par_id=${af.map.par_id}" />
          <td align="left"><a href="${url}" title="${fn:escapeXml(cur.comm_name)}">
            <c:out value="${fnx:abbreviate(cur.comm_name, 60, '...')}" />
            </a></td>
          <td>${fn:escapeXml(cur.cls_name)}</td>
          <td>${fn:escapeXml(cur.comm_no)}</td>
          <td><fmt:formatNumber pattern="#,##0.00" value="${cur.sale_price}"/></td>
          <td>
            <c:choose>
              <c:when test="${cur.audit_state eq -1}"><span class="tip-danger">管理员审核不通过</span></c:when>
              <c:when test="${cur.audit_state eq -2}"><span class="tip-danger">合伙人审核不通过</span></c:when>
              <c:when test="${cur.audit_state eq 0}"><span class="tip-default">待审核</span></c:when>
              <c:when test="${cur.audit_state eq 2}"><span class="tip-success">合伙人审核通过</span></c:when>
              <c:when test="${cur.audit_state eq 1}"><span class="tip-success">管理员审核通过</span></c:when>
            </c:choose>
          </td>
          <td><c:if test="${cur.is_sell eq 1}">
              <fmt:formatDate value="${cur.up_date}" pattern="yyyy-MM-dd" />
              至
              <fmt:formatDate value="${cur.down_date}" pattern="yyyy-MM-dd" />
            </c:if>
            <c:if test="${cur.is_sell eq 0}"><span style="color:#F00;">暂不上架</span></c:if>
          </td>
          <td nowrap="nowrap"><c:choose>
              <c:when test="${cur.is_sell eq 1}"><span class="label label-success">已上架</span></c:when>
              <c:when test="${cur.is_sell eq 0}"><span class="label label-danger">已下架</span></c:when>
              <c:otherwise>未上架 </c:otherwise>
            </c:choose></td>
          <td nowrap="nowrap"><a class="label label-warning" onclick="issell('${cur.id}')"><span>上下架</span></a> </td>
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
          <td width="8%" nowrap="nowrap">&nbsp;</td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="CommInfo.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "sellList");
					pager.addHiddenInputs("comm_name_like", "${fn:escapeXml(af.map.comm_name_like)}");
					pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
			        pager.addHiddenInputs("par_id", "${af.map.par_id}");
					pager.addHiddenInputs("audit_state", "${af.map.audit_state}");
					pager.addHiddenInputs("comm_type", "${af.map.comm_type}");
					pager.addHiddenInputs("cls_name", "${af.map.cls_name}");
					pager.addHiddenInputs("cls_id", "${af.map.cls_id}");
					pager.addHiddenInputs("is_del", "${af.map.is_del}");
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
	
});

function issell(_id){
	var queryString = $('#bottomPageForm').serialize();
	$.dialog({
		title:"商品上下架",
		width:500,
		height:450,
        lock:true ,
		content:"url:${ctx}/manager/customer/CommInfo.do?method=issell&id=" + _id + "&" + queryString
	});
}

function getParClsInfo() {
	var url = "PdInfo.do?method=getParClsInfo&isPd=true&azaz=" + Math.random();
	$.dialog({
		title:  "选择产品类别",
		width:  450,
		height: 400,
        lock:true ,
		content:"url:"+url
	});
}
function refreshPar(){
	window.location.reload();
}

//]]></script>
</body>
</html>

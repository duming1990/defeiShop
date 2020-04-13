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
  <html-el:form action="/customer/CommWelfareApply.do" styleClass="searchForm">
    <html-el:hidden property="method" value="commInfoList" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td>商品类别：
          <div style="margin-top: 5px;">商品名称：
            <html-el:text property="comm_name_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
          
            &nbsp;&nbsp;
            <button class="" type="submit"><i class=""></i>查 询</button>
          </div></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="CommInfo.do?method=delete">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr class="tite2">
        <th nowrap="nowrap">商品名称</th>
        <th width="8%">商品主图</th>
        <th width="6%">产品类别</th>
        <th width="8%">商品编号</th>
        <th width="6%">销售价格</th>
        <th width="10%">上下架时间</th>
        <th width="8%">商品库存</th>
        <th width="10%">商品二维码</th>
        <th width="8%">是否提供发票</th>
        <c:if test="${empty is_jd}"><th width="8%">能否自提</th></c:if>
        <th width="8%">审核状态</th>
      </tr>
      <c:forEach var="cur" items="${commInfoList}" varStatus="vs">
       <tr>
          <td align="left">
            <c:if test="${cur.audit_state eq 1}">
               <c:url var="url" value="/entp/IndexEntpInfo.do?method=getCommInfo&id=${cur.id}" />
               <a href="${url}" target="_blank"><i class="fa fa-globe preview"></i></a>
            </c:if>
            <c:url var="url" value="/manager/customer/CommInfo.do?method=view&amp;id=${cur.id}&amp;mod_id=${af.map.mod_id}&amp;par_id=${af.map.par_id}" />
            <a href="${url}" title="${fn:escapeXml(cur.comm_name)}">
            <c:out value="${fnx:abbreviate(cur.comm_name, 60, '...')}" />
            </a></td>
          <td><img src="${ctx}/${cur.main_pic}" width="100%" /></td>
          <td>${fn:escapeXml(cur.cls_name)}</td>
          <td>${fn:escapeXml(cur.comm_no)}</td>
          <td><fmt:formatNumber pattern="#,##0.00" value="${cur.sale_price}"/></td>
          <td><c:if test="${cur.is_sell eq 1}">
              <fmt:formatDate value="${cur.up_date}" pattern="yyyy-MM-dd" />
              <br/> 至<br/>
              <fmt:formatDate value="${cur.down_date}" pattern="yyyy-MM-dd" />
            </c:if>
            <c:if test="${cur.is_sell eq 0}"><span style="color:#F00;">暂不上架</span></c:if>
          </td>
          <td><c:if test="${not empty cur.map.count_tczh_price_inventory}">
              <c:if test="${cur.map.count_tczh_price_inventory le 0}"> <span>库存不足，请及时修改库存</span> </c:if>
              <c:if test="${cur.map.count_tczh_price_inventory gt 0}"> <span style="color:#060;">库存充足</span> </c:if>
            </c:if>
            <c:if test="${empty cur.map.count_tczh_price_inventory}"> <span style="color:#F00;">没有维护套餐，没有库存</span> </c:if>
          </td>
           <td>
            <c:if test="${ not empty cur.comm_qrcode_path}">
           <img src="${ctx}/${cur.comm_qrcode_path}" width="120"/>
            </c:if>
          </td>
          <td>
          	<c:if test="${cur.is_fapiao eq 0}"><span style="color:red;">不提供</span> </c:if>
            <c:if test="${cur.is_fapiao eq 1}"> <span style="color:#060;">提供</span> </c:if>
          </td>
          <c:if test="${empty is_jd}">
          <td>
          	<c:if test="${cur.is_ziti eq 0}"><span style="color:red;">不能自提</span> </c:if>
            <c:if test="${cur.is_ziti eq 1}"> <span style="color:#060;">能自提</span> </c:if>
          </td>
          </c:if>
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
        </tr>
        <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
      </c:forEach>
      <c:forEach begin="${i}" end="${af.map.pager.pageSize - 1}">
        <tr align="center">
          <td nowrap="nowrap">&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
         <c:if test="${empty is_jd}"> <td>&nbsp;</td></c:if>
          <td>&nbsp;</td>
        </tr>
      </c:forEach>
    </table>
  </form>
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
	$(".qtip").quicktip();
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

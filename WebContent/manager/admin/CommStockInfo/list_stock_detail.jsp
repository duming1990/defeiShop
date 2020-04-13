<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>进货管理 - ${app_name}</title>
<meta content="${app_name}会员中心关键字" name="keywords" />
<meta content="${app_name}会员中心介绍" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <html-el:form action="/admin/CommStockInfo" styleClass="searchForm">
    <html-el:hidden property="method" value="listStockDetail" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="id" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td>
          <div style="margin-top: 5px;">时间：
                              从&nbsp;
            <html-el:text property="begin_date" styleId="begin_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker()" />
               	&nbsp;至&nbsp;
               <html-el:text property="end_date" styleId="end_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker()" />
           &nbsp;&nbsp;进/出货：
            <html-el:select property="stock_type" >
              <html-el:option value="-1">全部</html-el:option>
              <html-el:option value="0">进货</html-el:option>
              <html-el:option value="1">出货</html-el:option>
            </html-el:select>
           &nbsp;&nbsp;<button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button>
          </div></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="CommStockInfo.do?method=delete">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr class="tite2">
        <th width="5%">序号</th>
        <th nowrap="nowrap">商品名称</th>
        <th width="8%">进/出货</th>
        <th width="15%">进/出货类型</th>
        <th width="10%">进/出货前库存</th>
        <th width="8%">进/出货数量</th>
        <th width="8%">进货价</th>
        <th width="8%">进/出货后库存</th>
        <th width="10%">操作时间</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          <td>${vs.count}</td>
          <td>${fn:escapeXml(cur.map.comm_name)}</td>
          <td>
              <c:if test="${cur.stock_type eq 0}"> <span style="color:#060;">进货</span> </c:if>
              <c:if test="${cur.stock_type eq 1}"> <span style="color:#F00;">出货</span> </c:if>
          </td>
          <td align="center">${cur.input_out_type_name}</td>
          <td align="center">${cur.before_stock}</td>
	      <td>
	         <c:if test="${cur.stock_type eq 0}"> <span style="color:#060;">${cur.this_stock}</span></c:if>
	         <c:if test="${cur.stock_type eq 1}"> <span style="color:#F00;">${cur.this_stock}</span></c:if>
	      </td>
	      <td align="center"><fmt:formatNumber pattern="￥#,##0.00" value="${cur.input_price}" /></td>
	      <td align="center">${cur.after_stock}</td>
	      <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm" /></td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="CommStockInfo.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "listStockDetail");
					pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
			        pager.addHiddenInputs("par_id", "${af.map.par_id}");
			        pager.addHiddenInputs("id", "${af.map.id}");
					pager.addHiddenInputs("comm_name_like", "${af.map.comm_name_like}");
					pager.addHiddenInputs("begin_date", "${af.map.begin_date}");
					pager.addHiddenInputs("end_date", "${af.map.end_date}");
					pager.addHiddenInputs("stock_type", "${af.map.stock_type}");
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
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$(".qtip").quicktip();
});
//]]></script>
</body>
</html>

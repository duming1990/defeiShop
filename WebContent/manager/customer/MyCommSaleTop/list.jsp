<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${app_name}</title>
<meta content="${app_name}" name="keywords" />
<meta content="${app_name}" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/MyCommSaleTop" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id"/>
    <html-el:hidden property="par_id"/>
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td>商品名称：
            <html-el:text property="comm_name_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
            &nbsp;直接查询：
            <html-el:select property="orderDay" styleId="orderDay">
              <html-el:option value="0">总排名</html-el:option>
              <html-el:option value="1">本月排名</html-el:option>
              <html-el:option value="2">昨日排名</html-el:option>
              <html-el:option value="3">今日排名</html-el:option>
            </html-el:select>
            <c:if test="${userInfo.is_fuwu eq 1}">
             &nbsp;所属店铺：
            <html-el:hidden property="own_entp_id" styleId="own_entp_id" />                                                                    
            <html-el:text property="entp_name" styleId="entp_name" maxlength="125" styleClass="webinput" readonly="true" onclick="openEntpChild()" />
            </c:if>
            &nbsp;&nbsp;
            <button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button>
<!--             &nbsp; -->
<!--           <button class="bgButtonFontAwesome" type="button" id="download"><i class="fa fa-download"></i>导 出</button> -->
          </td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="MyCommSaleTop.do?method=delete">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr class="tite2">
        <th width="5%">排名</th>
        <th nowrap="nowrap">商品名称</th>
        <c:if test="${userInfo.is_fuwu eq 1}">
        <th nowrap="nowrap">商家名称</th>
       </c:if>
        <th width="10%">销量总量</th>
        <th width="10%">销售总金额</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          <td align="center">${vs.count}</td>
          <td align="left"><c:out value="${fnx:abbreviate(cur.comm_name, 60, '...')}" /></td>
            <c:if test="${userInfo.is_fuwu eq 1}">
           <td>${fn:escapeXml(cur.map.entp_name)}</td>
         </c:if>
          <td>${fn:escapeXml(cur.map.sum_good_count)}</td>
          <td><fmt:formatNumber pattern="#,##0.00" value="${cur.map.sum_good_price}"/>元</td>
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
          <c:if test="${userInfo.is_fuwu eq 1}">
          <td>&nbsp;</td></c:if>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="MyCommSaleTop.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
           <script type="text/javascript">
			 var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
			 pager.addHiddenInputs("method", "list");
			 pager.addHiddenInputs("comm_name_like", "${fn:escapeXml(af.map.comm_name_like)}");
			 pager.addHiddenInputs("orderDay", "${fn:escapeXml(af.map.orderDay)}");
			 pager.addHiddenInputs("own_entp_id", "${fn:escapeXml(af.map.own_entp_id)}");
			 pager.addHiddenInputs("entp_name", "${fn:escapeXml(af.map.entp_name)}");
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
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
   $("#download").click(function(){
		var submit = function (v, h, f) {
		    if (v == true) {
		    	location.href = "${ctx}/manager/customer/MyCommSaleTop.do?method=toExcel&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
		    } else {
		    	location.href = "${ctx}/manager/customer/MyCommSaleTop.do?method=toExcel&code=GBK&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
		    }
		    return true;
		};
		var tip = "确认导出EXCEL格式数据？如果UTF-8编码格式乱码，请选择GBK编码格式下载！";
		$.jBox.confirm(tip, "系统提示", submit, { buttons: { '下载(UTF-8编码)': true, '下载(GBK编码)': false} });
	});
});


function openEntpChild(){
    
	var url = "${ctx}/BaseCsAjax.do?method=chooseEntpInfo2&dir=customer";
	$.dialog({
		title:  "选择企业",
		width:  770,
		height: 550,
        lock:true ,
		content:"url:"+url
	});
}


function getInputStockHistory(id){
	var url = "${ctx}/CsAjax.do?method=getInputStockHistory&comm_id=" + id + "&azaz=" + Math.random();
	$.dialog({
		title:  "查看进货历史",
		width:  850,
		height: 400,
        lock:true ,
		content:"url:"+url
	});
}


//]]></script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />

<style>
span.dot{ display:inline-block; *zoom:1; width:7px; height:4px; background-image:url(${ctx}/images/bgdown.png); background-position:left center; background-repeat:no-repeat; overflow:hidden; vertical-align:middle;}
</style>
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/OrderInfoModify">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
            <td nowrap="nowrap">修改时间从：
                <html-el:text property="st_add_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                至：
                <html-el:text property="en_add_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                &nbsp;
                <html-el:submit value="查 询" styleClass="bgButton" /></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
   <div style="padding-bottom:5px;"></div>
   <div style="text-align: left;padding: 5px;">
      <input type="button" name="add" id="add" class="bgButton" value="修改订单价格" onclick="location.href='OrderInfoModify.do?method=add&mod_id=${af.map.mod_id}';" />
   </div>
   <div style="padding-bottom:5px;"></div>
      
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
          <th width="4%">序号</th>
	      <th nowrap="nowrap">
	       <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass" id="hbtable">
               <tr>
               <th width="19%">订单号</th>
               <th width="15%">所属企业</th>
               <th width="25%" nowrap="nowrap">商品名称</th>
               <th width="5%">商品<br/>数量</th>
               <th width="10%">调整后商品<br/>总金额</th>
               <th width="10%">原商品<br/>总金额</th>               
               <th width="15%">订单状态</th>
               </tr>
           </table>
	      </th>
<!-- 	      <th width="6%">是否管理员修改</th> -->
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center">${vs.count}</td>
          <td>
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass" id="hbtable">
              
               <tr>
             <td align="center" class="hbtd" width="19%">
               ${fn:escapeXml(cur.trade_index)}
               <br/>
               <a title="收货地址和发票信息" class="cases" href="${ctx}/manager/entpm/ShippinInvoiceInfo.do?shipping_id=${cur.shipping_address_id}"><span>${fn:escapeXml(cur.rel_name)}</span></a>
               </td>
              <td align="left" class="hbtd2" width="15%"> <a title="企业信息" class="cases" href="${ctx}/manager/admin/EntpInfo.do?method=getEntpInfo&id=${cur.entp_id}"><span>${fn:escapeXml(cur.entp_name)}</span></a></td>
             <td>
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass" id="hbtable">
             <c:forEach var="cur3" items="${cur.map.orderInfoDetailsList}">
              <tr>
               <td align="left">${fn:escapeXml(fnx:abbreviate(cur3.comm_name, 40, '...'))}</td>
                <td align="center" width="10%">${cur3.good_count}</td>
                <td align="center" width="20%">${cur3.good_sum_price}
                <c:if test="${not empty (cur3.price_modify_remark)}">&nbsp;<span style="cursor: pointer;" class="dot" title="已备注订单修改"></span>
                <div style="padding-top: 5px;display:none"><span style=" color:#F00;">${fn:escapeXml(cur3.price_modify_remark)}</span></div>
                </c:if>
               </td>
                <td align="center" width="20%">
                 <c:if test="${empty cur3.price_modify_old}">
                ${cur3.good_sum_price}
                </c:if>
                <c:if test="${not empty cur3.price_modify_old}">
                ${cur3.price_modify_old}
                </c:if>
                </td>
              </tr>
              </c:forEach>
              
             <tr>
               <td align="center" colspan="2">订单总运费：</td>
               <td align="center" width="20%">${cur.matflow_price}
               <c:if test="${not empty (cur.price_modify_remark)}">&nbsp;<span style="cursor: pointer;" class="dot" title="已备注运费修改"></span>
                <div style="padding-top: 5px;display:none"><span style=" color:#F00;">${fn:escapeXml(cur.price_modify_remark)}</span></div>
               </c:if>
				</td>
               <td align="center" width="20%">
               <c:if test="${empty cur.matflow_price_modify_old}">
                ${cur.matflow_price}
                </c:if>
                <c:if test="${not empty cur.matflow_price_modify_old}">
                ${cur.matflow_price_modify_old}
                </c:if>
               </td>
              </tr>
              </table>
             </td>
              <td  width="15%"  align="center" nowrap="nowrap">
                <c:choose>
	            <c:when test="${cur.order_state eq -1}">
	              <c:out value="已取消" />
	            </c:when>
	            <c:when test="${cur.order_state eq 0}">
	              <c:out value="已预订" />
	            </c:when>
	            <c:when test="${cur.order_state eq 1}">
	              <c:out value="已付款" />
	            </c:when>
	            <c:when test="${cur.order_state eq 2}">
	              <c:out value="已发货" />
	            </c:when>
	            <c:when test="${cur.order_state eq 3}">
	              <c:out value="已到货" />
	            </c:when>
	            <c:when test="${cur.order_state eq 4}"> 已收货<br />
	              (<span style="color: green;">交易成功</span>) </c:when>
	            <c:when test="${cur.order_state eq 9}">
	              <c:out value="已关闭" />
	            </c:when>
	            <c:otherwise></c:otherwise>
	           </c:choose><br/>
	            &nbsp;<a class="cases butbase" href="OrderQuery.do?method=view&order_id=${cur.id}&mod_id=${af.map.mod_id}"><span class="icon-node">查看订单</span></a>
            </td>
               </tr>

              </table>
          </td>
<!--           <td align="center"> -->
<%--           <c:choose> --%>
<%--               <c:when test="${cur.map.is_admin eq 1}"><span style=" color:#47740C;">是 </span></c:when> --%>
<%--               <c:when test="${cur.map.is_admin ne 1}"><span style=" color:#F00;">否 </span></c:when> --%>
<%--          </c:choose> --%>
<!--           </td> -->
        </tr>
      </c:forEach>
    </table>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="OrderInfoModify.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("flow_type", "${af.map.flow_type}");
            pager.addHiddenInputs("type", "${af.map.type}");
            pager.addHiddenInputs("st_add_date", "${af.map.st_add_date}");
			pager.addHiddenInputs("en_add_date", "${af.map.en_add_date}");
            document.write(pager.toString());
            </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>

<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$(this).find("span.dot").toggle(function(){
		$(this).css("background-image","url(${ctx}/images/bgup.png)");
		$(this).next("div").slideDown();
	},function(){
		$(this).css("background-image","url(${ctx}/images/bgdown.png)");
		$(this).next("div").slideUp();
	});
	
	$("a.cases").colorbox({width:"80%", height:"80%", iframe:true});   
	
	//trMerge("hbtd", $("#hbtable"));
	//trMerge("hbtd2", $("#hbtable"));
});
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>

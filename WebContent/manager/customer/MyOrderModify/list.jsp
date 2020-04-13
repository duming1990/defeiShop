<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改价格- ${app_name}</title>
<meta content="${app_name}订单管理" name="keywords" />
<meta content="${app_name}订单" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body style="height:1500px;">
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
             <html-el:form action="/customer/MyOrderModify">
              <html-el:hidden property="method" value="list" />
              <html-el:hidden property="mod_id" />
              <html-el:hidden property="par_id" />
            <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
                <tr>
                  <td>
              	   &nbsp;订单编号：
                 <html-el:text property="trade_index"  styleClass="webinput" maxlength="50"/>
                    &nbsp;修改时间 从:
                    	<html-el:text property="st_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                    至：
                    <html-el:text property="en_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                    &nbsp;
                    <button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button>
                   </td>
                  </tr>
              </table>
            </html-el:form>
      <!-- search end -->
      <%@ include file="/commons/pages/messages.jsp" %>
<!--       <div class="all"> -->
<!--           <ul class="nav nav-tabs" id="nav_ul_content"> -->
<%--           <c:forEach var="cur" items="${orderTypeList}" varStatus="vs"> --%>
<%--           <c:set var="liClass" value="" /> --%>
<%--           <c:if test="${vs.count eq 1}"> --%>
<%--           <c:set var="liClass" value="active" /> --%>
<%--           </c:if>  --%>
<%--            <li data-type="${cur.index}" class="${liClass}"><a><span>${cur.name}</span></a></li> --%>
<%--            </c:forEach> --%>
<!--    		 </ul> -->
<!--         </div> -->
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
       <tr class="tite2">
          <th width="5%"><strong>序号</strong></th>
		  <th width="20%"><strong>订单号</strong></th>
          <th width="20%" nowrap="nowrap"><strong>商品名称</strong></th>
          <th width="10%"><strong>商品数量</strong></th>
          <th width="15%"><strong>调整后商品总金额</strong></th>
          <th width="15%"><strong>原商品总金额</strong></th>               
          <th width="15%"><strong>订单状态</strong></th>
      	</tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center"  width="5%">${vs.count}</td>
             <td align="center" class="hbtd" width="20%">
               ${fn:escapeXml(cur.trade_index)}<br/>
               </td>
             <td colspan="4">
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable" id="hbtable">
             <c:forEach var="cur3" items="${cur.map.orderInfoDetailsList}">
              <tr>
               <td align="left">${fn:escapeXml(fnx:abbreviate(cur3.comm_name, 40, '...'))}</td>
                <td align="center" width="17%">${cur3.good_count}</td>
                <td align="center" width="25%">${cur3.good_sum_price}
                <c:if test="${not empty (cur3.price_modify_remark)}">&nbsp;<span style="cursor: pointer;" class="dot" title="已备注订单修改"></span>
                <div style="padding-top: 5px;display:none"><span style=" color:#F00;">${fn:escapeXml(cur3.price_modify_remark)}</span></div>
                </c:if>
               </td>
                <td align="center" width="25%">
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
               <script type="text/javascript">showOrderState(${cur.order_state},${cur.pay_type},${cur.order_type})</script>
                <a href="../customer/MyOrderDetail.do?order_id=${cur.id}&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}&from=entp" class="label label-warning label-block">订单详情</a>
                <a href="MyOrderModify.do?method=check&trade_index=${cur.trade_index}&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}" class="label label-warning label-block">修改价格</a> 
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
        </tr>
      </c:forEach>
      
    </table>
	<div class="clear"></div>
    <div class="black">
      <form id="bottomPageForm" name="bottomPageForm" method="post" action="MyOrderModify.do">
        <table width="98%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
              <script type="text/javascript">
				var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
		        pager.addHiddenInputs("method", "list");
				pager.addHiddenInputs("st_date", "${af.map.st_date}");
				pager.addHiddenInputs("en_date", "${af.map.en_date}");
				pager.addHiddenInputs("trade_index", "${af.map.trade_index}");
				pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
		        pager.addHiddenInputs("par_id", "${af.map.par_id}");
		        pager.addHiddenInputs("order_type", "${af.map.order_type}");
		        document.write(pager.toString());
            	</script></td>
          </tr>
        </table>
      </form>
      </div>
    <div class="clear"></div>
  </div>
<!-- main end -->
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	//导航回显
	$("#nav_ul_content li").each(function(){
		if($(this).attr("data-type") ==  $("#order_type").val()){
			$(this).addClass("active").siblings().removeClass("active");
			return false;
		}
	});

	//导航跳转
	$("#nav_ul_content a").click(function(){ 
		 var type = $(this).parent().attr("data-type");
		 this.href= "${ctx}/manager/customer/MyOrderModify.do?method=list&order_type="+ type + "&" + $('#bottomPageForm').serialize();
	     this.target = "_self";
	});
	
	var f = document.forms[0];
	$("#btn_submit").click(function(){
		f.submit();
	});
});

//]]></script>

</body>
</html>
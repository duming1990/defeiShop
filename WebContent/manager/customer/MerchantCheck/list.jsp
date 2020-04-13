<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.butbase{
	background-color: #f0ad4e;
	display: inline;
    padding: .2em .5em .3em;
    font-size: 75%;
    line-height: 1;
    color: #fff !important;
    text-align: center;
    white-space: nowrap;
    vertical-align: baseline;
    border-radius: .25em;}
</style>
</head>
<body>
<div class="mainbox mine">
<jsp:include page="../_nav.jsp" flush="true"/>
<html-el:form action="/customer/MerchantCheck" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="entp_type" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td><div> 添加时间：
            从
            <html-el:text property="st_add_date" styleClass="webinput"  styleId="st_add_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
            至
            <html-el:text property="en_add_date" styleClass="webinput"  styleId="en_add_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
            &nbsp;审核状态：
            <html-el:select property="is_check" styleId="is_check">
           		<html-el:option value="">全部</html-el:option>
            	<html-el:option value="0">待结算</html-el:option>
	            <html-el:option value="1">结算成功</html-el:option>
	            <html-el:option value="-1">结算失败</html-el:option>
	            <html-el:option value="15">付款成功</html-el:option>
	            <html-el:option value="20">退款成功</html-el:option>
            </html-el:select>
             &nbsp;类型：
            <html-el:select property="order_type" >
              <html-el:option value="">全部</html-el:option>
              <html-el:option value="0">线上</html-el:option>
              <html-el:option value="90">线下</html-el:option>
            </html-el:select>
            &nbsp;<html-el:submit value="查 询" styleClass="bgButton"  />
          </div></td>
      </tr>
    </table>
  </html-el:form>
<%@ include file="/commons/pages/messages.jsp" %>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
  <tr>
  	<th width="5%">结算类型</th>
  	<th width="5%">订单金额</th>
    <th width="8%">货款</th>
    <th width="6%">结算金额</th>
    <th width="15%">企业名称</th>
    <th width="7%">开始时间</th>
    <th width="7%">结束时间</th>
    <th width="10%">添加时间</th>
    <th width="10%">审核备注</th>
    <th width="10%">审核时间</th>
    <th width="5%">审核状态</th>
    <th width="10%">操作</th>
  </tr>
  <c:forEach var="cur" items="${entityList}">
     	<tr>
     		<td align="center">
     			<c:if test="${cur.order_type eq 0}"><span style="color :red;">线上</span></c:if>
     			<c:if test="${cur.order_type ne 0}"> <span style="color :green;">线下</span> </c:if>
     		</td>
     		<td align="center">${fn:escapeXml(cur.sum_order_money)}</td>
     		<td align="center">${fn:escapeXml(cur.sum_money)}<br/> <span align="center" style="color: red">手续费：${fn:escapeXml(cur.cash_rate)}元</span></td>
     		<td align="center">${fn:escapeXml(cur.cash_pay)}</td>
     		<td align="left">${cur.entp_name}</td>
     		<fmt:formatDate var="add_check_date" value="${cur.add_check_date}" pattern ="yyyy-MM-dd HH:mm:ss"/>
     		<fmt:formatDate var="end_check_date" value="${cur.end_check_date}" pattern ="yyyy-MM-dd HH:mm:ss"/>
     		<fmt:formatDate var="add_date" value="${cur.add_date}" pattern ="yyyy-MM-dd HH:mm:ss"/>
     		<fmt:formatDate var="confirm_date" value="${cur.confirm_date}" pattern ="yyyy-MM-dd HH:mm:ss"/>
     		<td align="center">${fn:escapeXml(add_check_date)}</td>
     		<td align="center">${fn:escapeXml(end_check_date)}</td>
     		<td align="center">${fn:escapeXml(add_date)}</td>
     		<td align="center">${fn:escapeXml(cur.confirm_desc)}</td>
     		<td align="center">${fn:escapeXml(confirm_date)}</td>
     		<c:if test="${cur.is_check eq 0}">
     			<td align="center">待结算</td>
     		</c:if>
     		<c:if test="${cur.is_check eq -1}">
     			<td align="center" ><span style="color :red;">结算失败</span></td>
     		</c:if>
     		<c:if test="${cur.is_check eq 1}">
     			<td align="center"><span style="color :green;">结算成功</span></td>
     		</c:if>
            <c:if test="${cur.is_check eq 15}">
                <td align="center"><span style="color :green;">付款成功</span></td>
            </c:if>
            <c:if test="${cur.is_check eq 20}">
                <td align="center"><span style="color :green;">退款成功</span></td>
            </c:if>
     		<td align="center">
     			<a href="${ctx}/manager/customer/MerchantCheck.do?method=listDetails&mod_id=${af.map.mod_id}&id=${cur.id}" class="butbase beautybg" style="color: white;"><span class="icon-ok">查看详情</span></a>
     			&nbsp;&nbsp;&nbsp;
     		</td>
     	</tr>
     </c:forEach>
</table>
</div>
<div class="black">
  <form id="bottomPageForm" name="bottomPageForm" method="post" action="MerchantCheck.do">
    <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
      <tr>
        <td height="10" ><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
          <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, "${af.map.pager.recordCount}", '${af.map.pager.pageSize}', '${af.map.pager.currentPage}');
            pager.addHiddenInputs("method", "list");
			pager.addHiddenInputs("st_add_date", "${af.map.st_add_date}");
			pager.addHiddenInputs("en_add_date", "${af.map.en_add_date}");
			pager.addHiddenInputs("confirm_state", "${af.map.confirm_state}");
			pager.addHiddenInputs("is_check", "${af.map.is_check}");
			pager.addHiddenInputs("order_type", "${af.map.order_type}");
            document.write(pager.toString());
        </script></td>
      </tr>
    </table>
  </form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
<script type="text/javascript">
$("a.beautybg").colorbox({width:"90%", height:"80%", iframe:true});

function checkDZ(id){
	var flag = confirm("确定代表对账通过，取消代表对账不通过");  
    if(flag){  
    	$.ajax({
			type: "POST" , 
			url: "MerchantCheck.do", 
			data:"method=save&confirm_state=1&id="+id,
			dataType: "json", 
	        error: function (request, settings) {}, 
	        success: function (data) {
				if (data.code == 1) {
					alert("操作成功");
				} else {
					alert("操作失败，请联系管理员");
				}
				location.reload(true);
	        }
		});
    }else{  
    	$.ajax({
			type: "POST" , 
			url: "MerchantCheck.do", 
			data:"method=save&confirm_state=-1&id="+id,
			dataType: "json", 
	        error: function (request, settings) {}, 
	        success: function (data) {
				if (data.code == 1) {
					alert("操作成功");
				} else {
					alert("操作失败，请联系管理员");
				}
				location.reload(true);
	        }
		});
    }  
}
</script>
</body>
</html>

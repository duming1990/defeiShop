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
<html-el:form action="/customer/MerchantCheckApply" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
      
        <td>
        <div> <span style="color:red">*</span>最后结算时间：
<!--             从 -->
<%--             <html-el:text property="st_finish_date" styleClass="webinput" styleId="st_finish_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/> --%>
<!--             至 -->
            <html-el:text property="en_finish_date" styleClass="webinput" styleId="en_finish_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
            
            &nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${empty show}"><span style="color:red">请选择要结算最后结算</span></c:if>
            
            &nbsp;类型：
            <html-el:select property="order_type" >
              <html-el:option value="0">线上</html-el:option>
              <html-el:option value="90">线下</html-el:option>
            </html-el:select>
            
            &nbsp;<html-el:submit value="查 询" styleClass="bgButton" styleId="bgButton" />
            
          </div>
         </td>
      </tr>
    </table>
  </html-el:form>
<%@ include file="/commons/pages/messages.jsp" %>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
  	 <tr>
        <th width="20%">商家名</th>
        <th width="10%">订单总数量</th>
        <th width="10%">产品总数量</th>
        <th width="10%">订单总金额</th>
        <th width="10%">本次结算金额</th>
        <th width="10%">商家货款币</th>
        <th width="15%">结算</th>
      </tr>
	    <c:forEach var="cur" items="${entityList}">
	     	<tr>
	     		<td align="center">${fn:escapeXml(cur.map.entp_name)}</td>
	     		<td align="center">${fn:escapeXml(cur.map.count_entp_id)}</td>
	     		<td align="center">${fn:escapeXml(cur.map.sum_order_num)}</td>
	     		<td align="center"><fmt:formatNumber pattern="#0.00" value="${cur.map.sum_order_money}" /></td>
	     		<td align="center"><fmt:formatNumber pattern="#0.00" value="${cur.map.sum_huokuan}" /></td>
	     		<td align="center"><fmt:formatNumber pattern="#0.00" value="${cur.map.bi_huoKuang}"/></td>
	     		
	     		<td align="center">
	     			<a href="${ctx}/manager/customer/MerchantCheckApply.do?method=listDetails&mod_id=${af.map.mod_id}&st_finish_date=${af.map.st_finish_date}&en_finish_date=${af.map.en_finish_date}&order_type=${af.map.order_type}" class="butbase beautybg" style="color: white;"><span class="icon-ok">查看</span></a>
	     			<a class="butbase"><span onclick="doNeedMethod(null, 'MerchantCheckApply.do', 'startCheck', $('.searchForm').serialize())" class="icon-ok">结算</span></a>
	     		</td>
	     	</tr>
	     </c:forEach>
</table>
</div>
<div class="black">
  <form id="bottomPageForm" name="bottomPageForm" method="post" action="MerchantCheckApply.do">
    <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
      <tr>
        <td height="10" ><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
          <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, parseInt("${af.map.pager.recordCount}"),parseInt("${af.map.pager.pageSize}"), parseInt('${af.map.pager.currentPage}'));
            pager.addHiddenInputs("method", "list");
// 			pager.addHiddenInputs("st_finish_date", "${af.map.st_finish_date}");
			pager.addHiddenInputs("en_finish_date", "${af.map.en_finish_date}");
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

$("#bgButton").click(function(){
// 	var st=$("#st_finish_date").val();
	var en=$("#en_finish_date").val();
	if(!en){
		alert("最后结算时间必须填写");
		return false;
	}
});
</script>
</body>
</html>

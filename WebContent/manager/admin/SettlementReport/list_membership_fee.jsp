<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div style="width: 99%" class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/SettlementReport" styleClass="searchForm">
    <html-el:hidden property="method" value="listMembershipFee" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td>
            &nbsp;缴费时间从:
            <html-el:text property="st_pay_date" styleId="st_pay_date" size="16" maxlength="16" readonly="true" styleClass="webinput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" />
           	 至：
            <html-el:text property="en_pay_date" styleId="en_pay_date" size="16" maxlength="16" readonly="true" styleClass="webinput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
            &nbsp;缴费方式
          	<html-el:select property="pay_type" styleId="pay_type">
              <html-el:option value="">全部</html-el:option>
              <html-el:option value="0">后台升级</html-el:option>
              <html-el:option value="1">支付宝</html-el:option>
              <html-el:option value="3">微信支付</html-el:option>
              <html-el:option value="-9">人工升级</html-el:option>
            </html-el:select>
            &nbsp;
            <html-el:button value="查 询" styleClass="bgButton" property="btn_submit" styleId="btn_submit" />
            &nbsp;
           <html-el:button value="导 出" styleClass="bgButton" property="download" styleId="download" />
          </td>
      </tr>
    </table>
  </html-el:form>
  <form>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
	<tr>
        <th width="18%">会员名称</th>
        <th width="13%">手机号</th>
        <th width="8%">缴费时间</th>
        <th width="10%">缴费金额</th>
        <th width="10%">缴费方式</th>
      </tr>
     <c:forEach var="cur" items="${entityList}">
     	<tr>
     		<td align="center">${fn:escapeXml(cur.map.user_name)}</td>
     		<td align="center">${fn:escapeXml(cur.map.mobile)}</td>
     		<td align="center"><fmt:formatDate value="${cur.pay_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
     		<td align="center"><fmt:formatNumber value="${cur.order_money}" pattern="0.00"></fmt:formatNumber></td>
     		<td align="center">
     		    <c:if test="${empty cur.pay_type}">人工升级</c:if>
     			<c:if test="${cur.pay_type eq 0}">后台升级</c:if>
     			<c:if test="${(not empty cur.pay_type) and cur.pay_type ne 0}">
     			<c:forEach var="curPayType" items="${payTypeList}">
	              <c:if test="${curPayType.index eq cur.pay_type}">
	                ${curPayType.name}
	              </c:if>
	            </c:forEach>
	            </c:if>
       		</td>
     	</tr>
     </c:forEach>
	</table>
  </form>
 <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="SettlementReport.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "listMembershipFee");
           	pager.addHiddenInputs("st_pay_date", "${af.map.st_pay_date}");
           	pager.addHiddenInputs("en_pay_date", "${af.map.en_pay_date}");
           	pager.addHiddenInputs("pay_type", "${af.map.pay_type}");
            document.write(pager.toString());
            </script></td>
        </tr>
      </table>
    </form>
  </div>
 </div>
 <script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	var f = document.forms[0];
	$("#btn_submit").click(function(){
		var start_date = $("#st_pay_date").val();
		var end_date = $("#en_pay_date").val();
		if(end_date != ""){
			  if(start_date > end_date){
				  alert("时间选择有错误！");
				  return false;
			  }
			}
		f.submit();
	});
});
$("#download").click(function(){
	var submit = function (v, h, f) {
	    if (v == true) {
	    	location.href = "${ctx}/manager/admin/SettlementReport.do?method=toMemerExcel&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
	    } else {
	    	location.href = "${ctx}/manager/admin/SettlementReport.do?method=toMemerExcel&code=GBK&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
	    }
	    return true;
	};
	var tip = "确认导出EXCEL格式数据？如果UTF-8编码格式乱码，请选择GBK编码格式下载！";
	$.jBox.confirm(tip, "系统提示", submit, { buttons: { '下载(UTF-8编码)': true, '下载(GBK编码)': false} });
});
//]]></script>
</body>
</html>

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
  <html-el:form action="/admin/ServiceCenterTop" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td><!--            &nbsp; 商品名称： -->
          <%--             <html-el:text property="comm_name_like" styleClass="webinput" maxlength="50" style="width:200px;"/> --%>
          &nbsp;排序优先级：
          <html-el:select property="right_join" styleId="right_join">
            <html-el:option value="">合伙人优先</html-el:option>
            <html-el:option value="1">商家销售额优先 </html-el:option>
          </html-el:select>
<!--           &nbsp;  时间：(默认为总排名) -->
<%--           <html-el:text property="sereach_servecenter_date" styleClass="webinput"  styleId="sereach_servecenter_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM'});"/> --%>
           &nbsp;  时间：
                      从<html-el:text property="sereach_st_date" styleClass="webinput"  styleId="sereach_st_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
         -
          <html-el:text property="sereach_en_date" styleClass="webinput"  styleId="sereach_en_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
          &nbsp;
          <html-el:submit value="查 询" styleClass="bgButton" styleId="bgButton" />
          <!--            &nbsp; -->
          <%--            <html-el:button value="导 出" styleClass="bgButton" property="download" styleId="download" /> --%>
        </td>
      </tr>
    </table>
  </html-el:form>
  
  <html-el:form action="/admin/ServiceCenterTop" styleClass="searchForm">
    <html-el:hidden property="method" value="search" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td>
          &nbsp; 合伙人名称：
          <html-el:text property="service_name_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
          &nbsp;  时间：
                     从<html-el:text property="sereach_servecenter_st_date" styleClass="webinput"  styleId="sereach_servecenter_st_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
         -
          <html-el:text property="sereach_servecenter_en_date" styleClass="webinput"  styleId="sereach_servecenter_en_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
          &nbsp;
          <html-el:submit value="查 询" styleClass="bgButton" styleId="bgButton" />
        </td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr class="tite2">
        <th width="5%" nowrap="nowrap">排名</th>
        <th nowrap="20%">合伙人名称</th>
        <c:if test="${sum_count_xianshi eq 0}">
          <th nowrap="20%">所属区域</th>
        </c:if>
        <th nowrap="10%">总商家</th>
        <th width="10%">活动商家</th>
        <th width="10%">营业总额</th>
      </tr>
      <c:forEach var="cur" items="${PaiMing_list}" varStatus="vs">
        <tr align="center">
          <td align="center">${vs.count}</td>
          <c:if test="${empty cur.map.servicecenter_name}">
            <td class="tip-danger">无合伙人</td>
          </c:if>
          <c:if test="${not empty cur.map.servicecenter_name}">
            <td>${cur.map.servicecenter_name}</td>
          </c:if>
          <c:if test="${sum_count_xianshi eq 0}">
            <td>${cur.map.full_name}</td>
          </c:if>
          <c:if test="${empty cur.map.entp_sum_count and sum_count_xianshi eq 0}">
            <td class="tip-danger">暂无合伙人</td>
          </c:if>
          <c:if test="${empty cur.map.entp_sum_count and sum_count_xianshi eq 1}">
            <td>0</td>
          </c:if>
          <c:if test="${not empty cur.map.entp_sum_count}">
            <td>${cur.map.entp_sum_count}</td>
          </c:if>
          <c:if test="${empty cur.map.entp_count}">
            <td>0</td>
          </c:if>
          <c:if test="${not empty cur.map.entp_count}">
            <td>${cur.map.entp_count}</td>
          </c:if>
          <c:if test="${empty cur.map.entp_sale_money}">
            <td>0元</td>
          </c:if>
          <c:if test="${not empty cur.map.entp_sale_money}">
            <td>${cur.map.entp_sale_money}元</td>
          </c:if>
          <%--           <fmt:formatNumber pattern="#,##0.00" value="${cur.map.entp_sale_money}"/>元 --%>
        </tr>
      </c:forEach>
    </table>
  </form>
  <!--   <div class="pageClass"> -->
  <!--     <form id="bottomPageForm" name="bottomPageForm" method="post" action="ServiceCenterTop.do"> -->
  <!--       <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0"> -->
  <!--         <tr> -->
  <%--           <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> --%>
  <!--            <script type="text/javascript"> -->
  <%-- 			 var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage}); --%>
  <!-- 			 pager.addHiddenInputs("method", "list"); -->
  <%-- 			 pager.addHiddenInputs("comm_name_like", "${fn:escapeXml(af.map.comm_name_like)}"); --%>
  <%-- 			 pager.addHiddenInputs("orderDay", "${fn:escapeXml(af.map.orderDay)}"); --%>
  <%-- 			 pager.addHiddenInputs("saleCount", "${fn:escapeXml(af.map.saleCount)}"); --%>
  <%-- 			 pager.addHiddenInputs("own_entp_id", "${af.map.own_entp_id}"); --%>
  <%-- 			 pager.addHiddenInputs("entp_name", "${af.map.entp_name}"); --%>
  <%-- 			 pager.addHiddenInputs("mod_id", "${af.map.mod_id}"); --%>
  <!-- 			 document.write(pager.toString()); -->
  <!-- 	       </script></td> -->
  <!--         </tr> -->
  <!--       </table> -->
  <!--     </form> -->
  <!--   </div> -->
  <div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	 $("#download").click(function(){
			var submit = function (v, h, f) {
			    if (v == true) {
			    	location.href = "${ctx}/manager/admin/CommSaleTop.do?method=toExcel&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
			    } else {
			    	location.href = "${ctx}/manager/admin/CommSaleTop.do?method=toExcel&code=GBK&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
			    }
			    return true;
			};
			var tip = "确认导出EXCEL格式数据？如果UTF-8编码格式乱码，请选择GBK编码格式下载！";
			$.jBox.confirm(tip, "系统提示", submit, { buttons: { '下载(UTF-8编码)': true, '下载(GBK编码)': false} });
		});
	 
	 $("#saleCount").change(function(){
		var thisVal = $(this).val();
		if("" != thisVal && null != thisVal){
			if(thisVal == 1){
				$("#orderDay").val(0);
				$("#orderDay").attr("disabled",true);
			}else{
				$("#orderDay").removeAttr("disabled");
			}
		}
	 });
	 
	 <c:if test="${af.map.saleCount eq 1}">
	    $("#orderDay").val(0);
		$("#orderDay").attr("disabled",true);
	 </c:if>
});  

function openEntpChild(){
	
	var url = "${ctx}/BaseCsAjax.do?method=chooseEntpInfo&dir=admin";
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

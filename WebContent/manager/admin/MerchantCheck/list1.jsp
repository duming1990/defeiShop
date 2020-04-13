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
  <html-el:form action="/admin/MerchantCheck" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="entp_type" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td><div> 商家名：
            <html-el:text property="enty_name" maxlength="40" style="width:150px;" styleClass="webinput" />
          
          &nbsp;对账区间：
            从
            <html-el:text property="add_date_st" styleClass="webinput"  styleId="add_date_st" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
            至
            <html-el:text property="add_date_en" styleClass="webinput"  styleId="add_date_en" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
            &nbsp;<html-el:submit value="查 询" styleClass="bgButton"  />
			&nbsp;<input id="download" type="button" value="导出" class="bgButton" />
          </div></td>
      </tr>
    </table>
  </html-el:form>
  <div style="padding-bottom:5px;">
      <input type="button" name="add" id="add" class="bgButton" value="对 账" onclick="" />
    </div>
  <form>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
	<tr>
        <th width="8%">交易流水号</th>
        <th width="8%">商家ID</th>
        <th width="8%">商家名</th>
        <th width="8%">订单类型</th>
        <th width="8%">订单数量</th>
        <th width="6%">订单金额</th>
        <th width="6%">下单时间</th>
        <th width="10%">添加人</th>
      </tr>
     <c:forEach var="cur" items="${entityList}">
     	<tr>
     		<td align="center">${cur.trade_index}</td>
     		<td align="center">${cur.entp_id}</td>
     		<td align="center">${cur.entp_name}</td>
     		<td align="center">${cur.order_type}</td>
     		<td align="center">${cur.order_num}</td>
     		<td align="center">${cur.order_money}</td>
     		 <fmt:formatDate var="order_date" value="${cur.order_date}" pattern="yyyy-MM-dd HH:mm:ss" />
     		<td align="center">${order_date}</td>
     		<td align="center">${cur.add_user_name}</td>
     	</tr>
     </c:forEach>
</table>
  </form>
 <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="MerchantCheck.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
           // pager.addHiddenInputs("enty_name", "${af.map.enty_name}");
			//pager.addHiddenInputs("add_date_st", "${af.map.add_date_st}");
			//pager.addHiddenInputs("add_date_en", "${af.map.add_date_en}");
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
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
</body>
</html>

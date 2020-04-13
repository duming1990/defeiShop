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
  <form>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
	<tr>
        <th width="18%">交易流水号</th>
        <th width="13%">商家名</th>
        <th width="8%">产品数量</th>
        <th width="10%">订单金额</th>
        <th width="10%">订单货款</th>
        <th width="15%">下单时间</th>
        <th width="12%">下单人</th>
      </tr>
     <c:forEach var="cur" items="${entityList}">
     	<tr>
     		<td align="center"><a href="OrderQuery.do?method=view&amp;mod_id=${af.map.mod_id}&amp;order_id=${cur.id}">${cur.trade_index}</a></td>
     		<td align="center">${fn:escapeXml(cur.entp_name)}</td>
     		<td align="center">${cur.order_num}</td>
     		<td align="center"><fmt:formatNumber value="${cur.order_money+cur.money_bi}" pattern="0.00"></fmt:formatNumber></td>
     		<td align="center"><fmt:formatNumber value="${cur.entp_huokuan_bi}" pattern="0.00"></fmt:formatNumber></td>
     		 <fmt:formatDate var="order_date" value="${cur.order_date}" pattern="yyyy-MM-dd HH:mm:ss" />
     		<td align="center">${order_date}</td>
     		<td align="center">${fn:escapeXml(cur.add_user_name)}</td>
     	</tr>
     </c:forEach>
</table>
  </form>
 <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="OrderInfoReport.do?method=reportOrderList">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "listDetails");
           	pager.addHiddenInputs("st_order_date_fmt", "${af.map.st_order_date_fmt}");
           	pager.addHiddenInputs("en_order_date_fmt", "${af.map.en_order_date_fmt}");
           	pager.addHiddenInputs("st_pay_date_fmt", "${af.map.st_pay_date_fmt}");
           	pager.addHiddenInputs("en_pay_date_fmt", "${af.map.en_pay_date_fmt}");
        	pager.addHiddenInputs("own_entp_id", "${af.map.own_entp_id}");
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

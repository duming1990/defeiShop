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
        <th width="8%">订单数量</th>
        <th width="10%">订单金额</th>
        <th width="15%">交易完成时间</th>
        <th width="12%">下单人</th>
      </tr>
     <c:forEach var="cur" items="${entityList}">
     	<tr>
     		<td align="center">${cur.trade_index}</td>
     		<td align="center">${cur.entp_name}</td>
     		<td align="center">${cur.order_num}</td>
     		<td align="center">${cur.order_money}</td>
     		 <fmt:formatDate var="_finish_date" value="${cur.finish_date}" pattern="yyyy-MM-dd HH:mm:ss" />
     		<td align="center">${_finish_date}</td>
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
            pager.addHiddenInputs("method", "listDetails");
           	pager.addHiddenInputs("entp_id", "${af.map.entp_id}");
			pager.addHiddenInputs("st_finish_date", "${af.map.st_finish_date}");
			pager.addHiddenInputs("en_finish_date", "${af.map.en_finish_date}");
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

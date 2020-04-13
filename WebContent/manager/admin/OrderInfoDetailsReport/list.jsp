<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/OrderInfoDetailsReport" styleClass="searchForm">
  <html-el:hidden property="method" value="list" />
  <html-el:hidden property="mod_id" />
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
    <tr>
      <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
          <tr>
            <td width="6%" nowrap="nowrap">支付方式：
		         <html-el:select property="pay_type" styleId="pay_type" styleClass="webinput">
		           <html-el:option value="">全部</html-el:option>
		           <c:forEach var="curPayType" items="${payTypes}">
		             <html-el:option value="${curPayType.index}">${curPayType.name}</html-el:option>
		           </c:forEach>
		         </html-el:select>
              	  &nbsp;订单状态：
		          <html-el:select property="order_state" styleId="order_state" styleClass="webinput">
		            <html-el:option value="">全部</html-el:option>
		            <html-el:option value="10">等待发货</html-el:option>
		            <html-el:option value="20">已发货</html-el:option>
		            <html-el:option value="40">交易成功</html-el:option>
		            <html-el:option value="90">关闭</html-el:option>
		          </html-el:select>
              	  &nbsp;订单类型：
		          <html-el:select property="is_fuxiao" styleId="is_fuxiao" styleClass="webinput">
		            <html-el:option value="">全部</html-el:option>
		            <html-el:option value="1">重消单</html-el:option>
		            <html-el:option value="0">新单</html-el:option>
		          </html-el:select>
		          <br />
		          商品名称：<html-el:text property="comm_name_like" styleId="comm_name_like" style="width:120px;" styleClass="webinput" />
		          &nbsp;用户名：<html-el:text property="add_user_name_like" styleId="add_user_name_like" style="width:120px;" styleClass="webinput" />
                &nbsp;支付时间 从：
                <html-el:text property="st_pay_date" styleId="st_pay_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                	至：
                <html-el:text property="en_pay_date" styleId="en_pay_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                &nbsp;<html-el:submit value="查 询" styleClass="bgButton" styleId="bgButton" />
                &nbsp;<input id="download" type="button" value="导出" class="bgButton" />
                </td>
          </tr>
        </table></td>
    </tr>
  </table>
</html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="OrderInfoDetailsReport.do?method=delete">
    <div style="padding-bottom:5px;">
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%">序号</th>
        <th width="7%">状态</th>
        <th width="7%">付款状态</th>
        <th width="5%">单类</th>
        <th width="7%">订单号</th>
        <th nowrap="nowrap">商品名称</th>
        <th width="7%">用户名</th>
        <th width="7%">下单时间</th>
        <th width="7%">商品金额</th>
        <th width="7%">余额抵扣</th>
        <th width="7%">应付金额</th>
        <th width="5%">单数</th>
        <th width="7%">支付时间</th>
        <th width="7%">支付渠道</th>
        <th width="5%">支付金额</th>
        <th width="5%">返佣次数</th>
        <th width="5%">已发推广奖</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center">${vs.count}</td>
          <td align="center"><c:forEach items="${orderStates}" var="keys"><c:if test="${cur.map.order_state eq  keys.index}">${keys.name}</c:if></c:forEach></td>
          <td align="center">${cur.map.is_pay_name}</td>
          <td align="center">${cur.map.is_fuxiao eq 1?'重消单':'新单'}</td>
          <td align="center">${cur.map.trade_index}</td>
          <td align="center">${cur.map.comm_name}</td>
          <td align="center">${cur.map.add_user_name}</td>
          <td align="center"><fmt:formatDate value="${cur.map.add_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
          <td align="center"><fmt:formatNumber pattern="0" value="${cur.map.good_price}" /></td>
          <td align="center"><fmt:formatNumber pattern="0" value="${cur.map.bi_money}" /></td>
		  <td align="center"><fmt:formatNumber pattern="0" value="${cur.map.yf_money}" /></td>
		  <td align="center"><fmt:formatNumber pattern="0" value="${cur.map.hz_count}" /></td>
		  <td align="center"><fmt:formatDate value="${cur.map.pay_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
          <td align="center"><c:forEach items="${payTypes}" var="keys"><c:if test="${cur.map.pay_type eq  keys.index}">${keys.name}</c:if></c:forEach></td>
          <td align="center"><fmt:formatNumber pattern="0" value="${cur.map.zf_money}" /></td>
          <td align="center"><c:if test="${cur.map.is_fuxiao eq 1}">-</c:if><c:if test="${cur.map.is_fuxiao eq 0}"><fmt:formatNumber pattern="0" value="${cur.map.fy_count}" /></c:if></td>
          <td align="center"><c:if test="${cur.map.is_tg gt 0}">已发</c:if><c:if test="${cur.map.is_tg le 0}">未发</c:if></td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="OrderInfoDetailsReport.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}"); 
			pager.addHiddenInputs("st_pay_date", "${fn:escapeXml(af.map.st_pay_date)}");
            pager.addHiddenInputs("en_pay_date", "${fn:escapeXml(af.map.en_pay_date)}");
            pager.addHiddenInputs("pay_type", "${fn:escapeXml(af.map.pay_type)}");
            pager.addHiddenInputs("order_state", "${fn:escapeXml(af.map.order_state)}");
            pager.addHiddenInputs("is_fuxiao", "${fn:escapeXml(af.map.is_fuxiao)}");
            pager.addHiddenInputs("add_user_name_like", "${fn:escapeXml(af.map.add_user_name_like)}");
            pager.addHiddenInputs("comm_name_like", "${fn:escapeXml(af.map.comm_name_like)}");
            document.write(pager.toString());
            </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>

<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
     $("#download").click(function(){
		
		var submit = function (v, h, f) {
		    if (v == true) {
		    	location.href = "${ctx}/manager/admin/OrderInfoDetailsReport.do?method=toExcel&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
		    } else {
		    	location.href = "${ctx}/manager/admin/OrderInfoDetailsReport.do?method=toExcel&code=GBK&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
		    }
		    return true;
		};
		var tip = "确认导出EXCEL格式数据？如果UTF-8编码格式乱码，请选择GBK编码格式下载！";
		$.jBox.confirm(tip, "系统提示", submit, { buttons: { '下载(UTF-8编码)': true, '下载(GBK编码)': false} });
	});
});

//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<style type="">
.flexigrid {
    margin-top: 10px;
    position: relative;
    z-index: 1;
    overflow: hidden;
}
.flexigrid .bDiv {
    min-height: 300px;
    position: relative;
    z-index: 2;
    height: auto;overflow:auto;overflow-y:hidden;
}
</style>
</head>
<body>
<div style="width: 99%" class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/OrderBillQuery" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td nowrap="nowrap">
                &nbsp;订单遍号：
                <html-el:text property="trade_index_like" maxlength="25" style="width:140px;" styleClass="webinput" />  
                &nbsp;&nbsp;用户名：
                <html-el:text property="add_user_name" maxlength="40" style="width:100px;" styleClass="webinput" />      
            	&nbsp;&nbsp;支付方式：
                <html-el:select property="pay_type" styleId="pay_type" styleClass="webinput">
                  <html-el:option value="">请选择...</html-el:option>
                  <c:forEach var="curPayType" items="${payTypeList}">
                    <html-el:option value="${curPayType.index}">${curPayType.name}</html-el:option>
                  </c:forEach>
                </html-el:select>
                &nbsp;下单时间 从：
                <html-el:text property="st_add_date" styleId="st_add_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker()" />
                至：
                <html-el:text property="en_add_date" styleId="en_add_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker()" />
                &nbsp;
                <input type="submit" class="bgButton" value="查 询" />
                &nbsp;
                <input id="download" type="button" value="导出" class="bgButton" />
                </td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="OrderBillQuery.do?method=delete">
    <div class="flexigrid">
      <div class="bDiv">
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
          <tr>
            <th width="5%" nowrap="nowrap">序号</th>
            <th nowrap="nowrap">订单编号</th>
            <th nowrap="nowrap">用户名</th>
            <th nowrap="nowrap">支付类型</th>
            <th nowrap="nowrap">订单金额</th>
            <th nowrap="nowrap">返现奖励</th>
            <th nowrap="nowrap">扶贫金</th>
            <th nowrap="nowrap">商家货款</th>
            <th nowrap="nowrap">公司利润</th>
            <th nowrap="nowrap">异常说明</th>
            <th nowrap="nowrap">下单时间</th>
          </tr>
          <c:forEach var="cur" items="${entityList}" varStatus="vs">
            <tr>
              <td align="center" nowrap="nowrap">${vs.count}</td>
              <td nowrap="nowrap" align="center">${cur.trade_index}</td>
              <td nowrap="nowrap" align="center">${cur.add_user_name}</td>
              <td align="center" nowrap="nowrap"><c:forEach items="${payTypeList}" var="keys">
                  <c:if test="${cur.pay_type eq keys.index}">${keys.name}</c:if>
                </c:forEach>
              </td>
              <!--订单金额 -->
              <td align="center" nowrap="nowrap"><fmt:formatNumber pattern="0.########" value="${cur.no_dis_money}" /></td>
              <!--返现奖励 -->
              <td align="center" nowrap="nowrap"><fmt:formatNumber pattern="0.########" value="${cur.reward_money}" /></td>
              <!--扶贫金 -->
              <td align="center" nowrap="nowrap"><fmt:formatNumber pattern="0.########" value="${cur.xiadan_user_sum - cur.res_balance}" /></td>
              <!--商家待返 -->
              <td align="center" nowrap="nowrap"><fmt:formatNumber pattern="0.########" value="${cur.entp_huokuan_bi}" /></td>
              <!-- 公司毛利 -->
              <td align="center"nowrap="nowrap">
                <fmt:formatNumber pattern="0.########" value="${cur.order_money + cur.money_bi - cur.reward_money  - (cur.xiadan_user_sum - cur.res_balance) - cur.entp_huokuan_bi}" />
              </td>
                <td align="center"nowrap="nowrap">
                  <c:if test="${not empty cur.matflow_price_modify_old}">
             		 修改前运费为：${cur.matflow_price_modify_old}
             	 </c:if>
             	  <c:if test="${not empty cur.price_modify_old}"></br>
             		 修改前订单价格为：${cur.price_modify_old}
             	 </c:if>
             	  <c:if test="${empty cur.price_modify_old and empty cur.matflow_price_modify_old }">
             		--
             	 </c:if>
              </td>
              <td align="center"nowrap="nowrap">
              <fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm" />
              </td>
            </tr>
          </c:forEach>
        </table>
      </div>
    </div>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="OrderBillQuery.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("add_user_name", "${fn:escapeXml(af.map.add_user_name)}");
			pager.addHiddenInputs("st_add_date", "${af.map.st_add_date}");
			pager.addHiddenInputs("en_add_date", "${af.map.en_add_date}");
			pager.addHiddenInputs("add_user_name_like", "${af.map.add_user_name_like}");
			pager.addHiddenInputs("trade_index_like", "${af.map.trade_index_like}");
			pager.addHiddenInputs("pay_type", "${af.map.pay_type}");
			pager.addHiddenInputs("order_type", "${af.map.order_type}");
            document.write(pager.toString());
            </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];

$(document).ready(function(){
	
	
	$("#download").click(function(){
		
		var submit = function (v, h, f) {
		    if (v == true) {
		    	location.href = "${ctx}/manager/admin/OrderBillQuery.do?method=toExcel&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
		    } else {
		    	location.href = "${ctx}/manager/admin/OrderBillQuery.do?method=toExcel&code=GBK&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
		    }
		    return true;
		};
		var tip = "确认导出EXCEL格式数据？如果UTF-8编码格式乱码，请选择GBK编码格式下载！";
		$.jBox.confirm(tip, "系统提示", submit, { buttons: { '下载(UTF-8编码)': true, '下载(GBK编码)': false} });
	});
	
});

// function getRewardInfo(link_id,order_type){
	
// 	$.dialog({
// 		title:  "查看实发金额明细",
// 		width:  800,
// 		height: 600,
//         lock:true ,
// 		content:"url:${ctx}/manager/admin/OrderBillQuery.do?method=getRewardInfo&order_id=" + link_id + "&order_type=" + order_type
// 	});
	
// }


//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>

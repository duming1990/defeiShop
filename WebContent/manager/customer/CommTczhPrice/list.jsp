<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>进货管理 - ${app_name}</title>
<meta content="${app_name}会员中心关键字" name="keywords" />
<meta content="${app_name}会员中心介绍" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/CommTczhPrice" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="mod_id" />  
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td>          
         <div style="margin-top: 5px;">规格条形码：
            <html-el:text property="comm_barcode_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
        	&nbsp;&nbsp;商品名称：
            <html-el:text property="comm_name_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
            &nbsp;进货时间： 从&nbsp;
            <html-el:text property="begin_date" styleId="begin_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker()" />
               	&nbsp;至&nbsp;
               <html-el:text property="end_date" styleId="end_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker()" />
            &nbsp;<html-el:checkbox property="critical" styleId="critical" styleClass="webinput"  style="vertical-align:inherit;" value="1" />
              <label for="critical">库存达到预警值的商品</label>
            &nbsp;&nbsp;
            <button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button>
            <input id="download" type="button" value="导出" class="bgButton" />
          </div></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="CommTczhPrice.do?method=delete">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr class="tite2">
        <th width="5%">序号</th>
        <th nowrap="nowrap">商品名称</th>
        <th width="12%">商品条形码</th>
        <th width="12%">规格条形码</th>
        <th width="8%">进货价</th>
        <th width="8%">售价</th>
        <th width="10%">商品库存</th>
        <th width="10%">进货时间</th>
        <th width="10%">库存预警值</th>
        <th width="10%">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <c:set var="background_color" value="" />
        <c:if test="${cur.early_warning_value gt cur.inventory}">
           <c:set var="background_color" value="background-color:#e6a4a4;" />
        </c:if>
        <tr align="center" style="${background_color}">
          <td>${vs.count}</td>
          <td align="left">${fn:escapeXml(cur.map.comm_name)}</td>
          <td>${fn:escapeXml(cur.map.barcode)}</td>
          <td>${fn:escapeXml(cur.comm_barcode)}</td>
          <td align="center"><fmt:formatNumber pattern="￥#,##0.00" value="${cur.cost_price}" /></td>
          <td align="center"><fmt:formatNumber pattern="￥#,##0.00" value="${cur.comm_price}" /></td>
          <td align="center">${cur.inventory}</td>
           <td><fmt:formatDate value="${cur.update_date}" pattern="yyyy-MM-dd HH:mm" /></td>
          <td align="center">${cur.early_warning_value}</td>
          <td>
	          <a class="label label-warning label-block" id="view" onclick="getListStockDetail('${cur.id}')"><span class="icon-edit">查看历史</span></a>
	          <a class="label label-danger label-block" id="remove" onclick="createEarlyWarningValue('${cur.id}')"><span class="icon-remove">预警值设置</span></a>
          </td>
        </tr>
        <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
       
      </c:forEach>
    </table>
  </form>
  <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="CommTczhPrice.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "list");
					pager.addHiddenInputs("comm_barcode_like", "${fn:escapeXml(af.map.comm_barcode_like)}");
					pager.addHiddenInputs("comm_name_like", "${fn:escapeXml(af.map.comm_name_like)}");
					pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
			        pager.addHiddenInputs("par_id", "${af.map.par_id}");
			        pager.addHiddenInputs("begin_date", "${af.map.begin_date}");
			        pager.addHiddenInputs("end_date", "${af.map.end_date}");			
			        pager.addHiddenInputs("critical", "${af.map.critical}");			
					document.write(pager.toString());
	            	</script>
	      </td>
        </tr>
      </table>
    </form>
  </div>
  <div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$(".qtip").quicktip();
});

$("#download").click(function(){
	
	var submit = function (v, h, f) {
	    if (v == true) {
	    	location.href = "${ctx}/manager/customer/CommTczhPrice.do?method=toExcel&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}&" + $('.searchForm').serialize();
	    } else {
	    	location.href = "${ctx}/manager/customer/CommTczhPrice.do?method=toExcel&code=GBK&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}&" + $('.searchForm').serialize();
	    }
	    return true;
	};
	var tip = "确认导出EXCEL格式数据？如果UTF-8编码格式乱码，请选择GBK编码格式下载！";
	$.jBox.confirm(tip, "系统提示", submit, { buttons: { '下载(UTF-8编码)': true, '下载(GBK编码)': false} });
});

function getListStockDetail(attr_id) {
	var url = "${ctx}/manager/customer/CommStockInfo.do?method=listDetails&attr_id="+attr_id;
	$.dialog({
		title:  "商品进货细节",
		width:  1200,
		height: 600,
        lock:true ,
        zIndex:"10000",
		content:"url:"+url
	});
};
function createEarlyWarningValue(id){
	var url = "${ctx}/manager/customer/CommTczhPrice.do?method=createEarlyWarningValue&id=" + id;
	$.dialog({
		title:  "设置库存预警值",
		width:500,
		height:450,
		padding: 0,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url)
	});
}
function refreshPage(){
	window.location.reload();
}
//]]></script>
</body>
</html>

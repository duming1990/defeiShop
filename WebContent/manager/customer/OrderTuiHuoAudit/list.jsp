<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的订单- ${app_name}</title>
<meta content="${app_name}订单管理" name="keywords" />
<meta content="${app_name}订单" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body style="height:2500px;">
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/MyOrderEntp">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="order_type" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td>商品名称：
          <html-el:text property="comm_name_like" styleClass="webinput" maxlength="50" style="width:120px;"/>
          &nbsp;订单编号：
          <html-el:text property="trade_index"  styleClass="webinput" maxlength="50" style="width:120px;"/>
          &nbsp; 订单状态：
          <html-el:select property="order_state" styleId="order_state" styleClass="webinput">
            <html-el:option value="">请选择...</html-el:option>
            <c:forEach var="curOrderState" items="${orderStateList}">
              <html-el:option value="${curOrderState.index}">${curOrderState.name}</html-el:option>
            </c:forEach>
          </html-el:select>
          <div style="margin:5px 0 5px 0;"> 下单人姓名：
            <html-el:text property="add_user_name_like" maxlength="20" style="width:70px;" styleClass="webinput" />
            &nbsp;下单人手机号：
            <html-el:text property="add_user_mobile_like" maxlength="20" style="width:80px;" styleClass="webinput" />
            &nbsp;收货人姓名：
            <html-el:text property="rel_name_like" maxlength="20" style="width:70px;" styleClass="webinput" />
            &nbsp;收货人电话：
            <html-el:text property="rel_phone_like" maxlength="20" style="width:80px;" styleClass="webinput" />
          </div>
          下单时间 从:
          <html-el:text property="st_date" styleId="st_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
          至：
          <html-el:text property="en_date" styleId="en_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
          &nbsp;
          &nbsp; 发票状态：
          <html-el:select property="fp_state" styleId="fp_state" styleClass="webinput">
            <html-el:option value="">请选择...</html-el:option>
              <html-el:option value="0">不需要发票</html-el:option>
              <html-el:option value="1">发票未寄送</html-el:option>
              <html-el:option value="2">发票已寄送</html-el:option>
          </html-el:select>
          <button class="bgButtonFontAwesome" type="button" id="btn_submit">
          <i class="fa fa-search"></i>查 询</button>
          <button class="bgButtonFontAwesome" type="button" id="download">
导出</button></td>
          
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="MyOrderEntp.do?method=delete">
    <div class="all">
      <ul class="nav nav-tabs" id="nav_ul_content">
        <c:forEach var="cur" items="${orderTypeList}" varStatus="vs">
          <c:set var="liClass" value="" />
          <c:if test="${vs.count eq 1}">
            <c:set var="liClass" value="active" />
          </c:if>
          <li data-type="${cur.index}" class="${liClass}"><a>
            <c:set var="orderName" value="${cur.name}" />
            <c:if test="${cur.index eq 10}">
              <c:set var="orderName" value="商品订单" />
            </c:if>
            <span>${orderName}</span></a></li>
        </c:forEach>
      </ul>
    </div>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable">
      <tr>
        <th nowrap="nowrap" align="center"><strong>订单信息</strong></th>
        <th width="10%" align="center"><strong>收货人</strong></th>
        <th width="10%" align="center"><strong>订单金额</strong></th>
        <th width="10%" align="center"><strong>支付方式</strong></th>
        <th width="10%" align="center"><strong>订单状态</strong></th>
        <th width="10%" align="center"><strong>操作</strong></th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td colspan="6" style="background: #e6e6e6;"> ${vs.count}、
            &nbsp;订单编号：${cur.trade_index}&nbsp;&nbsp;下单时间：
            <fmt:formatDate value="${cur.order_date}" pattern="yyyy-MM-dd HH:mm:ss" />
          </td>
        </tr>
        <tr>
          <td align="left"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable">
              <c:set var="isPd" value="false" />
              <c:forEach var="oidsList" varStatus="vs1" items="${cur.orderInfoDetailsList}">
                <tr>
                  <td>${oidsList.comm_name}
                    <c:if test="${not empty oidsList.comm_tczh_name}"> &nbsp;[${oidsList.comm_tczh_name}] </c:if>
                    <c:if test="${oidsList.fp_state eq 2}"><img  src="${ctx}/styles/images/fapiao.png" title="发票已寄送" class="fapiao"/></c:if>
                    <c:if test="${oidsList.fp_state eq 1}"><img src="${ctx}/styles/images/no_fapiao.png" title="发票未寄送" class="fapiao"/></c:if>
                  </td>
                  <td width="20%" align="center"><c:if test="${not empty oidsList.good_price}"> ￥
                      <fmt:formatNumber value="${oidsList.good_price}" pattern="0.##" />
                    </c:if>
                    <c:if test="${empty oidsList.good_price}">-</c:if>
                  </td>
                  <td width="12%" align="center">${oidsList.good_count}</td>
                </tr>
              </c:forEach>
            </table></td>
          <td align="center">${cur.rel_name} <br/>
          <c:if test="${empty cur.rel_phone}">
          --
          </c:if>
          <c:if test="${not empty cur.rel_phone}">
          (${cur.rel_phone})
          </c:if>
          </td>
          <td align="center" nowrap="nowrap">￥<fmt:formatNumber value="${cur.order_money}" pattern="0.##"/>
            <br/>
            (运费:￥
            <fmt:formatNumber value="${cur.matflow_price}" pattern="0.##"/>
            ) </td>
          <td align="center"><c:forEach var="curPayType" items="${payTypeList}">
              <c:if test="${curPayType.index eq cur.pay_type}">
                <c:if test="${cur.order_type eq 30}">
                  <c:if test="${cur.pay_type ne 0}">${curPayType.name}</c:if>
                  <c:if test="${cur.pay_type eq 0}">-</c:if>
                </c:if>
                <c:if test="${cur.order_type ne 30}">${curPayType.name}</c:if>
              </c:if>
            </c:forEach>
          </td>
          <td align="center" nowrap="nowrap" id="order_state_${cur.id}" title="审核说明：${cur.map.orderAudit.audit_desc}" data-order-state="${cur.order_state}" data-pay-type="${cur.pay_type}" data-order-type="${cur.order_type}"><script type="text/javascript">showOrderState(${cur.order_state},${cur.pay_type},${cur.order_type})</script>
            <div>
              <c:if test="${cur.is_ziti eq 1}" var="isziti"><span class="tip-success qtip" title="用户自提订单"><i class="fa fa-shopping-basket"></i>自提</span></c:if>
            </div>
            <div>
              <c:if test="${not empty cur.remark}"><span class="tip-success qtip" title="买家留言：${fn:escapeXml(cur.remark)}"><i class="fa fa-wechat"></i>买家留言</span></c:if>
            </div>
            </td>
          <td align="center">
          <a class="label label-info label-block" href="../customer/MyOrderDetail.do?order_id=${cur.id}&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}&from=entp">订单详情</a>
          <c:if test="${cur.map.orderAudit.audit_state eq -1 and cur.order_type eq 40}">
		   <a class="label label-warning label-block" id="edit" onclick="confirmUpdate('null', 'MyOrderEntp.do', 'order_id=${cur.id}&par_id=${af.map.par_id}&mod_id=${af.map.mod_id}&' + $('#bottomPageForm').serialize())"><span id="${cur.id}" class="icon-edit">修改订单</span></a>
          </c:if>
            <c:choose>
              <c:when test="${cur.order_state eq 10}">
                <c:if test="${cur.is_ziti eq 1}"> <a class="label label-success label-block" href="javascript:void(0);" onclick="orderConfirm('${cur.id}');">订单确认</a> </c:if>
                <c:if test="${((cur.order_type eq 11) or (cur.order_type eq 10) or (cur.order_type eq 140)) and (cur.is_ziti eq 0)}"> <a class="label label-success label-block" href="javascript:void(0);" onclick="orderFh('${cur.id}');">发货</a> </c:if>
              </c:when>
            </c:choose>
            <c:if test="${cur.order_state eq -10}"><a class="label label-danger label-block" href="javascript:void(0);" onclick="confirmDelete('MyOrderEntp.do', 'delete', 'id=${cur.id}&'+ $('#bottomPageForm').serialize());">删除</a> </c:if>
            <c:if test="${cur.order_state ge 10 && cur.fp_state ne 0}"><a class="label label-success label-block" href="javascript:void(0);" onclick="orderFP('${cur.id}');">寄送发票</a> </c:if>
          </td>
        </tr>
        <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
      </c:forEach>
      <c:forEach begin="${i}" end="${af.map.pager.pageSize - 1}">
        <tr align="center">
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="${ctx}/manager/customer/MyOrderEntp.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
				var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
		        pager.addHiddenInputs("method", "list");
				pager.addHiddenInputs("comm_name_like", "${af.map.comm_name_like}");
				pager.addHiddenInputs("st_date", "${af.map.st_date}");
				pager.addHiddenInputs("en_date", "${af.map.en_date}");
				pager.addHiddenInputs("order_state", "${af.map.order_state}");
				pager.addHiddenInputs("fp_state", "${af.map.fp_state}");
				pager.addHiddenInputs("trade_index", "${af.map.trade_index}");
				pager.addHiddenInputs("order_type", "${af.map.order_type}");
				pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
		        pager.addHiddenInputs("par_id", "${af.map.par_id}");
				pager.addHiddenInputs("rel_name_like", "${af.map.rel_name_like}");
				pager.addHiddenInputs("rel_phone_like", "${af.map.rel_phone_like}");
				pager.addHiddenInputs("add_user_name_like", "${af.map.add_user_name_like}");
				pager.addHiddenInputs("add_user_mobile_like", "${af.map.add_user_mobile_like}");
		        document.write(pager.toString());
            	</script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<div class="clear"></div>
<!-- main end -->
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/scripts/order/order.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	$("#download").click(function(){
		
		var submit = function (v, h, f) {
		    if (v == true) {
		    	location.href = "${ctx}/manager/customer/MyOrderEntp.do?method=toExcel&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
		    } else {
		    	location.href = "${ctx}/manager/customer/MyOrderEntp.do?method=toExcel&code=GBK&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
		    }
		    return true;
		};
		var tip = "确认导出EXCEL格式数据？如果UTF-8编码格式乱码，请选择GBK编码格式下载！";
		$.jBox.confirm(tip, "系统提示", submit, { buttons: { '下载(UTF-8编码)': true, '下载(GBK编码)': false} });
	});
	
	
	$(".qtip").quicktip();
	//导航回显
	$("#nav_ul_content li").each(function(){
		if($(this).attr("data-type") ==  $("#order_type").val()){
			$(this).addClass("active").siblings().removeClass("active");
			return false;
		}
	});

	//导航跳转
	$("#nav_ul_content a").click(function(){ 
		 var type = $(this).parent().attr("data-type");
		 this.href= "${ctx}/manager/customer/MyOrderEntp.do?method=list&order_type="+ type + "&" + $('#bottomPageForm').serialize();
	     this.target = "_self";
	});
});


var f = document.forms[0];
$("#btn_submit").click(function(){
	var start_date = $("#st_date").val();
	var end_date = $("#en_date").val();
	if(end_date != ""){
		  if(start_date > end_date){
			  alert("下单时间选择有错误！");
			  return false;
		  }
		}
	f.submit();
});

function orderFP(id) { 
	var title = "寄送发票";
	var url = "${ctx}/manager/customer/MyOrderEntp.do?method=orderFP&order_id=" + id ;
	$.dialog({
		title:  title,
		width:  600,
		height: 450,
		max: false,
        min: false,
        fixed: true,
        lock: true,
        zIndex:100,
		content:"url:"+ encodeURI(url)
	});
}

function orderFh(id) { 
	var title = "订单发货";
	var url = "${ctx}/manager/customer/MyOrderEntp.do?method=orderFh&order_id=" + id ;
	$.dialog({
		title:  title,
		width:  600,
		height: 450,
		max: false,
        min: false,
        fixed: true,
        lock: true,
        zIndex:100,
		content:"url:"+ encodeURI(url)
	});
}

function orderConfirm(id) { 
	var title = "订单确认";
	var url = "${ctx}/manager/customer/MyOrderEntp.do?method=orderConfirm&order_id=" + id ;
	$.dialog({
		title:  title,
		width:  500,
		height: 300,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url)
	});
}

function delayShouhuo(id) { 
	var title = "延迟收货";
	var url = "${ctx}/manager/customer/MyOrderEntp.do?method=delayShouhuo&order_id=" + id ;
	$.dialog({
		title:  title,
		width:  500,
		height: 300,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url)
	});
}
function getDaiFanMoneyDetails(id) { 
	var title = "查看已返金额明细";
	var url = "${ctx}/manager/customer/MyTianfan.do?link_id=" + id;
	$.dialog({
		title:  title,
		width:  800,
		height: 600,
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

</script>
</body>
</html>

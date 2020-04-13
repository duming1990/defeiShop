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
<style>
#jbox{top: 10%!important;}
</style>
</head>
<body style="height:2500px;">
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/MyOrder">
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
<!--           订单状态： -->
<%--           <html-el:select property="order_state" styleId="order_state" styleClass="webinput"> --%>
<%--             <html-el:option value="">请选择...</html-el:option> --%>
<%--             <c:forEach var="curOrderState" items="${orderStateList}"> --%>
<%--               <html-el:option value="${curOrderState.index}">${curOrderState.name}</html-el:option> --%>
<%--             </c:forEach> --%>
<%--           </html-el:select> --%>
<!--           支付方式： -->
<%--           <html-el:select property="pay_type" styleId="pay_type" styleClass="webinput"> --%>
<%--             <html-el:option value="">请选择...</html-el:option> --%>
<%--             <c:forEach var="curPayType" items="${payTypeList}"> --%>
<%--               <html-el:option value="${curPayType.index}">${curPayType.name}</html-el:option> --%>
<%--             </c:forEach> --%>
<%--           </html-el:select> --%>
          &nbsp;下单时间 从:
          <html-el:text property="st_date" styleId="st_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
          至：
          <html-el:text property="en_date" styleId="en_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
          &nbsp;
          <button class="bgButtonFontAwesome" type="button" id="btn_submit"><i class="fa fa-search"></i>查 询</button></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="MyOrder.do?method=delete">
    <div class="all">
      <ul class="nav nav-tabs" id="nav_ul">
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
        <tbody id="order_${cur.id}">
          <tr>
            <td colspan="6" style="background: #e6e6e6;"> ${vs.count}、&nbsp;订单编号：${cur.trade_index}&nbsp;&nbsp;下单时间：
              <fmt:formatDate value="${cur.order_date}" pattern="yyyy-MM-dd HH:mm:ss" />
                &nbsp;<a><span class="tip-primary">商家：${cur.entp_name}</span></a>
                <c:if test="${not empty cur.map.title}">
                	&nbsp;所属活动：<span class="tip-primary">${cur.map.title}</span>
                </c:if>
            </td>
          </tr>
          <tr>
            <td align="left"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable">
                <c:set var="isPd" value="false" />
                <c:forEach var="oidsList" varStatus="vs1" items="${cur.orderInfoDetailsList}">
                  <tr>
                    <td>${oidsList.comm_name}<c:if test="${not empty oidsList.comm_tczh_name}"> &nbsp;[${oidsList.comm_tczh_name}] </c:if>
                    </td>
                    <td width="20%" align="center"><c:if test="${not empty oidsList.good_price}">￥
                        <fmt:formatNumber value="${oidsList.good_price}" pattern="0.##" />
                      </c:if>
                      <c:if test="${empty oidsList.good_price}">-</c:if>
                    </td>
                    <td width="12%" align="center">${oidsList.good_count}</td>
                    <!-- 评价 -->
                    <c:set var="comment_state" value="0"/>
                    <c:if test="${(cur.order_state eq 40) && (cur.order_type eq 10 or cur.order_type eq 11)}">
                      <c:set var="comment_state" value="1"/>
                    </c:if>
                    <!-- 售后 -->
                    <c:set var="order_return_state" value="0"/>
                    <c:if test="${(cur.order_state ge 20) && (cur.order_type eq 11)}">
                      <c:set var="order_return_state" value="1"/>
                    </c:if>
                    <c:if test="${comment_state eq 1 or order_return_state eq 1}">
                      <td width="15%" align="center"><c:if test="${(oidsList.has_comment eq 0) and (cur.order_state ge 40)}"> <a onclick="goComment(${cur.id},${oidsList.comm_id},${cur.order_type},${oidsList.comm_tczh_id},${oidsList.id});" class="label-block" id ="comment">评价</a> </c:if>
                        <c:if test="${oidsList.has_comment eq 1}"> <span class="label label-default">已评价</span> </c:if>
                      </td>
                    </c:if>
                  </tr>
                </c:forEach>
              </table></td>
            <td align="center">${cur.rel_name} <br/>(${cur.rel_phone})</td>
            <td align="center" nowrap="nowrap"><fmt:formatNumber value="${cur.order_money}" pattern="0.##"/>
              <br/>
              (运费:<fmt:formatNumber value="${cur.matflow_price}" pattern="0.##"/>)
            </td>
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
            <td align="center" nowrap="nowrap" id="order_state_${cur.id}" date-isshixiao="${cur.is_shixiao}" data-order-type="${cur.order_type}">
              <fmt:formatDate value="${cur.end_date}" pattern="yyyy-MM-dd HH:mm"  var="end_date"/>
              <script type="text/javascript">showOrderState(${cur.order_state},${cur.pay_type},${cur.order_type})</script>
              <div>
                <c:if test="${not empty cur.remark}"><span class="tip-success qtip" title="留言：${fn:escapeXml(cur.remark)}"><i class="fa fa-wechat"></i>留言</span></c:if>
              </div>
            </td>
            <td align="center"><a class="label label-info label-block" href="../customer/MyOrderDetail.do?order_id=${cur.id}&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}&from=user">订单详情</a>
              <c:choose>
                <c:when test="${cur.order_state eq 0}">
                  <c:url var="payUrl" value="IndexShoppingCar.do?method=selectPayType&trade_index=${cur.trade_index}&pay_type=${cur.pay_type}" />
                  <c:if test="${cur.order_type ne 20}"> <a class="label label-warning label-block" href="${ctx}/${payUrl}" target="_blank">去付款</a></c:if>
                  <a class="label label-danger label-block" href="javascript:void(0);"  id="cancel" onclick="updateState('MyOrder.do', 'updateState', ${cur.id}, -10, this);">取消订单</a> 
                </c:when>
                <c:when test="${cur.order_state eq 20}">
                  <div class="tip-danger qtip"><a class="label label-success label-block" id="close" onclick="updateState('MyOrder.do', 'updateState', ${cur.id}, 40, this);">确认收货</a></div>
                </c:when>
              </c:choose>
              <c:if test="${cur.order_state eq -10}"><a class="label label-danger label-block" href="javascript:void(0);" onclick="confirmDelete('MyOrder.do', 'delete', 'id=${cur.id}&'+ $('#bottomPageForm').serialize());">删除</a> </c:if>
            </td>
          </tr>
        </tbody>
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
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="${ctx}/manager/customer/MyOrder.do">
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
				pager.addHiddenInputs("trade_index", "${af.map.trade_index}");
				pager.addHiddenInputs("order_type", "${af.map.order_type}");
				pager.addHiddenInputs("pay_type", "${af.map.pay_type}");
				pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
		        pager.addHiddenInputs("par_id", "${af.map.par_id}");
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
<%-- <script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script> --%>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	$(".qtip").quicktip();
	
	//导航回显
	$("#nav_ul li").each(function(){
		if($(this).attr("data-type") ==  $("#order_type").val()){
			$(this).addClass("active").siblings().removeClass("active");
			return false;
		}
	});

	//导航跳转
	$("#nav_ul a").click(function(){ 
		 var type = $(this).parent().attr("data-type");
		 this.href= "${ctx}/manager/customer/MyOrder.do?method=list&order_type="+ type + "&" + $('#bottomPageForm').serialize();
	     this.target = "_self";
	});
// 	$("a#comment").colorbox({width:"80%", height:"40%", iframe:true});
});
function refreshPage(){
	window.location.reload();
}
function goComment(order_id,link_id,comm_type,comm_tczh_id,ods_id) {
	var url = "CommentInfo.do?&order_id="+order_id+"&link_id="+link_id+"&comm_type="+comm_type+"&comm_tczh_id="+comm_tczh_id+"&ods_id="+ods_id;
	$.dialog({
		title:  "评论",
		width:  750,
		height: 450,
        lock:true ,
		content:"url:"+url
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

</script>
</body>
</html>

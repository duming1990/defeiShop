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
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
    <html-el:form action="/customer/MyTuiHuo">
      <html-el:hidden property="method" value="list" />
      <html-el:hidden property="par_id" />
      <html-el:hidden property="mod_id" />
      <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
        <tr>
          <td>商品名称：
            <html-el:text property="comm_name_like" styleClass="webinput" maxlength="50" style="width:120px;"/>
             &nbsp;退货单号：
          <html-el:text property="return_no"  styleClass="webinput" maxlength="50" style="width:120px;"/>
            &nbsp;订单编号：
            <html-el:text property="trade_index"  styleClass="webinput" maxlength="50" style="width:120px;"/>
           
             &nbsp;订单类型：
            <html-el:select property="order_type" styleClass="webinput" >
              <html-el:option value="">全部</html-el:option>
              <html-el:option value="10">消费商品订单</html-el:option>
              <html-el:option value="11">实物商品订单</html-el:option>
            </html-el:select>
             <br/>
           	审核状态：
            <html-el:select property="audit_state" styleClass="webinput" >
              <html-el:option value="">全部</html-el:option>
              <html-el:option value="0">待审核</html-el:option>
              <html-el:option value="2">商家审核通过</html-el:option>
              <html-el:option value="-2">商家审核不通过</html-el:option>
              <html-el:option value="2">管理员审核通过</html-el:option>
              <html-el:option value="-2">管理员审核不通过</html-el:option>
            </html-el:select>
          	 是否删除：
            <html-el:select property="is_del" styleClass="webinput" >
              <html-el:option value="">全部</html-el:option>
              <html-el:option value="0">否</html-el:option>
              <html-el:option value="1">是</html-el:option>
            </html-el:select>
<!--             &nbsp;换货原因： -->
<%--            <html-el:select property="return_type" styleClass="webinput" > --%>
<%--            		<html-el:option value="">请选择...</html-el:option> --%>
<%-- 				<c:forEach items="${returnTypeList}" var="cur"> --%>
<%--            			<html-el:option value="${cur.id}">${cur.type_name}</html-el:option> --%>
<%--            		</c:forEach> --%>
<%--        	 	</html-el:select> --%>
            下单时间：
            从
            <html-el:text property="st_add_date" styleClass="webinput"  styleId="st_add_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
            至
            <html-el:text property="en_add_date" styleClass="webinput"  styleId="en_add_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
             &nbsp;
            <button class="bgButtonFontAwesome" type="button" id="btn_submit"><i class="fa fa-search"></i>查 询</button></td>
        </tr>
      </table>
    </html-el:form>
      <%@ include file="/commons/pages/messages.jsp" %>
      <form id="listForm" name="listForm" method="post" action="MyOrderEntp.do?method=delete">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable">
          <tr>
          <th align="center"><strong>订单信息</strong></th>
            <th width="10%" align="center"><strong>用户名</strong></th>
            <th width="8%" align="center"><strong>金额</strong></th>
            <th width="10%" align="center"><strong>退换货原因</strong></th>
            <th width="10%" align="center"><strong>审核状态</strong></th>
            <th width="7%" align="center"><strong>操作</strong></th>
          </tr>
          <c:forEach var="cur" items="${entityList}" varStatus="vs">
            <tr>
              <td colspan="7" style="background: #e6e6e6;">
               	 退货申请时间：
                <fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm:ss" /> &nbsp;&nbsp;订单编号：${cur.map.trade_index}
                </td>
            </tr>
            <tr align="center">
            <td align="left"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable">
              <c:set var="isPd" value="false" />
              <c:forEach var="oidsList" varStatus="vs1" items="${cur.map.orderInfo.orderInfoDetailsList}">
                <tr>
                  <td>
                      ${oidsList.comm_name}
                    <c:if test="${not empty oidsList.comm_tczh_name}"> &nbsp;[${oidsList.comm_tczh_name}] </c:if>
                    <c:if test="${not empty oidsList.huizhuan_rule}">
                      <c:forEach var="curBaseData" items="${baseData700List}">
                        <c:if test="${curBaseData.id eq oidsList.huizhuan_rule}">&nbsp;[${curBaseData.type_name}]</c:if>
                      </c:forEach>
                    </c:if>
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
                      <td>${cur.user_name}</td>
                      <td>${cur.price}元</td>
<%--                       <td>${cur.num}</td> --%>
                      <td><script type="text/javascript">showTuiHuoCause(${cur.return_way})</script></td>
                      <td>
                      	<c:choose>
			              <c:when test="${cur.audit_state eq -1}"><span class="label label-danger">管理员审核不通过</span></c:when>
			              <c:when test="${cur.audit_state eq -2}"><span class="label label-danger">审核不通过</span></c:when>
			              <c:when test="${cur.audit_state eq 0}"><span class="label label-default">待审核</span></c:when>
			              <c:when test="${cur.audit_state eq 2}"><span class="label label-success">审核通过</span></c:when>
			              <c:when test="${cur.audit_state eq 1}"><span class="label label-success">管理员审核通过</span></c:when>
            			</c:choose>
                     </td>
<%--                       <td>物流公司：${cur.map.wl_name }<br/>物流单号：${cur.th_wl_no }</td> --%>
              <td align="center">
                 <a class="label label-info label-block" href="../customer/MyTuiHuo.do?method=view&id=${cur.id}&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}">查看</a>
<%--                  <c:if test="${cur.audit_state eq -2 }"><a class="label label-warning label-block" href="javascript:void(0);" id="close" onclick="fankui(${cur.id})">反馈</a> </c:if> --%>
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
      <form id="bottomPageForm" name="bottomPageForm" method="post" action="${ctx}/manager/customer/MyTuiHuo.do">
        <table width="98%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
              <script type="text/javascript">
				var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
				 pager.addHiddenInputs("method", "list");
					pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
			        pager.addHiddenInputs("par_id", "${af.map.par_id}");
					pager.addHiddenInputs("comm_name_like", "${af.map.comm_name_like}");
					pager.addHiddenInputs("st_add_date", "${af.map.st_add_date}");
					pager.addHiddenInputs("en_add_date", "${af.map.en_add_date}");
					pager.addHiddenInputs("trade_index", "${af.map.trade_index}");
					pager.addHiddenInputs("order_type", "${af.map.order_type}");
					pager.addHiddenInputs("audit_state", "${af.map.audit_state}");
					pager.addHiddenInputs("is_del", "${af.map.is_del}");
			        pager.addHiddenInputs("return_no", "${af.map.return_no}");
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
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
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

function fankui(id) { 
	var title = "售后反馈";
	var url = "${ctx}/manager/customer/MyTuiHuo.do?method=fankui&id=" + id ;
	$.dialog({
		title:  title,
		width:  600,
		height: 500,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url)
	});

}


function orderFh(id) { 
	var title = "订单发货";
	var url = "${ctx}/manager/customer/MyOrderEntp.do?method=orderFh&order_id=" + id ;
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


function refreshPage(){
	window.location.reload();
}

</script>
</body>
</html>
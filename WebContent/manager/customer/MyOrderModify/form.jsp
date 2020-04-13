<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改价格- ${app_name}</title>
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
     <html-el:form action="/customer/MyOrderModify">
      <html-el:hidden property="method" value="save" />
 	  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
         <tr class="tite2">
               <th width="18%"><strong>订单号</strong></th>
               <th width="16%" nowrap="nowrap"><strong>商品名称</strong></th>
               <th width="8%"><strong>商品数量</strong></th>
               <th width="18%"><strong>调整后商品总金额及运费</strong></th>
               <th width="14%"><strong>原商品总金额及运费</strong></th>               
               <th width="12%"><strong>订单状态</strong></th>
      	</tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left" id="xzShow">
       	<html-el:hidden property="id" value="${entity.id}"/>
       	<html-el:hidden property="order_price_modify_old" value="${entity.order_money}"/>
        <tr>
          <td>
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable" id="hbtable">
               <tr>
             <td align="center" class="hbtd" width="19%">
               ${fn:escapeXml(entity.trade_index)}
             </td>
             <td>
             <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable" id="hbtable">
             <c:forEach var="cur3" items="${entity.map.orderInfoDetailsList}">
              <tr id="xzHidden">
               <td align="left" width="30%" >${fn:escapeXml(fnx:abbreviate(cur3.comm_name, 40, '...'))}</td>
                <td align="center" width="15%">${cur3.good_count}</td>
                <td align="center" width="30%">
                <input type="hidden" name="details_id" value="${cur3.id}"/>
                <input type="hidden" name="details_order_id" value="${cur3.order_id}"/>
                <html-el:text property="good_sum_price" styleId="good_sum_price" maxlength="10" style="width:70%;" styleClass="webinput" value="${cur3.good_sum_price}"/><br/>
                <html-el:text property="details_matflow_price" styleId="details_matflow_price" onchange="js_yf_money_count();" maxlength="10" style="width:70%;margin-top:5px;" styleClass="webinput" value="${cur3.matflow_price}"/><br/>
                <div style="cursor: pointer;" class="dot">可备注修改原因&nbsp;<span class="dot" title="备注"></span></div>
                <div style="padding-top: 5px;display:none">
                <html-el:textarea property="price_modify_remark" styleId="price_modify_remark" style="width:95%;" rows="8" value="${cur3.price_modify_remark}"></html-el:textarea>       
               </div>
                </td>
                <td align="center" width="28%">
                <c:set var = "price_modify_old" value = "" />
                <c:if test="${empty cur3.price_modify_old}">
                <c:set var = "price_modify_old" value = "${cur3.good_sum_price}" />
                </c:if>
                <c:if test="${not empty cur3.price_modify_old}">
                <c:set var = "price_modify_old" value = "${cur3.price_modify_old}" />
                </c:if>
                <html-el:text property="price_modify_old" styleId="price_modify_old" maxlength="10" style="width:100px" styleClass="webinput" value="${price_modify_old}" readonly="true" />
                 <c:set var = "details_matflow_price_old" value = "" />
                <c:if test="${empty cur3.matflow_price_old}">
                <c:set var = "details_matflow_price_old" value = "${cur3.matflow_price}" />
                </c:if>
                <c:if test="${not empty cur3.matflow_price_old}">
                <c:set var = "details_matflow_price_old" value = "${cur3.matflow_price_old}" />
                </c:if>
                <html-el:text property="details_matflow_price_old" styleId="details_matflow_price_old" maxlength="10" style="width:100px;margin-top:5px;" styleClass="webinput" value="${details_matflow_price_old}" readonly="true" />
                </td>
              </tr>
              </c:forEach>
               <tr>
               <td align="center" colspan="2">订单总运费：</td>
               <td align="center" >
                <input type="hidden" name="order_id" value="${entity.id}"/>
                <html-el:text property="matflow_price" styleId="matflow_price" readonly="true" maxlength="10" style="width:70%;" styleClass="webinput" value="${entity.matflow_price}"/><br/>
                <div style="cursor: pointer;" class="dot">可备注修改原因&nbsp;<span class="dot" title="备注"></span></div>
                <div style="padding-top: 5px;display:none">
                <html-el:textarea property="order_price_modify_remark" styleId="order_price_modify_remark" style="width:95%;" rows="8" value="${entity.price_modify_remark}"></html-el:textarea>       
               </div>
			   </td>
               <td align="center">
               <c:set var = "matflow_price_modify_old" value = "" />
                <c:if test="${empty entity.matflow_price_modify_old}">
                <c:set var = "matflow_price_modify_old" value = "${entity.matflow_price}" />
                </c:if>
                <c:if test="${not empty entity.matflow_price_modify_old}">
                <c:set var = "matflow_price_modify_old" value ="${entity.matflow_price_modify_old}" />
                </c:if>
               <html-el:text property="matflow_price_modify_old" styleId="matflow_price_modify_old" maxlength="10" style="width:100px" styleClass="webinput" value="${matflow_price_modify_old}" readonly="true" /></td>
              </tr>
              </table>
             </td>
              <td align="center" width="13%" nowrap="nowrap">
                <script type="text/javascript">showOrderState(${entity.order_state},${entity.pay_type},${entity.order_type})</script>
	           <a class="label label-warning label-block" href="../customer/MyOrderDetail.do?order_id=${entity.id}&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}&from=entp" class="orderinfo">订单详情</a>
              </td>
             </tr>
            </table>
          </td>
        </tr>
        <tr>
         <td style="text-align:center">
          <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
 	</html-el:form>
  </div>
<!-- main end -->
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$(this).find("div.dot").toggle(function(){
		$(this).find("span.dot").css("background-image","url(${ctx}/images/bgup.png)");
		$(this).next("div").slideDown();
	},function(){
		$(this).find("span.dot").css("background-image","url(${ctx}/images/bgdown.png)");
		$(this).next("div").slideUp();
	});
	
	$("input[name='good_sum_price']").map(function(){
		$(this).attr("datatype", "Currency").attr("msg", "请填写调整后商品总金额，且必须为正确数字！");
	});
	$("input[name='matflow_price']").map(function(){
		$(this).attr("datatype", "Currency").attr("msg", "请填写调整后商品总金额，且必须为正确数字！");
	});
	$("textarea[name='price_modify_remark']").map(function(){
		$(this).attr("datatype","LimitB").attr("max","200").attr("msg","填写备注信息在200字以内！");
	});
	$("textarea[name='order_price_modify_remark']").map(function(){
		$(this).attr("datatype","LimitB").attr("max","200").attr("msg","填写备注信息在200字以内！");
	});
	var f = document.forms[0];
	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 3)){
            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
			f.submit();
		}
		return false;
	});
});

function js_yf_money_count(){
	var count = 0;
	$("#xzShow").find("#xzHidden").each(function(){
		count= count + parseFloat($(this).find("input[name=details_matflow_price]").val());
	});	
	$("#matflow_price").val(count);
}

//]]></script>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
<style>
span.dot{ display:inline-block; *zoom:1; width:7px; height:4px; background-image:url(${ctx}/images/bgdown.png); background-position:left center; background-repeat:no-repeat; overflow:hidden; vertical-align:middle;}
</style>
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/OrderInfoModify">
   <html-el:hidden property="method" value="save" />
   <html-el:hidden property="mod_id" />
   <%@ include file="/commons/pages/messages.jsp" %>
   <div style="padding-bottom:5px;"></div>
      
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass" id="xzShow">
      <tr>
	      <th nowrap="nowrap">
	       <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass" id="hbtable">
               <tr>
               <th width="19%">订单号</th>
               <th width="15%">所属企业</th>
               <th width="25%" nowrap="nowrap">商品名称</th>
               <th width="5%">商品<br/>数量</th>
               <th width="10%">调整后商品<br/>总金额及运费</th>
               <th width="10%">原商品<br/>总金额及运费</th>               
               <th width="15%">订单状态</th>
               </tr>
           </table>
	      </th>
      </tr>
       	<html-el:hidden property="id" value="${entity.id}"/>
       	<html-el:hidden property="order_price_modify_old" value="${entity.order_money}"/>
        <tr>
          <td>
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass" id="hbtable">
               <tr>
             <td align="center" class="hbtd" width="19%">
               ${fn:escapeXml(entity.trade_index)}<br/>
               <a title="收货地址和发票信息" class="cases" href="${ctx}/manager/admin/ShippinInvoiceInfo.do?shipping_id=${entity.shipping_address_id}"><span>${fn:escapeXml(entity.map.rel_name)}</span></a>
               </td>
              <td align="left" class="hbtd2" width="15%"> <a title="企业信息" class="cases" href="${ctx}/manager/admin/EntpInfo.do?method=getEntpInfo&id=${entity.entp_id}"><span>${fn:escapeXml(entity.entp_name)}</span></a></td>
             <td>
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass" id="hbtable">
             <c:forEach var="cur3" items="${entity.map.orderInfoDetailsList}">
              <tr id="xzHidden">
               <td align="left">${fn:escapeXml(fnx:abbreviate(cur3.comm_name, 40, '...'))}</td>
                <td align="center" width="10%">${cur3.good_count}</td>
                <td align="center" width="20%">
                <input type="hidden" name="details_id" value="${cur3.id}"/>
                <input type="hidden" name="details_order_id" value="${cur3.order_id}"/>
                <html-el:text  property="good_sum_price" styleId="good_sum_price" maxlength="10" style="width:70%;" styleClass="webinput" value="${cur3.good_sum_price}"/><br/>
     			<html-el:text property="details_matflow_price" styleId="details_matflow_price" onchange="js_yf_money_count();" maxlength="10" style="width:70%;margin-top:5px;" styleClass="webinput" value="${cur3.matflow_price}"/><br/>
     			<div style="cursor: pointer;" class="dot">可备注修改原因&nbsp;<span class="dot" title="备注"></span></div>          
                <div style="padding-top: 5px;display:none">
                <html-el:textarea property="price_modify_remark" styleId="price_modify_remark" style="width:95%;" rows="8" value="${cur3.price_modify_remark}"></html-el:textarea>       
               </div>
                </td>
                <td align="center" width="20%">
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
               <td align="center" width="20%">

                <input type="hidden" name="order_id" value="${entity.id}"/>
                <html-el:text property="matflow_price" styleId="matflow_price" maxlength="9" style="width:70%;" styleClass="webinput" value="${entity.matflow_price}" readonly="true"/><br/>
                <div style="cursor: pointer;" class="dot">可备注修改原因&nbsp;<span class="dot" title="备注"></span></div>
                <div style="padding-top: 5px;display:none">
                <html-el:textarea  property="order_price_modify_remark" styleId="order_price_modify_remark" style="width:95%;" rows="8" value="${entity.price_modify_remark}"></html-el:textarea>       
               </div>
			   </td>
               <td align="center" width="20%">
               <c:set var = "matflow_price_modify_old" value = "" />
                <c:if test="${empty entity.matflow_price_modify_old}">
                <c:set var = "matflow_price_modify_old" value = "${entity.matflow_price}" />
                </c:if>
                <c:if test="${not empty entity.matflow_price_modify_old}">
                <c:set var = "matflow_price_modify_old" value = "${entity.matflow_price_modify_old}" />
                </c:if>
               <html-el:text property="matflow_price_modify_old" styleId="matflow_price_modify_old" maxlength="10" style="width:100px" styleClass="webinput" value="${matflow_price_modify_old}" readonly="true" /></td>
              </tr>
              </table>
             </td>
              <td align="center" width="15%" nowrap="nowrap">
                <c:choose>
	            <c:when test="${entity.order_state eq -1}">
	              <c:out value="已取消" />
	            </c:when>
	            <c:when test="${entity.order_state eq 0}">
	              <c:out value="已预订" />
	            </c:when>
	            <c:when test="${entity.order_state eq 1}">
	              <c:out value="已付款" />
	            </c:when>
	            <c:when test="${entity.order_state eq 2}">
	              <c:out value="已发货" />
	            </c:when>
	            <c:when test="${entity.order_state eq 3}">
	              <c:out value="已到货" />
	            </c:when>
	            <c:when test="${entity.order_state eq 4}"> 已收货<br />
	              (<span style="color: green;">交易成功</span>) </c:when>
	            <c:when test="${entity.order_state eq 9}">
	              <c:out value="已关闭" />
	            </c:when>
	            <c:otherwise></c:otherwise>
	           </c:choose><br/>
	            &nbsp;<a class="cases butbase" href="OrderQuery.do?method=view&id=${entity.id}&mod_id=${af.map.mod_id}"><span class="icon-node">查看订单</span></a>
            </td>
               </tr>
              </table>
          </td>
        </tr>

      <tr>
         <td colspan="3" style="text-align:center">
          <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>

<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$(this).find("div.dot").toggle(function(){
		$(this).find("span.dot").css("background-image","url(${ctx}/images/bgup.png)");
		$(this).next("div").slideDown();
	},function(){
		$(this).find("span.dot").css("background-image","url(${ctx}/images/bgdown.png)");
		$(this).next("div").slideUp();
	});
	
	$("a.cases").colorbox({width:"49%", height:"60%", iframe:true}); 
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

//	$("#order_price_modify_remark").attr("datatype","LimitB").attr("max","200").attr("msg","填写备注信息在200字以内！");
	
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
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>

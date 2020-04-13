<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单详情- ${app_name}</title>
<meta content="${app_name}订单管理" name="keywords" />
<meta content="${app_name}订单" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body id="order-detail" >
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <div id="content" style="width:100%;">
    <div class="mainbox mine" style="min-height: 1200px;">
      <h2>退货审核<span class="op-area"><a href="javascript:void(0);" onclick="history.back();">返回我的订单</a></span></h2>
      <dl class="bunch-section J-coupon">
        <dt class="bunch-section__label">订单状态</dt>
        <dd class="bunch-section__content">
          <div class="coupon-field"> 
<%--            <c:if test="${af.map.order_type eq 10}"> --%>
<%--             <p class="coupon-field__tip">小提示：记下或拍下密码向商家出示即可消费。</p></c:if> --%>
              <ul>
                  <li class="invalid">退款状态：<span>
                  	<c:choose>
              		<c:when test="${af.map.tk_status eq 0}">未退款</c:when>
              		<c:when test="${af.map.tk_status eq 1}">已退款</c:when>
            		</c:choose>
                    </span></li>
                  <li class="invalid">退货成功确认：<span>
                  	<c:choose>
              		<c:when test="${af.map.is_confirm eq 0}">未确认</c:when>
              		<c:when test="${af.map.is_confirm eq 1}">已确认</c:when>
            		</c:choose>
                    </span></li>
              </ul>
          </div>
        </dd>
        
        <dt class="bunch-section__label">售后订单信息</dt>
        <dd class="bunch-section__content">
          <ul class="flow-list">
            <li>订单编号：${trade_index}</li>
            <li>申请售后时间：
              <fmt:formatDate value="${af.map.add_date}" pattern="yyyy-MM-dd HH:mm:ss" />
            </li>
            <li>退换货原因：${returnTypeName}</li>
            <li>买家期望处理方式：<c:choose>
              <c:when test="${af.map.expect_return_way eq 1}">退货退款</c:when>
              <c:when test="${af.map.expect_return_way eq 2}">换货</c:when>
            </c:choose></li>
            <c:if test="${not empty af.map.expect_return_way}"><!--实际退款方式已填，才会显示实际退货方式- -->
	            <li>实际退货方式：<c:choose>
	              <c:when test="${af.map.expect_return_way eq 1}">退货退款</c:when>
	              <c:when test="${af.map.expect_return_way eq 2}">换货</c:when>
	            </c:choose></li>
            </c:if>
            <li>买家是否承担物流费用：<c:choose>
	              <c:when test="${af.map.fh_fee eq 0}">不承担</c:when>
	              <c:when test="${af.map.fh_fee eq 1}">承担</c:when>
	            </c:choose></li>
<%--             <c:if test="${not empty af.map.end_date}"> --%>
<!-- 	            <li>订单有效期： -->
<%-- 	              <fmt:formatDate value="${af.map.end_date}" pattern="yyyy-MM-dd HH:mm:ss" /> --%>
<!-- 	            </li> -->
<%--             </c:if> --%>
<!--             <li> 付款方式： -->
<%--               <c:forEach var="curPayType" items="${payTypeList}"> --%>
<%--                 <c:if test="${curPayType.index eq af.map.pay_type}"> --%>
<%--                   <c:if test="${af.map.order_type eq 30}"> --%>
<%--                     <c:if test="${af.map.pay_type ne 0}">${curPayType.name}</c:if> --%>
<%--                     <c:if test="${af.map.pay_type eq 0}">-</c:if> --%>
<%--                   </c:if> --%>
<%--                   <c:if test="${af.map.order_type ne 30}">${curPayType.name}</c:if> --%>
<%--                 </c:if> --%>
<%--               </c:forEach> --%>
<!--             </li> -->
<%--             <c:if test="${not empty af.map.pay_date}"> --%>
<!--               <li>付款时间： -->
<%--                 <fmt:formatDate value="${af.map.pay_date}" pattern="yyyy-MM-dd HH:mm:ss" /> --%>
<!--               </li> -->
<%--             </c:if> --%>
<%--             <c:if test="${not empty af.map.fahuo_remark}"> --%>
<!--               <li>发货备注： -->
<%--                 ${af.map.fahuo_remark} --%>
<!--               </li> -->
<%--             </c:if> --%>
          </ul>
        </dd>
        <dt class="bunch-section__label">商品信息</dt>
        <dd class="bunch-section__content">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable">
            <tr>
<!--               <th class="left" width="10">序号</th> -->
              <th class="left" width="100">商品名称</th>
              <th width="50">单价</th>
              <th width="30">数量</th>
<!--               <th width="54">支付金额</th> -->
            </tr>
<%--             <c:forEach items="${orderInfoDetailList}" var="cur" varStatus="vs"> --%>
              <tr>
<%--                 <td align="center" width="5%">${vs.count}</td> --%>
                <td class="left">${af.map.comm_name}</td>
                <td width="20%" align="center"><span class="money">¥</span>
                    <fmt:formatNumber pattern="0.##" value="${af.map.price}" />
                </td>
                <td width="20%" align="center">${af.map.num}</td>
              </tr>
<%--             </c:forEach> --%>
          </table>
        </dd>
        <c:if test="${not empty imgsList}">
        <dt class="bunch-section__label">用户上传图片</dt>
        <dd class="bunch-section__content">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable">
            <tr id="trFile">
<%--       <td class="title_item">${btnName}：</td> --%>
      <td colspan="2"><c:forEach var="cur" items="${imgsList}" varStatus="vs"> <span style="float:left;"><img src="${ctx}/${cur.file_path}@s400x400"  height="200" width="200"/>
          <c:if test="${vs.count%4 eq 0}"><br/>
          </c:if>
          <c:if test="${vs.count%4 ne 0}">&nbsp;&nbsp;</c:if>
          </span></c:forEach></td>
    </tr>
          </table>
        </dd>
        </c:if>
        <dt class="bunch-section__label">物流信息</dt>
        <dd class="bunch-section__content">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable">
            <tr>
              <th>物流公司名称</th>
              <th>物流单号</th>
              <th>物流费用</th>
              </th>
            </tr>
              <tr>
                <td align="center" width="15%"><c:out value="${af.map.th_wl_company}" /></td>
                <td align="center"><c:out value="${af.map.th_wl_no}" /></td>
                <td align="center"><span class="money">¥</span><fmt:formatNumber pattern="0.##" value="${af.map.th_wl_fee}" /></td>
              </tr>
          </table>
        </dd>
        <dt class="bunch-section__label">审核信息</dt>
        <dd class="bunch-section__content">
     <html-el:form action="/customer/TuiHuoAudit.do" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable">
<!--             <th colspan="4">审核信息</th> -->
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>审核状态：</td>
        <td colspan="3"><html-el:select property="audit_state" styleId="audit_state">
            <html-el:option value="">请选择...</html-el:option>
            <html-el:option value="2">审核通过</html-el:option>
            <html-el:option value="-2">审核不通过</html-el:option>
          </html-el:select></td>
      </tr>
      <tr id="_return_way">
        <td class="title_item"><span style="color: #F00;">*</span>退货方式：</td>
        <td colspan="3"><html-el:select property="return_way" styleId="return_way">
            <html-el:option value="">请选择...</html-el:option>
            <html-el:option value="1">退货退款</html-el:option>
            <html-el:option value="2">换货</html-el:option>
            <html-el:option value="3">只退款</html-el:option>
          </html-el:select></td>
      </tr>
      
      <tr id="_hh_wl_no">
        <td class="title_item"><span style="color: #F00;">*</span>发货物流快递公司：</td>
        <td colspan="3"><html-el:select property="hh_wl_company" styleId="hh_wl_company" styleClass="webinput">
	         <html-el:option value="">请选择</html-el:option>
	         <c:forEach items="${wlCompInfoList}" var="cur">
                <html-el:option value="${cur.wl_comp_name}">${cur.wl_comp_name}</html-el:option>
             </c:forEach>
             </html-el:select></td>
      </tr>
      <tr id="_th_wl_company">
        <td class="title_item"><span style="color: #F00;">*</span>发货物流单号：</td>
        <td colspan="3">
    		<html-el:text property="hh_wl_no" styleId="hh_wl_no" maxlength="125" style="width:200px" styleClass="webinput" />
            </td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>审核意见:</td>
        <td colspan="3"><html-el:text property="audit_note" styleId="audit_note" maxlength="125" style="width:480px" styleClass="webinput" /></td>
      </tr>
      <tr>
        <td colspan="4" align="center"><html-el:button property="" value="审 核" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
          </table>
          </html-el:form>
        </dd>
      </dl>
    </div>
  </div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#_return_way").hide();
	$("#_th_wl_company").hide();
	$("#_hh_wl_no").hide();
	
	
	$("#audit_state").change(function(){
        if("2" == $(this).val()){
        	$("#_return_way").show();
        	$("#_th_wl_company").show();
        	$("#_hh_wl_no").show();
        }else{
        	$("#_return_way").hide();
        	$("#_th_wl_company").hide();
        	$("#_hh_wl_no").hide();
        }
	});
	
	$("#audit_state").attr("dataType", "Require").attr("msg", "请选择审核状态！");
	$("#return_way").attr("dataType", "Require").attr("msg", "请选择退货方式！");
	$("#audit_note").attr("dataType", "Require").attr("msg", "请填写审核意见！");
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
//]]></script>
</body>
</html>

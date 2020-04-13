<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
    <tr>
      <th colspan="4">退货基本信息</th>
    </tr>
    <tr>
      <td width="15%" class="title_item">用户名：</td>
      <td colspan="3">${fn:escapeXml(af.map.user_name)}</td>
    </tr>
    <tr>
      <td width="15%" class="title_item">应退金额：</td>
      <td colspan="3">${af.map.price}</td>
    </tr>
    <c:if test="${af.map.audit_state eq 1 }">
    <tr>
      <td width="15%" class="title_item">实际退款金额：</td>
      <td colspan="3">原路退回金额:<span style="color: #468e33">${fn:escapeXml(af.map.return_real_money)}</span>
      </br>
      余额退款：<span style="color: #468e33">${fn:escapeXml(af.map.return_bi_dianzi)}</span></td>
    </tr>
<!--     <tr> -->
<!--       <td width="15%" class="title_item">余额退款：</td> -->
<%--       <td colspan="3">${fn:escapeXml(af.map.return_bi_dianzi)}</td> --%>
<!--     </tr> -->
    <tr>
      <td width="15%" class="title_item">退款方式：</td>
      <td colspan="3">
      	<c:if test="${af.map.return_money_type eq 0 }">余额</c:if>
      	<c:if test="${af.map.return_money_type eq 1 }">原路退回</c:if>
      	<c:if test="${af.map.return_money_type eq 2 }">余额+原路退回</c:if>
      </td>
    </tr>
    </c:if>
    <tr>
      <td width="15%" class="title_item">退换货原因：</td>
      <td colspan="3"><script type="text/javascript">showTuiHuoReasone('${af.map.return_type}')</script></td>
    </tr>
    <tr>
      <td width="15%" class="title_item">期望处理方式：</td>
      <td colspan="3"><script type="text/javascript">showTuiHuoCause('${af.map.expect_return_way}')</script></td>
    </tr>
    <tr>
      <td width="15%" class="title_item">退换货联系人：</td>
      <td colspan="1">${af.map.return_link_man }</td>
      <td width="15%" class="title_item">退换货联系电话：</td>
      <td colspan="1">${af.map.return_tel }</td>
    </tr>
    <tr>
      <td width="15%" class="title_item">原因说明：</td>
      <td colspan="1">${af.map.return_desc }</td>
      <td width="15%" class="title_item">添加时间：</td>
      <td colspan="1"><fmt:formatDate value="${af.map.add_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
    </tr>
    <tr>
      <td class="title_item">${btnName}轮播图片：</td>
      <td colspan="4">
      <c:forEach var="cur" items="${imgsList}" varStatus="vs">
      <span style="float:left;"> <a href="${ctx}/${cur.file_path}" title="查看轮播图" class="viewImg cb" style="text-decoration: none;"> <img src="${ctx}/${cur.file_path}@s400x400" height="50" width="50"/> </a> &nbsp; </span></c:forEach></td>
    </tr>
    <tr>
      <td width="15%" class="title_item">审核状态：</td>
      <td colspan="4"><c:choose>
          <c:when test="${af.map.audit_state eq 0}">待审核</c:when>
          <c:when test="${af.map.audit_state eq 2}">商家审核通过</c:when>
          <c:when test="${af.map.audit_state eq 1}">平台审核通过</c:when>
          <c:when test="${af.map.audit_state eq -1}">平台审核不通过</c:when>
          <c:when test="${af.map.audit_state eq -2}">商家审核不通过</c:when>
        </c:choose></td>
    </tr>
    <c:if test="${not empty af.map.audit_date}">
    <tr>
      <td width="15%" class="title_item">审核时间：</td>
      <td colspan="4"><fmt:formatDate value="${af.map.audit_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
    </tr>
    </c:if>
    <th colspan="4">审核信息</th>
      <c:forEach var="item" items="${orderReturnAuditList}">
      <tr>
    	<td width="15%" class="title_item">审核状态：</td>
    	<td><c:choose>
          <c:when test="${item.audit_state eq 0}">待审核</c:when>
          <c:when test="${item.audit_state eq 2}">商家审核通过</c:when>
          <c:when test="${item.audit_state eq 1}">平台审核通过</c:when>
          <c:when test="${item.audit_state eq -1}">平台审核不通过</c:when>
          <c:when test="${item.audit_state eq -2}">商家审核不通过</c:when>
        </c:choose>
        </td>
    	<td  width="15%" class="title_item">审核说明：</td>
    	<td>${item.remark}
        </td>
      </tr>
    	</c:forEach>
    <tr class="Tp">
      <th colspan="4">订单基本信息</th>
    </tr>
    <tr class="Tp">
      <td width="12%" nowrap="nowrap" class="title_item">用户名：</td>
      <td><c:out value="${orderInfo.add_user_name}"></c:out></td>
      <td nowrap="nowrap" class="title_item">商品所属企业：</td>
      <td><c:out value="${orderInfo.entp_name}"></c:out></td>
    </tr>
    <tr class="Tp">
      <td width="12%" nowrap="nowrap" class="title_item">交易流水号：</td>
      <td ><c:out value="${orderInfo.trade_index}"></c:out></td>
      <td nowrap="nowrap" class="title_item">订单状态：</td>
      <td>
      <c:forEach var="curOrderState" items="${orderStateList}">
       <c:if test="${curOrderState.index eq orderInfo.order_state}">
         <c:set var="orderStateName" value="${curOrderState.name}" />
         <c:if test="${orderInfo.order_type eq 10 and orderStateName eq '等待发货'}">
           <c:set var="orderStateName" value="等待消费" />
         </c:if>
         <c:if test="${orderInfo.order_type eq 30 and orderStateName eq '等待发货'}">
             <c:set var="orderStateName" value="充值成功" />
           </c:if>
         ${orderStateName} </c:if>
       </c:forEach>
      </td>
    </tr>
    <tr class="Tp">
      <td width="12%" nowrap="nowrap" class="title_item">订单产品数量：</td>
      <td><c:out value="${orderInfo.order_num}"></c:out></td>
      <td width="12%" nowrap="nowrap" class="title_item">订单金额详情：</td>
      <td>应付款金额：<fmt:formatNumber pattern="0.00" value="${orderInfo.order_money}" />元 </td>
    </tr>
    <tr class="Tp">
      <td width="12%" nowrap="nowrap" class="title_item">支付方式：</td>
      <td colspan="3">
          <c:forEach var="curPayType" items="${payTypeList}">
              <c:if test="${curPayType.index eq orderInfo.pay_type}">
                <c:if test="${orderInfo.order_type eq 30}">
                  <c:if test="${orderInfo.pay_type ne 0}">${curPayType.name}</c:if>
                  <c:if test="${orderInfo.pay_type eq 0}">-</c:if>
                </c:if>
                <c:if test="${orderInfo.order_type ne 30}">${curPayType.name}</c:if>
              </c:if>
            </c:forEach>
      </td>
    </tr>
    <tr>
      <td width="12%" nowrap="nowrap" class="title_item">订单时间：</td>
      <td colspan="3">
      <div><fmt:formatDate value="${orderInfo.order_date}" pattern="yyyy-MM-dd HH:mm:ss"  var="od"/>下单时间：${od}</div>
      <div><fmt:formatDate value="${orderInfo.pay_date}" pattern="yyyy-MM-dd HH:mm:ss"  var="od"/>支付时间：${od}</div>
      <div><fmt:formatDate value="${orderInfo.fahuo_date}" pattern="yyyy-MM-dd HH:mm:ss"  var="od"/>发货时间：${od}</div>
      <div><fmt:formatDate value="${orderInfo.qrsh_date}" pattern="yyyy-MM-dd HH:mm:ss"  var="od"/>收货时间：${od}</div>
		</td>
    </tr>
    <tr>
      <td colspan="7">
      <table width="100%" border="0"  cellpadding="0" cellspacing="0"  align="left"  class="tableClass">
          <tr>
            <th colspan="7">退货商品信息</th>
          </tr>
          <tr>
            <th>商品名称</th>
            <th>商品数量</th>
            <th>商品单价</th>
            <th>商品总金额</th>
            <th>订单支付</th>
            <th>运费</th>
            <th>实际支付金额</th>
          </tr>
          <c:forEach var="cur" items="${orderInfoDetailList}" varStatus="vs">
            <tr>
              <td align="center" nowrap="nowrap">
              <c:if test="${cur.order_type eq 10 or cur.order_type eq 11}">
                    <c:if test="${cur.order_type eq 10 or cur.order_type eq 11}">
	                   <c:url var="url" value="/entp/IndexEntpInfo.do?method=getCommInfo&id=${cur.comm_id}" />
	                </c:if>   
                    <a href="${url}" target="_blank">${cur.comm_name}</a>
			  </c:if>
               <c:if test="${cur.order_type eq 10 or cur.order_type eq 7}"> ${cur.comm_name} </c:if>
               </td>
              <td align="center" nowrap="nowrap"><c:out value="${cur.good_count}" /></td>
              <td align="center" nowrap="nowrap">
              <c:if test="${not empty cur.good_price}">￥<fmt:formatNumber value="${cur.good_price}" pattern="0.00" /></c:if>
              <c:if test="${empty cur.good_price}">-</c:if>
              </td>
              <td align="center" nowrap="nowrap">
              	<c:if test="${not empty cur.good_sum_price}">￥<fmt:formatNumber value="${cur.good_sum_price}" pattern="0.00" /></c:if>
                <c:if test="${empty cur.good_sum_price}">-</c:if>
                </td>
             	<fmt:formatNumber var="actual_money" value="${cur.actual_money}" pattern="0.00" />
              	<fmt:formatNumber var="matflow_price" value="${cur.matflow_price}" pattern="0.00" />
               <td align="center" nowrap="nowrap">￥${actual_money}</td>
               <td align="center" nowrap="nowrap">￥${matflow_price}</td>
              
                <td align="center" nowrap="nowrap">
              	￥${actual_money + matflow_price}
                </td>
            </tr>
          </c:forEach>
        </table></td>
    </tr>
    <c:if test="${not empty msglist}">
    <tr>
      <td colspan="4">
      <table width="100%" border="0"  cellpadding="0" cellspacing="0"  align="left"  class="tableClass">
          <tr>
            <th colspan="4">申诉内容</th>
          </tr>
          <c:forEach items="${msglist}" var="item">
          <tr>
           		<td width="15%" class="title_item">申诉内容：</td>
		      <td >${item.content}</td>
		      </tr>
		      <tr>
		      <td width="15%" class="title_item">申诉时间：</td>
		      <td><fmt:formatDate value="${item.add_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		    </tr>
		   </c:forEach>
          
        </table>
        </td>
    </tr>
    </c:if>
<!--     <tr> -->
<%--       <td colspan="6" style="text-align:center"><html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td> --%>
<!--     </tr> -->
  </table>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
  <c:if test="${af.map.is_audit eq 1 }">
      <tr>
        <th colspan="4">审核信息</th>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>审核状态：</td>
        <td colspan="3"><html-el:select property="audit_state" styleId="audit_state">
            <html-el:option value="">请选择...</html-el:option>
            <html-el:option value="0">待商家审核</html-el:option>
            <html-el:option value="1">平台审核通过</html-el:option>
            <html-el:option value="-1">平台审核不通过</html-el:option>
            <html-el:option value="2">商家审核通过</html-el:option>
            <html-el:option value="-2">商家审核不通过</html-el:option>
            
          </html-el:select></td>
      </tr>
     
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>审核意见:</td>
        <td colspan="3"><html-el:text property="audit_note" styleId="audit_note" maxlength="125" style="width:480px" styleClass="webinput" /></td>
      </tr>
       </c:if>
      <tr>
      <td colspan="4" align="center">
      <c:if test="${af.map.is_audit eq 1 }">
        <html-el:button property="" value="审 核" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          </c:if>
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>



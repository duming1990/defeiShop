<table width="100%" border="1" cellpadding="5" cellspacing="1" >
  
  <tr>
      <th width="5%">序号</th>
      <th width="8%">银联订单号</th>
      <th width="8%">商城订单号</th>
      <th width="4%">数量</th>
      <th width="8%">订单金额(元)</th>
      <th width="8%">货到付款金额(元)</th>
      <th width="8%">银联支付金额(元)</th>
      <th width="8%">运费(元)</th>
      <th width="8%">优惠券金额(元)</th>
      <th width="6%">订单佣金(元)</th>
      <th width="8%">收货人姓名</th>
      <th width="8%">收货人电话</th>
      <th width="7%">支付方式</th>
      <th width="7%">订单状态</th>
      <th width="8%">订单日期</th>
      <th width="8%">确认收货日期</th>
 </tr>
	 <#if (entityList)??>
	  <#list entityList as cur1>
	  	<tr align="center">
        <td align="center"> ${cur1_index + 1}</td>
        <td align="center">
        <#if (cur1.pay_type == 0)>
         	货到付款不存在银联订单号
        </#if>
        <#if (cur1.pay_type == 2)>
        &nbsp;${((cur1.trade_merger_index)!"----")?html}
        </#if>
        </td>
        <td align="center">&nbsp;${((cur1.trade_index)!" ")?html}</td>
        <td nowrap="nowrap">${((cur1.order_num)!" ")?html}</td>
        <td align="center" nowrap="nowrap">
        	￥${((cur1.map.order_money)!" ")?html}
          	<#if (cur1.yhq_id)??><br/>${((cur1.yhq_tip_desc)!" ")?html}</#if>
            <#if (cur1.jfdh_money)??><br/>积分兑换：${((cur1.jfdh_money)!" ")?html}</#if>
            <#if (cur1.qdyh_id)??><br/>${((cur1.qdyh_tip_desc)!" ")?html}
                <#if (cur1.matflow_price)??><br/>(运费:${((cur1.matflow_price)!" ")?html}元)</#if>
            <#else>
            <br/>(运费:${((cur1.matflow_price)!" ")?html}元)
            </#if>
        </td>
        
        <td align="center" nowrap="nowrap">
           <#if (cur1.pay_type == 0)>${((cur1.map.sp_money)!" ")?html}</#if>
           <#if (cur1.pay_type == 2)>0</#if>
        </td>
        
        <td align="center" nowrap="nowrap">
           <#if (cur1.pay_type == 0)>0</#if>
           <#if (cur1.pay_type == 2)>${((cur1.map.sp_money)!" ")?html}</#if>
        </td>
        
        <td align="center" nowrap="nowrap">
           <#if (cur1.matflow_price)??>${((cur1.matflow_price)!"0")?html}</#if>
        </td>
        
        <td align="center" nowrap="nowrap">
        <#if (cur1.yhq_id)??>${((cur1.map.yhq_money)!"0")?html}</#if>
        </td>
        
        <td nowrap="nowrap">
        	${((cur1.map.pay_yj)!"----")?html}
        </td>
        <td nowrap="nowrap"> ${((cur1.rel_name)!" ")?html}</td>
        <td nowrap="nowrap">${((cur1.rel_phone)!" ")?html}</td>
        <td align="center">
         <#if (cur1.pay_type == 0)>货到付款</#if>
         <#if (cur1.pay_type == 2)>银联支付</#if>
        </td>
        <td align="center">
        	<#if (cur1.order_state == -10)>已取消</#if>
        	<#if (cur1.order_state == 0)>
        		<#if (cur1.pay_type != 3)>已预订</#if>
        		<#if (cur1.pay_type == 3)>已预订(银行汇款)<br/>【请等待收款确认】</#if>
        	</#if>
        	<#if (cur1.order_state == 10)>
        		<#if (cur1.pay_type == 0)>等待发货</#if>
        		<#if (cur1.pay_type == 2)>已付款</#if>
        	</#if>
        	<#if (cur1.order_state == 20)>已发货</#if>
        	<#if (cur1.order_state == 30)>已到货</#if>
        	<#if (cur1.order_state == 40)>已收货<br />(<span style="color: green;">交易成功</span>)</#if>
        	<#if (cur1.order_state == 90)>已关闭</#if>
          </td>
        <td>${cur1.order_date?string("yyyy-MM-dd")}</td>
        <td>${cur1.qrsh_date?string("yyyy-MM-dd")}</td>
      </tr>
	 </#list>
</#if>
</table>
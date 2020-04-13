<style type="text/css">
.tostring{mso-style-parent:style0;mso-number-format:"\@";}
</style>
<table width="100%" border="1" cellpadding="5" cellspacing="1" >
     <tr>
        <th width="5%">序号</th>
          <#if (is_fuwu == 1)><th width="15%">店铺</th></#if>
        <th width="15%">订单流水号</th>
        <th width="10%">支付时间</th>
        <th nowrap="nowrap">商品名称</th>
        <th width="15%">订单状态</th>
        <th width="10%">商品数量</th>
        <th width="8%">单价</th>
        <th width="8%">总金额</th>
     </tr>
	  <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
	  	<td align="center" nowrap="nowrap">${cur_index + 1}</td>
	  	  <#if (is_fuwu == 1)>${((cur.map.entp_name)!" ")?html}</#if>
	  	<td align="center" class="tostring">${((cur.map.trade_index)!" ")?html}</td>
	  	<td align="center">${(cur.map.pay_date)?string("yyyy-MM-dd")}&nbsp;</td>
	  	<td align="center">${((cur.comm_name)!" ")?html}</td>  	
	  	<td align="center">
          <#if (cur.map.order_state == 10)>等待发货</#if>
          <#if (cur.map.order_state == 15)>退款/换货申请中</#if>
          <#if (cur.map.order_state == 20)>已发货(待确认收货)</#if>
          <#if (cur.map.order_state == 40)>已收货</#if>
          <#if (cur.map.order_state == 50)>交易成功</#if>
          <#if (cur.map.order_state == 90)>关闭</#if>
        </td>
        <td align="center">${((cur.good_count)!" ")?html}</td>
	  	<td align="center">${((cur.good_price)!" ")?html}</td>
	  	<td align="center">${((cur.good_sum_price)!" ")?html}</td>		
      </tr>
	 </#list>
	 </#if>
	
</table>
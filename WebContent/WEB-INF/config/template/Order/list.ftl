<style type="text/css">
.tostring{mso-style-parent:style0;mso-number-format:"\@";}
</style>
<table width="100%" border="1" cellpadding="5" cellspacing="1" >
  <tr><td align="left" colspan="13">${title}</td></tr>
  <tr>
	<th nowrap="nowrap" align="center"><strong>序号</strong></th>
	<th nowrap="nowrap" align="center"><strong>订单编号</strong></th>
	<th width="8%" align="center"><strong>下单人</strong></th>
    <th width="8%" align="center"><strong>下单人电话</strong></th>
	<th width="8%" align="center"><strong>下单时间</strong></th>
	<th width="8%" align="center"><strong>收货人</strong></th>
	<th width="8%" align="center"><strong>收货人电话</strong></th>
	<th width="8%" align="center"><strong>收货人地址</strong></th>
	<th width="8%" align="center"><strong>收货时间</strong></th>
	<th width="8%" align="center"><strong>订单单数</strong></th>
	<th width="8%" align="center"><strong>订单金额</strong></th>
	<th width="8%" align="center"><strong>运费</strong></th>
	<th width="8%" align="center"><strong>支付方式</strong></th>
	<th width="8%" align="center"><strong>订单状态</strong></th>
  </tr>
	 <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
          <td align="left">${((cur_index + 1)!" ")?html}</td>
          <td align="left" class="tostring">${((cur.trade_index)!" ")?html}</td>
          <td align="center">${((cur.add_user_name)!" ")?html}</td>
          <td align="center">${((cur.map.add_user_mobile)!" ")?html}</td>
          <td align="center" class="tostring">${cur.order_date?string("yyyy-MM-dd HH:mm:ss")}</td>
          <td align="center">${((cur.rel_name)!" ")?html}</td>
          <td align="center">${((cur.rel_phone)!" ")?html}</td>
          <td align="center">${((cur.map.shippingAddress.map.full_name)!" ")?html}${((cur.rel_addr)!" ")?html}</td>
          <td align="center" class="tostring"><#if (cur.qrsh_date)??>${cur.qrsh_date?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
          <td align="center" nowrap="nowrap">${((cur.order_num)!" ")?html}</td>
          <td align="center" nowrap="nowrap">${((cur.order_money)!" ")?html}</td>
          <td align="center" nowrap="nowrap">${((cur.matflow_price)!" ")?html}</td>
          <td align="center">
          <#if (cur.pay_type == 0)>现金结算</#if>
          <#if (cur.pay_type == 1)>支付宝</#if>
          <#if (cur.pay_type == 3)>微信支付</#if>
          </td>
          <td align="center" nowrap="nowrap">
            <#if (cur.order_state == -90)>异常订单</#if>
            <#if (cur.order_state == -20)>已退款</#if>
            <#if (cur.order_state == -20)>已取消</#if>
        	<#if (cur.order_state == 0)>已预订&nbsp;(<span style="color:red;">未支付</span>)</#if>
        	<#if (cur.order_state == 15)>退款/换货申请</#if>
        	<#if (cur.order_state == 20)>已发货&nbsp;(<span style="color:green;">待收货</span>)</#if>
        	<#if (cur.order_state == 40)>已收货</#if>
        	<#if (cur.order_state == 50)>交易成功</#if>
        	<#if (cur.order_state == 90)>已关闭</#if>
          </td>         
      </tr>
      <tr>
       <td colspan="14">
		<table width="8%" border="1" cellpadding="5" cellspacing="1" >
		  <tr>
	       <th width="8%" align="center" colspan = "3">商品名称</th>
	       <th width="8%" align="center" colspan = "3">商品数量</th>
	       <th width="8%" align="center" colspan = "3">商品单价</th>
	       <th width="8%" align="center" colspan = "3">商品总金额</th>
	       <th width="8%" align="center" colspan = "3">是否需要发票</th>
	      </tr> 
	      <#list cur.orderInfoDetailsList as curDetails>
	       <tr style="background-color:#71C671;">
	       <td align="center" colspan = "3">${((curDetails.comm_name)!" ")?html}</td>
	       <td align="center" colspan = "3">${((curDetails.good_count)!" ")?html}</td>
	       <td align="center" colspan = "3">${((curDetails.good_price)!" ")?html}</td>
	       <td align="center" colspan = "3">${((curDetails.good_sum_price)!" ")?html}</td>
	       <td align="center" colspan = "3">
	        <#if (curDetails.fp_state == 0)>不需要发票</#if>
        	<#if (curDetails.fp_state == 1)>需要发票</#if>
        	<#if (curDetails.fp_state == 2)>已寄送发票</#if>
	        </td>
	       </tr>
	      </#list>
	    </table>
       </td>
      </tr>
	 </#list>
</#if>
</table>

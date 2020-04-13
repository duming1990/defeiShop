<style type="text/css">
.tostring{mso-style-parent:style0;mso-number-format:"\@";}
</style>
<table width="100%" border="1" cellpadding="5" cellspacing="1" >
  <tr><td align="left" colspan="19">${title}</td></tr>
  <tr>
	<th nowrap="nowrap" align="center"><strong>序号</strong></th>
	<th nowrap="nowrap" align="center"><strong>订单编号</strong></th>
	<th nowrap="nowrap" align="center"><strong>商品名称</strong></th>
	<th nowrap="nowrap" align="center"><strong>商品规格</strong></th>
	<th nowrap="nowrap" align="center"><strong>商品数量</strong></th>
	<th nowrap="nowrap" align="center"><strong>商品单价</strong></th>
	<th nowrap="nowrap" align="center"><strong>商品总金额</strong></th>
	<th width="8%" align="center"><strong>下单人</strong></th>
    <th width="8%" align="center"><strong>下单人电话</strong></th>
	<th width="8%" align="center"><strong>下单时间</strong></th>
	<th width="8%" align="center"><strong>收货人</strong></th>
	<th width="8%" align="center"><strong>收货人电话</strong></th>
	<th width="8%" align="center"><strong>收货人地址</strong></th>
	<th width="8%" align="center"><strong>收货时间</strong></th>
	<th width="8%" align="center"><strong>订单单数</strong></th>
	<th width="8%" align="center"><strong>订单金额</strong></th>
	<th width="8%" align="center"><strong>余额抵扣</strong></th>
	<th width="8%" align="center"><strong>运费</strong></th>
	<th width="8%" align="center"><strong>支付方式</strong></th>
	<th width="8%" align="center"><strong>订单状态</strong></th>
	<th width="8%" align="center"><strong>所属企业</strong></th>
	<th width="8%" align="center"><strong>所属活动</strong></th>
	
  </tr>
	 <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
          <td align="left" >${((cur_index + 1)!" ")?html}</td>
          <td align="left" class="tostring"  >${((cur.trade_index)!" ")?html}</td>
	  	  <td align="center" colspan="5">
            <table width="100%" border="1" cellpadding="6" cellspacing="1">
		     <#list cur.orderInfoDetailsList as curDetails>
			    <tr>
				   <td align="center">${((curDetails.comm_name)!" ")?html}</td>
				   <td align="center">${((curDetails.comm_tczh_name)!" ")?html}</td>
				   <td align="center">${((curDetails.good_count)!" ")?html}</td>
				   <td align="center">${((curDetails.good_price)!" ")?html}</td>
				   <td align="center">${((curDetails.good_sum_price)!" ")?html}</td>
			    </tr>
		  	  </#list>
			 </table> 
          </td>
          <td align="center"  >${((cur.add_user_name)!" ")?html}</td>
          <td   align="center">${((cur.map.add_user_mobile)!" ")?html}</td>
          <td align="center"   class="tostring">${cur.order_date?string("yyyy-MM-dd HH:mm:ss")}</td>
          <td   align="center">${((cur.rel_name)!" ")?html}</td>
          <td   align="center">${((cur.rel_phone)!" ")?html}</td>
          <td   align="center">${((cur.map.shippingAddress.map.full_name)!" ")?html}${((cur.rel_addr)!" ")?html}</td>
          <td align="center"   class="tostring"><#if (cur.qrsh_date)??>${cur.qrsh_date?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
          <td align="center"  nowrap="nowrap">${((cur.order_num)!" ")?html}</td>
          <td align="center"  nowrap="nowrap">${((cur.order_money)!" ")?html}</td>
          <td align="center"  nowrap="nowrap">${((cur.money_bi)!" ")?html}</td>
          <td align="center"  nowrap="nowrap">${((cur.matflow_price)!" ")?html}</td>
          <td   align="center">
          <#if (cur.pay_type == 0)>余额</#if>
          <#if (cur.pay_type == 1)>支付宝</#if>
          <#if (cur.pay_type == 3)>微信支付</#if>
          <#if (cur.pay_type == 4)>通联支付</#if>
          <#if (cur.pay_type == 6)>现金</#if>
          <#if (cur.pay_type == 7)>福利卡支付</#if>
          </td>
          <td align="center" nowrap="nowrap">
            <#if (cur.order_state == -90)>异常订单</#if>
            <#if (cur.order_state == -20)>已退款</#if>
            <#if (cur.order_state == -10)>已取消</#if>
        	<#if (cur.order_state == 0)>已预订&nbsp;(<span style="color:red;">未支付</span>)</#if>
        	<#if (cur.order_state == 15)>退款/换货申请</#if>
        	<#if (cur.order_state == 20)>已发货&nbsp;(<span style="color:green;">待收货</span>)</#if>
        	<#if (cur.order_state == 40)>已收货</#if>
        	<#if (cur.order_state == 50)>交易成功</#if>
        	<#if (cur.order_state == 90)>已关闭</#if>
          </td> 
          <td align="center">
        	  ${((cur.entp_name)!" ")?html}
          </td>   
          <td align="center">
        	  ${((cur.map.title)!" ")?html}
          </td>             
      </tr>
	 </#list>
</#if>
</table>

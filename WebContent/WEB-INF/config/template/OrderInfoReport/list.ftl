<style type="text/css">
.tostring{mso-style-parent:style0;mso-number-format:"\@";}
</style>
<table width="100%" border="1" cellpadding="5" cellspacing="1" >
  <tr><td align="center" colspan="9"><strong>${title}</strong></td></tr>
  <tr>
	<th width="10%"><strong>商家名称</strong></th>
    <th width="10%"><strong>订单总数</strong></th>
    <th width="10%"><strong>实际支付金额</strong></th>
    <th width="10%"><strong>余额抵扣金额</strong></th>
    <th width="10%"><strong>使用福利金金额</strong></th>
    <th width="10%"><strong>总金额</strong></th>
    <th width="10%"><strong>代言人奖励</strong></th>
    <th width="10%"><strong>驿站合伙人奖励</strong></th>
    <th width="10%"><strong>普通县域合伙人奖励</strong></th>
    <th width="10%"><strong>股份县域合伙人奖励</strong></th>
  </tr>
	 <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
          <td align="center" class="tostring">${((cur.map.entp_name)!" ")?html}</td>
          <td align="center">${((cur.map.cnt)!" ")?html}单</td>
          <td align="center">${((cur.map.sum_money)!" ")?html}</td>
          <td align="center">${((cur.map.sum_ye)!" ")?html}</td>
          <td align="center">${((cur.map.sum_welfare_pay_money)!" ")?html}</td>
          <td align="center">${((cur.map.sum_pay_money)!" ")?html}</td>
          <td align="center">${((cur.map.price_money_1002)!" ")?html}</td>
          <td align="center">${((cur.map.price_money_1003)!" ")?html}</td>
          <td align="center">${((cur.map.price_money_1004)!" ")?html}</td>
          <td align="center">${((cur.map.price_money_1005)!" ")?html}</td>
      </tr>
	 </#list>
	 </#if>
	  <tr>
   		  <td align="left" class="tostring"><strong>合计:</strong></td>
          <td align="center">${cnt}单</td>
          <td align="center">${sum_money}</td>
          <td align="center">${sum_ye}</td>
          <td align="center">${sum_welfare_pay_money}</td>
          <td align="center">${sum_pay_money}</td>
          <td align="center">${price_money_1002}</td>
          <td align="center">${price_money_1003}</td>
          <td align="center">${price_money_1004}</td>
          <td align="center">${price_money_1005}</td>
      </tr>
</table>

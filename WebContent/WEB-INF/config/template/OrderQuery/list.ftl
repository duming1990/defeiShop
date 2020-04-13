<style type="text/css">
.tostring{mso-style-parent:style0;mso-number-format:"\@";}
</style>
<table width="100%" border="1" cellpadding="5" cellspacing="1" >
  <tr><td align="left" colspan="10">${title}</td></tr>
  <tr>
	<th nowrap="nowrap" align="center"><strong>序号</strong></th>
	<th nowrap="nowrap" align="center"><strong>订单编号</strong></th>
	<th nowrap="nowrap" align="center"><strong>用户名</strong></th>
	<th width="8%" align="center"><strong>支付类型</strong></th>
	<th width="8%" align="center"><strong>订单金额</strong></th>
	<th width="8%" align="center"><strong>返现奖励</strong></th>
    <th width="8%" align="center"><strong>扶贫金</strong></th>
	<th width="8%" align="center"><strong>商家货款</strong></th>
	<th width="8%" align="center"><strong>公司利润</strong></th>
	<th width="8%" align="center"><strong>下单时间</strong></th>
  </tr>
	 <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
          <td align="center">${((cur_index + 1)!" ")?html}</td>
          <td align="center" class="tostring">${((cur.trade_index)!" ")?html}</td>
          <td align="center" class="tostring">${((cur.add_user_name)!" ")?html}</td>
          <td align="center">
          <#if (cur.pay_type == 1)>支付宝</#if>
          <#if (cur.pay_type == 3)>微信支付</#if>
          <#if (cur.pay_type == 0)>现金支付</#if>
          </td>
          <td align="center">${((cur.no_dis_money)!" ")?html}</td>
          <td align="center">${((cur.reward_money)!" ")?html}</td>
          <td align="center">${((cur.xiadan_user_sum-cur.res_balance)!" ")?html}</td>
          <td align="center">${((cur.entp_huokuan_bi)!" ")?html}</td>
          <td align="center">${((cur.order_money + cur.money_bi - cur.reward_money   - (cur.xiadan_user_sum - cur.res_balance) - cur.entp_huokuan_bi)!" ")?html}</td>
          <td align="center" class="tostring">${cur.add_date?string("yyyy-MM-dd HH:mm:ss")}</td>
      </tr>
	 </#list>
</#if>
</table>

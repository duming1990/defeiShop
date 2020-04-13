<style type="text/css">
.tostring{mso-style-parent:style0;mso-number-format:"\@";}
</style>
<table width="100%" border="1" cellpadding="5" cellspacing="1" >
<tr><td align="left" colspan="15">${((title)!" ")?html}</td></tr>
     <tr>
        <th width="5%">序号</th>
        <th width="7%">状态</th>
        <th width="7%">付款状态</th>
        <th width="5%">单类</th>
        <th width="7%">订单号</th>
        <th nowrap="nowrap">商品名称</th>
        <th width="7%">用户名</th>
        <th width="7%">下单时间</th>
        <th width="7%">商品金额</th>
        <th width="7%">电子币抵扣</th>
        <th width="7%">应付金额</th>
        <th width="5%">单数</th>
        <th width="7%">支付时间</th>
        <th width="7%">支付渠道</th>
        <th width="7%">支付金额</th>
      </tr>
	  <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr>
          <td align="center">${cur_index + 1}</td>
          <td align="center">
          	<#if cur.map.order_state == 10>等待发货</#if>
          	<#if cur.map.order_state == 20>已发货</#if>
          	<#if cur.map.order_state == 40>交易成功</#if>
          	<#if cur.map.order_state == 90>关闭</#if>
          </td>
          <td align="center">${cur.map.is_pay_name}</td>
          <td align="center"><#if cur.map.is_fuxiao == 1>重消单</#if><#if cur.map.is_fuxiao == 0>新单</#if></td>
          <td align="center" class="tostring">${cur.map.trade_index}</td>
          <td align="center">${cur.map.comm_name}</td>
          <td align="center">${cur.map.add_user_name}</td>
          <td align="center">${cur.map.add_date?string("yyyy-MM-dd HH:mm:ss")}</td>
          <td align="right">${cur.map.good_price?string("0")}</td>
          <td align="right">${cur.map.bi_money?string("0")}</td>
		  <td align="right">${cur.map.yf_money?string("0")}</td>
		  <td align="right">${cur.map.hz_count?string("0")}</td>
		  <td align="center">${cur.map.pay_date?string("yyyy-MM-dd HH:mm:ss")}</td>
          <td align="center">
          	<#if cur.map.pay_type == 0>电子币</#if>
          	<#if cur.map.pay_type == 1>支付宝</#if>
          	<#if cur.map.pay_type == 2>银联支付</#if>
          	<#if cur.map.pay_type == 3>微信支付</#if>
          	<#if cur.map.pay_type == 4>货款</#if>
          	<#if (cur.pay_type == 5)>现金支付</#if>
          </td>
          <td align="right">${cur.map.zf_money?string("0")}</td>
        </tr>
	 </#list>
	 </#if>
</table>
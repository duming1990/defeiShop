<style type="text/css">
.tostring{mso-style-parent:style0;mso-number-format:"\@";}
</style>
<table width="100%" border="1" cellpadding="5" cellspacing="1" >
  <tr><td align="center" colspan="6"><strong>${title}</strong></td></tr>
  <tr>
	 <th width="10%"><strong>区域</strong></th>
        <th width="10%"><strong>用户个数</strong></th>
        <th width="10%"><strong>代言人奖励</strong></th>
        <th width="10%"><strong>驿站合伙人奖励</strong></th>
        <th width="10%"><strong>普通县域合伙人奖励</strong></th>
        <th width="10%"><strong>股份县域合伙人奖励</strong></th>
  </tr>
	 <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
          <td align="center" class="tostring">${((cur.map.full_name)!" ")?html}</td>
          <td align="center">${((cur.map.user_num)!" ")?html}</td>
          <td align="center">${((cur.map.price_money_1002)!" ")?html}</td>
          <td align="center">${((cur.map.price_money_1003)!" ")?html}</td>
          <td align="center">${((cur.map.price_money_1004)!" ")?html}</td>
          <td align="center">${((cur.map.price_money_1005)!" ")?html}</td>
      </tr>
	 </#list>
	 </#if>
	  <tr>
   		  <td align="left" class="tostring"><strong>合计:</strong></td>
          <td align="center">${user_num}</td>
          <td align="center">${((price_money_1002)!" ")?html}</td>
          <td align="center">${((price_money_1003)!" ")?html}</td>
          <td align="center">${((price_money_1004)!" ")?html}</td>
          <td align="center">${((price_money_1005)!" ")?html}</td>
      </tr>
</table>

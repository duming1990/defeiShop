<style type="text/css">
.tostring{mso-style-parent:style0;mso-number-format:"\@";}
</style>
<table width="100%" border="1" cellpadding="5" cellspacing="1" >
  <tr><td align="center" colspan="4"><strong>${title}</strong></td></tr>
  <tr>
        <th width="10%"><strong>商家名称</strong></th>
        <th width="10%"><strong>订单总数</strong></th>
        <th width="10%"><strong>订单金额</strong></th>
        <th width="10%"><strong>结算金额</strong></th>
        <th width="10%"><strong>添加时间</strong></th>
        <th width="10%"><strong>确认时间</strong></th>
  </tr>
	 <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
          <td align="center" class="tostring">${((cur.entp_name)!" ")?html}</td>
          <td align="center">${((cur.map.order_count)!" ")?html}</td>
          <td align="center">${((cur.sum_order_money)!" ")?html}</td>
          <td align="center">${((cur.sum_money)!" ")?html}</td>
          <td align="center" class="tostring">
         <#if (cur.map.add_date)??>${cur.map.add_date?string("yyyy-MM-dd")}</#if>
         </td>
         <td align="center" class="tostring">
         <#if (cur.map.confirm_date)??>${cur.map.confirm_date?string("yyyy-MM-dd")}</#if>
         </td>
      </tr>
	 </#list>
	 </#if>
	  <tr>
   		  <td align="center" class="tostring"><strong>合计:</strong></td>
          <td align="center">${((order_count)!" ")?html}</td>
          <td align="center">${((sum_order_money)!" ")?html}</td>
          <td align="center">${((sum_money)!" ")?html}</td>
      </tr>
</table>

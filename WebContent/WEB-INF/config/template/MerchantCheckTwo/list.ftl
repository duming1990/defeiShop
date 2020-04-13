<table width="100%" border="1" cellpadding="5" cellspacing="1" >
     <tr>
        <th width="5%">序号</th>
   		<th width="14%">企业名称</th>
        <th width="8%">订单金额</th>
        <th width="8%">商家货款</th>
         <th width="8%">手续费</th>
        <th width="8%">结算金额</th>
       
        <th width="6%">开始时间</th>
        <th width="6%">结束时间</th>
        <th width="10%">添加时间</th>
        <th width="7%">审核状态</th>
        <th width="7%">审核时间</th>
        <th width="8%">审核意见</th>
        <th width="8%">付款备注</th>
        										
     </tr>
	  <#if (entityList)??>
	  <#list entityList as cur>
	  <tr>
	  <td>${cur_index + 1}</td>
	  <td align="left">${((cur.entp_name)!"")?replace("/\n|\r\n/g","<br>")}</td>
		<td align="center">${((cur.sum_order_money)!"")?html}</td>
     		<td align="center">${(((cur.sum_money)!"")?html)}</td>
     		<td align="center">${(((cur.cash_rate)!"")?html)}</td>
     		
     		<td align="center">${(((cur.cash_pay)!"")?html)}</td>
     		<td>	${cur.add_check_date?string("yyyy-MM-dd")}  </td>
     		
     		<td align="center">${cur.end_check_date?string("yyyy-MM-dd")}</td>
     		<td align="center">${cur.add_date?string("yyyy-MM-dd")}</td>
     		<#if (cur.is_check == 0)>
     			<td align="center">待结算</td>
     		</#if>
     		<#if (cur.is_check == -1)>
     			<td align="center" >结算失败</td>
     		</#if>
     		<#if (cur.is_check == 1)>
     			<td align="center">结算成功</td>
     		</#if>
            <#if (cur.is_check == 15)>
                <td align="center">付款成功</td>
            </#if>
            <#if (cur.is_check == 20)>
                <td align="center">退款成功</td>
            </#if>
     		<td align="center">${cur.confirm_date?string("yyyy-MM-dd")}</td>
     		<td align="center">${((cur.confirm_desc)!"")?html}</td>
     		<td align="center">${((cur.pay_remarks)!"")?html}</td>


      </tr>
	 </#list>
	 </#if>
</table>
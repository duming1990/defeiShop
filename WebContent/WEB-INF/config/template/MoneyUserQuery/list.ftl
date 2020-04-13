<style type="text/css">
.tostring{mso-style-parent:style0;mso-number-format:"\@";}
</style>
<table width="100%" border="1" cellpadding="5" cellspacing="1" >
     <tr>
        <th width="5%">序号</th>
        <th width="5%">状态</th>
	    <th width="5%">用户名</th>
	    <th nowrap="nowrap">姓名</th>
	    <th width="5%">申请时间</th>
	    <th width="5%">提现前金额</th>
	    <th width="5%">预提金额</th>
	    <th width="5%">手续费</th>
	    <th width="5%">实提金额</th>
	    <th width="5%">审核人</th>
	    <th width="5%">审核时间</th>
	    <th width="5%">审核说明</th>
	    <th width="5%">返现开户行</th>
	    <th width="5%">返现帐号</th>
	    <th width="5%">返现开户名称</th>
	    <th width="5%">联系电话</th>
	    <th width="5%">提现类型</th>
	    <th width="5%">备注</th>
     </tr>
	  <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
	  	<td align="center" nowrap="nowrap">${cur_index + 1}</td>
	    <td align="center">
			<#if cur.info_state == 0><span style="color: #060;">未审核</span></#if>
			<#if cur.info_state == 1><span style="color: #F00;">审核通过</span></#if>
			<#if cur.info_state == -1><span style="color: #F00;">审核驳回</span></#if>
			<#if cur.info_state == 2><span style="color: #F00;">已付款</span></#if>
			<#if cur.info_state == -2><span style="color: #F00;">已退款</span></#if>
		</td>
		<td align="left">${((cur.map.apply_user_name)!" ")?html}</td>
		<td align="left">${((cur.real_name)!" ")?html}</td>
		<td align="center">
		<#if (cur.add_date)??>${cur.add_date?string("yyyy-MM-dd")}</#if></td>
		<td align="left">${((cur.cash_count_before)!" ")?html}</td>
		<td align="left">${((cur.cash_count)!" ")?html}</td>
		<td align="center">${((cur.cash_count-cur.cash_pay)!" ")?html}</td>
		<td align="center">${((cur.cash_pay)!" ")?html}</td>
		<td align="left">${((cur.map.audit_user_name)!" ")?html}</td>
		<td align="center">
		<#if (cur.audit_date)??>${cur.audit_date?string("yyyy-MM-dd")}</#if></td>
		<td align="center">${((cur.audit_memo)!" ")?html}</td>
		<td align="center">${((cur.bank_name)!" ")?html}</td>
		<td align="center" class="tostring">${((cur.bank_account?replace(' ', ''))!" ")?html}</td>
		<td align="center">${((cur.bank_account_name)!" ")?html}</td>
		<td align="center">${((cur.mobile)!" ")?html}</td>
		<td align="center">
			<#if cur.cash_type == 10><span style="color: #060;">余额提现</span></#if>
			<#if cur.cash_type == 30><span style="color: #F00;">货款提现</span></#if>
			<#if cur.cash_type == 40><span style="color: #F00;">福利金提现</span></#if>
		</td>
		<td align="center">${((cur.add_memo)!" ")?html}</td>
      </tr>
	 </#list>
	 </#if>
	
</table>
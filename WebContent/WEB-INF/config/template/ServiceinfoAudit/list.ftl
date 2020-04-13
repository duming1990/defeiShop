<table width="100%" border="1" cellpadding="5" cellspacing="1" >
     <tr>
        <th width="5%">序号</th>
        <th>合伙人名称</th>
        <th width="8%">对公账户名称</th>
        <th width="10%">对公账号</th>
        <th width="12%">开户行名称</th>
        <th width="8%">服务编号</th>
        <th width="10%">联系电话</th>
        <th width="12%">所属地区</th>
        <th width="7%">添加时间</th>
        <th width="6%">更新时间</th>
        <th width="6%">审核时间</th>
        <th width="6%">是否审核</th>
        <th width="8%">是否生效</th>
     </tr>
	  <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
	  	<td align="center" nowrap="nowrap">${cur_index + 1}</td>
	  	<td align="center">${((cur.servicecenter_name)!" ")?html}</td>
	  	<td align="center">${((cur.brought_account)!" ")?html}</td>
	  	<td align="center">${((cur.brought_account_no)!" ")?html}</td>
	  	<td align="center">${((cur.bank_name)!" ")?html}</td>
	  	<td align="center">${((cur.servicecenter_no)!" ")?html}</td>
	  	<td align="center">${((cur.servicecenter_linkman_tel)!" ")?html}</td>
	  	<td align="center">${((cur.map.full_name)!" ")?html}</td>
	  	<td align="center">${cur.add_date?string("yyyy-MM-dd")}</td>
	  	<td align="center">${cur.update_date?string("yyyy-MM-dd")}</td>
	  	<td align="center">${cur.audit_date?string("yyyy-MM-dd")}</td>
	  	<td align="center">
	  	<#if (cur.audit_state == 1)>审核通过</#if>
	  	<#if (cur.audit_state == 0)>未审核</#if>
	  	<#if (cur.audit_state == -1)>审核不通过</#if>
	  	</td>
	 	<td align="center">
	 	<#if (cur.effect_state == 0)>未生效</#if>
	 	<#if (cur.effect_state == 1)>已生效</#if>
	 	</td>
      </tr>
	 </#list>
	 </#if>
</table>
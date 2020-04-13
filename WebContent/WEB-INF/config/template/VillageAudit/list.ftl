<table width="100%" border="1" cellpadding="5" cellspacing="1" >
     <tr>
        <th width="5%">序号</th>
     	<th width="10%">村站名称</th>
        <th width="8%">村站用户</th>
        <th width="8%">个人用户</th>
        <th width="8%">站主姓名</th>
        <th width="8%">村站成员数</th>
        <th width="15%">所属地区</th>
        <th width="8%">村站运营时间</th>
        <th width="8%">添加时间</th>
        <th width="8%">审核状态</th>
     </tr>
	  <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
	  	<td align="center" nowrap="nowrap">${cur_index + 1}</td>
	  	<td align="center">${((cur.village_name)!" ")?html}</td>
	  	<td align="center">${((cur.map.village_no)!" ")?html}</td>
	  	<td align="center">${((cur.map.village_person_no)!" ")?html}</td>
	  	<td align="center">${((cur.owner_name)!" ")?html}</td>
	  	<td align="center">${((cur.count)!" ")?html}</td>
	  	<td align="center">${((cur.map.full_name)!" ")?html}</td>
	  	<td align="center">${(cur.service_operation_date?string("yyyy-MM-dd"))}-${(cur.service_operation_date_end?string("yyyy-MM-dd"))}</td>
	  	<td align="center">${cur.add_date?string("yyyy-MM-dd")}</td>
	  	<td align="center">
	  	<#if (cur.audit_state == 1)>审核通过</#if>
	  	<#if (cur.audit_state == 0)>未审核</#if>
	  	<#if (cur.audit_state == -1)>审核不通过</#if>
	  	</td>
      </tr>
	 </#list>
	 </#if>
</table>
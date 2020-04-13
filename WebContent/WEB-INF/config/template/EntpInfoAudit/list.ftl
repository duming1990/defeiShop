<table width="100%" border="1" cellpadding="5" cellspacing="1" >
     <tr>
        <th width="5%">序号</th>
   		<th width="14%">店铺名称</th>
        <th width="8%">商家编号</th>
        <th width="8%">商家类型</th>
        <th width="8%">用户名</th>
        <th width="6%">姓名</th>
        <th width="6%">联系电话</th>
        <th width="10%">所属地区</th>
        <th width="7%">添加时间</th>
        <th width="7%">审核时间</th>
        <th width="8%">是否审核</th>
     </tr>
	  <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
	  	<td align="center" nowrap="nowrap">${cur_index + 1}</td>
	  	<td align="center">${((cur.entp_name)!" ")?html}</td>
	  	<td align="center">${((cur.entp_no)!" ")?html}</td>
	  	<td align="center">	  	
	  	<#if (cur.entp_type == 10)>普通店铺</#if>
        <#if (cur.entp_type == 20)>合伙人店铺</#if>
	  	</td>
	  	<td align="center">${((cur.map.userInfoTemp.user_name)!" ")?html}</td>
	  	<td align="center">${((cur.entp_linkman)!" ")?html}</td>
	  	<td align="center">${((cur.entp_tel)!" ")?html}</td>
	  	<td align="center">${((cur.map.full_name)!" ")?html}</td>
	  	<td align="center">${cur.add_date?string("yyyy-MM-dd")}</td>
	  	<td align="center">${cur.audit_date?string("yyyy-MM-dd")}</td>
	  	<td align="center">
	  	<#if (cur.audit_state == 2)>审核通过</#if>
	  	<#if (cur.audit_state == 0)>未审核</#if>
	  	<#if (cur.audit_state == -2)>审核不通过</#if>
	  	</td>
      </tr>
	 </#list>
	 </#if>
</table>
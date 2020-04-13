<style type="text/css">
.tostring{mso-style-parent:style0;mso-number-format:"\@";}
</style>
<table width="100%" border="1" cellpadding="5" cellspacing="1" >
     <tr>
        <th width="5%">序号</th>
        <th width="8%">结算台编码</th>
        <th width="8%">结算台名称</th>
        <th width="14%">企业名称</th>
        <th width="6%">密码</th>


     </tr>
	  <#if (baseDataList)??>
	  <#list baseDataList as cur>
	  	<tr align="center">
	  	<td align="center" nowrap="nowrap">${cur_index + 1}</td>
	  	<td align="center" class="tostring">${((cur.pre_varchar_2)!" ")?html}</td>
	  	<td align="center">${((cur.type_name)!" ")?html}</td>
	  	<td align="center">${((cur.pre_varchar_1)!" ")?html}</td>
	  	<td align="center">${((cur.map.decPassword)!" ")?html}</td>
	
      </tr>
	 </#list>
	 </#if>
	
</table>
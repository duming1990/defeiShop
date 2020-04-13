<style type="text/css">
.tostring{mso-style-parent:style0;mso-number-format:"\@";}
</style>
<table width="100%" border="1" cellpadding="5" cellspacing="1" >
     <tr>
        <th width="5%">序号</th>
        <th nowrap="nowrap">商品名称</th>
        <th width="15%">销量总量</th>
        <th width="15%">销售总金额</th>
       

     </tr>
	  <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
	  	<td align="center" nowrap="nowrap">${cur_index + 1}</td>
	  	<td align="center">${((cur.map.comm_name)!" ")?html}</td>
	  	<td align="center">${((cur.map.good_count)!" ")?html}</td>
	  	<td align="center">${((cur.map.good_sum_price)!" ")?html}</td>
      </tr>
	 </#list>
	 </#if>
	
</table>
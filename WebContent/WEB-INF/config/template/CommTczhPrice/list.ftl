<style type="text/css">
.tostring{mso-style-parent:style0;mso-number-format:"\@";}
</style>
<table width="100%" border="1" cellpadding="5" cellspacing="1" >
     <tr>
        <th width="5%">序号</th>
        <th nowrap="nowrap">商品名称</th>
        <th width="15%">商品条形码</th>
        <th width="15%">规格条形码</th>
        <th width="8%">进货价/元</th>
        <th width="10%">商品库存</th>

     </tr>
	  <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
	  	<td align="center" nowrap="nowrap">${cur_index + 1}</td>
	  	<td align="center">${((cur.map.comm_name)!" ")?html}</td>
	  	<td align="center" class="tostring">${((cur.map.barcode)!" ")?html}</td>
	  	<td align="center" class="tostring">${((cur.comm_barcode)!" ")?html}</td>
	  	<td align="center">${((cur.cost_price)!" ")?html}</td>
	  	<td align="center">${((cur.inventory)!" ")?html}</td>
      </tr>
	 </#list>
	 </#if>
	
</table>
<style type="text/css">
.tostring{mso-style-parent:style0;mso-number-format:"\@";}
</style>
<table width="100%" border="1" cellpadding="5" cellspacing="1" >
     <tr>
        <th width="5%">序号</th>
        <th nowrap="nowrap">商品名称</th>
        <th width="15%">商品条形码</th>
        <th width="15%">进货时间</th>
        <th width="10%">进货数量</th>
        <th width="8%">进货价/元</th>
        <th width="8%">售价/元</th>

     </tr>
	  <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
	  	<td align="center" nowrap="nowrap">${cur_index + 1}</td>
	  	<td align="center">${((cur.comm_name)!" ")?html}</td>
	  	<td align="center" class="tostring">${((cur.comm_barcode)!" ")?html}</td>
	  	<td align="center">${(cur.add_date)?string("yyyy-MM-dd")}&nbsp;</td>
	  	<td align="center">${((cur.inventory)!" ")?html}</td>
	  	<td align="center">${((cur.price_ref)!" ")?html}</td>
	  	<td align="center">${((cur.sale_price)!" ")?html}</td>	
      </tr>
	 </#list>
	 </#if>
	
</table>
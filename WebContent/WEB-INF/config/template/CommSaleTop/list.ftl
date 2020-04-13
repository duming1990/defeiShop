<style type="text/css">
.tostring{mso-style-parent:style0;mso-number-format:"\@";}
</style>
<table width="100%" border="1" cellpadding="4" cellspacing="1" >
  <tr><td align="center" colspan="4"><strong>${title}</strong></td></tr>
  <tr>
  	<th width="5%" nowrap="nowrap"><strong>排名</strong></th>
    <th nowrap="20%"><strong>商品名称</strong></th>
    <th width="10%"><strong>销量总量</strong></th>
    <th width="10%"><strong>销售总金额</strong></th>
  </tr>
	 <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
          <td align="left">${((cur_index + 1)!" ")?html}</td>
          <td align="left" class="tostring">${((cur.comm_name)!" ")?html}</td>
          <td align="center">
           <#if (saleCount == 1)>0</#if>
           <#if (saleCount != 1)>${((cur.map.sum_good_count)!" ")?html}</#if>
          </td>
          <td align="center">
           <#if (saleCount == 1)>0</#if>
           <#if (saleCount != 1)>${((cur.map.sum_good_price)!" ")?html}</#if>
          </td>
      </tr>
	 </#list>
</#if>
</table>

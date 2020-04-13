<style type="text/css">
.tostring{mso-style-parent:style0;mso-number-format:"\@";}
</style>
<table width="100%" border="1" cellpadding="5" cellspacing="1" >
 <tr><td align="left" colspan="8">${title}</td></tr>
     <tr>
        <th width="nowrap">序号</th>
        <th width="nowrap">卡号</th>
        <th width="nowrap">密码</th>
        <th width="8%">卡类型</th>
        <th width="8%">额度</th>
        <th width="8%">有效期开始时间</th>
        <th width="8%">有效期结束时间</th>
        <th width="10%">生成时间</th>
     </tr>
	  <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
	  	<td align="center" nowrap="nowrap">${cur_index + 1}</td>
	  	<td align="center">${((cur.card_no)!" ")?html}</td>
	  	<td align="center" class="tostring">${((cur.map.card_pwd)!" ")?html}</td>
	  	<td align="center">
			<#if cur.card_type == 10>实体卡</#if>
			<#if cur.card_type == 20>电子卡</#if>
		</td>
	  	<td align="center">${((cur.card_amount)!" ")?html}</td>
		<td align="center" class="tostring">${(cur.start_date)?string("yyyy-MM-dd")}&nbsp;</td>
		<td align="center" class="tostring">${(cur.end_date)?string("yyyy-MM-dd")}&nbsp;</td>
		<td align="center" class="tostring">${(cur.add_date)?string("yyyy-MM-dd")}&nbsp;</td>
      </tr>
	 </#list>
	 </#if>
</table>
<style type="text/css">
.tostring{mso-style-parent:style0;mso-number-format:"\@";}
</style>
<table width="100%" border="1" cellpadding="5" cellspacing="1" >
 <tr><td align="left" colspan="9">${title}</td></tr>
     <tr>
        <th width="5%" align="center">序号</th>
        <th width="5%" align="center">类型</th>
        <th nowrap="nowrap" align="center">订单编号</th>
        <th width="5%" align="center">下单用户</th>
        <th width="5%" align="center">奖励用户</th>
        <th width="5%" align="center">操作前余额</th>
        <th width="5%" align="center">本次余额</th>
        <th width="5%" align="center">操作后余额</th>
        <th width="10%" align="center">操作时间</th>
     </tr>
	  <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
	  	<td align="center" nowrap="nowrap">${cur_index + 1}</td>
	  	<td align="center">${((cur.map.bi_get_memo)!" ")?html}</td>
	  	<td align="center" class="tostring">${((cur.map.trade_index)!" ")?html}</td>
	  	<td align="center">${((cur.map.add_user_name)!" ")?html}</td>
	  	<td align="center">${((cur.map.add_uname)!" ")?html}</td>
	  	<td align="center">${((cur.map.bi_no_before)!" ")?html}</td>
	  	<td align="center">${((cur.map.bi_no)!" ")?html}</td>
	  	<td align="center">${((cur.map.bi_no_after)!" ")?html}</td>
		<td align="center" class="tostring">${(cur.map.add_date)?string("yyyy-MM-dd")}&nbsp;</td>
      </tr>
	 </#list>
	 </#if>
</table>
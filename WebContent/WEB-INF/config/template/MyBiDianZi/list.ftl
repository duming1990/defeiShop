<style type="text/css">
.tostring{mso-style-parent:style0;mso-number-format:"\@";}
</style>
<table width="100%" border="1" cellpadding="5" cellspacing="1" >
  <tr><td align="left" colspan="5">${title}</td></tr>
  <tr>
    <th nowrap="nowrap" align="center"><strong>序号</strong></th>
    <th nowrap="nowrap" align="center"><strong>类型</strong></th>
    <th nowrap="nowrap" align="center"><strong>操作前金额</strong></th>
    <th nowrap="nowrap" align="center"><strong>本次金额</strong></th>
    <th nowrap="nowrap" align="center"><strong>操作时间</strong></th>
  </tr>
	 <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
          <td align="center">${((cur_index + 1)!" ")?html}</td>
          <td align="center">${((cur.map.bi_get_name)!" ")?html}</td>
          <td align="center">${((cur.bi_no_before)!" ")?html}</td>
          <td align="center">${((cur.bi_no)!" ")?html}</td>
          <td align="center" class="tostring">${cur.add_date?string("yyyy-MM-dd HH:mm:ss")}</td>
      </tr>
	 </#list>
</#if>
</table>

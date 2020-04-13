<style type="text/css">
.tostring{mso-style-parent:style0;mso-number-format:"\@";}
</style>
<table width="100%" border="1" cellpadding="5" cellspacing="1" >
  <tr><td align="left" colspan="13">${title}</td></tr>
  <tr>
  	  <th>序号</th>
	  <th>用户名</th>
      <th width="25%">类型</th>
      <th>操作前金额</th>
      <th>本次金额</th>
      <th>操作后金额</th>
      <th>派发消费者金额</th>
      <th>操作时间</th>
  </tr>
	 <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
          <td align="left">${((cur_index + 1)!" ")?html}</td>
          <td align="left" class="tostring">${((cur.add_uname)!" ")?html}</td>
          <td align="center">
          <#if (cur.bi_get_type == 230)>增值券充值</#if>
          <#if (cur.bi_get_type == -130)>增值券派送</#if>
          </td>
          <td align="center">${((cur.bi_no_before/rmb_to_fanxianbi_rate)!" ")?html}</td>
          <td align="center">
          <#if (cur.bi_get_type == 230)>+${((cur.bi_no/rmb_to_fanxianbi_rate)!" ")?html}</#if>
          <#if (cur.bi_get_type == -130)>-${((cur.bi_no/rmb_to_fanxianbi_rate)!" ")?html}</#if>
          </td>
          <td align="center">${((cur.bi_no_after/rmb_to_fanxianbi_rate)!" ")?html}</td>
          <td align="center">
          <#if (cur.bi_get_type == 230)>——</#if>
          <#if (cur.bi_get_type == -130)>${((cur.map.Xiadan_user_balance)!" ")?html}</#if>
          </td>
          <td align="center" class="tostring">${cur.add_date?string("yyyy-MM-dd HH:mm:ss")}</td>
      </tr>
	 </#list>
	</#if>
</table>

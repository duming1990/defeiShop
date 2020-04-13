<style type="text/css">
.tostring{mso-style-parent:style0;mso-number-format:"\@";}
</style>
<table width="100%" border="1" cellpadding="5" cellspacing="1" >
  <tr><td align="center" colspan="5"><strong>${title}</strong></td></tr>
  <tr>
	 <th width="10%">区域</th>
        <th width="10%">贫困户人数</th>
        <th width="10%">待发扶贫金总额</th>
        <th width="10%">已发扶贫金总额</th>
        <th width="10%">扶贫金总额</th>
  </tr>
	 <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
          <td align="center" class="tostring">${((cur.map.full_name)!" ")?html}</td>
          <td align="center">${((cur.map.poor_num)!" ")?html}</td>
          <td align="center">${((cur.map.sum_bi_aid)!" ")?html}</td>
          <td align="center">${((cur.map.sum_bi_aid_sended)!" ")?html}</td>
          <td align="center">${((cur.map.sum_bi_aid + cur.map.sum_bi_aid_sended)!" ")?html}</td>
      </tr>
	 </#list>
	 </#if>
	  <tr>
   		  <td align="left" class="tostring"><strong>合计:</strong></td>
          <td align="center">${user_num}</td>
          <td align="center">${((sum_bi_aid)!" ")?html}</td>
          <td align="center">${((sum_bi_aid_sended)!" ")?html}</td>
          <td align="center">${((sum_money)!" ")?html}</td>
      </tr>
</table>

<style type="text/css">
.tostring{mso-style-parent:style0;mso-number-format:"\@";}
</style>
<table width="100%" border="1" cellpadding="5" cellspacing="1" >
  <tr><td align="center" colspan="2"><strong>${title}</strong></td></tr>
  <tr>
	 <th width="10%"><strong>区域</strong></th>
        <th width="10%"><strong>用户个数</strong></th>
  </tr>
     <#assign user_num = 0>
	 <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
          <td align="center" class="tostring">${((cur.map.full_name)!" ")?html}</td>
          <td align="center">${((cur.map.user_num)!" ")?html}</td>
          
          <#assign user_num ="${user_num?number + cur.map.user_num?number}">
      </tr>
	 </#list>
	 </#if>
	  <tr>
   		  <td align="center" class="tostring"><strong>合计:</strong></td>
          <td align="center">${user_num}</td>
      </tr>
</table>

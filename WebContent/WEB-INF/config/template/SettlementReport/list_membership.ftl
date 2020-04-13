<style type="text/css">
.tostring{mso-style-parent:style0;mso-number-format:"\@";}
</style>
<table width="100%" border="1" cellpadding="5" cellspacing="1" >
  <tr><td align="center" colspan="5"><strong>${title}</strong></td></tr>
  <tr>
        <th width="18%"><strong>会员名称</strong></th>
        <th width="13%"><strong>手机号</strong></th>
        <th width="8%"><strong>缴费时间</strong></th>
        <th width="10%"><strong>缴费金额</strong></th>
        <th width="10%"><strong>缴费方式</strong></th>
  </tr>
	 <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
          <td align="center" class="tostring">${((cur.map.user_name)!" ")?html}</td>
          <td align="center">${((cur.map.mobile)!" ")?html}</td>
         <td align="center" class="tostring">
         <#if (cur.pay_date)??>${cur.pay_date?string("yyyy-MM-dd HH:mm:ss")}</#if>
         </td>
          <td align="center">${((cur.order_money)!" ")?html}</td>
          <td align="center">
           <#if (cur.pay_type)??>
           <#if (cur.pay_type == 0)>后台升级</#if>
           <#if (cur.pay_type == 1)>支付宝</#if>
           <#if (cur.pay_type == 3)>微信支付</#if>
           <#else>
           	人工升级
           </#if>
          </td>
      </tr>
	 </#list>
	 </#if>
</table>

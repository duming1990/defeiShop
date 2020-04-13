<table width="100%" border="1" cellpadding="5" cellspacing="1" >
   <#if (entityList)??>
  <tr>
  	<td align="center" colspan="4">${((title)!" ")?html}</td>
  </tr>
  <tr>
  	<td align="left" colspan="4">${((Sum1)!" ")?html}${((entity2.map.sum_good_num)!" ")?html}${((Sum2)!" ")?html}${((entity2.map.sum_good_money)!" ")?html}元。</td>
  </tr>
  
  <tr>
    <th height="30" align="center" bgcolor="#fff2dc"><font class="bigall">序号</font></th>
    <th height="30" align="center" bgcolor="#fff2dc"><font class="bigall">商品名称</font></th>
    <th width="15%" height="30" align="center" bgcolor="#fff2dc"><font class="bigall">商品合计销售数量</font></th>
    <th width="30%" height="30" align="center" bgcolor="#fff2dc"><font class="bigall">商品合计销售金额(元)</font></th>
  </tr>
   <#if (entityList)??>
  <#list entityList as cur>
  <#list cur.entpOrderInfoDetailslist as cur1>
  <tr>
    <td align="center">${cur1_index + 1}</td>
    <td align="center">${((cur1.comm_name)!" ")?html}</td>
    <td align="center">${((cur1.map.sum_good_num)!" ")?html}</td>
    <td align="center">${((cur1.map.sum_good_money)!" ")?html}</td>
  </tr>
 </#list>
 </#list>
</#if>
</#if>

<#if (entityListAdmin)??>
  <tr>
  	<td align="center" colspan="3">${((title)!" ")?html}</td>
  </tr>
  <tr>
  	<td align="left" colspan="3">${((Sum1)!" ")?html}${((entityAdmin.map.sum_good_num)!" ")?html}${((Sum2)!" ")?html}${((entityAdmin.map.sum_good_money)!" ")?html}元。</td>
  </tr>
  
  <tr>
    <th height="30" align="center" bgcolor="#fff2dc"><font class="bigall">序号</font></th>
    <th height="30" align="center" bgcolor="#fff2dc"><font class="bigall">企业名称</font></th>
    <th width="15%" height="30" align="center" bgcolor="#fff2dc"><font class="bigall">订单统计</font></th>
  </tr>
   <#if (entityListAdmin)??>
  <#list entityListAdmin as cur>
  
  <tr>
    <td align="center">${cur_index + 1}</td>
    <td align="center">${((cur.map.entp_name)!" ")?html}</td>
    <td align="center">
    <table width="100%" border="1" cellpadding="1" cellspacing="1">
    <tr>
    <th width="40%" nowrap="nowrap"><font class="bigall">产品名称</font></th>
    <th width="30%" nowrap="nowrap"><font class="bigall">产品合计销售数量</font></th>
    <th width="30%" nowrap="nowrap"><font class="bigall">产品合计销售金额(元)</font></th>
    </tr>
    <#list cur.entpOrderInfoDetailslist as cur1>
     <tr align="center">
      <td align="center">${((cur1.pd_name)!" ")?html}</td>
      <td align="center">${((cur1.map.sum_good_num)!" ")?html}</td>
      <td align="center">${((cur1.map.sum_good_money)!" ")?html}</td>
       </tr>
    </#list>
    </table>
    </td>
  </tr>
 </#list>
   </#if>
   </#if>
</table>
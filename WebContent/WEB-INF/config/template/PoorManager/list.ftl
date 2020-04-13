<style type="text/css">
.tostring{mso-style-parent:style0;mso-number-format:"\@";}
</style>
<table width="100%" border="1" cellpadding="5" cellspacing="1" >
     <tr>
       <th width="3%">序号</th>
        <th width="6%">真实姓名</th>
        <th width="8%">用户编码</th>
        <th width="2%">性别</th>
        <th width="2%">民族</th>
        <th width="2%">家庭人口</th>
        <th width="8%">家庭住址</th>
        <th width="10%">所属村站</th>
        <th width="6%">电话</th>
        <th width="15%">身份证</th>
        <th width="15%">开户银行</th>
        <th width="15%">开户名</th>
        <th width="15%">开户账户</th>
        <th width="8%">添加时间</th>
        <th width="8%">是否审核</th>
     </tr>
	  <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
	  	<td align="center" nowrap="nowrap">${cur_index + 1}</td>
	  	<td align="center">${((cur.real_name)!" ")?html}</td>
	  	<td align="center">${((cur.user_name)!" ")?html}</td>
		<td align="center">
	 	<#if (cur.sex == 0)>男</#if>
	 	<#if (cur.sex == 1)>女</#if>
	 	</td>
	 	<td align="center">${((cur.nation)!" ")?html}</td>
	  	<td align="center">${((cur.family_num)!" ")?html}</td>
	  	<td align="center">${((cur.addr)!" ")?html}</td>
	  	<td align="center">${((cur.map.villageInfo.village_name)!" ")?html}</td>
	  	<td align="center">${((cur.mobile)!" ")?html}</td>
	  	<td align="center" class="tostring">${((cur.id_card)!" ")?html}</td>
	  	<td align="center" >${((cur.map.user.bank_name)!" ")?html}</td>
	  	<td align="center">${((cur.map.user.bank_account_name)!" ")?html}</td>
	  	<td align="center" class="tostring">${((cur.map.user.bank_account)!" ")?html}</td>
	 	<td align="center">${cur.add_date?string("yyyy-MM-dd")}</td>
	  	<td align="center">
	  	<#if (cur.audit_state == 1)>审核通过</#if>
	  	<#if (cur.audit_state == 0)>未审核</#if>
	  	<#if (cur.audit_state == -1)>审核不通过</#if>
	  	</td>
      </tr>
	 </#list>
	 </#if>
	
</table>
<style type="text/css">
.tostring{mso-style-parent:style0;mso-number-format:"\@";}
</style>
<table width="100%" border="1" cellpadding="5" cellspacing="1" >
     <tr>
       <th width="3%">序号</th>
        <th width="10%">所在地</th>
        <th width="6%">真实姓名</th>
         <th width="10%">账户信息</th>
        <th width="10%">手机号</th>
        <th width="8%">用户编码</th>
        <th width="2%">性别</th>
        <th width="10%">待发扶贫金总额</th>
        <th width="10%">已发扶贫金总额</th>
        <th width="10%">扶贫金总额</th>
     </tr>
     
	  <#if (entityList)??>
	  <#list entityList as cur>
	  <#if ((cur.map.bi_aid)?? && (cur.map.bi_aid >= limit_money))>
	  <tr align="center" style="background-color: #bdce22;">
	  <#else>
	  <tr align="center">
	  </#if>
	  	<td align="center" nowrap="nowrap">${cur_index + 1}</td>
	  	<td align="center">${((cur.addr)!" ")?html}</td>
	  	<td align="center">${((cur.real_name)!" ")?html}</td>
	  		<td align="center">
	  		
	  		开户银行：${((cur.map.userInfo.bank_name)!" ")?html}<br/>
            	开户账号：${((cur.map.userInfo.bank_account)!" ")?html}<br/>
           		 开户名：${((cur.map.userInfo.bank_account_name)!" ")?html}
	  		</td>
	  	<td align="center">${((cur.mobile)!" ")?html}</td>
	  	<td align="center">${((cur.user_name)!" ")?html}</td>
		<td align="center">
	 	<#if (cur.sex == 0)>男</#if>
	 	<#if (cur.sex == 1)>女</#if>
	 	</td>
	  	<td align="center">${((cur.map.bi_aid)!" ")?html}</td>
          <td align="center">${((cur.map.bi_aid_sended)!" ")?html}</td>
          <td align="center">${((cur.map.bi_aid + cur.map.bi_aid_sended)!" ")?html}</td>
      </tr>
	 </#list>
	 </#if>
</table>
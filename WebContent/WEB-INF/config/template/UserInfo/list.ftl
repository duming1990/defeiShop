<style type="text/css">
.tostring{mso-style-parent:style0;mso-number-format:"\@";}
</style>
<table width="100%" border="1" cellpadding="5" cellspacing="1" >
     <tr>
        <th width="5%">序号</th>
        <th nowrap="nowrap">登录名</th>
        <th nowrap="nowrap">真实姓名</th>
        <th width="6%">手机</th>
        <th width="7%">个人积分</th>
        <th width="7%">联盟积分</th>
        <th width="7%">会员佣金</th>
        <th width="7%">余额</th>
        <th width="7%">货款</th>
        <th width="7%">消费券</th>
        <th width="7%">增值券</th>
        <th width="8%">用户类型</th>
        <th width="5%">商家</th>
        <th width="5%">运营中心</th>
        <th width="7%">服务专员</th>
        <th width="9%">添加时间</th>
        <th width="9%">最后登录时间</th>
     </tr>
	  <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
	  	<td align="center" nowrap="nowrap">${cur_index + 1}</td>
	  	<td align="center">${((cur.user_name)!" ")?html}</td>
	  	<td align="center">${((cur.real_name)!" ")?html}</td>
	  	<td align="center">${((cur.mobile)!" ")?html}</td>
	  	<td align="center">${((cur.cur_score)!" ")?html}</td>
	  	<td align="center">${((cur.user_score_union)!" ")?html}</td>
	  	<td align="center">${((cur.bi_dianzi + cur.bi_huokuan)!" ")?html}</td>
	  	<td align="center">${((cur.bi_dianzi)!" ")?html}</td>
	  	<td align="center">${((cur.bi_huokuan)!" ")?html}</td>
	  	<td align="center">${(((cur.bi_dianzi_lock)/100)!" ")?html}</td>
	  	
	  	<td align="center">${cur.leiji_money_entp/rmb_to_fanxianbi_rate}</td>
	    <td align="center">
			<#if cur.user_type == 2>注册会员</#if>
			<#if cur.user_type == 1>系统超级管理员</#if>
			<#if cur.user_type == 100>后台管理人员</#if>
			<#if cur.user_type == 4>大区管理员</#if>
			<#if cur.user_type == 4>大区管理员</#if>
			<#if cur.user_type == 6>客服人员</#if>
			<#if cur.user_type == 7>产品部人员</#if>
			<#if cur.user_type == 8>财务人员</#if>
			<#if cur.user_type == 9>企划部人员</#if>
			<#if cur.user_type == 10>人力资源部人员</#if>
			<#if cur.user_type == 11>市场部人员</#if>
		</td>
	    <td align="center">
			<#if cur.is_entp == 0><span style="color: #F00;">否</span></#if>
			<#if cur.is_entp == 1><span style="color: #060;">是</span></#if>
		</td>
		
	    <td align="center">
			<#if cur.is_fuwu == 0><span style="color: #F00;">否</span></#if>
			<#if cur.is_fuwu == 1><span style="color: #060;">是</span></#if>
		</td>
	    <td align="center">
			<#if cur.is_city_manager == 0><span style="color: #F00;">否</span></#if>
			<#if cur.is_city_manager == 1><span style="color: #060;">是</span></#if>
		</td>
		<td align="center">${(cur.add_date)?string("yyyy-MM-dd")}&nbsp;</td>
		
		<td align="center">
		<#if cur.last_login_time?exists>${(cur.last_login_time)?string("yyyy-MM-dd")}&nbsp;</#if>
		</td>
	
      </tr>
	 </#list>
	 </#if>
	
</table>
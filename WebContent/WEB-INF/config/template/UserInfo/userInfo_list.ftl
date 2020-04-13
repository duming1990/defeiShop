<table width="100%" border="1" cellpadding="5" cellspacing="1" >
  <tr>
  	<td align="center" colspan="8">${((title)!" ")?html}</td>
  </tr>
  <tr>
    <td height="30" align="center" bgcolor="#fff2dc"><font class="bigall">序号</font></td>
    <td height="30" align="center" bgcolor="#fff2dc"><font class="bigall">登录名</font></td>
    <td width="15%" height="30" align="center" bgcolor="#fff2dc"><font class="bigall">真实名称</font></td>
    <#if (userInfoList2)??><td width="30%" height="30" align="center" bgcolor="#fff2dc"><font class="bigall">电子邮箱</font></td></#if>
    <#if (userInfoList3)??><td width="10%" height="30" align="center" bgcolor="#fff2dc"><font class="bigall">所属企业</font></td>
    <td width="10%" height="30" align="center" bgcolor="#fff2dc"><font class="bigall">企业联系人</font></td></#if>
    <td width="30%" height="30" align="center" bgcolor="#fff2dc"><font class="bigall">手机号码</font></td>
    <td width="30%" height="30" align="center" bgcolor="#fff2dc"><font class="bigall">身份证号</font></td>
    <td width="30%" height="30" align="center" bgcolor="#fff2dc"><font class="bigall">性别</font></td>
    <td width="10%" height="30" align="center" bgcolor="#fff2dc"><font class="bigall">添加时间</font></td>
  </tr>
  <#if (userInfoList2)??>
  <#list userInfoList2 as cur>
  <tr>
    <td align="center">${cur_index + 1}</td>
    <td align="center">${((cur.user_name)!" ")?html}</td>
    <td align="center">${((cur.real_name)!" ")?html}</td>
    <td align="center">${((cur.email)!" ")?html}</td>
    <td align="center">${((cur.mobile)!" ")?html}</td>
    <td align="center"  nowrap="nowrap">${((cur.id_card)!" ")?html}&nbsp;</td>
    <td align="center"><#if (cur.sex)??>
     <#if (cur.sex == 0)>男</#if>
     <#if (cur.sex == 1)>女</#if>
    </#if>
    </td>
    <td align="center">${(cur.add_date)?string("yyyy-MM-dd")}</td>
  </tr>
 </#list>
 </#if>
 <#if (userInfoList3)??>
 <#list userInfoList3 as cur>
  <tr>
    <td align="center">${cur_index + 1}</td>
    <td align="center">${((cur.user_name)!" ")?html}</td>
    <td align="center">${((cur.real_name)!" ")?html}</td>
    <td align="center">${((cur.own_entp_name)!" ")?html}</td>
    <td align="center">${((cur.map.entp_linkman)!" ")?html}</td>
    <td align="center">${((cur.mobile)!" ")?html}</td>
    <td align="center"  nowrap="nowrap">${((cur.id_card)!" ")?html}&nbsp;</td>
    <td align="center"><#if (cur.sex)??>
     <#if (cur.sex == 0)>男</#if>
     <#if (cur.sex == 1)>女</#if>
    </#if>
    </td>
    <td align="center">${(cur.add_date)?string("yyyy-MM-dd")}</td>
  </tr>
 </#list>
  </#if>
</table>
<table width="100%" border="1" cellpadding="5" cellspacing="1" >
     <tr>  
        <th width="5%">序号</th>
        <th width="8%">命令名称</th>
        <th width="8%">命令类型</th>
        <th width="14%">设备类型</th>
        <th width="6%">命令内容</th>
        <th width="8%">添加时间</th>
        <th width="6%">关联厂商名称</th>
        

     </tr>
	  <#if (entityList)??>
	  <#list entityList as cur>
	  	<tr align="center">
	  	<td align="center" nowrap="nowrap">${cur_index + 1}</td>
	  	<td align="center">${((cur.command_name)!" ")?html}</td>
	  	<td align="center"><#if (cur.command_type)??>
        <#if cur.command_type ==  0>标准模式</#if>
        <#if cur.command_type ==  1>天线切换</#if>
        <#if cur.command_type ==  2>标签读</#if>
        <#if cur.command_type ==  3>标签写</#if>
        <#if cur.command_type ==  4>心跳</#if>
       </td>
       </#if>
       <td align="center"><#if (cur.device_type)??>
        <#if (cur.device_type == 0)>RFID</#if>
        <#if (cur.device_type == 1)>门禁</#if>
        </#if>
       </td>
	  	<td align="center">${((cur.command_content)!" ")?html}</td>
	  	<td align="center">${(cur.add_date)?string("yyyy-MM-dd")}&nbsp;</td>
        <td align="center">${((cur.link_factory_name)!" ")?html}</td>
      </tr>
	 </#list>
	 </#if>
	
</table>
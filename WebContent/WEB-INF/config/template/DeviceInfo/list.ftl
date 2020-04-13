<table width="100%" border="1" cellpadding="5" cellspacing="1" >
     <tr>  
        <th width="5%">序号</th>
        <th width="8%">设备编号</th>
        <th width="8%">设备名称</th>
        <th width="14%">企业名称</th>
        <th width="6%">设备类型</th>
        <th width="8%">IP地址</th>
        <th width="6%">端口号</th>
        <th width="8%">密码</th>
        <th width="6%">天线个数</th>

     </tr>
	  <#if (deviceInfoList)??>
	  <#list deviceInfoList as cur>
	  	<tr align="center">
	  	<td align="center" nowrap="nowrap">${cur_index + 1}</td>
	  	<td align="center">${((cur.device_no)!" ")?html}</td>
	  	<td align="center">${((cur.device_name)!" ")?html}</td>
	  	<td align="center">${((cur.own_entp_name)!" ")?html}</td>
	  	<td align="center"><#if (cur.device_type)??>
        <#if (cur.device_type == 0)>RFID</#if>
        <#if (cur.device_type == 1)>门禁</#if>
        </#if>
       </td>
        <td align="center">${((cur.device_ip)!" ")?html}</td>
	  	<td align="center">${((cur.device_port)!" ")?html}</td>
	    <td align="center">${((cur.map.decPassword)!" ")?html}</td>
	    <td align="center">${((cur.tx_count)!" ")?html}</td>
      </tr>
	 </#list>
	 </#if>
	
</table>
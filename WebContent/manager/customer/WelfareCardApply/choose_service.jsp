<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/admin/css/admin.css"  />
</head>
<body>
<div style="width: 99%" class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/customer/WelfareCardApply">
    <html-el:hidden property="method" value="chooseService" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td>
        	<div id="city_div"> 
                                合伙人名称：
            <html-el:text property="servicecenter_name_like" maxlength="40" style="width:150px;" styleClass="webinput" />
           	&nbsp;所在地区：
            <html-el:select property="province" styleId="province" style="width:120px;" styleClass="pi_prov webinput">
              <html-el:option value="">请选择...</html-el:option>
            </html-el:select>
            &nbsp;
            <html-el:select property="city" styleId="city" style="width:120px;" styleClass="pi_city webinput">
              <html-el:option value="">请选择...</html-el:option>
            </html-el:select>
            &nbsp;
            <html-el:select property="country" styleId="country" style="width:120px;" styleClass="pi_dist webinput">
              <html-el:option value="">请选择...</html-el:option>
            </html-el:select>
            &nbsp;
            &nbsp;<html-el:submit value="查 询" styleClass="bgButton"/>
             &nbsp;<input id="confirm" type="button" value="确定所选" class="bgButton" />
          </div>
         </td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="WelfareCardApply.do?method=delete">
  <div style="padding-bottom:5px;">
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="8%">序号</th>
        <th>合伙人名称</th>
        <th width="20%">所属地区</th>
        <th width="15%">联系电话</th>
        <th width="15%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center">${vs.count}</td>
          <td align="center">${fn:escapeXml(cur.servicecenter_name)}</td>
          <td align="center">${fn:escapeXml(cur.map.full_name)}</td>
          <td align="center">${fn:escapeXml(cur.servicecenter_linkman_tel)}</td>
          <td align="center" nowrap="nowrap">
            <a id="choose_${cur.id}" class="butbase" onclick="choose(this,'${cur.id}','${cur.p_index}','${cur.servicecenter_name}','${cur.map.full_name}');">
            	<span class="icon-ok">选择</span>
            </a>
          </td>
        </tr>
         <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
  </c:forEach>
  <c:forEach begin="${i}" end="${af.map.pager.pageSize-1}">
        <tr align="center">
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </c:forEach>
    </table>
  </form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.cs.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#city_div").citySelect({
        data:getAreaDic(),
        province:"${af.map.province}",
        city:"${af.map.city}",
        country:"${af.map.country}",
        required:false
    });
});

var api = frameElement.api, W = api.opener;

var service_names=[];
var p_indexs=[];
var full_names=[];
var service_ids=[];
var choosed_ids="${af.map.service_ids}";

if(choosed_ids!=null&&choosed_ids!=""){
	//由于url传参中文乱码，用ajax获取县域信息
	$.ajax({
		type: "POST",
		url: "?method=getService&service_ids="+choosed_ids,
		dataType: "json",
		error: function(request, settings) {},
		success: function(data) {
			service_ids=choosed_ids.split(",");
			service_names=data.service_names;
			p_indexs=data.p_indexs;
			full_names=data.full_names;
			for(var i=0;i<service_ids.length;i++){
				$("#choose_"+service_ids[i]).attr("onclick","cancel(this"  + ",'" + service_ids[i] + "','" +p_indexs[i] + "','"+service_names[i]+ "','"+full_names[i]+"')").find("span").text("取消").attr("class","icon-cancel");
			 }
		}
	});	
}

function choose(obj,service_id,p_index,service_name,full_name){
	 $(obj).attr("onclick","cancel(this"  + ",'" + service_id + "','" +p_index + "','"+service_name+ "','"+full_name+"')").find("span").text("取消").attr("class","icon-cancel");
	 
	 service_ids.push(service_id);
	 service_names.push(service_name);
	 p_indexs.push(p_index);
	 full_names.push(full_name);
}

function cancel(obj,service_id,p_index,service_name,full_name){
	$(obj).attr("onclick","choose(this"  + ",'" + service_id + "','" +p_index + "','"+service_name+ "','"+full_name+"')").find("span").text("选择").attr("class","icon-ok");
	
	for(var i=0;i<service_ids.length;i++){
		 if(service_id == service_ids[i]){
			 service_ids.splice(i,1);
			 service_names.splice(i,1);
			 p_indexs.splice(i,1);
			 full_names.splice(i,1);
			 break;
		 }
	 }
}
$("#confirm").click(function(){
	W.returnVal(service_ids,service_names,p_indexs,full_names);
	api.close();
})
//]]></script>
</body>
</html>

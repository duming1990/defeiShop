<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/commons/styles/nav.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/public.js"></script>
<div align="center" class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/VillageAudit" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="6%" nowrap="nowrap">村站名称：
                <html-el:text property="village_name_like" styleId="village_name_like" style="width:140px;" styleClass="webinput"/>
                &nbsp;审核状态：
                <html-el:select property="audit_state" styleClass="webinput" >
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="-1">审核不通过</html-el:option>
                  <html-el:option value="0">待审核</html-el:option>
                  <html-el:option value="1">审核通过</html-el:option>
                </html-el:select>
            	&nbsp;&nbsp;&nbsp;所在地区：
             <html-el:select property="province" styleId="province" styleClass="pi_prov">
		            <html-el:option value="">请选择...</html-el:option>
		          </html-el:select>
		          &nbsp;
		          <html-el:select property="city" styleId="city" styleClass="pi_city">
		            <html-el:option value="">请选择...</html-el:option>
		          </html-el:select>
		          &nbsp;
		          <html-el:select property="country" styleId="country" styleClass="pi_dist">
		            <html-el:option value="">请选择...</html-el:option>
		          </html-el:select>
		           &nbsp; 	
		          <html-el:select property="town" styleId="town" styleClass="pi_town">
		            <html-el:option value="">请选择...</html-el:option>
		          </html-el:select>
		           &nbsp;
		          <html-el:select property="village" styleId="village" styleClass="pi_village">
		            <html-el:option value="">请选择...</html-el:option>
		          </html-el:select>
                &nbsp;&nbsp;
                <button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button>
                &nbsp;<input id="download" type="button" value="导出" class="bgButton" />
                &nbsp;<input id="downloadQrcode" type="button" value="导出二维码" class="bgButton" />
                </td>
                
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="VillageAudit.do?method=delete">
<!--     <div style="padding:5px;"> -->
<%--     <logic-el:match name="popedom" value="+1+"> --%>
<%--       <button class="bgButtonFontAwesome" type="button" onclick="location.href='VillageAudit.do?method=add&par_id=${af.map.par_id}&mod_id=${af.map.mod_id}';" ><i class="fa fa-plus-square"></i>添加</button> --%>
<%--     </logic-el:match> --%>
<!--     </div> -->
     <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="10%">村站名称</th>
        <th width="8%">村站用户</th>
        <th width="8%">个人用户</th>
        <th width="5%">站主姓名</th>
        <th width="5%">村站成员数</th>
        <th width="15%">所属地区</th>
        <th width="8%">村商标</th>
        <th width="5%">商标认证状态</th>
        <th width="8%">村站运营时间</th>
        <th width="8%">添加时间</th>
        <th width="8%">审核状态</th>
        <th width="10%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="left"><a title="查看" href="VillageAudit.do?method=view&amp;id=${cur.id}&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}">${fn:escapeXml(cur.village_name)}</a></td>
          <c:if test="${not empty cur.map.village_no}">
          	<td align="center">
          	<a title="查看" href=" UserInfo.do?method=view&id=${cur.map.user_id}&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}">${fn:escapeXml(cur.map.village_no)}</a>
          	<a class="butbase" href="javascript:void(0);"><span class="icon-lock" onclick="initPassword(${cur.map.user_id});">初始化密码</span></a>
          	</td>
          </c:if>
          <c:if test="${empty cur.map.village_no}">
          	<td align="center">---</td>
          </c:if>
         
          <c:if test="${not empty cur.map.village_person_no}">
          	<td align="center">
          	<a title="查看" href=" UserInfo.do?method=view&id=${cur.map.village_person_user_id}&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}">${fn:escapeXml(cur.map.village_person_no)}</a>
          	<a class="butbase" href="javascript:void(0);"><span class="icon-lock" onclick="initPassword(${cur.map.village_person_user_id});">初始化密码</span></a></td>
          </c:if>
          <c:if test="${empty cur.map.village_person_no}">
          	<td align="center">---</td>
          </c:if>
          <td align="center">${fn:escapeXml(cur.owner_name)}</td>
          <td align="center"><a title="查看" onclick="villageCount(${cur.id});">${fn:escapeXml(cur.count)}</a></td>
          <td align="center">${fn:escapeXml(cur.map.full_name)}</td>
          <td align="center">
          <c:if test="${not empty cur.village_logo}">
         	 <a href="${ctx}/${cur.village_logo}" target="blank"><img src="${ctx}/${cur.village_logo}@s400x400" height="100"></img></a>
          </c:if>
          <c:if test="${empty cur.village_logo}">
        	  -
          </c:if>
          </td>
            <td align="center">
          	<c:choose>
              <c:when test="${cur.is_ren_zheng eq 0}"><span style=" color:red;">未认证</span></c:when>
              <c:when test="${cur.is_ren_zheng eq 1}"><span style=" color:#060;">已认证</span></c:when>
            </c:choose>
          </td>
          <td align="center"><fmt:formatDate value="${cur.service_operation_date}" pattern="yyyy-MM-dd" />-<fmt:formatDate value="${cur.service_operation_date_end}" pattern="yyyy-MM-dd" /></td>
          <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd" /></td>
          <td align="center">
          	<c:choose>
              <c:when test="${cur.audit_state eq -1}"><span style=" color:#F00;">审核不通过</span></c:when>
              <c:when test="${cur.audit_state eq 0}"><span>待审核</span></c:when>
              <c:when test="${cur.audit_state eq 1}"><span style=" color:#060;">审核通过</span></c:when>
            </c:choose>
          </td>
          <td align="center" nowrap="nowrap">
            <logic-el:match name="popedom" value="+8+">
                <c:if test="${cur.is_ren_zheng ne 1}">
                  <a class="butbase" onclick="renZheng(${cur.id},1)"><span class="icon-ok">认证</span></a>
                  <a class="butbase but-disabled"><span class="icon-remove">取消认证</span></a>  
                </c:if>
                <c:if test="${cur.is_ren_zheng eq 1}">
                	<a class="butbase but-disabled" title="已认证通过，不能在进行认证！"><span class="icon-ok">认证</span></a> 
                	<a class="butbase" onclick="renZheng(${cur.id},0)"><span class="icon-remove">取消认证</span></a>  
               	</c:if>
            </logic-el:match>
          </td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="VillageAudit.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10" ><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("par_id", "${af.map.par_id}");
            pager.addHiddenInputs("village_name_like", "${fn:escapeXml(af.map.village_name_like)}");
            pager.addHiddenInputs("province", "${fn:escapeXml(af.map.province)}");
            pager.addHiddenInputs("city", "${fn:escapeXml(af.map.city)}");
            pager.addHiddenInputs("country", "${fn:escapeXml(af.map.country)}");
            pager.addHiddenInputs("town", "${fn:escapeXml(af.map.town)}");
            pager.addHiddenInputs("village", "${fn:escapeXml(af.map.village)}");
            pager.addHiddenInputs("audit_state", "${fn:escapeXml(af.map.audit_state)}");
            document.write(pager.toString());
        </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.cs.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#province").attr({"subElement": "city", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.province}","datatype": "Require", "msg": "请选择省份"});
	$("#city").attr({"subElement": "country", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.city}","datatype": "Require", "msg": "请选择市"});
	$("#country").attr({"subElement": "town", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.country}","datatype": "Require", "msg": "请选择县"});
	$("#town").attr({"subElement": "village", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.town}","datatype": "Require", "msg": "请选择乡/镇"});
	$("#village" ).attr({"defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.village}","datatype": "Require", "msg": "请选择村"});
	$("#province").cs("${ctx}/BaseCsAjax.do?method=getBaseProvinceList", "p_index", "0", false);
});

$("#downloadQrcode").click(function(){
	var submit = function (v, h, f) {
	    if (v == true) {
	    	location.href = "${ctx}/manager/admin/VillageAudit.do?method=downloadVillageQrcode&" + $('.searchForm').serialize();
	    }
	    return true;
	};
	var tip = "确定导出二维码图片吗？";
	$.jBox.confirm(tip, "系统提示", submit, { buttons: { '确定': true, '取消': false} });
});

$("#download").click(function(){
	
	var submit = function (v, h, f) {
	    if (v == true) {
	    	location.href = "${ctx}/manager/admin/VillageAudit.do?method=toExcel&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
	    } else {
	    	location.href = "${ctx}/manager/admin/VillageAudit.do?method=toExcel&code=GBK&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
	    }
	    return true;
	};
	var tip = "确认导出EXCEL格式数据？如果UTF-8编码格式乱码，请选择GBK编码格式下载！";
	$.jBox.confirm(tip, "系统提示", submit, { buttons: { '下载(UTF-8编码)': true, '下载(GBK编码)': false} });
});
	function initPassword(id) {
		if (confirm("确认要初始化密码吗？")) {
			var password = prompt("请输入您的新密码,如果不输入,默认初始密码为“${init_pwd}”。","");
			if (null != password) {
				if (password.length == 0) {
					password = "${init_pwd}";
				}
				$.post("CsAjax.do?method=initPassword",{uid : id, password : password},function(data){
					if(null != data.result){alert(data.msg);} else {alert("初始化密码失败");}
				});
			}
		}
		return false;
	}
	
	function renZheng(id,is_ren_zheng) {
		var msg = "确认要认证该村商标吗？";
		if(is_ren_zheng == 0){
			msg="确认要取消认证该村商标吗？"
		}
		if (confirm(msg)) {
			
			$.ajax({
				type: "POST",
				url: "${ctx}/manager/admin/VillageAudit.do?method=renZheng&is_ren_zheng="+is_ren_zheng+"&id="+id+"&"+$('#bottomPageForm').serialize(),
				data: $("#listForm").serialize(),
				dataType: "json",
				error: function(request, settings) {},
				success: function(data) {
					if (data.ret == 1) {
						alert(data.msg)
						window.location.reload();
					} else{
						alert(data.msg)
					}
				}
			});	
		}
		return false;
	}
	function returnTo(){
		var api = frameElement.api, W = api.opener;
		W.refreshPage();
		api.close();
	}
	
	function villageCount(id){
		var url = "${ctx}/manager/admin/VillageAudit.do?method=member&id="+id;
		$.dialog({
			title:  "成员",
			width:  900,
			height: 520,
			padding: 0,
			max: false,
	        min: false,
	        fixed: true,
	        lock: true,
			content:"url:"+ encodeURI(url)
		});
	}
	
	function auditDesc(id){
		var url = "${ctx}/manager/customer/VillageAudit.do?method=auditDesc&village_id=" + id;
		$.dialog({
			title:  "审核信息",
			width:  900,
			height: 520,
			padding: 0,
			max: false,
	        min: false,
	        fixed: true,
	        lock: true,
			content:"url:"+ encodeURI(url)
		});
	}
</script>
</body>
</html>

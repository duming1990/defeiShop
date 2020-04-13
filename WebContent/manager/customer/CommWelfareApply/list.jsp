<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心 - ${app_name}</title>
<meta content="${app_name}会员中心关键字" name="keywords" />
<meta content="${app_name}会员中心介绍" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body style="height:2500px;">
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/CommWelfareApply.do" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">	
      <tr>
        <td>
        <div id="city_div"> 
                                福利区域名称：
            <html-el:text property="service_name_like" maxlength="40" style="width:150px;" styleClass="webinput" />
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
          </div>
        </td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="CommWelfareApply.do?method=delete">
    <div style="text-align: left;padding:5px;">
      <button class="bgButtonFontAwesome" type="button" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);"><i class="fa fa-minus-square"></i>删除所选</button>
      <button class="bgButtonFontAwesome" type="button" onclick="location.href='CommWelfareApply.do?method=add';" ><i class="fa fa-plus-square"></i>添加</button>
    </div>
    
  <table width="" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr class="tite2">
        <th width="5%"><input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /></th>
        <th nowrap="nowrap">福利区域名称</th>
        <th width="9%">福利区域等级</th>
        <th width="9%">福利区域地址</th>
        <th width="12%">福利区域法人代表</th>
        <th width="10%">福利区域性质</th>
        <th width="5%">审核状态</th>
        <th width="11%">所属该福利商品</th>
        <th width="15%">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          <td align="center">
               <c:if test="${cur.audit_state ne 1}">
                <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}" />
              </c:if>
              <c:if test="${cur.audit_state eq 1}">
                <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}" disabled="disabled" />
              </c:if>
            </td>
           <td>${fn:escapeXml(cur.map.serviceInfo.servicecenter_name)}</td>
          <td align="center">
	          <c:choose>
		          <c:when test="${cur.map.serviceInfo.servicecenter_level eq 1}">省级服务中心</c:when>
		          <c:when test="${cur.map.serviceInfo.servicecenter_level eq 2}">市级服务中心</c:when>
		          <c:when test="${cur.map.serviceInfo.servicecenter_level eq 3}">县级服务中心</c:when>
	          </c:choose>
          </td>
          <td>${fn:escapeXml(cur.map.serviceInfo.servicecenter_addr)}</td>
          <td>${fn:escapeXml(cur.map.serviceInfo.servicecenter_corporation)}</td>
          <td>${fn:escapeXml(cur.map.serviceInfo.servicecenter_type)}</td>
          
          <td align="center">
	          <c:choose>
		          <c:when test="${cur.audit_state eq 0}" ><span class="label label-default">未审核</span></c:when>
		          <c:when test="${cur.audit_state eq 1}"><span class="label label-success">审核通过</span></c:when>
		          <c:when test="${cur.audit_state eq -1}"><span class="label label-danger label-block">审核不通过</span></c:when>
	          </c:choose>
          </td>
          
          <td>
            <a onclick="getCommList(${cur.id})" title="详情">商品列表</a>
          </td>
          <td>
             <a class="label label-warning label-block" id="edit" onclick="confirmUpdate('null', 'CommWelfareApply.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><span id="${cur.id}">修改</span></a> 
	          <c:if test="${cur.audit_state ne 1}">
             	<a class="label label-danger label-block" id="remove" onclick="confirmDelete(null, 'CommWelfareApply.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><span id="${cur.id}">删除</span></a>
	          </c:if>
	          <c:if test="${cur.audit_state eq 1}">
          		<a class="label label-default label-block" id="remove" onclick="javascript:void(0);"  title="已审核，不能删除"><span id="${cur.id}">删除</span></a>
	          </c:if>
          </td>
        </tr>
        <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
      </c:forEach>
      <c:forEach begin="${i}" end="${af.map.pager.pageSize - 1}">
        <tr align="center">
          <td>&nbsp;</td>
          <td nowrap="nowrap">&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td width="8%" nowrap="nowrap">&nbsp;</td>
        </tr>
      </c:forEach>
    </table>
  </form>
   <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="CommWelfareApply.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "list");
					pager.addHiddenInputs("service_name_like", "${fn:escapeXml(af.map.service_name_like)}");
					pager.addHiddenInputs("province", "${fn:escapeXml(af.map.province)}");
					pager.addHiddenInputs("city", "${fn:escapeXml(af.map.city)}");
					pager.addHiddenInputs("country", "${fn:escapeXml(af.map.country)}");
					document.write(pager.toString());
	            	</script>
	            	</td>
        </tr>
      </table>
    </form>
  </div>
  <div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.cs.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
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

function getCommList(id) {
	var url = "${ctx}/manager/customer/CommWelfareApply.do?method=commInfoList&welfareApply_id="+id;
	$.dialog({
		title:  "商品列表",
		width:  "80%",
		height: "90%",
        lock:true ,
        zIndex:"10000",
		content:"url:"+url
	});
};
//]]></script>
</body>
</html>

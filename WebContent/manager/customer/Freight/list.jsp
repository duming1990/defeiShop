<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业会员中心 - ${app_name}</title>
<meta content="${app_name}会员中心关键字" name="keywords" />
<meta content="${app_name}会员中心介绍" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/Freight">
   <html-el:hidden property="method" value="list" />
   <html-el:hidden property="mod_id" />
   <html-el:hidden property="own_entp_id" styleId="own_entp_id"/>
   <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
     <tr>
     <td>
     ${btnName}名称：<html-el:text property="fre_title_like" style="width:200px;" styleClass="webinput" maxlength="30" />
    	&nbsp;&nbsp; 企业名称：<html-el:text property="entp_name" styleId="entp_name" style="width:200px;" styleClass="webinput" maxlength="100" readonly="true" onclick="openEntpChild()"/>
     &nbsp;&nbsp;创建时间 从：<html-el:text property="st_update_time" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" /> 至： <html-el:text property="en_update_time" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />&nbsp;&nbsp;&nbsp;
     <button class="bgButtonFontAwesome" id="btn_submit"><i class="fa fa-search"></i>查 询</button></td>
     </tr>
   </table>
 </html-el:form>
	<%@ include file="/commons/pages/messages.jsp"%>
    <form id="listForm" name="listForm" method="post" action="Freight.do?method=delete">
        <input type="hidden" name="method" id="method" value="delete" />
        <div style="padding-bottom:5px;float:left;">
		  <button class="bgButtonFontAwesome" type="button" onclick="this.form.action += '&amp;' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);"><i class="fa fa-minus-square"></i>删除所选</button>
		  <button class="bgButtonFontAwesome" type="button" onclick="location.href='Freight.do?method=add';" ><i class="fa fa-plus-square"></i>添加</button>
	    </div>
         <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
          <tr class="tite2">
            <th width="5%" align="center" nowrap="nowrap">
            <input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /></th>
            <th align="center" nowrap="nowrap">${btnName}名称</th>
            <th width="12%" align="center" nowrap="nowrap"><strong>所属商家</strong></th>
            <th width="60%" align="center" nowrap="nowrap" colspan="5"><strong>模版明细</strong></th>
            <th width="10%" align="center" nowrap="nowrap"><strong>操作</strong></th>
          </tr>
          <c:forEach var="cur" items="${entityList}" varStatus="vs">
            <tr align="center">
              <td><input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}" /></td>
              <td><c:choose>
                  <c:when test="${fn:length(cur.fre_title) > 20}">
                    <c:out value="${fn:substring(cur.fre_title, 0, 20)}......" />
                  </c:when>
                  <c:otherwise>
                    <c:out value="${cur.fre_title}" />
                  </c:otherwise>
                </c:choose></td>
              <td>${cur.map.entpInfo.entp_name}</td>
              <td colspan="5">
                <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
                  <tr>
                    <td align="center" nowrap="nowrap" style="font-weight: boild;">运送方式</td>
                    <td align="center" nowrap="nowrap" style="font-weight: nomal;">运送到</td>
                    <td align="center" nowrap="nowrap" style="font-weight: nomal;">首
                      <c:choose>
                        <c:when test="${cur.valuation eq 1}"> 件 </c:when>
                        <c:when test="${cur.valuation eq 2}"> 重(kg) </c:when>
                        <c:when test="${cur.valuation eq 3}"> m³ </c:when>
                      </c:choose></td>
                    <td align="center" nowrap="nowrap" style="font-weight: nomal;">运费(元)</td>
                    <td align="center" nowrap="nowrap" style="font-weight: nomal;">续
                      <c:choose>
                        <c:when test="${cur.valuation eq 1 }"> 件 </c:when>
                        <c:when test="${cur.valuation eq 2 }"> 重(kg) </c:when>
                        <c:when test="${cur.valuation eq 3 }"> m³ </c:when>
                      </c:choose></td>
                    <td align="center" nowrap="nowrap" style="font-weight: nomal;">运费(元)</td>
                  </tr>
                  <c:forEach var="detail" items="${cur.freightDetailList}" varStatus="vs">
                    <tr>
                      <td align="center"><c:choose>
                          <c:when test="${detail.delivery_way eq 1}"> 快递 </c:when>
                          <c:when test="${detail.delivery_way eq 2}"> EMS </c:when>
                          <c:when test="${detail.delivery_way eq 3}"> 平邮 </c:when>
                        </c:choose></td>
                      <td align="center"><c:choose>
                          <c:when test="${fn:length(detail.area_name) > 20}">
                            <c:out value="${fn:substring(detail.area_name, 0, 20)}......" escapeXml="true" />
                          </c:when>
                          <c:when test="${fn:length(detail.area_name) == 0 }">
                            <c:out value="默认区域"/>
                          </c:when>
                          <c:otherwise>
                            <c:out value="${detail.area_name}" escapeXml="true" />
                          </c:otherwise>
                        </c:choose></td>
                      <td align="center"><fmt:formatNumber pattern="#,##0.00" value="${detail.first_weight }" /></td>
                      <td align="center"><fmt:formatNumber pattern="#,##0.00" value="${detail.first_price }" /></td>
                      <td align="center"><fmt:formatNumber pattern="#,##0.00" value="${detail.sed_weight }" /></td>
                      <td align="center"><fmt:formatNumber pattern="#,##0.00" value="${detail.sed_price }" /></td>
                    </tr>
                  </c:forEach>
                </table></td>
              <td>
              <a class="butbase" onclick="confirmUpdate(null, 'Freight.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><span class="icon-edit">修改</span></a>
              <a class="butbase" onclick="confirmDelete(null, 'Freight.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><span class="icon-remove">删除</span></a></td>
            </tr>
            <c:if test="${vs.last eq true}">
              <c:set var="i" value="${vs.count}" />
            </c:if>
          </c:forEach>
        </table>
      </form>
  
  <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="Freight.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
			pager.addHiddenInputs("method", "list");
			pager.addHiddenInputs("comm_name_like", "${fn:escapeXml(af.map.comm_name_like)}");
			pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
	        pager.addHiddenInputs("par_id", "${af.map.par_id}");
			pager.addHiddenInputs("province", "${af.map.province}");
			pager.addHiddenInputs("city", "${af.map.city}");
			pager.addHiddenInputs("country", "${af.map.country}");
			pager.addHiddenInputs("audit_state", "${af.map.audit_state}");
			pager.addHiddenInputs("comm_type", "${af.map.comm_type}");
			pager.addHiddenInputs("cls_name", "${af.map.cls_name}");
			pager.addHiddenInputs("cls_id", "${af.map.cls_id}");
			pager.addHiddenInputs("is_del", "${af.map.is_del}");
			document.write(pager.toString());
	      </script></td>
        </tr>
      </table>
    </form>
  </div>
  <div class="clear"></div>
</div>

  
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	var f = document.forms[0];

	$("#btn_submit").click(function(){
		this.form.submit();
	});
	
	
});

function openEntpChild(){
	
	var url = "${ctx}/BaseCsAjax.do?method=chooseEntpInfo&dir=admin";
	$.dialog({
		title:  "选择企业",
		width:  770,
		height: 550,
        lock:true ,
		content:"url:"+url
	});
}
//]]></script>
</body>
</html>
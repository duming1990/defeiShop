<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${app_name}</title>
<meta content="${app_name}会员中心关键字" name="keywords" />
<meta content="${app_name}会员中心介绍" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine" style="height:2500px">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/customer/VillageCommInfo" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="comm_type" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td>
        	&nbsp;商品名称：
        	<html-el:text property="comm_name_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
            &nbsp;
            <html-el:submit value="查 询" styleClass="bgButton" />
            &nbsp;<input id="downloadQrcode" type="button" value="导出二维码" class="bgButton" />
           </td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="VillageCommInfo.do?method=delete">
    <input type="hidden" name="method" id="method" value="delete" />
    <input type="hidden" name="mod_id" id="mod_id" value="${af.map.mod_id}" />
    <input type="hidden" name="comm_type" id="comm_type" value="${af.map.comm_type}" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr class="tite2">
        <th width="5%" ><input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /></th>
         <th width="8%">商品名称</th>
        <th width="10%">商品编号</th>
        <th width="10%">发布人</th>
        <th width="8%">发布时间</th>
        <th width="10%">销售价格</th>
        <th width="8%">商品二维码</th>
        <th width="8%">上下架时间 </th>
        <th width="8%">商品库存</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
        <td>
			${vs.count}
          </td>
          <td align="center">
            <c:url var="url" value="/manager/customer/VillageCommInfo.do?method=view&amp;id=${cur.map.commInfo.id}&amp;mod_id=${af.map.mod_id}" />
            <a href="${url}" title="${fn:escapeXml(cur.map.commInfo.comm_name)}">
            <c:out value="${fnx:abbreviate(cur.map.commInfo.comm_name, 60, '...')}" />
            </a>
          </td>
          <td>${fn:escapeXml(cur.map.commInfo.comm_no)}</td>
          <td>${fn:escapeXml(cur.map.commInfo.add_user_name)}</td>
      	<td><fmt:formatDate value="${cur.map.commInfo.add_date}" pattern="yyyy-MM-dd" /></td>
          <td><fmt:formatNumber pattern="#,##0.00" value="${cur.map.commInfo.sale_price}"/></td>
          <td>
            <c:if test="${ not empty cur.qrcode_image_path}">
           <img src="${ctx}/${cur.qrcode_image_path}" width="100%"/>
            </c:if>
          </td>
          <td><div>
              <c:choose>
                <c:when test="${cur.map.commInfo.is_sell eq 1}"> <span class="tip-success">已上架</span> </c:when>
                <c:otherwise><span class="tip-danger">已下架</span></c:otherwise>
              </c:choose>
            </div>
              <fmt:formatDate value="${cur.map.commInfo.up_date}" pattern="yyyy-MM-dd" />
            至
              <fmt:formatDate value="${cur.map.commInfo.down_date}" pattern="yyyy-MM-dd" />
          </td>
          <td><c:if test="${not empty cur.map.count_tczh_price_inventory}">
              <c:if test="${cur.map.count_tczh_price_inventory le 0}"> <span>库存不足，请及时修改库存</span> </c:if>
              <c:if test="${cur.map.count_tczh_price_inventory gt 0}"> <span style="color:#060;">库存充足</span> </c:if>
            </c:if>
            <c:if test="${empty cur.map.count_tczh_price_inventory}"> <span style="color:#F00;">没有维护套餐，没有库存</span> </c:if>
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
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="VillageCommInfo.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "list");
					pager.addHiddenInputs("comm_name_like", "${fn:escapeXml(af.map.comm_name_like)}");
					pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
					document.write(pager.toString());
	            	</script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){


	
	$(".qtip").quicktip();
	
	var f = document.forms[0];

	$("#btn_submit").click(function(){
		this.form.submit();
	});
	
	$("#downloadQrcode").click(function(){
		var submit = function (v, h, f) {
		    if (v == true) {
		    	location.href = "${ctx}/manager/customer/VillageCommInfo.do?method=downloadQrcode&" + $('.searchForm').serialize();
		    }
		    return true;
		};
		var tip = "确定导出二维码图片吗？";
		$.jBox.confirm(tip, "系统提示", submit, { buttons: { '确定': true, '取消': false} });
	});

});
function windowReload(){
	window.location.reload();
}

//]]></script>
</body>
</html>

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
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body style="height:2500px;">
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/Coupons" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td>
          <div style="margin-top: 5px;">优惠券名称：
            <html-el:text property="yhq_name" styleClass="webinput" maxlength="50" style="width:200px;"/>
            &nbsp;&nbsp;
            <button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button>
          </div></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="Coupons.do?method=delete">
    <div style="text-align: left;padding:5px;">
      <button class="bgButtonFontAwesome" type="button" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);"><i class="fa fa-minus-square"></i>删除所选</button>
      <button class="bgButtonFontAwesome" type="button" onclick="location.href='Coupons.do?method=add&mod_id=${af.map.mod_id}&';" ><i class="fa fa-plus-square"></i>添加</button>
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr class="tite2">
        <th width="5%"><input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /></th>
        <th width="nowrap">优惠券名称</th>
        <th width="8%">优惠券使用数量</th>
        <th width="8%">优惠券金额</th>
        <th width="12%">优惠券使用条件 满多少钱可用</th>
        <th width="8%">优惠券使用时间</th>
        <th width="8%">优惠券结束时间</th>
        <th width="10%">是否限制领取数量</th>
        <th width="8%">限制领取数量</th>
        <th width="10%">领取优惠券二维码</th>
        <th width="10%">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
         	<td align="center">
                <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}" />
            </td>
          <td><a class="viewDetail" href="${ctx}/manager/customer/Coupons.do?method=view&id=${cur.id}">${fn:escapeXml(cur.yhq_name)}</a></td>
          <td>${fn:escapeXml(cur.yhq_number_now)}</td>
          <td><fmt:formatNumber pattern="#,##0.00" value="${cur.yhq_money}"/></td>
          <td><fmt:formatNumber pattern="#,##0.00" value="${cur.yhq_sytj}"/></td>
          <td><fmt:formatDate value="${cur.yhq_start_date}" pattern="yyyy-MM-dd" /></td>
          <td><fmt:formatDate value="${cur.yhq_end_date}" pattern="yyyy-MM-dd" /></td>
          <td>
          	<c:if test="${cur.is_limited eq 0}"><span style="color:red;">不限制</span> </c:if>
            <c:if test="${cur.is_limited eq 1}"> <span style="color:#060;">限制</span> </c:if>
          </td>
          <td>
          <fmt:formatNumber value="${cur.limited_number}"/></td>
          <td>
            <c:if test="${ not empty cur.qrcpde_path}">
           <img src="${ctx}/${cur.qrcpde_path}" width="120"/>
            </c:if>
          </td>
          <td>
             <a class="label label-warning label-block" id="edit" onclick="confirmUpdate('null', 'Coupons.do', 'id=${cur.id}&mod_id=${af.map.mod_id}&' + $('#bottomPageForm').serialize())"><span>修改优惠券</span></a> 
             <a class="label label-danger label-block" id="remove" onclick="confirmDelete(null, 'Coupons.do', 'id=${cur.id}&mod_id=${af.map.mod_id}&' + $('#bottomPageForm').serialize())"><span>删除优惠券</span></a>
          </td>
        </tr>
        <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
      </c:forEach>
      <c:forEach begin="${i}" end="${af.map.pager.pageSize - 1}">
        <tr align="center">
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="Coupons.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "list");
					pager.addHiddenInputs("yhq_name", "${fn:escapeXml(af.map.yhq_name)}");
					pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
					document.write(pager.toString());
	            	</script></td>
        </tr>
      </table>
    </form>
  </div>
  <div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	var f = document.forms[0];
	$("#downloadQrcode").click(function(){
		var submit = function (v, h, f) {
		    if (v == true) {
		    	location.href = "${ctx}/manager/customer/CommInfo.do?method=downloadQrcode&" + $('.searchForm').serialize();
		    }
		    return true;
		};
		var tip = "确定导出二维码图片吗？";
		$.jBox.confirm(tip, "系统提示", submit, { buttons: { '确定': true, '取消': false} });
	});
	$(".qtip").quicktip();
	$("a.viewDetail").colorbox({width:"60%", height:"60%", iframe:true});
});
//]]></script>
</body>
</html>

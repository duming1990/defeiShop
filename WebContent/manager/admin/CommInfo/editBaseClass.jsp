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
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/CommInfo" styleClass="searchForm">
    <html-el:hidden property="method" value="editBaseClass" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="comm_type" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td>产品类别：
          <html-el:hidden property="cls_id" styleId="cls_id"/>
          <html-el:text property="cls_name" styleId="cls_name" readonly="true" onclick="getParClsInfo();" styleClass="webinput" maxlength="50"  style="width:258px;"/>
          &nbsp;商品名称：
          <html-el:text property="comm_name_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
           &nbsp;企业名称：
            <html-el:hidden property="own_entp_id" styleId="own_entp_id" />
            <html-el:text property="entp_name" styleId="entp_name" maxlength="125" styleClass="webinput" value="${entp_name}" readonly="true" onclick="openEntpChild()"/>
          <div style="margin-top:5px;" id="city_div">
          	审核状态：
            <html-el:select property="audit_state" styleId="audit_state" styleClass="webinput" >
              <html-el:option value="">请选择...</html-el:option>
              <html-el:option value="-1">管理员审核不通过</html-el:option>
              <html-el:option value="-2">合伙人审核不通过</html-el:option>
              <html-el:option value="0">待审核</html-el:option>
              <html-el:option value="2">合伙人审核通过</html-el:option>
              <html-el:option value="1">管理员审核通过</html-el:option>
            </html-el:select>
            &nbsp;运营类型：
            <html-el:select property="is_zingying" styleId="is_zingying" styleClass="webinput">
              <html-el:option value="">请选择...</html-el:option>
              <c:forEach var="cur" items="${CommZyTypeList}">
               <html-el:option value="${cur.index}">${cur.name}</html-el:option>
              </c:forEach>
            </html-el:select>
            &nbsp;是否删除：
            <html-el:select property="is_del" >
              <html-el:option value="">全部</html-el:option>
              <html-el:option value="0">未删除</html-el:option>
              <html-el:option value="1">已删除</html-el:option>
            </html-el:select>
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
             <html-el:submit value="查 询" styleClass="bgButton" />
             </div>
           </td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="CommInfo.do?method=saveNewBaseClass">
    <div style="text-align: left;padding: 5px 0px;">
    	<span class="label label-danger" style="font-size:14px;">变更后产品类别：</span>
         <input type="hidden" name="hy_cls_id" id="hy_cls_id"/>
         <input type="text" name="hy_cls_name" id="hy_cls_name" readonly="true" onclick="getHyClsInfo();" styleClass="webinput" maxlength="50"  style="width:258px;"/>
         <input type="button" name="delete" id="delete" class="bgButton" value="变更所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();doNeedMethodAll(this.form,'saveNewBaseClass','您确定要变更所选商品的产品分类吗？');" />
    </div>
    <input type="hidden" name="queryString" id="queryString" />
    <input type="hidden" name="method" id="method" value="saveNewBaseClass" />
    <input type="hidden" name="mod_id" id="mod_id" value="${af.map.mod_id}" />
    <input type="hidden" name="comm_type" id="comm_type" value="${af.map.comm_type}" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr class="tite2">
        <th width="5%" ><input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /></th>
        <th nowrap="nowrap">商品名称</th>
        <th width="6%">用户名</th>
        <th width="6%">所属区域</th>
        <th width="8%">联系电话</th>
        <th width="8%">企业名称</th>
        <th width="8%">产品类别</th>
        <th width="8%">商品编号</th>
        <th width="6%">销售价格</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          <td><input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}" /></td>
          <td align="left">
             <c:if test="${cur.audit_state eq 1}">
              <c:url var="url" value="/entp/IndexEntpInfo.do?method=getCommInfo&id=${cur.id}" />
              <a href="${url}" target="_blank"><i class="fa fa-globe preview"></i></a></c:if>
            <c:url var="url" value="/manager/admin/CommInfo.do?method=view&amp;id=${cur.id}&amp;mod_id=${af.map.mod_id}" />
            <a href="${url}" title="${fn:escapeXml(cur.comm_name)}">
            <c:out value="${fnx:abbreviate(cur.comm_name, 60, '...')}" />
            </a>
            <c:if test="${not empty cur.map.commZyName}">
                   <span class="label label-danger">${fn:escapeXml(cur.map.commZyName)}</span>
            </c:if>
            </td>
          <td>${fn:escapeXml(cur.map.user_name)}
            <div><span class="qtip" title="真实姓名：${fn:escapeXml(cur.map.real_name)}">(${fn:escapeXml(cur.map.real_name)})</span></div></td>
          <td>${fn:escapeXml(cur.map.full_name)}</td>
          <td>${fn:escapeXml(cur.map.mobile)}</td>
          <td>${fn:escapeXml(cur.map.entp_name)}</td>
          <td>${fn:escapeXml(cur.cls_name)}</td>
          <td>${fn:escapeXml(cur.comm_no)}</td>
          <td><fmt:formatNumber pattern="#,##0.00" value="${cur.sale_price}"/></td>
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
          <td>&nbsp;</td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="CommInfo.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "editBaseClass");
					pager.addHiddenInputs("comm_name_like", "${fn:escapeXml(af.map.comm_name_like)}");
					pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
					pager.addHiddenInputs("province", "${af.map.province}");
					pager.addHiddenInputs("city", "${af.map.city}");
					pager.addHiddenInputs("country", "${af.map.country}");
					pager.addHiddenInputs("audit_state", "${af.map.audit_state}");
					pager.addHiddenInputs("comm_type", "${af.map.comm_type}");
					pager.addHiddenInputs("cls_name", "${af.map.cls_name}");
					pager.addHiddenInputs("cls_id", "${af.map.cls_id}");
					pager.addHiddenInputs("is_del", "${af.map.is_del}");
					pager.addHiddenInputs("own_entp_id", "${af.map.own_entp_id}");
					pager.addHiddenInputs("comm_type", "${af.map.comm_type}");
					pager.addHiddenInputs("is_zingying", "${af.map.is_zingying}");
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
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#city_div").citySelect({
        data:getAreaDic(),
        province:"${af.map.province}",
        city:"${af.map.city}",
        country:"${af.map.country}",
        required:false
    });

	
	$(".qtip").quicktip();
	
	var f = document.forms[0];

	$("#btn_submit").click(function(){
		this.form.submit();
	});

	 $("#download").click(function(){
			location.href = "CommInfo.do?method=toExcelForCommInfo&" + $(".searchForm").serialize();
		});

});

// function openEntpChild(){
// 	$.dialog({
// 		title:  "选择企业",
// 		width:  770,
// 		height: 550,
//         lock:true ,
// 		content:"url:${ctx}/manager/admin/CommInfo.do?method=chooseEntpInfo"
// 	});
// }
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
function pingjia(id){
	var url = "${ctx}/manager/admin/CommInfo.do?method=evaluate&id=" + id;
	$.dialog({
		title:  "新增评价",
		width:  500,
		height: 600,
        lock:true ,
		content:"url:"+url
	});
}
function updateSaleCount(id){
	
	var url = "${ctx}/manager/admin/CommInfo.do?method=updateSaleCount&id=" + id;
	$.dialog({
		title:  "修改销量",
		width:  500,
		height: 400,
        lock:true ,
		content:"url:"+url
	});
}


function getParClsInfo() {
	var url = "BasePdClass.do?method=getParClsInfo&isPd=true&azaz=" + Math.random();
	$.dialog({
		title:  "选择产品类别",
		width:  450,
		height: 400,
        lock:true ,
		content:"url:"+url
	});
}

function getHyClsInfo() {
	var url = "BasePdClass.do?method=getParClsInfo&isPd=true&entp_chanage_class=1&azaz=" + Math.random();
	$("#queryString").val($('#bottomPageForm').serialize());
	$.dialog({
		title:  "选择产品类别",
		width:  450,
		height: 400,
        lock:true ,
		content:"url:"+url
	});
}

function windowReload(){
	window.location.reload();
}

//]]></script>
<jsp:include page="../../../_public_page.jsp" flush="true"/>
</body>
</html>

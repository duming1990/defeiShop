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
  <html-el:form action="/admin/VillageCommInfo" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="comm_type" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td>
          &nbsp;商品名称：
          <html-el:text property="comm_name_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
          &nbsp;审核状态：
          <html-el:select property="audit_state" styleId="audit_state" styleClass="webinput" >
             <html-el:option value="">请选择...</html-el:option>
             <html-el:option value="-1">管理员审核不通过</html-el:option>
             <html-el:option value="-2">合伙人审核不通过</html-el:option>
             <html-el:option value="0">待审核</html-el:option>
             <html-el:option value="2">合伙人审核通过</html-el:option>
             <html-el:option value="1">管理员审核通过</html-el:option>
          </html-el:select>
          &nbsp;是否删除：
          <html-el:select property="is_del" >
             <html-el:option value="">全部</html-el:option>
             <html-el:option value="0">未删除</html-el:option>
             <html-el:option value="1">已删除</html-el:option>
          </html-el:select>
          <div style="margin-top:5px;" id="city_div">
          &nbsp;发布人：
          <html-el:text property="add_user_name" styleClass="webinput" maxlength="50" style="width:200px;"/>
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
           </div></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="VillageCommInfo.do?method=delete">
    <input type="hidden" name="method" id="method" value="delete" />
    <input type="hidden" name="mod_id" id="mod_id" value="${af.map.mod_id}" />
    <input type="hidden" name="comm_type" id="comm_type" value="${af.map.comm_type}" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr class="tite2">
        <th width="5%" ><input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /></th>
        <th width="10%">商品名称</th>
        <th width="8%">发布人</th>
        <th width="15%">发布村</th>
        <th width="8%">商品二维码</th>
        <th width="8%">商品编号</th>
        <th width="8%">销售价格</th>
        <th width="8%">上下架时间 </th>
        <th width="8%">商品库存</th>
        <th width="6%">审核状态</th>
        <th width="6%" >审核操作</th>
        <th width="10%" >操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          <td><c:if test="${cur.is_del eq 1}">
              <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}_${cur.cls_id}" disabled="disabled" />
            </c:if>
            <c:if test="${cur.is_del eq 0}">
              <c:if test="${cur.audit_state eq 0}">
                <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}_${cur.cls_id}" />
              </c:if>
              <c:if test="${cur.audit_state ne 0}">
                <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}_${cur.cls_id}" disabled="disabled" />
              </c:if>
            </c:if>
          </td>
          <td align="center">
            <c:url var="url" value="/manager/admin/VillageCommInfo.do?method=view&amp;id=${cur.id}&amp;mod_id=${af.map.mod_id}" />
            <a href="${url}" title="${fn:escapeXml(cur.comm_name)}">
            <c:out value="${fnx:abbreviate(cur.comm_name, 60, '...')}" />
            </a>
          </td>
          <td>${fn:escapeXml(cur.add_user_name)}</td>
          <td>${fn:escapeXml(cur.map.village_dynamic.village_name)}</td>
           <td> 
         	<c:if test="${ not empty cur.map.village_dynamic.qrcode_image_path}">
         	  <img src="${ctx}/${cur.map.village_dynamic.qrcode_image_path}" width="100%"/>
            </c:if>
          </td>
          <td>${fn:escapeXml(cur.comm_no)}</td>
          <td><fmt:formatNumber pattern="#,##0.00" value="${cur.sale_price}"/></td>
          <td><div>
              <c:choose>
                <c:when test="${cur.is_sell eq 1}"> <span class="tip-success">已上架</span> </c:when>
                <c:otherwise><span class="tip-danger">已下架</span></c:otherwise>
              </c:choose>
            </div>
            <div>
              <fmt:formatDate value="${cur.up_date}" pattern="yyyy-MM-dd" />
            </div>
            <div>至</div>
            <div>
              <fmt:formatDate value="${cur.down_date}" pattern="yyyy-MM-dd" />
            </div>
          </td>
          <td><c:if test="${not empty cur.map.count_tczh_price_inventory}">
              <c:if test="${cur.map.count_tczh_price_inventory le 0}"> <span>库存不足，请及时修改库存</span> </c:if>
              <c:if test="${cur.map.count_tczh_price_inventory gt 0}"> <span style="color:#060;">库存充足</span> </c:if>
            </c:if>
            <c:if test="${empty cur.map.count_tczh_price_inventory}"> <span style="color:#F00;">没有维护套餐，没有库存</span> </c:if>
          </td>
          <td><c:choose>
              <c:when test="${cur.audit_state eq -1}"><span class="tip-danger">管理员审核不通过</span></c:when>
              <c:when test="${cur.audit_state eq -2}"><span class="tip-danger">合伙人审核不通过</span></c:when>
              <c:when test="${cur.audit_state eq 0}"><span class="tip-default">待审核</span></c:when>
              <c:when test="${cur.audit_state eq 2}"><span class="tip-success">合伙人审核通过</span></c:when>
              <c:when test="${cur.audit_state eq 1}"><span class="tip-success">管理员审核通过</span></c:when>
            </c:choose>
          	<c:if test="${not empty cur.map.auditUserInfo}">
             <div><span class="qtip" title="审核人姓名：${fn:escapeXml(cur.map.auditUserInfo)}">(${fn:escapeXml(cur.map.auditUserInfo)})</span></div>
            </c:if>   
          </td>
          <td>
            <a class="butbase" onclick="doNeedMethod(null, 'VillageCommInfo.do', 'audit','id=${cur.id}&mod_id=${af.map.mod_id}&'+$('#bottomPageForm').serialize())"><span class="icon-ok">审核</span></a><br/>
           </td>
            <td><c:if test="${cur.is_del eq 0}"> <a class="butbase" id="edit" onclick="confirmUpdate('null', 'VillageCommInfo.do', 'id=${cur.id}&mod_id=${af.map.mod_id}&' + $('#bottomPageForm').serialize())"><span id="${cur.id}" class="icon-edit">修改商品</span></a><br/>
              <c:if test="${cur.audit_state ne 1}"><a class="butbase" id="remove" onclick="confirmDelete(null, 'VillageCommInfo.do', 'id=${cur.id}&cls_id=${cur.cls_id}&mod_id=${af.map.mod_id}&' + $('#bottomPageForm').serialize())"><span id="${cur.id}" class="icon-remove">删除商品</span></a></c:if>
              <c:if test="${cur.audit_state eq 1}"><a class="butbase but-disabled" id="remove" onclick="javascript:void(0);"  title="已审核，不能删除"><span id="${cur.id}" class="icon-remove">删除商品</span></a></c:if>
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
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td width="8%" nowrap="nowrap">&nbsp;</td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="VillageCommInfo.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "list");
					pager.addHiddenInputs("comm_name_like", "${fn:escapeXml(af.map.comm_name_like)}");
					pager.addHiddenInputs("add_user_name","${af.map.add_user_name}");
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
			location.href = "VillageCommInfo.do?method=toExcelForCommInfo&" + $(".searchForm").serialize();
		});

});

// function openEntpChild(){
// 	$.dialog({
// 		title:  "选择企业",
// 		width:  770,
// 		height: 550,
//         lock:true ,
// 		content:"url:${ctx}/manager/admin/VillageCommInfo.do?method=chooseEntpInfo"
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
	var url = "${ctx}/manager/admin/VillageCommInfo.do?method=evaluate&id=" + id;
	$.dialog({
		title:  "新增评价",
		width:  500,
		height: 600,
        lock:true ,
		content:"url:"+url
	});
}
function updateSaleCount(id){
	
	var url = "${ctx}/manager/admin/VillageCommInfo.do?method=updateSaleCount&id=" + id;
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

function windowReload(){
	window.location.reload();
}

//]]></script>
<jsp:include page="../../../_public_page.jsp" flush="true"/>
</body>
</html>

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
  <html-el:form action="/admin/SuperMarketCommInfo" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="comm_type" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td>
          &nbsp;商品名称：
          <html-el:text property="comm_name_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
          	审核状态：
            <html-el:select property="audit_state" styleId="audit_state" styleClass="webinput" >
              <html-el:option value="">请选择...</html-el:option>
              <html-el:option value="-1">管理员审核不通过</html-el:option>
              <html-el:option value="0">待审核</html-el:option>
              <html-el:option value="1">管理员审核通过</html-el:option>
            </html-el:select>
            &nbsp;是否删除：
            <html-el:select property="is_del" >
              <html-el:option value="">全部</html-el:option>
              <html-el:option value="0">未删除</html-el:option>
              <html-el:option value="1">已删除</html-el:option>
            </html-el:select>
             <html-el:submit value="查 询" styleClass="bgButton" />
           </td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="SuperMarketCommInfo.do?method=delete">
    <input type="hidden" name="method" id="method" value="delete" />
    <input type="hidden" name="mod_id" id="mod_id" value="${af.map.mod_id}" />
    <input type="hidden" name="comm_type" id="comm_type" value="${af.map.comm_type}" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr class="tite2">
        <th width="5%" >
<!--         <input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /> -->
        序号
        </th>
        <th nowrap="nowrap">商品名称</th>
        <th width="12%">用户名</th>
        <th width="12%">联系电话</th>
        <th width="12%">企业名称</th>
        <th width="12%">商品编号</th>
        <th width="12%">销售价格</th>
        <th width="12%">审核状态</th>
        <th width="12%">是否设置扶贫户</th>
        <th width="12%" >操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          <td>
<%--           <c:if test="${cur.is_del eq 1}"> --%>
<%--               <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}_${cur.cls_id}" disabled="disabled" /> --%>
<%--             </c:if> --%>
<%--             <c:if test="${cur.is_del eq 0}"> --%>
<%--               <c:if test="${cur.audit_state eq 0}"> --%>
<%--                 <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}_${cur.cls_id}" /> --%>
<%--               </c:if> --%>
<%--               <c:if test="${cur.audit_state ne 0}"> --%>
<%--                 <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}_${cur.cls_id}" disabled="disabled" /> --%>
<%--               </c:if> --%>
<%--             </c:if> --%>
            ${vs.count}
            </td>
          <td align="center">
<%--              <c:if test="${cur.audit_state eq 1}"> --%>
<%--               <c:url var="url" value="/entp/IndexEntpInfo.do?method=getSuperMarketCommInfo&id=${cur.id}" /> --%>
<%--               <a href="${url}" target="_blank"><i class="fa fa-globe preview"></i></a></c:if> --%>
<%--             <c:url var="url" value="/manager/admin/SuperMarketCommInfo.do?method=view&amp;id=${cur.id}&amp;mod_id=${af.map.mod_id}" /> --%>
<%--             <a href="${url}" title="${fn:escapeXml(cur.comm_name)}"> --%>
            <c:out value="${fnx:abbreviate(cur.comm_name, 60, '...')}" />
            </a>
            <c:if test="${not empty cur.map.commZyName}">
                   <span class="label label-danger" style="line-height:2;">${fn:escapeXml(cur.map.commZyName)}</span>
            </c:if>
            </td>
          <td>${fn:escapeXml(cur.map.user_name)}
            <div><span class="qtip" title="真实姓名：${fn:escapeXml(cur.map.real_name)}">(${fn:escapeXml(cur.map.real_name)})</span></div></td>
          <td>${fn:escapeXml(cur.map.mobile)}</td>
          <td>${fn:escapeXml(cur.map.entp_name)}</td>
          <td>${fn:escapeXml(cur.comm_no)}</td>
          <td><fmt:formatNumber pattern="#,##0.00" value="${cur.sale_price}"/></td>
          <td><c:choose>
              <c:when test="${cur.audit_state eq -1}"><span class="tip-danger">管理员审核不通过</span></c:when>
              <c:when test="${cur.audit_state eq 0}"><span class="tip-default">待审核</span></c:when>
              <c:when test="${cur.audit_state eq 1}"><span class="tip-success">管理员审核通过</span></c:when>
            </c:choose>
            <c:if test="${not empty cur.map.auditUserInfo}">
             <div><span class="qtip" title="审核人姓名：${fn:escapeXml(cur.map.auditUserInfo)}">(${fn:escapeXml(cur.map.auditUserInfo)})</span></div>
            </c:if> 
            </td>
            <td>${fn:escapeXml(cur.map.is_poor_set)}</td>
          <td><c:if test="${cur.is_del eq 0}"> <a class="butbase" id="edit" onclick="confirmUpdate('null', 'SuperMarketCommInfo.do', 'id=${cur.id}&mod_id=${af.map.mod_id}&' + $('#bottomPageForm').serialize())"><span id="${cur.id}" class="icon-edit">修改商品</span></a><br/>
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
          <td width="8%" nowrap="nowrap">&nbsp;</td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="SuperMarketCommInfo.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "list");
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
	$("#downloadQrcode").click(function(){
		var submit = function (v, h, f) {
		    if (v == true) {
		    	location.href = "${ctx}/manager/admin/SuperMarketCommInfo.do?method=downloadQrcode&" + $('.searchForm').serialize();
		    }
		    return true;
		};
		var tip = "确定导出二维码图片吗？";
		$.jBox.confirm(tip, "系统提示", submit, { buttons: { '确定': true, '取消': false} });
	});

	
	$(".qtip").quicktip();
	
	var f = document.forms[0];

	$("#btn_submit").click(function(){
		this.form.submit();
	});

	 $("#download").click(function(){
			location.href = "SuperMarketCommInfo.do?method=toExcelForSuperMarketCommInfo&" + $(".searchForm").serialize();
		});
	 

});

// function openEntpChild(){
// 	$.dialog({
// 		title:  "选择企业",
// 		width:  770,
// 		height: 550,
//         lock:true ,
// 		content:"url:${ctx}/manager/admin/SuperMarketCommInfo.do?method=chooseEntpInfo"
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
	var url = "${ctx}/manager/admin/SuperMarketCommInfo.do?method=evaluate&id=" + id;
	$.dialog({
		title:  "新增评价",
		width:  600,
		height: 700,
        lock:true ,
		content:"url:"+url
	});
}
function updateSaleCount(id){
	
	var url = "${ctx}/manager/admin/SuperMarketCommInfo.do?method=updateSaleCount&id=" + id;
	$.dialog({
		title:  "修改销量",
		width:  500,
		height: 400,
        lock:true ,
		content:"url:"+url
	});
}

function windowReload(){
	window.location.reload();
}

function fapiao(id,is_tigong){
	if(is_tigong == 1){
		var fapiao = 0;
	}else{
		var fapiao = 1;
	}
	 var submit2 = function(v, h, f) {
		if (v == "ok") {
			$.jBox.tip("修改中！", 'loading');
			window.setTimeout(function () {
			  $.ajax({
					type: "POST" , 
					url: "${ctx}/manager/admin/SuperMarketCommInfo.do" , 
					data:"method=fapiao&comm_id="+id+"&is_fapiao="+fapiao,
					dataType: "json", 
			        async: true, 
			        error: function (request, settings) {alert(" 数据加载请求失败！ ");}, 
			        success: function (data) {
						if (data.code == 0) {
							$.jBox.tip(data.msg, 'error');
						} else {
							$.jBox.tip("修改成功！", 'success');
							window.setTimeout(function () {
							 window.location.reload(); 
							}, 1000);
						}
			        }
			 });
		   }, 1000);	 
		}
	};
	$.jBox.confirm("您确定要修改吗?", "确定提示", submit2);
}

function ziti(id,is_ziti){
	if(is_ziti == 1){
		var ziti = 0;
	}else{
		var ziti = 1;
	}
	 var submit2 = function(v, h, f) {
		if (v == "ok") {
			$.jBox.tip("修改中！", 'loading');
			window.setTimeout(function () {
			 $.ajax({
					type: "POST" , 
					url: "${ctx}/manager/admin/SuperMarketCommInfo.do" , 
					data:"method=ziti&comm_id="+id+"&is_ziti="+ziti,
					dataType: "json", 
			        async: true, 
			        error: function (request, settings) {alert(" 数据加载请求失败！ ");}, 
			        success: function (data) {
						if (data.code == 0) {
							$.jBox.tip(data.msg, 'error');
						} else {
							$.jBox.tip("修改成功！", 'success');
							 window.setTimeout(function () {
							window.location.reload(); 
							}, 1000);
						}
			        }
				});
			}, 1000);
		}
	};
	$.jBox.confirm("您确定要修改吗?", "确定提示", submit2);
}

//]]></script>
<jsp:include page="../../../_public_page.jsp" flush="true"/>
</body>
</html>

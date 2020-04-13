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
  <html-el:form action="/customer/CommInfo" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="comm_type" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td>商品类别：
          <html-el:hidden property="cls_id" styleId="cls_id"/>
          <html-el:text property="cls_name" styleId="cls_name" readonly="true" onclick="getParClsInfo();" styleClass="webinput" maxlength="50"  style="width:258px;"/>
          <div style="margin-top: 5px;">商品名称：
            <html-el:text property="comm_name_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
            &nbsp;审核状态：
            <html-el:select property="audit_state" styleId="audit_state" styleClass="webinput" >
              <html-el:option value="">请选择...</html-el:option>
              <html-el:option value="-1">审核不通过</html-el:option>
              <html-el:option value="0">待审核</html-el:option>
              <html-el:option value="1">审核通过</html-el:option>
            </html-el:select>
            &nbsp;是否扶贫：
            <html-el:select property="is_aid" >
              <html-el:option value="">全部</html-el:option>
              <html-el:option value="0">否</html-el:option>
              <html-el:option value="1">是</html-el:option>
            </html-el:select>
            &nbsp;是否删除：
            <html-el:select property="is_del" >
              <html-el:option value="">全部</html-el:option>
              <html-el:option value="0">未删除</html-el:option>
              <html-el:option value="1">已删除</html-el:option>
            </html-el:select>
            &nbsp;&nbsp;
            <button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button>
           &nbsp;<input id="downloadQrcode" type="button" value="导出二维码" class="bgButton" />
          </div></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="CommInfo.do?method=delete">
    <div style="text-align: left;padding:5px;">
      <button class="bgButtonFontAwesome" type="button" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);"><i class="fa fa-minus-square"></i>删除所选</button>
      <button class="bgButtonFontAwesome" type="button" onclick="location.href='CommInfo.do?method=add&par_id=${af.map.par_id}&mod_id=${af.map.mod_id}&comm_type=${af.map.comm_type}';" ><i class="fa fa-plus-square"></i>添加</button>
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr class="tite2">
        <th width="5%"><input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /></th>
        <th nowrap="nowrap">商品名称</th>
        <th width="8%">商品主图</th>
        <th width="6%">产品类别</th>
        <th width="8%">商品编号</th>
        <th width="6%">销售价格</th>
        <th width="6%">上下架时间</th>
        <th width="8%">商品库存</th>
        <th width="10%">商品二维码</th>
        <th width="8%">是否提供发票</th>
        <c:if test="${empty is_jd}"><th width="8%">能否自提</th></c:if>
        <th width="8%">审核状态</th>
        <th width="10%">操作</th>
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
            </c:if></td>
          <td align="left">
            <c:if test="${cur.audit_state eq 1}">
               <c:url var="url" value="/entp/IndexEntpInfo.do?method=getCommInfo&id=${cur.id}" />
               <a href="${url}" target="_blank"><i class="fa fa-globe preview"></i></a>
            </c:if>
            <c:url var="url" value="/manager/customer/CommInfo.do?method=view&amp;id=${cur.id}&amp;mod_id=${af.map.mod_id}&amp;par_id=${af.map.par_id}" />
            <a href="${url}" title="${fn:escapeXml(cur.comm_name)}">
            <c:out value="${fnx:abbreviate(cur.comm_name, 60, '...')}" />
            </a></td>
          <td><img src="${ctx}/${cur.main_pic}" width="100%" /></td>
          <td>${fn:escapeXml(cur.cls_name)}</td>
          <td>${fn:escapeXml(cur.comm_no)}</td>
          <td><fmt:formatNumber pattern="#,##0.00" value="${cur.sale_price}"/></td>
          <td><c:if test="${cur.is_sell eq 1}">
              <fmt:formatDate value="${cur.up_date}" pattern="yyyy-MM-dd" />
              <br/> 至<br/>
              <fmt:formatDate value="${cur.down_date}" pattern="yyyy-MM-dd" />
            </c:if>
            <c:if test="${cur.is_sell eq 0}"><span style="color:#F00;">暂不上架</span></c:if>
          </td>
          <td><c:if test="${not empty cur.map.count_tczh_price_inventory}">
              <c:if test="${cur.map.count_tczh_price_inventory le 0}"> <span>库存不足，请及时修改库存</span> </c:if>
              <c:if test="${cur.map.count_tczh_price_inventory gt 0}"> <span style="color:#060;">库存充足</span> </c:if>
            </c:if>
            <c:if test="${empty cur.map.count_tczh_price_inventory}"> <span style="color:#F00;">没有维护套餐，没有库存</span> </c:if>
          </td>
           <td>
            <c:if test="${ not empty cur.comm_qrcode_path}">
           <img src="${ctx}/${cur.comm_qrcode_path}" width="120"/>
            </c:if>
          </td>
          <td>
          	<c:if test="${cur.is_fapiao eq 0}"><span style="color:red;">不提供</span> </c:if>
            <c:if test="${cur.is_fapiao eq 1}"> <span style="color:#060;">提供</span> </c:if>
            <span onclick="fapiao(${cur.id},${cur.is_fapiao});" class="label label-info" style="width: 40%;display: inherit;cursor: pointer;">更改</span>
          </td>
          <c:if test="${empty is_jd}">
          <td>
          	<c:if test="${cur.is_ziti eq 0}"><span style="color:red;">不能自提</span> </c:if>
            <c:if test="${cur.is_ziti eq 1}"> <span style="color:#060;">能自提</span> </c:if>
            <span onclick="ziti(${cur.id},${cur.is_ziti});" class="label label-info" style="width: 40%;display: inherit;cursor: pointer;">更改</span>
          </td>
          </c:if>
          <td>
          	<c:choose>
              <c:when test="${cur.audit_state eq -1}">
              <span class="label label-danger label-block">审核不通过</span>
              <c:if test="${(not empty cur.audit_desc)}">
	            <a title="${fn:escapeXml(cur.audit_desc)}" class="label label-warning label-block" onclick="viewAuditDesc('${cur.audit_desc}');">
	            <i class="fa fa-info-circle"></i>查看原因</a> 
	          </c:if>
              </c:when>
              <c:when test="${cur.audit_state eq 0}"><span class="label label-default">待审核</span></c:when>
              <c:when test="${cur.audit_state eq 1}"><span class="label label-success">审核通过</span></c:when>
            </c:choose>
          </td>
          <td>
          <c:if test="${cur.is_del eq 0}"> 
              <a class="label label-warning label-block" id="edit" onclick="confirmUpdate('null', 'CommInfo.do', 'id=${cur.id}&par_id=${af.map.par_id}&mod_id=${af.map.mod_id}&comm_type=${af.map.comm_type}&' + $('#bottomPageForm').serialize())"><span id="${cur.id}">修改商品</span></a> 
              <c:if test="${cur.audit_state ne 1}"><a class="label label-danger label-block" id="remove" onclick="confirmDelete(null, 'CommInfo.do', 'id=${cur.id}&cls_id=${cur.cls_id}&par_id=${af.map.par_id}&mod_id=${af.map.mod_id}&comm_type=${af.map.comm_type}&' + $('#bottomPageForm').serialize())"><span id="${cur.id}">删除商品</span></a></c:if>
              <c:if test="${cur.audit_state eq 1}"><a class="label label-default label-block" id="remove" onclick="javascript:void(0);"  title="已审核，不能删除"><span id="${cur.id}">删除商品</span></a></c:if>
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
         <c:if test="${empty is_jd}"> <td>&nbsp;</td></c:if>
          <td>&nbsp;</td>
          <td width="8%" nowrap="nowrap">&nbsp;</td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="CommInfo.do">
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
					pager.addHiddenInputs("is_aid", "${af.map.is_aid}");
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
});

function viewAuditDesc(audit_desc){
	$.dialog({
		title:  "审核不通过原因",
		width:  250,
		height: 100,
        lock:true ,
		content:audit_desc
	});
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
					url: "${ctx}/manager/customer/CommInfo.do" , 
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
					url: "${ctx}/manager/customer/CommInfo.do" , 
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

function getParClsInfo() {
	var url = "${ctx}/CsAjax.do?method=getClsId&noSelectFar=true&t=" + Math.random();
	$.dialog({
		title:  "选择商品类别",
		width:  450,
		height: 400,
        lock:true ,
		content:"url:"+url
	});
}

//]]></script>
</body>
</html>

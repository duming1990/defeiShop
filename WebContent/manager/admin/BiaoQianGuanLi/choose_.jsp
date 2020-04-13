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
  <html-el:form action="/admin/BiaoQianGuanLi" styleClass="searchForm">
    <html-el:hidden property="method" value="chooseCommInfo" />
    <html-el:hidden property="mod_id" value="${af.map.mod_id}"/>
<%--     <html-el:hidden property="cls_id" value="${af.map.cls_id}"/> --%>
    <html-el:hidden property="comm_type" />
    <html-el:hidden property="biaoqianid" value="${af.map.biaoqianid}"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td>产品类别：
          <html-el:hidden property="cls_id" styleId="cls_id"/>
          <html-el:text property="cls_name" styleId="cls_name" readonly="true" onclick="getParClsInfo();" styleClass="webinput" maxlength="50"  style="width:258px;"/>
          &nbsp;商品名称：
          <html-el:text property="comm_name_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
          &nbsp;商品编号：
          <html-el:text property="comm_no_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
           
          <div style="margin-top: 5px;">
          &nbsp;企业名称：
          <html-el:text property="entp_name_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
            &nbsp;审核状态：
            <html-el:select property="audit_state" styleId="audit_state" styleClass="webinput" >
             <html-el:option value="1">审核通过</html-el:option>
              <html-el:option value="-1">审核不通过</html-el:option>
              <html-el:option value="0">待审核</html-el:option>
              
            </html-el:select>
            &nbsp;是否删除：
            <html-el:select property="is_del" >
              <html-el:option value="">全部</html-el:option>
              <html-el:option value="0">未删除</html-el:option>
              <html-el:option value="1">已删除</html-el:option>
            </html-el:select>
            &nbsp;
            <html-el:submit value="查 询" styleClass="bgButton" styleId="bgButton"/>
          </div></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
     <logic-el:match name="popedom" value="+1+">
        <input type="button" name="addAll" id="addAll" class="bgButton" value="添加所选" style="margin-bottom: 5px;"/>
        <span style="color: red;" id="addAll_tip">&nbsp;添加中，请耐心等待...</span>
      </logic-el:match>
  <html-el:form action="/admin/BiaoQianGuanLi.do" styleClass="ajaxForm">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="savetags" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="biaoqianid" value="${af.map.biaoqianid}" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr class="tite2">
        <th width="5%" nowrap="nowrap"> 
            <input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" />
        </th>
        <th nowrap="nowrap">商品名称</th>
        <th nowrap="nowrap">产品类别</th>
        <th nowrap="nowrap">商品编号</th>
        <th nowrap="nowrap">销售价格</th>
        <th nowrap="nowrap">所属商家</th>
        <c:if test="${is_select eq 1}"><th nowrap="nowrap">排序值</th></c:if>
         <th width="15%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center" id="_${cur.id}">
           <td>
           <c:if test="${fn:contains(popedom, '+1+')}">
              <c:if test="${cur.map.comm_tags_cunzai ne cur.id}">
                <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}" />
              </c:if>
              <c:if test="${cur.map.comm_tags_cunzai eq cur.id}">
                <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}" disabled="disabled" />
              </c:if>
            </c:if>
           </td>
          <td align="center">
            <c:out value="${fnx:abbreviate(cur.comm_name, 60, '...')}" />
            </a></td>
          <td>${fn:escapeXml(cur.cls_name)}</td>
          <td>${fn:escapeXml(cur.comm_no)}</td>
          <td><fmt:formatNumber pattern="#,##0.00" value="${cur.sale_price}"/></td>
          <td>${fn:escapeXml(cur.map.entp_name)}</td>
          <c:if test="${is_select eq 1}"><td>${cur.map.order_value}</td></c:if>
            <td>
            <c:if test="${cur.map.comm_tags_cunzai eq cur.id}">
            	<a class="butbase"  onclick="shanchu(${cur.id})" ><span class="icon-remove">删除</span></a>
            	<a class="butbase"  onclick="rank(${cur.id})" ><span class="icon-node">排序</span></a>
            </c:if>
            <c:if test="${cur.map.comm_tags_cunzai ne cur.id}">
           		<a class="butbase"  onclick="xuanze(${cur.id})" ><span class="icon-ok">选择</span></a></c:if>
            </td>
        </tr>
        <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
      </c:forEach>
    </table>
  </html-el:form>
    <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="BiaoQianGuanLi.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "chooseCommInfo"); 
 					pager.addHiddenInputs("comm_name_like", "${fn:escapeXml(af.map.comm_name_like)}");
 					pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
 					pager.addHiddenInputs("province", "${af.map.province}");
 					pager.addHiddenInputs("city", "${af.map.city}");
 					pager.addHiddenInputs("pks", "${af.map.pks}");
 					pager.addHiddenInputs("country", "${af.map.country}");
 					pager.addHiddenInputs("audit_state", "${af.map.audit_state}");
 					pager.addHiddenInputs("comm_type", "${af.map.comm_type}");
				    pager.addHiddenInputs("cls_name", "${af.map.cls_name}");
					pager.addHiddenInputs("cls_id", "${af.map.cls_id}");
					pager.addHiddenInputs("is_del", "${af.map.is_del}");
					pager.addHiddenInputs("own_entp_id", "${af.map.own_entp_id}");
					pager.addHiddenInputs("comm_type", "${af.map.comm_type}");
					pager.addHiddenInputs("biaoqianid", "${af.map.biaoqianid}");
					pager.addHiddenInputs("is_select", "${af.map.is_select}");
					document.write(pager.toString()); 
         	</script></td> 
        </tr>
      </table>
    </form>
  </div>
  <div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#addAll_tip").hide();
	var f1 = document.forms[1];
	$("#addAll").click(function(){
		
		$("#addAll").attr("value", "正在添加...").attr("disabled", "true");
		$("#addAll_tip").show();
		
		$.ajax({
			type: "POST",
			url: "${ctx}/manager/admin/BiaoQianGuanLi.do?method=saveAllTags&mod_id=${af.map.mod_id}",
			data: $(f1).serialize(),
			dataType: "json",
			error: function(request, settings) {},
			success: function(data) {
						alert(data.msg)
						if(data.ret==0){
						$("#addAll_tip").hide();
						$("#addAll").attr("value", "添加所选").attr("disabled", false);
						}else{
						window.location.reload();
						}
						
				}
		});
		
	});
	
	//地市选择
	$("#province").citySelect({
	        data:getAreaDic(),
	        province:"${af.map.province}",
	        city:"${af.map.city}",
	        country:"${af.map.country}",
	        province_required : false,
	        city_required : false,
	        country_required : false,
	        callback:function(selectValue,selectText){
	        	if(null != selectValue && "" != selectValue){
	        		var p_indexs = selectValue.split(",");
	        		if(null != p_indexs && p_indexs.length > 0){
	        			$("#p_index").val(p_indexs[p_indexs.length - 1]);
	        		}
	        	}
	        }
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

var f0 = $(".ajaxForm").get(0);
// var f1 = $(".searchForm").get(0);
function xuanze(id){
	
		$.ajax({
			type: "POST",
			url: "${ctx}/manager/admin/BiaoQianGuanLi.do?method=savetags&id="+id,
			data: $(f0).serialize(),
			dataType: "json",
			error: function(request, settings) {},
			success: function(data) {
				if (data.ret == 1) {
					$("#_"+id).remove();
// 					returnTo();
				}else{
					alert(data.msg);
				}
			}
		});
 }
function shanchu(id){
	$.ajax({
		type: "POST",
		url: "${ctx}/manager/admin/BiaoQianGuanLi.do?method=delettags&id="+id,
		data: $(f0).serialize(),
		dataType: "json",
		error: function(request, settings) {},
		success: function(data) {
			if (data.ret == 1) {
				$("#_"+id).remove();
			} 
			
		}
	});
}

function rank(id){
	var tag_id=${af.map.biaoqianid};
	var url = "BiaoQianGuanLi.do?method=rank&id=" + id+"&tag_id="+tag_id;
	$.dialog({
		title:  "请填写排序值",
		width:  450,
		height: 350,
		zIndex:999,
        lock:true ,
		content:"url:"+url
	});
}
function refreshPage(){
	window.location.reload();
}
function returnTo(){
	var api = frameElement.api, W = api.opener;
	W.refreshPage();
	api.close();
	
}

</script>
<jsp:include page="../../../_public_page.jsp" flush="true"/>
</body>
</html>

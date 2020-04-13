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
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/customer/BaseComminfoTags" styleClass="searchForm">
    <html-el:hidden property="method" value="chooseCommInfo" />
    <html-el:hidden property="mod_id" value="${af.map.mod_id}"/>
    <html-el:hidden property="comm_type" />
    <html-el:hidden property="tag_id" />  
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td>商品名称：
          <html-el:text property="comm_name_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
           	 &nbsp;商品编号：
            <html-el:text property="comm_no_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
			&nbsp;&nbsp;
            <html-el:submit value="查 询" styleClass="bgButton" styleId="bgButton" />
         </td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <html-el:form action="/customer/BaseComminfoTags.do" styleClass="ajaxForm">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="savetags" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="tag_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr class="tite2">
        <th width="5%" nowrap="nowrap">序号</th>
        <th nowrap="nowrap">商品名称</th>
        <th nowrap="nowrap">产品类别</th>
        <th nowrap="nowrap">商品编号</th>
        <th nowrap="nowrap">销售价格</th>
        <th width="15%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center" id="_${cur.id}">
           <td>${vs.count}</td>
          <td align="center"><c:out value="${fnx:abbreviate(cur.comm_name, 60, '...')}" /></a></td>
          <td>${fn:escapeXml(cur.cls_name)}</td>
          <td>${fn:escapeXml(cur.comm_no)}</td>
          <td><fmt:formatNumber pattern="#,##0.00" value="${cur.sale_price}"/></td>
          <td>
            <c:if test="${cur.map.comm_tags_cunzai eq cur.id}">
            	<a class="label label-danger" onclick="deleteTag(${cur.id})"><span class="icon-remove">删除</span></a>
            </c:if>
            <c:if test="${cur.map.comm_tags_cunzai ne cur.id}">
           		<a class="label label-success" onclick="chooseTag(${cur.id})"><span class="icon-ok">选择</span></a></c:if>
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
          <c:if test="${is_select eq 1}"><td>&nbsp;</td></c:if>
          <td width="8%" nowrap="nowrap">&nbsp;</td>
        </tr>
      </c:forEach>
    </table>
  </html-el:form>
    <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="BaseComminfoTags.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "chooseCommInfo"); 
 					pager.addHiddenInputs("comm_name_like", "${fn:escapeXml(af.map.comm_name_like)}");
 					pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
 					pager.addHiddenInputs("audit_state", "${af.map.audit_state}");
					pager.addHiddenInputs("own_entp_id", "${af.map.own_entp_id}");
					pager.addHiddenInputs("comm_type", "${af.map.comm_type}");
					pager.addHiddenInputs("tag_id", "${af.map.tag_id}");
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
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#addAll_tip").hide();
	var f1 = document.forms[1];
	$("#addAll").click(function(){
		
		$("#addAll").attr("value", "正在添加...").attr("disabled", "true");
		$("#addAll_tip").show();
		
		$.ajax({
			type: "POST",
			url: "${ctx}/manager/customer/BaseComminfoTags.do?method=saveAllTags&mod_id=${af.map.mod_id}",
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
	
	
	$(".qtip").quicktip();
	
	var f = document.forms[0];

	$("#btn_submit").click(function(){
		this.form.submit();
	});
});


var f0 = $(".ajaxForm").get(0);
function chooseTag(id){
		$.ajax({
			type: "POST",
			url: "${ctx}/manager/customer/BaseComminfoTags.do?method=savetags&comm_id="+id,
			data: $(f0).serialize(),
			dataType: "json",
			error: function(request, settings) {},
			success: function(data) {
				if (data.code == 1) {
					$.jBox.tip("选择成功", "success",{timeout:1000});
					window.setTimeout(function(){
						window.location.reload();
					},1500);
				}else{
					$.jBox.tip(data.msg, "error",{timeout:1000});
				}
			}
		});
 }
function deleteTag(id){
	$.ajax({
		type: "POST",
		url: "${ctx}/manager/customer/BaseComminfoTags.do?method=delettags&id="+id,
		data: $(f0).serialize(),
		dataType: "json",
		error: function(request, settings) {},
		success: function(data) {
			if (data.code == 1) {
				$.jBox.tip("删除成功", "success",{timeout:1000});
				window.setTimeout(function(){
					window.location.reload();
				},1500);
			} else{
				$.jBox.tip(data.msg, "error",{timeout:1000});
			}
		}
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
</body>
</html>

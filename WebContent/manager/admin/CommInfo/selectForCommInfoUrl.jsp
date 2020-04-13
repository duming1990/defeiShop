<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择商品链接  - ${app_name}</title>
<meta content="${app_name}会员中心关键字" name="keywords" />
<meta content="${app_name}会员中心介绍" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<%-- <link href="${ctx}/commons/styles/green/base.css" rel="stylesheet" type="text/css" /> --%>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/admin/css/admin.css?v20170426"  />
</head>
<body>
<div class="divContent">
  <div style="margin-bottom: 2px;">
    <ul class="rtitnav">
      <li id="status1" <c:if test="${af.map.tab eq 1 or empty af.map.tab}"> class="cur" </c:if>><a href="CommInfo.do?method=selectForCommInfoUrl&tab=1&is_m=${af.map.is_m}">产品链接</a></li>
      <li id="status2" <c:if test="${af.map.tab eq 2}"> class="cur" </c:if>><a href="CommInfo.do?method=selectForCommInfoUrl&tab=2&is_m=${af.map.is_m}">分类链接</a></li>
      <li id="status3" <c:if test="${af.map.tab eq 3}"> class="cur" </c:if>><a href="CommInfo.do?method=selectForCommInfoUrl&tab=3&is_m=${af.map.is_m}">店铺链接</a></li>
      <li id="status4" <c:if test="${af.map.tab eq 4}"> class="cur" </c:if>><a href="CommInfo.do?method=selectForCommInfoUrl&tab=4&is_m=${af.map.is_m}">搜索条件</a></li>
      <li id="status5" <c:if test="${af.map.tab eq 5}"> class="cur" </c:if>><a href="CommInfo.do?method=selectForCommInfoUrl&tab=5&is_m=${af.map.is_m}">类型链接</a></li>
      <li id="status6" <c:if test="${af.map.tab eq 6}"> class="cur" </c:if>><a href="CommInfo.do?method=selectForCommInfoUrl&tab=6&is_m=${af.map.is_m}">线下分类链接</a></li>
    </ul>
  </div>
  <c:if test="${af.map.tab eq 1 or empty af.map.tab}">
    <div id="tab_1">
      <html-el:form action="/admin/CommInfo" styleClass="searchForm">
        <html-el:hidden property="method" value="selectForCommInfoUrl" />
        <html-el:hidden property="mod_id" />
        <input type="hidden" name="is_m" id="is_m" value="${af.map.is_m}" />
        <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
          <tr>
            <td>产品类别：
              <html-el:hidden property="cls_id" styleId="cls_id"/>
              <html-el:text property="cls_name" styleId="cls_name" readonly="true" onclick="getParClsInfo();" styleClass="webinput" maxlength="50"  />
              商品名称：
              <html-el:text property="comm_name_like" styleClass="webinput" maxlength="50"/>
              店铺名称：
              <html-el:hidden property="own_entp_id" styleId="own_entp_id" />
              <html-el:text property="entp_name" styleId="entp_name" maxlength="125" styleClass="webinput" value="${entp_name}" readonly="true" onclick="openEntpChild()"/>
              &nbsp;&nbsp;
              <html-el:submit value="查 询" styleClass="bgButton" styleId="bgButton" /></td>
          </tr>
        </table>
      </html-el:form>
      <%@ include file="/commons/pages/messages.jsp" %>
      <form id="listForm" name="listForm" method="post" action="CommInfo.do?method=delete">
        <input type="hidden" name="method" id="method" value="delete" />
        <input type="hidden" name="mod_id" id="mod_id" value="${af.map.mod_id}" />
        <input type="hidden" name="is_m" id="is_m" value="${af.map.is_m}" />
        <input type="hidden" name="comm_type" id="comm_type" value="${af.map.comm_type}" />
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
          <tr class="tite2">
            <th width="5%" >序号 </th>
            <th nowrap="nowrap">商品名称</th>
            <th width="10%">店铺名称</th>
            <th width="8%">产品类别</th>
            <th width="8%">商品编号</th>
            <th width="6%">参考价格</th>
            <th width="9%">商品库存</th>
            <th width="8%">上下架时间 </th>
            <th width="10%" >操作</th>
          </tr>
          <c:forEach var="cur" items="${entityList}" varStatus="vs">
            <tr align="center">
              <td>${vs.count }</td>
              <td align="left"><c:out value="${fnx:abbreviate(cur.comm_name, 60, '...')}" /></td>
              <td>${fn:escapeXml(cur.map.entp_name)}</td>
              <td>${fn:escapeXml(cur.cls_name)}</td>
              <td>${fn:escapeXml(cur.comm_no)}</td>
              <td><fmt:formatNumber pattern="#,##0.00" value="${cur.price_ref}"/></td>
              <td><c:if test="${not empty cur.map.count_tczh_price_inventory}">
                  <c:if test="${cur.map.count_tczh_price_inventory le 0}"> <span> <a style="color:#F00;" id="edittcfw" onclick="doNeedMethod('null', 'CommInfo.do', 'edittcfw', 'comm_id=${cur.id}&' + $('#bottomPageForm').serialize())"> 库存不足，请及时修改库存</a></span> </c:if>
                  <c:if test="${cur.map.count_tczh_price_inventory gt 0}"> <span style="color:#060;">库存充足</span> </c:if>
                </c:if>
                <c:if test="${empty cur.map.count_tczh_price_inventory}"> <span style="color:#F00;"> <a style="color:#F00;" id="edittcfw" onclick="doNeedMethod('null', 'CommInfo.do', 'edittcfw', 'comm_id=${cur.id}&' + $('#bottomPageForm').serialize())"> 没有维护套餐，没有库存</a></span> </c:if></td>
              <td><fmt:formatDate value="${cur.up_date}" pattern="yyyy-MM-dd" />
                至
                <fmt:formatDate value="${cur.down_date}" pattern="yyyy-MM-dd" /></td>
              <td><a class="butbase" href="#" onclick="getPdUrl('${cur.id}','${cur.comm_name }');" ><span class="icon-ok">选择</span></a></td>
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
      <div class="pageClass">
        <form id="bottomPageForm" name="bottomPageForm" method="post" action="CommInfo.do">
          <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
            <tr>
              <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
                <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "selectForCommInfoUrl");
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
					pager.addHiddenInputs("is_m", "${af.map.is_m}");
					document.write(pager.toString());
	            	</script></td>
            </tr>
          </table>
        </form>
      </div>
      <div class="clear"></div>
    </div>
  </c:if>
  <c:if test="${af.map.tab eq 2 }">
    <div id="tab_2">
      <html-el:form action="/admin/CommInfo" styleClass="searchForm">
        <html-el:hidden property="method" value="selectForCommInfoUrl" />
        <html-el:hidden property="mod_id" />
        <html-el:hidden property="cls_level" styleId="cls_level" />
        <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
          <tr>
            <td>产品类别：
              <html-el:hidden property="cls_id" styleId="cls_id"/>
              <html-el:text property="cls_name" styleId="cls_name" readonly="true" onclick="getParClsInfo();" styleClass="webinput" maxlength="50"  />
              &nbsp;&nbsp; <a class="butbase" href="#" onclick="getPdTypeUrl();" ><span class="icon-ok">选择</span></a></td>
          </tr>
        </table>
      </html-el:form>
    </div>
  </c:if>
  <c:if test="${af.map.tab eq 3 }">
    <div id="tab_3">
      <html-el:form action="/admin/CommInfo" styleClass="searchForm">
        <html-el:hidden property="method" value="selectForCommInfoUrl" />
        <html-el:hidden property="mod_id" />
        <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
          <tr>
            <td>店铺名称：
              <html-el:hidden property="own_entp_id" styleId="own_entp_id" />
              <html-el:text property="entp_name" styleId="entp_name" maxlength="125" styleClass="webinput" value="${entp_name}" readonly="true" onclick="openEntpChild()"/>
              &nbsp;&nbsp; <a class="butbase" href="#" onclick="getEntpUrl();" ><span class="icon-ok">选择</span></a></td>
          </tr>
        </table>
      </html-el:form>
    </div>
  </c:if>
  <c:if test="${af.map.tab eq 4 }">
    <div id="tab_4">
      <html-el:form action="/admin/CommInfo" styleClass="searchForm">
        <html-el:hidden property="method" value="selectForCommInfoUrl" />
        <html-el:hidden property="mod_id" />
        <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
          <tr>
            <td>搜索条件：
              <html-el:text property="comm_name_like" styleClass="webinput" maxlength="50" styleId="comm_name_like"/>
              &nbsp;&nbsp; <a class="butbase" href="#" onclick="getSearchUrl();" ><span class="icon-ok">选择</span></a></td>
          </tr>
        </table>
      </html-el:form>
    </div>
  </c:if>
  <c:if test="${af.map.tab eq 5 }">
    <div id="tab_5">
      <html-el:form action="/admin/CommInfo" styleClass="searchForm">
        <html-el:hidden property="method" value="selectForCommInfoUrl" />
        <html-el:hidden property="mod_id" />
        <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
          <tr>
            <td>产品类别：
              <html-el:hidden property="cls_id" styleId="cls_id"/>
              <html-el:text property="cls_name" styleId="cls_name" readonly="true" onclick="getParClsInfo();" styleClass="webinput" maxlength="50"  />
              &nbsp;&nbsp; <a class="butbase" href="#" onclick="getPdCommUrl();" ><span class="icon-ok">选择</span></a></td>
          </tr>
        </table>
      </html-el:form>
    </div>
  </c:if>
  <c:if test="${af.map.tab eq 6}">
    <div id="tab_6">
      <html-el:form action="/admin/CommInfo" styleClass="searchForm">
        <html-el:hidden property="method" value="selectForCommInfoUrl" />
        <html-el:hidden property="mod_id" />
        <html-el:hidden property="cls_scope" value="2" />
        <html-el:hidden property="cls_level" styleId="cls_level" />
        <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
          <tr>
            <td>产品类别：
              <html-el:hidden property="cls_id" styleId="cls_id"/>
              <html-el:text property="cls_name" styleId="cls_name" readonly="true" onclick="getXianxiaParClsInfo();" styleClass="webinput" maxlength="50"  />
              &nbsp;&nbsp; <a class="butbase" href="#" onclick="getXianxiaPdTypeUrl();" ><span class="icon-ok">选择</span></a></td>
          </tr>
        </table>
      </html-el:form>
    </div>
  </c:if>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script> 
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
		title:  "选择店铺",
		width:  450,
		height: 420,
        lock:true ,
        zIndex:999999,
		content:"url:"+url
	});
}

function getParClsInfo() {
	var url = "BasePdClass.do?method=getParClsInfo&isPd=true&isLevel=true&azaz=" + Math.random();
	$.dialog({
		title:  "选择产品类别",
		width:  450,
		height: 400,
        lock:true ,
        zIndex:999999,
		content:"url:"+url
	});
}
function getXianxiaParClsInfo() {
	var url = "BasePdClass.do?method=getParClsInfo&cls_scope=1&isLevel=true&isPd=true&azaz=" + Math.random();
	$.dialog({
		title:  "选择产品类别",
		width:  450,
		height: 400,
        lock:true ,
        zIndex:999999,
		content:"url:"+url
	});
}


function returnUrl(url,cls_name){
	var api = frameElement.api, W = api.opener; 
	W.document.getElementById("link_url").value ="http://"+ window.location.host+url; 
	W.document.getElementById("title").value = cls_name;
	if($("#cls_id").val()){
	W.document.getElementById("cls_id").value = $("#cls_id").val(); 
	}
	api.close();
}
<c:if test="${not empty af.map.is_m}">
<c:url var="pd_url" value="/m/MEntpInfo.do?id=" />
<c:url var="pd_type_url_level1" value="/m/MSearch.do?method=listPd&root_cls_id=" />
<c:url var="pd_type_url_level2" value="/m/MSearch.do?method=listPd&par_cls_id=" />
<c:url var="pd_xianxia_type_url_level1" value="/m/MSearch.do?method=listEntp&par_cls_id=" />
<c:url var="pd_xianxia_type_url_level2" value="/m/MSearch.do?method=listEntp&son_cls_id=" />
<c:url var="entp_url" value="/m/MSearch.do?htype=0&entp_id=" />
<c:url var="search_url" value="/m/MSearch.do?htype=0&keyword=" />
<c:url var="pd_comm_url" value="/m/MSearch.do?method=listPd&cls_id=" />
<c:url var="brand_url" value="/m/MSearch.do?method=listPd&brand_id=" />
</c:if>
<c:if test="${empty af.map.is_m}">
<c:url var="pd_url" value="/entp/IndexEntpInfo.do?method=getCommInfo&id=" />
<c:url var="pd_type_url" value="/IndexPd.do?method=listPdList&cls_id=" />
<c:url var="entp_url" value="/Search.do?htype=0&entp_id=" />
<c:url var="search_url" value="/Search.do?htype=0&keyword=" />
<c:url var="search_url" value="/Search.do?htype=0&keyword=" />
</c:if>

function getPdUrl(id,title){
	var url='${pd_url}'+id;
 	returnUrl(url,title);
}
function getBrandUrl(id,title){
 	var url='${brand_url}'+id;
 	returnUrl(url,title);
}

function getPdTypeUrl(){ 
	
	var cls_id=$("#cls_id").val();
	var cls_name = $("#cls_name").val();
	if(cls_id!=''){
		if($("#cls_level").val() == 1){
			var url='${pd_type_url_level1}'+cls_id;
		}
		if($("#cls_level").val() == 2){
			var url='${pd_type_url_level2}'+cls_id;
		}
		returnUrl(url,cls_name);
	}else{
		alert('请选择产品分类');
	}
}
function getXianxiaPdTypeUrl(){ 
	
	var cls_id=$("#cls_id").val();
	var cls_name = $("#cls_name").val();
	if(cls_id!=''){
		if($("#cls_level").val() == 1){
			var url='${pd_xianxia_type_url_level1}'+cls_id;
		}
		if($("#cls_level").val() == 2){
			var url='${pd_xianxia_type_url_level2}'+cls_id;
		}
		returnUrl(url,cls_name);
	}else{
		alert('请选择线下店铺分类');
	}
}

function getEntpUrl(){ 
	var own_entp_id=$("#own_entp_id").val();
	var entp_name = $("#entp_name").val();
	
	if(own_entp_id!=''){ 
		var url='${entp_url}'+own_entp_id;
		returnUrl(url,entp_name);
	}else{
		alert('请选择店铺');
	}
}

function getSearchUrl(){ 
	var comm_name_like=$("#comm_name_like").val();
	if(comm_name_like!=''){ 
		var url='${search_url}'+comm_name_like;
		returnUrl(url,comm_name_like);
	}else{
		alert('请输入搜索条件');
	}
}
function getPdCommUrl(){ 
	var cls_id=$("#cls_id").val();
	var cls_name = $("#cls_name").val();
	if(cls_id!=''){
		var url='${pd_comm_url}'+cls_id;
		returnUrl(url,cls_name);
	}else{
		alert('请选择产品分类');
	}
}

//]]></script>
<jsp:include page="../../../_public_page.jsp" flush="true"/>
</body>
</html>
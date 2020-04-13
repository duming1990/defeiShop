<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>请选择类别</title>
<base target="_self" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="MSThemeCompatible" content="no" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div id="navTab" class="tabsPage" style="text-align:left;">
  <div class="navTab-panel tabsPageContent">
    <div class="pageContent">
      <html-el:form action="/admin/BasePdClass">
        <html-el:hidden property="queryString" styleId="queryString" />
        <html-el:hidden property="method" styleId="method" value="save" />
        <html-el:hidden property="id" styleId="id" />
        <div style="height:5px;"></div>
        <table width="100%" border="0" class="list">
          <tr>
            <td>搜索关键字 ：
              <html-el:text property="key_word" styleId="key_word" size="24" maxlength="100" style="width:210px;" title="可以输入关键字进行即时搜索" /></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>
                    <html-el:select property="select1" multiple="true" style="width:400px;height:358px;" styleId="select1" onclick="returnPdClass(this.form.select1)" >
                      <c:forEach var="cur" items="${entityList}" varStatus="vs">
                      <c:if test="${not empty af.map.isSelectAll}">
                      <option value="${cur.cls_id}_${cur.cls_name}">${fn:escapeXml(cur.cls_name)}</option>
                      </c:if>
                      <c:if test="${empty af.map.isSelectAll}">
                          <c:if test="${cur.is_leaf eq 0}"><optgroup label="${fn:escapeXml(cur.cls_name)}"/></c:if>
                          <c:if test="${cur.is_leaf eq 1}"><option value="${cur.cls_id}_${cur.cls_name}">${fn:escapeXml(cur.cls_name)}</option></c:if>
                      </c:if>
                      </c:forEach>
                    </html-el:select></td>
                </tr>
              </table></td>
          </tr>
        </table>
      </html-el:form>
    </div>
  </div>
</div>

<script type="text/javascript">//<![CDATA[
var f = document.forms[0];
$(document).ready(function(){
	$("#key_word").attr("autocomplete", "off");
	$("#key_word").attr("disableautocomplete", "true");
	
    var bind_name = "input";
    if (navigator.userAgent.indexOf("MSIE") != -1){
        bind_name = "propertychange";
    }
    $("#key_word").bind(bind_name, function(){
    	getQueryNames($(this).val());
    });
    
    $("#key_word").keypress(function (e) {
        var k = e.keyCode || e.which;
        if (k == 13) {
            return false;
        }
    });
});

function getQueryNames(key_word) {
   	$("#select1").empty();
   	<c:if test="${empty af.map.select_for_pdInfo}">
   	$.ajax({
		type: "POST",
		cache: false,
		url: "BasePdClass.do?",
		data: "method=" + "getParClsInfo&ajax=1&pd_class_type=${af.map.pd_class_type}&" + "key_word=" + key_word,
		dataType: "json",
		error: function(request, settings){},
		success: function(data) {
			if (data.list.length >= 1) {
				for(var i = 0; i < data.list.length; i++) {
					var cur=data.list[i];
					var opt;
                    <c:if test="${not empty af.map.isSelectAll}">
                    opt = new Option(cur.cls_name,cur.cls_id+"_"+cur.cls_name);
                    $("#select1").get(0).add(opt);
                    </c:if>
                    <c:if test="${empty af.map.isSelectAll}">
    				if (cur.is_leaf == 0) {
						opt = document.createElement("optgroup");
						opt.label = cur.cls_name;
						$("#select1").get(0).appendChild(opt);
					} else {
						opt = new Option(cur.cls_name,cur.cls_id+"_"+cur.cls_name);
						$("#select1").get(0).add(opt);
					}
                    </c:if>
	
					
				}
			}
		}
	});
   	</c:if>
   	<c:if test="${not empty af.map.select_for_pdInfo}">
   	$.ajax({
		type: "POST",
		cache: false,
		url: "BasePdClass.do?",
		data: "method=" + "getParClsInfoForSelectPdInfo&ajax=1&main_pd_class_ids=${af.map.main_pd_class_ids}&" + "key_word=" + key_word,
		dataType: "json",
		error: function(request, settings){},
		success: function(data) {
			if (data.list.length >= 1) {
				for(var i = 0; i < data.list.length; i++) {
					var cur=data.list[i];
					var opt;
                    <c:if test="${not empty af.map.isSelectAll}">
                    opt = new Option(cur.cls_name,cur.cls_id+"_"+cur.cls_name);
                    $("#select1").get(0).add(opt);
                    </c:if>
                    <c:if test="${empty af.map.isSelectAll}">
    				if (cur.is_leaf == 0) {
						opt = document.createElement("optgroup");
						opt.label = cur.cls_name;
						$("#select1").get(0).appendChild(opt);
					} else {
						opt = new Option(cur.cls_name,cur.cls_id+"_"+cur.cls_name);
						$("#select1").get(0).add(opt);
					}
                    </c:if>
	
					
				}
			}
		}
	});
   	</c:if>
}


function returnPdClass(select){
	var cls_id="";
	var cls_name = "";
	for (var i = select.options.length - 1; i >= 0; i--){
		if (select.options[i].selected){
			var value=select.options[i].value;
			cls_id=value.split("_")[0];
			cls_name=value.split("_")[1];
		}
	}
	var api = frameElement.api, W = api.opener;
	if("" != cls_id && null != cls_id && "" != cls_name && null != cls_name){
		$.ajax({
			type: "POST",
			url: "BasePdClass.do?method=getRootClsInfo",
			data: "cls_id=" + cls_id,
			dataType: "json",
			error: function(request, settings) {alert("数据导出失败！");},
			success: function(data) {
				<c:if test="${(not empty from_basePdClass)}">
				W.document.getElementById("root_id").value = data.root_id;
				W.document.getElementById("root_name").value = data.root_name;
				W.document.getElementById("par_id").value = cls_id;
				W.document.getElementById("par_name").value = cls_name;
				</c:if>
				<c:if test="${(empty from_basePdClass)}">
				W.document.getElementById("cls_id").value = cls_id;
				W.document.getElementById("cls_name").value = cls_name;
				<c:if test="${(empty from_rule_query)}">
				W.document.getElementById("par_cls_id").value = data.root_id;
				W.document.getElementById("pd_no").value = data.pd_no;
				</c:if>
				W.getAttrToHtml("${ctx}/CsAjax.do?method=getPdAttrbute",cls_id);
				</c:if>
				<c:if test="${not empty af.map.select_for_pdInfo or (not empty af.map.from_pdInfo)}">
					W.CheckClsId();
				</c:if>
				
				api.close();
			}
		});
}
}

//]]></script>
</body>
</html>

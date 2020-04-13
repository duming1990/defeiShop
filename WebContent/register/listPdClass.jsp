<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base target="_self" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/styles/green/base.css" rel="stylesheet" type="text/css" />
<title>选择主营产品</title>
<style type="text/css">
body {
	background:#F8F8F8;
	font-family:"微软雅黑", "宋体";
}
fieldset {
	border:1px solid #CCC/*A3C0E8*/;
	text-align:left;
	margin:0 auto;
	margin:5px;
	border-radius:2px;
	background:#FFF;
}
fieldset a {
	height:30px;
	line-height:30px;
	background:#009900;
	color:#FFF;
	margin:2px 5px;
	padding:2px 8px;
	border-radius: 5px;
}
fieldset a:hover {
	background:#00B800;
	text-decoration:none;
	color:#FFF;/*color:#FFF0AC;*/
}
fieldset a:visited {
	color:#FFF;
	text-decoration:none;
}
legend {
	font-weight: bold;
}
.dataContent {
	width:99%;
	overflow:hidden;/*border:1px solid #A3C0E8;*/
	margin:5px auto;
	overflow:auto;/*height:320px;*/
}
.ul_selpd {
	padding-left:30px;
}
.ul_selpd li {/*width:210px;*/
	width:20%;
	line-height:18px;
	overflow:hidden;
	float:left;
	padding:5px;
	margin:5px;
	text-align:left;
}
.ul_selpd li b {
	float:right;
	font-weight:normal;
	padding-left:5px;
	font-size:12px;
}
.dataContent input,label{
	cursor:pointer;
}
.cked {
	background:#FFF0AC;/*#F1F8FF;*/
	border-radius: 5px;
}
.cked label {
	color:#FF5809;
	text-decoration:underline;/*#F1F8FF;*/
}
.fdul li{float:left;}
</style>
</head>
<body>
<div style="background:#FFFFFF;">&nbsp;</div>
<fieldset>
	<legend>已选&nbsp;${_cs_user}</legend>
	<div id="chZj" style="height:45px;overflow:auto;">
		<ul id="fdul" class="fdul">
			<c:forEach items="${baseClass2List}" var="cus">
				<li><a href="javascript:void(0);" id="a_${cus.cls_id}">${cus.cls_name}</a></li>
			</c:forEach>
		</ul>
	</div>
</fieldset>
<div align="center" style="height:305px;overflow:auto;">
	<div style="height:75px;">
		<html-el:form action="/Register.do">
			<html-el:hidden property="method" value="listPdClass" />
			<html-el:hidden property="main_pd_class_ids" styleId="main_pd_class_ids"/>
			<html-el:hidden property="main_pd_class_names" styleId="main_pd_class_names"/>
			<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="tableClass">
				<tr>
					<td align="left" nowrap="nowrap">
						主营产品名称：<html-el:text property="cls_name_like" styleClass="webinput" size="40" />
						&nbsp;<a class="butbase" id="btn_search" style="cursor:pointer;"><span class="icon-find">查询</span></a>
					</td>
				</tr>
			</table>
		</html-el:form>
	</div>
	<div style="height:200px;overflow:auto;">
		<form action="Register.do?method=listPdClass">
			<div style="text-align:left;padding-left:30px;margin-top:10px;margin-bottom:5px;">
				<c:if test="${not empty baseClassList}">
					<input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkCtAll(this);" /><label for="chkAll">全选</label>
				</c:if>
			</div>
			<div>
				<ul class="ul_selpd">
					<c:forEach var="cur" items="${baseClassList}" varStatus="vs1">
						<li title="${cur.cls_name}">
							<input name="pks" type="checkbox" id="pks_${cur.cls_id}" value="${cur.cls_id}+${cur.cls_name}" onclick="checkWk(this)" />
							<label for="pks_${cur.cls_id}">
								<span style="font-size:14px;">${fn:escapeXml(cur.cls_name)}</span><br/>
							</label>
						</li>
					</c:forEach>
				</ul>
			</div>
		</form>
	</div>
</div>
<div style="height:30px;width:100%;text-align:right;padding-top:10px;background:#EEEEEE;border-top:1px solid #CCCCCC;">
	<a class="butbase" href="#" onclick="returnVal();" ><span class="icon-ok">确定</span></a>&nbsp;
	<a class="butbase" href="#" onclick="var api = frameElement.api, W = api.opener;api.close();" ><span class="icon-remove">关闭</span></a>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	var f = document.forms[0];
	$("#btn_search").click(function(){
		f.submit();
		
	});
	
	$(".Tab tr").mouseover(function(){  
		$(this).addClass("over");
	}).mouseout(function(){
		$(this).removeClass("over");
	})
	$(".Tab tr:even").addClass("alteven");
	$(".Tab tr:odd").addClass("altodd");

	if ("" != "${af.map.main_pd_class_ids}") {
		var main_pd_class_ids = "${af.map.main_pd_class_ids}";
		var custIdsArray = new Array();
		custIdsArray = main_pd_class_ids.split(",");
		var cks = document.getElementsByName("pks");
		for ( var i = 0; i < cks.length; i++) {
			for ( var j = 0; j < custIdsArray.length; j++) {
				if (cks[i].value.split("+")[0] == custIdsArray[j]) {
					cks[i].checked = true;
					$(cks[i]).parent().addClass("cked");
				}
			}
		}
	}
	
});

//多选
function checkCtAll(e) {
	var idsArray = new Array();
	var namesArray = new Array();
	var wkIdsArray = new Array();
	var wkNamesArray = new Array();
	if (e.form.elements) {
		for (var i = e.form.elements.length - 1; i > 0; i--) {
			if (e.form.elements[i].type == "checkbox" && e.form.elements[i].disabled == false) {
				e.form.elements[i].checked = e.checked;
			}
		}
	
		var cks = document.getElementsByName("pks");
		if (e.checked) {
			$("a", "#fdul").each(function(){
				for ( var i = 0; i < cks.length; i++) {
					if ($(this).attr("id").split("_")[1] == cks[i].value.split("+")[0]) {
						$(this).parent().remove();
					}
				}
			});
	
			$("a", "#fdul").each(function(){
				var id = $(this).attr("id").split("_")[1];
				var name = $(this).text;
				idsArray.push(id);
				namesArray.push(name);
			});
			
			for ( var i = 0; i < cks.length; i++) {
				wkIdsArray.push(cks[i].value.split("+")[0]);
				wkNamesArray.push(cks[i].value.split("+")[1]);
				$("#fdul").append('<li><a href="javascript:void(0);" id="a_' + cks[i].value.split("+")[0] + '">' + cks[i].value.split("+")[1] + '</a></li>');
			}
	
			for ( var x = 0; x < idsArray.length; x++) {
				wkIdsArray.push(idsArray[x]);
			}
			for ( var y = 0; y < namesArray.length; y++) {
				wkNamesArray.push(namesArray[y]);
			}
			var ids = wkIdsArray.join(",");
			var names = wkNamesArray.join(",");
			$("input[name='main_pd_class_ids']").val(ids);
			$("input[name='main_pd_class_names']").val(names);
		} else {
			$("a", "#fdul").each(function(){
				for ( var i = 0; i < cks.length; i++) {
					if ($(this).attr("id").split("_")[1] == cks[i].value.split("+")[0]) {
						$(this).parent().remove();
					}
				}
			});
			$("a", "#fdul").each(function(){
				var id = $(this).attr("id").split("_")[1];
				var name = $(this).text;
				idsArray.push(id);
				namesArray.push(name);
			});
			var ids = idsArray.join(",");
			var names = namesArray.join(",");
			$("input[name='main_pd_class_ids']").val(ids);
			$("input[name='main_pd_class_names']").val(names);
		}
	}
}

//单选
function checkWk(e) {
	var wkIdsArray = new Array();
	var wkNamesArray = new Array();
	if (e.checked) { //选中
		$(e).parent().addClass("cked");//改变样式
		/******************************************/
		if ("" != $("#main_pd_class_ids").val()) {
			wkIdsArray = $("#main_pd_class_ids").val().split(",");
		}
		wkIdsArray.push($(e).val().split("+")[0]);
		var ids = wkIdsArray.join(",");
		$("input[name='main_pd_class_ids']").val(ids);
		/******************************************/
		if ("" != $("#main_pd_class_names").val()) {
			wkNamesArray = $("#main_pd_class_names").val().split(",");
		}
		wkNamesArray.push($(e).val().split("+")[1]);
		var names = wkNamesArray.join(",");
		$("input[name='main_pd_class_names']").val(names);
		/******************************************/
		$("#fdul").append('<li><a href="javascript:void(0);" id="a_' + $(e).val().split("+")[0] + '">' + $(e).val().split("+")[1] + '</a></li>');
	} else { //取消选中
		$(e).parent().removeClass("cked");
		/******************************************/
		var twkArray = new Array();
		if ("" != $("#main_pd_class_ids").val()) {
			wkIdsArray = $("#main_pd_class_ids").val().split(",");
		}
		for ( var i = 0; i < wkIdsArray.length; i++) {
			if (wkIdsArray[i] != $(e).val().split("+")[0]) {
				twkArray.push(wkIdsArray[i]);
			}
		}
		var ids = twkArray.join(",");
		$("input[name='main_pd_class_ids']").val(ids);
		/******************************************/
		var twknArray = new Array();
		if ("" != $("#main_pd_class_names").val()) {
			wkNamesArray = $("#main_pd_class_names").val().split(",");
		}
		for ( var i = 0; i < wkNamesArray.length; i++) {
			if (wkNamesArray[i] != $(e).val().split("+")[1]) {
				twknArray.push(wkNamesArray[i]);
			}
		}
		var names = twknArray.join(",");
		$("input[name='main_pd_class_names']").val(names);
		/******************************************/
		$("a", "#fdul").each(function(){
			var aid = $(this).attr("id").split("_")[1];
			if ($(e).val().split("+")[0] == aid) {
				$(this).parent().remove();
			}
		});
	}
	var main_pd_class_ids = $("input[name='main_pd_class_ids']").val();
	var main_pd_class_names = $("input[name='main_pd_class_names']").val();
}

function returnVal(){
	var api = frameElement.api, W = api.opener;
	if(null != "${af.map.cls_scope}" && "${af.map.cls_scope}" == "2"){
		W.document.getElementById("xianxia_pd_class_ids").value = $("input[name='main_pd_class_ids']").val();
		W.document.getElementById("xianxia_pd_class_names").value = $("input[name='main_pd_class_names']").val();
	}else{
		W.document.getElementById("main_pd_class_ids").value = $("input[name='main_pd_class_ids']").val();
		W.document.getElementById("main_pd_class_names").value = $("input[name='main_pd_class_names']").val();
	}
	
	api.close();
}
//]]></script>
</body>
</html>
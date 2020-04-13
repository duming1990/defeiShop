<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>请选择用户</title>
<base target="_self" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/styles/green/base.css" rel="stylesheet" type="text/css" />
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
.dataContent input, label {
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
.fdul li {
	float:left;
}
</style>
</head>
<body>
<div class="divContent" style="margin: 5px;">
  <fieldset>
    <legend>已选&nbsp;${_cs_user}</legend>
    <div id="chZj" style="height:45px;overflow:auto;">
      <ul id="fdul" class="fdul">
        <c:forEach items="${userListSelected}" var="cur">
          <li><a href="javascript:void(0);" id="a_${cur.id}">${cur.user_name}</a></li>
        </c:forEach>
      </ul>
    </div>
  </fieldset>
  <html-el:form action="/CsAjax">
    <html-el:hidden property="method" value="getUserInfo" />
    <html-el:hidden property="user_type" />
    <html-el:hidden property="type" />
    <html-el:hidden property="user_ids" styleId="user_ids"/>
    <html-el:hidden property="user_names" styleId="user_names"/>
    <html-el:hidden property="dosomeThing"/>
    <html-el:hidden property="userTypeNotIn"/>
    <table width="99%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td>用户姓名:
          <html-el:text property="user_name_like" maxlength="20" styleId="nurse_name_like" style="width:80px;" styleClass="webinput" />
          &nbsp;手机号码:
          <html-el:text property="mobile_like" maxlength="20" styleId="mobile_like" style="width:80px;" styleClass="webinput" />
          &nbsp;
          <html-el:submit value="查 询" styleClass="bgButton" /></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" >
    <div style="text-align:left;padding-left:30px;margin-top:10px;margin-bottom:5px;">
      <c:if test="${not empty entityList}">
        <input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkCtAll(this);" />
        <label for="chkAll">全选</label>
      </c:if>
      <span style="margin-left: 20px;"><a class="butbase" href="#" onclick="returnVal();" ><span class="icon-ok">确定</span></a>&nbsp; <a class="butbase" href="#" onclick="var api = frameElement.api, W = api.opener;api.close();" ><span class="icon-remove">关闭</span></a></span> </div>
    <div>
      <ul class="ul_selpd">
        <c:forEach var="cur" items="${entityList}" varStatus="vs1">
          <li class="qtip" title="真实姓名：${cur.real_name}，手机：${fn:escapeXml(cur.mobile)}">
            <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}+${cur.user_name}" onclick="checkWk(this)" />
            <label for="pks_${cur.id}"> <span style="font-size:14px;">${fn:escapeXml(cur.user_name)}</span><br/>
            </label>
          </li>
        </c:forEach>
      </ul>
    </div>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="CsAjax.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr align="center">
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "getUserInfo");
            pager.addHiddenInputs("user_name_like", "${fn:escapeXml(af.map.user_name_like)}");
			pager.addHiddenInputs("is_del", "${af.map.is_del}"); 
			pager.addHiddenInputs("mobile_like", "${af.map.mobile_like}"); 
			pager.addHiddenInputs("sex", "${af.map.sex}");
			pager.addHiddenInputs("type", "${af.map.type}");
			pager.addHiddenInputs("user_type", "${af.map.user_type}");
			pager.addHiddenInputs("dosomeThing", "${af.map.dosomeThing}");
			pager.addHiddenInputs("userTypeNotIn", "${af.map.userTypeNotIn}");
            document.write(pager.toString());
            </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	$(".qtip").quicktip();
	
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

	if ("" != "${af.map.user_ids}") {
		var user_ids = "${af.map.user_ids}";
		var custIdsArray = new Array();
		custIdsArray = user_ids.split(",");
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
			$("input[name='user_ids']").val(ids);
			$("input[name='user_names']").val(names);
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
			$("input[name='user_ids']").val(ids);
			$("input[name='user_names']").val(names);
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
		if ("" != $("#user_ids").val()) {
			wkIdsArray = $("#user_ids").val().split(",");
		}
		wkIdsArray.push($(e).val().split("+")[0]);
		var ids = wkIdsArray.join(",");
		$("input[name='user_ids']").val(ids);
		/******************************************/
		if ("" != $("#user_names").val()) {
			wkNamesArray = $("#user_names").val().split(",");
		}
		wkNamesArray.push($(e).val().split("+")[1]);
		var names = wkNamesArray.join(",");
		$("input[name='user_names']").val(names);
		/******************************************/
		$("#fdul").append('<li><a href="javascript:void(0);" id="a_' + $(e).val().split("+")[0] + '">' + $(e).val().split("+")[1] + '</a></li>');
	} else { //取消选中
		$(e).parent().removeClass("cked");
		/******************************************/
		var twkArray = new Array();
		if ("" != $("#user_ids").val()) {
			wkIdsArray = $("#user_ids").val().split(",");
		}
		for ( var i = 0; i < wkIdsArray.length; i++) {
			if (wkIdsArray[i] != $(e).val().split("+")[0]) {
				twkArray.push(wkIdsArray[i]);
			}
		}
		var ids = twkArray.join(",");
		$("input[name='user_ids']").val(ids);
		/******************************************/
		var twknArray = new Array();
		if ("" != $("#user_names").val()) {
			wkNamesArray = $("#user_names").val().split(",");
		}
		for ( var i = 0; i < wkNamesArray.length; i++) {
			if (wkNamesArray[i] != $(e).val().split("+")[1]) {
				twknArray.push(wkNamesArray[i]);
			}
		}
		var names = twknArray.join(",");
		$("input[name='user_names']").val(names);
		/******************************************/
		$("a", "#fdul").each(function(){
			var aid = $(this).attr("id").split("_")[1];
			if ($(e).val().split("+")[0] == aid) {
				$(this).parent().remove();
			}
		});
	}
	var user_ids = $("input[name='user_ids']").val();
	var user_names = $("input[name='user_names']").val();
}

function returnVal(){
	var api = frameElement.api, W = api.opener;
	W.document.getElementById("user_ids").value = $("input[name='user_ids']").val();
	W.document.getElementById("user_names").value = $("input[name='user_names']").val();
	<c:if test="${not empty af.map.dosomeThing}">
		W.${af.map.dosomeThing}();
	</c:if>
	api.close();
}
//]]></script>
</body>
</html>

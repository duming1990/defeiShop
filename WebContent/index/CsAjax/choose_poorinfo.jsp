<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>请选择贫困户</title>
<base target="_self" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
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
          <li><a href="javascript:void(0);" id="a_${cur.id}">${cur.real_name}</a></li>
        </c:forEach>
      </ul>
    </div>
  </fieldset>
  <html-el:form action="/CsAjax">
    <html-el:hidden property="method" value="choosePoorInfo" />
    <html-el:hidden property="user_type" />
    <html-el:hidden property="type" />
    <html-el:hidden property="ids" styleId="ids"/>
    <html-el:hidden property="names" styleId="names"/>
    <html-el:hidden property="p_names" styleId="p_names"/>
    <html-el:hidden property="dosomeThing"/>
    <html-el:hidden property="userTypeNotIn"/>
    <table width="99%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td>&nbsp;&nbsp;&nbsp;行政区划：&nbsp;<html-el:select property="province" styleId="province" styleClass="pi_prov" style="width:120px;">
	            <html-el:option value="">请选择...</html-el:option>
	          </html-el:select>
	          &nbsp;
	          <html-el:select property="city" styleId="city" styleClass="pi_city" style="width:120px;">
	            <html-el:option value="">请选择...</html-el:option>
	          </html-el:select>
	          &nbsp;
	          <html-el:select property="country" styleId="country" styleClass="pi_dist" style="width:120px;">
	            <html-el:option value="">请选择...</html-el:option>
	          </html-el:select>
	           &nbsp;
	          <html-el:select property="town" styleId="town" styleClass="pi_town" style="width:120px;">
	            <html-el:option value="">请选择...</html-el:option>
	          </html-el:select>
	           &nbsp;
	          <html-el:select property="village" styleId="village" styleClass="pi_village" style="width:120px;">
	            <html-el:option value="">请选择...</html-el:option>
	          </html-el:select>
	     </td>
      </tr>
      <tr>
      	<td>贫困户姓名：
          <html-el:text property="real_name_like" maxlength="20" styleId="real_name_like" style="width:80px;" styleClass="webinput" />
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
        <input name="random_num" placeholder="请填写随机人数" style="width: 100px;margin-left: 100px;vertical-align: middle;"/>
        <a class="butbase" href="#" onclick="returnVal(1);"><span class="icon-ok">随机确定</span></a>&nbsp; 
      </c:if>
      <span style="margin-left: 20px;">
      	 
	      <a class="butbase" href="#" onclick="returnVal(0);"><span class="icon-ok">确定</span></a>&nbsp; 
	      <a class="butbase" href="#" onclick="var api = frameElement.api, W = api.opener;api.close();" >
	      	<span class="icon-remove">关闭</span>
	      </a>
      </span> 
    </div>
    <div>
      <ul class="ul_selpd">
        <c:forEach var="cur" items="${entityList}" varStatus="vs1">
          <li class="qtip" title="真实姓名：${cur.real_name}，行政区划：${fn:escapeXml(cur.map.p_name)}，现有扶贫商品：${cur.map.comm_count}个，本年已扶贫金额：${cur.map.year_aid}">
            <c:set var="disabled" value="" />
            <c:if test="${cur.map.comm_count ge baseData1902.pre_number or (not empty cur.map.year_aid_out)}">
            <c:set var="disabled" value="disabled='disabled'" />
            </c:if>
            <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}+${cur.real_name}+${cur.map.p_name}" ${disabled} onclick="checkWk(this)" />
            <label for="pks_${cur.id}_${cur.map.p_name}"> <span style="font-size:14px;">${fn:escapeXml(cur.real_name)}</span><span style="font-size:14px;color:#f50606;">（${cur.map.comm_count}）</span><br/>
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
            pager.addHiddenInputs("method", "choosePoorInfo");
            pager.addHiddenInputs("pet_name_like", "${fn:escapeXml(af.map.pet_name_like)}");
            pager.addHiddenInputs("province", "${fn:escapeXml(af.map.province)}");
            pager.addHiddenInputs("city", "${fn:escapeXml(af.map.city)}");
            pager.addHiddenInputs("country", "${fn:escapeXml(af.map.country)}");
            pager.addHiddenInputs("town", "${fn:escapeXml(af.map.town)}");
            pager.addHiddenInputs("village", "${fn:escapeXml(af.map.village)}");
			pager.addHiddenInputs("type", "${af.map.type}");
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
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.cs.js"></script>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#province").attr({"subElement": "city", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.province}","datatype": "Require", "msg": "请选择省份"});
	$("#city").attr({"subElement": "country", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.city}","datatype": "Require", "msg": "请选择市"});
	$("#country").attr({"subElement": "town", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.country}","datatype": "Require", "msg": "请选择县"});
	$("#town").attr({"subElement": "village", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.town}","datatype": "Require", "msg": "请选择乡/镇"});
	$("#village" ).attr({"defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.village}","datatype": "Require", "msg": "请选择村"});
	$("#province").cs("${ctx}/BaseCsAjax.do?method=getBaseProvinceList", "p_index", "0", false);
	
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

	if ("" != "${af.map.ids}") {
		var user_ids = "${af.map.ids}";
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
	var pnamesArray = new Array();
	var wkIdsArray = new Array();
	var wkNamesArray = new Array();
	var wkPNamesArray = new Array();
	if (e.form.elements) {
		for (var i = e.form.elements.length - 1; i > 0; i--) {
			if (e.form.elements[i].type == "checkbox" && e.form.elements[i].disabled == false) {
				e.form.elements[i].checked = e.checked;
			}
		}
	
		var cks = $("input[name^='pks']").not(':disabled');
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
				var p_name = $(this).attr("id").split("_")[2];
				idsArray.push(id);
				namesArray.push(name);
				pnamesArray.push(p_name);
			});
			
			for ( var i = 0; i < cks.length; i++) {
				wkIdsArray.push(cks[i].value.split("+")[0]);
				wkNamesArray.push(cks[i].value.split("+")[1]);
				wkPNamesArray.push(cks[i].value.split("+")[2]);
				$("#fdul").append('<li><a href="javascript:void(0);" id="a_' + cks[i].value.split("+")[0] + '">' + cks[i].value.split("+")[1] + '</a></li>');
			}
	
			for ( var x = 0; x < idsArray.length; x++) {
				wkIdsArray.push(idsArray[x]);
			}
			for ( var y = 0; y < namesArray.length; y++) {
				wkNamesArray.push(namesArray[y]);
			}
			for ( var z = 0; z < pnamesArray.length; z++) {
				wkPNamesArray.push(pnamesArray[z]);
			}
			var ids = wkIdsArray.join(",");
			var names = wkNamesArray.join(",");
			var p_names = wkPNamesArray.join(",");
			$("input[name='ids']").val(ids);
			$("input[name='names']").val(names);
			$("input[name='p_names']").val(p_names);
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
				var p_name = $(this).attr("id").split("_")[2];
				idsArray.push(id);
				namesArray.push(name);
				pnamesArray.push(p_name);
			});
			var ids = idsArray.join(",");
			var names = namesArray.join(",");
			var p_names = pnamesArray.join(",");
			$("input[name='ids']").val(ids);
			$("input[name='names']").val(names);
			$("input[name='p_names']").val(p_names);
		}
	}
}

//单选
function checkWk(e) {
	var wkIdsArray = new Array();
	var wkNamesArray = new Array();
	var wkPNamesArray = new Array();
	if (e.checked) { //选中
		$(e).parent().addClass("cked");//改变样式
		/******************************************/
		if ("" != $("#ids").val()) {
			wkIdsArray = $("#ids").val().split(",");
		}
		wkIdsArray.push($(e).val().split("+")[0]);
		var ids = wkIdsArray.join(",");
		$("input[name='ids']").val(ids);
		/******************************************/
		if ("" != $("#names").val()) {
			wkNamesArray = $("#names").val().split(",");
		}
		wkNamesArray.push($(e).val().split("+")[1]);
		var names = wkNamesArray.join(",");
		$("input[name='names']").val(names);
		/******************************************/
		if ("" != $("#p_names").val()) {
			wkPNamesArray = $("#p_names").val().split(",");
		}
		wkPNamesArray.push($(e).val().split("+")[2]);
		var pnames = wkPNamesArray.join(",");
		$("input[name='p_names']").val(pnames);
		/******************************************/
		$("#fdul").append('<li><a href="javascript:void(0);" id="a_' + $(e).val().split("+")[0] + '">' + $(e).val().split("+")[1] + '</a></li>');
	} else { //取消选中
		$(e).parent().removeClass("cked");
		/******************************************/
		var twkArray = new Array();
		if ("" != $("#ids").val()) {
			wkIdsArray = $("#ids").val().split(",");
		}
		for ( var i = 0; i < wkIdsArray.length; i++) {
			if (wkIdsArray[i] != $(e).val().split("+")[0]) {
				twkArray.push(wkIdsArray[i]);
			}
		}
		var ids = twkArray.join(",");
		$("input[name='ids']").val(ids);
		/******************************************/
		var twknArray = new Array();
		if ("" != $("#names").val()) {
			wkNamesArray = $("#names").val().split(",");
		}
		for ( var i = 0; i < wkNamesArray.length; i++) {
			if (wkNamesArray[i] != $(e).val().split("+")[1]) {
				twknArray.push(wkNamesArray[i]);
			}
		}
		var names = twknArray.join(",");
		$("input[name='names']").val(names);
		/******************************************/
		var twkpArray = new Array();
		if ("" != $("#names").val()) {
			wkPNamesArray = $("#p_names").val().split(",");
		}
		for ( var i = 0; i < wkPNamesArray.length; i++) {
			if (wkPNamesArray[i] != $(e).val().split("+")[2]) {
				twkpArray.push(wkPNamesArray[i]);
			}
		}
		var pnames = twkpArray.join(",");
		$("input[name='p_names']").val(pnames);
		/******************************************/
		$("a", "#fdul").each(function(){
			var aid = $(this).attr("id").split("_")[1];
			if ($(e).val().split("+")[0] == aid) {
				$(this).parent().remove();
			}
		});
	}
	var ids = $("input[name='ids']").val();
	var names = $("input[name='names']").val();
	var p_names = $("input[name='p_names']").val();
}
/* function randomChoose(){
	$("input[name='ids']").val(ids);
	$("input[name='names']").val(names);
	var list = $("input[name='pks'][disabled!='disabled']");
	var array = new Array;
	for(var i=0;i<list.length;i++){
		array.push(i);
	}
	array.sort(function() {
	    return 0.5 - Math.random();
	});
	var count = $("input[name='random_num']").val();
	//console.log(count)
	for(var i=0;i<count;i++){
		//console.log(array[i])
		//console.log(list[array[i]])
		$(list[array[i]]).trigger("click");
		$(list[array[i]]).parent().addClass("cked");//改变样式
	}
	var list2 = $("li[class='qtip cked']>input");
	console.log(list2.length);
	var wkIdsArray = new Array();
	var wkNamesArray = new Array();
	var wkPNamesArray = new Array();
	for(var i=0;i<list2.length;i++){
		var e = list2[i];
		checkWk(e);
	}
		
} */
function returnVal(flag){
	if(flag==1){
		var list = new Array();
		var count=$("input[name='random_num']").val();
		if(count<=0){
			jBox.tip("随机人数必须大于0", 'info');
			return;
		}
		var enableChooseCount = ${enableChooseCount}
		if(count>enableChooseCount){
			jBox.tip("当前列表可选人数最多"+enableChooseCount+"人", 'info');
			return;
		} 
		<c:forEach var="cur" items="${poorInfoList}">
			<c:if test="${cur.map.enable_choose eq true}">
			 	var obj = new Object();
				obj.str = "${cur.id}+${cur.real_name}+${cur.map.p_name}";
				obj.comm_count=${cur.map.comm_count};
				list.push(obj);
			</c:if>
		</c:forEach>
		console.log(list);
		
		var idsArray = new Array();
		var namesArray = new Array();
		var pNamesArray = new Array();
		for(var i=0;i<count;i++){
			console.log(i);
			var obj = list[i];
			var array = (obj.str || "").split("+");
			idsArray.push(array[0]);
			namesArray.push(array[1]);
			pNamesArray.push(array[2]);
		}
		$("input[name='ids']").val(idsArray.join(","));
		$("input[name='names']").val(namesArray.join(","));
		$("input[name='p_names']").val(pNamesArray.join(","));
	}
	var api = frameElement.api, W = api.opener;
	/* W.document.getElementById("poor_ids").value = ","+$("input[name='ids']").val()+",";
	W.document.getElementById("poor_names").value = $("input[name='names']").val();
	W.document.getElementById("poor_pnames").value = $("input[name='p_names']").val();
	var id_array =  JSON.parse('['+$("input[name='ids']").val()+']');
	var name_array = JSON.parse('['+$("input[name='names']").val()+']');
	var p_name_array = JSON.parse('['+$("input[name='p_names']").val()+']'); */
	W.returnPoorInfo($("input[name='ids']").val(),$("input[name='names']").val(),$("input[name='p_names']").val());
	api.close();
}
//]]></script>
</body>
</html>

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
				background: #F8F8F8;
				font-family: "微软雅黑", "宋体";
			}
			
			fieldset {
				border: 1px solid #CCC/*A3C0E8*/
				;
				text-align: left;
				margin: 0 auto;
				margin: 5px;
				border-radius: 2px;
				background: #FFF;
			}
			
			fieldset a {
				height: 30px;
				line-height: 30px;
				background: #009900;
				color: #FFF;
				margin: 2px 5px;
				padding: 2px 8px;
				border-radius: 5px;
			}
			
			fieldset a:hover {
				background: #00B800;
				text-decoration: none;
				color: #FFF;
				/*color:#FFF0AC;*/
			}
			
			fieldset a:visited {
				color: #FFF;
				text-decoration: none;
			}
			
			legend {
				font-weight: bold;
			}
			
			.dataContent {
				width: 99%;
				overflow: hidden;
				/*border:1px solid #A3C0E8;*/
				margin: 5px auto;
				overflow: auto;
				/*height:320px;*/
			}
			
			.ul_selpd {
				padding-left: 30px;
			}
			
			.ul_selpd li {
				/*width:210px;*/
				width: 20%;
				line-height: 18px;
				overflow: hidden;
				float: left;
				padding: 5px;
				margin: 5px;
				text-align: center;
			}
			
			.ul_selpd li b {
				float: right;
				font-weight: normal;
				padding-left: 5px;
				font-size: 12px;
			}
			
			.dataContent input,
			label {
				cursor: pointer;
			}
			
			.cked {
				background: #FFF0AC;
				/*#F1F8FF;*/
				border-radius: 5px;
			}
			
			.cked label {
				color: #FF5809;
				text-decoration: underline;
				/*#F1F8FF;*/
			}
			
			.fdul li {
				float: left;
			}
		</style>
	</head>

	<body>
		<div style="background:#FFFFFF;">&nbsp;</div>
		<fieldset>
			<legend>已选&nbsp;${_cs_user}</legend>
			<div id="chZj" style="height:145px;overflow:auto;">
				<ul id="fdul" class="fdul">
					<c:forEach items="${commTczhAttributeList}" var="cur">
						<li>
							<a href="javascript:void(0);" comm_tczi_id="${cur.par_id}" id="a_${cur.par_id}">${cur.attr_name}</a>
						</li>
					</c:forEach>
				</ul>
			</div>
		</fieldset>
		<div align="center" style="height:405px;overflow:auto;">
			<div style="height:75px;">
				<html-el:form action="/BaseCsAjax.do">
					<html-el:hidden property="method" value="getCommInfoTcList" />
					<html-el:hidden property="comm_tczh_ids" styleId="comm_tczh_ids" />
					<html-el:hidden property="comm_tczh_names" styleId="comm_tczh_names" />
					<html-el:hidden property="par_comm_tczh_ids" styleId="par_comm_tczh_ids" />
					<html-el:hidden property="tg_tczh_ids" styleId="tg_tczh_ids" />
					<!-- 			<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="tableClass"> -->
					<!-- 				<tr> -->
					<!-- 					<td align="left" nowrap="nowrap"> -->
					<%-- 						产品名称：<html-el:text property="cls_name_like" styleClass="webinput" size="40" /> --%>
					<!-- 						&nbsp;<a class="butbase" id="btn_search" style="cursor:pointer;"><span class="icon-find">查询</span></a> -->
					<!-- 					</td> -->
					<!-- 				</tr> -->
					<!-- 			</table> -->
				</html-el:form>
			</div>
			<div style="height:300px;overflow:auto;">
				<form action="YHQInfo.do?method=listPdType">
					<div style="text-align:left;padding-left:30px;margin-top:10px;margin-bottom:5px;">
						<c:if test="${not empty entityList}">
							<input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkCtAll(this);" /><label for="chkAll">全选</label>
						</c:if>
					</div>
					<div>
						<ul class="ul_selpd">
							<c:forEach var="cur" items="${entityList}" varStatus="vs1">
								<li title="${cur.attr_name}">
									<input name="pks" type="checkbox" id="pks_${cur.comm_tczh_id}" data-comm_tczh_name="${cur.attr_name}" data-comm_tczh_id="${cur.comm_tczh_id }" value="${cur.comm_tczh_id}+${cur.attr_name}" onclick="checkWk(this)" />
									<label for="pks_${cur.comm_tczh_id}">
								<span style="font-size:14px;" id="attr_name_${cur.comm_tczh_id}">${fn:escapeXml(cur.attr_name)}</span><br/>
							</label>
								</li>
							</c:forEach>
						</ul>
					</div>
				</form>
			</div>
		</div>
		<div style="height:30px;width:100%;text-align:right;padding-top:10px;background:#EEEEEE;border-top:1px solid #CCCCCC;">
			<a class="butbase" href="#" onclick="returnVal();"><span class="icon-ok">确定</span></a>&nbsp;
			<a class="butbase" href="#" onclick="var api = frameElement.api, W = api.opener;api.close();"><span class="icon-remove">关闭</span></a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
		<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
		<script type="text/javascript" src="${ctx}/commons/scripts/jquery.cs.js"></script>
		<script type="text/javascript">
			//<![CDATA[
			$(document).ready(function() {

				var f = document.forms[0];
				$("#btn_search").click(function() {
					f.submit();
				});

				if("" != "${af.map.par_comm_tczh_ids}") {
// 					套餐已存在，进入时回显已有的套餐属性
					var comm_tczh_ids = "${af.map.par_comm_tczh_ids}";
					var custIdsArray = new Array();
					custIdsArray = comm_tczh_ids.split(",");
					var cks = document.getElementsByName("pks");
					for(var i = 0; i < cks.length; i++) {
						for(var j = 0; j < custIdsArray.length; j++) {
							if(cks[i].value.split("+")[0] == custIdsArray[j]) {
								cks[i].checked = true;
								$(cks[i]).parent().addClass("cked");
							}
						}
					}

					//判断是否全选
					if("" != "${af.map.comm_tczh_ids}") {
						var comm_tczh_ids1 = "${af.map.comm_tczh_ids}"
// 						console.log("comm_tczh_ids:" + comm_tczh_ids + ",comm_tczh_ids1:" + comm_tczh_ids1);
						if(comm_tczh_ids1 == comm_tczh_ids) {
							$("#chkAll").attr("checked", true);
						}
					}
				}
				
				//未保存团购商品，选择套餐后，再次打开选择套餐页面回显之前已选择的套餐
				if("" == "${commTczhAttributeList}"){
					if("" != "${af.map.par_comm_tczh_ids}"){
						var comm_tczh_ids = "${af.map.par_comm_tczh_ids}";
						var idArray = new Array();
						idArray = comm_tczh_ids.split(",");
						for(var i = 0; i < custIdsArray.length; i++) {
							var id = $("#pks_"+idArray[i]).attr("data-comm_tczh_id");
							var name = $("#pks_"+idArray[i]).attr("data-comm_tczh_name");
							$("#fdul").append('<li><a href="javascript:void(0);" id="a_' + id + '" comm_tczi_id="' + id + '">' + name + '</a></li>');
						}
					}
				}

				$(".Tab tr").mouseover(function() {
					$(this).addClass("over");
				}).mouseout(function() {
					$(this).removeClass("over");
				});

				$(".Tab tr:even").addClass("alteven");
				$(".Tab tr:odd").addClass("altodd");


			});

			//多选
			function checkCtAll(e) {
				var idsArray = new Array();
				var namesArray = new Array();
				var wkIdsArray = new Array();
				var wkNamesArray = new Array();

				var ids = [];
				var names = [];
				$("#fdul").html("");
				//隐藏域先设为空
				if($("#chkAll").attr("checked")) {
					$("#tg_tczh_ids").val();
					$("#comm_tczh_names").val();
				}

				// 	循环每个pks
				// 	若是 全选 则给已选择区域添加选择项
				// 	若是 全不选 则取消已选择区域的选择项
				$('input[name="pks"]').each(function() {
					var tczh_id = $(this).attr("data-comm_tczh_id");
					var tczh_name = $(this).attr("data-comm_tczh_name");
					//判断是否选中
					if($("#chkAll").attr("checked")) {
						if(!$(this).attr("disabled")) {
							$(this).attr("checked", true);
							$("#fdul").append('<li><a href="javascript:void(0);" id="a_' + tczh_id + '" comm_tczi_id="' + tczh_id + '">' + tczh_name + '</a></li>');
							$(this).parent().addClass("cked");
						}
					} else {
						//当前pks取消选中
						$(this).attr("checked", false);
						//套餐组合删除
						$("#a_" + tczh_id).parent().remove();
						
						$(this).parent().removeClass("cked");
					}

				});
				// 	循环每个已选择的pks，给tczh_id,tczh_name数组填充
				$('input[name="pks"]:checked').each(function() {
					var tczh_id = $(this).attr("data-comm_tczh_id");
					var tczh_name = $(this).attr("data-comm_tczh_name");
					ids.push(tczh_id);
					names.push(tczh_name);
				});
				//给隐藏域塞值
				$("#comm_tczh_names").val(names);
				$("#comm_tczh_ids").val(ids);

			}

			//单选
			function checkWk(e) {
				var ids = new Array();
				var names = new Array();
				
				if(e.checked) { //选中
					$(e).parent().addClass("cked"); //改变样式
					
					$("#fdul").html("");
					$('input[name="pks"]:checked').each(function() {
						var tczh_id = $(this).attr("data-comm_tczh_id");
						var tczh_name = $(this).attr("data-comm_tczh_name");
						ids.push(tczh_id);
						names.push(tczh_name);
						$("#fdul").append('<li><a href="javascript:void(0);" id="a_' + tczh_id + '">' + tczh_name + '</a></li>');
					});
					
				} else { //取消选中
					$(e).parent().removeClass("cked");

					$('input[name="pks"]:checked').each(function() {
						var tczh_id = $(this).attr("data-comm_tczh_id");
						var tczh_name = $(this).attr("data-comm_tczh_name");
						ids.push(tczh_id);
						names.push(tczh_name);
					});

					$("a", "#fdul").each(function() {
						var aid = $(this).attr("id").split("_")[1];
						if($(e).val().split("+")[0] == aid) {
							$(this).parent().remove();
						}
					});
				}
				
				
				var ids = ids.join(",");
				$("#comm_tczh_ids").val(ids);
				
				var names = names.join(",");
				$("#comm_tczh_names").val(names);
				
				if("" != "${af.map.comm_tczh_ids}") {
					var comm_tczh_ids = "${af.map.comm_tczh_ids}"
					if(comm_tczh_ids == ids) {
// 						console.log("comm_tczh_ids:" + comm_tczh_ids + ",ids:" + ids);
						$("#chkAll").attr("checked", true);
					}else{
						$("#chkAll").attr("checked", false);
					}
				}
				
			}

			function returnVal() {
				var api = frameElement.api,
					W = api.opener;
				W.document.getElementById("comm_tczh_ids").value = $("#comm_tczh_ids").val();
// 				W.document.getElementById("tg_tczh_ids").value = $("#tg_tczh_ids").val();
				W.document.getElementById("comm_tczh_names").value = $("input[name='comm_tczh_names']").val();
				api.close();
			}
			//]]>
		</script>
	</body>

</html>
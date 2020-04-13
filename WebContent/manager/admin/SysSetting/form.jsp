<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css"
	rel="stylesheet" type="text/css" />
</head>
<body>
	<script type="text/javascript"
		src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
	<script type="text/javascript"
		src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
	<div class="divContent">
		<div class="subtitle">
			<h3>${naviString}</h3>
		</div>
		<%@ include file="/commons/pages/messages.jsp"%>
		<html-el:form action="/admin/SysSetting">
			<html-el:hidden property="method" value="save" />
			<html-el:hidden property="id" />
			<html-el:hidden property="mod_id" />
			<div class="all">
				<ul class="nav nav-tabs" id="nav_ul_content">
					<c:forEach var="cur" items="${ShareSettings}" varStatus="vs">
						<c:set var="liClass" value="" />
						<c:if test="${vs.count eq 1}">
							<c:set var="liClass" value="active" />
						</c:if>
						<li data-type="${cur.index}" class="${liClass}"><a><span>${cur.name}</span></a></li>

					</c:forEach>
				</ul>
			</div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="tableClass">
				<tbody style="display: table-row-group;" id="system">
					<tr>
						<th colspan="2">系统设置</th>
					</tr>
					<tr>
						<td width="15%" class="title_item">是否启用验证码：</td>
						<td align="left"><html-el:select property="isEnabledCode"
								styleId="isEnabledCode" style="width:128px;"
								styleClass="webinput">
								<html-el:option value="1">启用验证码</html-el:option>
								<html-el:option value="0">不启用验证码</html-el:option>
							</html-el:select> <br /> 启用验证码会使得部分操作变得繁琐，但是可以避免恶意暴力破解密码，请选择是否启用验证码</td>
					</tr>
					<tr>
						<td class="title_item"><span style="color: red;">*</span>自动确认收货天数：</td>
						<td align="left"><html-el:text
								property="autoConfirmReceiptDays" maxlength="10"
								styleId="autoConfirmReceiptDays" style="width:128px;"
								styleClass="webinput" /> 天</td>
					</tr>
					<tr>
						<td class="title_item"><span style="color: red;">*</span>发货后收货延长天数：</td>
						<td align="left"><html-el:text property="fahuoShouhuoYanDays"
								maxlength="10" styleId="fahuoShouhuoYanDays"
								style="width:128px;" styleClass="webinput" /> 天</td>
					</tr>
					<tr>
						<td class="title_item"><span style="color: red;">*</span>系统管理员手机号：</td>
						<td align="left"><html-el:text property="adminMobile"
								maxlength="11" styleId="adminMobile" style="width:100px;"
								styleClass="webinput" /></td>
					</tr>
<!-- 					<tr> -->
<!-- 						<td class="title_item"><span style="color: red;">*</span>财务手机号：</td> -->
<%-- 						<td align="left"><html-el:text property="financialMobile" --%>
<%-- 								maxlength="11" styleId="financialMobile" style="width:100px;" --%>
<%-- 								styleClass="webinput" /></td> --%>
<!-- 					</tr> -->
				</tbody>

<!-- 				<tr> -->
<!-- 					<th colspan="2">企业门户信息设置</th> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td width="15%" class="title_item">联系电话：</td> -->
<%-- 					<td align="left"><html-el:text property="index_entp_tel" --%>
<%-- 							maxlength="50" styleId="index_entp_tel" style="width:57%;" --%>
<%-- 							styleClass="webinput" /></td> --%>
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td width="15%" class="title_item">企业地址：</td> -->
<%-- 					<td align="left"><html-el:text property="index_entp_addr" --%>
<%-- 							maxlength="50" styleId="index_entp_addr" style="width:57%;" --%>
<%-- 							styleClass="webinput" /></td> --%>
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td width="15%" class="title_item">企业邮箱：</td> -->
<%-- 					<td align="left"><html-el:text property="index_entp_mail" --%>
<%-- 							maxlength="50" styleId="index_entp_mail" style="width:57%;" --%>
<%-- 							styleClass="webinput" /></td> --%>
<!-- 				</tr> -->
				<tr>
					<td colspan="2" align="center"><html-el:button
							property="bgButton" value="保 存" styleId="bgButton"
							styleClass="bgButton" /> &nbsp; <html-el:reset value="重 填"
							onclick="this.form.reset();" styleClass="bgButton" /> &nbsp; <html-el:button
							property="back" value="返 回" onclick="history.back();"
							styleClass="bgButton" /></td>
				</tr>




			</table>
		</html-el:form>
	</div>
	<script type="text/javascript"
		src="${ctx}/commons/scripts/validator.js"></script>
	<script type="text/javascript"
		src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
	<script type="text/javascript">
		//<![CDATA[
		$(document).ready(
				function() {
					var f = document.forms[0];

					$("#autoConfirmReceiptDays").attr("datatype", "Integer")
							.attr("msg", "请填写正确的天数！");
					$("#fahuoShouhuoYanDays").attr("datatype", "Integer").attr(
							"msg", "请填写正确的天数！");
					$("#everyDayMaxSend").attr("datatype", "Integer").attr(
							"msg", "请填写正确的派送量！");
					$("#labelManager").attr("datatype", "Number").attr("msg",
							"请填写正确的店铺标签数量！");
					$("#upLevelNeedPayMoney").attr("datatype", "Currency")
							.attr("msg", "请填写正确的金额！");
					$("#adminMobile").attr("datatype", "Mobile").attr("msg",
							"请正确填写系统管理员手机号！");
					$("#bgButton").click(function() {
						if (Validator.Validate(f, 3)) {
							f.submit();
						}
					});
					$("#nav_ul_content a").click(
							function() {
								var index = $(this).parent().attr("data-type");
								if (index == 10) {
									$("#system").show();
									$("#share").hide();
									$("#tuiJian").hide();
								} else if (index == 20) {
									$("#system").hide();
									$("#share").show();
									$("#tuiJian").hide();
								} else {
									$("#system").hide();
									$("#share").hide();
									$("#tuiJian").show();
								}
								$(this).parent().addClass("active").siblings()
										.removeClass("active");
							});

					/*addAuthorisedIpEvent("ip");

					$("#isAuthorisedIp").change(function(){
						var isAuthorisedIp = $(this).val();
						if ("1" == isAuthorisedIp) {
							$("#tr_ip").show();
						} else {
							$("#tr_ip").hide();
						}
					});

					if ("" != "${af.map.isAuthorisedIp}") {
						$("#isAuthorisedIp").trigger("change");
					}
					 */
				});
		function getCuns() {
			var url = "${ctx}/BaseCsAjax.do?method=getCuns";
			$.dialog({
				title : "推荐村",
				width : 770,
				height : 550,
				lock : true,
				content : "url:" + url
			});
		}
		function getXians() {
			var url = "${ctx}/BaseCsAjax.do?method=getXians";
			$.dialog({
				title : "推荐县",
				width : 770,
				height : 550,
				lock : true,
				content : "url:" + url
			});
		}

		function addAuthorisedIpEvent(item) {
			$("#add_" + item + "_td").click(
					function() {
						$("#hidden_" + item + "_tr").clone().appendTo(
								$("#" + item + "_tbody")).show();
						var lastTR = $("tr:last", "#" + item + "_tbody");
						$("#ip_s", lastTR).attr("datatype", "IP").attr("msg",
								"请填写正确IP！");

						$("td:last", lastTR).click(function() {
							$(this).parent().remove();
						}).css("cursor", "pointer");
					}).css("cursor", "pointer");

			//编辑页面增加删除事件
			$("tr", "#" + item + "_tbody").each(
					function() {
						$("#ip_s", $(this)).attr("datatype", "IP").attr("msg",
								"请填写正确IP！");
						$("td:last", $(this)).click(function() {
							$(this).parent().remove();
						}).css("cursor", "pointer");
					});
		}
		//]]>
	</script>
	<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>

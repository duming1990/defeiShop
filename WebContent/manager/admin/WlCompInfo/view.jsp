<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
	<div class="divContent">
		<div class="subtitle">
			<h3>${naviString}</h3>
		</div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="tableClass">
			<tr>
				<th colspan="2">快递公司基本信息</th>
			</tr>
			<tr>
				<td width="15%" nowrap="nowrap" class="title_item">公司类型：</td>
				<td colspan="2" width="88%">快递公司</td>
			</tr>
			<tr>
				<td width="12%" nowrap="nowrap" class="title_item">快递公司名称：</td>
				<td colspan="2" width="88%">${fn:escapeXml(af.map.wl_comp_name)}</td>
			</tr>
			<c:if test="${af.map.comp_type eq 0}">
				<tr>
					<td width="12%" nowrap="nowrap" class="title_item">快递公司代码：</td>
					<td colspan="2" width="88%">${af.map.wl_code}</td>
				</tr>
			</c:if>
			<tr>
				<td width="12%" nowrap="nowrap" class="title_item">详细地址：</td>
				<td colspan="2" width="88%">${fn:escapeXml(af.map.addr)}</td>
			</tr>
			<tr>
				<td width="12%" nowrap="nowrap" class="title_item">联系人：</td>
				<td colspan="2" width="88%">${fn:escapeXml(af.map.link_man)}</td>
			</tr>
			<tr>
				<td width="12%" nowrap="nowrap" class="title_item">联系电话：</td>
				<td colspan="2" width="88%">${fn:escapeXml(af.map.tel)}</td>
			</tr>
			<tr>
				<td width="12%" nowrap="nowrap" class="title_item">传真：</td>
				<td colspan="2" width="88%">${fn:escapeXml(af.map.fax)}</td>
			</tr>
			<tr>
				<td class="title_item">所在地区：</td>
				<td>${fn:escapeXml(af.map.map.full_name)}</td>
			</tr>
			<tr>
				<td class="title_item">是否锁定：</td>
				<td><c:choose>
						<c:when test="${af.map.is_lock eq 0}">未锁定</c:when>
						<c:when test="${af.map.is_lock eq 1}">已锁定</c:when>
					</c:choose></td>
			</tr>
			<tr>
				<td class="title_item">是否是合作快递公司：</td>
				<td><c:choose>
						<c:when test="${af.map.is_collaborate eq 1}">是合作公司</c:when>
						<c:when test="${af.map.is_collaborate eq 0}">不是合作公司</c:when>
					</c:choose></td>
			</tr>
			<tr>
				<td class="title_item">排序值：</td>
				<td>${af.map.order_value}</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button" value="返 回"
					class="bgButton" onclick="history.back();" /></td>
			</tr>
		</table>
	</div>
	
	<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
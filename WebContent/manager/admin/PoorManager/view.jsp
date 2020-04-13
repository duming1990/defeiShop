<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
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
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="4">基本信息</th>
      </tr>
       <tr>
         <td width="14%"  class="title_item">申请区域：</td>
            <td colspan="3">
             <c:if test="${not empty full_name}">${fn:escapeXml(full_name)}</c:if>
            </td>
       </tr>
       <tr>
         <td width="14%"  class="title_item">昵称：</td>
         <td>${fn:escapeXml(af.map.pet_name)}</td>
         <td width="14%"  class="title_item">民族：</td>
         <td>${fn:escapeXml(af.map.nation)}</td>
       </tr>
       <tr>
         <td width="14%"  class="title_item">入乡时间：</td>
         <td><fmt:formatDate value="${af.map.residence_start_time}" pattern="yyyy-MM-dd" /></td>
          <td width="14%"  class="title_item">离乡时间：</td>
         <td><fmt:formatDate value="${af.map.residence_end_time}" pattern="yyyy-MM-dd" /></td>
       </tr>
       <tr>
         <td width="14%"  class="title_item">学历：</td>
         <td>${fn:escapeXml(af.map.education)}</td>
         <td width="14%"  class="title_item">毕业学校：</td>
         <td>${fn:escapeXml(af.map.graduate_school)}&nbsp;
         </td>
       </tr>
       <tr>
         <td width="14%"  class="title_item">个人简介：</td>
         <td>${fn:escapeXml(af.map.person_introduce)}</td>
       </tr>
      <tr>
        <th colspan="4">个人信息</th>
      </tr>
      <tr>
        <td class="title_item">真实姓名：</td>
        <td>${fn:escapeXml(af.map.real_name)}</td>
        <td class="title_item">头像：</td>
         <td><c:if test="${not empty af.map.head_logo}"> <a href="${ctx}/${af.map.head_logo}" title="头像" class="cb"> 
          <img height="100" src="${ctx}/${af.map.head_logo}@s400x400" /></a> </c:if></td>
      </tr>
       <tr>
        <td class="title_item">电话：</td>
        <td>${fn:escapeXml(af.map.mobile)}</td>
        <td class="title_item">性别：</td>
        <td><c:out value="${af.map.sex eq 0 ? '男' : '女'}"/></td>
      </tr>
       <tr>
        <td class="title_item">出生日期：</td>
        <td><fmt:formatDate value="${af.map.birthday}" pattern="yyyy-MM-dd"/></td>
        <td class="title_item">电子邮箱：</td>
        <td><c:out value="${af.map.email}"/></td>
      </tr>
      <tr>
         <td class="title_item">身份证号码：</td>
        <td colspan="3">${fn:escapeXml(af.map.id_card)}</td>
      </tr>
       <tr>
          <td class="title_item" >身份证正面：</td>
          <td><c:if test="${not empty af.map.img_id_card_zm}"> <a href="${ctx}/${af.map.img_id_card_zm}" title="身份证正面" class="cb"> 
          <img height="100" src="${ctx}/${af.map.img_id_card_zm}@s400x400" /></a> </c:if></td>
          <td class="title_item" >身份证背面：</td>
          <td><c:if test="${not empty af.map.img_id_card_fm}"> <a href="${ctx}/${af.map.img_id_card_fm}" title="身份证背面" class="cb"> 
          <img height="100" src="${ctx}/${af.map.img_id_card_fm}@s400x400" /></a> </c:if></td>
        </tr>
    
      <tr>
        <th colspan="4">审核信息</th>
      </tr>
      <tr>
        <td class="title_item">审核状态：</td>
        <td colspan="3">
	        <c:if test="${af.map.audit_state eq 0}">待审核</c:if>
	        <c:if test="${af.map.audit_state eq -1}">审核不通过</c:if>
	        <c:if test="${af.map.audit_state eq 1}">审核通过</c:if>
		</td>
      </tr>
      <tr>
        <td class="title_item">审核意见:</td>
        <td colspan="3">${fn:escapeXml(af.map.audit_desc)}</td>
      </tr>
      <tr>
      	<td colspan="4" align="center"><input type="button" value="返 回" class="bgButton" onclick="history.back();" /></td>
      </tr>
    </table>
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jquery-ui/external/jquery.bgiframe.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jquery-ui/ui/minified/jquery-ui.custom.min.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[

//]]></script>
</body>
</html>
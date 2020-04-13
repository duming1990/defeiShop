<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/m/scripts/lightGallery/css/lightgallery.css"/>
</head>
<body>
<div style="width: 99%" class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/UserAudit">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td><div>真实姓名：
            <html-el:text property="real_name_like" maxlength="40" style="width:150px;" styleClass="webinput" />
            &nbsp;用户名：
            <html-el:text property="add_user_name_like" maxlength="40" style="width:100px;" styleClass="webinput" />
            &nbsp;身份证号：
            <html-el:text property="id_card" maxlength="40" style="width:200px;" styleClass="webinput" />
            &nbsp;审核状态：
            <html-el:select property="audit_state" styleClass="webinput" >
              <html-el:option value="">全部</html-el:option>
              <html-el:option value="-1">审核不通过</html-el:option>
              <html-el:option value="0">待审核</html-el:option>
              <html-el:option value="1">审核通过</html-el:option>
            </html-el:select>
          </div>
          <div style="margin-top:5px;">添加时间：
            从
            <html-el:text property="add_date_st" styleClass="webinput"  styleId="add_date_st" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
            至
            <html-el:text property="add_date_en" styleClass="webinput"  styleId="add_date_en" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
            &nbsp;审核时间：
            从
            <html-el:text property="audit_date_st" styleClass="webinput"  styleId="audit_date_st" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
            至
            <html-el:text property="audit_date_en" styleClass="webinput"  styleId="audit_date_en" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
            &nbsp;
            <html-el:submit value="查 询" styleClass="bgButton"  />
          </div></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="UserAudit.do?method=delete">
  <div style="text-align: left;padding:5px;">
     <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='UserAudit.do?method=add&mod_id=${af.map.mod_id}';" />
  </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%"> <input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" />
        </th>
        <th width="8%">用户名</th>
        <th width="8%">真实姓名</th>
        <th width="6%">身份证号码</th>
        <th width="10%">身份证正面照片</th>
        <th width="10%">身份证反面照片</th>
        <th width="7%">添加时间</th>
        <th width="7%">审核时间</th>
        <th width="8%">是否审核</th>
        <th width="14%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center"><c:if test="${cur.audit_state eq 1}" var="isLock">
              <input name="pks" type="checkbox" id="pks" value="${cur.id}" disabled="disabled"/>
            </c:if>
            <c:if test="${cur.audit_state ne 1}">
              <input name="pks" type="checkbox" id="pks" value="${cur.id}" />
            </c:if></td>
          <td align="center">${fn:escapeXml(cur.add_user_name)}</td>
          <td align="center">${fn:escapeXml(cur.opt_note)}</td>
          <td align="center">${fn:escapeXml(cur.map.id_card)}</td>
          <td align="center" class="lightgallery"><c:if test="${not empty cur.map.img_id_card_zm}" var="noimgzm">
              <c:set var="img" value="${ctx}/${cur.map.img_id_card_zm}@s400x400" />
              <a href="${ctx}/${cur.map.img_id_card_zm}@compress" title="身份证正面"> <img src="${ctx}/${cur.map.img_id_card_zm}@s400x400" height="100" /> </a> </c:if>
            <c:if test="${not noimgzm}">未上传</c:if>
          </td>
          <td align="center" class="lightgallery"><c:if test="${not empty cur.map.img_id_card_fm}" var="noimgfm">
              <c:set var="img" value="${ctx}/${cur.map.img_id_card_fm}@s400x400" />
              <a href="${ctx}/${cur.map.img_id_card_fm}@compress" title="身份证反面" class="viewImgMain"> <img src="${ctx}/${cur.map.img_id_card_fm}@s400x400" height="100" /> </a> </c:if>
            <c:if test="${not noimgfm}">未上传</c:if>
          </td>
          <fmt:formatDate var="add_date" value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm:ss" />
          <td align="center" title="添加时间：${add_date}"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
          <fmt:formatDate var="audit_date" value="${cur.audit_date}" pattern="yyyy-MM-dd HH:mm:ss" />
          <td align="center" title="最后审核时间：${audit_date}"><fmt:formatDate value="${cur.audit_date}" pattern="yyyy-MM-dd" /></td>
          <td align="center"><c:choose>
              <c:when test="${cur.audit_state eq -1}"><span style=" color:#F00;">审核不通过</span></c:when>
              <c:when test="${cur.audit_state eq 0}"><span>待审核</span></c:when>
              <c:when test="${cur.audit_state eq 1}"><span style=" color:#060;">审核通过</span></c:when>
            </c:choose>
          </td>
          <td align="center" nowrap="nowrap"><a class="butbase"><span onclick="doNeedMethod(null, 'UserAudit.do', 'audit','id=${cur.id}&mod_id=${af.map.mod_id}&'+$('#bottomPageForm').serialize())" class="icon-ok">审核</span></a>
            <c:if test="${cur.audit_state ne 1}" var="flag_edit"> 
            <a class="butbase"><span onclick="confirmUpdate(null, 'UserAudit.do', 'id=${cur.id}&mod_id=${af.map.mod_id}&' + $('#bottomPageForm').serialize())" class="icon-edit">修改</span></a>
            <a class="butbase"><span onclick="confirmDelete(null, 'UserAudit.do', 'id=${cur.id}&mod_id=${af.map.mod_id}&' + $('#bottomPageForm').serialize())" class="icon-remove">删除</span></a>
            </c:if>
            <c:if test="${!flag_edit}"> <a class="butbase but-disabled" title="已审核，不能再修改"><span class="icon-edit">修改</span></a> 
            <a class="butbase but-disabled" title="已审核，不能删除"><span class="icon-remove">删除</span></a>
            </c:if>
          </td>
        </tr>
        <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
      </c:forEach>
      <c:forEach begin="${i}" end="${af.map.pager.pageSize - 1}">
        <tr align="center">
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="UserAudit.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("real_name_like", "${fn:escapeXml(af.map.real_name_like)}");
            pager.addHiddenInputs("add_user_name_like", "${fn:escapeXml(af.map.add_user_name_like)}");
			pager.addHiddenInputs("add_date_st", "${af.map.add_date_st}");
			pager.addHiddenInputs("add_date_en", "${af.map.add_date_en}");
            pager.addHiddenInputs("audit_date_st", "${af.map.audit_date_st}");
			pager.addHiddenInputs("audit_date_en", "${af.map.audit_date_en}");
			pager.addHiddenInputs("audit_state", "${af.map.audit_state}");
            document.write(pager.toString());
            </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/m/scripts/lightGallery/js/lightgallery-all.min.js?20180530"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$('.lightgallery').lightGallery({download:false});
});
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>

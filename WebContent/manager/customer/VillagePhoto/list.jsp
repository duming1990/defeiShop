<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/VillagePhoto">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="6%" nowrap="nowrap">相册描述：
                <html-el:text property="photo_desc_like" styleId="photo_desc_like" style="width:140px;" styleClass="webinput"/>
                 &nbsp;添加时间 从：
                <html-el:text property="st_add_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                至：
                <html-el:text property="en_add_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                &nbsp;&nbsp;
                <button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="VillagePhoto.do?method=delete">
    <div style="padding:5px;">
    <logic-el:match name="popedom" value="+1+">
      <button class="bgButtonFontAwesome" type="button" onclick="location.href='VillagePhoto.do?method=add&par_id=${af.map.par_id}&mod_id=${af.map.mod_id}';" ><i class="fa fa-plus-square"></i>添加</button>
    </logic-el:match>
    <logic-el:match name="popedom" value="+4+">
    <button class="bgButtonFontAwesome" type="button" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);"><i class="fa fa-minus-square"></i>删除所选</button>
    </logic-el:match>
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th width="5%"><input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /></th>
        <th width="3%">序号</th>
        <th width="20%">相片</th>
        <th width="30%">文字描述</th>
        <th width="10%">添加时间</th>
        <th width="10%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
         <td align="center">
            <c:if test="${cur.is_del eq 1}">
              <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}" disabled="disabled" />
            </c:if>
            <c:if test="${cur.is_del eq 0}">
                <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}"/>
            </c:if></td>
          <td align="center">${vs.count}</td>
          <td align="center">
            <c:if test="${not empty cur.save_path}">
          	<c:set var="img" value="${ctx}/${cur.save_path}@s400x400" />
        	</c:if>
        	<c:if test="${fn:contains(cur.save_path, 'http://')}">
		 	 <c:set var="img" value="${cur.save_path}"/>
		 	</c:if>
		 	<a href="${img}" target="blank"><img src="${img}" height="100"/></a>
		  </td>
          <td align="center">${fn:escapeXml(cur.file_desc)}</td>
          <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd" /></td>
          <td align="center" nowrap="nowrap">
            <div style="padding-top: 2px;">
              <logic-el:match name="popedom" value="+2+">
			    <a class="butbase" onclick="confirmUpdate(null, 'VillagePhoto.do', 'id=${cur.id}&mod_id=${af.map.mod_id}&' + $('#bottomPageForm').serialize())"><span class="icon-edit">修改</span></a>
              </logic-el:match>
              <logic-el:notMatch name="popedom" value="+2+"><a class="butbase but-disabled"><span class="icon-edit">修改</span></a> </logic-el:notMatch>
              <logic-el:match name="popedom" value="+4+">
                <a class="butbase" onclick="confirmDelete(null, 'VillagePhoto.do', 'id=${cur.id}&mod_id=${af.map.mod_id}&' + $('#bottomPageForm').serialize())"><span class="icon-remove">删除</span></a>
              </logic-el:match>
              <logic-el:notMatch name="popedom" value="+4+"><a class="butbase but-disabled"><span class="icon-remove">删除</span></a> </logic-el:notMatch>
            </div>
          </td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="VillagePhoto.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
        <tr>
          <td height="10" ><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("par_id", "${af.map.par_id}");
            pager.addHiddenInputs("village_name_like", "${fn:escapeXml(af.map.village_name_like)}");
            pager.addHiddenInputs("audit_state", "${fn:escapeXml(af.map.audit_state)}");
            document.write(pager.toString());
        </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
</body>
</html>

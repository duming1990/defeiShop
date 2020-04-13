<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div align="center" class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/CommentInfo">
    <html-el:hidden property="method" value="viewCommentSon" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="par_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td width="5%" nowrap="nowrap">
          	回复人：<html-el:text property="user_name_like" styleId="user_name_like" />
          	被回复人：<html-el:text property="to_user_name_like" styleId="to_user_name_like" />
          	&nbsp;时间 从：
                <html-el:text property="st_add_date" styleId="st_add_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker()" />
                至：
                <html-el:text property="en_add_date" styleId="en_add_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker()" />
<%--           <html-el:select property="comm_type" styleId="comm_type" > --%>
<%--               <html-el:option value="">请选择...</html-el:option> --%>
<%--               <c:forEach var="commentType" items="${commentTypeList}"> --%>
<%--                 <html-el:option value="${commentType.index}">${commentType.name}</html-el:option> --%>
<%--               </c:forEach> --%>
<%--           </html-el:select> --%>
<!--           &nbsp;评论状态： -->
<%--           <html-el:select property="comm_state" styleId="comm_state" > --%>
<%--             <html-el:option value="">全部</html-el:option> --%>
<%--             <html-el:option value="-1">审核不通过</html-el:option> --%>
<%--             <html-el:option value="0">待审核</html-el:option> --%>
<%--             <html-el:option value="1">审核通过</html-el:option> --%>
<%--           </html-el:select> --%>
          &nbsp;
          <html-el:submit value="查 询" styleClass="bgButton" styleId="btn_submit"/></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="CommentInfo.do?method=deleteCommentSon">
    <div style="padding-bottom:5px;text-align:left;">
      <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" />
      <input type="hidden" name="method" id="method" value="delete" />
      <input type="hidden" name="mod_id" id="mod_id" value="${af.map.mod_id}" />
      <input type="hidden" name="par_id" value="${af.map.par_id}" />
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
    	<tr>
    		<td colspan="5" align="left" >商品评论：<span style="color: #777;">${fn:escapeXml(fnx:abbreviate(commentInfo.comm_experience, 50, '...'))}</span></td>
    	</tr>
      <tr>
        <th width="5%"><input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /></th>
        <th width="20%">回复信息</th>
        <th width="10%">回复人</th>
        <th width="10%">回复时间</th>
        <th width="10%" >操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          <td>
          <c:if test="${cur.is_del ne 1}">
              <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}" />
            </c:if>
            <c:if test="${cur.is_del eq 1}">
              <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}" disabled="disabled"/>
            </c:if></td>
          <td align="center">${fn:escapeXml(fnx:abbreviate(cur.content, 25, '...'))}</td>
          <td align="center">
	          <c:url var="url" value="CommentInfo.do?method=unameInfo&id=${cur.add_user_id}" />
	          <a title="回复人信息" class="cases" href="${url}">${cur.add_user_name}:</a>@
	          <c:url var="url" value="CommentInfo.do?method=unameInfo&id=${cur.to_user_id}" />
	          <a title="被回复人信息" class="cases" href="${url}"> ${cur.to_user_name}</a>
          </td>
          <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm" /></td>
          <td nowrap="nowrap">
<%--             <c:if test="${cur.comm_state eq 1}">  --%>
<!--             <a class="butbase but-disabled"  onclick="javascript:void(0);"  title="已审核，不能删除"><span class="icon-remove">删除</span></a>  -->
<%--              </c:if> --%>
<%--             <c:if test="${cur.comm_state ne 1}"> --%>
             <a class="butbase" onclick="confirmDelete(null, 'CommentInfo.do', 'id=${cur.id}&mod_id=${af.map.mod_id}&link_id=${af.map.link_id}&' + $('#bottomPageForm').serialize())"  title="已审核，不能删除"><span class="icon-remove">删除</span></a>
<%--             </c:if> --%>
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
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="CommentInfo.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "viewCommentSon");
					pager.addHiddenInputs("user_name_like", "${fn:escapeXml(af.map.user_name_like)}");
					pager.addHiddenInputs("to_user_name_like", "${fn:escapeXml(af.map.to_user_name_like)}");
					pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
					pager.addHiddenInputs("par_id", "${af.map.par_id}");
					document.write(pager.toString());
	            	</script></td>
        </tr>
      </table>
    </form>
  </div>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/cs.js"></script>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript">//<![CDATA[
$("a.cases").colorbox({width:"52%", height:"60%", iframe:true}); 
$(document).ready(function(){

	var f = document.forms[0];

	$("#btn_submit").click(function(){
		this.form.submit();
	});
});
function confirmDelete(msg, page, queryString) {
	msg  = msg  || "确定要删除这条回复信息吗？";
	page = page || "?";
	page = page.indexOf("?") != -1 ? page : (page + "?");
	if(confirm(msg)){
		location.href = page + "method=deleteCommentSon&" + queryString;
	}
}
//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/DynamicManager">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="6%" nowrap="nowrap">发布人名称：
                <html-el:text property="add_user_name_like" styleId="add_user_name_like" style="width:140px;" styleClass="webinput"/>
                &nbsp;审核状态：
                <html-el:select property="audit_state" styleClass="webinput" >
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="-1">审核不通过</html-el:option>
                  <html-el:option value="0">待审核</html-el:option>
                  <html-el:option value="1">审核通过</html-el:option>
                </html-el:select>
                &nbsp;&nbsp;
                <button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button>
              </td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="DynamicManager.do?method=delete">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th width="5%">序号</th>
        <th width="8%">发布人姓名</th>
        <th width="15%">发布内容</th>
        <th width="15%">相关图片</th>
        <th width="5%">评论数</th>
        <th width="5%">点赞数</th>
        <th width="8%">发布时间</th>
        <th width="8%">审核状态</th>
        <th width="8%">审核人</th>
        <th width="10%">审核说明</th>
        <th width="10%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center">${vs.count}</td>
          <td align="center">${fn:escapeXml(cur.add_user_name)}</td>
          <td align="center"><span title="${fn:escapeXml(cur.content)}">${fnx:abbreviate(cur.content, 60, '...')}</span></td>
          <td align="center">
	          <c:forEach var="item" items="${cur.map.imgList}" varStatus="vss">
			        <a target="_blank" href="${ctx}/${item.file_path}" title="相关图片" class="cb"> 
			          <img height="50" src="${ctx}/${item.file_path}@s400x400" />
			        </a>
		        </c:forEach>
          </td>
          <td align="center">${cur.comment_count}</td>
          <td align="center">${cur.like_count}</td>
          <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd" /></td>
          <td align="center"><c:choose>
              <c:when test="${cur.audit_state eq -1}"><span style=" color:#F00;">审核不通过</span></c:when>
              <c:when test="${cur.audit_state eq 0}"><span>待审核</span></c:when>
              <c:when test="${cur.audit_state eq 1}"><span style=" color:#060;">审核通过</span></c:when>
            </c:choose>
          </td>
          <td align="center">
          	<c:if test="${not empty cur.audit_user_name}">${fn:escapeXml(cur.audit_user_name)}</c:if>
          	<c:if test="${empty cur.audit_user_name}">---</c:if>
          </td>
          <td align="center">
          	<c:if test="${not empty cur.audit_desc}">${fn:escapeXml(cur.audit_desc)}</c:if>
          	<c:if test="${empty cur.audit_desc}">---</c:if>
          </td>
          <td align="center" nowrap="nowrap">
            <logic-el:match name="popedom" value="+8+">
               <a class="butbase" onclick="doNeedMethod(null, 'DynamicManager.do', 'audit','id=${cur.id}&mod_id=${af.map.mod_id}&'+$('#bottomPageForm').serialize())"><span class="icon-ok">审核</span></a> 
               <a class="butbase" onclick="commentList('${cur.id}');"><span class="icon-search">查看评论</span></a>
            </logic-el:match>
          </td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="DynamicManager.do">
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
<script type="text/javascript">
function refreshPage(){
	window.location.reload();
}
function commentList(id){
	var url = "${ctx}/manager/customer/DynamicManager.do?method=commentList&dynamic_id=" + id;
	$.dialog({
		title:  "评论信息",
		width:  900,
		height: 520,
		padding: 0,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url)
	});
}
</script>
</body>
</html>

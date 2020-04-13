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
  <html-el:form action="/customer/DynamicReportManager">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="6%" nowrap="nowrap">
                &nbsp;是否已读：
                <html-el:select property="is_read" styleClass="webinput" >
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="0">未读</html-el:option>
                  <html-el:option value="1">已读</html-el:option>
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
  <form id="listForm" name="listForm" method="post" action="DynamicReportManager.do?method=delete">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th width="8%">发布人</th>
        <th width="8%">类型</th>
        <th width="15%">发布内容</th>
        <th width="8%">动态所属村</th>
        <th width="8%">发布时间</th>
        <th width="8%">举报人</th>
        <th width="10%">举报缘由</th>
        <th width="10%">是否已读</th>
        <th width="10%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center">${fn:escapeXml(cur.map.vDynamic.add_user_name)}</td>
          <td align="center">
          	<c:if test="${cur.map.vDynamic.type eq 1}">举报发布的动态</c:if>
        	<c:if test="${cur.map.vDynamic.type eq 2}">举报发布的商品</c:if>
          </td>
          <td align="center"><span title="${fn:escapeXml(cur.map.vDynamic.content)}">${fnx:abbreviate(cur.map.vDynamic.content, 60, '...')}</span></td>
<!--           <td align="center"> -->
<%-- 	          <c:forEach var="item" items="${cur.map.imgList}" varStatus="vss"> --%>
<%-- 			        <a target="_blank" href="${ctx}/${item.file_path}" title="相关图片" class="cb">  --%>
<%-- 			          <img height="50" src="${ctx}/${item.file_path}@s400x400" /> --%>
<!-- 			        </a> -->
<%-- 		        </c:forEach> --%>
<!--           </td> -->
          <td align="center">${fn:escapeXml(cur.map.vDynamic.village_name)}</td>
          <td align="center"><fmt:formatDate value="${cur.map.vDynamic.add_date}" pattern="yyyy-MM-dd" /></td>
          <td align="center">${fn:escapeXml(cur.add_user_name)}</td>
          <td align="center">${fn:escapeXml(cur.reason)}</td>
          <c:if test="${cur.is_read eq 1}"><td align="center" style="color: blue;">已读</td></c:if>
          <c:if test="${cur.is_read ne 1}"><td align="center" style="color: red;">未读 </td></c:if>
          <td align="center" nowrap="nowrap">
               <a class="butbase" onclick="doNeedMethod(null, 'DynamicReportManager.do', 'audit','id=${cur.id}&dynamic_id=${cur.dynamic_id}&mod_id=${af.map.mod_id}&'+$('#bottomPageForm').serialize())"><span class="icon-ok">动态审核</span></a> 
          </td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="DynamicReportManager.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
        <tr>
          <td height="10" ><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("par_id", "${af.map.par_id}");
            pager.addHiddenInputs("village_name_like", "${fn:escapeXml(af.map.village_name_like)}");
            pager.addHiddenInputs("is_read", "${fn:escapeXml(af.map.is_read)}");
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
	var url = "${ctx}/manager/customer/DynamicReportManager.do?method=commentList&dynamic_id=" + id;
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

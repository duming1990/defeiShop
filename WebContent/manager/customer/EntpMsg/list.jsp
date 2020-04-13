<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/EntpMsg">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="6%" nowrap="nowrap"> 标题：
                <html-el:text property="msg_title_like" styleId="msg_title_like" style="width:140px;" styleClass="webinput"/>
                &nbsp;回复状态：
                <html-el:select property="info_state" styleClass="webinput" >
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="0">未回复</html-el:option>
                  <html-el:option value="1">已回复</html-el:option>
                </html-el:select>
                &nbsp;&nbsp;
                <button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <div style="padding:5px;">
    <button class="bgButtonFontAwesome" type="button" onclick="location.href='EntpMsg.do?method=add&par_id=${af.map.par_id}&mod_id=${af.map.mod_id}';" ><i class="fa fa-plus-square"></i>添加</button>
  </div>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
    <tr>
      <th width="18%">申请区域</th>
      <th width="18%">标题</th>
      <th>内容</th>
      <th width="12%">申请时间</th>
      <th width="12%">是否回复</th>
      <th width="12%" nowrap="nowrap">操作</th>
    </tr>
    <c:forEach var="cur" items="${msgList}" varStatus="vs">
      <tr>
        <td align="center">${fn:escapeXml(cur.p_name)}</td>
        <td align="center">${fn:escapeXml(cur.msg_title)}</td>
        <td align="left">${fn:escapeXml(cur.msg_content)}</a></td>
        <td align="center"><fmt:formatDate value="${cur.send_time}" pattern="yyyy-MM-dd" /></td>
        <td align="center"><c:choose>
            <c:when test="${cur.info_state eq 0}"><span class="label label-danger">未回复</span></c:when>
            <c:when test="${cur.info_state eq 1}"><span class="label label-success">已回复</span></c:when>
          </c:choose>
        </td>
        <td align="center" nowrap="nowrap"><a title="查看" class="label label-warning" href="EntpMsg.do?method=view&amp;id=${cur.id}&par_id=${af.map.par_id}&mod_id=${af.map.mod_id}">查看</a> </td>
      </tr>
    </c:forEach>
  </table>
  </form>
  <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="EntpMsg.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
        <tr>
          <td height="10" ><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("par_id", "${af.map.par_id}");
            pager.addHiddenInputs("msg_title_like", "${fn:escapeXml(af.map.msg_title_like)}");
            document.write(pager.toString());
        </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
});

</script>
</body>
</html>

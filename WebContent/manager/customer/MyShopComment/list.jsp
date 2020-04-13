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
<%@ include file="/commons/pages/messages.jsp" %>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
  <tr>
    <th width="20%">评价商品</th>
    <th>评论人</th>
    <th>评论心得</th>
    <th>评论时间</th>
    <th>操作</th>
  </tr>
  <c:forEach var="cur" items="${commentInfoList}" varStatus="vs">
    <tr>
      <td align="center">查看</td>
      <td align="center">${fn:escapeXml(cur.comm_uname)}</a></td>
      <td align="center">${fn:escapeXml(cur.comm_experience)}</a></td>
      <td align="center"><fmt:formatDate value="${cur.comm_time}" pattern="yyyy-MM-dd HH:mm" /></td>
      <td align="center"><a class="label label-success" id="edit" onclick="doNeedMethod('null', 'MyShopComment.do', 'reply', 'id=${cur.id}&comm_type=${cur.comm_type}&par_id=${af.map.par_id}&mod_id=${af.map.mod_id}&' + $('#bottomPageForm').serialize())"><span id="${cur.id}" class="icon-edit">回  复</span></a></td>
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
<div class="black">
  <form id="bottomPageForm" name="bottomPageForm" method="post" action="MyShopComment.do">
    <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
      <tr>
        <td height="10" ><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
          <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("par_id", "${af.map.par_id}");
            pager.addHiddenInputs("comm_type", "${af.map.comm_type}");
            document.write(pager.toString());
        </script></td>
      </tr>
    </table>
  </form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript">//<![CDATA[
  var f = document.forms[0];
  $("#btn_submit").click(function(){
  	f.submit();
  });
  </script>
</body>
</html>

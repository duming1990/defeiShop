<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>咨询投诉 - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<link href="${ctx}/styles/indexv3/css/top.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv3/css/global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv3/css/fonts.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv2/css/nmnetwork.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv2/css/netlist.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv3/css/bottom.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv2/css/left_sort.css" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="../../_header.jsp" flush="true" />
<!--first start -->
<div class="newnavdown">
  <c:url var="url" value="/index.do"></c:url>
  <div class="neone"><a href="${url}">首页</a> &gt; <strong>咨询投诉</strong> </div>
</div>

<!-- list start -->
<div class="listcont"> 
  <!-- left -->
  <div class="listleft">
    <div class="listab1">
      <div class="listit2">热门快报</div>
      <ul class="listul2">
        <jsp:include page="../../public/_index_tjzx.jsp" flush="true" />
      </ul>
    </div>
    <div class="listbot2"></div>
    <div class="listab1 martop8">
      <div class="listit2">品牌推荐</div>
      <ul class="rp1">
        <jsp:include page="../../public/_index_qytgfw.jsp" flush="true" />
      </ul>
    </div>
    <div class="listbot2"></div>
  </div>
  <!-- right -->
  <div class="listright">
    <%-- <div class="picone"><img src="${ctx}/styles/indexv2/images/qainfo.jpg" height="85" /></div> --%>
    <div class="qysearch">
      <c:url var="url" value="IndexQaInfo.do" />
      <html-el:form action="${url}" styleClass="qaForm">
        <html-el:hidden property="method" value="list" />
        <table width="794" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td height="47"><img src="${ctx}/styles/indexv2/images/qainfo_1.jpg" width="171" height="49" /></td>
            <td width="551">主题：
              <html-el:text property="q_title_like" maxlength="50" style="width:120px;"/></td>
            <c:url var="url" value="/IndexQaInfo.do?method=add" />
            <td width="74"><span id="sousuo_submit" style="cursor: pointer;"><img src="${ctx}/styles/indexv2/images/sousuo_ion.png" width="55" height="23" /></span></td>
            <td width="74"><span id="btn_qstw" style="cursor: pointer;"><a href="${url}">快速投诉</a></span></td>
          </tr>
        </table>
      </html-el:form>
    </div>
    <div class="listnews" style="width: 1018px;">
      <div class="f_fenlei">
        <c:forEach items="${qaInfoList}" var="cur" varStatus="vs">
          <div class="part12">
            <p>序号:[${vs.count}] &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;投诉时间：[
              <fmt:formatDate value="${cur.q_date}" pattern="yyyy-MM-dd"/>
              ]</p>
            <p><span class="red3">问题：&nbsp;${cur.q_title}</span></p>
            <p>内容：&nbsp;${cur.q_content}</p>
            <p class="blue2">回答：&nbsp;${cur.a_content}</p>
            <div class="stop7"></div>
          </div>
        </c:forEach>
      </div>
      <div style="line-height:40px; text-align:center; margin-top:10px;">
        <c:if test="${empty qaInfoList}">
          <div class="infoNoItem">
            <p class="warningFailed">信息建设中......</p>
          </div>
        </c:if>
        <c:if test="${not empty qaInfoList}">
          <c:url var="url" value="/IndexQaInfo.do"/>
          <form id="bottomPageForm" name="bottomPageForm" method="post" action="${url}">
            <table width="98%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
                  <script type="text/javascript">
						var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
						pager.addHiddenInputs("method", "list");
						pager.addHiddenInputs("q_title_like", "${fn:escapeXml(af.map.q_title_like)}");
						document.write(pager.toString());
		            	</script></td>
              </tr>
            </table>
          </form>
        </c:if>
      </div>
    </div>
  </div>
</div>
<div class="clear"></div>
<!-- list end -->
<jsp:include page="../../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/scripts/tabs/tabs.switch.min.js"></script> 

<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	var f = $(".qaForm").get(0);// 第二个form,第一个为header页面中的登录

	$("#btn_qstw").click(function(){
		location.href="IndexQaInfo.do?method=add";
	});
	
	$("#sousuo_submit").click(function(){
		f.submit();
	});
});
//]]></script>
</body>
</html>
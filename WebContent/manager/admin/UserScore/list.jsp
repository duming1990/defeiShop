<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
  <html-el:form action="/admin/UserScore">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="id" />
    <html-el:hidden property="user_name" />
    <html-el:hidden property="mobile" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td nowrap="nowrap"><div>
<%--                   <input type="button" name="add" id="add" class="bgButton" value="赠送积分" onclick="location.href='UserScore.do?method=add&mod_id=${af.map.mod_id}&id=${af.map.id}';" /> --%>
                  &nbsp;总积分：
                  <fmt:formatNumber var="score_bi" value="${score}" pattern="0.########"/>
                  ${score_bi}
                </div></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
    <tr>
      <th align="center" nowrap="nowrap">用户名</th>
      <th width="25%">类型</th>
      <th>操作前积分</th>
      <th>本次积分</th>
      <th>操作后积分</th>
      <th nowrap="nowrap">操作时间</th>
    </tr>
    <c:forEach var="cur" items="${userScoreRecordList}">
      <tr>
        <td align="center"><c:out value="${cur.map.userName}" /></td>
        <td align="center"><c:forEach items="${scoreTypes}" var="keys">
            <c:if test="${cur.score_type eq  keys.index}">${keys.name}</c:if>
          </c:forEach></td>
        <fmt:formatNumber var="score" value="${cur.hd_score_before}" pattern="0.########"/>
        <td align="center">${score}</td>
        <fmt:formatNumber var="score" value="${cur.hd_score}" pattern="0.########"/>
        <td align="center">${score}</td>
        <fmt:formatNumber var="score" value="${cur.hd_score_after}" pattern="0.########"/>
        <td align="center">${score}</td>
        <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd" /></td>
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
        </tr>
      </c:forEach>
  </table>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="UserScore.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("user_name", "${af.map.user_name}");
            pager.addHiddenInputs("mobile", "${af.map.mobile}");
            pager.addHiddenInputs("user_no", "${af.map.user_no}");
            pager.addHiddenInputs("begin_date", "${af.map.begin_date}");
            pager.addHiddenInputs("end_date", "${af.map.end_date}");
            pager.addHiddenInputs("score_type", "${af.map.score_type}");
            pager.addHiddenInputs("id", "${af.map.id}");
            document.write(pager.toString());
            </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript">//<![CDATA[
$("#id_role_id").focus(setOnlyNum).blur(function(){
	if(/[.+]+/.test(this.value)){
		alert("ID只能为整数！");
		this.value = this.value.replace(/[.+]/g,"");
		this.focus();
		return false;
	}	
});
function setOnlyNum() {
	$(this).css("ime-mode", "disabled").attr("t_value", "").attr("o_value", "").bind("dragenter",function(){
		return false;
	});
	$(this).keypress(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).keyup(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).blur(function (){
		if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value;}
		if(this.value.length == 0) this.value = "";
	});
	//this.text.selected;
}
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>

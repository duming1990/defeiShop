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
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <%@ include file="/commons/pages/messages.jsp" %>
<!--   <div style="padding-bottom:5px;"> -->
<%--   <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='NewsInfoSingle.do?method=add&mod_id=${af.map.mod_id}';" /> --%>
<!--   </div> -->
  <form id="listForm" name="listForm" method="post" action="NewsInfo.do?method=delete">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%" nowrap="nowrap">序号</th>
        <th width="20%">标题</th>
        <th>图片</th>
        <th width="10%">排序值</th>
        <th width="12%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          <td>${vs.count}</td>
          <td align="center">${fn:escapeXml(cur.title)}</td>
          <td align="center"><img src="${ctx}/${cur.image_path}" style="width: 100px;"></img></td>
          <td align="center">${fn:escapeXml(cur.order_value)}</td>
            <td nowrap="nowrap">
                <a class="butbase"  onclick="confirmUpdate(null, 'NewsInfoSingle.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())">
                <span  class="icon-edit">修改</span></a>
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
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="NewsInfo.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
            <script type="text/javascript">
				var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
				pager.addHiddenInputs("method", "list");
				pager.addHiddenInputs("title_like", "${fn:escapeXml(af.map.title_like)}");
				pager.addHiddenInputs("st_pub_date", "${af.map.st_pub_date}");
				pager.addHiddenInputs("en_pub_date", "${af.map.en_pub_date}");
				pager.addHiddenInputs("info_state", "${af.map.info_state}");
				pager.addHiddenInputs("pub_state", "${af.map.pub_state}");
				pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
				pager.addHiddenInputs("order_value", "${af.map.order_value}");
				document.write(pager.toString());
            	</script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/cs.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	var f = document.forms[0];
	var states = 
	[
		["P", "已发布", "1"],
		["P", "未发布", "0"],
		//[1, "直接发布", "1"],
		//[1, "已修改（已发布）", "2"],
		[1, "已审核（已发布）", "3"],
		[0, "默认（待审核）", "0"],
		//[0, "不发布", "-1"],
		//[0, "已修改（未发布）", "-2"],
		[0, "已审核（未发布）", "-3"]
	]

	f.pub_state.setAttribute("subElement", "info_state");
	
	f.pub_state.setAttribute("defaultText", "请选择...");
	f.pub_state.setAttribute("defaultValue", "");
	f.pub_state.setAttribute("selectedValue", "${af.map.pub_state}");
	
	f.info_state.setAttribute("defaultText", "请选择...");
	f.info_state.setAttribute("defaultValue", "");
	f.info_state.setAttribute("selectedValue", "${af.map.info_state}");

	new CascadeSelect(f.pub_state, "P", states);

	$("#btn_submit").click(function(){
		if (this.form.st_pub_date.value != "" && this.form.en_pub_date.value != "") {
			if (this.form.en_pub_date.value < this.form.st_pub_date.value) {
				alert("结束日期不得早于开始日期,请重新选择!")
				return false;
			}
		}
		this.form.submit();
	});
});
//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>

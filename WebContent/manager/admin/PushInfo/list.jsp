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
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/PushInfo">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="5%" nowrap="nowrap">&nbsp;标题：
                <html-el:text property="title_like" style="width:100px;" styleClass="webinput" />
                &nbsp;发布时间 从：
                <html-el:text property="st_pub_date" size="8" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                至
                <html-el:text property="en_pub_date" size="8" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                <html-el:submit value="查 询" styleClass="bgButton"  /></td>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="PushInfo.do?method=delete">
    <div style="padding-bottom:5px;">
      <logic-el:match name="popedom" value="+4+">
        <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" />
      </logic-el:match>
      <logic-el:match name="popedom" value="+1+">
        <input type="button" name="add" id="add" class="bgButton" value="添加" onclick="location.href='PushInfo.do?method=add&mod_id=${af.map.mod_id}';" />
      </logic-el:match>
      <input type="hidden" name="method" id="method" value="delete" />
      <input type="hidden" name="mod_id" id="mod_id" value="${af.map.mod_id}" />
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%" nowrap="nowrap"> <c:if test="${fn:contains(popedom, '+4+')}" var="isDelete">
            <input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" />
          </c:if>
          <c:if test="${not isDelete}"> 序号 </c:if>
        </th>
        <th nowrap="nowrap">标题</th>
        <th width="20%" nowrap="nowrap">推送</th>
        <th width="10%" nowrap="nowrap">添加时间</th>
        <th width="10%" nowrap="nowrap">推送状态</th>
        <c:if test="${fn:contains(popedom, '+2+') or fn:contains(popedom, '+4+') or fn:contains(popedom, '+8+')}" var="isContains">
          <th width="12%" nowrap="nowrap">操作</th>
        </c:if>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          <td nowrap="nowrap"><input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}" /></td>
          <td align="left" ><a title="${fn:escapeXml(cur.title)}" style="color:${cur.title_color}; text-decoration:none; <c:if test='${cur.title_is_strong eq 1}'>font-weight:bold;</c:if>" href="PushInfo.do?method=view&amp;id=${cur.id}&amp;mod_id=${cur.mod_id}">
            <c:out value="${fnx:abbreviate(cur.title, 50, '...')}" />
            </a></td>
          <td align="center" nowrap="nowrap"><a class="butbase" href="javascript:void(0);"> <span class="icon-add" onclick="apppush('${af.map.mod_id}','${cur.id}','');">推送到全部用户</span></a> 
          &nbsp;<a class="butbase" href="javascript:void(0);"> <span class="icon-add" onclick="apppush('${af.map.mod_id}','${cur.id}','ios');">推送到苹果用户</span></a>
          &nbsp;<a class="butbase" href="javascript:void(0);"> <span class="icon-add" onclick="apppush('${af.map.mod_id}','${cur.id}','android');">推送到安卓用户</span></a>
          </td>
          <td align="center" nowrap="nowrap"><fmt:formatDate value="${cur.add_time}" pattern="yyyy-MM-dd" /></td>
          <td nowrap="nowrap"><c:choose>
              <c:when test="${cur.is_push eq 1}"> <span style="color:#f00;">已推送</span> </c:when>
              <c:when test="${cur.is_push eq 0}"> 未推送 </c:when>
            </c:choose></td>
          <c:if test="${isContains}" >
            <td nowrap="nowrap"><logic-el:match name="popedom" value="+2+"><a class="butbase" href="javascript:void(0);"><span class="icon-edit" onclick="confirmUpdate(null, 'PushInfo.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())">修改</span></a> </logic-el:match>
              <logic-el:match name="popedom" value="+4+"><a class="butbase" href="javascript:void(0);"><span class="icon-remove" onclick="confirmDelete(null, 'PushInfo.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())">删除</span></a></logic-el:match>
            </td>
          </c:if>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="PushInfo.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
				var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
				pager.addHiddenInputs("method", "list");
				pager.addHiddenInputs("title_like", "${af.map.title_like}");
				pager.addHiddenInputs("title", "${fn:escapeXml(af.map.title)}");
				pager.addHiddenInputs("st_pub_date", "${af.map.st_pub_date}");
				pager.addHiddenInputs("en_pub_date", "${af.map.en_pub_date}");
				pager.addHiddenInputs("info_state", "${af.map.info_state}");
				pager.addHiddenInputs("province", "${af.map.province}");
				pager.addHiddenInputs("city", "${af.map.city}");
				pager.addHiddenInputs("country", "${af.map.country}");
				pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
				pager.addHiddenInputs("order_value", "${af.map.order_value}");
				document.write(pager.toString());
            	</script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/cs.js"></script>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	var f = document.forms[0];

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

function apppush(mod_id,id,platform){
	if(null == mod_id || "" == mod_id){
		$.jBox.alert("参数有误！","系统提示");
		return false;
	}
	
	var submit = function (v, h, f) {
	    if (v == true) {
	     $.jBox.tip("推送中...", 'loading');
	     window.setTimeout(function () { 
		     $.post("?method=appPush",{mod_id:mod_id,id:id,platform:platform},function(data){
		    	 var code = data.code;
		    	 if(code == 1){
		    		  $.jBox.tip(data.msg, "success");
		    	      //window.location.reload();
		    	 } else{
		    		 $.jBox.tip(data.msg, "error");
		    	 }
		     });
	     }, 1000);
	    } 
	    return true;
	};
	myConfirm("确定要推送该消息吗？",submit);
}
function myConfirm(tip, submit){ 
	$.jBox.confirm(tip, "${app_name}", submit, { buttons: { '确定': true, '取消': false} });
}

//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>

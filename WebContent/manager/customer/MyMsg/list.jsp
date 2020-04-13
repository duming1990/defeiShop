<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>消息中心- ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/MyMsg">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="order_type" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td>标题：
          <html-el:text property="msg_title" styleClass="webinput" maxlength="50" style="width:120px;"/>
          &nbsp;发送时间 从:
          <html-el:text property="st_date" styleId="st_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
          至：
          <html-el:text property="en_date" styleId="en_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
          &nbsp;
          状态：
          <html-el:select property="read_state" styleId="read_state" styleClass="webinput">
            <html-el:option value="">全部</html-el:option>
              <html-el:option value="0">未读</html-el:option>
              <html-el:option value="1">已读</html-el:option>
          </html-el:select>
          &nbsp;
          <button class="bgButtonFontAwesome" type="button" id="btn_submit"><i class="fa fa-search"></i>查 询</button>
          </td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <div style="padding: 5px"><button onclick="setRead();" class="bgButtonFontAwesome" type="button" id="btn_submit"><i class="fa  fa-check-circle-o"></i>全部标记为已读</button></div>
  <form id="listForm" name="listForm" method="post">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable">
      <tr>
        <th width="7%" nowrap="nowrap">序号</th>
        <th nowrap="nowrap">主题</th>
        <th width="15%" nowrap="nowrap">发送人</th>
        <th width="15%" nowrap="nowrap">发送时间</th>
        <th width="8%" nowrap="nowrap">是否已读</th>
        <th width="8%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center" nowrap="nowrap">${vs.count}</td>
          <td align="left">${fn:escapeXml(cur.msg_title)}</td>
          <td align="left">${cur.user_name}
            <input type="hidden" name="user_name" id="user_name" value="${cur.user_name}" /></td>
          <td align="center"><fmt:formatDate value="${cur.send_time}" pattern="yyyy-MM-dd HH:mm" /></td>
          <td align="center"><c:choose>
              <c:when test="${cur.is_read eq 1}"><span class="label label-success">已读</span></c:when>
              <c:otherwise><span class="label label-danger">未读</span></c:otherwise>
            </c:choose></td>
            <td align="center"><a class="label label-warning label-block" href="MyMsg.do?method=view&amp;par_id=${af.map.par_id}&amp;mod_id=${af.map.mod_id}&amp;msg_id=${cur.msg_id}&amp;msg_rec_id=${cur.id}&read_state=${af.map.read_state}">查看</a>
            <c:if test="${cur.is_send_all eq 1}" var="isall"><span class="label label-default label-block" title="群发信息不能删除">群发</span></c:if>
            <c:if test="${not isall}"><a class="butbase" href="javascript:void(0);"><span class="label label-danger label-block" onclick="confirmDelete(null, 'MyMsg.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())">删除</span></a></c:if>
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
          <td>&nbsp;</td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="${ctx}/manager/customer/MyMsg.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
				var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
		        pager.addHiddenInputs("method", "list");
				pager.addHiddenInputs("msg_title", "${af.map.msg_title}");
				pager.addHiddenInputs("st_date", "${af.map.st_date}");
				pager.addHiddenInputs("en_date", "${af.map.en_date}");
				pager.addHiddenInputs("read_state", "${af.map.read_state}");
				pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
		        pager.addHiddenInputs("par_id", "${af.map.par_id}");
		        document.write(pager.toString());
            	</script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<div class="clear"></div>
<!-- main end -->
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	//导航回显
	$("#nav_ul li").each(function(){
		if($(this).attr("data-type") ==  $("#order_type").val()){
			$(this).addClass("active").siblings().removeClass("active");
			return false;
		}
	});

	//导航跳转
	$("#nav_ul a").click(function(){ 
		 var type = $(this).parent().attr("data-type");
		 this.href= "${ctx}/manager/customer/MyHzOrder.do?method=list&order_type="+ type + "&" + $('#bottomPageForm').serialize();
	     this.target = "_self";
	});
});

var f = document.forms[0];
$("#btn_submit").click(function(){
	var start_date = $("#st_date").val();
	var end_date = $("#en_date").val();
	if(end_date != ""){
		  if(start_date > end_date){
			  alert("下单时间选择有错误！");
			  return false;
		  }
		}
	f.submit();
});

function setRead(){
	if(confirm("确认标记所有消息为已读？")){
		$msg = $(".user-info-msg", top.document).find("em").text("(0)").removeClass("blink");
		location.href="MyMsg.do?method=setRead&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}";
	}
}

</script>
</body>
</html>

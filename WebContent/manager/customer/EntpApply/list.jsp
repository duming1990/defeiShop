<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body style="height:1200px;">
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/EntpApply">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td>店铺名：
              <html-el:text property="entp_name_like" maxlength="40" style="width:150px;" styleClass="webinput" />
             	&nbsp;审核状态：
              <html-el:select property="audit_state" styleClass="webinput" style="width:100px;">
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="-2">审核不通过</html-el:option>
                  <html-el:option value="0">待审核</html-el:option>
                  <html-el:option value="2">审核通过</html-el:option>
                </html-el:select>
                <div style="margin-top:5px;">
                 	店铺编号：
                 <html-el:text property="entp_no" maxlength="40" style="width:100px;" styleClass="webinput" />
              	&nbsp;是否删除：
              <html-el:select property="is_del" styleClass="webinput" >
                  <html-el:option value="-1">全部</html-el:option>
                  <html-el:option value="0">未删除</html-el:option>
                  <html-el:option value="1">已删除</html-el:option>
                </html-el:select>
              	&nbsp;添加时间：
              <html-el:text property="today_date" styleClass="webinput"  styleId="today_date" size="9" maxlength="9" readonly="true" onclick="WdatePicker()" style="width:80px;"/>
             	&nbsp;<button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button>
             </div>
               </td>
            </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="EntpApply.do?method=delete">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th width="5%">序号</th>
        <th>店铺名称</th>
         <th width="8%">店铺编号</th>
        <th width="16%">所属地区</th>
        <th width="8%">添加时间</th>
        <th width="8%">修改时间</th>
        <th width="8%">排序值</th>
        <th width="10%">审核状态</th>
        <th width="8%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center">${vs.count}</td>
          <td align="left">
          <a title="查看" href="EntpApply.do?method=view&amp;mod_id=${af.map.mod_id}&amp;id=${cur.id}&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}">${fn:escapeXml(cur.entp_name)}</a></td>
          <td align="center">
          <c:if test="${not empty cur.entp_no}">
          	${fn:escapeXml(cur.entp_no)}
          </c:if>
          <c:if test="${empty cur.entp_no}">---</c:if>
          </td>
          <td align="center">${fn:escapeXml(cur.map.full_name)}</td>
          <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd" /></td>
          <fmt:formatDate value="${cur.update_date}" pattern="yyyy-MM-dd HH:mm:ss" var="up_date"/>
          <td align="center" title="最后修改时间：${up_date}" class="quicktip"><fmt:formatDate value="${cur.update_date}" pattern="yyyy-MM-dd" /></td>
          <td align="center">${cur.order_value}</td>
          <td align="center">
            <c:choose>
              <c:when test="${cur.audit_state eq -2}"><span class="label label-danger">审核不通过</span></c:when>
              <c:when test="${cur.audit_state eq 0}"><span class="label label-default">待审核</span></c:when>
              <c:when test="${cur.audit_state eq 2}"><span class="label label-success">审核通过</span></c:when>
            </c:choose>
            </td>
          <td align="center" nowrap="nowrap">
          	<a class="label label-warning label-block" id="remove" onclick="confirmUpdate(null, 'EntpApply.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())">修改</a>
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
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="black">
  <form id="bottomPageForm" name="bottomPageForm" method="post" action="EntpApply.do">
     <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
      <tr>
        <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
          <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("par_id", "${af.map.par_id}");
            pager.addHiddenInputs("entp_name_like", "${fn:escapeXml(af.map.entp_name_like)}");
			pager.addHiddenInputs("is_del", "${af.map.is_del}");
			pager.addHiddenInputs("today_date", "${af.map.today_date}");
			pager.addHiddenInputs("audit_state", "${af.map.audit_state}");
			pager.addHiddenInputs("entp_no", "${af.map.entp_no}");
			pager.addHiddenInputs("is_has_open_online_shop", "${af.map.is_has_open_online_shop}");
            document.write(pager.toString());
            </script></td>
      </tr>
    </table>
  </form>
  </div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$(".quicktip").quicktip();
});

function createLinkUserInfo(id){
	var url = "${ctx}/manager/customer/EntpApply.do?method=addUser&own_entp_id=" + id;
	$.dialog({
		title:  "创建店长",
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

function commInfoCopy(id){
	var url = "${ctx}/manager/customer/EntpApply.do?method=commInfoCopy&own_entp_id=" + id;
	$.dialog({
		title:  "创建店长",
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

function updateOnlineShopState(id){
	
	var submit = function(v, h, f) {
		var state;
		if (v == true) {
			state = 0;
		}else{
			state = 1;
		}
		
		$.jBox.tip("正在操作...", 'loading');
		
		$.ajax({
			type: "POST" , 
			url: "EntpApply.do" , 
			data:"method=updateOnlineShopState&id="+ id +"&is_has_open_online_shop="+ state + "&t=" + new Date(),
			dataType: "json", 
	        async: true, 
	        error: function (request, settings) {}, 
	        success: function (data) {
				if (data.code == 1) {
					$.jBox.tip("操作成功", "success",{timeout:1000});
				} else {
					$.jBox.tip(data.msg, "error",{timeout:1000});
				}
				window.setTimeout(function(){
					refreshPage();	
				},2000);
				
	        }
		});
		
	};
	$.jBox.confirm("是否开通网上店铺？", "系统提示", submit,{ buttons: { '开通': true, '暂不开通': false} });
}


function refreshPage(){
	window.location.reload();
}


//]]></script>
</body>
</html>

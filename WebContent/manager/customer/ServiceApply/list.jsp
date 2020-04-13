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
  <html-el:form action="/customer/ServiceApply">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="6%" nowrap="nowrap">合伙人名称：
                <html-el:text property="servicecenter_name_like" styleId="servicecenter_name_like" style="width:140px;" styleClass="webinput"/>
                &nbsp;审核状态：
                <html-el:select property="audit_state" styleClass="webinput" >
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="-1">审核不通过</html-el:option>
                  <html-el:option value="0">待审核</html-el:option>
                  <html-el:option value="1">审核通过</html-el:option>
                </html-el:select>
                &nbsp;&nbsp;
                <button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="ServiceApply.do?method=delete">
    <c:if test="${entityQuerySizeCount < 1}">
    <div style="padding:5px;">
      <button class="bgButtonFontAwesome" type="button" onclick="location.href='ServiceApply.do?method=add&par_id=${af.map.par_id}&mod_id=${af.map.mod_id}';" ><i class="fa fa-plus-square"></i>添加</button>
    </div>
    </c:if>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th>合伙人名称</th>
        <th width="10%">服务编号</th>
        <th width="10%">所属地区</th>
        <th width="8%">添加时间</th>
        <th width="8%">是否生效</th>
        <th width="8%">生效时间</th>
        <th width="8%">失效时间</th>
        <th width="8%">是否审核</th>
<!--         <th width="8%">物流状态</th> -->
        <th width="8%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="left"><a title="查看" href="ServiceApply.do?method=view&amp;id=${cur.id}&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}">${fn:escapeXml(cur.servicecenter_name)}</a></td>
          <td align="center">
          <c:if test="${not empty cur.servicecenter_no}">
          	${fn:escapeXml(cur.servicecenter_no)}
          </c:if>
          <c:if test="${empty cur.servicecenter_no}">---</c:if>
          </td>
          <td align="center">${fn:escapeXml(cur.map.full_name)}</td>
          <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd" /></td>
           <td align="center">
            <c:choose>
              <c:when test="${cur.effect_state eq 0}"><span>未生效</span></c:when>
              <c:when test="${cur.effect_state eq 1}"><span style=" color:#060;">已生效</span></c:when>
            </c:choose>
            </td>
           <td align="center">
           <c:if test="${cur.effect_state eq 0}">还未确认生效</c:if>
           <c:if test="${cur.effect_state eq 1}">
           <fmt:formatDate value="${cur.effect_date}" pattern="yyyy-MM-dd" /></c:if>
           </td>
           <td align="center">
           <c:if test="${not empty cur.effect_end_date}">
           <div class="tip-danger quicktip" title="距离合伙人到期还剩${cur.map.day_tip_give_money}天"><i class="fa fa-clock-o"></i> ${cur.map.day_tip_give_money}天</div>
           <fmt:formatDate value="${cur.effect_end_date}" pattern="yyyy-MM-dd" />
           </c:if>
           </td>
          <td align="center"><c:choose>
              <c:when test="${cur.audit_state eq -1}"><span style=" color:#F00;">审核不通过</span></c:when>
              <c:when test="${cur.audit_state eq 0}"><span>待审核</span></c:when>
              <c:when test="${cur.audit_state eq 1}"><span style=" color:#060;">审核通过</span></c:when>
            </c:choose>
          </td>
          <td align="center" nowrap="nowrap">
            <c:if test="${cur.audit_state ne 1}">
	            <a class="label label-warning label-block" onclick="confirmUpdate(null, 'ServiceApply.do', 'id=${cur.id}&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}&' + $('#bottomPageForm').serialize())">修改</a>
	            <a class="label label-danger label-block" onclick="confirmDelete(null, 'ServiceApply.do', 'id=${cur.id}&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}&' + $('#bottomPageForm').serialize())">删除</a>
	        </c:if>
            <c:if test="${cur.audit_state eq 1}">
	            <span class="label label-default label-block" title="已审核，不能再修改">修改</span>
	            <span class="label label-default label-block" title="已审核，不能再删除">删除</span>
            </c:if>
          </td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="ServiceApply.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
        <tr>
          <td height="10" ><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("par_id", "${af.map.par_id}");
            pager.addHiddenInputs("servicecenter_name_like", "${fn:escapeXml(af.map.servicecenter_name_like)}");
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
$(document).ready(function(){
	$(".quicktip").quicktip();
});
function updateState(action, mtd, id, state, obj) {
	var submit2 = function(v, h, f) {
		if (v == "ok") {
			$.jBox.tip("加载中...", "loading");
			$.ajax({
				type : "POST",
				cache : false,
				url : action,
				data : "method=" + mtd + "&id=" + id + "&state=" + state,
				dataType : "json",
				error : function(request, settings) {
					$.jBox.tip("系统繁忙，请稍后重试！", "error",{timeout:2000});
				},
				success : function(data) {
					if (data.ret == "1") {
						 $.jBox.tip(data.msg, "success",{timeout:1000});
						 window.setTimeout(function () {
							 $("#bottomPageForm").submit();
						 }, 1500);					

					} else {
						$.jBox.tip(data.msg, "error",{timeout:2000});
					}
				}
			})
		}
		return true
	};
	var tip = "确定收货吗？";
	$.jBox.confirm(tip, "确定提示", submit2)
}

function showDialog(){
	 var submit = function (v, h, f) {
		    window.location.reload();
		    return true;
		};
		// 自定义按钮
	$.jBox.confirm("请您在新打开的页面进行支付,支付完成前请不要关闭此窗口!", "在线支付", submit, { width: 400,height: 160,buttons: { '支付成功': true, '支付失败': false}});
}
</script>
</body>
</html>

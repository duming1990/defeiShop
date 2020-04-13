<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心 - ${app_name}</title>
<meta content="${app_name}会员中心关键字" name="keywords" />
<meta content="${app_name}会员中心介绍" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div class="divContent">
  <html-el:form action="/customer/WelfareCard" styleClass="searchForm">
    <html-el:hidden property="method" value="cardList" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="gen_id" value="${af.map.gen_id}"/>
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td>
          <div style="margin-top: 5px;">卡号：
            <html-el:text property="card_no_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
            &nbsp;&nbsp;状态：
            <html-el:select property="card_state">
	            <html-el:option value="">请选择</html-el:option>
	            <html-el:option value="-1">作废</html-el:option>
	            <html-el:option value="0">未激活</html-el:option>
	            <html-el:option value="1">已激活</html-el:option>
	            <html-el:option value="2">已用完</html-el:option>
            </html-el:select>
             &nbsp;&nbsp;是否停用：
            <html-el:select property="is_del">
	            <html-el:option value="">请选择</html-el:option>
	            <html-el:option value="1">已停用</html-el:option>
	            <html-el:option value="0">未停用</html-el:option>
            </html-el:select>
            &nbsp;&nbsp;<button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button>
<!--             &nbsp;&nbsp;<button class="bgButtonFontAwesome" type="button" id="download">导出</button> -->
          </div></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="WelfareCard.do?method=delete">
    <div style="text-align: left;padding:5px;"></div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr class="tite2">
        <th width="nowrap">卡号</th>
        <th width="8%">密码</th>
        <th width="10%">申请批次</th>
<!--         <th width="10%">生成批次</th> -->
        <th width="6%">卡类型</th>
        <th width="6%">额度</th>
        <th width="6%">可用余额</th>
        <th width="6%">状态</th>
        <th width="10%">有效期</th>
        <th width="10%">生成时间</th>
        <th width="10%">是否停用</th>
        <c:if test="${card_type eq 20}">
        	<th width="6%">是否发送短信</th>
       		<th width="10%">操作</th>
        </c:if>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          <td>${fn:escapeXml(cur.card_no)}</td>
          <td>${fn:escapeXml(cur.map.card_pwd)}</td>
          <td>${fn:escapeXml(cur.apply_no)}</td>
<%--           <td>${fn:escapeXml(cur.gen_no)}</td> --%>
          <td>
	          <c:if test="${cur.card_type eq 10}">实体卡</c:if>
	          <c:if test="${cur.card_type eq 20}">电子卡</c:if>
          </td>
          <td><fmt:parseNumber value="${cur.card_amount}" pattern="0.##"></fmt:parseNumber></td>
          <td><fmt:parseNumber value="${cur.card_cash}" pattern="0.##"></fmt:parseNumber></td>
          <td>
	          <c:if test="${cur.card_state eq -1}"> <span style="color:red;">作废</span></c:if>
	          <c:if test="${cur.card_state eq 0}"><span>未激活</span></c:if>
	          <c:if test="${cur.card_state eq 1}"><span style="color:#148647db;">已激活</span></c:if>
	          <c:if test="${cur.card_state eq 2}"><span style="color:#0a74f9db;">已用完</span></c:if>
          </td>
           <td>
	          <fmt:formatDate value="${cur.start_date}" pattern="yyyy-MM-dd" />
	        	 </br> 至 </br>
	          <fmt:formatDate value="${cur.end_date}" pattern="yyyy-MM-dd" />
          </td>
          <td><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd" /></td>
          <td>
	          <c:if test="${cur.is_del eq 1}"><span style="color: red;">已停用</span></c:if>
	          <c:if test="${cur.is_del eq 0}"><span style="color: green;">未停用</span></c:if>
          </td>
       	  <c:if test="${card_type eq 20}">
       	    <td>
	            <c:if test="${cur.is_send eq 1}"><span style="color: green;cursor: pointer;">已发送</span></c:if>
	            <c:if test="${cur.is_send ne 1}"><span style="color: red;">未发送</span></c:if>
            </td>
          	<td>
          		<c:if test="${cur.is_send ne 1}">
	          		<a class="butbase" onclick="sendMsg(${cur.id});"><span class="icon-search">短信发放</span></a> 
	          	</c:if>
	          	<c:if test="${cur.is_send eq 1}">
	          		<a class="butbase" onclick="sendMsg(${cur.id});"><span class="icon-search">查看信息</span></a> 
	          	</c:if>
          	</td>
          </c:if>
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
          <td>&nbsp;</td>
          <c:if test="${card_type eq 20}">
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          </c:if>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
   <form id="bottomPageForm" name="bottomPageForm" method="post" action="WelfareCard.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "cardList");
					pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
					pager.addHiddenInputs("card_no_like", "${fn:escapeXml(af.map.card_no_like)}");
					pager.addHiddenInputs("card_state", "${fn:escapeXml(af.map.card_state)}");
					pager.addHiddenInputs("is_del", "${fn:escapeXml(af.map.is_del)}");
					pager.addHiddenInputs("gen_id", "${fn:escapeXml(af.map.gen_id)}");
					document.write(pager.toString());
	            	</script></td>
        </tr>
      </table>
    </form>
  </div>
  <div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript">//<![CDATA[
$("#download").click(function(){
	
	var submit = function (v, h, f) {
	    if (v == true) {
	    	location.href = "${ctx}/manager/customer/WelfareCard.do?method=toExcel&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
	    } else {
	    	location.href = "${ctx}/manager/customer/WelfareCard.do?method=toExcel&code=GBK&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
	    }
	    return true;
	};
	var tip = "确认导出EXCEL格式数据？如果UTF-8编码格式乱码，请选择GBK编码格式下载！";
	$.jBox.confirm(tip, "系统提示", submit, { buttons: { '下载(UTF-8编码)': true, '下载(GBK编码)': false} });
});


function sendMsg(id){
	var url="${ctx}/manager/customer/WelfareCard.do?method=senMsg&id="+id;
	$.dialog({
		title:  "短信发卡",
		width:  800,
		height: 400,
        lock:true ,
        zIndex: 9999,
		content:"url:"+url
	});
}
//]]></script>
</body>
</html>

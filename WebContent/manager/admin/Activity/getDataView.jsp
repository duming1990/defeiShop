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
   <c:set var="action" value="/admin/Activity" />
<html-el:form action="${action }">
    <html-el:hidden property="method" value="getDataView" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="id" value="${af.map.id }" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="5%" nowrap="nowrap">&nbsp;商家名称：
                <html-el:text property="entp_name_like" styleClass="webinput" />
					&nbsp;<html-el:submit value="查 询" styleClass="bgButton" />
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="Activity.do?method=delete">
   
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%" nowrap="nowrap"> 序号</th>
        <th >商家名称</th>
        <th >用户名</th>
        <th >订单数量</th>
        <th >订单总金额</th>
          <th width="12%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${list}" varStatus="vs">
        <tr align="center">
          <td>
           ${vs.count }</td>
          <td align="left" >
<%--           <a href="Activity.do?method=view&amp;id=${cur.id}"> --%>
            <c:out value="${fnx:abbreviate(cur.map.entp_name, 50, '...')}" />
<!--             </a> -->
            
            </td>
          <td align="center">${cur.map.user_name }</td>
          <td align="center">${cur.map.good_count }</td>
          <td align="center">${cur.map.good_sum_price }</td>
<%--           <td align="center"><fmt:formatDate value="${cur.start_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td> --%>
<%--           <td align="center"><fmt:formatDate value="${cur.end_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td> --%>
<%--           <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td> --%>
            <td nowrap="nowrap">
            <a onclick="getDataViewComm(${cur.entp_id},${cur.activity_id})">
            	商品销售排名
            </a>
            </br>
            <a onclick="getDataViewOrder(${cur.entp_id},${cur.activity_id})">
            	订单交易明细
            </a>
              </td>
        </tr>
        <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
      </c:forEach>
    </table>
  </form>
  
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/cs.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	var f = document.forms[0];


// 	new CascadeSelect(f.pub_state, "P", states);

	$("#btn_submit").click(function(){
		if (this.form.st_start_date.value != "" && this.form.en_end_date.value != "") {
			if (this.form.en_end_date.value < this.form.st_start_date.value) {
				alert("结束日期不得早于开始日期,请重新选择!")
				return false;
			}
		}
		this.form.submit();
	});
});

function getDataViewComm(entp_id,activity_id){
	var url = "${ctx}/manager/admin/Activity.do?method=getDataViewComm&entp_id="+entp_id+"&activity_id="+activity_id;
	$.dialog({
		title:  "商品销售排名",
		width:  850,
		height: 600,
        lock:true ,
		content:"url:"+url
	});
}
function getDataViewOrder(id,activity_id){
	var url = "${ctx}/manager/admin/OrderQuery.do?activity=true&order_type=90&order_state=50&entp_id="+id+"&activity_id="+activity_id;
	$.dialog({
		title:  "商品销售排名",
		width:  1000,
		height: 800,
        lock:true ,
		content:"url:"+url
	});
}
//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>

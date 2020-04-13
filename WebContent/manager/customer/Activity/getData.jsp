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
   <c:set var="action" value="/customer/Activity" />
<html-el:form action="${action }">
    <html-el:hidden property="method" value="getData" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="5%" nowrap="nowrap">&nbsp;标题：
                <html-el:text property="title_like" styleClass="webinput" />
                &nbsp;活动时间 从：
                <html-el:text property="st_start_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                至:
                <html-el:text property="en_end_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                &nbsp;
<%--                 <html-el:button value="查 询" styleClass="bgButton" property="" styleId="btn_submit" /></td> --%>
 <input type="submit" class="bgButton" value="查 询" />
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
        <th >活动名称</th>
<!--         <th >参与商家数量</th> -->
        <th >订单数量</th>
        <th >订单金额</th>
          <th width="12%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          <td><c:if test="${fn:contains(popedom, '+4+')}" var="isDelete">
           <c:set var="isDel" value="true"></c:set>
             <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}" />
            </c:if>
            <c:if test="${not isDelete}"> ${vs.count } </c:if></td>
          <td align="left" >
           <c:if test="${not empty admin }">
          <a href="Activity.do?method=view&amp;id=${cur.id}">
            <c:out value="${fnx:abbreviate(cur.map.title, 50, '...')}" />
            </a>
            </c:if>
            <c:if test="${empty admin }">
            <c:out value="${fnx:abbreviate(cur.map.title, 50, '...')}" />
            </c:if>
            </td>
<%--           <td align="center">${cur.map.count }</td> --%>
          <td align="center">${cur.map.order_count }</td>
          <td align="center">${cur.map.sum_money }</td>
<!--             <td nowrap="nowrap"> -->
<%--             <a href="Activity.do?method=getDataView&id=${cur.id}"> --%>
<!--             	查看明细 -->
<!--             </a> -->
<!--               </td> -->
<td nowrap="nowrap">
<%--             <a onclick="getDataViewComm(${cur.link_id})"> --%>
<!--             	商品销售排名 -->
<!--             </a> -->
<!--             </br> -->
<%--             <a onclick="getDataViewOrder(${cur.entp_id})"> --%>
<!--             	订单交易明细 -->
<!--             </a> -->
              </td>
        </tr>
        <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="Activity.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
            <script type="text/javascript">
				var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
				pager.addHiddenInputs("method", "getData");
				pager.addHiddenInputs("title_like", "${fn:escapeXml(af.map.title_like)}");
				pager.addHiddenInputs("st_start_date", "${af.map.st_start_date}");
				pager.addHiddenInputs("en_end_date", "${af.map.en_end_date}");
				pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
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


	new CascadeSelect(f.pub_state, "P", states);

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

function getDataViewComm(id){
	var url = "${ctx}/manager/customer/Activity.do?method=getDataViewComm&id="+id;
	$.dialog({
		title:  "商品销售排名",
		width:  850,
		height: 500,
        lock:true ,
		content:"url:"+url
	});
}
function getDataViewOrder(id){
	var url = "${ctx}/manager/customer/OrderQuery.do?order_type=90&entp_id="+id;
	$.dialog({
		title:  "商品销售排名",
		width:  1000,
		height: 500,
        lock:true ,
		content:"url:"+url
	});
}

//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>

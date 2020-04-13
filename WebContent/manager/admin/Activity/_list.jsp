<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>

<c:set var="action" value="/admin/Activity" />
<c:if test="${empty admin }">
 <c:set var="action" value="/customer/ActivityApply" />
</c:if>
<html-el:form action="${action }">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="5%" nowrap="nowrap">&nbsp;活动名称：
                <html-el:text property="title_like" styleClass="webinput" />
                &nbsp;发布时间 从：
                <html-el:text property="st_start_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                至:
                <html-el:text property="en_end_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                &nbsp;
                <html-el:button value="查 询" styleClass="bgButton" property="" styleId="btn_submit" />
                
                 &nbsp;<input id="downloadQrcode" type="button" value="导出二维码" class="bgButton" />
                
                </td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="Activity.do?method=delete">
    <div style="padding-bottom:5px;">
      <logic-el:match name="popedom" value="+4+">
        <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" />
      </logic-el:match>
      <logic-el:match name="popedom" value="+1+">
      <c:if test="${is_admin ne 0 }">
        <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='Activity.do?method=add&mod_id=${af.map.mod_id}';" />
        </c:if>
        
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
        <th >活动名称</th>
        <th width="10%">活动二维码</th>
        <th width="10%" >开始时间</th>
        <th width="10%" >结束时间</th>
        <th width="10%" >添加时间</th>
        <c:if test="${empty admin }">
        <th width="10%" >申请结果</th>
        </c:if>
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
            <c:out value="${fnx:abbreviate(cur.title, 50, '...')}" />
            </a>
            </c:if>
            <c:if test="${empty admin }">
            <a  href="Activity.do?method=view&amp;id=${cur.id}">
            <c:out value="${fnx:abbreviate(cur.title, 50, '...')}" />
            </a>
            </c:if>
            </td>
          <td align="center">
          <c:if test="${ not empty cur.map.qrcode_path}">
           <img src="${ctx}/${cur.map.qrcode_path}" width="100%"/>
            </c:if>
            </td>
          <td align="center"><fmt:formatDate value="${cur.start_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
          <td align="center"><fmt:formatDate value="${cur.end_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
          <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
           <c:if test="${empty admin }">
          <td>
          	<c:choose>
              <c:when test="${cur.map.audit_state eq -1}">
              <span class="label label-danger label-block">审核不通过</span>
              <c:if test="${(not empty cur.map.remark)}">
	            <a title="${fn:escapeXml(cur.map.remark)}" class="label label-warning label-block" onclick="viewAuditDesc('${cur.map.remark}');">
	            <i class="fa fa-info-circle"></i>查看原因</a> 
	          </c:if>
              </c:when>
              <c:when test="${cur.map.audit_state eq 0}"><span class="label label-default">待审核</span></c:when>
              <c:when test="${cur.map.audit_state eq 1}"><span class="label label-success">审核通过</span></c:when>
            </c:choose>
            <c:if test="${empty cur.map.audit_state }">
            <span class="label label-danger">未申请</span>
            </c:if>
          </td>
          </c:if>
            <td nowrap="nowrap">
            
            <c:if test="${not empty admin }">
            <logic-el:match name="popedom" value="+8+">
            <a class="butbase" onclick="doNeedMethod(null, 'Activity.do', 'audit','id=${cur.id}&mod_id=${cur.mod_id}&'+$('#bottomPageForm').serialize())">
            <span class="icon-ok">审核</span></a>
            </logic-el:match>
              <logic-el:match name="popedom" value="+2+">
                <a class="butbase but-disabled"  onclick="javascript:void(0);"  title="已审核（已发布），不能再修改"><span class="icon-edit">修改</span></a> 
              </logic-el:match>
              <logic-el:match name="popedom" value="+4+">
                <c:if test="${isDel}">
                <a class="butbase"   onclick="confirmDelete(null, 'Activity.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())">
                <span  class="icon-remove">删除</span></a>
                </c:if>
                <c:if test="${not isDel}">
                <a class="butbase but-disabled"  onclick="javascript:void(0);"  title="已审核，不能删除"><span class="icon-remove">删除</span></a>
                </c:if>
              </logic-el:match>
              </c:if>
<!--               不是超级管理员端 -->
                <c:if test="${empty admin and cur.map.audit_state ne 0}">
	                <c:set var="apply_name" value="活动申请" />
	                <c:if test="${cur.map.audit_state eq -1 }">
	                <c:set var="apply_name" value="再次申请" />
	                </c:if>
	                <c:if test="${cur.map.audit_state ne 1}">
	                	<a class="label label-warning label-block" onclick="apply(${cur.id})"><span id="${cur.id}">${apply_name}</span></a>
	                	</c:if>
	                	<a class="label label-info label-block"onclick="editActivityCommInfo(${cur.id})"><span id="${cur.id}">修改商品</span></a>
	                	
	                	<c:if test="${cur.map.audit_state eq 1}">
	                	<a class="label label-info label-block"onclick="downloadQrcode(${cur.map.apply_id})"><span id="${cur.id}">导出二维码</span></a>
	                	<a class="label label-default label-block" id="remove" onclick="javascript:void(0);"  title="已审核"><span id="${cur.id}">已申请</span></a>
	                	</c:if>
                </c:if>
                   <a class="butbase" onclick="javascript:openInput('${cur.id}');">
                <span  class="icon-edit">大屏页面</span></a>
              </td>
        </tr>
        <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
      </c:forEach>
<%--         <c:forEach begin="${i}" end="${af.map.pager.pageSize - 1}">  --%>
<!--          <tr align="center">  -->
<!--            <td>&nbsp;</td>  -->
<!--            <td>&nbsp;</td>  -->
<!--            <td>&nbsp;</td>  -->
<!--            <td>&nbsp;</td>  -->
<!--            <td>&nbsp;</td>  -->
<!--            <td>&nbsp;</td> -->
<!--            <td>&nbsp;</td>  -->
<!--            <td>&nbsp;</td>  -->
<%--            <c:if test="${empty admin }"><td>&nbsp;</td></c:if> --%>
<!--          </tr>  -->
<%--        </c:forEach>  --%>
    </table>
  </form>
  <div class="pageClass">
  <c:if test="${not empty thisAction }">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="${thisAction}.do">
    </c:if>
    
      <c:if test="${ empty thisAction }">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="Activity.do">
    </c:if>
    
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
            <script type="text/javascript">
				var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
				pager.addHiddenInputs("method", "list");
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
  <script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
  <script type="text/javascript" src="${ctx}/commons/scripts/cs.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
  <script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	var f = document.forms[0];

	$("#btn_submit").click(function(){
		this.form.submit();
	});
	
	
	$("#downloadQrcode").click(function(){
		var submit = function (v, h, f) {
		    if (v == true) {
		    	location.href = "/manager/customer/ActivityApply.do?method=downloadPoorInfoQrcode&" + $('.searchForm').serialize();
		    }
		    return true;
		};
		var tip = "确定导出二维码图片吗？";
		$.jBox.confirm(tip, "系统提示", submit, { buttons: { '确定': true, '取消': false} });
	});
});

function downloadQrcode(id){
	var submit = function (v, h, f) {
	    if (v == true) {
	    	location.href = "/manager/customer/ActivityApply.do?method=downloadPoorInfoQrcode&id="+id
	    }
	    return true;
	};
	var tip = "确定导出二维码图片吗？";
	$.jBox.confirm(tip, "系统提示", submit, { buttons: { '确定': true, '取消': false} });
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
function viewAuditDesc(audit_desc){
	$.dialog({
		title:  "审核不通过原因",
		width:  250,
		height: 100,
        lock:true ,
		content:audit_desc
	});
}

function apply(id) {
	
	var url = "${ctx}/manager/customer/ActivityApply.do?method=chooseActivityCommInfo&activity_id="+id+"&t=" + new Date().getTime();
	$.dialog({
		title:  "选择商品",
		width:  1050,
		height: 500,
        lock:true ,
        zIndex:"10000",
		content:"url:"+url
	});
};

function editActivityCommInfo(id) {
	
	var url = "${ctx}/manager/customer/ActivityApply.do?method=editActivityCommInfo&activity_id="+id+"&t=" + new Date().getTime();
	$.dialog({
		title:  "选择商品",
		width:  1050,
		height: 500,
        lock:true ,
        zIndex:"10000",
		content:"url:"+url
	});
};
function reloadJsp(){
	location.reload()
}
var lastId = 0;
function openInput(id){
	window.open("${ctx}/BigShow.do?method=openBigShowPage&orderDate=2016-1-1&id="+id);      
}
//]]></script>
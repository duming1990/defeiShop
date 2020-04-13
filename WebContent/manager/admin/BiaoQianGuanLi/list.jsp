<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/BiaoQianGuanLi">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="6%" nowrap="nowrap">频道名称：<html-el:text property="tag_name_like" styleId="tag_name_like" style="width:80px;" styleClass="webinput"/>
                &nbsp;&nbsp;是否锁定：
                <html-el:select property="is_lock">
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="0">未锁定</html-el:option>
                  <html-el:option value="1">已锁定</html-el:option>
                </html-el:select>
                &nbsp;&nbsp;是否删除：
                <html-el:select property="is_del">
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="0">未删除</html-el:option>
                  <html-el:option value="1">已删除</html-el:option>
                </html-el:select>
                &nbsp;&nbsp;
                <html-el:submit value="查 询" styleClass="bgButton" /></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="BiaoQianGuanLi.do?method=delete">
    <div style="text-align: left; padding: 5px;">
       <logic-el:match name="popedom" value="+4+"> 
        <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" /> 
       </logic-el:match> 
      <logic-el:match name="popedom" value="+1+"> 
        <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='BiaoQianGuanLi.do?method=add&mod_id=${af.map.mod_id}';" /> 
      </logic-el:match> 
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="7%" nowrap="nowrap"> 
            <c:if test="${fn:contains(popedom, '+4+')}" var="isDelete">
                <input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" />
            </c:if>
        </th>
        <th nowrap="nowrap">频道名称</th>
        <th nowrap="nowrap">频道类型</th>
        <th nowrap="nowrap">频道图片</th>
       <!--  <th nowrap="nowrap">相关类别</th> -->
        <th nowrap="nowrap">添加时间</th>
<!--         <th nowrap="nowrap">排序值</th> -->
        <th nowrap="nowrap">是否锁定</th>
        <th nowrap="nowrap">是否删除</th>
        <c:if test="${fn:contains(popedom, '+2+') or fn:contains(popedom, '+4+')}" var="isContains">
          <th width="15%" nowrap="nowrap">操作</th>
        </c:if>
      </tr>
      <c:forEach var="cur" items="${BaseComminfoTagsList}" varStatus="vs">
        <tr>
          <td align="center" nowrap="nowrap">
          <c:if test="${fn:contains(popedom, '+4+')}" var="isDelete">
              <c:set var="isLock" value="${cur.is_lock eq 1}"></c:set>
              <c:set var="isDel" value="${cur.is_del eq 1}"></c:set>
              <c:if test="${isLock or isDel}">
                <input name="pks" type="checkbox" id="pks" value="${cur.id}" disabled="disabled"/>
              </c:if>
              <c:if test="${not isLock and not isDel}">
                <input name="pks" type="checkbox" id="pks" value="${cur.id}" />
              </c:if>
            </c:if>
           </td>
<!--            <td nowrap="nowrap" align="center"> -->
<%--                 <a title="查看所属商品" href="BiaoQianGuanLi.do?method=view&mod_id=${af.map.mod_id}&id=${cur.id}">${fn:escapeXml(cur.tag_name)}</a> --%>
<!--            </td> -->
          <td nowrap="nowrap" align="center">
          <c:url var="entp_url" value="/m/Index.shtml?tag_id=${cur.id}" />
          <a href="${entp_url}" target="_blank"><i class="fa fa-globe preview"></i></a>
          ${fn:escapeXml(cur.tag_name)}</td>
          <td align="center">${empty cur.map.p_name?'全国':cur.map.p_name}</td>
          <td align="center"><img src="${ctx}/${cur.image_path}" height="100"/></td>
        <%-- <td align="center"><c:out value="${cur.cls_name}"></c:out></td> --%>
          <td align="center"><fmt:formatDate value="${cur.add_date }" pattern="yyyy-MM-dd"/></td>
<%--           <td align="center"><c:out value="${cur.order_value }"></c:out></td> --%>
          <td align="center"><c:choose>
              <c:when test="${cur.is_lock eq 0}"><span style=" color:#060;">未锁定</span></c:when>
              <c:when test="${cur.is_lock eq 1}"><span style=" color:#ccc;">已锁定</span></c:when>
            </c:choose></td>
          <td align="center"><c:choose>
              <c:when test="${cur.is_del eq 0}"><span style=" color:#060;">未删除</span></c:when>
              <c:when test="${cur.is_del eq 1}"><span style=" color:#F00;">已删除</span></c:when>
            </c:choose></td>
          
            <td align="center" nowrap="nowrap">
            <c:if test="${isContains}" >
              <logic-el:match name="popedom" value="+2+">
              <c:if test="${cur.p_index eq 0 && cur.index_lock eq 0}">
              <a class="butbase"  onclick="indexLock(${cur.id},${cur.index_lock})" ><span class="icon-ok">设为默认</span></a>
              </c:if>
               <c:if test="${cur.p_index eq 0 && cur.index_lock eq 1}">
              <a class="butbase"  onclick="indexLock(${cur.id},${cur.index_lock})" ><span class="icon-remove">取消默认</span></a>
              </c:if>
              <a class="butbase"  onclick="xuanze(${cur.id},${af.map.mod_id})" ><span class="icon-ok">选择商品</span></a>
              <a class="butbase" onclick="confirmUpdate(null, 'BiaoQianGuanLi.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><span class="icon-edit">修改</span></a>
              </logic-el:match>
              <logic-el:match name="popedom" value="+4+">
                <c:if test="${isLock or isDel}">
                <a class="butbase but-disabled"><span class="icon-remove">删除</span></a></c:if>
                <c:if test="${not isLock and not isDel}">
               <a class="butbase" onclick="confirmDelete(null, 'BiaoQianGuanLi.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><span class="icon-remove">删除</span></a>
                </c:if>
              </logic-el:match>
              </c:if>
              </td>
          
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="BiaoQianGuanLi.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
        <tr>
          <td height="10" ><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("tag_name_like", "${fn:escapeXml(af.map.tag_name_like)}");
			pager.addHiddenInputs("is_del", "${af.map.is_del}");
			pager.addHiddenInputs("is_lock", "${af.map.is_lock}");
            document.write(pager.toString());
        </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>

<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){

});
	function xuanze(id,mod_id){
			var url = "${ctx}/manager/admin/BiaoQianGuanLi.do?method=chooseCommInfo&biaoqianid="+id+"&mod_id="+mod_id;
			
			$.dialog({
				title:  "选择商品",
				width:  1070,
				height: 750,
				zIndex:80,
		        lock:true ,
				content:"url:"+url
			});
	}
	function refreshPage(){
		window.location.reload();
	}
	
	function indexLock(id,index_lock){
		if(id){
			if(index_lock == 1){
				var index_lock = 0;
			}else{
				var index_lock = 1;
			}
			var submit = function (v, h, f) {
			    if (v == true) {
			    	$.jBox.tip("数据提交中...", 'loading');
			    	window.setTimeout(function () { 
			    	 $.post("?method=indexLock",{id:id,index_loock:index_lock},function(data){
				    	 jBox.tip(data.msg, 'info');
				    	 window.setTimeout(function () {
				    	  if(data.ret == 1){
				    		window.location.reload();
				    	  }
				    	 }, 1000);
					 });
			    	}, 1000);
			    } 
			    return true;
			};
			// 自定义按钮
			$.jBox.confirm("您确定设为默认标签吗吗?", "系统提示", submit, { buttons: { '确定': true, '取消': false} });
		}
	}

</script>
</body>
<jsp:include page="../public_page.jsp" flush="true" />
</html>

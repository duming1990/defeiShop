<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/BaseComminfoTags">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="par_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="6%" nowrap="nowrap">标签名：<html-el:text property="tag_name_like" styleId="tag_name_like" style="width:80px;" styleClass="webinput"/>
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
                           
                 &nbsp;&nbsp;是否显示：
                <html-el:select property="is_show">
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="0">未显示</html-el:option>
                  <html-el:option value="1">已显示</html-el:option>
                </html-el:select>
                
                &nbsp;&nbsp;
                <html-el:submit value="查 询" styleClass="bgButton" /></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="BaseComminfoTags.do?method=delete">
    <div style="text-align: left; padding: 5px;">
        <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" /> 
        <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='BaseComminfoTags.do?method=add&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}';" /> 
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th width="7%" nowrap="nowrap"> 
          <input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" />
        </th>
        <th nowrap="nowrap">标签名</th>
        <th nowrap="nowrap">相关类别</th>
        <th nowrap="nowrap">添加时间</th>
        <th nowrap="nowrap">是否锁定</th>
        <th nowrap="nowrap">是否显示</th>
        <th nowrap="nowrap">是否删除</th>
        <th width="15%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${BaseComminfoTagsList}" varStatus="vs">
        <tr>
          <td align="center" nowrap="nowrap">
              <c:set var="isLock" value="${cur.is_lock eq 1}"></c:set>
              <c:set var="isDel" value="${cur.is_del eq 1}"></c:set>
              <c:if test="${isLock or isDel}">
                <input name="pks" type="checkbox" id="pks" value="${cur.id}" disabled="disabled"/>
              </c:if>       
              <c:if test="${not isLock and not isDel}">
                <input name="pks" type="checkbox" id="pks" value="${cur.id}" />
              </c:if>
           </td>
          <td nowrap="nowrap" align="center">${fn:escapeXml(cur.tag_name)}</td>
          <td align="center"><c:out value="${cur.cls_name}"></c:out></td>
          <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd"/></td>
          <td align="center"><c:choose>
              <c:when test="${cur.is_lock eq 0}"><span style=" color:#060;">未锁定</span></c:when>
              <c:when test="${cur.is_lock eq 1}"><span style=" color:#ccc;">已锁定</span></c:when>
            </c:choose></td>
            
            <td align="center"><c:choose>
              <c:when test="${cur.is_show eq 0}"><span style=" color:#ccc;">未显示</span></c:when>
              <c:when test="${cur.is_show eq 1}"><span style=" color:#060;">已显示</span></c:when>
            </c:choose></td>
            
            
          <td align="center"><c:choose>
              <c:when test="${cur.is_del eq 0}"><span style=" color:#060;">未删除</span></c:when>
              <c:when test="${cur.is_del eq 1}"><span style=" color:#F00;">已删除</span></c:when>
            </c:choose></td>
           <td align="center" nowrap="nowrap">
               <a class="label label-warning label-block" onclick="chooseCommInfo(${cur.id},${af.map.mod_id})">选择商品</a>
               <a class="label label-success label-block" onclick="confirmUpdate(null, 'BaseComminfoTags.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><span class="icon-edit">修改</span></a>
               <c:if test="${isLock or isDel}">
               <a class="tip-default"><span class="label label-default label-block" title="已删除，不能再次删除！">删除</span></a>
               </c:if>
               <c:if test="${not isLock and not isDel}">
                <a class="label label-danger label-block" onclick="confirmDelete(null, 'BaseComminfoTags.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><span class="icon-remove">删除</span></a>
               </c:if>
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
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="BaseComminfoTags.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
        <tr>
          <td height="10" ><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("par_id", "${af.map.par_id}");
            pager.addHiddenInputs("tag_name_like", "${fn:escapeXml(af.map.tag_name_like)}");
			pager.addHiddenInputs("is_del", "${af.map.is_del}");
			pager.addHiddenInputs("is_lock", "${af.map.is_lock}");
			pager.addHiddenInputs("is_show", "${af.map.is_show}");
            document.write(pager.toString());
        </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>

<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>     
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>        
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){

});

function chooseCommInfo(id,mod_id){
	var url = "${ctx}/manager/customer/BaseComminfoTags.do?method=chooseCommInfo&tag_id=" + id + "&mod_id=" + mod_id;
	$.dialog({
		title:  "选择商品",
		width:  1070,
		height: 750,
        lock:true ,
		content:"url:"+url
	});
}
function refreshPage(){
	window.location.reload();
}

</script>
</body>
</html>

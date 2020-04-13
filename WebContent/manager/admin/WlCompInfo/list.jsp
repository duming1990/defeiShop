<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div align="center" class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/WlCompInfo">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="60%" nowrap="nowrap">快递公司名称：
                <html-el:text property="wl_comp_name_like" maxlength="40" size="10" />
                &nbsp;所属区域：
                      <select name="province" id="province" style="width:100px;">
                        <option value="">请选择...</option>
                      </select>
                      <select name="city" id="city" style="width:100px;">
                        <option value="">请选择...</option>
                      </select>
                      <select name="country" id="country" style="width:100px;">
                        <option value="">请选择...</option>
                      </select>
                &nbsp;是否删除：
                <html-el:select property="is_del" styleId="is_del" >
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
  <form id="listForm" name="listForm" method="post" action="WlCompInfo.do?method=delete">
    <div style="padding-bottom:5px;text-align: left;">
      <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" />
      <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='WlCompInfo.do?method=add&pd_class_type=${af.map.pd_class_type}&mod_id=${af.map.mod_id}&' + $('#bottomPageForm').serialize();" />
      <input type="hidden" name="mod_id" id="mod_id" value="${af.map.mod_id}" />
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%" nowrap="nowrap"> 
        <input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" />
        </th>
        <th nowrap="nowrap">快递公司名称</th>
        <th width="15%">所属区域</th>
        <th width="8%">联系人</th>
        <th width="10%">联系电话</th>
        <th width="6%">排序值</th>
        <th width="6%">是否锁定</th>
        <th width="6%">是否删除</th>
        <th width="6%">是否合作</th>
        <th width="15%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
         <c:set var="isLock" value="${cur.is_lock eq 1}"></c:set>
         <c:set var="isDel" value="${cur.is_del eq 1}"></c:set>
          <td align="center">
              <c:if test="${isLock or isDel}">
                <input name="pks" type="checkbox" id="pks" value="${cur.id}" disabled="disabled"/>
              </c:if>
              <c:if test="${not isLock and not isDel}">
                <input name="pks" type="checkbox" id="pks" value="${cur.id}" />
              </c:if>
              </td>
          <td align="left">
          <a href="WlCompInfo.do?method=view&id=${cur.id}&mod_id=${af.map.mod_id}">${fn:escapeXml(fnx:abbreviate(cur.wl_comp_name, 45, '...'))}
                  <c:if test="${cur.comp_type eq 0}">
          [${fn:escapeXml(cur.wl_comp_en_name)}]
          </c:if>
          </a>
          </td>
          <td align="left">${cur.map.full_name}</td>
          <td align="center">${cur.link_man}</td>
          <td align="center">${cur.tel}</td>
          <td align="center"><c:out value="${cur.order_value}"></c:out></td>
          <td align="center"><c:choose>
              <c:when test="${cur.is_lock eq 0}"><span style=" color:#060;">未锁定</span></c:when>
              <c:when test="${cur.is_lock eq 1}"><span style=" color:#ccc;">已锁定</span></c:when>
            </c:choose></td>
          <td align="center"><c:choose>
              <c:when test="${cur.is_del eq 0}"><span style=" color:#060;">未删除</span></c:when>
              <c:when test="${cur.is_del eq 1}"><span style=" color:#F00;">已删除</span></c:when>
            </c:choose></td>
          <td align="center"><c:choose>
              <c:when test="${cur.is_collaborate eq 1}"><span style=" color:#060;">已合作</span></c:when>
              <c:when test="${cur.is_collaborate eq 0}"><span style=" color:#F00;">未合作</span></c:when>
            </c:choose></td>
          <td nowrap="nowrap">
           <c:if test="${not isLock and not isDel}">
          <span style="cursor: pointer;" onclick="confirmUpdate(null, 'WlCompInfo.do', 'id=${cur.id}&mod_id=${af.map.mod_id}&' + $('#bottomPageForm').serialize())"><img src="${ctx}/styles/images/xiugai.gif" width="55" height="18" /></span> 
          <span style="cursor: pointer;" onclick="confirmDelete(null, 'WlCompInfo.do', 'id=${cur.id}&mod_id=${af.map.mod_id}&' + $('#bottomPageForm').serialize())"> <img src="${ctx}/styles/images/shanchu.gif" width="55" height="18" /></span> </c:if>
            <c:if test="${isLock or isDel}">
            <span style="cursor: pointer;" title="已锁定或已删除，不能再修改"><img src="${ctx}/styles/images/xiugai_2.gif" width="55" height="18" /></span> 
            <span style="cursor: pointer;" title="已锁定或已删除，不能再删除"><img src="${ctx}/styles/images/shanchu_2.gif" width="55" height="18" /></span> </c:if></td>
        </tr>
         <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
      </c:forEach>
       <c:forEach begin="${i}" end="${af.map.pager.pageSize - 1}">
        <tr align="center">
          <td>&nbsp;</td>
          <td nowrap="nowrap">&nbsp;</td>
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
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="WlCompInfo.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "list");
					pager.addHiddenInputs("id", "${af.map.id}");
					pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
					pager.addHiddenInputs("wl_comp_name_like", "${af.map.wl_comp_name_like}");
					pager.addHiddenInputs("province", "${af.map.province}");
					pager.addHiddenInputs("city", "${af.map.city}");
					pager.addHiddenInputs("country", "${af.map.country}");
					pager.addHiddenInputs("is_del", "${af.map.is_del}");
					pager.addHiddenInputs("is_lock", "${af.map.is_lock}");
					document.write(pager.toString());
	            	</script></td>
        </tr>
      </table>
    </form>
  </div>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/cs.js"></script>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#province").citySelect({
        data:getAreaDic(),
        province:"${af.map.province}",
        city:"${af.map.city}",
        country:"${af.map.country}",
        province_required : false,
        city_required : false,
        country_required : false,
        callback:function(selectValue,selectText){
        	if(null != selectValue && "" != selectValue){
        		var p_indexs = selectValue.split(",");
        		if(null != p_indexs && p_indexs.length > 0){
        			$("#p_index").val(p_indexs[p_indexs.length - 1]);
        		}
        	}
        }
 });
	$("#province").change();
});
//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>

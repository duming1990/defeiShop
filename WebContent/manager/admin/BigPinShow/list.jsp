<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
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
  <html-el:form action="/admin/BigPinShow" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="5%" nowrap="nowrap">
                <div style="margin-top:5px;" id="city_div">
                &nbsp;所在地区：
                 <html-el:select property="province" styleId="province" style="width:120px;" styleClass="pi_prov webinput">
             	 <html-el:option value="">请选择...</html-el:option>
           		 </html-el:select>
          		&nbsp;
         		 <html-el:select property="city" styleId="city" style="width:120px;" styleClass="pi_city webinput">
              	 <html-el:option value="">请选择...</html-el:option>
           		 </html-el:select>
         		&nbsp;
            	 <html-el:select property="country" styleId="country" style="width:120px;" styleClass="pi_dist webinput">
              	 <html-el:option value="">请选择...</html-el:option>
            	 </html-el:select>
                &nbsp;
                <html-el:submit value="查 询" styleClass="bgButton" />
                </div>
                </td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="BigPinShow.do?method=delete">
    <div style="padding-bottom:5px;">
      <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" />
      <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='BigPinShow.do?method=add&mod_id=${af.map.mod_id}';" />
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%" nowrap="nowrap"><input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /></th>
        <th nowrap="nowrap">县名</th>
          <th width="20%">大屏显示标题</th>
        <th width="35%">大屏显示图片</th>
        <th width="9%">添加时间</th>
        <th width="8%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
	       <td align="center"><input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}"/></td>
	       <td>
	       <c:url var="url" value="/BigShow.do?&id=${cur.id}" />
           <a href="${url}" target="_blank"><i class="fa fa-globe preview"></i></a>   
	       ${fn:escapeXml(cur.map.fullname)}</td>
	       <td align="center">${cur.file_name}</td>
           <td align="center">
            <c:if test="${not empty cur.save_path}">
          	 <c:set var="img" value="${ctx}/${cur.save_path}" />
        	</c:if>
		 	<img src="${img}" height="100"/>
		   </td>
	       <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd" /></td>
           <td align="center" nowrap="nowrap">
      			<a class="butbase" href="javascript:void(0);"><span class="icon-edit" onclick="confirmUpdate(null, 'BigPinShow.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize());">修改</span></a>
      			<a class="butbase" href="javascript:void(0);"><span class="icon-remove" onclick="confirmDelete(null, 'BigPinShow.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize());">删除</span></a>
          </td>
        </tr>
    </c:forEach>
   </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="BigPinShow.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
			pager.addHiddenInputs("province", "${fn:escapeXml(af.map.province)}");
			pager.addHiddenInputs("city", "${fn:escapeXml(af.map.city)}");
			pager.addHiddenInputs("country", "${fn:escapeXml(af.map.country)}");
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
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#city_div").citySelect({
        data:getAreaDic(),
        province:"${af.map.province}",
        city:"${af.map.city}",
        country:"${af.map.country}",
        required:false
    });
	$(".qtip").quicktip();
});


//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>

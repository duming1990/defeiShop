<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/ClassRanking" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
     <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td>
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
            
            
            &nbsp;&nbsp;&nbsp;总额为0的是否显示：
            <html-el:select property="sum_money" styleId="sum_money">
              <html-el:option value="0">显示</html-el:option>
              <html-el:option value="1">不显示</html-el:option>
            </html-el:select>
            &nbsp;&nbsp;&nbsp;累计交易数为0的是否显示：
            <html-el:select property="cls_num" styleId="cls_num">
              <html-el:option value="0">显示</html-el:option>
              <html-el:option value="1">不显示</html-el:option>
            </html-el:select>
            &nbsp;
           <html-el:submit value="查 询" styleClass="bgButton" styleId="bgButton" /> 
           </div>
          </td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
   <form id="listForm" name="listForm" method="post">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr class="tite2">
        <th width="5%" nowrap="nowrap">排名</th>
        <th nowrap="nowrap">商品类别</th>
        <th nowrap="nowrap">累计交易数</th>
        <th width="nowrap">累计交易额</th>
        <th width="nowrap">商品所属地区</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
      		<tr align="center">
          <td align="center">${vs.count}</td>
          <td align="center">${cur.map.cls_name}</td>
          <td align="center">${cur.map.cls_num}</td>
          <td align="center">${cur.map.sum_money}</td>
          <td align="center">${cur.map.full_name}</td>
        </tr>
      </c:forEach>
      </table>
      </form>
      
      
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="ClassRanking.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
           <script type="text/javascript">
			 var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
			 pager.addHiddenInputs("method", "list");
			 pager.addHiddenInputs("sum_money","${fn:escapeXml(af.map.sum_money)}");
			 pager.addHiddenInputs("cls_num","${fn:escapeXml(af.map.cls_num)}");
			 pager.addHiddenInputs("province", "${fn:escapeXml(af.map.province)}");
			 pager.addHiddenInputs("city", "${fn:escapeXml(af.map.city)}");
			 pager.addHiddenInputs("country", "${fn:escapeXml(af.map.country)}");
			 document.write(pager.toString());
	       </script></td>
        </tr>
      </table>
    </form>
  </div>
  <div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#city_div").citySelect({
        data:getAreaDic(),
        province:"${af.map.province}",
        city:"${af.map.city}",
        country:"${af.map.country}",
        required:false
    });
})
</script>
</body>
</html>

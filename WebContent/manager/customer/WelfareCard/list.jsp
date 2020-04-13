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
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td>
          <div style="margin-top: 5px;">生成批次：
            <html-el:text property="gen_no_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
            &nbsp;&nbsp;卡类型：
            <html-el:select property="card_type">
	            <html-el:option value="">请选择</html-el:option>
	            <html-el:option value="10">实体卡</html-el:option>
	            <html-el:option value="20">电子卡</html-el:option>
            </html-el:select>
            &nbsp;&nbsp;状态：
            <html-el:select property="card_state">
	            <html-el:option value="">请选择</html-el:option>
	            <html-el:option value="-1">作废</html-el:option>
	            <html-el:option value="0">新卡</html-el:option>
	            <html-el:option value="1">部分分配</html-el:option>
	            <html-el:option value="2">全部分配</html-el:option>
            </html-el:select>
             &nbsp;&nbsp;是否停用：
            <html-el:select property="is_del">
	            <html-el:option value="">请选择</html-el:option>
	            <html-el:option value="1">已停用</html-el:option>
	            <html-el:option value="0">未停用</html-el:option>
            </html-el:select>
              &nbsp;&nbsp;生成时间
                            从:
          <html-el:text property="st_add_date" styleId="st_add_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
         	 至：
          <html-el:text property="en_add_date" styleId="en_add_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
            &nbsp;&nbsp;<button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button>
          </div></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="WelfareCard.do?method=delete">
    <div style="text-align: left;padding:5px;"></div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr class="tite2">
        <th width="5%">序号</th>
        <th width="15%">生成批次号</th>
        <th width="8%">卡类型</th>
        <th width="8%">额度</th>
        <th width="8%">生成数量</th>
        <th width="8%">已使用数量</th>
        <th width="8%">状态</th>
        <th width="10%">生成时间</th>
        <th width="10%">是否停用</th>
        <th width="10%">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          <td>${vs.count}</td>
          <td>${fn:escapeXml(cur.gen_no)}</td>
          <td>
	          <c:if test="${cur.card_type eq 10}">实体卡</c:if>
	          <c:if test="${cur.card_type eq 20}">电子卡</c:if>
          </td>
          <td><fmt:parseNumber value="${cur.card_amount}" pattern="0.##"></fmt:parseNumber></td>
          <td>${fn:escapeXml(cur.gen_count)}</td>
          <td>${fn:escapeXml(cur.used_count)}</td>
          <td>
	          <c:if test="${cur.info_state eq -1}">作废</c:if>
	          <c:if test="${cur.info_state eq 0}">新卡</c:if>
	          <c:if test="${cur.info_state eq 1}">部分分配</c:if>
	          <c:if test="${cur.info_state eq 2}">全部分配</c:if>
          </td>
          <td><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd" /></td>
          <td>
	          <c:if test="${cur.is_del eq 1}"><span style="color: red;">已停用</span></c:if>
	          <c:if test="${cur.is_del eq 0}"><span style="color: green;">未停用</span></c:if>
          </td>
          <td>
          	<a class="butbase" onclick="getCardList(${cur.id},${cur.card_type});"><span class="icon-search">管理</span></a> 
          	<c:if test="${cur.card_type eq 20}">
          		<a class="butbase" href="${ctx}/manager/customer/WelfareCard.do?method=sendMsgLot&gen_id=${cur.id}&mod_id=${af.map.mod_id}"><span class="icon-ok">批量发卡</span></a>
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
          <td>&nbsp;</td>
          <td>&nbsp;</td>
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
					pager.addHiddenInputs("method", "list");
					pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
					pager.addHiddenInputs("gen_no_like", "${fn:escapeXml(af.map.gen_no_like)}");
					pager.addHiddenInputs("st_add_date", "${fn:escapeXml(af.map.st_add_date)}");
					pager.addHiddenInputs("en_add_date", "${fn:escapeXml(af.map.en_add_date)}");
					pager.addHiddenInputs("card_type", "${fn:escapeXml(af.map.card_type)}");
					pager.addHiddenInputs("card_state", "${fn:escapeXml(af.map.card_state)}");
					pager.addHiddenInputs("is_del", "${fn:escapeXml(af.map.is_del)}");
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
                                          
function getCardList(id,card_type){
	var url="${ctx}/manager/customer/WelfareCard.do?method=cardList&mod_id=${af.map.mod_id}&gen_id="+id+"&card_type="+card_type;
	$.dialog({
		title:  "福利卡信息",
		width: "80%",
		height: "90%",
        lock:true ,
        zIndex: 999,
		content:"url:"+url
	});
}

//]]></script>
</body>
</html>

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
<div style="width: 99%" class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/EntpInfoAudit" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td><div> 商家名：
            <html-el:text property="entp_name_like" maxlength="40" style="width:150px;" styleClass="webinput" />
            &nbsp;用户名：
            <html-el:text property="user_name" maxlength="40" style="width:100px;" styleClass="webinput" />
            &nbsp;审核状态：
            <html-el:select property="audit_state" styleClass="webinput" >
              <html-el:option value="">全部</html-el:option>
              <html-el:option value="-2">审核不通过</html-el:option>
              <html-el:option value="0">待审核</html-el:option>
              <html-el:option value="2">审核通过</html-el:option>
            </html-el:select>
          </div>
          <div style="margin-top:5px;">
           	是否删除：
            <html-el:select property="is_del" styleClass="webinput" >
              <html-el:option value="">全部</html-el:option>
              <html-el:option value="0">未删除</html-el:option>
              <html-el:option value="1">已删除</html-el:option>
            </html-el:select>
            &nbsp;商家编号：
            <html-el:text property="entp_no" maxlength="40" style="width:100px;" styleClass="webinput" />
          &nbsp;添加时间：
            从
            <html-el:text property="add_date_st" styleClass="webinput"  styleId="add_date_st" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
            至
            <html-el:text property="add_date_en" styleClass="webinput"  styleId="add_date_en" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
            &nbsp;审核时间：
            从
            <html-el:text property="audit_date_st" styleClass="webinput"  styleId="audit_date_st" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
            至
            <html-el:text property="audit_date_en" styleClass="webinput"  styleId="audit_date_en" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
          </div>
          <div style="margin-top:5px;" id="city_div">所在地区：
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
            &nbsp;商家类型：
            <html-el:select property="entp_type" styleClass="webinput" >
              <html-el:option value="">全部</html-el:option>
              <c:forEach var="cur" items="${entpTypeList}">
               <html-el:option value="${cur.index}">${cur.name}</html-el:option>
              </c:forEach>
            </html-el:select>
            &nbsp;
            <html-el:submit value="查 询" styleClass="bgButton"  />
			&nbsp;<input id="download" type="button" value="导出" class="bgButton" />
          </div></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="EntpInfoAudit.do?method=delete">
<!--     <div style="padding-bottom:5px;"> -->
<%--       <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='EntpInfoAudit.do?method=add&mod_id=${af.map.mod_id}';" /> --%>
<!--     </div> -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%"> <input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" />
        </th>
        <th width="14%">店铺名称</th>
        <th width="8%">商家编号</th>
        <th width="8%">商家类型</th>
        <th width="8%">用户名</th>
        <th width="6%">姓名</th>
        <th width="6%">联系电话</th>
        <th width="10%">所属地区</th>
        <th width="7%">添加时间</th>
        <th width="7%">审核时间</th>
        <th width="7%">是否使用店铺模板</th>
        <th width="8%">是否审核</th>
        <th width="10%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}">
        <tr>
          <td align="center"><c:if test="${(cur.is_del eq 1) or (cur.audit_state eq 1)}" var="isLock">
              <input name="pks" type="checkbox" id="pks" value="${cur.id}" disabled="disabled"/>
            </c:if>
            <c:if test="${(cur.is_del ne 1) and (cur.audit_state ne 1)}">
              <input name="pks" type="checkbox" id="pks" value="${cur.id}" />
            </c:if></td>
          <td align="left">
            <c:if test="${cur.audit_state eq 2}">
              <c:url var="entp_url" value="/entp/IndexEntpInfo.do?entp_id=${cur.id}" />
              <a href="${entp_url}" target="_blank"><i class="fa fa-globe preview"></i></a>
             </c:if>
            <a title="查看" href="EntpInfoAudit.do?method=view&amp;mod_id=${af.map.mod_id}&amp;id=${cur.id}">${fn:escapeXml(cur.entp_name)}</a></td>
          <td align="center">
            <c:if test="${not empty cur.entp_no}"> ${fn:escapeXml(cur.entp_no)} </c:if>
            <c:if test="${empty cur.entp_no}">---</c:if>
          </td>
          <td align="center">
           <c:forEach var="curSon" items="${entpTypeList}">
            <c:if test="${curSon.index eq cur.entp_type}">${curSon.name}</c:if>
           </c:forEach>
          </td>
          <td align="center"><c:if test="${not empty cur.map.userInfoTemp.user_name}"> ${fn:escapeXml(cur.map.userInfoTemp.user_name)}</c:if>
            <c:if test="${empty cur.map.userInfoTemp.user_name}">---</c:if>
          </td>
          <td align="center">${fn:escapeXml(cur.entp_linkman)}</td>
          <td align="center"><c:if test="${not empty cur.entp_tel}"> ${fn:escapeXml(cur.entp_tel)}</c:if>
            <c:if test="${empty cur.entp_tel}">---</c:if>
          </td>
           <td align="left">${fn:escapeXml(cur.map.full_name)}</td>
          <fmt:formatDate var="add_date" value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm:ss" />
          <td align="center" title="添加时间：${add_date}"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd" /></td>
          <fmt:formatDate var="audit_date" value="${cur.audit_date}" pattern="yyyy-MM-dd HH:mm:ss" />
          <td align="center" title="最后审核时间：${audit_date}"><fmt:formatDate value="${cur.audit_date}" pattern="yyyy-MM-dd" /></td>
          <td align ="center">
          	<c:if test="${cur.is_entpstyle  eq 0}"><span class="label label-default">未使用模板</span></c:if>
          	<c:if test="${cur.is_entpstyle  eq 1}"><span class="label label-success">已使用模板</span></c:if>
          </td>
          <td align="center">
            <c:choose>
              <c:when test="${cur.audit_state eq -2}"><span class="label label-danger">审核不通过</span></c:when>
              <c:when test="${cur.audit_state eq 0}"><span class="label label-default">待审核</span></c:when>
              <c:when test="${cur.audit_state eq 2}"><span class="label label-success">审核通过</span></c:when>
            </c:choose>
          </td>
          <td align="center" nowrap="nowrap"><logic-el:match name="popedom" value="+8+"> <a class="butbase"><span onclick="doNeedMethod(null, 'EntpInfoAudit.do', 'audit','id=${cur.id}&mod_id=${af.map.mod_id}&'+$('#bottomPageForm').serialize())" class="icon-ok">审核</span></a> </logic-el:match>
            <logic-el:notMatch name="popedom" value="+8+"> <a class="butbase but-disabled"><span class="icon-ok">审核</span></a> </logic-el:notMatch>
            <logic-el:match name="popedom" value="+2+">
              <c:if test="${cur.audit_state ne 2}" var="flag_edit"> <a class="butbase"><span onclick="confirmUpdate(null, 'EntpInfoAudit.do', 'id=${cur.id}&mod_id=${af.map.mod_id}&entp_type=${cur.entp_type}&' + $('#bottomPageForm').serialize())" class="icon-edit">修改</span></a> </c:if>
              <c:if test="${!flag_edit}"><a class="butbase but-disabled" title="已审核，不能再修改"><span class="icon-edit">修改</span></a> </c:if>
            </logic-el:match>
            <logic-el:notMatch name="popedom" value="+2+"> <a class="butbase but-disabled"><span class="icon-edit">修改</span></a> </logic-el:notMatch>
           <logic-el:match name="popedom" value="+8+">
              <c:if test="${cur.audit_state eq 2}"> <span id="cancel_${cur.id}"> <a class="butbase"><span onclick="cancelEntp(${cur.id})" class="icon-cancel">取消</span></a></span> </c:if>
              <c:if test="${cur.audit_state ne 2}"> <a class="butbase but-disabled"><span title="未审核通过，不能取消" class="icon-cancel">取消</span></a> </c:if>
            </logic-el:match>
            <logic-el:notMatch name="popedom" value="+8+">
              <a class="butbase but-disabled"><span class="icon-cancel">取消</span></a>
            </logic-el:notMatch>
          </td>
        </tr>
    </c:forEach>
   </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="EntpInfoAudit.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("entp_name_like", "${fn:escapeXml(af.map.entp_name_like)}");
            pager.addHiddenInputs("add_user_name_like", "${fn:escapeXml(af.map.add_user_name_like)}");
			pager.addHiddenInputs("is_del", "${af.map.is_del}");
            
			pager.addHiddenInputs("add_date_st", "${af.map.add_date_st}");
			pager.addHiddenInputs("add_date_en", "${af.map.add_date_en}");
            pager.addHiddenInputs("update_date_st", "${af.map.update_date_st}");
			pager.addHiddenInputs("update_date_en", "${af.map.update_date_en}");
            pager.addHiddenInputs("audit_date_st", "${af.map.audit_date_st}");
			pager.addHiddenInputs("audit_date_en", "${af.map.audit_date_en}");
            
			pager.addHiddenInputs("audit_state", "${af.map.audit_state}");
			pager.addHiddenInputs("entp_type", "${af.map.entp_type}");
			pager.addHiddenInputs("province", "${fn:escapeXml(af.map.province)}");
			pager.addHiddenInputs("city", "${fn:escapeXml(af.map.city)}");
			pager.addHiddenInputs("country", "${fn:escapeXml(af.map.country)}");
			pager.addHiddenInputs("is_show", "${fn:escapeXml(af.map.is_show)}");
			pager.addHiddenInputs("update_date_eq", "${af.map.update_date_eq}");
			pager.addHiddenInputs("entp_no", "${af.map.entp_no}");
            document.write(pager.toString());
            </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>

<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
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
});

$("#download").click(function(){
	
	var submit = function (v, h, f) {
	    if (v == true) {
	    	location.href = "${ctx}/manager/admin/EntpInfoAudit.do?method=toExcel&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
	    } else {
	    	location.href = "${ctx}/manager/admin/EntpInfoAudit.do?method=toExcel&code=GBK&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
	    }
	    return true;
	};
	var tip = "确认导出EXCEL格式数据？如果UTF-8编码格式乱码，请选择GBK编码格式下载！";
	$.jBox.confirm(tip, "系统提示", submit, { buttons: { '下载(UTF-8编码)': true, '下载(GBK编码)': false} });
});

function cancelEntp(entp_id){
	if(entp_id){
		var submit = function (v, h, f) {
		    if (v == true) {
		    	$.jBox.tip("数据提交中...", 'loading');
				window.setTimeout(function () {
		    	  $.post("?method=updateEntpInfoForCancel",{entp_id:entp_id},function(data){
		    		  jBox.tip(data.msg, 'info');
		    		  if(data.ret == 1){
				    		 $("#cancel_"+entp_id).html(' <a class="butbase but-disabled"><span title="未审核通过，不能取消" class="icon-cancel">取消</span></a>');
				    		   location.reload();
				    	 }
					});
				}, 1000);
		    }		  
		    return true;
		};
		// 自定义按钮
		$.jBox.confirm("你确定要取消吗？", "系统提示", submit, { buttons: { '确定': true, '取消': false} });
	}
}




function updateAd(id){
	
	var submit = function(v, h, f) {
		var state;
		if (v == true) {
			state = 0;
		}else{
			state = 1;
		}
		
		$.jBox.tip("正在操作...", 'loading');
		
		$.ajax({
			type: "POST" , 
			url: "EntpInfoAudit.do" , 
			data:"method=updateAd&id="+ id +"&is_open_ad="+ state + "&t=" + new Date(),
			dataType: "json", 
	        async: true, 
	        error: function (request, settings) {}, 
	        success: function (data) {
				if (data.code == 1) {
					$.jBox.tip("操作成功", "success",{timeout:1000});
				} else {
					$.jBox.tip(data.msg, "error",{timeout:1000});
				}
				window.setTimeout(function(){
					refreshPage();	
				},2000);
				
	        }
		});
		
		
	};
	$.jBox.confirm("是否开通广告权限？", "系统提示", submit,{ buttons: { '开通': true, '暂不开通': false} });
}


function updateShop(id){
	
	var submit = function(v, h, f) {
		var state;
		if (v == true) {
			state = 0;
		}else{
			state = 1;
		}
		
		$.jBox.tip("正在操作...", 'loading');
		
		$.ajax({
			type: "POST" , 
			url: "EntpInfoAudit.do" , 
			data:"method=updateShop&id="+ id +"&is_has_open_online_shop="+ state + "&t=" + new Date(),
			dataType: "json", 
	        async: true, 
	        error: function (request, settings) {}, 
	        success: function (data) {
				if (data.code == 1) {
					$.jBox.tip("操作成功", "success",{timeout:1000});
				} else {
					$.jBox.tip(data.msg, "error",{timeout:1000});
				}
				window.setTimeout(function(){
					refreshPage();	
				},2000);
				
	        }
		});
		
		
	};
	$.jBox.confirm("是否开通网上店铺？", "系统提示", submit,{ buttons: { '开通': true, '暂不开通': false} }); 
}


function refreshPage(){
	window.location.reload();
}


function coupons(id,is_coupons){
	if(is_coupons == 1){
		var is_coupons = 0;
	}else{
		var is_coupons = 1;
	}
	 var submit = function(v, h, f) {
		if (v == "ok") {
			$.jBox.tip("修改中！", 'loading');
			window.setTimeout(function () {
			 $.ajax({
					type: "POST" , 
					url: "${ctx}/manager/admin/EntpInfoAudit.do" , 
					data:"method=coupons&entp_id="+id+"&is_coupons="+is_coupons,
					dataType: "json", 
			        async: true, 
			        error: function (request, settings) {alert(" 数据加载请求失败！ ");}, 
			        success: function (data) {
						if (data.code == 0) {
							$.jBox.tip(data.msg, 'error');
						} else {
							$.jBox.tip("修改成功！", 'success');
							 window.setTimeout(function () {
							window.location.reload(); 
							}, 1000);
						}
			        }
				});
			}, 1000);
		}
	};
	$.jBox.confirm("您确定要修改吗?", "确定提示", submit);
}

//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>

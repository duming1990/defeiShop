<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/UserInfo" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="5%" nowrap="nowrap">
              <div>
              	登录名：
                <html-el:text property="par_user_name_like" maxlength="20" styleId="par_user_name_like" style="width:80px;" styleClass="webinput" />
              	 &nbsp;真实姓名：
                <html-el:text property="real_name_like" maxlength="20" styleId="real_name_like" style="width:80px;" styleClass="webinput" />
                &nbsp;手机号：
                <html-el:text property="mobile" maxlength="20" styleId="mobile" style="width:80px;" styleClass="webinput" />
                &nbsp;用户类型：
                <html-el:select property="user_type" styleClass="webinput">
                  <html-el:option value="">请选择...</html-el:option>
                  <c:forEach items="${baseData10List}" var="cur">
                    <html-el:option value="${cur.id}">${fn:escapeXml(cur.type_name)}</html-el:option>
                  </c:forEach>
                </html-el:select>
                &nbsp;时间从：
                <html-el:text property="st_add_date" styleId="st_add_date" styleClass="webinput" size="9" maxlength="9" readonly="true" onclick="WdatePicker();" />
                - <html-el:text property="en_add_date" styleId="en_add_date" styleClass="webinput" size="9" maxlength="9" readonly="true" onclick="WdatePicker();" />
   				<html-el:submit value="查 询" styleClass="bgButton" />
                 &nbsp;&nbsp;
                <input id="download" type="button" value="导出" class="bgButton" />
                </div>
                </td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="UserInfo.do?method=delete">
    <div style="padding-bottom:5px;">
    <logic-el:match name="popedom" value="+4+">
      <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" />
    </logic-el:match>
    <logic-el:match name="popedom" value="+1+">
      <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='UserInfo.do?method=add&mod_id=${af.map.mod_id}';" />
    </logic-el:match>
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%" nowrap="nowrap"><input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /></th>
        <th nowrap="20%">登录名</th>
        <th width="15%">手机</th>
        <th width="15%">用户类型</th>
        <th width="15%">添加时间</th>
        <c:if test="${userInfo.user_type eq 1}">
        <th width="15%" nowrap="nowrap">初始化密码</th>
        </c:if>
        <th width="15%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center"><c:set var="isLock" value="${cur.is_lock eq 1}"></c:set>
            <c:set var="isDel" value="${cur.is_del eq 1}"></c:set>
            <logic-el:match name="popedom" value="+4+">
            	<c:choose>
            	<c:when test="${cur.user_type eq 1}">
            		<input name="pks" type="checkbox" id="pks" value="${cur.id}" disabled="disabled"/>
            	</c:when>
            	<c:otherwise>
            		<c:if test="${isLock or isDel}">
		              <input name="pks" type="checkbox" id="pks" value="${cur.id}" disabled="disabled"/>
		            </c:if>
		            <c:if test="${not isLock and not isDel}">
		              <input name="pks" type="checkbox" id="pks" value="${cur.id}" />
		            </c:if>
            	</c:otherwise>
              </c:choose>
            </logic-el:match>
            <logic-el:notMatch name="popedom" value="+4+">
            	${vs.count}
            </logic-el:notMatch>
          </td>
          <td align="center">
          <c:if test="${cur.user_type eq 19}">
             <c:url var="url" value="/m/IndexMTsg.do?method=index&link_id=${cur.id}" />
             <a href="${url}" target="_blank"><i class="fa fa-globe preview"></i></a>
          </c:if>
          <a title="查看" href="UserInfo.do?method=view&amp;mod_id=${af.map.mod_id}&amp;id=${cur.id}">${fn:escapeXml(cur.user_name)}</a>
          <c:if test="${cur.user_level ne 202}">
          	<c:set var="img" value="${ctx}/styles/images/fufei_no.png"/>
          	<c:set var="tishi" value="非付费会员"/>
          </c:if>
          <c:if test="${cur.user_level eq 202}">
          	<c:set var="img" value="${ctx}/styles/images/fufei.png" />
          	 <fmt:formatDate value="${cur.card_end_date}" pattern="yyyy-MM-dd" var="date_card_end_date"/>
          	<c:set var="tishi" value="付费会员,到期时间：${date_card_end_date}"/>
          </c:if>
          
          <img src="${img}" title="${tishi}" class="fapiao"/>
          <div class="qtip" title="真实姓名：${fn:escapeXml(cur.real_name)}">(${fn:escapeXml(cur.real_name)})</div>
          </td>
          <td align="center">${cur.mobile}</td>
          <td align="center">
          <c:set var="type_name" value="超级管理员" />
            <c:forEach items="${baseData10List}" var="curBaseDate">
              <c:if test="${curBaseDate.id eq cur.user_type}">
                <c:set var="type_name" value="${fn:escapeXml(curBaseDate.type_name)}" />
              </c:if>
            </c:forEach>
            <c:if test="${type_name eq '注册会员'}">
              <c:if test="${cur.is_active eq 0}"> ${type_name}<span style="cursor: pointer;color: red;" onclick="confirmNeedMethod('确定要激活吗？', 'UserInfo.do', 'active', 'id=${cur.id}&' + $('#bottomPageForm').serialize())">(激活)</span> </c:if>
              <c:if test="${cur.is_active eq 1}"> ${type_name} </c:if>
            </c:if>
            <c:if test="${type_name ne '注册会员'}"> ${type_name} </c:if></td>
          <td align="center">
          <fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd" />
          <div>
          <fmt:formatDate value="${cur.last_login_time}" pattern="yyyy-MM-dd" var="date_last_login_time"/>
          <fmt:formatDate value="${cur.last_login_time}" pattern="yyyy-MM-dd HH:mm:ss" var="date_last_login_time_detail"/>
          <c:if test="${not empty date_last_login_time}">
          <span class="tip-danger qtip" title="最后登录时间：${date_last_login_time_detail}"><i class="fa fa-clock-o"></i> ${date_last_login_time}</span>
          </c:if>
          </div>
          </td>
          <c:if test="${userInfo.user_type eq 1}">
          <td nowrap="nowrap" align="center">
          <a class="butbase" href="javascript:void(0);"><span class="icon-lock" onclick="initPassword(${cur.id});">初始化密码</span></a><br/>
            <a class="butbase" href="javascript:void(0);"><span class="icon-ok" onclick="unlock(${cur.id});">一键解锁</span></a><br/>
          </td>
          </c:if>
          <td align="center" nowrap="nowrap">
          	<logic-el:match name="popedom" value="+2+">
          		<c:if test="${cur.user_type eq 1}" var="not_edit">
          			<a class="butbase but-disabled" href="javascript:void(0);"><span class="icon-edit">修改</span></a>
          		</c:if>
          		<c:if test="${!not_edit}">
          			<a class="butbase" href="javascript:void(0);"><span class="icon-edit" onclick="confirmUpdate(null, 'UserInfo.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize());">修改</span></a>
          		</c:if>
          	</logic-el:match>
          	<logic-el:match name="popedom" value="+4+">
          		<c:if test="${not isLock and not isDel and cur.user_type ne 1}" var="not_del">
          			<a class="butbase" href="javascript:void(0);"><span class="icon-remove" onclick="confirmDelete(null, 'UserInfo.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize());">删除</span></a>
          		</c:if>
          		<c:if test="${!not_del}">
          			<a class="butbase but-disabled" href="javascript:void(0);"><span class="icon-remove">删除</span></a>
          		</c:if>
          	</logic-el:match>
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
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="UserInfo.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("user_name_like", "${fn:escapeXml(af.map.user_name_like)}");
            pager.addHiddenInputs("par_user_name_like", "${fn:escapeXml(af.map.par_user_name_like)}");
            pager.addHiddenInputs("mobile", "${fn:escapeXml(af.map.mobile)}");
            pager.addHiddenInputs("st_add_date", "${af.map.st_add_date}");
            pager.addHiddenInputs("en_add_date", "${af.map.en_add_date}");
			pager.addHiddenInputs("is_del", "${af.map.is_del}");
			pager.addHiddenInputs("is_entp", "${af.map.is_entp}");
			pager.addHiddenInputs("is_lianmeng", "${af.map.is_lianmeng}");
			pager.addHiddenInputs("is_fuwu", "${af.map.is_fuwu}");
			pager.addHiddenInputs("is_village", "${af.map.is_village}");
			pager.addHiddenInputs("is_poor", "${af.map.is_poor}");
			pager.addHiddenInputs("is_city_manager", "${af.map.is_city_manager}");
			pager.addHiddenInputs("is_daqu", "${af.map.is_daqu}");
			pager.addHiddenInputs("user_type", "${af.map.user_type}");
			pager.addHiddenInputs("user_level", "${af.map.user_level}");
			pager.addHiddenInputs("sex", "${af.map.sex}");
			pager.addHiddenInputs("province", "${fn:escapeXml(af.map.province)}");
			pager.addHiddenInputs("city", "${fn:escapeXml(af.map.city)}");
			pager.addHiddenInputs("country", "${fn:escapeXml(af.map.country)}");
			pager.addHiddenInputs("real_name_like", "${fn:escapeXml(af.map.real_name_like)}");
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
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
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
	
	$("a.viewDetail").colorbox({width:"100%", height:"100%", iframe:true});
});

function villageInfo(id){
	var url = "${ctx}/manager/admin/UserInfo.do?method=villageInfo&id=" + id;
	$.dialog({
		title:  "村站信息",
		width:  1000,
		height: 700,
		padding: 0,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url)
	});
}

$("#download").click(function(){
	
	var submit = function (v, h, f) {
	    if (v == true) {
	    	location.href = "${ctx}/manager/admin/UserInfo.do?method=toExcel&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
	    } else {
	    	location.href = "${ctx}/manager/admin/UserInfo.do?method=toExcel&code=GBK&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
	    }
	    return true;
	};
	var tip = "确认导出EXCEL格式数据？如果UTF-8编码格式乱码，请选择GBK编码格式下载！";
	$.jBox.confirm(tip, "系统提示", submit, { buttons: { '下载(UTF-8编码)': true, '下载(GBK编码)': false} });
});
function setUserLevel(user_id,flag){
	if(user_id){
		var submit = function (v, h, f) {
		    if (v == true) {
		    	$.jBox.tip("数据提交中...", 'loading');
		    	window.setTimeout(function () { 
		    	 $.post("?method=setUserLevel",{user_id:user_id,flag : flag},function(data){
			    	 jBox.tip(data.msg, 'info');
			    	 if(data.ret == 1){
			    		window.location.reload();
			    	 }
				 });
		    	}, 1000);
		    } 
		    return true;
		};
		// 自定义按钮
		$.jBox.confirm("您确定要进行该操作吗?", "系统提示", submit, { buttons: { '确定': true, '取消': false} });
	}
}

function unlock(user_id){
	if(user_id){
		var submit = function (v, h, f) {
		    if (v == true) {
		    	$.jBox.tip("数据提交中...", 'loading');
		    	window.setTimeout(function () { 
		    	 $.post("?method=unlock",{user_id:user_id},function(data){
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
		$.jBox.confirm("您确定要为他解锁吗?", "系统提示", submit, { buttons: { '确定': true, '取消': false} });
	}
}

function updateYmId(user_id){
	var url = "${ctx}/manager/admin/UserInfo.do?method=updateYmId&id=" + user_id;
	$.dialog({
		title:  "上级变更",
		width:  1000,
		height: 700,
		padding: 0,
		max: false,
        min: false,
        fixed: true,
        lock: true,
        zIndex:100,
		content:"url:"+ encodeURI(url)
	});
}

function initPassword(id) {
	if (confirm("确认要初始化密码吗？")) {
		var password = prompt("请输入您的新密码,如果不输入,默认初始密码为“${init_pwd}”。","");
		if (null != password) {
			if (password.length == 0) {
				password = "${init_pwd}";
			}
			$.post("CsAjax.do?method=initPassword",{uid : id, password : password},function(data){
				if(null != data.result){alert(data.msg);} else {alert("初始化密码失败");}
			});
		}
	}
	return false;
}
function shengji(id){
	$.ajax({ 	 	 	 
		type: "POST" , 
		url: "${ctx}/manager/admin/UserInfo.do?method=shengji&user_id="+id,
		dataType: "json", 
        async: true, 
        error: function (request, settings) {alert(" 数据加载请求失败！ ")},
        success: function (data) {
        	var html = "<div style='padding:10px;'>";
        			html+="个人积分："+data.cur_score+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联盟积分："+data.union_score+"</br>";
        			html+="总积分："+data.sum_score+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当前等级："+data.cur_level_name+"</br>";
        			html+=""+data.next_level_name+"最低积分为："+data.next_level_score+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+data.is_upgrade_name;
	        		if(data.is_upgrade < 2 && data.cur_user_level > 2){
	        			if(data.upgrade_defeated!=1){
	        				html+="直推联盟总积分升级还需：";
	        				if(data.union_score_need){
	        					html+=data.union_score_need;
	        					}else{
	        						html+="已满足升级条件";
	        					}
	        			}
	        		}
	        		html+="</div>";
        	 $.jBox.alert(html, '我的积分');
        }
	});
}

function updateLogin(id){
	$.ajax({ 	 	 	 
		type: "POST" , 
		url: "${ctx}/manager/admin/UserInfo.do?method=updateLogin&user_id="+id,
		dataType: "json", 
        async: true, 
        error: function (request, settings) {alert(" 数据加载请求失败！ ")},
        success: function (data) {
        	jBox.tip(data.msg, 'info');
        	window.setTimeout(function () { 
        	if(data.ret == -1){
	    		 window.location.href="${ctx}/manager/login.do";
	    	 }else{
	    		window.location.reload();
	    	 }
        	}, 1000);
        }
	});
}
function clearUserWeiXin(id) {
	if (confirm("确认要清空微信号吗？")) {
		if (null != id && "" != id) {
			$.post("UserInfo.do?method=clearUserWeiXin",{uid : id},function(data){
				if(data.ret == 1){
					alert(data.msg);
				} else {
					alert(data.msg);
				}
			});
		}
	}
	return false;
}

function addDomainSite(id){
	var url="UserInfo.do?method=addDomainSite&id="+id;
	$.dialog({
		titile:"设置二级域名",
		width:900,
		height:520,
		content:"url:"+encodeURI(url)
	});
}
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>

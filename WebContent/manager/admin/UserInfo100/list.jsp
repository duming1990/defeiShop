<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/scripts/autocomplete/autocomplete.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/UserInfo100">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="5%" nowrap="nowrap">
              <div>
              	登录名：
                <html-el:text property="user_name_like" maxlength="20" styleId="user_name_like" style="width:80px;" styleClass="webinput" />
                &nbsp;添加时间：
                <html-el:text property="today_date" styleId="today_date" styleClass="webinput" size="9" maxlength="9" readonly="true" onclick="WdatePicker();" />
                &nbsp; 性别：
                <html-el:select property="sex">
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="0">男</html-el:option>
                  <html-el:option value="1">女</html-el:option>
                </html-el:select>
                </div>
                <div style="margin-top:5px;" id="city_div">
                	所在地区：
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
  <form id="listForm" name="listForm" method="post" action="UserInfo100.do?method=delete">
    <div style="padding-bottom:5px;">
    <logic-el:match name="popedom" value="+4+">
      <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" />
    </logic-el:match>
    <logic-el:match name="popedom" value="+1+">
      <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='UserInfo100.do?method=add&mod_id=${af.map.mod_id}';" />
    </logic-el:match>
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%" nowrap="nowrap"><input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /></th>
        <th nowrap="nowrap">登录名</th>
        <th width="10%">真实姓名</th>
        <th width="10%">性别</th>
        <th width="10%">添加时间</th>
        <th width="8%">用户状态</th>
        <th width="6%">会员积分</th>
        <th width="6%">会员等级</th>
        <th width="10%" nowrap="nowrap">分配角色</th>
        <c:if test="${userInfo.user_type eq 1}">
        <th width="10%" nowrap="nowrap">初始化密码</th>
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
          <td align="left"><a >${fn:escapeXml(cur.user_name)}</a>
          </td>
          <td align="left">${fn:escapeXml(cur.real_name)}</td>
             <td align="center"><c:choose>
              <c:when test="${cur.sex eq 0}"> <span style="color: #F00;">男</span> </c:when>
              <c:when test="${cur.sex eq 1}"> <span style="color: #060;">女</span> </c:when>
            </c:choose></td>
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
          <td align="center">
           <c:if test="${cur.map.user_state eq 1}">
           <span style="color: #F00;" class="qtip" title="用户合法">用户非法</span>
           <a class="butbase" href="javascript:void(0);" onclick="setUserNomal('${cur.id}');"><span class="icon-edit">设置合法</span></a>
           </c:if>
           <c:if test="${cur.map.user_state eq 0}"><span style="color: #060;" class="qtip" title="用户正常">用户正常</span></c:if>
          </td>
          <td align="center">
          <fmt:formatNumber var="cur_score_" value="${cur.cur_score}" pattern="0.########"/>
          <a class="viewDetail qtip" href="${ctx}/manager/admin/UserScore.do?method=list&id=${cur.id}&mod_id=1002004000" title="个人积分:${fn:escapeXml(cur_score_)}">
          ${fn:escapeXml(cur_score_)}</a>
         </td>
          <td align="center">
          	<c:forEach var="curSon" items="${UserLevelBaseDataList}">
          		<c:if test="${curSon.id eq cur.user_level}">${curSon.type_name}</c:if>
          	</c:forEach>
          </td>
         <td nowrap="nowrap" align="center"><a class="butbase" href="javascript:void(0);"><span class="icon-configure" onclick="doNeedMethod(null, 'UserInfo100.do','setRoleUser', 'user_id=${cur.id}&' + $('#bottomPageForm').serialize())">分配角色</span></a></td>
          <c:if test="${userInfo.user_type eq 1}"><td nowrap="nowrap" align="center"><a class="butbase" href="javascript:void(0);"><span class="icon-lock" onclick="initPassword(${cur.id});">初始化密码</span></a></td></c:if>
          <td align="center" nowrap="nowrap"> 
       		<c:if test="${userInfo.user_type eq 1}">
       		<logic-el:match name="popedom" value="+20+">
                <c:if test="${cur.is_city_manager eq 0}">
                 <c:set var="hide" value="设置专员" />
                 <c:set var="is_city_manager" value="1" />
                 <a class="butbase" href="javascript:void(0);"><span class="icon-edit" onclick="cityManger(${cur.id},'${is_city_manager}')" class="icon-split" id="hide_${cur.id}">${hide}</span></a>
                </c:if>
             </logic-el:match>
             <logic-el:match name="popedom" value="+21+">
             	<c:set var="hide" value="取消专员" />
                <c:if test="${cur.is_city_manager eq 1}">
                 <c:set var="is_city_manager" value="0" />
                 <a class="butbase" href="javascript:void(0);"><span class="icon-edit" onclick="cityManger(${cur.id},'${is_city_manager}')" class="icon-split" id="hide_${cur.id}">${hide}</span></a>
                </c:if>
       		 </logic-el:match>
       		</c:if>
          	<logic-el:match name="popedom" value="+2+">
          		<c:if test="${cur.user_type eq 1}" var="not_edit">
          			<a class="butbase but-disabled" href="javascript:void(0);"><span class="icon-edit">修改</span></a>
          		</c:if>
          		<c:if test="${!not_edit}">
          			<a class="butbase" href="javascript:void(0);"><span class="icon-edit" onclick="confirmUpdate(null, 'UserInfo100.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize());">修改</span></a>
          		</c:if>
          	</logic-el:match>
          	<logic-el:match name="popedom" value="+4+">
          		<c:if test="${not isLock and not isDel and cur.user_type ne 1}" var="not_del">
          			<a class="butbase" href="javascript:void(0);"><span class="icon-remove" onclick="confirmDelete(null, 'UserInfo100.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize());">删除</span></a>
          		</c:if>
          		<c:if test="${!not_del}">
          			<a class="butbase but-disabled" href="javascript:void(0);"><span class="icon-remove">删除</span></a>
          		</c:if>
          	</logic-el:match>
            </td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="UserInfo100.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("user_name_like", "${fn:escapeXml(af.map.user_name_like)}");
            pager.addHiddenInputs("mobile", "${fn:escapeXml(af.map.mobile)}");
            pager.addHiddenInputs("today_date", "${af.map.today_date}");
			pager.addHiddenInputs("is_del", "${af.map.is_del}");
			pager.addHiddenInputs("is_entp", "${af.map.is_entp}");
			pager.addHiddenInputs("is_lianmeng", "${af.map.is_lianmeng}");
			pager.addHiddenInputs("is_fuwu", "${af.map.is_fuwu}");
			pager.addHiddenInputs("is_city_manager", "${af.map.is_city_manager}");
			pager.addHiddenInputs("is_daqu", "${af.map.is_daqu}");
			pager.addHiddenInputs("user_type", "${af.map.user_type}");
			pager.addHiddenInputs("sex", "${af.map.sex}");
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
<script type="text/javascript" src="${ctx}/scripts/autocomplete/autocomplete.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
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
	
	
    var options = {
      serviceUrl : "UserInfo100.do?method=getQueryUserNames",
      maxHeight : 240,
      width: 80,
      delimiter : /(,|;)\s*/,
      pageCount : 20,
      animate : true,
      onSelect : function(value, data) {},
      deferRequestBy : 0, //miliseconds
      params : {country : "Yes"}
    };
    $("#user_name_like").autocomplete(options);
});
function initPassword(id) {
	if (confirm("确认要初始化密码吗？")) {
		var password = prompt("请输入您的新密码,如果不输入,默认初始密码为“0”。","");
		if (null != password) {
			if (password.length == 0) {
				password = "0";
			}
			$.post("CsAjax.do?method=initPassword",{uid : id, password : password},function(data){
				if(null != data.result){alert(data.msg);} else {alert("初始化密码失败");}
			});
		}
	}
	return false;
}

function cityManger(user_id,is_city_manager){
	if(user_id){
		var submit = function (v, h, f) {
		    if (v == true) {
		    	$.jBox.tip("数据提交中...", 'loading');
		    	window.setTimeout(function () { 
		    	 $.post("?method=updateToCityManager",{user_id:user_id,is_city_manager : is_city_manager},function(data){
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

function setUserNomal(user_id){
	if(null != user_id && "" != user_id){
		var submit = function (v, h, f) {
		    if (v == true) {
				$.jBox.tip("数据提交中...", 'loading');
				window.setTimeout(function () { 
					$.ajax({
						type: "POST",
						url: "UserInfo100.do?method=updateUserIsNomal",
						data: "user_id=" + user_id,
						dataType: "json",
						error: function(request, settings) {$.jBox.tip("数据请求失败", "error");},
						success: function(data) {
							if(data.ret == "0"){
								$.jBox.tip(data.msg, "info");
							} else {
								$.jBox.tip(data.msg, "success");
								window.setTimeout(function () {
									window.location.reload();
								}, 1500);
							}
						}
					});	
		    	}, 1000);
		    }
		    return true;
		};
		var tip = "你确定要设置该用户合法吗？";
		$.jBox.confirm(tip, "系统提示", submit, { buttons: { '确定': true, '取消': false} });
	}
}

    

//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>

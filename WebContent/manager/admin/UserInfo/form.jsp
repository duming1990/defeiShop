<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
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
  <html-el:form action="/admin/UserInfo.do">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="p_index" styleId="p_index" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">用户基本信息</th>
      </tr>
      <tr>
        <td width="15%" class="title_item"><span style="color: #F00;">*</span>登录名：</td>
        <td width="85%"><html-el:text property="user_name" maxlength="20" styleClass="webinput" styleId="user_name" style="width:200px" />
          &nbsp;<span id="user_name_tip" style="display: none;"></span></td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>真实姓名：</td>
        <td><html-el:text property="real_name" maxlength="20" styleClass="webinput" styleId="real_name" style="width:200px" /></td>
      </tr>
      <tr>
        <td class="title_item">所属区域：</td>
        <td id="city_div">
        <html-el:select property="province" styleId="province" styleClass="pi_prov" style="width:120px;">
            <html-el:option value="">请选择...</html-el:option>
          </html-el:select>
          &nbsp;
          <html-el:select property="city" styleId="city" styleClass="pi_city" style="width:120px;">
            <html-el:option value="">请选择...</html-el:option>
          </html-el:select>
          &nbsp;
          <html-el:select property="country" styleId="country" styleClass="pi_dist" style="width:120px;">
            <html-el:option value="">请选择...</html-el:option>
          </html-el:select></td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>用户类型：</td>
        <td><html-el:select property="user_type" styleId="user_type">
            <html-el:option value="">请选择...</html-el:option>
            <c:forEach items="${baseData10List}" var="cur">
              <html-el:option value="${cur.id}">${fn:escapeXml(cur.type_name)}</html-el:option>
            </c:forEach>
          </html-el:select></td>
      </tr>
      <tr>
        <td class="title_item">性别：</td>
        <td><html-el:select property="sex" styleId="sex">
            <html-el:option value="0">男</html-el:option>
            <html-el:option value="1">女</html-el:option>
          </html-el:select></td>
      </tr>
      <tr>
        <td class="title_item">生日：</td>
        <td><fmt:formatDate value="${af.map.birthday}" pattern="yyyy-MM-dd" var="_add_birthday" />
          <html-el:text property="birthday" size="10" maxlength="10" readonly="true" onclick="WdatePicker();" value="${_add_birthday}" style="cursor:pointer;text-align:center;" title="点击选择日期" /></td>
      </tr>
      <tr>
        <td class="title_item">用户头像：</td>
        <td width="85%"><c:set var="img" value="${ctx}/styles/imagesPublic/user_header.png" />
        <c:if test="${not empty af.map.user_logo}">
          <c:set var="img" value="${ctx}/${af.map.user_logo}@s400x400" />
        </c:if>
        <c:if test="${fn:contains(af.map.user_logo, 'http://')}">
		  <c:set var="img" value="${af.map.user_logo}"/>
		 </c:if>
        <a href="${img}" target="_blank"><img src="${img}" height="100" id="user_logo_img" /></a></td>
      </tr>
      <tr id="tr-ymid">
        <td class="title_item"><span style="color: #F00;">*</span>邀请人：</td>
        <td width="85%">
        <c:if test="${empty af.map.id}" var="isadd"><html-el:text property="ymid" maxlength="20" styleClass="webinput" styleId="ymid" style="width:200px" /></c:if>
        <c:if test="${not isadd}">
        <html-el:hidden property="ymid" />
        ${fn:escapeXml(af.map.ymid)}</c:if>
        </td>
      </tr>
      <tr>
        <th colspan="2">联系方式</th>
      </tr>
      <tr id="tr-mobile">
        <td class="title_item"><span style="color: #F00;">*</span>手机：</td>
        <td><html-el:text property="mobile" maxlength="80" styleClass="webinput" styleId="mobile" onblur="validMobile($(this).val());" style="width:200px;" /></td>
      </tr>
      <tr>
        <td class="title_item">办公电话：</td>
        <td><html-el:text property="office_tel" maxlength="80" styleClass="webinput" styleId="office_tel" style="width:400px;" />
          多个办公电话请用逗号分隔</td>
      </tr>
      <tr>
        <td class="title_item">电子信箱：</td>
        <td><html-el:text property="email" styleClass="webinput" styleId="email" maxlength="50" />
          &nbsp;<span id="email_tip" style="display: none;"></span></td>
      </tr>
      <tr>
        <th colspan="2">银行信息</th>
      </tr>
      <tr>
        <td class="title_item">开户银行：</td>
        <td width="85%">${fn:escapeXml(af.map.bank_name)}</td>
      </tr>
      <tr>
        <td class="title_item">开户账号：</td>
        <td><html-el:text property="bank_account" styleClass="webinput" styleId="bank_account" maxlength="50" style="width:200px;" /></td>
      </tr>
      <tr>
        <td class="title_item">开户名：</td>
        <td width="85%">${fn:escapeXml(af.map.bank_account_name)}</td>
      </tr>
      <tr>
        <th colspan="2">第三方信息</th>
      </tr>
      <tr>
        <td class="title_item">微信ID：</td>
        <td width="85%">${fn:escapeXml(af.map.appid_weixin)}</td>
      </tr>
      <tr>
        <td class="title_item">微信唯一ID：</td>
        <td width="85%">${fn:escapeXml(af.map.appid_weixin_unionid)}</td>
      </tr>
      <tr>
        <td class="title_item">是否关注微信平台：</td>
        <td width="85%"><c:choose>
            <c:when test="${not empty (af.map.appid_weixin_is_follow)}">是</c:when>
            <c:when test="${empty (af.map.appid_weixin_is_follow)}">否</c:when>
          </c:choose>
        </td>
      </tr>
      <tr>
        <td class="title_item">QQID：</td>
        <td width="85%">${fn:escapeXml(af.map.appid_qq)}</td>
      </tr>
      <tr>
        <td class="title_item">新浪微博ID：</td>
        <td width="85%">${fn:escapeXml(af.map.appid_weibo)}</td>
      </tr>
      <tr>
        <th colspan="2">积分&资金</th>
      </tr>
      <c:if test="${is_open_bi_dian eq true}">
      <tr>
        <td class="title_item">余额：</td>
        <td width="85%"><fmt:formatNumber var="bi" value="${af.map.bi_dianzi}" pattern="0.########"/>${bi}</td>
      </tr>
      </c:if>
      <tr>
        <td class="title_item">货款：</td>
        <td width="85%"><fmt:formatNumber var="bi" value="${af.map.bi_huokuan}" pattern="0.########"/>${bi}</td>
      </tr>
      <tr>
        <td class="title_item">积分：</td>
        <td width="85%">个人积分：
         <fmt:formatNumber var="cur_score" value="${af.map.cur_score}" pattern="0.########"/>
         <html-el:text property="cur_score" maxlength="80" styleClass="webinput" styleId="cur_score" style="width:200px;" value="${cur_score}"/>
        </td>
      </tr>
      <tr>
        <td class="title_item">排序值：</td>
        <td><html-el:text property="order_value" styleClass="webinput" styleId="order_value" size="4" maxlength="4" />
          值越大，显示越靠前，范围：0-9999</td>
      </tr>
      <c:if test="${af.map.is_del eq 1}">
        <tr>
          <td class="title_item">是否删除：</td>
          <td><html-el:select property="is_del" styleId="is_del">
              <html-el:option value="0">否</html-el:option>
              <html-el:option value="1">是</html-el:option>
            </html-el:select></td>
        </tr>
      </c:if>
      <tr>
        <td colspan="2" style="text-align: center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript">//<![CDATA[
	$("#city_div").citySelect({
	        data:getAreaDic(),
	        province:"${af.map.province}",
	        city:"${af.map.city}",
	        country:"${af.map.country}",
	        province_required:true,
	        city_required:true,
	        country_required:false,
	        callback:function(selectValue,selectText){
	        	if(null != selectValue && "" != selectValue){
	        		var p_indexs = selectValue.split(",");
	        		if(null != p_indexs && p_indexs.length > 0){
	        			$("#p_index").val(p_indexs[p_indexs.length - 1]);
	        		}
	        	}
	        }
	    });

		var user_exits = '<span id="tip1" style="color:red;">对不起，此用户名已被注册</span>';
		var email_exits = '<span id="tip2" style="color:red;">对不起，此邮箱已被注册</span>';
		var user_not_exits = '<span id="tip" style="color:#33CC33;">恭喜你，该用户名可以使用</span>';

		var f = document.forms[0];

		$("#user_type").change(function() {
			if ($(this).val() == 19) {
				$("#tr-mobile").hide();
				$("#tr-ymid").hide();
			} 
		});

		$("#user_name").attr("datatype", "Require").attr("msg", "登录名必须填写,");
		$("#real_name").attr("datatype", "Require").attr("msg", "真实姓名必须填写");
		$("#user_type").attr("datatype", "Require").attr("msg", "请选择用户类型");
		$("#password").attr("datatype", "Require").attr("msg", "密码必须填写");
		
		$("#order_value").attr("datatype", "Number").attr("msg","排序值必须在0~9999之间的正整数");
		

		// 提交
		$("#btn_submit").click(function() {
			var mobile = $("#mobile").val();
			if(null != mobile && mobile != '' && mobile != 19){
				$("#mobile").attr("datatype", "Mobile").attr("msg", "请正确填写手机号");
				$("#ymid").attr("datatype", "Require").attr("msg", "邀请人必须填写");
			}
			
			if (Validator.Validate(f, 3)) {

				var u_name = $.trim($("#user_name").val());
				var u_id = $("#id").val();
				var email = $("#email").val();
				if ("" == u_name) {
					alert("请填写登录名");
					return false;
				}

				var val = this.value;
				if (u_name != '' || email != '') {
					$.post("UserInfo.do?method=checkLoginNameAndEmail",{user_name : u_name,id : u_id,email : email},
						function(data) {
						if (data == "1") {
							$("#user_name_tip").show().append(user_exits);
							return false;
						} else if (data == "2") {
							$("#email_tip").show().append(email_exits);
							return false;
						} else if (data == "3") {
							$("#email_tip").show().append(email_exits);
							$("#user_name_tip").show().append(user_exits);
							return false;
						} else {
							$("#btn_submit").attr("value","正在提交...").attr("disabled","true");
							$("#btn_reset").attr("disabled","true");
							$("#btn_back").attr("disabled","true");
							f.submit();
						}
					});
				} else {
					$("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
					$("#btn_reset").attr("disabled", "true");
					$("#btn_back").attr("disabled", "true");
					f.submit();
				}

			}
		});

		function setOnlyNum() {
			$(this).css("ime-mode", "disabled");
			$(this).attr("t_value", "");
			$(this).attr("o_value", "");
			$(this).bind("dragenter", function() {
				return false;
			});
			$(this).keypress(function() {
				if (!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))
					this.value = this.t_value;
				else
					this.t_value = this.value;
				if (this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))
					this.o_value = this.value;
			}).keyup(function() {
				if (!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))
					this.value = this.t_value;
				else
					this.t_value = this.value;
				if (this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))
					this.o_value = this.value;
			}).blur(function() {
				if (!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))
					this.value = this.o_value;
				else {
					if (this.value.match(/^\.\d+$/))
						this.value = 0 + this.value;
					if (this.value.match(/^\.$/))
						this.value = 0;
					this.o_value = this.value;
				}
				if (this.value.length == 0)
					this.value = 0;
			});
		}


function validMobile(mobile){
	if ("" != mobile && $("#mobile").attr("readonly") != "readonly") {
		var reg = /^((\(\d{2,3}\))|(\d{3}\-))?((1[3-9]\d{9}))$/;
		if (mobile.match(reg)) {
			$.ajax({
				type: "POST" , 
				url: "${ctx}/CsAjax.do" , 
				data:"method=validateMobile&mobile=" + mobile + "&t=" + new Date(),
				dataType: "json", 
		        async: true, 
		        error: function (request, settings) {alert(" 数据加载请求失败！ ");	$("#btn_submit").attr("disabled", "true");}, 
		        success: function (result) {
					if (result == 0) {
						$.jBox.alert("参数丢失","提示");
						$("#btn_submit").attr("disabled", "true");
						return false;
					} else if (result == 1) {
						$("#btn_submit").removeAttr("disabled");
					} else if (result == 2) {
						$.jBox.alert("该手机号码已被注册！","提示");
						$("#btn_submit").attr("disabled", "true");
						return false;
					}
		        }
			});
		} else {
			$.jBox.alert("手机格式不正确！","提示");
			$("#btn_submit").attr("disabled", "true");
			return false;
		}
	}
}		
//]]>
</script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>

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
  <html-el:form action="/admin/UserInfo100.do">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="p_index" styleId="p_index" />
    <html-el:hidden property="user_type" styleId="user_type" />
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
        <td id="city_div"><html-el:select property="province" styleId="province" styleClass="pi_prov" style="width:120px;">
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
      <c:if test="${empty af.map.password}">
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>密码：</td>
        <td><html-el:password property="password" maxlength="40" styleClass="webinput" styleId="password" style="width:200px" /></td>
      </tr>
      </c:if>
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
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){                                          
});
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
			if ($(this).val() == 2) {
				$("#user_level_tr").show();
			} else {
				$("#user_level_tr").hide();
			}
		});
		if ("2" == $("#user_type").val()) {
			$("#user_level_tr").show();
		}

		$("#user_name").attr("datatype", "Require").attr("msg", "登录名必须填写,");
		$("#real_name").attr("datatype", "Require").attr("msg", "真实姓名必须填写");
		$("#user_type").attr("datatype", "Require").attr("msg", "请选择用户类型");
		$("#password").attr("datatype", "Require").attr("msg", "密码必须填写");
		$("#ymid").attr("datatype", "Require").attr("msg", "邀请人必须填写");
		//$("#mobile").attr("datatype", "Mobile").attr("msg", "请正确填写手机号");
		$("#order_value").attr("datatype", "Number").attr("msg","排序值必须在0~9999之间的正整数");


		// 提交
		$("#btn_submit").click(function() {
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
					$.post("UserInfo100.do?method=checkLoginNameAndEmail",{user_name : u_name,id : u_id,email : email},
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
				url: "${ctx}/Register.do" , 
				data:"method=validateMobile&mobile=" + mobile + "&t=" + new Date(),
				dataType: "json", 
		        async: true, 
		        error: function (request, settings) {alert(" 数据加载请求失败！ ");	$("#btn_submit").attr("disabled", "true");}, 
		        success: function (result) {
					if (result == 0) {
						alert('参数丢失！', '提示');
						$("#btn_submit").attr("disabled", "true");
						return false;
					} else if (result == 1) {
						$("#btn_submit").removeAttr("disabled");
					} else if (result == 2) {
						alert('该手机号码已被注册！', '提示');
						$("#btn_submit").attr("disabled", "true");
						return false;
					}
		        }
			});
		} else {
			alert('手机格式不正确！', '提示');
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

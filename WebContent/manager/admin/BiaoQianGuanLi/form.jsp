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
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/BiaoQianGuanLi.do">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="id" styleId="id"/>
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">${base_name}</th>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>频道名：</td>
        <td><html-el:text property="tag_name" maxlength="200"  styleClass="webinput" styleId="tag_name" style="width:60%" /></td>
      </tr>
      <tr>
		<td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>频道背景图：</td>
			<td colspan="4">
				<c:set var="img" value="${ctx}/styles/imagesPublic/user_header.png" />
				<c:if test="${not empty af.map.image_path}">
					<c:set var="img" value=" ${ctx}/${af.map.image_path}" />
				</c:if>
				<img src="${img}" height="80" id="image_path_img" />
				<html-el:hidden property="image_path" styleId="image_path" value="${af.map.image_path}" />
				<div class="files-warp" id="image_path_warp">
					<div class="btn-files"> <span>上传主图</span>
						<input id="image_path_file" type="file" name="image_path_file" />
					</div>
					<div class="progress"> <span class="bar"></span><span class="percent">0%</span></div>
				</div>
				<c:url var="urlps" value="/IndexPs.do" />
				<span>[图片尺寸] 建议：[800px * 400px]。<a href="${urlps}" class="label label-danger" target="_blank">[在线编辑图片]</a></span></td>
		</tr>
      <%-- <tr>
        <td width="14%" nowrap="nowrap" class="title_item">相关类别：</td>
        <td colspan="2" width="88%"><html-el:hidden property="cls_id" styleId="cls_id" />
          <html-el:text property="cls_name" styleId="cls_name" readonly="true" onclick="getParClsInfo();" maxlength="125" style="width:280px" styleClass="webinput"/>
          &nbsp;
          <a class="butbase" onclick="getParClsInfo();"><span class="icon-search">选择</span></a></td>
      </tr> --%>
      <%-- <tr>
        <td class="title_item">标签类型：</td>
        <td><html-el:select property="tag_type" styleId="tag_type">
        <c:forEach var="cur" items="${BaseDatalist}">
              <html-el:option value="${cur.id}">${cur.type_name}</html-el:option>
        </c:forEach>      
            </html-el:select> </td>
      </tr> --%>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>频道类型：</td>
        <td><html-el:select property="p_index" styleId="p_index">
        <html-el:option value="0">全国</html-el:option>
        <c:forEach var="cur" items="${baseProvinceList}">
              <html-el:option value="${cur.p_index}">${cur.p_name}</html-el:option>
        </c:forEach>      
            </html-el:select> </td>
      </tr>
      <tr>
        <td class="title_item">是否锁定：</td>
        <td><html-el:select property="is_lock" styleId="is_lock">
              <html-el:option value="0">否</html-el:option>
              <html-el:option value="1">是</html-el:option>
            </html-el:select>（锁定后，数据将无法删除） </td>
      </tr>
        <tr>
          <td class="title_item">是否删除：</td>
          <td><html-el:select property="is_del" styleId="is_del">
              <html-el:option value="0">否</html-el:option>
              <html-el:option value="1">是</html-el:option>
            </html-el:select></td>
        </tr>
       <tr>
        <td colspan="2" style="text-align:center">
        <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript">//<![CDATA[
var type_name_exits = '<span id="tip" style="color:red;">对不起，此类型已被注册</span>';
var type_name_not_exits = '<span id="tip" style="color:#33CC33;">恭喜你，该类型名可以使用</span>';

var f = document.forms[0];

var btn_name = "上传图片";
if("" != "${af.map.image_path}") {
	btn_name = "重新上传";
}
upload("image_path", "image", btn_name, "${ctx}");

$("#tag_name").attr("datatype","Require").attr("msg","频道名必须填写");
$("#p_index").attr("datatype","Require").attr("msg","频道类型必须选择");
$("#image_path").attr("dataType", "Filter" ).attr("msg", "请上传格式为（bmp, gif, jpeg, jpg, png）的图片地址！").attr("require", "true").attr("accept", "bmp, gif, jpeg, jpg, png");
// $("#order_value").attr("datatype","Number").attr("msg","排序值必须在0~9999之间的正整数");

// 提交
$("#btn_submit").click(function(){
	if(Validator.Validate(f, 3)){
		var t_name = $.trim($("#tag_name").val());
		var t_id = $("#id").val();
		if("" == t_name) {
			alert("请填写频道名");
			return false;
		}
        $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
        $("#btn_reset").attr("disabled", "true");
        $("#btn_back").attr("disabled", "true");
		f.submit();
	}
});
function getParClsInfo() {
	var url = "BasePdClass.do?method=getParClsInfo&isPd=true&azaz=" + Math.random();
	$.dialog({
		title:  "选择产品类别",
		width:  450,
		height: 400,
        lock:true ,
		content:"url:"+url
	});
}
function setOnlyNum() {
	$(this).css("ime-mode", "disabled");
	$(this).attr("t_value", "");
	$(this).attr("o_value", "");
	$(this).bind("dragenter",function(){
		return false;
	});
	$(this).keypress(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).keyup(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).blur(function (){
		if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value;}
		if(this.value.length == 0) this.value = 0;
	});
}
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>

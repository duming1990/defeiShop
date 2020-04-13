<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/BaseComminfoTags.do">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="id" styleId="id"/>
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="par_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th colspan="2">标签管理</th>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>标签名：</td>
        <td><html-el:text property="tag_name" maxlength="200"  styleClass="webinput" styleId="tag_name" style="width:280px" /></td>
      </tr>
       <tr>            
		<td nowrap="nowrap" class="title_item">图片：</td>
			<td colspan="4">
				<c:set var="img" value="${ctx}/styles/imagesPublic/user_header.png" />
				<c:if test="${not empty af.map.image_path}">
					<c:set var="img" value=" ${ctx}/${af.map.image_path}@s400x400" />
				</c:if>
				<img src="${img}" height="80" id="img_path_img" />
				<html-el:hidden property="image_path" styleId="image_path" value="${af.map.image_path}" />
				<div class="files-warp" id="image_path_warp">
					<div class="btn-files"> <span>上传主图</span>
						<input id="image_path_file" type="file" name="image_path_file" />
					</div>
					<div class="progress"> <span class="bar"></span><span class="percent">0%</span></div>
				</div>
				<c:url var="urlps" value="/IndexPs.do" />
				<span>[图片尺寸] 建议：[479px * 180px]。<a href="${urlps}" class="label label-danger" target="_blank">[在线编辑图片]</a></span></td>
		</tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>相关类别：</td>
        <td colspan="2" width="88%"><html-el:hidden property="cls_id" styleId="cls_id" />
          <html-el:text property="cls_name" styleId="cls_name" readonly="true" onclick="getParClsInfo();" maxlength="125" style="width:280px" styleClass="webinput"/>
          &nbsp;
          <button class="bgButton" type="button" onclick="getParClsInfo()"><i class="fa fa-search"></i> 选择</button>
          </td>
      </tr>
      <tr>
        <td class="title_item">是否锁定：</td>
        <td><html-el:select property="is_lock" styleId="is_lock">
              <html-el:option value="0">否</html-el:option>
              <html-el:option value="1">是</html-el:option>
            </html-el:select>（锁定后，数据将无法删除） </td>
      </tr>
       <tr>
          <td class="title_item">是否显示：</td>
          <td><html-el:select property="is_show" styleId="is_show">
              <html-el:option value="1">是</html-el:option>
              <html-el:option value="0">否</html-el:option>
            </html-el:select></td>
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

var f = document.forms[0];

var btn_name = "上传图片";
if("" != "${af.map.image_path}") {
	btn_name = "重新上传";
}
upload("image_path", "image", btn_name, "${ctx}");

$("#tag_name").attr("datatype","Require").attr("msg","标签名必须填写");
// $("#tag_type").attr("datatype","Require").attr("msg","标签类型必须选择")
$("#cls_name").attr("datatype","Require").attr("msg","标签类型必须选择")

// 提交
$("#btn_submit").click(function(){
	if(Validator.Validate(f, 3)){
        $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
        $("#btn_reset").attr("disabled", "true");
        $("#btn_back").attr("disabled", "true");
		f.submit();
	}
});
function getParClsInfo() {
	var url = "${ctx}/CsAjax.do?method=getClsId&t=" + Math.random();
	$.dialog({
		title:  "选择商品类别",
		width:  450,
		height: 400,
        lock:true ,
		content:"url:"+url
	});
}
//]]></script>
</body>
</html>

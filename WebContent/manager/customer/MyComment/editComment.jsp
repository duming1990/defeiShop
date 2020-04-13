<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>收货地址信息 - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../../public_page_in_head.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
 <div style="padding-top: 20px;" align="center">
      <html-el:form action="/customer/MyComment">
        <html-el:hidden property="method" value="save" />
        <html-el:hidden property="id" styleId="id" />
        <html-el:hidden property="order_id" styleId="order_id" />
        <html-el:hidden property="link_id" styleId="link_id" />
        <html-el:hidden property="comm_tczh_id" styleId="comm_tczh_id" />
        <html-el:hidden property="comm_type" styleId="comm_type" />
        <html-el:hidden property="comm_score" styleId="comm_score" />
        <html-el:hidden property="comm_level" styleId="comm_level" />
        <html-el:hidden property="order_type" styleId="order_type" />
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
          <tr>
            <th colspan="2">评价信息</th>
          </tr>
          <tr>
            <td  width="15%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span><strong>商品评分：</strong></td>
            <td nowrap="nowrap" valign="middle"><div id="comm_good_rate" style="padding-bottom: 6px;float: left;"></div>
              &nbsp;
              <div style="float: left;padding-top: 4px;color: #FF7B22;" id="hint"></div>
              &nbsp;
              <div style="float: left;padding-top: 4px;color: #FF7B22;" id="hintinfo"></div></td>
          </tr>
          <tr>
            <td width="15%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span><strong>购买心得：</strong></td>
            <td align="left">
            <html-el:text property="comm_experience" maxlength="100" styleId="comm_experience" style="width:80%;"/></td>
          </tr>
          <tr>
            <td width="15%" nowrap="nowrap" class="title_item"><span style="color: #F00;"></span><strong>上传评论图片：</strong></td>
          	<td colspan="2">
          	<c:forEach items="${baseFilesList}" var="cur" varStatus="vs">
		        <div style="float:left;width:20%;">
		        <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
		        <c:if test="${not empty cur.save_path}">
		          <c:set var="img" value="${ctx}/${cur.save_path}@s400x400" />
		        </c:if>
		        <img src="${img}" width="100" height="100" id="base_files${vs.count}_img" />
		        <html-el:hidden property="base_files${vs.count}" styleId="base_files${vs.count}" value="${cur.save_path}"/>
		        <div class="files-warp" id="base_files${vs.count}_warp">
		          <div class="btn-files"> <span>添加附件</span>
		            <input id="base_files${vs.count}_file" type="file" name="base_files${vs.count}_file" value="${ctx}/${cur.save_path}"/>
		          </div>
		        </div>
		        </div>
		    </c:forEach>
<!-- 		        <div style="float:left;width:20%;"> -->
<%-- 		        <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" /> --%>
<%-- 		        <img src="${img}" width="100" height="100" id="base_files2_img" /> --%>
<%-- 		        <html-el:hidden property="base_files2" styleId="base_files2" /> --%>
<!-- 		        <div class="files-warp" id="base_files2_warp"> -->
<!-- 		          <div class="btn-files"> <span>添加附件</span> -->
<!-- 		            <input id="base_files2_file" type="file" name="base_files2_file"/> -->
<!-- 		          </div> -->
<!-- 		        </div> -->
<!-- 		        </div> -->
<!-- 		        <div style="float:left;width:20%;"> -->
<%-- 		        <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" /> --%>
<%-- 		        <img src="${img}" width="100" height="100" id="base_files3_img" /> --%>
<%-- 		        <html-el:hidden property="base_files3" styleId="base_files3" /> --%>
<!-- 		        <div class="files-warp" id="base_files3_warp"> -->
<!-- 		          <div class="btn-files"> <span>添加附件</span> -->
<!-- 		            <input id="base_files3_file" type="file" name="base_files3_file"/> -->
<!-- 		          </div> -->
<!-- 		        </div> -->
<!-- 		        </div> -->
<!-- 		        <div style="float:left;width:20%;"> -->
<%-- 		        <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" /> --%>
<%-- 		        <img src="${img}" width="100" height="100" id="base_files4_img" /> --%>
<%-- 		        <html-el:hidden property="base_files4" styleId="base_files4" /> --%>
<!-- 		        <div class="files-warp" id="base_files4_warp"> -->
<!-- 		          <div class="btn-files"> <span>添加附件</span> -->
<!-- 		            <input id="base_files4_file" type="file" name="base_files4_file"/> -->
<!-- 		          </div> -->
<!-- 		        </div> -->
<!-- 		        </div> -->
<!-- 		        <div style="float:left;width:20%;"> -->
<%-- 		        <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" /> --%>
<%-- 		        <img src="${img}" width="100" height="100" id="base_files5_img" /> --%>
<%-- 		        <html-el:hidden property="base_files5" styleId="base_files5" /> --%>
<!-- 		        <div class="files-warp" id="base_files5_warp"> -->
<!-- 		          <div class="btn-files"> <span>添加附件</span> -->
<!-- 		            <input id="base_files5_file" type="file" name="base_files5_file"/> -->
<!-- 		          </div> -->
<!-- 		        </div> -->
<!-- 		        </div> -->
		        说明：[图片比例]建议：1:1 [图片尺寸] 建议：[600px * 600px] ，图片大小不超过2M。
        </td>
      </tr>
          <tr>
            <td colspan="2" align="center">
            <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
             &nbsp;
            <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="window.parent.$.colorbox.close();" /></td>
          </tr>
        </table>
      </html-el:form>
</div>
<!-- main end -->
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/rate/min.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript">//<![CDATA[
var f = document.forms[0]; 

$(document).ready(function(){
	
	var btn_name = "上传评论图片";
	upload("base_files1", "image", btn_name, "${ctx}");
	
	var btn_name = "上传评论图片";
	upload("base_files2", "image", btn_name, "${ctx}");
	
	var btn_name = "上传评论图片";
	upload("base_files3", "image", btn_name, "${ctx}");
	
	var btn_name = "上传评论图片";
	upload("base_files4", "image", btn_name, "${ctx}");
	
	var btn_name = "上传评论图片";
	upload("base_files5", "image", btn_name, "${ctx}");
	
	$("#comm_experience").attr("datatype","Limit").attr("min","1").attr("max","100").attr("msg","请填写购买商品心得且在100以内。");
	
	
	$("#comm_good_rate").raty({
		path:"${ctx}/scripts/rate/img/",
		start: ${af.map.comm_score},
		//iconRange: [['star-on-bad.png', 1], ['star-on-bad.png', 2]],
		click: function(score, evt) {
			$("#comm_score").val(score);
			$("#hintinfo").html("[感谢您的评价，您的评分为：" + $("#comm_score").val()+"]");
			if(score == 1){
				$("#comm_level").val(3);
			}
			if(score == 2 || score == 3){
				$("#comm_level").val(2);
			}
			if(score == 4 || score == 5){
				$("#comm_level").val(1);
			}
		}
	});
	
	//提交
	$("#btn_submit").click(function(){
		if($("#comm_score").val() == ""){
			alert("请对商品进行评分！");
			return false;
		}
		if(Validator.Validate(f, 3)){
			f.submit();
		}
	});
});
//]]></script>
</body>
</html>
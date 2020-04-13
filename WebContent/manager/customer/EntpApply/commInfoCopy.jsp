<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form styleClass="ajaxForm">
    <html-el:hidden property="own_entp_id" styleId="own_entp_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <td width="20%" class="title_item">店名：</td>
        <td>${fn:escapeXml(entpInfo.entp_name)}</td>
      </tr>
      <tr>
        <td width="20%" class="title_item"><span style="color: #F00;">*</span>选择从哪个店铺复制：</td>
        <td width="85%">
          <html-el:select property="from_entp_id" styleId="from_entp_id" style="width:120px;">
            <html-el:option value="">请选择...</html-el:option>
            <c:forEach var="cur" items="${entpInfoAllList}" varStatus="vs">
             <html-el:option value="${cur.id}">${cur.entp_name}</html-el:option>
            </c:forEach>
          </html-el:select>
          <div class="label label-danger label-block" style="text-align:left;width:65%;">复制商品将会将两家店铺的差异商品赋值过去，从而达到减少新店铺重复上架商品。</div>
          <div class="label label-success" style="line-height:2;" id="diffent_count"></div>
        </td>
      </tr>
      <tr>
        <td colspan="2" style="text-align: center">
          <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
        </td>
      </tr>
    </table>
  </html-el:form>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript">//<![CDATA[

		var api = frameElement.api, W = api.opener;                                  
		var f = document.forms[0];

		$("#from_entp_id").attr("datatype", "Require").attr("msg", "请选择所要从哪个店铺复制商品");

		$("#from_entp_id").change(function(){
			var thisVal = $(this).val();
			var own_entp_id = $("#own_entp_id").val();
			if(null != thisVal && "" != thisVal){
				if(own_entp_id == thisVal){
					$.jBox.tip("不能选择自己店铺！", "error",{timeout:1000});
					$(this).val("");
				}else{//这个地方需要列出来所有的差异的商品
					$.ajax({
						type: "POST",
						url: "EntpApply.do",
						data: "method=queryDiffentsComm&own_entp_id=" + own_entp_id + "&from_entp_id=" + thisVal,
						dataType: "json",
						error: function(request, settings) {flag = false;},
						success: function(data) {
							if (data.code == 1) {
								$("#diffent_count").text("查到找" + data.datas.count + "个不同商品！");
							} else {
								$.jBox.tip(data.msg, "error",{timeout:1000});
							}
						}
					});
				}
			}
		});
		
		// 提交
		$("#btn_submit").click(function() {
			if (Validator.Validate(f, 3)) {
				$.jBox.tip("正在操作...", 'loading');
				$.ajax({
					type: "POST",
					url: "EntpApply.do",
					data: "method=saveDiffentsComm&" + $(".ajaxForm").serialize(),
					dataType: "json",
					error: function(request, settings) {flag = false;},
					success: function(data) {
						if (data.code == 1) {
							$.jBox.tip("操作成功", "success",{timeout:1000});
							window.setTimeout(function(){
								W.refreshPage();	
							},2000);
						} else {
							$.jBox.tip(data.msg, "error",{timeout:1000});
						}
					}
				});
			}
		});

//]]>
</script>
</body>
</html>

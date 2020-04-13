<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name_min}触屏版</title>
<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" name="viewport"/>
<meta content="yes" name="apple-mobile-web-app-capable"/>
<meta content="black" name="apple-mobile-web-app-status-bar-style"/>
<meta content="telephone=no" name="format-detection"/>
<jsp:include page="../_public_in_head_new.jsp" flush="true" />
<link rel="stylesheet" href="${ctx}/m/styles/village/css/release.css?v=20180308" />
<style type="text/css">
img{
display: -webkit-inline-box;
    margin: 0;
    padding: 0;
}
.mui-img a{
	position: relative;
    display: inline-block;
}
.mui-img a .aui-icon{
	position: absolute;
    right: -3px;
    top: -10px;
}
</style>
</head>
<body>
<div id="app" v-cloak>
<header class="mui-bar mui-bar-nav mui-bars">
			<i class="mui-icon mui-icon-closeempty" onclick="Common.goBack();"></i>
			<h1 class="mui-title">发布动态</h1>
		</header>
		<form action="/m/MVillageDynamic" enctype="multipart/form-data" method="post" class="ajaxForm0">
    	<input type="hidden" name="method" value="save" />
		<input type="hidden" name="village_id" id="village_id" value="${af.map.village_id}"/>
		<input type="hidden" name="is_public" id="is_public" value="1" />
		<input type="hidden" name="type" id="type" value="${af.map.type}" />
		<div class="color_bjt">
			<div class="shuru">
				<textarea rows="6" name="content" id="content" placeholder="分享新鲜事..."></textarea>
				<div class="mui-img" style="padding: 0 0 20px;position: relative;">
					<img class="photo photoadd" @click="clickFiles();" src="${ctx}/m/styles/village/img/imgcama.png" alt="" />
					<input type="file" style="display: none;" name="files" id="files" accept="image/*" />
				</div>
				<div class="xuanz">
					是否公开
					<div style="display: inline-block;float: right;padding: 20px 0;">
						<div class="mui-switch mui-switch-mini mui-active">
							<div class="mui-switch-handle"></div>
						</div>
					</div>
				</div>
<!-- 				<div style="padding: 0 0 10px;" class="positions"> -->
<!-- 					<button type="button" @click="selects($event)" class="mui-btn mui-btn-dangers" v-for="item in datas.villageInfoList">{{item.village_name}}</button> -->
<!-- 				</div> -->
			</div>
		</div>
		</form>
		<div style="position:fixed;left:0;bottom:0;width: 100%;background-color: #fff;height: 50px;" class="footers">
			<button type="button" @click="btn();" class="mui-btn mui-btn-danger mui-btn-block">确定发布</button>
		</div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript">

  var vm = new Vue({
    el: '#app',
    data: {
      ctx:Common.api,
      datas:"",
    },
    mounted: function() {
    	this.$nextTick(function() {
      	  this.getAjaxData();
        });
    },
    updated: function() {
    },
    methods: {
    	getAjaxData: function() {
       		Common.getData({
       			route: '/m/MVillageDynamic.do?method=getAjaxDataList',
       			data:{
       				village_id:'${af.map.village_id}',
       				type:'${af.map.type}',
       			},
       			success: function(data) {
       				if(data.code == 0) {
       				  mui.alert(data.msg);
       	              return false;
       	            } else if(data.code == 1) {
       	            	vm.datas = data.datas;
       	            }
       			},
       			error: function() {
       				mui.alert('好像出错了哦~');
       			}
       		});
          },
    	clickFiles:function(){
    		$("#files").trigger("click");
    	},
    	selects:function(e){
    		if($(e.target).is(".bian_color")) {
    			$(e.target).removeClass("bian_color");
			} else {
				$(e.target).addClass("bian_color");
			}
    	},
    	btn:function(){
    		var f0 = $(".ajaxForm0").get(0);
    		Common.loading();
    		$.ajax({
				type: "POST",
				url: "?method=save",
				data: $(f0).serialize(),
				dataType: "json",
				error: function(request, settings) {},
				success: function(data) {
					Common.hide();
					mui.toast(data.msg);
					if(data.code == 1){
						var url = "MVillage.do?method=index&id=${af.map.village_id}";
						setTimeout(function() {
							goUrl(url);
						}, 1000);
					}
				}
			});
    	}
    }
  });
  
   mui('.color_bjt .mui-switch').each(function() { //循环所有toggle
		this.addEventListener('toggle', function(event) {
			if(event.detail.isActive) {
				$("#is_public").val(1);
			} else {
				$("#is_public").val(0);
			}
		});
	});

	$(".color_bjt").css("height", window.innerHeight - $("header").height() - $(".footers").height());
	
	var btn_name = "上传";
	uploadNew("files", "image", btn_name, "${ctx}");

  
</script>
	</body>
</html>
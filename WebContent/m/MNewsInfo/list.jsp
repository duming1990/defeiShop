<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"/>
<meta name="apple-mobile-web-app-capable" content="yes"/>
<meta name="apple-mobile-web-app-status-bar-style" content="black"/>
<meta content="telephone=no" name="format-detection"/>
<meta name="wap-font-scale" content="no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${header_title}</title>
<jsp:include page="../_public_in_head.jsp" flush="true" />
<style type="text/css">
.mui-table-view-chevron .mui-table-view-cell{
	padding-right:inherit;
}
</style>
</head>
<body>
<div id="app">
<header class="mui-bar mui-bar-nav">
		<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
		<h1 class="mui-title">${header_title}</h1>
</header>
<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
<div class="mui-scroll">
	<!--数据列表-->
	<ul class="mui-table-view mui-table-view-chevron" id="data-list" startPage="0">
		
	</ul>
</div>
</div>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
		<script>
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
		    	mui.init({
					pullRefresh: {
						container: '#pullrefresh',
						down: {
							callback: pulldownRefresh
						},
						up: {
							contentrefresh: '正在加载...',
							callback: pullupRefresh
						}
					}
				});
		    	if (mui.os.plus) {
					mui.plusReady(function() {
						setTimeout(function() {
							mui('#pullrefresh').pullRefresh().pullupLoading();
						}, 1000);

					});
				} else {
					mui.ready(function() {
						mui('#pullrefresh').pullRefresh().pullupLoading();
					});
				}
		    },
		    methods: {
		    	getAjaxData: function() {
		       		Common.getData({
		       			route: 'm/MNewsInfo.do?method=getListJson',
		       			data: {
		       				link_id: '${af.map.link_id}',
		       				mod_id: '${af.map.mod_id}',
		       				add_uid: '${af.map.add_uid}',
						},
		       			success: function(data) {
			   				var html = "";
			   				if(null != data.datas.newsInfoList){
			   					for(var x in data.datas.newsInfoList){
			   						var news = data.datas.newsInfoList[x];
			   						var url = 'm/MNewsInfo.do?method=view&uuid='+news.uuid;
									html += '<li class="mui-table-view-cell mui-media">';
									html += '<a href="${ctx}/'+url+'">';
									if(null != news.image_path){
										html += '<img class="mui-media-object mui-pull-left" src="${ctx}/'+news.image_path+'">';
									}
									html += '<div class="mui-media-body">';
									html +=  news.title;
									html += '<p class="mui-ellipsis">';
									html +=  news.summary;
									html += '</p></div></a></li>';
			   					}
			   					var startPage = $("#data-list").attr("startPage");
			   					$("#data-list").attr("startPage",startPage++);
			   					
//	 		   					setTimeout(function() {
			   						$("#data-list").append(html);
// 			   						mui('#pullrefresh').pullRefresh().endPulldownToRefresh(); //refresh completed
//	 		   					}, 1500);
			   				}
		       			},
		       			error: function() {
		       				mui.alert('好像出错了哦~');
		       			}
		       		});
		          },
		          pulldownRefresh:function(){
		        	  Common.getData({
				   			route: 'm/MNewsInfo.do?method=getListJson',
				   			success: function(data) {
				   				var html = "";
				   				if(null != data.datas.newsInfoList){
				   					for(var x in data.datas.newsInfoList){
				   						var news = data.datas.newsInfoList[x];
				   						var url = 'm/MNewsInfo.do?method=view&uuid='+news.uuid;
										html += '<li class="mui-table-view-cell mui-media">';
										html += '<a href="${ctx}/'+url+'">';
										if(null != news.image_path){
											html += '<img class="mui-media-object mui-pull-left" src="${ctx}/'+news.image_path+'">';
										}
										html += '<div class="mui-media-body">';
										html +=  news.title;
										html += '<p class="mui-ellipsis">';
										html +=  news.summary;
										html += '</p></div></a></li>';
				   					}
//		 		   					setTimeout(function() {
				   					$("#data-list").append(html);
				   					mui('#pullrefresh').pullRefresh().endPulldownToRefresh(); //refresh completed
//		 		   					}, 1500);
				   				}
				   			},
				   			error: function() {
				   				mui.alert('好像出错了哦~');
				   			}
				   		}); 
		          },
		          pullupRefresh:function(){
						console.info("====上拉加载===")
						var startPage = $("#data-list").attr("startPage");
						Common.getData({
				   			route: 'm/MNewsInfo.do?method=getListJson&startPage='+startPage,
				   			success: function(data) {
				   				var html = "";
				   				if(null != data.datas.newsInfoList){
				   					for(var x in data.datas.newsInfoList){
				   						var news = data.datas.newsInfoList[x];
				   						var url = 'm/MNewsInfo.do?method=view&uuid='+news.uuid;
										html += '<li class="mui-table-view-cell mui-media">';
										html += '<a href="${ctx}/'+url+'">';
										if(null != news.image_path){
											html += '<img class="mui-media-object mui-pull-left" src="${ctx}/'+news.image_path+'">';
										}
										html += '<div class="mui-media-body">';
										html +=  news.title;
										html += '<p class="mui-ellipsis">';
										html +=  news.summary;
										html += '</p></div></a></li>';
				   					}
				   					$("#data-list").attr("startPage",startPage++);
				   					
//		 		   					setTimeout(function() {
				   						$("#data-list").append(html);
				   						mui('#pullrefresh').pullRefresh().endPulldownToRefresh(); //refresh completed
//		 		   					}, 1500);
				   				}
				   			},
				   			error: function() {
				   				mui.alert('好像出错了哦~');
				   			}
				   		});
					}
		          
		    }
		  });
		
			
// 			function pulldownRefresh() {
// 				console.info("====下拉刷新===")
// 				Common.getData({
// 		   			route: 'm/MNewsInfo.do?method=getListJson',
// 		   			success: function(data) {
// 		   				var html = "";
// 		   				if(null != data.datas.newsInfoList){
// 		   					for(var x in data.datas.newsInfoList){
// 		   						var news = data.datas.newsInfoList[x];
// 		   						var url = 'm/MNewsInfo.do?method=view&uuid='+news.uuid;
// 								html += '<li class="mui-table-view-cell mui-media">';
// 								html += '<a href="${ctx}/'+url+'">';
// 								if(null != news.image_path){
// 									html += '<img class="mui-media-object mui-pull-left" src="${ctx}/'+news.image_path+'">';
// 								}
// 								html += '<div class="mui-media-body">';
// 								html +=  news.title;
// 								html += '<p class="mui-ellipsis">';
// 								html +=  news.summary;
// 								html += '</p></div></a></li>';
// 		   					}
// // 		   					setTimeout(function() {
// 		   					$("#data-list").append(html);
// 		   					mui('#pullrefresh').pullRefresh().endPulldownToRefresh(); //refresh completed
// // 		   					}, 1500);
// 		   				}
// 		   			},
// 		   			error: function() {
// 		   				mui.alert('好像出错了哦~');
// 		   			}
// 		   		});
				
// 			},
			
			
		</script>
</body>
</html>

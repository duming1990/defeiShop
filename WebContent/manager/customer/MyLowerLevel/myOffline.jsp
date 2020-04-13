<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div style="text-align:center;padding-bottom: 5px;">
<c:set var="tip" value="亲，下面的画布可以拖动和鼠标滚轮缩放哦！" />
<span class="label label-success"><i class="fa fa-info-circle"></i>&nbsp;温馨提示： ${tip}</span>
</div>
<div class="new-ct" id="viewOffline" style="height:1190px;width:740px;">
  
</div>

<script type="text/javascript" src="${ctx}/scripts/echarts/dist/echarts-all.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[
var myChart;
$(function(){
	   echartAjaxs("viewOffline","ajaxGetMyLowerLevelDataList");
	window.addEventListener('resize', function () {
		   var width = $(document.body).width();
		   //alert(width);
		   $("#viewOffline").width(width);
		   myChart.resize();
	});
});


function echartAjaxs(id,method){
    myChart = echarts.init(document.getElementById(id)); 
	var options = drawEcharts(myChart);
	    $.ajax({
	       type: "post",
	       async: true, //同步执行
	       url: encodeURI("MyLowerLevel.do?method="+ method),
	       dataType: "json", //返回数据形式为json
	       success: function (result) {
	           if (result) {
	               options.series[0].data =result.seriesData;
	               myChart.hideLoading();
	               myChart.setOption(options);
	               window.onresize = myChart.resize;
	           }else{
	        	   alert("查询数据为空!");
	           }
	       },
	       error: function (errorMsg) {
	    	   alert("请求出错，图表请求数据失败!");
	       }
	   }); 
}

function drawEcharts(myChart){
	myChart.showLoading({
	    text : "数据加载中...",
	    x:"center",
	    y:200
	});
	option = {
			tooltip : {
		        trigger: 'item',
		        formatter: function (params,ticket,callback) {
	                 var res="";
	                 res += '会员推广：' + params.data.user_par_levle + "层会员<br/>";
	                 res += '用户个人积分：' + params.data.cur_score + "<br/>";
	                 res += '用户总积分：' + params.data.user_score_sum + "<br/>";
	                 setTimeout(function (){
	                     callback(ticket, res);
	                 }, 0);
	                 return 'loading';
	             }
		    },
		    series : [
		        {
		            name:'树图',
		            type:'tree',
		            orient: 'vertical',  
		            hoverable: false,
		            roam: true,
		            symbolSize: 30,
		            rootLocation: {x: 'center',y: 20},
		            nodePadding: 50,
		            itemStyle: {
		                normal: {
		                    label: {
		                        show: true,
		                        position: 'bottom',
		                        formatter: "{b}",
		                        textStyle: {
		                            color: '#000',
		                            fontSize: 16
		                        }
		                    },
		                    lineStyle: {
		                        color: '#999',
		                        width: 1,
		                        type: 'broken' 

		                    }
		                },
		                emphasis: {
		                	color: '#4883b4',
		                    label: {
		                        show: false
		                    },
		                    borderWidth: 0
		                }
		            },data: []
		        }
		    ]
		};
	   return option;
}

//]]></script>
</body>
</html>
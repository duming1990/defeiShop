<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<link href="${ctx}/commons/styles/nav.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/index/css/btns.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>【${user_name_par}】的下三级用户</h3>
  </div>
   <div class="all">
      <ul class="nav nav-tabs" id="nav_ul_content">
          <li><a href="${ctx}/manager/customer/MyTeam.do?method=list&user_id=${af.map.user_id}"><span>列表查看</span></a></li>
          <li class="active"><a><span>图形查看</span></a></li>
          <li onclick="history.back();"><a class="tip-danger"><span>返回上页</span></a></li>
      </ul>
    </div>
<div style="text-align:center;padding: 5px;">
<c:set var="tip" value="亲，下面的画布可以拖动和滚轮缩放哦！" />
<span class="label label-success"><i class="fa fa-info-circle"></i>&nbsp;温馨提示： ${tip}</span>
</div>
<div class="new-ct" id="viewOffline" style="height:1190px;"></div> 
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/scripts/echarts/dist/echarts-all.js"></script>
<script type="text/javascript">//<![CDATA[
$(function(){
	   echartAjaxs("viewOffline","ajaxGetUserRelationDataList");
});

function echartAjaxs(id,method){
 var myChart = echarts.init(document.getElementById(id)); 
	var options = drawEcharts(myChart);
	    $.ajax({
	       type: "post",
	       async: true, //同步执行
	       url: encodeURI("MyTeam.do?method="+ method + "&user_id=${af.map.user_id}"),
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
	                 res += '手机号：' + params.data.mobile + "<br/>";
	                 res += '用户星级：' + params.data.user_level + "<br/>";
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

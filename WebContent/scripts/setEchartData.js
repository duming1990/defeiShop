function setDataEcharts(myChart,options,getDataUrl,needxAxis){
	 $.ajax({
	       type: "post",
	       async: false,
	       url: getDataUrl,
	       dataType: "json",
	       error: function (errorMsg) {
	           alert("请求出错，图表请求数据失败!");
	       },
	       success: function (result) {
	           if (result.ret == 1) {
	        	     if(needxAxis){
	        	    	 options.xAxis[0].data = eval(result.xAxis);
	        	     }
	                 options.series = eval(result.series);
	                 options.legend.data = eval(result.legend);
	                 myChart.hideLoading();
	                 myChart.setOption(options);
	           }else if(result.ret == 0){
	        	   alert("数据为空!");
	           }else{
	        	   alert("请求出错，图表请求数据失败!");
	           }
	       }
	 });
}

function drawEchartsLine(myChart){
	  //图表显示提示信息
		 myChart.showLoading({
	       text: "图表数据正在努力加载..."
	   });
		 //定义图表options
		  var options = {
				    title : {
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				    	data : []
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            mark : {show: true},
				            dataView : {show: true, readOnly: false},
				            magicType : {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,
				    xAxis : [
				        {
				            type : 'category',
				            data :[]
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value'
				        }
				    ],
				    series : []
				};
	   return options;
}
function drawEchartsPie(myChart){
	  //图表显示提示信息
		 myChart.showLoading({
	       text: "图表数据正在努力加载..."
	   });
		 //定义图表options
		  var options = {
				  title : {
   		        x:'center'
   		    },
   		    tooltip : {
   		        trigger: 'item',
   		        formatter: "{a} <br/>{b} : {c} ({d}%)"
   		    },
   		    legend: {
   		        orient : 'vertical',
   		        x : 'left',
   		        data:[]
   		    },
   		    toolbox: {
   		        show : true,
   		        feature : {
   		            mark : {show: true},
   		            dataView : {show: true, readOnly: false},
   		            magicType : {
   		                show: true, 
   		                type: ['pie', 'funnel'],
   		                option: {
   		                    funnel: {
   		                        x: '25%',
   		                        width: '50%',
   		                        funnelAlign: 'left',
   		                        max: 1548
   		                    }
   		                }
   		            },
   		            restore : {show: true},
   		            saveAsImage : {show: true}
   		        }
   		    },
   		    calculable : true,
   		    series :  []
		   };
	   return options;
}
	    
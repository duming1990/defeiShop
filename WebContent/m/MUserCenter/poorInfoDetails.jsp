<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>扶贫信息</title>
<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" name="viewport"/>
<meta content="yes" name="apple-mobile-web-app-capable"/>
<meta content="black" name="apple-mobile-web-app-status-bar-style"/>
<meta content="telephone=no" name="format-detection"/>
<jsp:include page="../_public_in_head.jsp" flush="true" />
<style>
	.mui-control-content {
		background-color: white;
		min-height: 550px; 
	}
	.mui-control-content .mui-loading {
		margin-top: 50px;
	}
	.mui-input-row .show{
	    padding: 11px 15px;
        display: inline-block;
    }
    h4{
    	padding:3px;
    }
</style>
</head>
<body>
<div id="app" v-cloak>
	<header class="index app_hide" v-show="showHeader">
		<div class="c-hd">
			<section class="back" id="back">
				<a class="mui-action-back"><i></i></a>
			</section>
			<section class="hd-title" style="width:80%;text-align:center;">${header_title}</section>
		</div>
	</header>
  <div class="mui-content mui-input-group">
			<div id="slider" class="mui-slider">
				<div id="sliderSegmentedControl" class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
					<a class="mui-control-item" href="#item1mobile">
						基本信息
					</a>
					<a class="mui-control-item" href="#item2mobile">
						家庭成员
					</a>
					<a class="mui-control-item" href="#item3mobile">
						帮扶措施
					</a>
					<a class="mui-control-item" href="#item4mobile">
						帮扶责任人
					</a>
				</div>
				<div id="sliderProgressBar" class="mui-slider-progress-bar mui-col-xs-3"></div>
				<div class="mui-slider-group">
					<div id="item1mobile" class="mui-slider-item mui-control-content mui-active">
						<div id="scroll1" class="mui-scroll-wrapper">
						 <div class="mui-scroll">
							<div class="mui-input-row">
								<label>户主姓名</label>
								<span class="show">{{entity.real_name}}</span>
							</div>
							<div class="mui-input-row">
								<label>性别</label>
								<span class="show" v-if = "entity.sex == 0" >男</span>
								<span class="show" v-if = "entity.sex == 1" >女</span>
							</div>
							<div class="mui-input-row">
								<label>家庭人口</label>
								<span class="show">{{entity.family_num}}</span>
							</div>
							<div class="mui-input-row">
								<label>民族</label>
								<span class="show">{{entity.nation}}</span>
							</div>
							<div class="mui-input-row">
								<label>出生日期</label>
								<span class="show">{{entity.brithday | formatDateYmd}}</span>
							</div>
							<div class="mui-input-row">
								<label>家庭住址</label>
								<label class="show" style="padding: 2px;width: 48%; padding-left: 14px;">{{entity.addr}}</label>
							</div>
							<div class="mui-input-row">
								<label>致贫原因</label>
								<span class="show">{{entity.poor_reason}}</span>
							</div>
							<div class="mui-input-row">
								<label>耕地面积</label>
								<span class="show">{{entity.gendi_arear}}(亩)</span>
							</div>
							<div class="mui-input-row">
								<label>林地面积</label>
								<span class="show">{{entity.lindi_arear}}(亩)</span>
							</div>
							<div class="mui-input-row">
								<label>牧草地面积</label>
								<span class="show">{{entity.mucaodi_arear}}(亩)</span>
							</div>
							<div class="mui-input-row">
								<label>住房面积</label>
								<span class="show">{{entity.house_arear}}(平方米)</span>
							</div>
<!-- 							<div class="mui-input-row"> -->
<!-- 								<label>识别贫困户时间</label> -->
<!-- 								<span class="show">{{entity.jian_dang_date | formatDateYmd}}</span> -->
<!-- 							</div> -->
<!-- 							<div class="mui-input-row"> -->
<!-- 								<label>计划脱贫时间</label> -->
<!-- 								<span class="show">{{entity.tuo_pin_plan_date | formatDateYmd}}</span> -->
<!-- 							</div> -->
						</div>
					  </div>
					</div>
					<div id="item2mobile" class="mui-slider-item mui-control-content">
						<div id="scroll2">
							   <div class="mui-card">
								<ul class="mui-table-view mui-table-view-striped mui-table-view-condensed">
							        <li class="mui-table-view-cell" v-for="(item,index) in datas.familyList">
							            <div class="mui-table">
							                <div class="mui-table-cell mui-col-xs-10">
							                    <h4 class="mui-ellipsis">{{item.family_name.substring(1,0)}}**
							                    <span class="mui-h5" style="float: right;">年龄：{{item.age}}</span></h4>
							                    <h5>户主关系：{{item.relation_ship}}
							                    <span class="mui-h5" style="float: right;">劳动能力：{{item.work_power}}</span>
							                    </h5>
							                </div>
							            </div>
							        </li>
							    </ul>
							 </div>
						</div>
					</div>
					<div id="item3mobile" class="mui-slider-item mui-control-content">
						<div id="scroll3">
							<div class="mui-card">
								<ul class="mui-table-view mui-table-view-striped mui-table-view-condensed">
							        <li class="mui-table-view-cell" v-for="(item,index) in datas.cuoshiList">
							            <div class="mui-table">
							                <div class="mui-table-cell mui-col-xs-10">
							                    <h4 class="mui-ellipsis">{{item.dan_wei_name}}
							                    <span class="mui-h5" style="float: right;">{{item.help_date | formatDateYmd}}</span></h4>
<!-- 							                    <h5>帮扶时间：{{item.help_date | formatDateYmd}}</h5> -->
							                    <p class="mui-h6 mui-ellipsis" style="white-space: inherit;">项目内容：{{item.content}}</p>
							                </div>
							            </div>
							        </li>
							    </ul>
							 </div>
						</div>
					</div>
					<div id="item4mobile" class="mui-slider-item mui-control-content">
						<div id="scroll4">
							<div class="mui-card">
								<ul class="mui-table-view mui-table-view-striped mui-table-view-condensed">
							        <li class="mui-table-view-cell" v-for="(item,index) in datas.pZeRenList">
							            <div class="mui-table">
							                <div class="mui-table-cell mui-col-xs-10">
							                    <h4 class="mui-ellipsis">{{item.name}}</h4>
							                    <h5>单位名称：{{item.dan_wei_name}}</h5>
							                    <p class="mui-h6 mui-ellipsis" style="white-space: inherit;">单位隶属关系：{{item.dan_wei_relation}}</p>
							                </div>
							            </div>
							        </li>
							    </ul>
							 </div>
						</div>
					</div>
				</div>
			</div>
		</div>
</div>
<script src="https://res.wx.qq.com/open/js/jweixin-1.3.2.js"></script>
<script type="text/javascript">
       var vm = new Vue({
           el: '#app',
           data: {
               ctx:Common.api,
               poorInfo: '',
               datas:"",
               entity:"",
               showHeader:true,
           },
           mounted: function() {
               this.$nextTick(function() {
            	   ready()
               });
           },
           updated: function() {
        	   $('.mui-scroll-wrapper').scroll({
					indicators: true //是否显示滚动条
				});
           },
           methods: {
               getAjaxData: function() {
                   Common.getData({
                       route: 'm/MUserCenter.do?method=getPoorDetailsAjaxData&poor_id=${af.map.poor_id}',
                       success: function(data) {
                           if (data.code == 0) {
                               mui.alert(data.msg);
                               return false;
                           } else if (data.code == 1) {
                               vm.datas = data.datas;
                               vm.entity = data.datas.entity;
                           }
                       },
                       error: function() {
                           mui.alert('好像出错了哦~');
                       }
                   });
               },
               getAjaxDataMin: function() {
                   Common.getData({
                	   url:'https://www.9tiaofu.com/',
                       route: 'm/MUserCenter.do?method=getPoorDetailsAjaxData&poor_id=${af.map.poor_id}',
                       success: function(data) {
                           if (data.code == 0) {
                               mui.alert(data.msg);
                               return false;
                           } else if (data.code == 1) {
                               vm.datas = data.datas;
                               vm.entity = data.datas.entity;
                           }
                       },
                       error: function() {
                           mui.alert('好像出错了哦~');
                       }
                   });
               }
           }
       });
       
       
       function ready() {
    	   if(window.__wxjs_environment === 'miniprogram'){
      			vm.showHeader = false;
				vm.getAjaxDataMin();
      		 }else{
      			vm.getAjaxData();
      		 }
   		}
</script>
</body>
</html>
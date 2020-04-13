function getData(is_hd) {
	var ajax_url = ctx + "/m/MSearch.do?method=getPdListJson&" + $(".attrForm").serialize();
	if(is_hd){
		ajax_url = ctx + "/m/MIndexHd.do?method=getPdListJson&" + $(".attrForm").serialize();
	}
	$('.conter').dropload({
        scrollArea : window,
        autoLoad : true,     
        loadDownFn : function(me){
        	var page = $("#ul_data").attr('data-page');
        	page = Number(page);
            $.ajax({
                type: 'GET',
                url: ajax_url,
                data: "startPage=" + page,
                dataType: 'json',
                success: function(data){
                	var html = "";
                	if(null != data.datas.dataList){
                		 var dataList = eval(data.datas.dataList);
         				$.each(dataList, function(i,data){
         					var url = app_path + "/m/MEntpInfo.do?id=" + data.id;
         					html += '<li>';
         					html += '<div>';
         					html += '<a onclick="goUrl(\''+url+'\')">';
         					html += '<p class="img">';
         					html +=' <img data-original="'+data.main_pic+'@s400x400" class="lazy_'+page+'"/></p>';
         					html += '<p class="name_my">' + data.comm_name + '</p>';
         					html += ' <h6 class="title">';
         					if(null!=data.commZyName){
         						html +='<span class="commInfo_from">'+ data.commZyName +'</span>';
         					}
         					html += '</h6>';
         					html += '  <p class="price"><span class="red">¥' + data.sale_price + '</span>';
         					html += '  <span  class ="saleCount">已售' + data.sale_count + '</span>';
         					html += '  </p>';
         					html += '  </a>';
         					html += '  </div></li>';
                         });
                	}
                   
    				setTimeout(function(){
    					$('#ul_data').append(html);
    					lazyload(page - 1);
    					
                        me.resetload();
                    },500); 
                    page += 1;
			        $("#ul_data").attr('data-page',page);
                   	if (data.code == 2) {
                         me.lock();// 锁定
                         me.noData(); // 无数据
   					} 
                },
            });
        },
        domUp : {// 上方DOM                                                       
            domClass   : 'dropload-up',
            domRefresh : '<div class="dropload-refresh"><i class="fa fa-long-arrow-down"></i>&nbsp;下拉刷新</div>',
            domUpdate  : '<div class="dropload-update"><i class="fa fa-long-arrow-up"></i>&nbsp;释放更新</div>',
            domLoad    : '<div class="dropload-load"><span class="loading"></span>刷新中...</div>'
        },
        loadUpFn : function(me){
            // 为了测试，延迟1秒加载
            setTimeout(function(){
                me.resetload();
                me.unlock();
                me.noData(false);
                Common.loading();
 	       		window.setTimeout(function () {
 	       			window.location.reload();
 	       		}, 1000);
            },500);
         },
        threshold : 50
    });
}
function getEntpList() {
	var ajax_url = ctx + "/m/MSearch.do?method=getEntpListJson&" + $(".attrForm").serialize();
	$('.conter').dropload({
        scrollArea : window,
        autoLoad : true,     
        loadDownFn : function(me){
        	var page = $("#ul_data").attr('data-page');
        	page = Number(page);
            $.ajax({
                type: 'GET',
                url: ajax_url,
                data: "startPage=" + page,
                dataType: 'json',
                success: function(data){
                	var html = "";
                	if(null != data.datas.dataList){
                		 var dataList = eval(data.datas.dataList);
         				$.each(dataList, function(i,data){
         					var url = ctx + "/m/MEntpInfo.do?method=index&entp_id=" + data.id;
         					console.log(url)
        					html += '<section class="list-ul list-sort"> <a onclick="goUrl(\''+url+'\')" class="z">';
        					html += '<div class="list-item">';
        					html += '<div class="box-flex pic"> <img class="lazy_'+ page +'" data-original="' + data.entp_logo_400 + '" style="display: inline;"></div>';
        					html += '       <div class="info">';
        					html += '         <h2 class="title">' + data.entp_name + '</h2>';
        					html += '        <div class="main">';
        					html += '          <p class="box-flex keyword">' + data.entp_addr + '</p>';
        					if(null != data.distance){
        						html += '          <p class="num bought"><span class="site">距离</span>' + data.distance + '</p>';
        					}
        					html += '        </div>';
        					html += '        <div class="main">';
        					html += '          <p class="box-flex keyword">' + data.entp_tel + '</p>';
        					html += '          <p class="num bought"><span class="site">销售额</span>' + data.sum_sale_money + '</p>';
        					html += '        </div>';
        					html += '      </div>';
        					html += '    </div>';
        					html += '   </a> </section>';
                         });
                	}
                   
    				setTimeout(function(){
    					$('#ul_data').append(html);
    					
    					lazyload(page - 1);
    					
                        me.resetload();
                    },500); 
                    page += 1;
			        $("#ul_data").attr('data-page',page);
                   	if (data.code == 2) {
                         me.lock();// 锁定
                         me.noData(); // 无数据
   					} 
                },
            });
        },
        domUp : {// 上方DOM                                                       
            domClass   : 'dropload-up',
            domRefresh : '<div class="dropload-refresh"><i class="fa fa-long-arrow-down"></i>&nbsp;下拉刷新</div>',
            domUpdate  : '<div class="dropload-update"><i class="fa fa-long-arrow-up"></i>&nbsp;释放更新</div>',
            domLoad    : '<div class="dropload-load"><span class="loading"></span>刷新中...</div>'
        },
        loadUpFn : function(me){
            // 为了测试，延迟1秒加载
            setTimeout(function(){
                me.resetload();
                me.unlock();
                me.noData(false);
                Common.loading();
 	       		window.setTimeout(function () {
 	       			window.location.reload();
 	       		}, 1000);
            },500);
         },
        threshold : 50
    });
}

function appendMoreEntp() {

	Common.loading();
	var page = $("#appendMore").attr('data-pages');
	page = Number(page);
	var ajax_url = ctx + "/m/MSearch.do?method=getEntpListJson&" + $(".attrForm").serialize();
	$.ajax({
		type: "POST",
		url: ajax_url,
		data: 'startPage=' + page +'&' + $(".attrForm").serialize(),
		dataType: "json",
		error: function(request, settings) {},
		success: function(datas) {
			var html = "";
			$("#appendMore").hide();
			M._hide();
			if (datas.ret == 1) {
				var dataList = eval(datas.dataList);
				$.each(dataList, function(i,data){  
					var url = ctx + "/m/MEntpInfo.do?method=index&entp_id=" + data.id;
					
					html += '<section class="list-ul list-sort"> <a onclick="goUrl(\''+url+'\')" class="z">';
					html += '<div class="list-item">';
					html += '<div class="box-flex pic"> <img class="lazy_'+ page +'" data-original="' + data.entp_logo_400 + '" style="display: inline;"></div>';
					html += '       <div class="info">';
					html += '         <h2 class="title">' + data.entp_name + '</h2>';
					html += '        <div class="main">';
					html += '          <p class="box-flex keyword">' + data.entp_addr + '</p>';
					if(null != data.distance){
						html += '          <p class="num bought"><span class="site">距离</span>' + data.distance + '</p>';
					}
					html += '        </div>';
					html += '        <div class="main">';
					html += '          <p class="box-flex keyword">' + data.entp_tel + '</p>';
					html += '          <p class="num bought"><span class="site">销售额</span>' + data.sum_sale_money + '</p>';
					html += '        </div>';
					html += '      </div>';
					html += '    </div>';
					html += '   </a> </section>';
					
				});
				page += 1;
				$("#appendMore").attr('data-pages',page);
				if (datas.appendMore == 1) {
					$("#appendMore").show();
				} else {
					mui.toast("全部加载完成");
				}
				$("#div_data").append(html);
				
				lazyload(page - 1);
				
			} else {
				mui.toast(datas.msg);
			}
			if (datas.ret == 2) {
				html = "<div>"+datas.msg+"</div>";
			}
		
		}
	});	
}

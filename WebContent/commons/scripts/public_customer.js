$(document).ready(function(){
	$("#admincp-container-left").find(".nav-tabs").each(function(){
		$(this).find("dl").each(function(){
			$(this).click(function(){
				$(this).addClass("active").siblings().removeClass("active");
			});
		});
	});
	
	$("#foldSidebar").click(function(){
		var $admincpContainer = $("#admincp-container");
		if($admincpContainer.hasClass("unfold")){
			$admincpContainer.addClass("fold").removeClass("unfold");
			$("#admincp-container-left").find(".sub-menu").css("top","0px");
			$admincpContainer.find(".fa").css("font-size","16px");
		}else{
			$admincpContainer.addClass("unfold").removeClass("fold");
			$admincpContainer.find(".fa").css("font-size","24px");
			$("#admincp-container-left").find(".sub-menu").each(function(){
				var topValue = $(this).attr("data-top");
				$(this).css("top","-"+ topValue +"px");
			});
		}
	});
	
	
	//更换颜色
	
	//布局换色设置
    var bgColorSelectorColors = [{ c: '#981767', cName: '' }, { c: '#AD116B', cName: '' }, { c: '#B61944', cName: '' }, { c: '#AA1815', cName: '' }, { c: '#C4182D', cName: '' }, { c: '#D74641', cName: '' }, { c: '#ED6E4D', cName: '' }, { c: '#D78A67', cName: '' }, { c: '#F5A675', cName: '' }, { c: '#F8C888', cName: '' }, { c: '#F9D39B', cName: '' }, { c: '#F8DB87', cName: '' }, { c: '#FFD839', cName: '' }, { c: '#F9D12C', cName: '' }, { c: '#FABB3D', cName: '' }, { c: '#F8CB3C', cName: '' }, { c: '#F4E47E', cName: '' }, { c: '#F4ED87', cName: '' }, { c: '#DFE05E', cName: '' }, { c: '#CDCA5B', cName: '' }, { c: '#A8C03D', cName: '' }, { c: '#73A833', cName: '' }, { c: '#468E33', cName: '' }, { c: '#5CB147', cName: '' }, { c: '#6BB979', cName: '' }, { c: '#8EC89C', cName: '' }, { c: '#9AD0B9', cName: '' }, { c: '#97D3E3', cName: '' }, { c: '#7CCCEE', cName: '' }, { c: '#5AC3EC', cName: '' }, { c: '#16B8D8', cName: '' }, { c: '#49B4D6', cName: '' }, { c: '#6DB4E4', cName: '' }, { c: '#8DC2EA', cName: '' }, { c: '#BDB8DC', cName: '' }, { c: '#8381BD', cName: '' }, { c: '#7B6FB0', cName: '' }, { c: '#AA86BC', cName: '' }, { c: '#AA7AB3', cName: '' }, { c: '#935EA2', cName: '' }, { c: '#9D559C', cName: '' }, { c: '#C95C9D', cName: '' }, { c: '#DC75AB', cName: '' }, { c: '#EE7DAE', cName: '' }, { c: '#E6A5CA', cName: '' }, { c: '#EA94BE', cName: '' }, { c: '#D63F7D', cName: '' }, { c: '#C1374A', cName: '' }, { c: '#AB3255', cName: '' }, { c: '#A51263', cName: '' }, { c: '#7F285D', cName: ''}];
    $("#trace_show").click(function(){
        $("div.bgSelector").toggle(300, function() {
            if ($(this).html() == '') {
                $(this).sColor({
                    colors: bgColorSelectorColors,  // 必填，所有颜色 c:色号（必填） cName:颜色名称（可空）
                    colorsWidth: '50px',  // 必填，颜色的高度
                    colorsHeight: '31px',  // 必填，颜色的高度
                    curTop: '0', // 可选，颜色选择对象高偏移，默认0
                    curImg: ctx + '/styles/admin/images/cur.png',  //必填，颜色选择对象图片路径
                    form: 'drag', // 可选，切换方式，drag或click，默认drag
                    keyEvent: true,  // 可选，开启键盘控制，默认true
                    prevColor: true, // 可选，开启切换页面后背景色是上一页面所选背景色，如不填则换页后背景色是defaultItem，默认false
                    defaultItem: ($.cookie('bgColorSelectorPosition') != null) ? $.cookie('bgColorSelectorPosition') : 22  // 可选，第几个颜色的索引作为初始颜色，默认第1个颜色
                });
            }
        });//切换显示
    });    
    if ($.cookie('bgColorSelectorPosition') != null) {
        $('body').css('background-color', bgColorSelectorColors[$.cookie('bgColorSelectorPosition')].c);
    } else {
        $('body').css('background-color', bgColorSelectorColors[22].c);
    } 
	
	var tabTitleHeight = 33; // 页签的高度
	$.fn.initJerichoTab({
        renderTo: '#right', uniqueId: 'jerichotab',
        contentCss: { 'height': $('#right').height() + tabTitleHeight },
        tabs: [], loadOnce: true, tabWidth: 120, titleHeight: tabTitleHeight
    });
	
	openItem(true);
    
	
    $("#admin-manager-btn").click(function(){
    	$("#updatePass").toggle();
    });
    
});


function updatePassDia(){
	$.dialog({
		title:  "修改密码",
		width:  500,
		height: 400,
        lock:true ,
		content:"url:"+ ctx +"/manager/admin/Profile.do"
	});
}

function loginOut(){
	$.cookie("customer_parParId_parId_sonId_cookie", null);
	location.href='../login.do?method=logout';
}

function setCookieAndOpenUrl(url,obj,flag,goUrl){
	var modId = $(obj).attr("data-modid");
	var parParId,parId;
	if(flag == 3){//证明最底层点击的
		 parId = $(obj).parent().parent().parent().attr("data-modid");
		 parParId = $(obj).parent().parent().parent().parent().attr("data-modid");
	}
	if(flag == 2){//证明选择最左边二级
		parParId = $(obj).parent().parent().attr("data-modid");
		parId = $(obj).parent().attr("data-modid");
		modId = $(obj).parent().find("dd ul li:first").attr("data-modid");
		url = $("#sonSys_" + modId).attr("data-url");
	}
	if(flag == 1){//证明选择最顶级
		parParId = $(obj).attr("data-id");
		parId = $("#parParSys_" + parParId).find("dl:first").attr("data-modid");
		modId = $("#parParSys_" + parParId).find("dl:first").find("dd ul li:first").attr("data-modid");
		url = $("#sonSys_" + modId).attr("data-url");
	}
	
	var parParId_parId_sonId_cookie = parParId +"," + parId +"," + modId + "," + url;
	if ($.isFunction($.cookie)) $.cookie("customer_parParId_parId_sonId_cookie", parParId_parId_sonId_cookie, { path: '/' });
	if(goUrl){
		openItem(false);
		//$('#workspace').attr('src',url);
		addTab($("#sonSys_" + modId) , url, true);
	}
}


function openItem(is_first_init){
	var parParId,parId,modId,src;
	var args =  $.cookie("customer_parParId_parId_sonId_cookie");
	if(null != args){ // 如果cookie 不为空
		var spl = args.split(",");
		parParId = spl[0];
		parId = spl[1];
		modId = spl[2];
		url = spl[3];
		$("#parParSysTop_" + parParId).addClass("active").siblings().removeClass("active");
		$("#parParSys_" + parParId).show().siblings().hide();
		$("#parSys_" + parId).addClass("active").siblings().removeClass("active");
		$("#sonSys_" + modId).addClass("active").siblings().removeClass("active");
	}else{// 如果cooike 为空 则
		parParId = $("#nc-row li:first").attr("data-id");
		var $mod_id = $("#parParSys_" + parParId).find("dl:first").find("dd ul li:first")
		url = $mod_id.attr("data-url");
		modId = $mod_id.attr("data-modid");
	}
	if(is_first_init){
		addTab($("#sonSys_" + modId) , url, true);
		//$('#workspace').attr('src',url);
	}
}

function addTab($this, url, refresh){
	$(".jericho_tab").show();
	$("#workspace").hide();
	$.fn.jerichoTab.addTab({
        tabFirer: $this,
        title: $this.text(),
        closeable: true,
        data: {
            dataType: 'iframe',
            dataLink: url
        }
    }).loadData(refresh);
	return false;
} 

function iFrameHeight(obj) {   
	var id = $(obj).attr("id");
	var ifm = document.getElementById(id); 
	var subWeb = document.frames ? document.frames[id].document : ifm.contentDocument;   
	if(ifm != null && subWeb != null) {
		$("#"+id).css({  height: subWeb.body.scrollHeight+250 })
	}   
}  

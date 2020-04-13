///显示隐藏
$(document).ready(function(){
	$(".my-qr").hover(function(){ 
	    $(this).find(".show-qr").toggle();
    });
	
    $("#pub_dynamic").click(function(){ 
    	if(vm.ui == null || vm.ui == ""){
			loginTip();
		}else{
			if(vm.datas.applyVillageInfo != null && vm.datas.applyVillageInfo.audit_state == 1){
				$("#type").val(1);
		    	vm.showPub = true;
			    $(".panel-dt").fadeIn(200);
			}else{
				mui.toast('请先加入该村！');
			}
		}
    });
    
    $("#pub_comm").click(function(){ 
    	if(vm.ui == null || vm.ui == ""){
			loginTip();
		}else{
			if(vm.datas.applyVillageInfo != null && vm.datas.applyVillageInfo.audit_state == 1){
				$("#type").val(2);
		    	vm.showPub = false;
			    $(".panel-dt").fadeIn(200);
			}else{
				mui.toast('请先加入该村！');
			}
		}
    });
    
    $(".close-panel-dt").click(function(){ 
	    $(".panel-dt").fadeOut(200);
    });
    
    function loginTip() {
		mui.toast('请先登录！');
		login();
	}
    
	function login(){
		window.setTimeout(function () { 
			window.location.href=vm.ctx+"login.shtml?returnUrl=VillageIndex.do";
		}, 1500);
	}
})


$(function(){
	//专题页轮播
	$('#owl-demo').owlCarousel({
		navigation: true,
		navigationText: ["<img src='images/jdzt/zuo.png'>","<img src='images/jdzt/you.png'>"],
		items:3,
        pagination: true,
        navigation: true
	});
	//相关业务推荐
	$(".ywtj_oo ul").children().children("a").animate({opacity:"0"});
	$(".ywtj_oo .ulo").hover(function(){
		$(this).children(".li1").stop().animate({top:"97px"});
		$(this).children().children("a").stop().animate({opacity:"1"})
	},function(){
		$(this).children(".li1").stop().animate({top:"130px"});
		$(this).children().children("a").stop().animate({opacity:"0"})
	})
	$(".ywtj_oo .ult").hover(function(){
		$(this).children(".li2").stop().animate({top:"75px"});
		$(this).children().children("a").stop().animate({opacity:"1"})
	},function(){
		$(this).children(".li2").stop().animate({top:"106px"});
		$(this).children().children("a").stop().animate({opacity:"0"})
	})
    //表单
    $(".btn").click(function(){
		$(".cx").slideToggle(200)
		$(".jiant").toggleClass("fan");
	})
	$(".cx a").each(function(index){
		$(this).on('click',function(){
			$(".jiant").removeClass("fan");
			$(".btn input").val($(".cx a").eq(index).html());
			$(".cx").hide();
			$(this).addClass('active').siblings().removeClass('active');
		})
	})
	$(".djd").click(function(){
		$(".mengb").fadeIn();
		$("body").css("overflow","hidden");
		$(".gsmc").val($(".mc").val())
	})
	$(".mengb").click(function(){
		$(this).fadeOut();
		$("body").css("overflow","visible");
	})
	$(".form2").click(function(e){
		e.stopPropagation()
	})
	$(".gsmc").blur(function(){
		$(".cuow1").removeClass("alpha");
		$(this).css("border-bottom","1px solid #eee");
	})
	$(".cuow1").click(function(){
		$(".gsmc").val("");
		$(".gsmc").focus();
	})
	$(".tell").blur(function(){
		$(".cuow2").removeClass("alpha");
		$(this).css("border-bottom","1px solid #eee");
	})
	$(".cuow2").click(function(){
		$(".tell").val("");
		$(".tell").focus();
	})
	$(".nem").blur(function(){
		$(".cuow3").removeClass("alpha");
		$(this).css("border-bottom","1px solid #eee");
	})
	$(".cuow3").click(function(){
		$(".nem").val("");
		$(".nem").focus();
	})
});
//表单
$(".gsmc").focus(function(){
	$(".gsmc").css("border-bottom","1px solid #000");
})
function inpo(){
    	if($(".gsmc").val() == ""){
			$(".cuow1").removeClass("alpha");
		}else{
			$(".cuow1").addClass("alpha");
		}
    }
$(".tell").focus(function(){
	$(".tell").css("border-bottom","1px solid #000");
})
function inpt(){
    	if($(".tell").val() == ""){
			$(".cuow2").removeClass("alpha");
		}else{
			$(".cuow2").addClass("alpha");
		}
    }
$(".nem").focus(function(){
	$(".nem").css("border-bottom","1px solid #000");
})
function inps(){
    	if($(".nem").val() == ""){
			$(".cuow3").removeClass("alpha");
		}else{
			$(".cuow3").addClass("alpha");
		}
    }
//常见问答
	$(document).ready(function(){
		    //限制字符个数
		    $(".csfw_w_x .sig .p2 span").each(function(){
		        var maxwidth=128;
		        if($(this).text().length>maxwidth){
		            $(this).text($(this).text().substring(0,maxwidth));
		            $(this).html($(this).html()+'...');
		        }
		    });
	});
function bd(){
		if($(".gsmc").val() == ""){
			alert("集团名称不能为空");
			return false;
		}
		var reg = $(".form2 .tell").val();
		if(!(/^1[34578]\d{9}$/g.test(reg))){
			alert("请输入正确的联系方式");
			return false;
		}
		var reg1 = $(".form2 .nem").val();
		if(!(/^[\u4e00-\u9fa5]+$/.test(reg1))){
			alert("请输入中文名")
			return false;
		}else{
			$.ajax({
				cache:false,
				"type": "POST",
                "url":"22.php?&t="+new Date().getTime(),
                "data":$('.formo').serialize(),
                "error": function(XMLHttpRequest, textStatus, errorThrown){
                           $("#btn").val('提交失败');
                    },
                 "success": function() {
                 	      $(".duig").show();
                          $("#btn").val('提交成功，工作人员将30分钟内与您联系');
                          $("#btn").css({
                          	"text-align":"right",
                          	"padding-right":"35px"
                          	})
                          setTimeout(function(){
                            $("input[type=reset]").trigger("click");
                            $("#btn").val('获取查询结果');
                            $(".duig").hide();
                            $("#btn").css({
                          	"text-align":"center"
                          	});
                          	$(".gsmc").val("");
                          	$(".tell").val("");
                          	$(".nem").val("");
                          },3000);
                    }
			})
		}
		
	}

   
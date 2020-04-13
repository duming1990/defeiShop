$(function(){
/**
 * 此对象,适用于新版列表页的删除特效
 * 包括： 我的浏览历史、收藏、已付款订单、未付款订单
 */
var Order = {

    /**
     * 点击删除按钮，执行的操作
     */
    showCheckBox: function(){
        $('#flow_buybox').show();
        $('ul.list-ul').addClass('list-del disabled');
        $("#order-del-btn").text('取消').removeClass('del');
        $('input#del-true-btn').bind('click', Order._del);
        
        $('.list-ul').find('a').addClass('un-click');
		
		// tips show ,from page[exec_page=order_list]
		if(typeof exec_page != 'undefined' && exec_page === 'order_list' && typeof ors != 'undefined' && ors != 1){
			Order.showTipsDel();
		}
    },
    
    /**
     * 点击取消按钮，执行的操作
     */
    hideCheckBox: function(){
        $('#flow_buybox').hide();
        $('ul.list-ul').removeClass('list-del disabled');
        $("#order-del-btn").text('删除').addClass('del');
        $('i.check-icon-toggle').removeClass('select');
        Order.checkCanDel();
        $('.list-ul').find('a').removeClass('un-click');
    },
	
	/**
	 * 订单列表页面 - 已付款订单删除时，吐司提示 ：未消费和退款中的订单不支持删除
	 */
	showTipsDel: function(){
		var html = '<div class="tips-prompt">\
			<span class="name">提示：未消费和退款中的订单不支持删除</span>\
			<span class="bg"></span>\
		</div>';
		$(html).appendTo($('body')).fadeIn();
		setTimeout(function(){
			$('div.tips-prompt').fadeOut();
		},2000);
	},
    
    /**
     * toggle
     *
     */
    checkIconToggle: function(){
        $(this).toggleClass('select');
        Order.checkCanDel();
    },
    
    /**
     * 检测删除按钮置灰处理
     * 并计算删除的数量
     */
    checkCanDel: function(){
        var checkedObj = $('i.select');
        var inputObj = $('input#del-true-btn');
        if (checkedObj.length == 0) {
            inputObj.addClass('disabled').attr('disabled', 'disabled').val('删除(0)');
        }
        else {
            inputObj.removeAttr('disabled').removeClass('disabled').val('删除(' + checkedObj.length + ')');
        }
    },
    
    /**
     * 提交删除按钮的 锁定于解锁
     * @param {Object} opt
     */
    delBtn: function(opt){
        var obj = $('input#del-true-btn');
        if (opt == 'lock') {
            obj.val('删除中...').attr('disabled', 'disabled');
        }
        else {
            obj.removeAttr('disabled');
            this.checkCanDel();
        }
    },
    
    /**
     * 删除动作执行入口
     */
    _del: function(){
    
        // 获取要删除的项目			
        var items = '';
        var checkedObj = $('i.select');
        
        if (checkedObj.length < 1) 
            return false;
        
        checkedObj.each(function(i){
            items += ',' + $(this).attr('data-id');
        });
        
        items = items.substr(1);
        
        // 删除类型判断
        var delType = $(this).attr('data-type');
        
		if($.inArray(delType,['payed','unPay','history','shoucang','shoucang_sp']) === -1) return false;
		
		var postParams = {};
		var args = {'msg':'','ajaxUrl':'','delType':delType};
		
		if (delType == 'payed' || delType == 'unPay') {
            postParams = {'delTrades': items,'delType': delType,'act': 'del'};
			args.ajaxUrl = '/ajax/aj_delete_myorder.php';
        }else{
			postParams = {'edit': 'del','edit_id': items,'suid': $("input[name='suid']").val()};
			if(delType == 'history'){
				args.msg = '没有最近浏览数据';
				args.ajaxUrl = '/ajax/dohistory.php';
			}else if(delType == 'shoucang'){
				args.msg = '您还没有添加收藏';
				args.ajaxUrl = '/ajax/doshoucang.php';
			}else if(delType == 'shoucang_sp'){
				args.msg = '您还没有收藏商家';
				args.ajaxUrl = '/ajax/doshoucang.php?source=sp';
			}
		}
		
        Order.delItems(postParams, checkedObj,args);
    },
	
	// 浏览历史、收藏商家、收藏商品、待付款、已付款列表删除
	delItems: function(postParams,checkedObj,args){
		Order.delBtn('lock');
        setTimeout(function(){
            $.ajax({
                type: "post",
                url: args.ajaxUrl,
                data: postParams,
                dataType: 'json',
                async: false,
                success: function(data){
					
					if(args.delType == 'payed' || args.delType == 'unPay'){
						if (data.status == 1) Order.removeItems(checkedObj);
                    	else alert(data.msg);
					}else{
						if(data.code == 5 || data.code == 15){
							Order.removeItems(checkedObj);
	                        Order.EmptyMsg(args.msg);
						}else{
							alert('操作失败,请重试');
						}
					}
					
                }
            })
            Order.delBtn('unlock');
        }, 10)
	},
    
    /**
     * 浏览历史为空时,显示
     */
    EmptyMsg: function(msg){
        if ($("ul.list-ul > li").length == 0) {
            $(".list-ul").html("<div style='background:#fff; padding:15px; margin-bottom:10px;'>" + msg + "</div>");
        }
    },
    
    /**
     * 点击删除后，页面中a标签的点击事件将返回false
     */
    unClick: function(msg){
    
        if ($(this).hasClass('btn-combg')) {
            var checkIcon = $(this).parent().find('div.info').find('i.check-icon-toggle');
            if (checkIcon.length != 0) {
                checkIcon.trigger('click');
            }
        }
        
        return false;
    },
    
    /**
     * 移除 列表项
     * @param {Object} checkedObj
     */
    removeItems: function(checkedObj){
        checkedObj.each(function(i){
            $('li.li_' + $(this).attr('data-id')).remove();
        });
        $('#order-del-btn').trigger('click');
        $('.list-ul').find('a').removeClass('un-click');
        
        // 删除成功后判断：页面中是否还存在可删除项目,如果不存在,则移除删除按钮
        if ($('i.check-icon-toggle').length == 0) {
            $('#order-del-btn').parent().remove();
        }
        
        // 删除操作执行后,检测页面中列表数据是否为空
        if (Order.getListViewNum() == 0) {
            var un_data_str = '<div class="gap-page"><i class="rw"></i><p> 暂无数据</p><a class="j_submit" href="/list.php?cat=0&sort=default&fr=0&vt=3">随便逛逛</a></div>';
            Order.listView.before(un_data_str).remove();
			
			if($('div#has_show_all').length > 0){
				$('div#has_show_all').remove();
			}
			
        }
    },
    
    /**
     * 检测列表页面中是否还存在 li 元素
     */
    getListViewNum: function(){
        Order.listView = $("div.content div.list-view");
        return Order.listView.find('ul > li').length;
    },
    
    // 绑定事件
    _bindEvent: function(){
        $('#order-del-btn').toggle(this.showCheckBox, this.hideCheckBox);
        $('i.check-icon-toggle').live('click', this.checkIconToggle);
        
        $('.list-ul').find('a.un-click').live('click', this.unClick);
    },
    
    // 初始化
    init: function(){
        this._bindEvent();
        
        // 载入页面时检测是否存在 列表数据，如果列表数据不存在，则移除删除按钮
        if (Order.getListViewNum() == 0) {
            var del_btn = $('#order-del-btn').parent();
            del_btn.length && del_btn.remove();
        }
    }
    
}
    Order.init();
})

$(function() {
	var slideBar = new SlideNav();
	
	// 分类，商区，排序栏
	$('.mall-cate-box a').click(function() {
		var $this = $(this);
		var type = $this.attr('data-type');
		if (!type) return;
		
		var $allwrap = $(slideBar.options.menuSelector.wrap).filter('[data-type="' + type + '"]');
		var $menu = $allwrap.find(slideBar.options.menuSelector.top);
		if ($menu.length) {
			var isShow = slideBar.isShowedMenu($menu, 1);
			
			$('.pop-shade').trigger('click'); // 隐藏所有菜单
			if (!isShow) { // 点击收起, 显示一级菜单
				$this.parents('li').addClass('select');
				var $controller = slideBar.controllerToMenu($menu, 1, true);
				
				window.scrollTo(0, $('.mall-cate-box').offset().top);
				$allwrap.show();
				slideBar.fixHeight($menu);
				slideBar.showTopMenu($controller, $menu); // 展开二级菜单
			}
		}
	});

	// 左侧菜单
	$('.sort-view section a').bind('iscrollTap click', function(event) {
		var $this = $(this);
		var main = $this.attr('data-main');
		var isEmptyMenu = main && main.split('_')[1] === 'emptyMenu';
		
		if (event.type === 'click') return main && !isEmptyMenu ? false : '';
		
		var $section = $this.parent();
		$section.siblings('.select').removeClass('select');
		$section.addClass('select');
		
		if (main && !isEmptyMenu) {
			if ($.inArray(main, slideBar.options.noTriggerMenus) > -1) {
				if (main === slideBar.options.distanceHandle) {
					getDistanceList($this.attr('href'), $this.find('span'));
				}
				return false;
			}
			
			var $submenu = $this.parents(slideBar.options.menuSelector.top)
					.siblings(slideBar.options.menuSelector.sub).filter('[data-main="' + main + '"]');
			var isShow = slideBar.isShowedMenu($submenu, 2);
			if (!isShow) {
				slideBar.hideMenu(null, 2);
				slideBar.showSubMenu(null, $submenu);
			}
			
			return false;
		}
	});

	// 右侧菜单
	$('.sort-sider section a').bind('iscrollTap click', function(event) {
		var $this = $(this);
		var isSubHandle = $this.is(slideBar.options.menuSelector.subHandle);
		
		if (event.type === 'click') return isSubHandle && $this.attr('data-sub') ? false : '';
		
		if (isSubHandle) {
			var $submenu = slideBar.controllerToMenu($this, 2);
			var $subchildmenu = slideBar.controllerToMenu($this, 3);
//			var isShow = $subchildmenu.is(':visible');
			var isShow = slideBar.isShowedMenu($subchildmenu, 3);
			
			slideBar.controllerToMenu($submenu, 2, true).removeClass('select');
			slideBar.hideMenu(null, 3);
			if (isShow) { // 点击收起
				$submenu.showIScroll(true, 'refresh');
			} else { // 显示三级菜单
				$this.addClass('select');
				slideBar.showThirdMenu(null, $subchildmenu);
			}
			
			if ($subchildmenu.length) {
				return false;
			}
		}
	});

	// 商区，地铁切换
//	$('.sort-sub-nav a').click(function() {
//		var $this = $(this);
//		var type = $this.attr('data-type');
//
//		if (type) {
//			$('#districtLine').attr('data-type', type).trigger('click');
//		}
//	});

	// 遮罩
	$('.pop-shade').click(function() {
		slideBar.hideMenu(null, 3);
		slideBar.hideMenu(null, 2);
		slideBar.hideMenu(null, 1);
		$('.mall-cate-box li').removeClass('select');
		$(this).hide().css('height', '100%');
	});
	
	// 全部分类页初始化
	if (window.initSort) {
		var $allwrap = $(slideBar.options.menuSelector.wrap + '[data-type="' + window.initSort + '"]');
		if ($allwrap.length) {	
			var $menu = $allwrap.find(slideBar.options.menuSelector.top);
			var $controller = slideBar.controllerToMenu($menu, 1, true);
			
			slideBar.resize(true, 0);
			slideBar.fixHeight($menu, true, 0);
			slideBar.showTopMenu($controller, $menu); // 展开二级菜单
		}
	} else {
		slideBar.resize();
	}
});

$.fn.extend({
	/**
	 * 使区域可滚动
	 * @param {Boolean} handleIScroll
	 * @param {String} method
	 */
	showIScroll: function(handleIScroll, method) {
		if (this.length === 0) {
			return this;
		}
		
		var scrollHandle = this.data('initIScroll');
		if (!scrollHandle) {
			var scrollHandle = new IScroll(this.get(0), {
				mouseWheel: true
				,tap: 'iscrollTap'
				,click: true
				,disableMouse: 'ontouchend' in document
//				,scrollbars: true
			});

			SlideNav.prototype.displayMoreSign(scrollHandle);
			scrollHandle.on('scrollEnd', function() {
				SlideNav.prototype.displayMoreSign(scrollHandle);
			});
			scrollHandle.on('refresh', function() {
				SlideNav.prototype.displayMoreSign(scrollHandle);
			});

			this.data('initIScroll', handleIScroll ? scrollHandle : 'ok');
		} else if (method && scrollHandle instanceof IScroll) {
			if (method === 'refresh') { // 用于三级菜单展开收起后重新计算滑动区域高度
				scrollHandle.refresh();
				SlideNav.prototype.displayMoreSign(scrollHandle);
			}
		}
		return this;
	}
});

/**
 * 滚动分类栏
 */
var SlideNav = function(options) {
	this.options = $.extend({}, this.options, options);
	this.showedMenus = {};
};

SlideNav.prototype = {
	options: {
		noTriggerMenus: ['distanceHandle'], // 点击分类不自动触发的二级菜单
		distanceHandle: 'distanceHandle', // 离我最近a标签
		menuSignKey: 'slideMenuKey',
		menuSelector: {wrap: '.sortdrop-wrapper', top: '.sort-view', sub: '.sort-sider', 
			third: '.sort-shop-sider', subHandle: '.submenu_handle', moreSign: '.m-down', noExists: '#noneExistsElement'},
		thirdMenus: ['category_3'], // 有三级菜单
		showEmptySubMenu: false // 显示空的二级菜单
	},
	showedMenus: {},
	/**
	 * 判断当前菜单有没有显示
	 */
	isShowedMenu: function($menu, level) {
		var sign = $menu.data(this.options.menuSignKey);
		if (sign && this.showedMenus) {
			for (var key in this.showedMenus) {
				if (!level || level === ~~key) {
					var showedSign = this.showedMenus[key].data(this.options.menuSignKey);
					if (showedSign === sign) {
						return ~~key;
					}
				}
			}
		}
		return 0;
	},
	ctrlShowedMenu: function(action, level, $menu) {
		switch (action) {
			case 'get': 
				if (level in this.showedMenus) {
					return this.showedMenus[level]; 
				} else return $(this.options.menuSelector.noExists);
			case 'show': 
				if ($menu) this.showedMenus[level] = $menu; break;
			case 'hide': 
				delete this.showedMenus[level]; break;
		}
	},
	/**
	 * 显示一个菜单
	 * @param {jQueryItem} $menu
	 * @param {Integer} level 菜单级别 1: 1级菜单, 2: 2级菜单
	 * @param {Boolean} noIScroll 不生成新滚动区域
	 * @param {Boolean} justSelf 
	 */
	showMenu: function($menu, level, noIScroll, justSelf) {
		if ($menu.length === 0) return false;
		
		if (level === 1 && !justSelf && this.options.menuSelector.wrap) {
			$menu.parents(this.options.menuSelector.wrap).show();
		}
		
		$menu.data(this.options.menuSignKey, Math.random());
		noIScroll && $menu.show() || $menu.show().showIScroll();
		this.ctrlShowedMenu('show', level, $menu);
	},
	hideMenu: function($menu, level, justSelf) {
		$menu = $menu && $menu.length ? $menu : this.ctrlShowedMenu('get', level);
		var showLevel = this.isShowedMenu($menu, level);
		if (showLevel > 0) {
			this.ctrlShowedMenu('hide', showLevel);
			if (showLevel === 1 && !justSelf && this.options.menuSelector.wrap) {
				$menu.parents(this.options.menuSelector.wrap).hide();
			} else {
				$menu.hide();
			}
		}
		
		return $menu;
	},
	/**
	 * 显示一级菜单
	 * @param {type} $controller 一级菜单中A标签
	 */
	showTopMenu: function($controller, $menu, justSelf) {
		$menu = $menu && $menu.length ? $menu : this.controllerToMenu($controller, 1);
		this.showMenu($menu, 1);
		$('.pop-shade').show().height($('.pop-shade').height() + $('footer').height());

		// 自动展开二级菜单
		if ($controller && $controller.length && !justSelf) {
			var main = $controller.attr('data-main');
			var isEmptyMenu = main && main.split('_')[1] === 'emptyMenu';
			if (main && $.inArray(main, this.options.noTriggerMenus) === -1 && (!isEmptyMenu || this.options.showEmptySubMenu)) {
				var $submenu = $menu.parents(this.options.menuSelector.wrap).find(this.options.menuSelector.sub).filter('[data-main="' + main + '"]');
				var $subcontroller = this.controllerToMenu($submenu, 2, true);
				this.showSubMenu($subcontroller, $submenu); // 自动展开三级菜单
			}
		}
	},
	/**
	 * 显示二级菜单
	 * @param {type} $controller 二级菜单中A标签
	 */
	showSubMenu: function($controller, $menu, justSelf) {
		$menu = $menu && $menu.length ? $menu : this.controllerToMenu($controller, 2);
		this.showMenu($menu, 2, true);
		$menu.showIScroll($.inArray($menu.attr('data-main'), this.options.thirdMenus) > -1); // 展开收起三级菜单时需要使用IScroll对象，保存起来

		// 自动展开三级菜单
		if ($controller && !justSelf) {
			this.showThirdMenu($controller);
		}
	},
	/**
	 * 显示三次标签
	 * @param {type} $controller 二级菜单中A标签
	 */
	showThirdMenu: function($controller, $menu) {
		$menu = $menu && $menu.length ? $menu : this.controllerToMenu($controller, 3);
		this.showMenu($menu, 3, true);
		$menu.parents(this.options.menuSelector.sub).showIScroll(true, 'refresh');
	},
	/**
	 * menu和controller相互转换
	 * @param {Boolean} reverse 逆向转换
	 */
	controllerToMenu: function($item, level, reverse) {
		if (!$item || typeof $item !== 'object') {
			return $(this.options.menuSelector.noExists);
		}
		
		switch (level) {
			case 1:
				if (reverse) {
					return $item.find('.select a');
				} else return $item.parents(this.options.menuSelector.top);
			case 2:
				if (reverse) {
					return $item.find('a.select');
				} else return $item.parents(this.options.menuSelector.sub);
			case 3:
				if (reverse) {
					return $item.siblings(this.options.menuSelector.subHandle);
				} else return $item.siblings(this.options.menuSelector.third);
			default:
				return $(this.options.menuSelector.noExists);
		}
	},
	/**
	 * 是否显示更多图标
	 * @param {IScroll} iScrollHandle
	 */
	displayMoreSign: function(iScrollHandle) {
		var display = true;
		if (iScrollHandle.scrollerHeight <= iScrollHandle.wrapperHeight) {
			display = false;
		} else if (iScrollHandle.y - iScrollHandle.maxScrollY < 20) {
			display = false;
		}

		$(iScrollHandle.wrapper).find(this.options.menuSelector.moreSign)[display ? 'show' : 'hide']();
	},
	/**
	 * 设置一级和二级菜单的高度，修正火狐显示问题，横竖屏切换问题
	 * @param {type} $topMenu 一级菜单
	 * @param {type} removeHeaderSize 全部分类页面，返回头部占用高度，应该减掉
	 * @param {type} paddingSize 底部保留的遮罩高度
	 * @returns {undefined}
	 */
	fixHeight: function($topMenu, removeHeaderSize, paddingSize) {
		paddingSize = typeof paddingSize === 'number' ? paddingSize : 30;
		var headerSize = removeHeaderSize ? $('.mall-cate-box').offset().top : 0;
		var $sort_wrapper = $topMenu.closest('.sort-wrapper');
//		var $allwrap = $sort_wrapper.closest(this.options.menuSelector.wrap);
		var $sub_nav = $sort_wrapper.siblings('.sort-sub-nav');

		var height = Math.min($sub_nav.height() + $topMenu.children('div').height(), $(window).height() - $('.mall-cate-box').outerHeight() - headerSize - paddingSize);
		if (/qqbrowser/i.test(navigator.userAgent) && /iphone|ipod/i.test(navigator.userAgent)) {
			height -= 45;
		}
		var scrollHeight = height - $sub_nav.height();
		var curHeight = $topMenu.data('slide_curHeight'); // 目前的高度

//		$allwrap.height(height);
		if (curHeight !== scrollHeight) { // 高度有变化
	//		$sort_wrapper.height(scrollHeight);
//			$topMenu.height(scrollHeight).data('slide_curHeight', scrollHeight);
//			$topMenu.siblings(this.options.menuSelector.sub).height(scrollHeight);
			$(window).triggerHandler('resize');
		}
		
		var $body_wrap = $('#wrap'), width = Math.floor($body_wrap.width() / 2);
		if ($body_wrap.data('slide_curWidth') !== width) {
			$body_wrap.data('slide_curWidth', width);
			var $style = $('<style type="text/css" id="slideNav_css">.sortdrop-wrapper .sort-view, .sortdrop-wrapper .sort-sider {min-width: '+ width +'px; }</style>');
			$('#slideNav_css').remove();
			$style.appendTo('body');
		}
	},
	/**
	 * 窗口大小变化处理
	 * @param {type} removeHeaderSize
	 * @param {type} paddingSize
	 * @returns {undefined}
	 */
	resize: function(removeHeaderSize, paddingSize) {
		var _self = this, timeId = 0;
		$(window).bind('orientationchange resize', function(event) {
			var $topMenu = _self.ctrlShowedMenu('get', 1), type = event.type;
			if ($topMenu.length) {
				if (timeId > 0) {
					clearTimeout(timeId);
					timeId = 0;
				}
				timeId = setTimeout(function() {
					type === 'orientationchange' && window.scrollTo(0, removeHeaderSize ? 0 : $('.mall-cate-box').offset().top); // 重置滚动条位置
					_self.fixHeight($topMenu, removeHeaderSize, paddingSize);
				}, 60);
			}
		});
	}
};

function getDistanceList(url, $textItem) {
	var cookie_lat = getCookie("cur_lat");
	var cookie_lng = getCookie("cur_lng");
	var _function = arguments.callee;

	if (_function.isLoading) {
		return;
	} else {
		_function.isLoading = true;
	}

	$textItem.text("正在获取位置信息...");

	if (cookie_lat > 0 && cookie_lng > 0) {
		_function.isLoading = false;
		$textItem.text('成功获取,正在跳转...');
		setTimeout(function() {
			$textItem.text('离我最近');
		}, 3000);
		location.href = url;
	} else {
		getLocation(function() {
			_function.isLoading = false;
			$textItem.text('成功获取,正在跳转...');
			setTimeout(function() {
				$textItem.text('离我最近');
			}, 3000);
			location.href = url;
		}, function(error) {
			_function.isLoading = false;
			$textItem.text(error.show_msg);
			setTimeout(function() {
				$textItem.text('离我最近');
			}, 1000);
		});
	}
}

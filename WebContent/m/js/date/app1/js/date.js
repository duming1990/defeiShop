/* 
   datePicker.js
   description:滑动选取日期（年，月，日,时间）
   vaersion:1.0
   author:ddd
   https://github.com/ddd702/datePicker
   update：2015-3-7
 */
(function($) {
    "use strict";
    $.fn.datePicker = function(options) {
        return this.each(function(e) {
            //插件默认选项
            var that = $(this),
                docType = $(this).is('input'),
                nowdate = new Date(),
                yearScroll = null,
                monthScroll = null,
                dayScroll = null,
                hourScroll = null,
                minuteScroll = null,
                initY = null,
                initM = null,
                initD = null,
                initH = null,
                initI = null,
                initS = null,
                initVal = null;
            /*使用到的全局函数-e*/
            $.fn.datePicker.defaultOptions = {
                beginyear: 1920, //日期--年--份开始
                endyear: 2030, //日期--年--份结束
                monthDay: [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31], //日期--12个月天数(默认2月是28,闰年为29)--份结束
                days: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
                dayscar: ['今天', '明天', '后天'],
                beginhour: 6,
                endhour: 23,
                beginminute: 0,
                endminute: 59,
                mstep: 10,
                curdate: false, //打开日期是否定位到当前日期
                liH: 40,
                theme: "date", //控件样式（1：日期(date)，2：日期+时间(datetime),3:时间(time),4:年月(month)）
                mode: null, //操作模式（滑动模式）
                event: "click", //打开日期插件默认方式为点击后后弹出日期 
                show: true,
                scrollOpt: {
                    snap: "li",
                    checkDOMChanges: true
                },
                callBack: function() {}
            }
            var opts = $.extend(true, {}, $.fn.datePicker.defaultOptions, options);
            if (!opts.show) {
                that.off('click');
            } else {
                //绑定事件（默认事件为获取焦点）
                that.on(opts.event, function() {
                    init();
                });
            }

            function init() { //初始化函数
                initVal = that.val();
                if (!$('#datePlugin').size()) {
                    $('body').append('<div id="datePlugin"></div>');
                }
                nowdate = new Date();
                nowdate = dateAdd("h",1,nowdate);
                document.getElementsByTagName('body')[0].addEventListener('touchmove', cancleDefault, false);
                if (!opts.curdate && $.trim(initVal) != '') {
                    var inputDate = null,
                        inputTime = null;
                    if (opts.theme == 'date' || opts.theme == 'datetime') {
                        inputDate = initVal.split(' ')[0];
                        initY = parseInt(inputDate.split('-')[0] - parseInt(opts.beginyear)) + 1,
                            initM = parseInt(inputDate.split('-')[1]),
                            initD = parseInt(inputDate.split('-')[2]);
                    }
                    if (opts.theme == 'datetime') {
                        inputTime = initVal.split(' ')[1];
                        initH = parseInt(inputTime.split(':')[0]) + 1,
                            initI = parseInt(inputTime.split(':')[1]) + 1;
                    }
                    if (opts.theme == 'datetimecar') {
                    	inputDate = initVal.split(' ')[0];
                    	inputTime = initVal.split(' ')[1];
                    	
                    	initH = parseInt(inputTime.split(':')[0])+1,
                    	initI = parseInt(inputTime.split(':')[1]/opts.mstep) + 1;
                    	if(inputDate == "今天") {
                    		initD = 1;
                    		opts.beginhour = nowdate.getHours()-1;
                    	}
                    	if(inputDate == "明天") {
                    		opts.beginhour = 6;
                    		initD = 2;
                    	}
                    	if(inputDate == "后天") {
                    		opts.beginhour = 6;
                    		initD = 3;
                    	}
                    
                    }
                    if (opts.theme == 'time') {
                        inputTime = initVal;
                        initH = parseInt(inputTime.split(':')[0]) + 1,
                            initI = parseInt(inputTime.split(':')[1]) + 1;
                    }
                    if (opts.theme == 'month') {
                        inputDate = initVal;
                        initY = parseInt(inputDate.split('-')[0] - parseInt(opts.beginyear)) + 1,
                            initM = parseInt(inputDate.split('-')[1]);
                    }
                } else {
                    initY = parseInt(nowdate.getFullYear()) - parseInt(opts.beginyear) + 1,
                        initM = parseInt(nowdate.getMonth()) + 1,
                        initD = parseInt(nowdate.getDate()),
                        initH = parseInt(nowdate.getHours()) + 1,
                        initI = parseInt(nowdate.getMinutes()) + 1,
                        initS = parseInt(nowdate.getSeconds());
        
                    if (opts.theme == 'datetimecar') {
                    	initD = 1;
                    	initI = parseInt(nowdate.getMinutes()/opts.mstep) + 1;
                    	opts.beginhour = nowdate.getHours();
                    	console.log("initI-1:"+initI);
                    	if(initI >= 6){
                    		nowdate = dateAdd( "h", 1, nowdate);
                    		initH = parseInt(nowdate.getHours()) + 1,
                    		initI = parseInt(nowdate.getMinutes()/opts.mstep) + 1;
                    		opts.beginhour = nowdate.getHours();
                    	}
                    	console.log("initI-2:"+initI);
                    	console.log("initI-2:"+initI);
                    }
                }
                
                $('#datePlugin').show();
                renderDom();
                $('#d-okBtn').on('click', function(event) {
                    document.getElementsByTagName('body')[0].removeEventListener('touchmove', cancleDefault, false);
                    var getY = $('#yearScroll li').eq(initY).data('num');
                    var getM = $('#monthScroll li').eq(initM).data('num');
                    var getD = $('#dayScroll li').eq(initD).data('num');
                    that.val($('.d-return-info').html());
                    $('#datePlugin').hide().html('');
                    opts.callBack();
                });
                $('#d-cancleBtn').on('click', function(event) {
                    $('#datePlugin').hide().html('');
                    document.getElementsByTagName('body')[0].removeEventListener('touchmove', cancleDefault, false);
                });
            }

            function cancleDefault(event) {
                event.preventDefault();
            }

            function isLeap(y) {
                if ((y % 4 == 0 && y % 100 != 0) || y % 400 == 0) {
                    return true;
                } else {
                    return false;
                }
            }

            function renderDom() {
                var mainHtml = ' <div class="d-date-box"><div class="app-dateField-top"><span class="app-dateField-btnCancel" id="d-cancleBtn">取消</span><span class="app-dateField-title"><p class="d-date-info"><b class="d-day-info"></b><b class="d-return-info"></b></p></span><span class="app-dateField-btnOk" id="d-okBtn">完成</span></div></div>';
               //var mainHtml = ' <div class="d-date-box"><div class="d-date-title">请选择日期</div><p class="d-date-info"><span class="d-day-info"></span><span class="d-return-info"></span></p></div>';
                var btnHtml = '<div class="d-date-btns"><button class="d-btn" id="d-okBtn">确定</button><button class="d-btn" id="d-cancleBtn">取消</button></div>';
                var dateHtml = '<div class="d-date-wrap">';
                dateHtml += '<div class="d-date-mark"></div>';
                dateHtml += '<div class="d-year-wrap d-date-cell" id="yearScroll"><ul></ul></div>';
                dateHtml += '<div class="d-month-wrap d-date-cell" id="monthScroll"><ul></ul></div>';
                dateHtml += '<div class="d-day-wrap d-date-cell" id="dayScroll"><ul></ul></div>';
                dateHtml += '</div>';
                var timeHtml = '<div class="d-date-wrap d-time-wrap">';
                timeHtml += '<div class="d-date-mark"></div>';
                timeHtml += '<div class="d-hour-wrap d-date-cell" id="hourScroll"><ul></ul></div>';
                timeHtml += '<div class="d-minute-wrap d-date-cell" id="minuteScroll"><ul></ul></div>';
                timeHtml += '</div>';
                if (opts.theme == 'datetimecar') {
                    timeHtml = '<div class="d-date-wrap d-time-wrap-car">';
                    timeHtml += '<div class="d-date-mark"></div>';
                    timeHtml += '<div class="d-day-wrap d-date-cell" id="dayScroll"><ul></ul></div>';
                    timeHtml += '<div class="d-hour-wrap d-date-cell" id="hourScroll"><ul></ul></div>';
                    timeHtml += '<div class="d-minute-wrap d-date-cell" id="minuteScroll"><ul></ul></div>';
                    timeHtml += '</div>';
                }
                var monthHtml = '<div class="d-date-wrap">';
                monthHtml += '<div class="d-date-mark"></div>';
                monthHtml += '<div class="d-year-wrap d-date-cell" style="width:50%" id="yearScroll"><ul></ul></div>';
                monthHtml += '<div class="d-month-wrap d-date-cell" style="width:50%" id="monthScroll"><ul></ul></div>';
                monthHtml += '</div>';
                $('#datePlugin').html(mainHtml);
                switch (opts.theme) {
                    case 'date':
                        $('.d-date-box').append(dateHtml);
                        createYear();
                        createMonth();
                        createDay(opts.monthDay[initM - 1]);
                        break;
                    case 'datetime':
                        $('.d-date-box').append(dateHtml);
                        $('.d-date-box').append(timeHtml);
                        createYear();
                        createMonth();
                        createDay(opts.monthDay[initM - 1]);
                        createHour();
                        createMinute();
                        break;
               		case 'datetimecar':
                    	$('.d-date-box').append(timeHtml);
                    	createDay(3);// 1 2 3
                    	createHour();
                    	createMinute();
                    	break;
                    case 'time':
                        $('.d-date-box').append(timeHtml);
                        createHour();
                        createMinute();
                        break;
                    case 'month':
                        $('.d-date-box').append(monthHtml);
                        createYear();
                        createMonth();
                        break;
                    default:
                        $('.d-date-box').append(dateHtml);
                        createYear();
                        createMonth();
                        createDay(opts.monthDay[initM - 1]);
                        break;
                }
                //$('.d-date-box').append(btnHtml);
                showTxt();
            }

            function showTxt() {
                var y = $('#yearScroll li').eq(initY).data('num'),
                    m = $('#monthScroll li').eq(initM).data('num'),
                    d = $('#dayScroll li').eq(initD).data('num'),
                    h = $('#hourScroll li').eq(initH - opts.beginhour).data('num'),
                    i = $('#minuteScroll li').eq(initI).data('num'),
                    date = new Date(y + '-' + m + '-' + d);
                switch (opts.theme) {
                    case 'date':
                        //$('.d-day-info').html(opts.days[date.getDay()] + "&nbsp;");
                    	var mf = m;
                    	var df = d;
                        if (m < 10) {
                        	mf = "0"+ m;
                        }
                        if (d < 10) {
                        	df = "0"+ d;
                        }
                        $('.d-return-info').html(y + '-' + mf + '-' + df);
                        break;
                    case 'datetime':
                        $('.d-day-info').html(opts.days[date.getDay()] + "&nbsp;");
                        $('.d-return-info').html(y + '-' + m + '-' + d + ' ' + h + ':' + i);
                        break;
                    case 'datetimecar':
                    	var showt = opts.dayscar[d-1];
                    	var daysreal = parseInt(nowdate.getDay()) + (d-1);
                    	console.log("initH - opts.beginhour:"+ (initH - opts.beginhour));
                    	//h = $('#hourScroll li').eq(initH - opts.beginhour).data('num'),
                    	console.log("h:"+h);
                    	//console.log("d:"+d);
                    	//console.log("i:"+i);
                    	//i = $('#minuteScroll li').eq(parseInt(initI/opts.minutestep)+1).data('num');
                    	$('.d-day-info').html(opts.days[daysreal] + "&nbsp;");
                    	$('.d-return-info').html(showt + ' ' + h + ':' + i);
                    	break;
                    case 'time':
                        $('.d-return-info').html(h + ':' + i);
                        break;
                    case 'month':
                        $('.d-return-info').html(y + '-' + m);
                        break;
                    default:
                        $('.d-day-info').html(opts.days[date.getDay()] + "&nbsp;");
                        $('.d-return-info').html(y + '-' + m + '-' + d);
                        break;
                }
            }

            function createYear() {
                var yearDom = $('#yearScroll'),
                    yearNum = opts.endyear - opts.beginyear,
                    yearHtml = '<li></li>';
                for (var i = 0; i <= yearNum; i++) {
                    yearHtml += '<li data-num=' + (opts.beginyear + i) + '>' + (opts.beginyear + i) + '年</li>';
                };
                yearDom.find('ul').html(yearHtml).append('<li></li>');
                yearScroll = new IScroll('#yearScroll', opts.scrollOpt);
                yearScroll.scrollTo(0, -(initY - 1) * opts.liH);
                yearScroll.on('scrollEnd', function(event) {
                    var yIndex = Math.floor(-this.y/opts.liH);
                    initY = yIndex + 1;
                    if (isLeap(parseInt(yearDom.find('li').eq(initY).data('num')))) {
                        opts.monthDay[1] = 29;
                    } else {
                        opts.monthDay[1] = 28;
                    }
                    if (initM == 2 && opts.theme != 'month') {
                        createDay(opts.monthDay[initM - 1]);
                    }
                    showTxt();
                });
            }

            function createMonth() {
                var monthDom = $('#monthScroll'),
                    monthHtml = '<li></li>';
                for (var i = 1; i <= 12; i++) {
                    if (i < 10) {
                        monthHtml += '<li data-num="0' + i + '">0' + i + '月</li>';
                    } else {
                        monthHtml += '<li data-num="' + i + '">' + i + '月</li>';
                    }
                };
                monthDom.find('ul').html(monthHtml).append('<li></li>');
                monthScroll = new IScroll('#monthScroll', opts.scrollOpt);
                monthScroll.scrollTo(0, -(initM - 1) * opts.liH);
                monthScroll.on('scrollEnd', function(event) {
                    var mIndex = Math.floor(-this.y/opts.liH);;
                    var dayNum = opts.monthDay[mIndex];
                    initM = mIndex + 1;
                    if (opts.theme != 'month') {
                        createDay(dayNum);
                    }
                    showTxt();
                });
            }

            function createDay(dayNum) {
                var dayDom = $('#dayScroll'),
                    dayHtml = '<li></li>';
                for (var i = 1; i <= dayNum; i++) {
                    if (i < 10) {
                    	 if (opts.theme == 'datetimecar') {
                    		 dayHtml += '<li data-num="0' + i  + '">' + opts.dayscar[i-1] + '</li>';
                    	 } else {
                    		 dayHtml += '<li data-num="0' + i + '">0' + i + '日</li>';
                    	 }
                    } else {
                        dayHtml += '<li data-num="' + i + '">' + i + '日</li>';
                    }
                };
                dayDom.find('ul').html(dayHtml).append('<li></li>');
                if (dayScroll) {
                    dayScroll.destroy();
                    dayScroll = null;
                }
                dayScroll = new IScroll('#dayScroll', opts.scrollOpt);
                if (initD > opts.monthDay[initM - 1]) {
                    initD = 1;
                }
                dayScroll.scrollTo(0, -(initD - 1) * opts.liH);
                dayScroll.on('scrollEnd', function(event) {
                    initD = Math.floor(-this.y/opts.liH) + 1;
                    console.log("dayScroll initD:"+initD);
                    if(initD==2 || initD ==3){
                    	 $('#hourScroll').find("ul").empty();
                    	opts.beginhour = 6;
                    	initH = opts.beginhour +1;
                    	//createHour();
                    }else{
                    	opts.beginhour = nowdate.getHours();
                    	initH = opts.beginhour+1;
                    	$('#hourScroll').find("ul").empty();
                    	//createHour();
                    }
                    //nowdate.getHours()
                    showTxt();
                });
            }

            function createHour() {
                var hourDom = $('#hourScroll'),
                    hourHtml = '<li></li>';
                console.log("createHour beginhour:"+opts.beginhour);
                for (var i = opts.beginhour; i <= opts.endhour; i++) {
                    if (i < 10) {
                        hourHtml += '<li data-num="0' + i + '">0' + i + '点</li>';
                    } else {
                        hourHtml += '<li data-num="' + i + '">' + i + '点</li>';
                    }
                };
                hourDom.find('ul').html(hourHtml).append('<li></li>');
                if (hourScroll) {
                    hourScroll.destroy();
                    hourScroll = null;
                }
                hourScroll = new IScroll('#hourScroll', opts.scrollOpt);
                hourScroll.scrollTo(0, -(initH - 1 - opts.beginhour) * opts.liH);
                hourScroll.on('scrollEnd', function(event) {
                    initH = Math.floor(-this.y/opts.liH)+ 1;
                    console.log("hourScroll initH:"+initH);
                    initH = initH + opts.beginhour;
                    //console.log("hourScroll initH:"+initH);
                    showTxt();
                });
            }

            function createMinute() {
                var minuteDom = $('#minuteScroll'),
                    minuteHtml = '<li id="mli"></li>';
                
                for (var i = 0; i <= (59/opts.mstep); i++) {
                	var v = i*opts.mstep;
                    if (v < 10) {
                        minuteHtml += '<li data-num="0' + v + '">0' + v + '分</li>';
                    } else {
                        minuteHtml += '<li data-num="' + v + '">' + v + '分</li>';
                    }
                };
                minuteDom.find('ul').html(minuteHtml).append('<li></li>');
                if (minuteScroll) {
                    minuteScroll.destroy();
                    minuteScroll = null;
                }
                minuteScroll = new IScroll('#minuteScroll', opts.scrollOpt);
                minuteScroll.scrollTo(0, -(initI - 1) * opts.liH);
                minuteScroll.on('scrollEnd', function(event) {
                    initI = Math.floor(-this.y/opts.liH) + 1;
                	//console.log("this.y:"+this.y);
                	//console.log("initI:"+initI);
                    showTxt();
                });
            }
        });
    }
})(typeof(Zepto) != 'undefined' ? Zepto : jQuery);

/*
 *   功能:实现VBScript的DateAdd功能.
 *   参数:interval,字符串表达式，表示要添加的时间间隔.
 *   参数:number,数值表达式，表示要添加的时间间隔的个数.
 *   参数:date,时间对象.
 *   返回:新的时间对象.
 *   var now = new Date();
 *   var newDate = DateAdd( "d", 5, now);
 *---------------   DateAdd(interval,number,date)   -----------------
 */
function dateAdd(interval, number, date) {
    switch (interval) {
    case "y": {
        date.setFullYear(date.getFullYear() + number);
        return date;
        break;
    }
    case "q": {
        date.setMonth(date.getMonth() + number * 3);
        return date;
        break;
    }
    case "m": {
        date.setMonth(date.getMonth() + number);
        return date;
        break;
    }
    case "w": {
        date.setDate(date.getDate() + number * 7);
        return date;
        break;
    }
    case "d": {
        date.setDate(date.getDate() + number);
        return date;
        break;
    }
    case "h": {
        date.setHours(date.getHours() + number);
        return date;
        break;
    }
    case "m": {
        date.setMinutes(date.getMinutes() + number);
        return date;
        break;
    }
    case "s": {
        date.setSeconds(date.getSeconds() + number);
        return date;
        break;
    }
    default: {
        date.setDate(d.getDate() + number);
        return date;
        break;
    }
    }
}

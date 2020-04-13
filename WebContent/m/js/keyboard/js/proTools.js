/* @update: 2016-12-28 15:45:45 */
window.debug = !0,
window.console = window.console || {
    error: function() {},
    log: function() {}
};
var toolsComponents = {}; !
function() {
    var t = "hidden",
    e = "slideUp",
    n = "zoomOut",
    a = "transitionend webkitTransitionEnd mozTransitionEnd",
    o = {
        show: function(n) {
            var o = n.find(".mask");
            o.off(a),
            n.removeClass(t),
            setTimeout(function() {
                n.addClass(e)
            },
            10)
        },
        close: function(n) {
            var o = n.find(".mask");
            o.on(a,
            function() {
                n.addClass(t),
                o.off(a)
            }),
            n.removeClass(e)
        }
    },
    i = {
        show: function(e) {
            var o = e.find(".mask");
            o.off(a),
            e.removeClass(t),
            setTimeout(function() {
                e.removeClass(n)
            },
            10)
        },
        close: function(t) {
            var e = t.find(".mask");
            e.on(a,
            function() {
                t.remove(),
                e.off(a)
            }),
            t.addClass(n)
        }
    },
    s = {
        show: function(n, o) {
            n.on(a,
            function() {
                n.off(a),
                o && o()
            }),
            n.removeClass(t),
            setTimeout(function() {
                n.addClass(e)
            },
            10)
        },
        close: function(n, o) {
            n.on(a,
            function() {
                n.addClass(t),
                n.off(a),
                o && o()
            }),
            setTimeout(function() {
                n.addClass(t),
                n.off(a)
            },
            360),
            n.removeClass(e)
        }
    };
    window.animate = {
        dialog: o,
        dialogMsg: i,
        keyboard: s
    }
} (),
!
function() {
    function t(t, e) {
        var n = document.createElement("div");
        return n.className = t,
        e && (n.innerHTML = e),
        document.body.appendChild(n),
        n
    }
    function e() {
        document.addEventListener("touchmove", a, !1)
    }
    function n() {
        document.removeEventListener("touchmove", a, !1)
    }
    function a(t) {
        t.preventDefault()
    }
    function o(t) {
        for (var e = [], n = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghigklmnopqrstuvwxyz0123456789-_", a = 0; t > a; a++) {
            var o = Math.floor(Math.random() * n.length);
            e.push(n[o])
        }
        return e.join("")
    }
    function i() {
        if (C) return C;
        var t = navigator.userAgent.toLowerCase(),
        e = t.indexOf("ipad") > -1 || t.indexOf("iphone") > -1 || !1,
        n = t.indexOf("micromessenger") > -1 || !1,
        a = t.indexOf("jdapp") > -1 || !1,
        o = t.indexOf("jdjr") > -1 || t.indexOf("android-async-http") > -1 || !1,
        i = t.indexOf("WalletClient") > -1 || !1,
        d = "app" == s("source"),
        l = {
            isIos: e,
            inWx: n,
            inApp: d,
            inJdApp: a,
            inJrApp: o,
            inWyApp: i
        };
        return C = l,
        l
    }
    function s(t) {
        var e = location.search,
        n = /(\w+)=(\w+)/gi,
        a = {};
        return e.replace(n,
        function(t, e, n) {
            a[e] = n
        }),
        a[t]
    }
    function d() {
        var t = "active",
        e = $(".support-box"),
        n = e.find(".tab-cont"),
        a = []; ! a[0] && (a[0] = new IScroll("#cont0", {
            mouseWheel: !0
        })),
        $("body").on(g, ".support-box .tab-title li",
        function() {
            $(this).addClass(t).siblings().removeClass(t);
            var e = $(this).index();
            n.addClass(y).eq(e).removeClass(y),
            !a[e] && (a[e] = new IScroll("#cont" + e, {
                mouseWheel: !0
            }))
        })
    }
    function l() {
        var t = $(".dialog-bankList"),
        e = null;
        return $("body").on(g, ".dialog-bankList .close-btn",
        function() {
            window.dialog.bankList.close()
        }),
        window.dialog.bankList = {
            show: function() {
                t.removeClass(y),
                !e && (e = new IScroll("#cont1", {
                    mouseWheel: !0
                }))
            },
            close: function() {
                t.addClass(y)
            }
        },
        window.dialog.bankList
    }
    function r(t) {
        function e(t) {
            var e = $(t).find(".bank-icon").attr("src"),
            n = $(t).find(".card-info>h4>p").text() || $(t).find(".card-info>h4").text(),
            a = $(t).find(".card-info>p").html() || "",
            o = {
                src: e,
                name: n,
                limit: a
            };
            return o
        }
        var n = "active",
        a = $(".dialog-quickly-bank"),
        o = null,
        i = t.select,
        s = t.init,
        d = t.selectedIndex,
        l = "tap";
        $("body").on(l, ".dialog-quickly-bank .arrow-box,.dialog-quickly-bank .mask",
        function() {
            window.dialog.quickCardList.close()
        }).on(l, ".dialog-quickly-bank .card-list li:not(.disabled)",
        function() {
            $(this).addClass(n).siblings().removeClass(n);
            var t = e(this),
            a = 0;
            $(this).find(".correct").length > 0 && (a = 1, window.dialog.quickCardList.close()),
            0 == a && (t.name = "请选择支付方式"),
            i && i(t, $(this), a)
        }),
        window.dialog.quickCardList = {
            show: function() {
                animate.dialog.show(a),
                !o && (o = new IScroll(".dialog-quickly-bank .card-listBox", {
                    probeType: 1,
                    mouseWheel: !0
                }))
            },
            close: function() {
                animate.dialog.close(a)
            }
        };
        var r = $(".dialog-quickly-bank .card-list li"),
        c = r.length,
        u = r.filter(":not(.add)").filter(":not(.disabled)").length,
        v = u > d ? d: 0,
        f = r.eq(v).addClass(n),
        h = 1;
        f.hasClass("add") && (h = 0),
        1 == c && (h = -1);
        var p = e(f);
        return 0 == h && (p.name = "请选择支付方式"),
        s && s(p, f, h),
        window.dialog.quickCardList
    }
    function c(t) {
        function e(t) {
            var e = $(t).find(".bank-icon").attr("src"),
            n = $(t).find(".card-info>h4>p").text() || $(t).find(".card-info>h4").text(),
            a = $(t).find(".card-info>p").html() || "",
            o = {
                src: e,
                name: n,
                limit: a
            };
            return o
        }
        var n = "active",
        a = t.select,
        o = t.init,
        i = t.selectedIndex,
        s = $("#flow_cardList .card-list li"),
        d = s.filter(":not(.add)").filter(":not(.disabled)").length,
        l = d > i ? i: 0,
        r = s.eq(l).addClass(n);
        return o && o(e(r), r),
        $("#flow_cardList").off().on("tap", "li:not(.disabled)",
        function() {
            if (!$(this).hasClass(n)) {
                $(this).addClass(n).siblings().removeClass(n);
                var t = e(this);
                a && a(t, $(this))
            }
        }),
        {}
    }
    function u(t) {
        function e(t) {
            var e = $(t).find(".bank-icon").attr("src"),
            n = $(t).find(".card-info>h4>p").text(),
            a = $(t).find(".card-info>p").html() || "",
            o = {
                src: e,
                name: n,
                limit: a
            };
            return o
        }
        var n = "active",
        a = $(".dialog-coupon"),
        o = null,
        i = t.select,
        s = t.init,
        d = t.selectedIndex,
        l = {
            show: function() {
                animate.dialog.show(a),
                !o && (o = new IScroll(".dialog-coupon .card-listBox", {
                    mouseWheel: !0
                }))
            },
            close: function() {
                animate.dialog.close(a)
            }
        };
        $("body").on(g, ".dialog-coupon .arrow-box, .dialog-coupon .mask",
        function() {
            l.close()
        }).on(g, ".dialog-coupon .card-list li:not(.disabled)",
        function() {
            $(this).addClass(n).siblings().removeClass(n);
            var t = e(this);
            l.close(),
            i && i(t, $(this))
        }).on(g, "h2>a",
        function() {
            var t = {
                src: "",
                name: "暂不使用",
                limit: ""
            };
            l.close(),
            i && i(t, $(this))
        });
        var r = $(".dialog-coupon .card-list li"),
        c = r.filter(":not(.add)").filter(":not(.disabled)").length,
        u = c > d ? d: 0,
        v = r.eq(u).addClass(n),
        f = r.length > 1 ? 1 : 0;
        return s && s(e(v), v, f),
        window.dialog.couponDialog = l,
        l
    }
    function v(t) {
        function e(t) {
            var e = $(t).find(".bank-icon").attr("src"),
            n = parseInt($(t).find(".stage-count").text()),
            a = isNaN(n) ? 0 : n,
            o = $(t).find(".card-info>h4>p").text(),
            i = $(t).find(".card-info>p").html() || "",
            s = {
                src: e,
                count: a,
                name: o,
                limit: i
            };
            return s
        }
        var n = "active",
        a = $(".dialog-stage"),
        o = t.select,
        i = t.init,
        s = t.selectedIndex,
        d = {
            show: function() {
                animate.dialog.show(a)
            },
            close: function() {
                animate.dialog.close(a)
            }
        };
        $("body").on(g, ".dialog-stage .arrow-box,.dialog-stage .mask",
        function() {
            d.close()
        }).on("tap", ".dialog-stage li:not(.disabled)",
        function() {
            $(this).addClass(n).siblings().removeClass(n);
            var t = e(this);
            d.close(),
            o && o(t, $(this))
        });
        var l = $(".dialog-stage .card-list li"),
        r = l.filter(":not(.add)").filter(":not(.disabled)").length,
        c = r > s ? s: 0,
        u = l.eq(c).addClass(n),
        v = l.length > 1 ? 1 : 0;
        return i && i(e(u), u, v),
        window.dialog.stageDialog = d,
        d
    }
    function f(t) {
        function e(t) {
            var e = $(t).find(".bank-icon").attr("src"),
            n = $(t).find(".card-info>h4>p").text(),
            a = $(t).find(".card-info>p").html() || "",
            o = {
                src: e,
                name: n,
                limit: a
            };
            return o
        }
        var n = "active",
        a = $(".dialog-allCoupon"),
        o = null,
        i = t.select,
        s = (t.init, t.selectedIndex),
        d = {
            show: function() {
                animate.dialog.show(a),
                o ? o.refresh() : o = new IScroll(".dialog-allCoupon .card-listBox", {
                    mouseWheel: !0
                })
            },
            close: function() {
                animate.dialog.close(a)
            },
            select: function(t) {
                var o = 1,
                s = {
                    src: "",
                    name: "暂无可用优惠",
                    limit: ""
                },
                l = a.find(".card-list").find("li"),
                r = l.filter("li:not(.disabled)");
                if (0 == r.length && (t = -2), 0 == l.length && (t = -3, s.name = "暂无优惠"), -1 == t && (s.name = "暂不使用"), 0 > t) return o = t,
                r.removeClass(n),
                void(i && i(s, $(this), o));
                o = 1,
                t > r.length - 1 && (t = 0);
                var c = r.eq(t);
                c.addClass(n).siblings().removeClass(n),
                s = e(c),
                d.close(),
                i && i(s, c, o)
            }
        };
        $("body").on(g, ".dialog-allCoupon .arrow-box, .dialog-allCoupon .mask",
        function() {
            d.close()
        }).on(g, ".dialog-allCoupon .card-list li:not(.disabled)",
        function() {
            d.select($(this).index())
        }).on(g, ".dialog-allCoupon h2>a",
        function() {
            d.select( - 1),
            d.close()
        });
        var l = $(".dialog-allCoupon .card-list li"),
        r = l.filter(":not(.add)").filter(":not(.disabled)").length,
        c = r > s ? s: 0;
        return d.select(c),
        window.dialog.allCouponDialog = d,
        d
    }
    function h(t) {
        this.options = {
            dateLHeight: 36 * ($(".dateL_cell").length - 1),
            dateRHeight: 36 * ($(".dateR_cell").length - 1)
        },
        this.init(t)
    }
    function p(t, e) {
        for (var n = new Date,
        a = n.getFullYear(), e = e || 30, o = $(".shadeDate"), i = "", s = 0; e > s; s++) i += ' <li class="dateR_cell">' + (a + s) + "年</li>";
        o.find(".dateR_cells_U").append(i),
        h && new h({
            year: n.getFullYear(),
            month: n.getMonth() + 1,
            done: function(e) {
                var n = e[0].toString().substr(2, 2),
                a = e[1],
                o = parseInt(a) > 9 ? a: "0" + a,
                i = o + "/" + n;
                t(i)
            }
        });
        var d = {
            show: function() {
                o.removeClass(y)
            },
            close: function() {
                o.addClass(y),
                tools.unlockPage()
            }
        };
        return $("body").on(g, ".dateControl .done",
        function() {
            d.close()
        }).on(g, ".dateControl .cancel",
        function() {
            d.close(),
            tools.unlockPage()
        }),
        d
    }
    function m(t) {
        var e = $(".keyboard"),
        n = [],
        a = t.max || 6;
        return t.init && (n = t.init.split("")),
        $("body").on(g, ".keyboard .arrow-box",
        function() {
            keyboard.number.close(),
            t.closeFn && t.closeFn()
        }),
        $(".keyboard.num").on("touchend", "li>div",
        function() {
            var e = this,
            o = $(this).data("key");
            if ("" !== o && void 0 !== o && ($(this).addClass("active"), setTimeout(function() {
                $(e).removeClass("active")
            },
            160)), n.length < a && /^\d$/.test(o)) {
                n.push(o);
                var i = keyboard.number.getResult();
                t.numicFn && t.numicFn(o, i)
            } else if ("del" == o && n.length > 0) {
                n.pop();
                var i = keyboard.number.getResult();
                t.delFn && t.delFn(i)
            }
        }),
        k.namespace(window, "keyboard.number"),
        window.keyboard.number = {
            constructor: KeyboardNumber,
            show: function(t) {
                var n = e;
                return animate.keyboard.show(n),
                this
            },
            close: function(t) {
                var n = e;
                return animate.keyboard.close(n),
                this
            },
            empty: function() {
                return n = [],
                this
            },
            getResult: function() {
                return n.join("")
            },
            setResult: function(t) {
                return "string" == typeof t && (n = t.split("")),
                this
            },
            closeAndClear: function() {
                return this.close(),
                this.empty(),
                this
            }
        },
        window.keyboard.number
    }
    function b(t) {
        var e = $(".dialog-numpwd");
        return $("body").on(g, ".dialog-numpwd .btnline-box a",
        function() {
            var e = $(this).index();
            0 == e ? (t.cancel && t.cancel(), window.dialog.numpwd.close()) : 1 == e && t.confirm && t.confirm()
        }),
        window.dialog.numpwd = {
            show: function() {
                e.removeClass(y)
            },
            close: function() {
                e.addClass(y)
            }
        },
        window.dialog.numpwd
    }
    var g = "click",
    y = "hidden",
    k = {
        namespace: function(t, e) {
            var n, a, o = e.split("."),
            i = t;
            for (n = 0, a = o.length; a > n; n++) i[o[n]] || (i[o[n]] = {}),
            i = i[o[n]];
            return i
        },
        getByNS: function(t, e) {
            var n, a, o = e.split("."),
            i = t;
            for (n = 0, a = o.length; a > n; n++) {
                if (!i[o[n]]) return null;
                i = i[o[n]]
            }
            return i
        },
        method: function(t, e, n) {
            if (arguments.length < 2) throw new Error("common.method 方法必须要传两个参数");
            this.namespace(t, e);
            for (var a = e.split(/\./), o = t || window, i = 0; i < a.length; i++) o = o[a[i]];
            for (var s in n) o[s] = n[s];
            return this
        }
    }; !
    function() {
        function e(e, n, a) {
            if ("string" == typeof e) var o = e,
            n = n,
            a = a;
            else var o = e.text,
            n = e.position,
            a = e.maxWidth || "",
            i = e.typeClass || "";
            if (0 == e.autoClose) var s = !1;
            else var s = !0;
            switch ($(".warningBox").remove(), n) {
            case "top":
                var d = t("warningBox fix-mt " + i, o);
                break;
            case "mid":
                var d = t("warningBox fix-mm " + i, o);
                break;
            default:
                var d = t("warningBox fix-mt " + i, o)
            }
            return a && (d.style.maxWidth = a),
            s && ($(d).addClass("fadeOut"), d.addEventListener("webkitAnimationEnd",
            function() {
                $(this).remove()
            })),
            $(d)
        }
        toolsComponents.alertWarining = toolsComponents.alertWarning = e
    } (),
    function() {
        function t(t, e) {
            var n = "nowrap",
            a = $(window).width();
            if (void 0 === t) return void console.error("请传入参数");
            var o = null;
            $(".warning-box").remove();
            var i = $('<div class="warning-box fix-lt"></div>'),
            s = $(tools.format('<div class="jtest vh">{0}</div><div class="warning-text abs-mm nowrap fs16 color-f">{0}</div>', t));
            i.append(s),
            e && i.addClass("mask"),
            $("body").append(i);
            var d = i.find(".jtest"),
            l = d.width();
            d.remove(),
            l > .8 * a && s.removeClass(n).width(.8 * a),
            i.on("webkitAnimationEnd animationend mozAnimationEnd",
            function(t) {
                "fadeOut" == t.animationName && $(this).remove(),
                clearTimeout(o)
            }),
            clearTimeout(o),
            o = setTimeout(function() {
                $(".warning-box").remove()
            },
            2400)
        }
        toolsComponents.alertDialogWarning = t
    } (),
    function() {
        function t(t, n) {
            function o() {
                animate.dialogMsg.close($(".dialog-msg"))
            }
            var i = tools.format;
            if (void 0 === t) return void console.error("请传入参数");
            var s = {};
            s.content = "string" == typeof t ? t: t.content,
            s.title = t.title || "",
            s.showTitle = t.title ? "": "hidden",
            s.notitle = t.title ? "": "notitle",
            s.showClose = t.showClose === !0 ? "": "hidden";
            var d = t.dialogStyle || "",
            l = t.width ? "width:" + t.width + ";": "";
            s.dialogStyle = 'style="' + l + d + '"',
            s.style = t.contentStyle ? i('style="{0}"', t.contentStyle) : "",
            s.style = n ? i('style="{0}"', n) : s.style,
            s.contentClass = t.contentClass ? t.contentClass: "",
            s.baseUrl = "//static.360buyimg.com/finance/mobile/payment/cashiers/1.0.0/images/common",
            s.btnType = "line" == t.btnType ? "btnline-box": "btn-box",
            s.type = "line" == t.btnType ? "line": "",
            s.btnClass = t.btnClass || "",
            t.btnFn = t.btnFn || o,
            s.btnText = t.btnText || "";
            var r = [],
            c = '<div class="mt30r {0}">{1}</div>',
            u = '<a href="javascript:;" class="{0} ta-c fs16 ptb15r" {2}>{1}</a>',
            v = "",
            f = "line" == t.btnType ? 1 : 0,
            h = "";
            if (s.count = "", "string" == a(s.btnText)) {
                var p = e(t.btnAttr);
                s.btnClass = 0 == f ? s.btnClass + " ba": s.btnClass,
                h = i(u, s.btnClass, s.btnText);
                var m = f ? " bt abs-mb": "";
                v = i(c, s.btnType + m, h, p),
                r = [t.btnFn]
            } else if ("array" == a(s.btnText) && 2 == s.btnText.length) {
                var b = s.btnText;
                s.btnClass = "array" == a(s.btnClass) ? s.btnClass: [s.btnClass, s.btnClass],
                "array" == a(t.btnFn) ? r = t.btnFn: "function" == a(t.btnFn) && (r = [t.btnFn, o]);
                for (var y = 0,
                k = s.btnText.length; k > y; y++) {
                    var C = 0 == y && f ? " br": "";
                    C = 0 == f ? " ba": C;
                    var p = e(t.btnAttr, y),
                    w = s.btnClass[y];
                    w = w || "",
                    h += i(u, w + C, b[y], p)
                }
                var m = f ? " bt abs-mb": "";
                v = i(c, s.btnType + m + " multiple", h)
            } else if ("array" == a(s.btnText) && 3 == s.btnText.length) {
                s.count = "three";
                var b = s.btnText;
                s.btnClass = "array" == a(s.btnClass) ? s.btnClass: [s.btnClass, s.btnClass],
                "array" == a(t.btnFn) ? (t.btnFn.splice(t.btnFn.length, 0, o, o, o), r = t.btnFn, r.length = 3) : "function" == a(t.btnFn) && (r = [t.btnFn, o, o]);
                for (var y = 0,
                k = s.btnText.length; k > y; y++) {
                    var C = " bt",
                    p = e(t.btnAttr, y),
                    w = s.btnClass[y];
                    w = w || "",
                    h += i(u, w + C, b[y], p)
                }
                var m = f ? " abs-mb": "";
                v = i(c, s.btnType + m, h)
            }
            s.btnResult = v;
            var x = '<div class="dialog dialog-msg fix-mt fullLayer zoomOut"> <div class="mask fullLayer abs-lt"></div> <div class="content abs-mm pr ta-c {count} {type} {notitle}" {dialogStyle}> <div class="close-btn abs-rt {showClose}"> <img src="{baseUrl}/close_btn.png" class="abs-mm" alt=""/> </div> <h4 class="fs18 font-bold ta-c pb20r {showTitle}">{title}</h4> <div {style} class="message fs16 ta-l pr {contentClass}">{content}</div> {btnResult}  </div> </div>',
            L = {
                close: o
            };
            $("body").append(i(x, s)),
            animate.dialogMsg.show($(".dialog-msg")),
            $(".dialog-msg").on(g, ".close-btn",
            function() {
                o()
            }).on(g, ".btn-box a, .btnline-box a",
            function() {
                var t = $(this).index();
                r[t] && r[t](L)
            })
        }
        function e(t, e) {
            var o = "";
            if (!t) return o;
            e = void 0 === e ? 0 : e;
            var i = t,
            s = a(t);
            return "array" == s ? (i = t[e], i = i || {},
            o = "string" == a(i) ? i: n(i)) : "object" == s && 0 === e ? o = n(i) : "string" == s && 0 === e && (o = t),
            o
        }
        function n(t) {
            var e = "";
            for (var n in t) e += n + '="' + t[n] + '" ';
            return e
        }
        function a(t) {
            var e = Object.prototype.toString.call(t),
            n = e.replace(/\[object\s+(\w+)\]/gim, "$1");
            return n.toLowerCase()
        }
        toolsComponents.showMessage = t
    } (),
    toolsComponents.lockPage = e,
    toolsComponents.unlockPage = n;
    var C = null;
    toolsComponents.getBrowserInfo = i,
    function() {
        function t(t, e) {
            function n(t) {
                return i.replace(/\$/gm, t)
            }
            var a = e.count || 60,
            o = e.className,
            i = e.countText || "$秒后重发",
            s = e.reText || "重新发送";
            o && t || console.error("className和按钮为必填"),
            t.addClass(o).html(n(a));
            var d = t.get(0);
            clearInterval(d.timer),
            d.timer = setInterval(function() {
                return a--,
                0 == a ? (clearInterval(d.timer), void t.html(s).removeClass(o)) : void t.html(n(a))
            },
            1e3)
        }
        toolsComponents.countDown = t
    } (),
    function() {
        var t = {
            domnode: $('<div class="jdloading fix-mt fullLayer"> <div class="loading abs-mm"> <div class="loading-circle fullLayer"></div> </div> </div>'),
            show: function(t) {
                this.hide(),
                t && this.domnode.addClass("no-mask"),
                $("body").append(this.domnode.removeClass(y))
            },
            hide: function() {
                $(".jdloading").remove()
            }
        };
        toolsComponents.loading = t
    } (),
    function() {
        function t(t) {
            var n = this,
            a = "keyboard_" + o(8);
            this.opt = t,
            this.opt.id = a,
            this.opt.result = t.init ? t.init.split("") : [];
            var i = t.box ? t.box: "body",
            s = t.box ? "abs-lt": "fix-lb",
            d = {
                keyClass: s,
                id: a,
                showClose: t.showClose === !1 ? "hidden": "",
                keyStyle: t.keyStyle || " "
            };
            $(i).append(tools.format(e, d)),
            $(i).on(g, "#" + a + " .arrow-box",
            function() {
                n.close(),
                n.opt.closeFn && n.opt.closeFn()
            }),
            $("#" + a).on("touchend", "li>div",
            function() {
                var t = this,
                e = $(this).data("key");
                if ("" !== e && void 0 !== e && ($(this).addClass("active"), setTimeout(function() {
                    $(t).removeClass("active")
                },
                160)), n.opt.result.length < n.opt.max && /^\d$/.test(e)) {
                    n.opt.result.push(e);
                    var a = n.getResult();
                    n.opt.numicFn && n.opt.numicFn(e, a)
                } else if ("del" == e && n.opt.result.length > 0) {
                    n.opt.result.pop();
                    var a = n.getResult();
                    n.opt.delFn && n.opt.delFn(a)
                }
            })
        }
        var e = '<div class="keyboard pct100 {keyClass} num hidden" id="{id}"> <div class="abs-mt symbol bt border-box "> <h5 class="abs-mm">安全键盘</h5> <div class="arrow-box abs-rm {showClose}"> <a href="javascript:;" class="arrow-down abs-mm"></a> </div> </div> <ul class="pct100 bt"> <li class="bb"> <div data-key="1"> 1</div> <div data-key="2" class="br bl">2</div> <div data-key="3"> 3</div> </li> <li class="bb"> <div data-key="4"> 4</div> <div data-key="5" class="br bl"> 5</div> <div data-key="6"> 6</div> </li> <li class="bb"> <div data-key="7"> 7</div> <div data-key="8" class="br bl"> 8</div> <div data-key="9"> 9</div> </li> <li class="bb"> <div class="bg-gray"> </div> <div data-key="0" class="br bl"> 0</div> <div data-key="del" class="del bg-gray"> <span class="abs-mm"></span></div> </li> </ul> </div>',
        n = ["none", "slideUp"];
        t.prototype = {
            constructor: t,
            show: function(t) {
                var e = $("#" + this.opt.id);
                return t = !0,
                t ? e.addClass(n[0]).removeClass(y).addClass(n[1]) : animate.keyboard.show(e),
                this
            },
            close: function(t) {
                var e = $("#" + this.opt.id);
                return t = !0,
                t ? e.removeClass(n[0]).addClass(y).removeClass(n[1]) : animate.keyboard.close(e),
                this
            },
            empty: function() {
                return this.opt.result = [],
                this
            },
            isShow: function() {
                var t = $("#" + this.opt.id);
                return t.hasClass("slideUp") && t.is(":visible")
            },
            getResult: function() {
                return this.opt.result.join("")
            },
            setResult: function(t) {
                return "string" == typeof t && (this.opt.result = t.split("")),
                this
            },
            closeAndClear: function() {
                return this.close(),
                this.empty(),
                this
            }
        },
        toolsComponents.KeyboardNumber = t
    } (),
    function() {
        function t(t) {
            var a = this,
            i = "keyboard_" + o(8),
            s = {
                id: i,
                key: t.key ? t.key: "",
                showClose: t.showClose === !1 ? "hidden": ""
            },
            d = "type-letter";
            /^(letter|num|char)$/.test(t["default"]) && (d = "type-" + t["default"]),
            s.defaultType = d,
            this.opt = t,
            this.opt.id = i,
            this.opt.result = t.init ? t.init.split("") : [];
            var l = t.box ? t.box: "body";
            $(l).append(tools.format(n, s));
            var r = "type-num type-letter type-char",
            c = $("#" + i),
            u = c.find("ul.letter");
            c.off().on(g, " .arrow-box",
            function() {
                a.close(),
                a.opt.closeFn && a.opt.closeFn()
            }).on("touchend", "li>div",
            function() {
                var t = this,
                n = $(this).data("key");
                if ("" !== n && void 0 !== n && "fn_caps" != n && ($(this).addClass(e), setTimeout(function() {
                    $(t).removeClass(e)
                },
                160)), "fn_del" == n) {
                    if (a.opt.result.length > 0) {
                        a.opt.result.pop();
                        var o = a.getResult();
                        a.opt.delFn && a.opt.delFn(o)
                    }
                } else if (/^fn_/.test(n)) {
                    $(this).toggleClass(e);
                    var i = n.substr(3);
                    if ("fn_caps" == n) {
                        var s = $(this).hasClass(e);
                        u.find("div[data-key]").each(function(t, e) {
                            var n = $(this).data("key");
                            if (1 == n.length && !/^\s+$/.test(n)) {
                                var a = s ? n.toUpperCase() : n.toLowerCase();
                                $(this).html(a)
                            }
                        })
                    } else "fn_num" == n ? c.removeClass(r).addClass("type-num") : "fn_char" == n ? c.removeClass(r).addClass("type-char") : "fn_letter" == n && c.removeClass(r).addClass("type-letter");
                    a.opt.fn && a.opt.fn(i)
                } else if (a.opt.result.length < a.opt.max) {
                    c.find(".caps").hasClass(e) && !/^\d+$/.test(n) && (n = n.toUpperCase()),
                    a.opt.result.push(n);
                    var d = a.getResult();
                    a.opt.letterFn && a.opt.letterFn(n, d)
                }
            })
        }
        var e = "active",
        n = '<div class="keyboard pct100 abs-lb letter hidden {defaultType}" id="{id}"> <div class="abs-mt symbol bt border-box "><h5 class="abs-mm">安全键盘</h5> <div class="arrow-box abs-rm {showClose}"><a href="javascript:;" class="arrow-down abs-mm"></a></div> </div><!--------  数字键盘 --------> <ul class="pct100 bt num"> <li> <div data-key="1">1</div> <div data-key="2" class="br bl">2</div> <div data-key="3">3</div> </li> <li class="bt"> <div data-key="4">4</div> <div data-key="5" class="br bl">5</div> <div data-key="6">6</div> </li> <li class="bt"> <div data-key="7">7</div> <div data-key="8" class="br bl">8</div> <div data-key="9">9</div> </li> <li class="bt"> <div class="fs18 bg-gray" data-key="fn_letter">ABC</div> <div data-key="0" class="br bl">0</div> <div data-key="fn_del" class="del bg-gray"> <span class="abs-mm"></span> </div> </li> </ul><!--------  字母键盘 --------> <ul class="pct100 bt letter"> <li> <div data-key="q">q</div> <div data-key="w">w</div> <div data-key="e">e</div> <div data-key="r">r</div> <div data-key="t">t</div> <div data-key="y">y</div> <div data-key="u">u</div> <div data-key="i">i</div> <div data-key="o">o</div> <div data-key="p">p</div> </li> <li class="bt"> <div data-key="a">a</div> <div data-key="s">s</div> <div data-key="d">d</div> <div data-key="f">f</div> <div data-key="g">g</div> <div data-key="h">h</div> <div data-key="j">j</div> <div data-key="k">k</div> <div data-key="l">l</div> </li> <li> <div data-key="fn_caps" class="bg-gray caps"> <span class="abs-mm"></span> </div> <!-- 大小写切换 --> <div data-key="z">z</div> <div data-key="x">x</div> <div data-key="c">c</div> <div data-key="v">v</div> <div data-key="b">b</div> <div data-key="n">n</div> <div data-key="m">m</div> <div data-key="fn_del" class="bg-gray del"> <span class="abs-mm"></span> </div> <!-- 删除 --> </li> <li class="bt"> <div data-key="fn_num" class="fs18 bg-gray">123</div> <!-- 123 --> <div data-key="&nbsp;" class="blank"><span class="abs-mm bb br bl"></span></div> <!-- 空格 --> <div data-key="fn_char" class="fs16 bg-gray">#+=</div><!-- 符号 --></li></ul><!--------  字符键盘 --------><ul class="pct100 bt char"> <li> <div data-key="!">!</div> <div data-key="@">@</div><div data-key="#">#</div><div data-key="$">$</div> <div data-key="%">%</div> <div data-key="^">^</div> <div data-key="&">&</div> <div data-key="*">*</div> <div data-key="(">(</div> <div data-key=")">)</div></li><li class="bt"> <div data-key="&apos;">&apos;</div><div data-key="&quot;">&quot;</div><div data-key="=">=</div> <div data-key="_">_</div> <div data-key=":">:</div><div data-key=";">;</div><div data-key="?">?</div><div data-key="~">~</div> <div data-key="|">|</div> <div data-key="`">&#96;</div></li><li><div data-key="+">+</div><div data-key="-">-</div><div data-key="&#92;">&#92;</div><div data-key="&#47;">&#47;</div><div data-key="[">[</div><div data-key="]">]</div><div data-key="&#123;">&#123;</div><div data-key="&#125;">&#125;</div><div data-key="fn_del" class="bg-gray del"><span class="abs-mm"></span></div></li><li><div data-key="fn_num" class="fs18 bg-gray">123</div><div data-key=".">.</div><div data-key=",">,</div><div data-key="&lt;">&lt;</div><div data-key="&gt;">&gt;</div><div data-key="&euro;">&euro;</div><div data-key="&pound;">&pound;</div><div data-key="&yen;">&yen;</div><div data-key="fn_letter" class="fs18 bg-gray">ABC</div></li></ul> </div>',
        a = ["none", "slideUp"];
        t.prototype = {
            constructor: t,
            show: function(t) {
                var e = $("#" + this.opt.id);
                return t = !0,
                t ? e.addClass(a[0]).removeClass(y).addClass(a[1]) : animate.keyboard.show(e),
                this
            },
            close: function(t) {
                var e = $("#" + this.opt.id);
                return t = !0,
                t ? e.removeClass(a[0]).addClass(y).removeClass(a[1]) : animate.keyboard.close(e),
                this
            },
            empty: function() {
                return this.opt.result = [],
                this
            },
            isShow: function() {
                var t = $("#" + this.opt.id);
                return t.hasClass("slideUp") && t.is(":visible")
            },
            getResult: function() {
                return this.opt.result.join("")
            },
            setResult: function(t) {
                return "string" == typeof t && (this.opt.result = t.split("")),
                this
            },
            closeAndClear: function() {
                return this.close(),
                this.empty(),
                this
            }
        },
        toolsComponents.KeyboardLetter = t
    } (),
    function() {
        function t(t) {
            this.opts = t,
            this.init()
        }
        function t(t) {
            this.opts = t,
            this.init()
        }
        var e = i(),
        n = navigator.userAgent,
        a = n.toUpperCase().match(/CPU\s+IPHONE\s+OS\s+(\d)/),
        o = a && 2 == a.length ? a[1] : 0,
        s = "animationend webkitAnimationEnd",
        d = "transitionend webkitTransitionEnd";
        if (e.isIos && o > 7) {
            var l = '<div class="success-loading abs-mm {statusClass} {visible}" style="zoom:{zoom};"> <svg id="svg"><!-- 底色 --> <circle r="27" cx="30" cy="30" fill="{color}"></circle><!-- 遮罩 --> <circle  class="circle-mask" r="28" cx="30" cy="30" fill="{bgColor}"></circle><!-- 圆轨 --> <path d="M3 30 A25 25 0 1 1 57 30 A25 25 0 0 1 3 30 A25 25 0 1 1 57 30 A25 25 0 0 1 3 30 A25 25 0 1 1 57 30 A25 25 0 0 1 3 30 A25 25 0 1 1 57 30" fill="none" stroke="{color}" stroke-width="3" class="path-rotate"></path><!-- 对号 --> <path  stroke-linecap="round" stroke-linejoin="round" class="path-correct" d="M17 30 L26 38 L43 21" fill="none" stroke="{bgColor}" stroke-width="3"></path> </svg> <div class="circle-maskUp" style="background-color:{bgColor}"></div></div>',
            r = ["rotate", "full", "scale", "complete", "scaleUp"];
            t.prototype = {
                constructor: t,
                init: function() {
                    var t = this,
                    e = 1,
                    n = "fullLayer",
                    a = $(this.opts.box);
                    if (t.opts.color = t.opts.color || "#fff", t.opts.bgColor = t.opts.bgColor || "#f0f2f5", t.opts.visible = t.opts.visible || "", 0 == a.length) a = $("body"),
                    n = "single";
                    else {
                        var o = a.width();
                        e = o / 60,
                        t.zoom = e
                    }
                    a.find(".success-loading").remove();
                    var i = $.extend(t.opts, {
                        statusClass: n,
                        zoom: e
                    });
                    a.append(tools.format(l, i)),
                    t.$box = a;
                    var c = a.find(".success-loading"),
                    u = c.find(".path-rotate"),
                    v = c.find(".circle-mask"),
                    f = c.find(".path-correct"),
                    h = c.find(".circle-maskUp");
                    t.$pathRotate = u,
                    t.$loading = c,
                    "button" == t.opts.type && v.addClass("zoom"),
                    u.off().on(s,
                    function(e) {
                        u.hasClass(r[0]) && !t.isComplete ? t.start() : u.hasClass(r[0]) && t.isComplete ? (t.opts.rotateEnd && t.opts.rotateEnd(), u.removeClass(r[0]).addClass(r[1])) : u.hasClass(r[1]) && (t.opts.fullEnd && t.opts.fullEnd(), v.addClass(r[2]))
                    }),
                    v.off().on(s,
                    function(e) {
                        t.opts.scaleEnd && t.opts.scaleEnd(),
                        c.addClass(r[3])
                    }),
                    f.off().on(d,
                    function() {
                        t.opts.correctEnd && t.opts.correctEnd(),
                        t.correctEnd && t.correctEnd()
                    }),
                    h.off().on(s,
                    function(t) {})
                },
                complete: function(t) {
                    this.isComplete = !0,
                    this.correctEnd = t
                },
                close: function() {
                    var t = this;
                    t.$loading.addClass(y)
                },
                start: function() {
                    var t = this;
                    t.$pathRotate.removeClass(r[0]),
                    setTimeout(function() {
                        t.$pathRotate.addClass(r[0])
                    },
                    10)
                },
                show: function() {
                    var t = this,
                    e = t.zoom;
                    if (!e) {
                        var n = $(t.opts.box);
                        if (n.length > 0) {
                            var a = n.width();
                            e = a / 60
                        }
                        n.find(".success-loading").css("zoom", e)
                    }
                    setTimeout(function() {
                        t.start()
                    },
                    10),
                    t.$loading.removeClass(y)
                }
            }
        } else {
            var l = '<div class="success-loading {androidClass}"><div class="circle-maskUp hidden" style="background-color:{color}"></div></div>',
            c = [44, 45, 63, 73, 76],
            u = [0, c[0], c[1]];
            t.prototype = {
                constructor: t,
                init: function() {
                    var t = this,
                    e = "fullLayer",
                    n = $(t.opts.box),
                    a = t.opts.color || "#fff",
                    o = "android";
                    "button" == t.opts.type && (o = "android-btn", c = [28, 29, 39, 45, 58], u = [0, c[0], c[1]]),
                    0 == n.length && (n = $("body"), e = "single");
                    var i = n.find(".success-loading");
                    i.remove(),
                    n.append(tools.format(l, {
                        statusClass: e,
                        color: a,
                        androidClass: o
                    })),
                    i = n.find(".success-loading"),
                    t.$box = n,
                    t.$loading = i,
                    t.status = 0
                },
                complete: function(t) {
                    this.status = 1,
                    this.correctEnd = t
                },
                close: function() {
                    var t = this;
                    t.$loading.addClass(y)
                },
                start: function() {
                    var t = this,
                    e = 0,
                    n = t.$box,
                    a = n.get(0),
                    o = n.find(".success-loading"),
                    i = n.find(".circle-maskUp"),
                    d = o.height();
                    o.height(d).width(d),
                    clearInterval(a.timer),
                    a.timer = setInterval(function() {
                        if (e++, e == c[0] && 0 == t.status) e = u[0];
                        else if (e == c[0] && 1 == t.status) e = u[1],
                        t.opts.rotateEnd && t.opts.rotateEnd();
                        else if (e == c[2]) t.opts.fullEnd && t.opts.fullEnd();
                        else if (e == c[3]) t.opts.scaleEnd && t.opts.scaleEnd();
                        else if (e == c[4]) return t.opts.correctEnd && t.opts.correctEnd(),
                        t.correctEnd && t.correctEnd(),
                        void clearInterval(a.timer);
                        o.css({
                            "background-position": "0 " + -e * d + "px"
                        })
                    },
                    60),
                    i.off().on(s,
                    function(t) {})
                },
                show: function() {
                    var t = this;
                    setTimeout(function() {
                        t.start()
                    },
                    10),
                    t.$loading.removeClass(y)
                }
            }
        }
        toolsComponents.SuccessLoading = t
    } (),
    function() {
        function t(t, n) {
            var a = this;
            n = n || {},
            this.opts = $.extend({
                focus: !0,
                split: !1
            },
            n),
            this.data = {
                event: {},
                timer: null,
                text: [],
                cursorIndex: 0
            },
            this.data.$box = t,
            this.data.$content = t.find(".custom-content"),
            this.data.$pointer = a.data.$content.find(".custom-pointer"),
            e.pageInit(this),
            this.opts.focus && e.startBlink(this)
        }
        t.prototype = {
            constructor: t,
            on: function(t, e) {
                return this.data.event[t] = e,
                this
            },
            focus: function() {
                e.startBlink()
            },
            blur: function() {
                var t = this.data;
                clearInterval(t.timer),
                t.$pointer.addClass(y)
            },
            backspace: function() {
                var t = this.data,
                n = t.text,
                a = t.cursorIndex;
                1 > a || (" " === n[a - 2] ? (t.cursorIndex--, n.splice(a - 2, 2)) : n.splice(a - 1, 1), t.cursorIndex--, e.redraw(this), e.handleEvent(this, "change"))
            },
            insert: function(t) {
                var n = this.data,
                a = this.opts,
                o = n.text,
                i = n.cursorIndex;
                a.split && 0 != i && (i + 1) % 5 == 0 ? (o.splice(i, 0, " ", t), n.cursorIndex++) : o.splice(i, 0, t),
                n.cursorIndex++,
                e.redraw(this),
                e.handleEvent(this, "change")
            },
            setValue: function(t) {
                var n = this.data;
                n.text = tools.split4(t).split(""),
                n.cursorIndex = n.text.length,
                e.redraw(this),
                e.handleEvent(this, "change")
            },
            getValue: function() {
                var t = this.data;
                return t.text.join("").replace(/\s+/g, "")
            },
            getText: function() {
                var t = this.data;
                return t.text.join("")
            }
        };
        var e = {
            pageInit: function(t) {
                var e = this,
                n = t.data;
                n.$box.on(g,
                function(a) {
                    var o = $(a.target);
                    "SPAN" != o.get(0).tagName.toUpperCase() || o.hasClass(".custom-pointer") ? (o.parents(".custom-text").length > 0 || o.hasClass("custom-text")) && (n.cursorIndex = n.text.length) : n.cursorIndex = o.index(),
                    e.locatePointer(t),
                    e.handleEvent(t, "click,focus")
                })
            },
            redraw: function(t) {
                var n = t.data,
                a = t.opts,
                o = "",
                i = n.text,
                s = [];
                if (a.split) {
                    for (var d = 0,
                    l = i.length; l > d; d++)" " !== i[d] && s.push(i[d]);
                    i = tools.split4(s.join("")).split("")
                }
                for (var d = 0,
                l = i.length; l > d; d++) {
                    var r = " " === i[d] ? "&nbsp;": i[d];
                    o += "<span>" + r + "</span>"
                }
                e.getSpans(t).remove(),
                n.$content.append(o),
                e.locatePointer(t)
            },
            locatePointer: function(t) {
                var e = t.data,
                n = e.text,
                a = e.$pointer,
                o = e.cursorIndex;
                if (0 == o) return void a.css({
                    left: 0
                });
                " " === n[o - 1] && (o--, e.cursorIndex--);
                var i = this.getSpans(t).eq(o - 1);
                if (0 != i.length) {
                    var s = i.position(),
                    d = i.offset();
                    s && d && a.css({
                        left: s.left + d.width - 1
                    })
                }
            },
            getSpans: function(t) {
                return t.data.$content.find("span").filter(":not(.custom-pointer)")
            },
            startBlink: function(t) {
                var e = t.data;
                clearInterval(e.timer),
                e.timer = setInterval(function() {
                    e.$pointer.toggleClass(y)
                },
                500)
            },
            handleEvent: function(t, e) {
                for (var n = t.data,
                a = e.replace(/\s+/g, "").split(","), o = 0, i = a.length; i > o; o++) {
                    var s = a[o];
                    n.event[s] && n.event[s]()
                }
            }
        };
        toolsComponents.CustomInput = t
    } (),
    function() {
        var t = {
            init: function() {
                var t = "fadeIn",
                e = null,
                n = this;
                n.pageLoading = {
                    init: function() {
                        var n = $(".page-loading");
                        n.on("transitionend webkitTransitionEnd",
                        function() {
                            clearTimeout(e),
                            e = setTimeout(function() {
                                a.start()
                            },
                            10)
                        });
                        var a = new tools.SuccessLoading({
                            box: $(".loading-container"),
                            color: "#00c752",
                            rotateEnd: function() {},
                            fullEnd: function() {},
                            scaleEnd: function() {
                                n.find("p").html("支付成功")
                            },
                            correctEnd: function() {}
                        });
                        return a.show = function() {
                            n.removeClass(y),
                            setTimeout(function() {
                                n.addClass(t)
                            },
                            15)
                        },
                        a.close = function() {
                            n.removeClass(t),
                            setTimeout(function() {
                                n.addClass(y)
                            },
                            410)
                        },
                        a
                    }
                },
                n.buttonLoading = {
                    loading: {},
                    init: function(t) {
                        t = $(t);
                        var e = t.find(".btn-box a i");
                        return t.hasClass("btn-box") ? e = t.find("a i") : t.parent().hasClass("btn-box") && (e = t.find("i")),
                        e.empty(),
                        this.loading = new tools.SuccessLoading({
                            box: e,
                            type: "button",
                            bgColor: "#ee3d3d",
                            color: "#fff",
                            visible: "hidden"
                        }),
                        this.loading
                    }
                },
                n.buttonNormalLoading = {
                    loading: null,
                    init: function(t) {
                        t = $(t);
                        var e = t.find(".btn-box a i");
                        return t.hasClass("btn-box") ? e = t.find("a i") : t.parent().hasClass("btn-box") && (e = t.find("i")),
                        e.empty(),
                        this.loading = function() {
                            e.empty().append('<div class="loading-normal hidden"></div>');
                            var t = e.find(".loading-normal");
                            return {
                                show: function() {
                                    return t.removeClass(y)
                                },
                                close: function() {
                                    return t.removeClass(y)
                                }
                            }
                        } (),
                        this.loading
                    }
                },
                window.pageLoading = n,
                window.pageInterface = window.pageInterface || {},
                pageInterface.pageLoading = n.pageLoading,
                pageInterface.buttonLoading = n.buttonLoading,
                pageInterface.buttonNormalLoading = n.buttonNormalLoading
            }
        };
        t.init(),
        toolsComponents.pageLoading = t
    } (),
    toolsComponents.validator = {
        checkTel: function(t) {
            return /^1\d{10}$/.test(t)
        },
        checkIdNo: function(t) {
            return /^(\d{17}[xX])|(\d{18})$/.test(t)
        }
    },
    toolsComponents.showQianBaoLoading = function() {
        var t = "transitionend webkitTransitionEnd",
        e = '<div class="dialog loading-qianbao fix-mt fullLayer"> <div class="mask fullLayer abs-lt"></div> <div class="content abs-mm"> <div class="loading-line abs-mb"> <div class="active-line pr"><span class="loading-dot abs-rm"></span></div> </div> </div> </div>',
        n = $(".loading-qianbao");
        n.remove(),
        $("body").append(e),
        n = $("body").find(".loading-qianbao");
        var a = n.find(".active-line");
        n.on(t,
        function(t) {
            $(t.target).hasClass("loading-qianbao") && $(this).addClass(y);
        }),
        a.on(t,
        function() {
            n.addClass("self-fadeOut")
        }),
        setTimeout(function() {
            n.addClass("move")
        },
        100)
    };
    var w = {};
    window.dialog = window.dialog || {},
    k.method(w, "dialog", {
        quickCardList: r,
        flowPayList: c,
        couponList: u,
        allCouponList: f,
        stageList: v,
        bankList: l,
        bankTabList: d,
        numpwd: b
    }),
    k.method(w, "keyboard", {
        number: m
    }),
    k.method(w, "custom", {
        datePicker: p
    }),
    h.prototype = {
        init: function(t) {
            $.extend(this, this.options, t),
            this.dateLMoveL = 0,
            this.dateRMoveL = 0,
            this.dateL_touchStart = this.dateR_touchStart = this.dateL_touchEnd = this.dateR_touchEnd,
            this.events()
        },
        events: function() {
            var t = this,
            e = t.dateLMoveL,
            n = t.dateRMoveL;
            $(".shadeDate").on("click",
            function(t) {
                0 == $(t.target).closest(".dateBox").length && $(".shadeDate").removeClass(y)
            }),
            $("body").on(g, ".dateControl .done",
            function() {
                var e = t.gainDate(1);
                t.done(e),
                $(".shadeDate").removeClass(y),
                tools.unlockPage()
            });
            var a = .3;
            $(".dateL").on("touchstart",
            function(e) {
                tools.lockPage(),
                t.dateL_touchStart = e.touches[0].clientY
            }).on("touchmove",
            function(n) {
                t.dateL_touchEnd = n.touches[0].clientY,
                e = t.dateLMoveL + t.dateL_touchEnd - t.dateL_touchStart,
                e <= -t.dateLHeight ? e = -t.dateLHeight: e >= 0 && (e = 0),
                $(".dateL_cells_U").css({
                    "-webkit-transform": "translateY(" + e + "px)"
                })
            }).on("touchend",
            function(n) {
                t.dateLMoveL = e,
                Math.abs(t.dateLMoveL % 36) < 18 ? (t.dateLMoveL -= t.dateLMoveL % 36, t.dateAnimate($(".dateL_cells_U"), t.dateLMoveL, a)) : Math.abs(t.dateLMoveL % 36) >= 18 && (t.dateLMoveL -= 36 - Math.abs(t.dateLMoveL % 36), t.dateAnimate($(".dateL_cells_U"), t.dateLMoveL, a))
            }),
            $(".dateR").on("touchstart",
            function(e) {
                tools.lockPage(),
                t.dateR_touchStart = e.touches[0].clientY
            }).on("touchmove",
            function(e) {
                t.dateR_touchEnd = e.touches[0].clientY,
                n = t.dateRMoveL + t.dateR_touchEnd - t.dateR_touchStart,
                n <= -t.dateRHeight ? n = -t.dateRHeight: n >= 0 && (n = 0),
                $(".dateR_cells_U").css({
                    "-webkit-transform": "translateY(" + n + "px)"
                })
            }).on("touchend",
            function(e) {
                t.dateRMoveL = n,
                Math.abs(t.dateRMoveL % 36) < 18 ? (t.dateRMoveL -= t.dateRMoveL % 36, t.dateAnimate($(".dateR_cells_U"), t.dateRMoveL, a)) : Math.abs(t.dateRMoveL % 36) >= 18 && (t.dateRMoveL -= 36 - Math.abs(t.dateRMoveL % 36), t.dateAnimate($(".dateR_cells_U"), t.dateRMoveL, a))
            })
        },
        dateAnimate: function(t, e, n) {
            t.css({
                "-webkit-transform": "translateY(" + e + "px)",
                "-webkit-transition-property": "all",
                "-webkit-transition-duration": n + "s",
                "-webkit-transition-delay": "0s",
                "-webkit-transition-timing-function": "ease-out"
            })
        },
        setDate: function() {
            var t = this,
            e = $(".dateL_cell"),
            n = $(".dateR_cell");
            $.each(e,
            function(n, a) {
                parseInt(e.eq(n).find(".year").html()) == t.year && parseInt(e.eq(n).find(".month").html()) == t.month && (t.dateLMoveL += -36 * n, t.dateAnimate($(".dateL_cells_U"), -36 * n, "0.3s"))
            }),
            $.each(n,
            function(e, a) {
                if (parseInt(n.eq(e).html()) == t.day) {
                    t.dateRMoveL += -36 * e;
                    var o = $(".dateR_cells_U"),
                    i = -36 * e,
                    s = "0.3s";
                    t.dateAnimate(o, i, s)
                }
            })
        },
        gainDate: function(t, e) {
            function n(t) {
                var e = $(t).css("transform");
                e = null == e ? $(t).css("-webkit-transform") : e;
                var n = e.replace(/translateY\(\-?(\d+)px\)/, "$1");
                return isNaN(parseInt(n)) ? 0 : n
            }
            var a = this,
            o = $(".dateL_cell"),
            i = $(".dateR_cell"),
            s = 0,
            d = n(".dateL_cells_U") || Math.abs(a.dateLMoveL),
            l = n(".dateR_cells_U") || Math.abs(a.dateRMoveL),
            r = parseInt(d / 36),
            c = parseInt(l / 36),
            u = parseInt(i.eq(c).html()),
            v = parseInt(o.eq(r).html());
            return t ? [u, v] : [u, v, s]
        },
        done: function(t) {}
    };
    var x = "split4Num";
    window.tools = $.extend({
        format: function(t) {
            var e = /\{([\w\-_]+)\}/gm,
            n = Array.prototype.slice.call(arguments, 1),
            a = /\{(\d+)\}/.test(t) ? n: n[0],
            o = t.replace(e,
            function() {
                return a[arguments[1]]
            });
            return o
        },
        split4Num: function(t, e) {
            return 0 == t.length || t.data(x) ? void console.error("文本框输入内容分组出错：传入对象为空或者已经调用过该方法") : (t.data(x, !0), void t.on("keyup input",
            function() {
                var t = $(this).val(),
                n = t.replace(/\s+/gm, "");
                t = n.replace(/(\d{4})/gm, "$& ").replace(/\s+$/gm, ""),
                $(this).val(t),
                e && e(n)
            }))
        },
        split4: function(t) {
            var e = t.replace(/\s+/gm, "");
            return t = e.replace(/(\d{4})/gm, "$& ").replace(/\s+$/gm, "")
        },
        orientationChange: function() {
            90 != window.orientation && -90 != window.orientation || setTimeout(function() {
                tools.alertDialogWarning({
                    text: "竖屏显示体验会更好哟",
                    boxStyle: {
                        "z-index": 2001
                    }
                })
            },
            100)
        },
        loadComponent: function(t, e) {
            var n = k.getByNS(w, t);
            if (!n) return void console.error("找不到该控件 : " + t);
            if (n.loaded) return void console.log("loadComponents", "已经加载过该控件");
            n.loaded = !0;
            var a = Array.prototype.slice.call(arguments, 1);
            return n.apply(null, a)
        }
    },
    toolsComponents)
} (),
document.addEventListener("DOMContentLoaded",
function(t) {
    if (window.devicePixelRatio && devicePixelRatio >= 2) {
        var e = document.createElement("div");
        e.style.border = ".5px solid transparent",
        document.body.appendChild(e),
        1 == e.offsetHeight && document.querySelector("html").classList.add("hairlines"),
        document.body.removeChild(e)
    }
}),
document.ontouchmove = function(t) {},
window.addEventListener("onorientationchange" in window ? "orientationchange": "resize", tools.orientationChange, !1);
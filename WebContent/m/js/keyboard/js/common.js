/* @update: 2016-10-10 14:7:7 */
!
function() {
    var e = "click",
    n = "hidden",
    o = {};
    window.pageInterface = window.pageInterface || {
        send: function(e) {},
        focusPhone: function() {},
        focusCode: function() {},
        clearPhone: function() {},
        clearCode: function() {},
        reinput: function() {}
    },
    pageInterface.send = function(e) {
        o.send(e)
    };
    var t = {
        init: function() {
            this.pageInit(),
            this.countInit()
        },
        pageInit: function() {
            function o(e, o) {
                $(window).height(),
                $(".login").height();
                setTimeout(function() {
                    e.show(),
                    v.eq(o).removeClass(n);
                    var t = $("#" + e.opt.id).offset();
                    t.top < 0 && $(".keyboard").removeClass("fix")
                },
                300)
            }
            function t(e) {
                C.setResult(e),
                u.html(e)
            }
            function a(e) {
                u.html(e),
                m.val(e)
            }
            function i(e) {
                f.html(e),
                r.val(e)
            }
            function c(e, o) {
                e.length > 0 ? h.eq(o).addClass(n) : h.eq(o).removeClass(n),
                m.val().length > 0 && r.val().length > 0 ? g.removeClass(s) : g.addClass(s)
            }
            var s = "disabled",
            l = $(".info-code"),
            d = $(".info-tel"),
            f = $(".info-code .dot-box"),
            u = $(".info-tel .dot-box"),
            r = $("#txt_code"),
            m = $("#txt_tel"),
            v = $(".custom-pointer"),
            h = $(".custom-holder"),
            g = $(".login .btn-box a"),
            C = (setInterval(function() {
                v.toggleClass("v-h")
            },
            500), new tools.KeyboardNumber({
                max: 11,
                numicFn: function(e, n) {
                    a(n),
                    c(n, 0)
                },
                delFn: function(e) {
                    a(e),
                    c(e, 0)
                }
            })),
            p = new tools.KeyboardNumber({
                max: 6,
                numicFn: function(e, n) {
                    i(n),
                    c(n, 1)
                },
                delFn: function(e) {
                    i(e),
                    c(e, 1)
                }
            }),
            b = "disabled" == m.attr("dis");
            b = !b,
            m.val() && (t(m.val()), d.find(".custom-holder").addClass(n)),
            b ? o(C, 0) : o(p, 1),
            pageInterface.focusPhone = function() {
                r.val() || l.find(".custom-holder").removeClass(n),
                p.close(),
                C.show(),
                v.addClass(n).eq(0).removeClass(n)
            },
            pageInterface.focusCode = function() {
                m.val() || d.find(".custom-holder").removeClass(n),
                C.close(),
                p.show(),
                v.addClass(n).eq(1).removeClass(n)
            },
            pageInterface.clearPhone = function() {
                a(""),
                C.empty()
            },
            pageInterface.clearCode = function() {
                i(""),
                p.empty()
            },
            pageInterface.reinput = function() {
                C.close(),
                p.show(),
                v.addClass(n).eq(1).removeClass(n),
                $(".info-con").find(".dot-box").empty(),
                p.empty(),
                r.val(""),
                g.addClass("disabled")
            },
            $("body").on(e, ".needKeyBoard",
            function() {
                b && pageInterface.focusPhone()
            })
        },
        countInit: function() {
            var e = $(".btn-count"),
            n = "\u91cd\u65b0\u83b7\u53d6";
            o.send = function(o) {
                var o = o || 10;
                tools.countDown(e, {
                    className: "disabled",
                    count: o,
                    reText: n,
                    countText: "$\u79d2\u540e\u91cd\u53d1"
                })
            },
            o.stop = function() {
                e.html(n).removeClass("disabled"),
                clearInterval(e.get(0).timer)
            }
        }
    };
    $(function() {
        FastClick && FastClick.attach(document.body),
        t.init()
    })
} ();
;function initPage() {   // 初始化页面
    if(top != window) {
        top.location.href = location.href;
    }
}

initPage();

$(function(){
    initDoc();
});

function winResize() {  // 自动计算高度并设置
    $("#mainFrame").css(
        { 'height': $(document).height() - $("#navtoolbar").outerHeight(true) - 18
        });
}

function initDoc() {
    winResize();

    $(window).resize(function(){
        winResize();
    });

    $("#navbar-collapse ul.navbar-nav > li > a[id]").click(function(e) {
        if(!$(this).attr("href")) {
            return true;
        }
        e.preventDefault();
        return toPage(this);
    });
    $("#navbar-collapse ul.dropdown-menu > li > a[id]").click(function(e) {
        if(!$(this).attr("href")) {
            return true;
        }
        e.preventDefault();
        return toPage(this);
    });

    $.hash().listen("m0", "p", hashSearchChangeHandler);

    if($.hash().get("p")) {
        hashSearchChangeHandler($.hash().get());
    } else {
        goIndex();
    }
}

function hashSearchChangeHandler(newRet, oldRet) {
    if(newRet.m0) {
        var $thiz = $("#" + newRet.m0).parent();
        $("#navbar-collapse li").removeClass("active");
        $thiz.addClass("active");
    }
    if(newRet.p) {
        $("#mainFrame").attr("src", decodeURIComponent(newRet.p));
    }
}

function goIndex() {
    return toPage($("#navbar-collapse li a[href*='/']").first());
}

function updateActive(thiz) { // 更新工具栏选择状态
    toPage(thiz);
    return true;
}
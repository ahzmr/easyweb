if(top == window) { // 主页执行
    if("/easyweb/" != location.pathname) {
        toPage(location.pathname);
    }
}

if(top != window) { // 子页执行
    $(function() {
        $("a[href*='/']").click(function(e) {
            e.preventDefault();
            top.toPage($(this));
        });
    });
}
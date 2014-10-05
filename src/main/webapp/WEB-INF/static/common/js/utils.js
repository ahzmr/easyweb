var global = global || {};

function goHistory(cur) {
    history.go(cur);
}

function toPage(path, notGo) {
    if(null) {
        return;
    }
    var p = path;
    if(typeof path != "string") {
        p = $(path).attr("href");
    }
    global.prevPage = $.hash().get("p");
    if(!notGo) {
        var $mainFrame = $("#mainFrame");
        if($mainFrame && $mainFrame.length) {
            $mainFrame.attr("src", p);
        } else {
            location.href = location.href.replace(/(\/easyweb\/)[^#]+/i, "$1");
        }
    }
    return false;   // 成功处理，不继续处理
}

$(function() {
   if(top == window && "/easyweb/" != location.pathname) {
       toPage(location.pathname);
   }
});

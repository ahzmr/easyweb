function goHistory(cur) {   // 历史跳转
    history.go(cur);
}

function subUrlFormat(url) {    // 子网网站格式化
    if(!url) {
        return url;
    }
    return encodeURIComponent(url.replace(/.*(\/easyweb\/)/i, ""));
}

function toPage(path) { // 跳转页面
    if(!path) {
        return;
    }
    var p = path;
    var m0 = null;
    if(typeof path != "string") {
        p = $(path).data("url") || $(path).attr("href");
        m0 = $(path).attr("pid");
    }
    if(!m0) {
        m0 = top.$.hash().get("m0");
    }
    p = subUrlFormat(p);
    top.location.href = "/easyweb/#?m0=" + m0 + "&p=" + p;
    return true;   // 成功处理
}

function page(pageNum) {   // 分页跳转
    var searchForm = $("#searchForm");
    if(!searchForm) {
        searchForm = $("form:first");
    }
    var $pageNum = searchForm.find('[name="pageNum"]:input');
    if(!$pageNum.length) {
        $pageNum=$('<input type="hidden" name="pageNum" />');
        searchForm.append($pageNum);
    }
    $pageNum.val(pageNum);
    searchForm.submit();
    return false;
}

function loading(mess){ // 显示加载框
    top.$.jBox.tip.mess = null;
    top.$.jBox.tip(mess,'loading',{opacity:0});
}

// 取消处理函数
function cancelHander() {
    return false;
}

// 只读处理函数
function readonlyHander(i, e) {
    var thiz = e && $(e) || $(this);
    thiz.addClass("readonly").attr("readonly", "readonly").attr("onclick", cancelHander);
}
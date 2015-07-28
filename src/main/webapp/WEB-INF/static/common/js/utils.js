function goHistory(cur) {   // 历史跳转
    history.go(cur);
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

function toPage(path) {
    if(!(typeof path === 'string')) {
        path = $(path).attr("href");
    }
    top.$("#mainFrame").attr("src", path);
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
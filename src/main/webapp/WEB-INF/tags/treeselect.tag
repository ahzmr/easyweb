<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/includes/taglib.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="隐藏域名称（ID）"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="隐藏域值（ID）"%>
<%@ attribute name="labelName" type="java.lang.String" required="true" description="输入框名称（Name）"%>
<%@ attribute name="labelValue" type="java.lang.String" required="true" description="输入框值（Name）"%>
<%@ attribute name="title" type="java.lang.String" required="true" description="选择框标题"%>
<%@ attribute name="url" type="java.lang.String" required="true" description="树结构数据地址"%>
<%@ attribute name="checked" type="java.lang.Boolean" required="false" description="是否显示复选框，如果不需要返回父节点，请设置notAllowSelectParent为true"%>
<%@ attribute name="extIds" type="java.lang.String" required="false" description="排除掉的编号（不能选择的编号）"%>
<%@ attribute name="notAllowSelectRoot" type="java.lang.Boolean" required="false" description="不允许选择根节点"%>
<%@ attribute name="notAllowSelectParent" type="java.lang.Boolean" required="false" description="不允许选择父节点"%>
<%@ attribute name="allowClear" type="java.lang.Boolean" required="false" description="是否允许清除"%>
<%@ attribute name="allowInput" type="java.lang.Boolean" required="false" description="文本框可填写"%>
<%@ attribute name="cssClass" type="java.lang.String" required="false" description="css样式"%>
<%@ attribute name="cssStyle" type="java.lang.String" required="false" description="css样式"%>
<%@ attribute name="hideBtn" type="java.lang.Boolean" required="false" description="是否显示按钮"%>
<%@ attribute name="disabled" type="java.lang.String" required="false" description="是否限制选择，如果限制，设置为disabled"%>
<%@ attribute name="dataMsgRequired" type="java.lang.String" required="false" description="必输项未选择的提示信息"%>

<link rel="stylesheet" href="${libUrl}/zTree_v3/css/zTreeStyle/zTreeStyle.css" />
<script src="${libUrl}/zTree_v3/js/jquery.ztree.all-3.5.min.js"></script>

<input id="${id}Id" name="${name}" class="${cssClass}" type="hidden" value="${value}"/>
<div class="input-group">
    <input id="${id}Name" name="${labelName}" ${allowInput?'':'readonly="readonly"'} type="text" value="${labelValue}" data-msg-required="${dataMsgRequired}"
           class="${cssClass} form-control" style="${cssStyle}"/>
    <div id="${id}Button" class="input-group-addon ${disabled} ${hideBtn ? 'hide' : ''}">&nbsp;<span class="glyphicon glyphicon-search"></span>&nbsp;</div>
</div>

<script type="text/javascript">
    var lastSelectTreeUrl = null;
    $("\#${id}Button, \#${id}Name").click(function(){
        // 是否限制选择，如果限制，设置为disabled
        if ($("\#${id}Button").hasClass("disabled")){
            return true;
        }
        $("#treeSelectModal").modal("show");
        if(lastSelectTreeUrl && lastSelectTreeUrl == "${url}") {
            return true;
        }
        lastSelectTreeUrl = "${url}"
        var ztreeSetting = {
            async: {
                enable: true,
                autoParam: ["id"],
                type: "post",
                dataType: "json",
                url: "${url}",
                otherParam: [ "extIds", "${extIds}"],
                dataFilter: function (treeId, parentNode, responseData) {
                    return responseData;
                }
            },
            check: {
                enable:"${checked}",
                nocheckInherit: true
            },
            callback: {
                onDblClick: function() {
                    $("#saveSelectTree").click();
                }
            }
        };
        var tree = $.fn.zTree.init($("#selectTree"), ztreeSetting);
        $("#saveSelectTree").click(function(e) {
            var nodes;
            var ids = [];
            var names = [];
            if ("${checked}" == "true"){
                nodes = tree.getCheckedNodes(true);
            }else{
                nodes = tree.getSelectedNodes();
            }
            for(var i=0; i<nodes.length; i++) {//<c:if test="${checked && notAllowSelectParent}">
                if (nodes[i].isParent){
                    continue; // 如果为复选框选择，则过滤掉父节点
                }//</c:if><c:if test="${notAllowSelectRoot}">
                if (nodes[i].level == 0){
                    top.$.jBox.tip("不能选择根节点（"+nodes[i].name+"）请重新选择。");
                    return false;
                }//</c:if><c:if test="${notAllowSelectParent}">
                if (nodes[i].isParent){
                    top.$.jBox.tip("不能选择父节点（"+nodes[i].name+"）请重新选择。");
                    return false;
                }//</c:if>
                ids.push(nodes[i].id);
                names.push(nodes[i].name);//<c:if test="${!checked}">
                break; // 如果为非复选框选择，则返回第一个选择  </c:if>
            }
            $("\#${id}Id").val(ids.join(","));
            $("\#${id}Name").val(names.join(","));
            $("#treeSelectModal").modal("hide");
        });
        $("#cleanSelectTree").click(function(e) {
            $("\#${id}Id").val("");
            $("\#${id}Name").val("");
            $("#treeSelectModal").modal("hide");
        });
    });
</script>
<div class="modal fade" id="treeSelectModal" tabindex="-1" role="dialog" aria-labelledby="treeSelectModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="treeSelectModalLabel">选择${title}</h4>
            </div>
            <div class="modal-body">
                <div id="selectTree" class="ztree"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button><c:if test="${allowClear}">
                <button id="cleanSelectTree" type="button" class="btn btn-default" >清除</button></c:if>
                <button id="saveSelectTree" type="button" class="btn btn-primary" >保存</button>
            </div>
        </div>
    </div>
</div>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@include file="/WEB-INF/includes/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta name="decorator" content="tree"/>
    <title>角色管理</title>
    <script>
        $(function() {
            initForm("#inputForm", {
                submitHandler: function(form){
                    var ids = [], nodes = menuTree.getCheckedNodes(true);
                    for(var i=0; i<nodes.length; i++) {
                        ids.push(nodes[i].id);
                    }
                    $("#menuIds").val(ids);
                    loading('正在提交，请稍等...');
                    form.submit();
                }
            });
            var selectIds = "${fns:join(entry.menuIds, ',')}";
            var map = {};
            if(selectIds) {
                var split = selectIds.split(",");
                for(var i = 0; i < split.length; i++) {
                    map[split[i]] = true;
                }
            }
            var menus = [
                    <c:forEach items="${menus}" var="menu">{id: "${menu.id}", pId: "${menu.parentId}", name: "${menu.name}", checked: map["${menu.id}"]},
                </c:forEach>];
            var settings = {
                check: {
                    enable: true,
                    nocheckInherit: true
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback: {
                    beforeClick: function(treeId, treeNode, clickFlag) {
                        menuTree.checkNode(treeNode, !treeNode.checked);
                        return false;
                    }
                }
            }
            var menuTree = $.fn.zTree.init($("#menuTree"), settings, menus);
            menuTree.expandAll(true);
        });
    </script>
</head>
<body>
<div class="container-fluid">
    <ul class="nav nav-tabs">
        <li><a href="${baseUrl}/system/SysRole/">角色列表</a></li>
        <li class="active"><a href="${baseUrl}/system/SysRole/form?isView=${param.isView}&id=${entry.id}">
        角色<tags:autoFormLabel editPermission="system:SysRole:edit" id="${entry.id}" /></a></li>
    </ul><br/>
<form:form id="inputForm" modelAttribute="entry" action="${baseUrl}/system/SysRole/save"
           method="post" class="form-horizontal inputForm">
    <tags:message />
    <div class="form-group hide-add">
        <label class="col-sm-3 control-label" for="id">编号：</label>
        <div class="col-sm-9">
                <form:input path="id" class="form-control readonly" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="name">名称：</label>
        <div class="col-sm-9">
                <form:input path="name" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="code">编码：</label>
        <div class="col-sm-9">
                <form:input path="code" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="remarks">备注信息：</label>
        <div class="col-sm-9">
            <form:input path="remarks" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="remarks">角色授权：</label>
        <div class="col-sm-9">
            <input id="menuIds" type="hidden" name="menuIds"/>
            <div id="menuTree" class="ztree"></div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label hide-add" for="createBy">创建者：</label>
        <div class="col-sm-9">
                <form:input path="createBy" class="form-control readonly" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label hide-add" for="createDate">创建时间：</label>
        <div class="col-sm-9">
                <input id="createDate" name="createDate" type="text" readonly="readonly" maxlength="20"
                       class="Wdate form-control readonly" value="<fmt:formatDate value="${entry.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label hide-add" for="updateBy">更新者：</label>
        <div class="col-sm-9">
                <form:input path="updateBy" class="form-control readonly" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label hide-add" for="updateDate">更新时间：</label>
        <div class="col-sm-9">
                <input id="updateDate" name="updateDate" type="text" readonly="readonly" maxlength="20"
                       class="Wdate form-control" value="<fmt:formatDate value="${entry.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
        </div>
    </div>
    <input type="hidden" name="delFlag" value="${entry.delFlag}" />
    <div class="form-group">
        <div class="col-sm-12 text-center">
            <shiro:hasPermission name="system:SysRole:edit">
                <button class="btn btn-primary" type="submit">保 存</button>&nbsp;
            </shiro:hasPermission>
            <button class="btn" type="button" onclick="goHistory(-1)">返 回</button>
        </div>
    </div>
</form:form>
</div>
</body>
</html>

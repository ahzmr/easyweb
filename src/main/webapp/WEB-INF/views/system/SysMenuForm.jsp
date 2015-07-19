<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@include file="/WEB-INF/includes/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta name="decorator" content="default"/>
    <title>菜单管理</title>
    <script>
        $(function() {
           initForm("#inputForm", {
               rules: {
                   name: { maxlength: 100, required: true },
                   sort: { number: true, required: true },
                   href: { maxlength: 2047 },
                   icon: { maxlength: 100 },
                   permission: { maxlength: 200 },
                   remarks: { maxlength: 255 }
               }
           });
        });
    </script>
</head>
<body>
<div class="container-fluid">
    <ul class="nav nav-tabs">
        <li><a href="${baseUrl}/system/SysMenu/">菜单列表</a></li>
        <li class="active"><a href="${baseUrl}/system/SysMenu/form?isView=${param.isView}&id=${entry.id}">
        菜单<tags:autoFormLabel editPermission="system:SysMenu:edit" id="${entry.id}" /></a></li>
    </ul><br/>
<form:form id="inputForm" modelAttribute="entry" action="${baseUrl}/system/SysMenu/save"
           method="post" class="form-horizontal inputForm">
    <tags:message />
    <div class="form-group hide-add">
        <label class="col-sm-3 control-label" for="id">编号：</label>
        <div class="col-sm-9">
                <form:input path="id" class="form-control readonly" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="parentId">父编号：</label>
        <div class="col-sm-9">
            <tags:treeselect id="parentId" name="parentId" value="${entry.parentId}"
                             labelName="parentIdLabel" labelValue="${entry.parentName}" allowClear="true"
                             title="父菜单" url="${baseUrl}/system/SysMenu/queryByParent.json" />
        </div>
    </div>
    <input type="hidden" name="parentIds" value="${entry.parentIds}" />
    <div class="form-group">
        <label class="col-sm-3 control-label" for="name">名称：</label>
        <div class="col-sm-9">
                <form:input path="name" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="sort">排序：</label>
        <div class="col-sm-9">
                <form:input path="sort" class="number form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="href">链接：</label>
        <div class="col-sm-9">
                <form:input path="href" class="form-control" />
        </div>
    </div>
    <%--<div class="form-group">
        <label class="col-sm-3 control-label" for="icon">图标：</label>
        <div class="col-sm-9">
                <form:input path="icon" class="form-control" />
        </div>
    </div>--%>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="isShow">是否显示：</label>
        <div class="col-sm-9 checkbox">
                <label><form:checkbox path="isShow" value="1" />显示</label>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="permission">权限标识：</label>
        <div class="col-sm-9">
                <form:input path="permission" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label">备注信息：</label>
        <div class="col-sm-9">
                <form:input path="remarks" class="form-control" />
        </div>
    </div>
    <div class="form-group hide-add">
        <label class="col-sm-3 control-label" for="createBy">创建者：</label>
        <div class="col-sm-9">
                <form:input path="createBy" class="form-control readonly" />
        </div>
    </div>
    <div class="form-group hide-add">
        <label class="col-sm-3 control-label" for="createDate">创建时间：</label>
        <div class="col-sm-9">
                <input id="createDate" name="createDate" type="text" readonly="readonly" maxlength="20"
                       class="Wdate form-control readonly" value="<fmt:formatDate value="${entry.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
        </div>
    </div>
    <div class="form-group hide-add">
        <label class="col-sm-3 control-label" for="updateBy">更新者：</label>
        <div class="col-sm-9">
                <form:input path="updateBy" class="form-control readonly" />
        </div>
    </div>
    <div class="form-group hide-add">
        <label class="col-sm-3 control-label" for="updateDate">更新时间：</label>
        <div class="col-sm-9">
                <input id="updateDate" name="updateDate" type="text" readonly="readonly" maxlength="20"
                       class="Wdate form-control readonly" value="<fmt:formatDate value="${entry.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
        </div>
    </div>
    <input type="hidden" name="delFlag" value="${entry.delFlag}" />
    <div class="form-group">
        <div class="col-sm-12 text-center">
            <shiro:hasPermission name="system:SysMenu:edit">
                <button class="btn btn-primary" type="submit">保 存</button>&nbsp;
            </shiro:hasPermission>
            <button class="btn" type="button" onclick="goHistory(-1)">返 回</button>
        </div>
    </div>
</form:form>
</div>
</body>
</html>

<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@include file="/WEB-INF/includes/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta name="decorator" content="default"/>
    <title>字典管理</title>
    <script>
        $(function() {
           initForm("#inputForm", {
               groups: {
                   typeVal: "value type"
               },
               rules: {
                   value: { maxlength: 100, required: true, repeatCheck: {
                       url: "${baseUrl}/system/SysDict/validate.json",
                       oldValue: "${entry.value}",
                       otherParam: {
                           type: function() { return $("#type").val(); }
                       }
                   } },
                   label: { maxlength: 100, required: true },
                   type: { maxlength: 100, required: true },
                   description: { maxlength: 100, required: true },
                   sort: { number: true, required: true },
                   remarks: { maxlength: 255 }
               }
           });
        });
    </script>
</head>
<body>
<div class="container-fluid">
    <ul class="nav nav-tabs">
        <li><a href="${baseUrl}/system/SysDict/">字典列表</a></li>
        <li class="active"><a href="${baseUrl}/system/SysDict/form?isView=${param.isView}&id=${entry.id}">
        字典<tags:autoFormLabel editPermission="system:SysDict:edit" id="${entry.id}" /></a></li>
    </ul><br/>
<form:form id="inputForm" modelAttribute="entry" action="${baseUrl}/system/SysDict/save"
           method="post" class="form-horizontal inputForm">
    <tags:message />
    <div class="form-group hide-add">
        <label class="col-sm-3 control-label" for="id">编号：</label>
        <div class="col-sm-9">
                <form:input path="id" class="form-control readonly-edit" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="label">标签名：</label>
        <div class="col-sm-9">
            <form:input path="label" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="type">类型：</label>
        <div class="col-sm-9">
            <form:input path="type" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="value">数据值：</label>
        <div class="col-sm-9">
                <form:input path="value" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="description">描述：</label>
        <div class="col-sm-9">
                <form:input path="description" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="sort">排序：</label>
        <div class="col-sm-9">
                <form:input path="sort" class="number form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="remarks">备注信息：</label>
        <div class="col-sm-9">
                <form:input path="remarks" class="form-control" />
        </div>
    </div>
    <div class="form-group hide-add">
        <label class="col-sm-3 control-label" for="createBy">创建者：</label>
        <div class="col-sm-9">
                <form:input path="createBy" class="form-control readonly-edit" />
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
            <shiro:hasPermission name="system:SysDict:edit">
                <button class="btn btn-primary" type="submit">保 存</button>&nbsp;
            </shiro:hasPermission>
            <button class="btn" type="button" onclick="goHistory(-1)">返 回</button>
        </div>
    </div>
</form:form>
</div>
</body>
</html>

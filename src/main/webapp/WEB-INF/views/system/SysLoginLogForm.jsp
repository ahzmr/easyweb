<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@include file="/WEB-INF/includes/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta name="decorator" content="default"/>
    <title>字典管理</title>
    <script>
        $(function() {
           initForm();
        });
    </script>
</head>
<body>
<div class="container-fluid">
    <ul class="nav nav-tabs">
        <li><a href="${baseUrl}/system/SysLoginLog/">字典列表</a></li>
        <li class="active"><a href="${baseUrl}/system/SysLoginLog/form?isView=${param.isView}&id=${entry.id}">
        字典<tags:autoFormLabel editPermission="system:SysLoginLog:edit" id="${entry.id}" /></a></li>
    </ul><br/>
<form:form id="inputForm" modelAttribute="entry" action="${baseUrl}/system/SysLoginLog/save"
           method="post" class="form-horizontal inputForm">
    <tags:message />
    <div class="form-group">
        <label class="col-sm-3 control-label" for="id">编号：</label>
        <div class="col-sm-9">
                <form:input path="id" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="createBy">登录人：</label>
        <div class="col-sm-9">
            <form:input path="createBy" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="createDate">登录时间：</label>
        <div class="col-sm-9">
            <input id="createDate" name="createDate" type="text" readonly="readonly" maxlength="20"
                   class="Wdate form-control" value="<fmt:formatDate value="${entry.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="loginIp">登录IP：</label>
        <div class="col-sm-9">
                <form:input path="loginIp" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="loginLocation">登录地点：</label>
        <div class="col-sm-9">
                <form:input path="loginLocation" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="osName">操作系统：</label>
        <div class="col-sm-9">
                <form:input path="osName" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="osVersion">操作系统版本：</label>
        <div class="col-sm-9">
                <form:input path="osVersion" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="deviceName">设备名称：</label>
        <div class="col-sm-9">
                <form:input path="deviceName" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="deviceType">设备类型：</label>
        <div class="col-sm-9">
                <form:input path="deviceType" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="appName">应用名称：</label>
        <div class="col-sm-9">
                <form:input path="appName" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="appType">应用类型：</label>
        <div class="col-sm-9">
                <form:input path="appType" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="appVersion">应用版本：</label>
        <div class="col-sm-9">
                <form:input path="appVersion" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="remarks">备注信息：</label>
        <div class="col-sm-9">
                <form:input path="remarks" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-12 text-center">
            <button class="btn" type="button" onclick="goHistory(-1)">返 回</button>
        </div>
    </div>
</form:form>
</div>
</body>
</html>

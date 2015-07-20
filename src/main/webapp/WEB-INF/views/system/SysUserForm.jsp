<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@include file="/WEB-INF/includes/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta name="decorator" content="default"/>
    <title>用户管理</title>
    <script>
        $(function() {
           initForm("#inputForm", {
               rules: {
                   loginName: { maxlength: 100, required: true },
                   no: { maxlength: 100 },
                   name: { maxlength: 100, required: true },
                   email: { email: 200 },
                   phone: { mobileOrPhoneCN: true },
                   mobile: { mobileOrPhoneCN: true},
                   remarks: { maxlength: 255 }
               }
           });
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${baseUrl}/system/SysUser/">用户列表</a></li>
    <li class="active"><a href="${baseUrl}/system/SysUser/form?isView=${param.isView}&id=${entry.id}">
    用户<tags:autoFormLabel editPermission="system:SysUser:edit" id="${entry.id}" /></a></li>
</ul><br/>
<div class="container-fluid">
<form:form id="inputForm" modelAttribute="entry" action="${baseUrl}/system/SysUser/save"
           method="post" class="form-horizontal inputForm">
    <tags:message />
    <div class="form-group hide-add">
        <label class="col-sm-3  control-label" for="id">编号：</label>
        <div class="col-sm-9">
                <form:input path="id" class="form-control readonly-edit" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3  control-label" for="loginName">登录名：</label>
        <div class="col-sm-9">
                <form:input path="loginName" class="form-control" />
        </div>
    </div>
    <input name="password" type="hidden" value="${entry.password}" />
    <div class="form-group">
        <label class="col-sm-3  control-label" for="no">工号：</label>
        <div class="col-sm-9">
                <form:input path="no" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3  control-label" for="name">姓名：</label>
        <div class="col-sm-9">
                <form:input path="name" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3  control-label" for="email">邮箱：</label>
        <div class="col-sm-9">
                <form:input path="email" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3  control-label" for="phone">电话：</label>
        <div class="col-sm-9">
                <form:input path="phone" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3  control-label" for="mobile">手机：</label>
        <div class="col-sm-9">
                <form:input path="mobile" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3  control-label" for="remarks">备注信息：</label>
        <div class="col-sm-9">
            <form:input path="remarks" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3  control-label" for="remarks">用户角色：</label>
        <div class="col-sm-9">
            <form:checkboxes items="${roles}" itemLabel="name" itemValue="id"
                             path="roleIds" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3  control-label" for="loginIp">最后登陆IP：</label>
        <div class="col-sm-9">
                <form:input path="loginIp" class="form-control readonly" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3  control-label" for="loginDate">最后登陆时间：</label>
        <div class="col-sm-9">
                <input id="loginDate" name="loginDate" type="text" readonly="readonly" maxlength="20"
                       class="Wdate form-control" value="<fmt:formatDate value="${entry.loginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
        </div>
    </div>
    <div class="form-group hide-add">
        <label class="col-sm-3  control-label" for="createBy">创建者：</label>
        <div class="col-sm-9">
                <form:input path="createBy" class="form-control readonly" />
        </div>
    </div>
    <div class="form-group hide-add">
        <label class="col-sm-3  control-label" for="createDate">创建时间：</label>
        <div class="col-sm-9">
                <input id="createDate" name="createDate" type="text" readonly="readonly" maxlength="20"
                       class="Wdate form-control" value="<fmt:formatDate value="${entry.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
        </div>
    </div>
    <div class="form-group hide-add">
        <label class="col-sm-3  control-label" for="updateBy">更新者：</label>
        <div class="col-sm-9">
                <form:input path="updateBy" class="form-control readonly" />
        </div>
    </div>
    <div class="form-group hide-add">
        <label class="col-sm-3  control-label" for="updateDate">更新时间：</label>
        <div class="col-sm-9">
                <input id="updateDate" name="updateDate" type="text" readonly="readonly" maxlength="20"
                       class="Wdate form-control" value="<fmt:formatDate value="${entry.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
        </div>
    </div>
    <input name="delFlag" type="hidden" value="${entry.delFlag}" />
    <div class="form-group">
        <div class="col-sm-12 text-center">
            <shiro:hasPermission name="system:SysUser:edit">
                <button class="btn btn-primary" type="submit">保 存</button>&nbsp;
            </shiro:hasPermission>
            <button class="btn" type="button" onclick="goHistory(-1)">返 回</button>
        </div>
    </div>
</form:form>
</div>
</body>
</html>

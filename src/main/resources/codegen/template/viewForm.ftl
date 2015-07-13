<#assign entityLable = table.remarks?replace("表", "") />
<#assign basePath = "${r'${baseUrl}'}/${moduleName}/${table.className}/" />
<#assign basePerm = "${moduleName}:${table.className}" />
<#assign entityIdRef = "${r'${'}entry.${table.primaryField.name}}" />
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@include file="/WEB-INF/includes/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta name="decorator" content="default"/>
    <title>${entityLable}管理</title>
    <script>
        $(function() {
           initForm();
        });
    </script>
</head>
<body>
<div class="container-fluid">
    <ul class="nav nav-tabs">
        <li><a href="${basePath}">${entityLable}列表</a></li>
        <li class="active"><a href="${basePath}form?isView=${r'${param.isView}'}&id=${entityIdRef}">
        ${entityLable}<tags:autoFormLabel editPermission="${basePerm}:edit" id="${entityIdRef}" /></a></li>
    </ul><br/>
<form:form id="inputForm" modelAttribute="entry" action="${basePath}save"
           method="post" class="form-horizontal">
    <tags:message />
    <#list table.fieldList as field>
    <div class="form-group">
        <label class="col-sm-3  control-label">${field.remarks}：</label>
        <div class="col-sm-9">
            <#if field.javaTypeName == 'Integer' || field.javaTypeName == 'Long' >
                <form:input path="${field.name}" class="number form-control"/>
            <#elseif field.javaTypeName == 'Date'>
                <input id="${field.name}" name="${field.name}" type="text" readonly="readonly" maxlength="20"
                       class="Wdate form-control" value="<fmt:formatDate value="${r'${entry.'}${field.name}}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
            <#else>
                <form:input path="${field.name}" class="form-control" />
            </#if>
        </div>
    </div>
    </#list>
    <div class="form-group">
        <div class="col-sm-12 text-center">
            <shiro:hasPermission name="${basePerm}:edit">
                <button class="btn btn-primary" type="submit">保 存</button>&nbsp;
            </shiro:hasPermission>
            <button class="btn" type="button" onclick="goHistory(-1)">返 回</button>
        </div>
    </div>
</form:form>
</div>
</body>
</html>

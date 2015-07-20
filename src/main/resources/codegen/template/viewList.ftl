<#assign entityLable = table.remarks?replace("表", "") />
<#assign basePath = "${r'${baseUrl}'}/${moduleName}/${table.className}/" />
<#assign basePerm = "${moduleName}:${table.className}" />
<#assign entityIdRef = "${r'${'}entity.${table.primaryField.name}}" />
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@include file="/WEB-INF/includes/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta name="decorator" content="default"/>
    <title>${entityLable}列表</title>
    <script>
        $(function() {
            initForm("#searchForm");
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${basePath}">${entityLable}列表</a></li>
    <shiro:hasPermission name="${basePerm}:edit"><li><a href="${basePath}form">${entityLable}添加</a></li></shiro:hasPermission>
</ul>
<form id="searchForm" class="form-horizontal search-form"
      action="${basePath}" method="post">
    <div class="row">
        <div class="form-group col-lg-4 col-sm-6">
            <label class="col-sm-4 control-label">${table.primaryField.remarks}：</label>
            <div class="col-sm-8">
                <input type="text" name="${table.primaryField.name}" data-defVal="${r'${'}param.${table.primaryField.name}}" class="form-control" />
            </div>
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-12 text-center">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
            <input class="btn" type="reset" value="重置"/>
        </div>
    </div>
</form>
<tags:message />
<div class="container-fluid table-responsive">
    <table class="table table-condensed table-hover table-striped table-bordered" >
        <thead>
        <tr>
        <#list table.fieldList as field>
            <th>${field.remarks}</th>
        </#list>
        <shiro:hasPermission name="system:SysRole:edit">
            <th>操作</th>
        </shiro:hasPermission>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${r'${page}'}" var="entity">
            <tr>
            <#assign isFirst = 1 />
            <#list table.fieldList as field>
                <#if isFirst == 1 >
                    <td><a href="${basePath}form?isView=1&id=${entityIdRef}">${r'${'}entity.${field.name}}</a></td>
                    <#assign isFirst = 0 />
                <#elseif field.javaTypeName == 'Date'>
                    <td><fmt:formatDate value="${r'${'}entity.${field.name}}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                <#else>
                    <td>${r'${'}entity.${field.name}}</td>
                </#if>
            </#list>
                <shiro:hasPermission name="${basePerm}:edit"><td>
                    <a href="${basePath}form?id=${entityIdRef}">修改</a>
                    <a href="${basePath}delete?id=${entityIdRef}" onclick="return confirmx('确认要删除该${entityLable}吗？', this.href)">删除</a>
                </td></shiro:hasPermission>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <tags:pagination page="${r'${page}'}" />
</div>
</body>
</html>

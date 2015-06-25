<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/includes/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@include file="/WEB-INF/includes/commonBase.jsp"%>
    <title>信息提示</title>
</head>
<body>
<div class="container">
    <tags:message />
    <div class="row">
        <div class="form-group">
            <div class="col-md-12">
                <button class="btn btn-primary" onclick="return top.goIndex();"
                       type="button">返回主页</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>

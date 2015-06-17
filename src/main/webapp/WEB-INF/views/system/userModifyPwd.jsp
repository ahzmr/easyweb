<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/includes/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@include file="/WEB-INF/includes/common.jsp"%>
    <script src="${libUrl}/jsSHA/js/sha.js"></script>
    <script src="${commonUrl}/js/securityutils.js"></script>
    <title>修改个人密码</title>
    <script>
        $(function(){
            initForm({
                rules: {
                    loginName: { required: true },
                    p: { required: true },
                    password: { equalTo: "#p" }
                },
                submitHandler: function(form) {
                    var pwd = genPwd($("#fp").val(), $("#loginName").val());
                    $("#fp").val(pwd);
                    $("#p").val(pwd);
                    loading('正在提交，请稍等...');
                    form.submit();
                }
                     });
        });
    </script>
</head>
<body>
<div class="container-fluid" style="max-width: 540px; margin: 0 auto;">
    <form:form id="inputForm" modelAttribute="user" action="${baseUrl}/sys/user/modifyPwd.html"
               method="post" class="form-horizontal">
        <tags:message />
        <div class="form-group">
            <label class="col-sm-3 control-label">登陆名:</label>
            <div class="col-sm-9">
                <form:input path="loginName" class="form-control" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">新密码:</label>
            <div class="col-sm-9">
                <input type="password" id="p" name="p" class="form-control" />
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label">重复密码:</label>
            <div class="col-sm-9">
                <input type="password" id="fp" name="password" class="form-control" />
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-12 text-center">
                <button class="btn btn-primary" type="submit">保 存</button>&nbsp;
                <button class="btn" type="button" onclick="goHistory(-1)">返 回</button>
            </div>
        </div>
    </form:form>
</div>
</body>
</html>

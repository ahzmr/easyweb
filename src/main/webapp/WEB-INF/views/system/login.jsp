<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/includes/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/includes/common.jsp"%>
    <title>通讯录增加或修改</title>
    <script>
        $(function() {
            $("#cellphone").focus();
            $("#loginForm").validate(
                    {
                        rules: {
                            username: { required: true },
                            password: { required: true }
                        },
                        submitHandler: function(form){
                            loading('正在提交，请稍等...');
                            form.submit();
                        },
                        errorContainer: "#messageBox",
                        errorPlacement: function(error, element) {
                            $("#messageBox").text("输入有误，请先更正。");
                            if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                                error.appendTo(element.parent().parent());
                            } else {
                                error.insertAfter(element);
                            }
                        }
                    });
        });
    </script>
</head>
<body>
<div class="container-fluid" style="max-width: 480px; margin: 0 auto;">
    <form id="loginForm"  class="form login-form" action="./login.html" method="post">
        <tags:message content="${empty message?param.message:message}" />
        <legend><h3>系统登陆</h3></legend>
        <div class="control-group">
            <label class="col-sm-4 control-label">登录名:</label>
            <div class="col-sm-6">
                <input type="text" name="username" value="${username}" placeholder="学号">
            </div>
        </div>

        <div class="control-group">
            <label class="col-sm-4 control-label">密码:</label>
            <div class="col-sm-6">
                <input type="password" name="password" placeholder="密码"/>
            </div>
        </div><%--
        <div class="control-group">
            <div class="col-sm-offset-4 col-sm-6">
                <div class="checkbox">
                    <input type="checkbox" name="rememberMe"><span>记住我</span>
                </div>
            </div>
        </div>--%>
        <div class="control-group">
            <div class="col-sm-12 text-center">
                <input class="btn btn-primary" type="submit" value="登 录"/>
            </div>
        </div>
    </form>
</div>
</body>
</html>

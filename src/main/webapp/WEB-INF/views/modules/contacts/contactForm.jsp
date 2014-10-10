<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/includes/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@include file="/WEB-INF/includes/common.jsp"%>
    <title>通讯录增加或修改</title>
    <script>
        $(function() {
           $("#cellphone").focus();
           $.validator.addMethod("isStuNo", function(val, element) {
               return this.optional(element) || val && val.length > 8 &&
                                                parseInt(val.substring(8)) > 0 &&
                                                val.substring(0, 8) == "07070110";
           }, "请输入正确的学号");
           initForm({
               rules: {
                   id: { required: true, isStuNo: true },   // 学号
                   name: { required: true },  // 名称
                   cellphone: { required: true, mobileCN: true},  // 常用联系方式
                   phone: { mobileOrPhoneCN: true },  // 其它联系方式
                   education: { required: true },  // 学历
                   university: { required: true },  // 学校
                   department: { required: true },  // 院系
                   profession: { required: true },  // 专业
                   address: { required: true },  // 常住地点
                   workAddr: { required: true },  // 工作地点
                   job: { required: true },  // 职业
                   company: { required: true },  // 公司
                   myMsg: { required: true },  // 互助信息
                   memo: {}  // 备注
               }
                    });
        });
    </script>
</head>
<body>
<div class="container-fluid" style="max-width: 540px; margin: 0 auto;">
<form:form id="inputForm" modelAttribute="entry" action="${baseUrl}/contacts/save.html"
           method="post" class="form-horizontal">
    <tags:message />
    <div class="form-group">
        <label class="col-sm-3 control-label">学号:</label>
        <div class="col-sm-9">
            <form:input path="id" class="form-control" readonly="true" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label">姓名:</label>
        <div class="col-sm-9">
            <form:input path="name" class="form-control" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label">常用联系方式:</label>
        <div class="col-sm-9">
            <form:input id="cellphone" path="cellphone" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label">其它联系方式:</label>
        <div class="col-sm-9">
            <form:input path="phone" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label">学历:</label>
        <div class="col-sm-9">
            <form:select path="education" items='<%=new String[]{"本科", "硕士"}%>' class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label">学校:</label>
        <div class="col-sm-9">
            <form:input path="university" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label">院系:</label>
        <div class="col-sm-9">
            <form:input path="department" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label">专业:</label>
        <div class="col-sm-9">
            <form:input path="profession" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label">常住地点:</label>
        <div class="col-sm-9">
            <form:input path="address" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label">工作地点:</label>
        <div class="col-sm-9">
            <form:input path="workAddr" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label">职业:</label>
        <div class="col-sm-9">
            <form:input path="job" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label">公司:</label>
        <div class="col-sm-9">
            <form:input path="company" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label">互助信息:</label>
        <div class="col-sm-9">
            <form:input path="myMsg" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label">备注:</label>
        <div class="col-sm-9">
            <form:input path="memo" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-12 text-center">
            <shiro:hasPermission name="contacts:edit:${entry.id}">
                <button class="btn btn-primary" type="submit">保 存</button>&nbsp;
            </shiro:hasPermission>
            <button class="btn" type="button" onclick="goHistory(-1)">返 回</button>
        </div>
    </div>
</form:form>
</div>
<shiro:lacksPermission name="contacts:edit:${entry.id}">
    <script>
        $("input").attr("readonly", "readonly").addClass("readonly");
        $("select").attr("readonly", "readonly").addClass("readonly");
    </script>
</shiro:lacksPermission>
</body>
</html>

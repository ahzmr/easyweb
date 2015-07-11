<%@ tag language="java" pageEncoding="UTF-8" isELIgnored="false" trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/includes/taglib.jsp"%>
<%@ attribute name="editPermission" type="java.lang.String" required="true" description="修改权限" %>
<%@ attribute name="id" type="java.lang.Object" required="false" description="主键"%>
<c:if test="${not empty param.isView}">查看
    <c:set var="optType" value="view" />
</c:if>
<c:if test="${empty param.isView}">
    <shiro:hasPermission name="${editPermission}">${not empty id?'修改':'添加'}
        <c:set var="optType" value="${not empty id?'modi':'add'}" />
    </shiro:hasPermission>
    <shiro:lacksPermission name="${editPermission}">查看
        <c:set var="optType" value="view" />
    </shiro:lacksPermission>
</c:if>
<script>
    $(function() {
        $(".hide-${optType}").hide();
        $(".readonly-${optType}").each(readonlyHander);
        //<c:if test="${optType == 'view'}">
        $("input, select, textarea").not(".view-edit, .btn").each(readonlyHander);
        $("input[type=submit]").hide();
        //</c:if>
        //<c:if test="${optType != 'view'}">
        $(".hide-edit").hide();
        //</c:if>
        $('select.readonly option').not(":selected").hide().attr('disabled', true);
    })
</script>

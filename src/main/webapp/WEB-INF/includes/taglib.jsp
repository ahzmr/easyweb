<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}" />
<c:set var="staticUrl" value="${baseUrl}/static" />
<c:set var="libUrl" value="${staticUrl}/lib" />
<c:set var="commonUrl" value="${staticUrl}/common" />
<c:set var="modulesUrl" value="${staticUrl}/modules" />

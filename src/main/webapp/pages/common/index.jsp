<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="properties.pagecontent" />

<html>
<head>
    <title><fmt:message key="index.title" /></title>
</head>
<body>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_login_page"><fmt:message key="index.login" /></a>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_register_page"><fmt:message key="index.register" /></a>
</body>
</html>
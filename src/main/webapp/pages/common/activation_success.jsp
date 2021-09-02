<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="properties.pagecontent" />

<html>
<head>
    <title><fmt:message key="activation_success.title" /></title>
</head>
<body>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_index_page"><fmt:message key="pages.home" /></a>
    <p><fmt:message key="activation_success.message" /></p>
</body>
</html>

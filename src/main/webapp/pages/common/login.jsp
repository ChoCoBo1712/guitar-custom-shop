<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="properties.pagecontent" />

<html>
<head>
    <title><fmt:message key="login.title" /></title>
    <jsp:include page="shared/head.html" />
</head>
<body>
    <jsp:include page="shared/header.jsp" />

    <form action="${pageContext.request.contextPath}/controller?command=login" method="post">
        <input type="text" name="login" placeholder=<fmt:message key="placeholder.login" /> required>
        <br>
        <input type="password" name="password" placeholder=<fmt:message key="placeholder.password" /> required>
        <br>
        <input type="submit" value=<fmt:message key="login.submit" />>
    </form>

    <a href="${pageContext.request.contextPath}/controller?command=go_to_forgot_password_page">
        <fmt:message key="login.forgot_password" />
    </a>

    <c:if test="${requestScope.loginError}">
        <p><fmt:message key="error.login" /></p>
    </c:if>

    <jsp:include page="shared/footer.jsp" />
</body>
</html>

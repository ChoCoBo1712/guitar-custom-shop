<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="properties.pagecontent" />

<html>
<head>
    <title><fmt:message key="register.title" /></title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/controller?command=register" method="post">
        <input type="email" name="email" placeholder=<fmt:message key="register.email" /> required maxlength="50">
        <br>
        <input type="text" name="login" placeholder=<fmt:message key="register.login" /> required
               pattern="[0-9a-zA-Z]{8,20}">
        <br>
        <input type="password" name="password" placeholder=<fmt:message key="register.password" /> required
               pattern="(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z0-9]{8,32}">
        <br>
        <input type="submit" value=<fmt:message key="register.submit" />>
    </form>

    <c:if test="${requestScope.invalidEmailPatternError}">
        <p><fmt:message key="create.invalid_email" /></p>
    </c:if>

    <c:if test="${requestScope.duplicateEmailError}">
        <p><fmt:message key="create.duplicate_email" /></p>
    </c:if>

    <c:if test="${requestScope.invalidLoginPatternError}">
        <p><fmt:message key="create.invalid_login" /></p>
    </c:if>

    <c:if test="${requestScope.duplicateLoginError}">
        <p><fmt:message key="create.duplicate_login" /></p>
    </c:if>

    <c:if test="${requestScope.invalidPasswordPatternError}">
        <p><fmt:message key="create.invalid_password" /></p>
    </c:if>
</body>
</html>

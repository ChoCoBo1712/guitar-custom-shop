<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="properties.pagecontent" />

<html>
<head>
    <title><fmt:message key="register.title" /></title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/controller?command=register" method="post">
        <input type="email" name="email" placeholder=<fmt:message key="register.email" /> required><br/>
        <input type="text" name="login" placeholder=<fmt:message key="register.login" /> required minlength="6"><br/>
        <input type="password" name="password" placeholder=<fmt:message key="register.password" /> required minlength="8"><br/>
        <input type="submit" value=<fmt:message key="register.submit" />>
    </form>

    <p>${sessionScope.validationError}</p>
</body>
</html>

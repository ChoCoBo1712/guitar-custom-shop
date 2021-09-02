<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="properties.pagecontent" />

<html>
<head>
    <title><fmt:message key="login.title" /></title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/controller?command=login" method="post">
        <input type="text" name="login" placeholder=<fmt:message key="login.login" /> required><br/>
        <input type="password" name="password" placeholder=<fmt:message key="login.password" /> required><br/>
        <input type="submit" value=<fmt:message key="login.submit" />>
    </form>

    <p>${loginError}</p>
</body>
</html>

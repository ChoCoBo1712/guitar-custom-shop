<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="properties.pagecontent" />

<html>
<head>
    <title><fmt:message key="password_change.title" /></title>
</head>
<body>
    <jsp:include page="shared/header.jsp" />

    <form action="${pageContext.request.contextPath}/controller?command=password_change" method="post">
        <input type="text" name="token" value="${requestScope.token}" hidden>
        <input type="text" name="email" value="${requestScope.email}" hidden>
        <input type="password" name="password" placeholder=<fmt:message key="placeholder.password" /> required
               pattern="(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z0-9]{8,32}">
        <br>
        <input type="submit">
    </form>

    <c:if test="${requestScope.validationError}">
        <p><fmt:message key="error.validation_error" /></p>
    </c:if>

    <jsp:include page="shared/footer.jsp" />
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="properties.pagecontent" />

<html>
<head>
    <title><fmt:message key="forgot_password.title" /></title>
    <jsp:include page="shared/head.html" />
</head>
<body>
    <jsp:include page="shared/header.jsp" />

    <form action="${pageContext.request.contextPath}/controller?command=send_password_change_link" method="post">
        <input type="email" name="email" placeholder=<fmt:message key="placeholder.email" /> required
               minlength="5" maxlength="50">
        <br>
        <input type="submit">
    </form>

    <c:if test="${requestScope.forgotPasswordError}">
        <p><fmt:message key="error.forgot_password" /></p>
    </c:if>

    <jsp:include page="shared/footer.jsp" />
</body>
</html>
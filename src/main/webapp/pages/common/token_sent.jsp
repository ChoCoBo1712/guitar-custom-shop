<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="properties.pagecontent" />

<html>
<head>
    <title><fmt:message key="token_sent.title" /></title>
    <jsp:include page="shared/head.html" />
</head>
<body>
    <jsp:include page="shared/header.jsp" />

    <c:if test="${requestScope.emailConfirmation}">
        <p><fmt:message key="token_sent.email_confirmation" /></p>
    </c:if>

    <c:if test="${requestScope.passwordChange}">
        <p><fmt:message key="token_sent.password_change" /></p>
    </c:if>

    <jsp:include page="shared/footer.jsp" />
</body>
</html>

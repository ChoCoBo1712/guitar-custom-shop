<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="properties.pagecontent" />

<html>
<head>
    <title><fmt:message key="profile.title" /></title>
</head>
<body>
    <jsp:include page="shared/header.jsp" />

    <form action="${pageContext.request.contextPath}/controller?command=update_profile" method="post">
        <input type="text" name="id" value="${sessionScope.user.entityId}" hidden>
        <input type="email" name="email" value="${sessionScope.user.email}"
               placeholder=<fmt:message key="placeholder.email" /> required minlength="5" maxlength="50">
        <br>
        <input type="text" name="login" value="${sessionScope.user.login}"
               placeholder=<fmt:message key="placeholder.login" /> required pattern="[0-9a-zA-Z]{6,20}">
        <br>
        <input type="password" name="password" placeholder=<fmt:message key="placeholder.password" />
               pattern="(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z0-9]{8,32}">
        <br>
        <input type="submit" value=<fmt:message key="admin.edit" />>
    </form>

    <div>${sessionScope.user.role}</div>
    <div>${sessionScope.user.status}</div>

    <c:if test="${sessionScope.user.status != 'CONFIRMED'}">
        <a href="${pageContext.request.contextPath}/controller?command=send_confirmation_link">
            <fmt:message key="profile.resend_link" />
        </a>
    </c:if>

    <c:if test="${requestScope.profileUpdated}">
        <p><fmt:message key="profile.updated" /></p>
    </c:if>

    <c:if test="${requestScope.duplicateEmailError}">
        <p><fmt:message key="error.duplicate_email" /></p>
    </c:if>

    <c:if test="${requestScope.duplicateLoginError}">
        <p><fmt:message key="error.duplicate_login" /></p>
    </c:if>

    <c:if test="${requestScope.validationError}">
        <p><fmt:message key="error.validation_error" /></p>
    </c:if>

    <jsp:include page="shared/footer.jsp" />

</body>
</html>

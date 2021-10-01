<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
    <title><cst:localeTag key="profile.title" /></title>
</head>
<body>
    <jsp:include page="shared/header.jsp" />

    <form action="${pageContext.request.contextPath}/controller?command=update_profile" method="post">
        <input type="text" name="id" value="${sessionScope.userId}" hidden>
        <input type="email" name="email" value="${sessionScope.userEmail}"
               placeholder=<cst:localeTag key="placeholder.email" /> required minlength="5" maxlength="50">
        <br>
        <input type="text" name="login" value="${sessionScope.userLogin}"
               placeholder=<cst:localeTag key="placeholder.login" /> required pattern="[0-9a-zA-Z]{6,20}">
        <br>
        <input type="password" name="password" placeholder=<cst:localeTag key="placeholder.password" />
               pattern="(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z0-9]{8,32}">
        <br>
        <input type="submit" value=<cst:localeTag key="admin.edit" />>
    </form>

    <div>${sessionScope.userRole}</div>

    <c:if test="${sessionScope.userRole == 'NOT_CONFIRMED'}">
        <a href="${pageContext.request.contextPath}/controller?command=send_confirmation_link">
            <cst:localeTag key="profile.resend_link" />
        </a>
    </c:if>

    <c:if test="${requestScope.profileUpdated}">
        <p><cst:localeTag key="profile.updated" /></p>
    </c:if>

    <c:if test="${requestScope.duplicateEmailError}">
        <p><cst:localeTag key="error.duplicate_email" /></p>
    </c:if>

    <c:if test="${requestScope.duplicateLoginError}">
        <p><cst:localeTag key="error.duplicate_login" /></p>
    </c:if>

    <c:if test="${requestScope.validationError}">
        <p><cst:localeTag key="error.validation_error" /></p>
    </c:if>

    <jsp:include page="shared/footer.jsp" />

</body>
</html>

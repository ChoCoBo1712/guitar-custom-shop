<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
    <title><cst:localeTag key="password_change.title" /></title>
    <jsp:include page="shared/head.html" />

    <script src="/static/js/common/set_locale.js"></script>
    <script src="/static/js/common/shared/footer.js"></script>
</head>
<body>

    <jsp:include page="shared/header.jsp" />

    <form action="${pageContext.request.contextPath}/controller?command=password_change" method="post">
        <input type="text" name="token" value="${param.token}" hidden>
        <input type="text" name="email" value="${requestScope.email}" hidden>
        <input type="password" name="password" placeholder=<cst:localeTag key="placeholder.password" /> required
               pattern="(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z0-9]{8,32}">
        <br>
        <input type="submit">
    </form>

    <c:if test="${param.validationError}">
        <p><cst:localeTag key="error.validation_error" /></p>
    </c:if>

    <jsp:include page="shared/footer.jsp" />
</body>
</html>
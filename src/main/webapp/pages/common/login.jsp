<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
    <title><cst:localeTag key="login.title" /></title>
    <jsp:include page="shared/head.html" />

    <script src="/static/js/common/set_locale.js"></script>
    <script src="/static/js/common/shared/footer.js"></script>
</head>
<body>

    <jsp:include page="shared/header.jsp" />

    <form action="${pageContext.request.contextPath}/controller?command=login" method="post">
        <input type="text" name="login" placeholder=<cst:localeTag key="placeholder.login" /> required>
        <br>
        <input type="password" name="password" placeholder=<cst:localeTag key="placeholder.password" /> required>
        <br>
        <input type="submit" value=<cst:localeTag key="login.submit" />>
    </form>

    <a href="${pageContext.request.contextPath}/controller?command=go_to_forgot_password_page">
        <cst:localeTag key="login.forgot_password" />
    </a>

    <c:if test="${param.loginError}">
        <p><cst:localeTag key="error.login" /></p>
    </c:if>

    <jsp:include page="shared/footer.jsp" />
</body>
</html>

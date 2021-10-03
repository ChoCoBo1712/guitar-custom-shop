<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
    <title><cst:localeTag key="forgot_password.title" /></title>
    <jsp:include page="shared/head.html" />

    <script src="/static/js/common/set_locale.js"></script>
    <script src="/static/js/common/shared/footer.js"></script>
</head>
<body>

    <jsp:include page="shared/header.jsp" />

    <form action="${pageContext.request.contextPath}/controller?command=send_password_change_link" method="post">
        <input type="email" name="email" placeholder=<cst:localeTag key="placeholder.email" /> required
               minlength="5" maxlength="50">
        <br>
        <input type="submit">
    </form>

    <c:if test="${param.forgotPasswordError}">
        <p><cst:localeTag key="error.forgot_password" /></p>
    </c:if>

    <jsp:include page="shared/footer.jsp" />
</body>
</html>
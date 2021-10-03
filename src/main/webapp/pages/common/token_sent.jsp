<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
    <title><cst:localeTag key="token_sent.title" /></title>
    <jsp:include page="shared/head.html" />

    <script src="/static/js/common/set_locale.js"></script>
    <script src="/static/js/common/shared/footer.js"></script>
</head>
<body>

    <jsp:include page="shared/header.jsp" />

    <c:if test="${requestScope.emailConfirmation}">
        <p><cst:localeTag key="token_sent.email_confirmation" /></p>
    </c:if>

    <c:if test="${requestScope.passwordChange}">
        <p><cst:localeTag key="token_sent.password_change" /></p>
    </c:if>

    <jsp:include page="shared/footer.jsp" />
</body>
</html>

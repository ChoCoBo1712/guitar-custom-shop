<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
    <title><cst:localeTag key="admin.create_user.title" /></title>
    <jsp:include page="../shared/head.html" />

    <script src="/static/js/common/set_locale.js"></script>
    <script src="/static/js/common/shared/footer.js"></script>
</head>
<body>

    <jsp:include page="../../common/shared/header.jsp" />
    <jsp:include page="../shared/header.jsp" />

    <form action="${pageContext.request.contextPath}/controller?command=create_user" method="post">
        <input type="email" name="email" placeholder=<cst:localeTag key="placeholder.email" /> required
               minlength="5" maxlength="50">
        <br>
        <input type="text" name="login" placeholder=<cst:localeTag key="placeholder.login" /> required
               pattern="[0-9a-zA-Z]{6,20}">
        <br>
        <input type="password" name="password" placeholder=<cst:localeTag key="placeholder.password" /> required
               pattern="(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z0-9]{8,32}">
        <br>
        <select name="role" id="role_select">
            <option value="ADMIN">ADMIN</option>
            <option value="CLIENT" selected>CLIENT</option>
            <option value="MASTER">MASTER</option>
        </select>
        <br>
        <select name="status" id="status_select">
            <option value="NOT_CONFIRMED" selected>NOT_CONFIRMED</option>
            <option value="CONFIRMED">CONFIRMED</option>
        </select>
        <br>
        <input type="submit" value=<cst:localeTag key="admin.create" />>
    </form>

    <c:if test="${requestScope.duplicateEmailError}">
        <p><cst:localeTag key="error.duplicate_email" /></p>
    </c:if>

    <c:if test="${requestScope.duplicateLoginError}">
        <p><cst:localeTag key="error.duplicate_login" /></p>
    </c:if>

    <c:if test="${requestScope.validationError}">
        <p><cst:localeTag key="error.validation_error" /></p>
    </c:if>

    <jsp:include page="../../common/shared/footer.jsp" />

</body>
</html>

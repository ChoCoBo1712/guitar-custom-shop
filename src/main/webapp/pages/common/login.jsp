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

<jsp:include page="shared/header.jsp" />

<body>

    <main role="main" class="container common-main-form">
        <h3 class="row justify-content-center mb-4">
            <cst:localeTag key="login.title" />
        </h3>
        <form action="${pageContext.request.contextPath}/controller?command=login" method="post">
            <div class="form-outline mb-3">
                <label class="form-group" for="li"><cst:localeTag key="admin.users.login" /></label>
                <input id="li" type="text" name="login" placeholder=<cst:localeTag key="placeholder.login" /> required
                       pattern="[0-9a-zA-Z]{6,20}" class="form-control form-control-sm">
            </div>
            <div class="form-outline mb-3">
                <label class="form-group" for="pi"><cst:localeTag key="admin.users.password" /></label>
                <input id="pi" type="password" name="password" placeholder=<cst:localeTag key="placeholder.password" /> required
                       pattern="(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z0-9]{8,32}" class="form-control form-control-sm">
            </div>
            <div class="form-actions text-center">
                <input type="submit" class="btn btn-secondary btn-block" value=<cst:localeTag key="login.submit" />>
            </div>
        </form>

        <div class="text-center">
            <a href="${pageContext.request.contextPath}/controller?command=go_to_forgot_password_page">
                <cst:localeTag key="login.forgot_password" />
            </a>
        </div>

        <c:if test="${param.loginError}">
            <p><cst:localeTag key="error.login" /></p>
        </c:if>
    </main>

</body>

<jsp:include page="shared/footer.jsp" />

</html>

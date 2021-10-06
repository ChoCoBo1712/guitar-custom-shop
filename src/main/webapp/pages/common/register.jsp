<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
    <title><cst:localeTag key="register.title" /></title>
    <jsp:include page="shared/head.html" />

    <script src="/static/js/common/set_locale.js"></script>
    <script src="/static/js/common/shared/footer.js"></script>
</head>

<jsp:include page="shared/header.jsp" />

<body>

    <main role="main" class="container common-main-form">
        <h3 class="row justify-content-center mb-4">
            <cst:localeTag key="register.title" />
        </h3>
        <form action="${pageContext.request.contextPath}/controller?command=register" method="post">
            <div class="form-outline mb-3">
                <label class="form-group" for="ei"><cst:localeTag key="admin.users.email" /></label>
                <input id="ei" type="email" name="email" placeholder=<cst:localeTag key="placeholder.email" /> required
                       minlength="5" maxlength="50" class="form-control form-control-sm">
            </div>
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
                <input type="submit" class="btn btn-secondary btn-block" value=<cst:localeTag key="register.submit" />>
            </div>
        </form>

        <c:if test="${param.duplicateEmailError}">
            <script>
                $.alert({
                    title: '<cst:localeTag key="error.error" />',
                    content: '<cst:localeTag key="error.duplicate_email" />'
                })
            </script>
        </c:if>

        <c:if test="${param.duplicateLoginError}">
            <script>
                $.alert({
                    title: '<cst:localeTag key="error.error" />',
                    content: '<cst:localeTag key="error.duplicate_login" />'
                })
            </script>
        </c:if>

        <c:if test="${param.validationError}">
            <script>
                $.alert({
                    title: '<cst:localeTag key="error.error" />',
                    content: '<cst:localeTag key="error.validation_error" />'
                })
            </script>
        </c:if>
    </main>

</body>

<jsp:include page="shared/footer.jsp" />

</html>

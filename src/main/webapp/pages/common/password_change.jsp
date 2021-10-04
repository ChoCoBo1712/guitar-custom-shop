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

<jsp:include page="shared/header.jsp" />

<body>

    <main role="main" class="container common-main-form">
        <h3 class="row justify-content-center mb-4">
            <cst:localeTag key="password_change.title" />
        </h3>
        <form action="${pageContext.request.contextPath}/controller?command=password_change" method="post">
            <input type="text" name="token" value="${param.token}" hidden>
            <input type="text" name="email" value="${requestScope.email}" hidden>
            <div class="form-outline mb-3">
                <label class="form-group" for="pi"><cst:localeTag key="admin.users.password" /></label>
                <input id="pi" type="password" name="password" placeholder=<cst:localeTag key="placeholder.password" /> required
                       pattern="(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z0-9]{8,32}" class="form-control form-control-sm">
            </div>
            <div class="form-actions text-center">
                <input type="submit" class="btn btn-secondary btn-block" value=<cst:localeTag key="admin.edit" />>
            </div>
        </form>

        <c:if test="${param.validationError}">
            <p><cst:localeTag key="error.validation_error" /></p>
        </c:if>
    </main>

</body>

<jsp:include page="shared/footer.jsp" />

</html>
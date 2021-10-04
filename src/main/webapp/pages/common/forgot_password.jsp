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

<jsp:include page="shared/header.jsp" />

<body>

    <main role="main" class="container common-main-form">
        <h3 class="row justify-content-center mb-4">
            <cst:localeTag key="forgot_password.title" />
        </h3>
        <form action="${pageContext.request.contextPath}/controller?command=send_password_change_link" method="post">
            <div class="form-outline mb-3">
                <label class="form-group" for="ei"><cst:localeTag key="admin.users.email" /></label>
                <input id="ei" type="email" name="email" placeholder=<cst:localeTag key="placeholder.email" /> required
                       minlength="5" maxlength="50" class="form-control form-control-sm">
            </div>
            <div class="form-actions text-center">
                <input type="submit" class="btn btn-secondary btn-block" value=<cst:localeTag key="forgot_password.submit" />>
            </div>
        </form>

        <c:if test="${param.forgotPasswordError}">
            <p><cst:localeTag key="error.forgot_password" /></p>
        </c:if>
    </main>


</body>

<jsp:include page="shared/footer.jsp" />

</html>
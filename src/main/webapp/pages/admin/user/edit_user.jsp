<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
    <title><cst:localeTag key="admin.edit_user.title" /></title>
    <jsp:include page="../shared/head.html" />

    <script src="/static/js/common/shared/footer.js"></script>
    <script src="/static/js/admin/user/edit_user.js"></script>
</head>

<jsp:include page="../../common/shared/header.jsp" />

<body data-role="${requestScope.user.role}"
      data-status="${requestScope.user.status}">

    <jsp:include page="../shared/header.jsp" />

    <main role="main" class="container bg-light admin-main-form">
        <h3 class="row justify-content-center mb-4">
            <cst:localeTag key="admin.edit_user.title" />
        </h3>
        <form action="${pageContext.request.contextPath}/controller?command=update_user" method="post">
            <input type="text" name="id" value="${requestScope.user.entityId}" hidden>
            <div class="form-outline mb-3">
                <label class="form-group" for="ei"><cst:localeTag key="admin.users.email" /></label>
                <input id="ei" type="email" name="email" placeholder=<cst:localeTag key="placeholder.email" /> required value="${requestScope.user.email}"
                       minlength="5" maxlength="50" class="form-control form-control-sm">
            </div>
            <div class="form-outline mb-3">
                <label class="form-group" for="li"><cst:localeTag key="admin.users.login" /></label>
                <input id="li" type="text" name="login" placeholder=<cst:localeTag key="placeholder.login" /> required value="${requestScope.user.login}"
                       pattern="[0-9a-zA-Z]{6,20}" class="form-control form-control-sm">
            </div>
            <div class="form-outline mb-3">
                <label class="form-group" for="pi"><cst:localeTag key="admin.users.password" /></label>
                <input id="pi" type="password" name="password" placeholder=<cst:localeTag key="placeholder.password" /> required
                       pattern="(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z0-9]{8,32}" class="form-control form-control-sm">
            </div>
            <div class="form-outline mb-3">
                <label class="form-group" for="role_select"><cst:localeTag key="admin.users.role" /></label>
                <select name="role" id="role_select" class="form-select form-select-sm">
                    <option value="ADMIN"><cst:localeTag key="admin.users.role.admin" /></option>
                    <option value="MAKER"><cst:localeTag key="admin.users.role.maker" /></option>
                    <option value="CLIENT"><cst:localeTag key="admin.users.role.client" /></option>
                </select>
            </div>
            <div class="form-outline mb-3">
                <label class="form-group" for="status_select"><cst:localeTag key="admin.users.status" /></label>
                <select name="status" id="status_select" class="form-select form-select-sm">
                    <option value="NOT_CONFIRMED"><cst:localeTag key="admin.users.status.not_confirmed" /></option>
                    <option value="CONFIRMED"><cst:localeTag key="admin.users.status.confirmed" /></option>
                </select>
            </div>
            <div class="form-actions text-center">
                <input type="submit" class="btn btn-secondary btn-block" value=<cst:localeTag key="admin.create" />>
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

<jsp:include page="../../common/shared/footer.jsp" />

</html>

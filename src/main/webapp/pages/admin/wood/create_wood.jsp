<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
    <title><cst:localeTag key="admin.create_wood.title" /></title>
    <jsp:include page="../shared/head.html" />

    <script src="/static/js/common/set_locale.js"></script>
    <script src="/static/js/common/shared/footer.js"></script>
</head>

<jsp:include page="../../common/shared/header.jsp" />

<body>

    <jsp:include page="../shared/header.jsp" />

    <main role="main" class="container bg-light admin-main-form">
        <h3 class="row justify-content-center mb-4">
            <cst:localeTag key="admin.create_wood.title" />
        </h3>
        <form action="${pageContext.request.contextPath}/controller?command=create_wood" method="post">
            <div class="form-outline mb-3">
                <label class="form-group" for="ni"><cst:localeTag key="admin.woods.name" /></label>
                <input id="ni" type="text" name="name" placeholder=<cst:localeTag key="placeholder.name" /> required
                       pattern="[a-zA-Z0-9\s\-]{1,30}" class="form-control form-control-sm">
            </div>
            <div class="form-actions text-center">
                <input type="submit" class="btn btn-secondary btn-block" value=<cst:localeTag key="admin.create" />>
            </div>
        </form>

        <c:if test="${param.validationError}">
            <p><cst:localeTag key="error.validation_error" /></p>
        </c:if>
    </main>


</body>

<jsp:include page="../../common/shared/footer.jsp" />

</html>

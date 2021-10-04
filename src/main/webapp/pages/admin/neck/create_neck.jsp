<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
    <title><cst:localeTag key="admin.create_neck.title" /></title>
    <jsp:include page="../shared/head.html" />
    <!-- jQuery Select2 -->
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/select2-bootstrap-theme/0.1.0-beta.10/select2-bootstrap.min.css" />
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    <c:if test="${sessionScope.locale == 'en_US'}">
        <script src="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/js/i18n/en.js"></script>
    </c:if>
    <c:if test="${sessionScope.locale == 'ru_RU'}">
        <script src="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/js/i18n/ru.js"></script>
    </c:if>

    <script src="/static/js/admin/neck/create_neck.js"></script>
    <script src="/static/js/common/shared/footer.js"></script>
</head>

<jsp:include page="../../common/shared/header.jsp" />

<body data-wood="<cst:localeTag key="admin.necks.wood" />"
      data-fretboard-wood="<cst:localeTag key="admin.necks.fretboard_wood" />">

    <jsp:include page="../shared/header.jsp" />

    <main role="main" class="container bg-light admin-main-form">
        <h3 class="row justify-content-center mb-4">
            <cst:localeTag key="admin.create_neck.title" />
        </h3>
        <form action="${pageContext.request.contextPath}/controller?command=create_neck" method="post">
            <div class="form-outline mb-3">
                <label class="form-group" for="ni"><cst:localeTag key="admin.necks.name" /></label>
                <input id="ni" type="text" name="name" placeholder=<cst:localeTag key="placeholder.name" /> required
                       pattern="[a-zA-Z0-9\s\-]{1,30}"  class="form-control form-control-sm">
            </div>
            <div class="form-outline mb-3">
                <label class="form-group" for="woodSelect"><cst:localeTag key="admin.necks.wood" /></label>
                <select name="woodId" id="woodSelect" required  class="form-control form-control-sm"></select>
            </div>
            <div class="form-outline mb-3">
                <label class="form-group" for="fretboardWoodSelect"><cst:localeTag key="admin.necks.fretboard_wood" /></label>
                <select name="fretboardWoodId" id="fretboardWoodSelect" required  class="form-control form-control-sm"></select>
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

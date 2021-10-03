<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
    <title><cst:localeTag key="admin.edit_neck.title" /></title>
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

    <script src="/static/js/util/fetch.js"></script>
    <script src="/static/js/admin/neck/edit_neck.js"></script>
    <script src="/static/js/common/shared/footer.js"></script>
</head>
<body data-wood="<cst:localeTag key="admin.necks.wood" />"
      data-fretboard-wood="<cst:localeTag key="admin.necks.fretboard_wood" />"
      data-wood-id="${requestScope.neck.woodId}"
      data-fretboard-wood-id="${requestScope.neck.fretboardWoodId}">

    <jsp:include page="../../common/shared/header.jsp" />
    <jsp:include page="../shared/header.jsp" />

    <form action="${pageContext.request.contextPath}/controller?command=update_neck" method="post">
        <input type="text" name="id" value="${requestScope.neck.entityId}" hidden>
        <input type="text" name="name" value="${requestScope.neck.name}"
               placeholder=<cst:localeTag key="placeholder.name" /> required pattern="[a-zA-Z0-9\s\-]{1,30}">
        <br>
        <select name="woodId" id="woodSelect" required></select>
        <br>
        <select name="fretboardWoodId" id="fretboardWoodSelect" required></select>
        <br>
        <input type="submit" value=<cst:localeTag key="admin.edit" />>
    </form>

    <c:if test="${param.validationError}">
        <p><cst:localeTag key="error.validation_error" /></p>
    </c:if>

    <jsp:include page="../../common/shared/footer.jsp" />

</body>
</html>

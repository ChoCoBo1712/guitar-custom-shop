<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
    <title><cst:localeTag key="admin.guitars.title" /></title>
    <jsp:include page="../shared/head.html" />
    <!-- jQuery Select2 -->
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    <c:if test="${sessionScope.locale == 'en_US'}">
        <script src="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/js/i18n/en.js"></script>
    </c:if>
    <c:if test="${sessionScope.locale == 'ru_RU'}">
        <script src="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/js/i18n/ru.js"></script>
    </c:if>

    <script src="/static/js/util/fetch.js"></script>
    <script src="/static/js/admin/guitar/guitars.js"></script>
    <script src="/static/js/common/shared/footer.js"></script>
</head>
<body data-search="<cst:localeTag key="admin.search" />"
      data-edit="<cst:localeTag key="admin.edit" />"
      data-delete="<cst:localeTag key="admin.delete" />"
      data-id="<cst:localeTag key="admin.necks.id" />"
      data-name="<cst:localeTag key="admin.necks.name" />"
      data-body="<cst:localeTag key="admin.guitars.body" />"
      data-neck="<cst:localeTag key="admin.guitars.neck" />"
      data-pickup="<cst:localeTag key="admin.guitars.pickup" />"
      data-neck-joint="<cst:localeTag key="admin.guitars.neck_joint" />"
      data-color="<cst:localeTag key="admin.guitars.color" />"
      data-user="<cst:localeTag key="admin.guitars.user" />"
      data-order-status="<cst:localeTag key="admin.guitars.order_status" />"
      data-create="<cst:localeTag key="admin.create" />">

    <jsp:include page="../../common/shared/header.jsp" />
    <jsp:include page="../shared/header.jsp" />

    <table id="guitars_table">
        <thead>
            <th><cst:localeTag key="admin.guitars.id" /></th>
            <th><cst:localeTag key="admin.guitars.name" /></th>
            <th><cst:localeTag key="admin.guitars.picture_path" /></th>
            <th><cst:localeTag key="admin.guitars.body" /></th>
            <th><cst:localeTag key="admin.guitars.neck" /></th>
            <th><cst:localeTag key="admin.guitars.pickup" /></th>
            <th><cst:localeTag key="admin.guitars.neck_joint" /></th>
            <th><cst:localeTag key="admin.guitars.color" /></th>
            <th><cst:localeTag key="admin.guitars.user" /></th>
            <th><cst:localeTag key="admin.guitars.order_status" /></th>
            <th><cst:localeTag key="admin.actions" /></th>
        </thead>
    </table>

    <jsp:include page="../../common/shared/footer.jsp" />

</body>
</html>


<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
    <title><cst:localeTag key="admin.guitars.title" /></title>
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
    <script src="/static/js/admin/guitar/guitars.js"></script>
    <script src="/static/js/common/shared/footer.js"></script>
</head>

<jsp:include page="../../common/shared/header.jsp" />

<body data-search="<cst:localeTag key="admin.search" />"
      data-edit="<cst:localeTag key="admin.edit" />"
      data-delete="<cst:localeTag key="admin.delete" />"
      data-id="<cst:localeTag key="admin.guitars.id" />"
      data-name="<cst:localeTag key="admin.guitars.name" />"
      data-body="<cst:localeTag key="admin.guitars.body" />"
      data-neck="<cst:localeTag key="admin.guitars.neck" />"
      data-pickup="<cst:localeTag key="admin.guitars.pickup" />"
      data-neck-joint="<cst:localeTag key="admin.guitars.neck_joint" />"
      data-bolt-on="<cst:localeTag key="admin.guitars.neck_joint.bolt_on" />"
      data-set-neck="<cst:localeTag key="admin.guitars.neck_joint.set_neck" />"
      data-neck-through="<cst:localeTag key="admin.guitars.neck_joint.neck_through" />"
      data-color="<cst:localeTag key="admin.guitars.color" />"
      data-user="<cst:localeTag key="admin.guitars.user" />"
      data-order-status="<cst:localeTag key="admin.guitars.order_status" />"
      data-ordered="<cst:localeTag key="admin.guitars.order_status.ordered" />"
      data-in-progress="<cst:localeTag key="admin.guitars.order_status.in_progress" />"
      data-completed="<cst:localeTag key="admin.guitars.order_status.completed" />"
      data-create="<cst:localeTag key="admin.create" />"
      data-any="<cst:localeTag key="admin.any" />">

    <jsp:include page="../shared/header.jsp" />

    <main role="main" class="container-fluid bg-light admin-main-table">
        <table id="guitars_table" class="table table-striped table-bordered">
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
    </main>

</body>

<jsp:include page="../../common/shared/footer.jsp" />

</html>


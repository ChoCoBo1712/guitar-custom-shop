<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
    <title><cst:localeTag key="admin.woods.title" /></title>
    <jsp:include page="../shared/head.html" />

    <script src="/static/js/admin/wood/woods.js"></script>
    <script src="/static/js/common/shared/footer.js"></script>
</head>

<jsp:include page="../../common/shared/header.jsp" />

<body data-search="<cst:localeTag key="admin.search" />"
      data-edit="<cst:localeTag key="admin.edit" />"
      data-delete="<cst:localeTag key="admin.delete" />"
      data-id="<cst:localeTag key="admin.woods.id" />"
      data-name="<cst:localeTag key="admin.woods.name" />"
      data-create="<cst:localeTag key="admin.create" />">

    <jsp:include page="../shared/header.jsp" />

    <main role="main" class="container-fluid bg-light admin-main-table">
        <table id="woods_table" class="table table-striped table-bordered">
            <thead>
            <th><cst:localeTag key="admin.woods.id" /></th>
            <th><cst:localeTag key="admin.woods.name" /></th>
            <th><cst:localeTag key="admin.actions" /></th>
            </thead>
        </table>
    </main>

</body>

<jsp:include page="../../common/shared/footer.jsp" />

</html>


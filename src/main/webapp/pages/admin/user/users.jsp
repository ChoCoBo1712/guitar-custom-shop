<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
    <title><cst:localeTag key="admin.users.title" /></title>
    <jsp:include page="../shared/head.html" />

    <script src="/static/js/admin/user/users.js"></script>
    <script src="/static/js/common/shared/footer.js"></script>
</head>

<jsp:include page="../../common/shared/header.jsp" />

<body data-search="<cst:localeTag key="admin.search" />"
      data-edit="<cst:localeTag key="admin.edit" />"
      data-delete="<cst:localeTag key="admin.delete" />"
      data-id="<cst:localeTag key="admin.users.id" />"
      data-email="<cst:localeTag key="admin.users.email" />"
      data-login="<cst:localeTag key="admin.users.login" />"
      data-role="<cst:localeTag key="admin.users.role" />"
      data-admin="<cst:localeTag key="admin.users.role.admin" />"
      data-maker="<cst:localeTag key="admin.users.role.maker" />"
      data-client="<cst:localeTag key="admin.users.role.client" />"
      data-status="<cst:localeTag key="admin.users.status" />"
      data-confirmed="<cst:localeTag key="admin.users.status.confirmed" />"
      data-not-confirmed="<cst:localeTag key="admin.users.status.not_confirmed" />"
      data-create="<cst:localeTag key="admin.create" />"
      data-any="<cst:localeTag key="admin.any" />">

    <jsp:include page="../shared/header.jsp" />

    <main role="main" class="container-fluid bg-light admin-main-table">
        <table id="users_table" class="table table-striped table-bordered">
            <thead>
            <th><cst:localeTag key="admin.users.id" /></th>
            <th><cst:localeTag key="admin.users.email" /></th>
            <th><cst:localeTag key="admin.users.login" /></th>
            <th><cst:localeTag key="admin.users.role" /></th>
            <th><cst:localeTag key="admin.users.status" /></th>
            <th><cst:localeTag key="admin.actions" /></th>
            </thead>
        </table>
    </main>

</body>

<jsp:include page="../../common/shared/footer.jsp" />

</html>

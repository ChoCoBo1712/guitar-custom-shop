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
<body data-search="<cst:localeTag key="admin.search" />"
      data-edit="<cst:localeTag key="admin.edit" />"
      data-delete="<cst:localeTag key="admin.delete" />"
      data-id="<cst:localeTag key="admin.users.id" />"
      data-email="<cst:localeTag key="admin.users.email" />"
      data-login="<cst:localeTag key="admin.users.login" />"
      data-role="<cst:localeTag key="admin.users.role" />"
      data-status="<cst:localeTag key="admin.users.status" />"
      data-create="<cst:localeTag key="admin.create" />">

    <jsp:include page="../../common/shared/header.jsp" />
    <jsp:include page="../shared/header.jsp" />

    <table id="users_table">
        <thead>
            <th><cst:localeTag key="admin.users.id" /></th>
            <th><cst:localeTag key="admin.users.email" /></th>
            <th><cst:localeTag key="admin.users.login" /></th>
            <th><cst:localeTag key="admin.users.role" /></th>
            <th><cst:localeTag key="admin.users.status" /></th>
            <th><cst:localeTag key="admin.actions" /></th>
        </thead>
    </table>

    <jsp:include page="../../common/shared/footer.jsp" />

</body>
</html>

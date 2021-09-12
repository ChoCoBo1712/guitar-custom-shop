<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="properties.pagecontent" />

<html>
<head>
    <title><fmt:message key="admin.users.title" /></title>
    <jsp:include page="shared/head.html" />
</head>
<body>
    <jsp:include page="shared/header.jsp" />

    <a href="${pageContext.request.contextPath}/controller?command=go_to_create_user_page"><fmt:message key="admin.create" /></a>

    <table id="users_table">
        <thead>
            <th><fmt:message key="admin.users.id" /></th>
            <th><fmt:message key="admin.users.email" /></th>
            <th><fmt:message key="admin.users.login" /></th>
            <th><fmt:message key="admin.users.role" /></th>
            <th><fmt:message key="admin.users.status" /></th>
            <th><fmt:message key="admin.users.actions" /></th>
        </thead>
    </table>

    <jsp:include page="shared/footer.jsp" />

    <script>
        $(document).ready( function () {
            $('#users_table').DataTable( {
                language: {
                    <c:if test="${sessionScope.locale == 'en_US'}">
                    url: 'https://cdn.datatables.net/plug-ins/1.11.1/i18n/en-gb.json'
                    </c:if>
                    <c:if test="${sessionScope.locale == 'ru_RU'}">
                    url: 'https://cdn.datatables.net/plug-ins/1.11.1/i18n/ru.json'
                    </c:if>
                },
                processing: true,
                serverSide: true,
                ajax: {
                    url: '/controller?command=get_users',
                    // data: function (d) {
                    //     d.requestType = "jquery_datatable";
                    //     d.filterCriteria = $('#searchCriteria').val();
                    // }
                },
                columns: [
                    { data: 'entityId'},
                    { data: 'email'},
                    { data: 'login'},
                    { data: 'role'},
                    { data: 'status'},
                    {
                        data: null,
                        render: function (row) {
                            return '<a href="/controller?command=go_to_edit_user_page&id=' + row.entityId + '">'
                                + '<fmt:message key="admin.edit" /></a>'
                                + '<br>'
                                + '<a href="/controller?command=delete_user&id=' + row.entityId + '">'
                                + '<fmt:message key="admin.delete" /></a>'
                        }
                    },
                ],
            });
        } );
    </script>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="properties.pagecontent" />

<html>
<head>
    <title><fmt:message key="admin.users.title" /></title>
    <jsp:include page="../shared/head.html" />
</head>
<body>
    <jsp:include page="../shared/header.jsp" />

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

    <jsp:include page="../shared/footer.jsp" />

    <script>
        $(document).ready( function () {
            let table = $('#users_table').DataTable( {
                language: {
                    <c:if test="${sessionScope.locale == 'en_US'}">
                    url: 'https://cdn.datatables.net/plug-ins/1.11.1/i18n/en-gb.json'
                    </c:if>
                    <c:if test="${sessionScope.locale == 'ru_RU'}">
                    url: 'https://cdn.datatables.net/plug-ins/1.11.1/i18n/ru.json'
                    </c:if>
                },
                dom: '<"toolbar">tipr',
                processing: true,
                serverSide: true,
                ordering: false,
                ajax: {
                    url: '/controller?command=get_users',
                    data: function (data) {
                        data.filterCriteria = $('#searchCriteria').val();
                    }
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
                initComplete: function () {
                    dataTableInitComplete(table);
                }
            });
        } );

        function dataTableInitComplete(table) {
            $("div.toolbar").html(`
                <div class="input-group mb-3">
                <button id="createButton" type="button" class="btn btn-secondary">
                    <fmt:message key="admin.create" />
                </button>
                <select id="searchCriteria" class="form-select">
                    <option value="ID"><fmt:message key="admin.users.id" /></option>
                    <option value="EMAIL"><fmt:message key="admin.users.email" /></option>
                    <option value="LOGIN"><fmt:message key="admin.users.login" /></option>
                    <option value="ROLE"><fmt:message key="admin.users.role" /></option>
                    <option value="STATUS"><fmt:message key="admin.users.status" /></option>
                </select>
                <input id="searchInput" maxlength="50" type="text" class="form-control w-50"
                 placeholder=<fmt:message key="admin.search" />>
                 <select id="searchSelect"></select>
                </div>
            `);

            let searchInput = $('#searchInput');
            let searchCriteria = $('#searchCriteria');
            let searchSelect = $('#searchSelect');

            searchSelect.hide();

            $('#createButton').click(function () {
                window.location.href = "${pageContext.request.contextPath}/controller?command=go_to_create_user_page";
            });

            searchInput.keyup(function () {
                table.search(searchInput.val().trim()).draw();
            });

            searchCriteria.change(function () {
                if (searchCriteria.val() === 'ROLE') {
                    searchInput.hide();
                    searchSelect.show();
                    searchSelect.html("")
                        .append($("<option></option>").attr("value", "").text("None"))
                        .append($("<option></option>").attr("value", "ADMIN").text("ADMIN"))
                        .append($("<option></option>").attr("value", "CLIENT").text("CLIENT"))
                        .append($("<option></option>").attr("value", "MASTER").text("MASTER"))
                    searchSelect.change();
                } else if (searchCriteria.val() === 'STATUS') {
                    searchInput.hide();
                    searchSelect.show();
                    searchSelect.html("")
                        .append($("<option></option>").attr("value", "").text("None"))
                        .append($("<option></option>").attr("value", "NOT_CONFIRMED").text("NOT_CONFIRMED"))
                        .append($("<option></option>").attr("value", "CONFIRMED").text("CONFIRMED"))
                    searchSelect.change();
                } else {
                    searchInput.val("");
                    searchInput.show();
                    searchSelect.hide();
                    table.search(searchInput.val()).draw();
                }
            });

            searchSelect.change(function () {
                table.search(searchSelect.val()).draw();
            });
        }
    </script>
</body>
</html>

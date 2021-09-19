<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="properties.pagecontent" />

<html>
<head>
    <title><fmt:message key="admin.pickups.title" /></title>
    <jsp:include page="../shared/head.html" />
</head>
<body>
    <jsp:include page="../shared/header.jsp" />

    <table id="pickups_table">
        <thead>
        <th><fmt:message key="admin.pickups.id" /></th>
        <th><fmt:message key="admin.pickups.name" /></th>
        <th><fmt:message key="admin.actions" /></th>
        </thead>
    </table>

    <jsp:include page="../shared/footer.jsp" />

    <script>
        $(document).ready( function () {
            let table = $('#pickups_table').DataTable( {
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
                    url: '/controller?command=get_pickups',
                    data: function (data) {
                        data.filterCriteria = $('#searchCriteria').val();
                        data.requestType = 'DATATABLE';
                    }
                },
                columns: [
                    { data: 'entityId'},
                    { data: 'name'},
                    {
                        data: null,
                        render: function (row) {
                            return '<a href="/controller?command=go_to_edit_pickup_page&id=' + row.entityId + '">'
                                + '<fmt:message key="admin.edit" /></a>'
                                + '<br>'
                                + '<a href="/controller?command=delete_pickup&id=' + row.entityId + '">'
                                + '<fmt:message key="admin.delete" /></a>'
                        }
                    },
                ],
                initComplete: function () {
                    onDataTableInitComplete(table);
                }
            });
        } );

        function onDataTableInitComplete(table) {
            $("div.toolbar").html(`
                    <div class="input-group mb-3">
                    <button id="createButton" type="button" class="btn btn-secondary">
                        <fmt:message key="admin.create" />
                    </button>
                    <select id="searchCriteria" class="form-select">
                        <option value="ID"><fmt:message key="admin.pickups.id" /></option>
                        <option value="NAME"><fmt:message key="admin.pickups.name" /></option>
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
                window.location.href = "${pageContext.request.contextPath}/controller?command=go_to_create_pickup_page";
            });

            searchInput.keyup(function () {
                table.search(searchInput.val().trim()).draw();
            });

            searchCriteria.change(function () {
                searchInput.val("");
                searchInput.show();
                searchSelect.hide();
                table.search(searchInput.val()).draw();
            });

            searchSelect.change(function () {
                table.search(searchSelect.val()).draw();
            });
        }
    </script>
</body>
</html>


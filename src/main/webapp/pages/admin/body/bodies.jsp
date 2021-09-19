<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="properties.pagecontent" />

<html>
<head>
    <title><fmt:message key="admin.bodies.title" /></title>
    <jsp:include page="../shared/head.html" />
</head>
<body>
    <jsp:include page="../shared/header.jsp" />

    <table id="bodies_table">
        <thead>
        <th><fmt:message key="admin.bodies.id" /></th>
        <th><fmt:message key="admin.bodies.name" /></th>
        <th><fmt:message key="admin.bodies.wood" /></th>
        <th><fmt:message key="admin.users.actions" /></th>
        </thead>
    </table>

    <jsp:include page="../shared/footer.jsp" />

    <script>
        $(document).ready( function () {
            sessionStorage.removeItem('cachedWoods');
            let table = $('#bodies_table').DataTable( {
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
                    url: '/controller?command=get_bodies',
                    data: function (data) {
                        data.filterCriteria = $('#searchCriteria').val();
                        data.requestType = 'DATATABLE';
                    }
                },
                drawCallback: function () { onDataLoaded(table); },
                columns: [
                    { data: 'entityId'},
                    { data: 'name'},
                    {
                        data: null,
                        render: function (row) {
                            return '<a href="/controller?command=go_to_edit_wood_page&id=' + row.woodId + '"></a>'
                        }
                    },
                    {
                        data: null,
                        render: function (row) {
                            return '<a href="/controller?command=go_to_edit_body_page&id=' + row.entityId + '">'
                                + '<fmt:message key="admin.edit" /></a>'
                                + '<br>'
                                + '<a href="/controller?command=delete_body&id=' + row.entityId + '">'
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
                        <option value="ID"><fmt:message key="admin.bodies.id" /></option>
                        <option value="NAME"><fmt:message key="admin.bodies.name" /></option>
                        <option value="WOOD"><fmt:message key="admin.bodies.wood" /></option>
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
                window.location.href = "${pageContext.request.contextPath}/controller?command=go_to_create_body_page";
            });

            searchInput.keyup(function () {
                table.search(searchInput.val().trim()).draw();
            });

            searchCriteria.change(function () {
                if (searchCriteria.val() === 'WOOD') {
                    searchInput.hide();
                    searchSelect.show();
                    searchSelect.html("")
                        .append($("<option></option>").attr("value", "").text("None"))
                        .append($("<option></option>").attr("value", "ADMIN").text("ADMIN"))
                        .append($("<option></option>").attr("value", "CLIENT").text("CLIENT"))
                        .append($("<option></option>").attr("value", "MASTER").text("MASTER"))
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

        function onDataLoaded(table) {
            table.rows().data().each(function (value, index) {
                fetchWood(value.woodId, function (entity) {
                    let woodName = entity.name;
                    let cell = table.cell(index, 2).node();
                    $(cell).find('a').text(woodName);
                });
            })
        }

        function fetchWood(id, callback) {
            let cachedWoods = JSON.parse(sessionStorage.getItem('cachedWoods'));

            if (cachedWoods === null) {
                cachedWoods = {};
                sessionStorage.setItem('cachedWoods', '{}');
            }

            if (id in cachedWoods) {
                callback(cachedWoods[id]);
            } else {
                $.ajax({
                    method: 'GET',
                    url: '/controller?command=get_woods',
                    data: {
                        id: id,
                        requestType: 'FETCH'
                    },
                    success: function (response) {
                        let data = JSON.parse(response);

                        if (data) {
                            cachedWoods[id] = data.entity;
                            sessionStorage.setItem('cachedWoods', JSON.stringify(cachedWoods));
                            callback(data.entity);
                        }
                    }
                });
            }
        }
    </script>
</body>
</html>


<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
    <title><cst:localeTag key="admin.necks.title" /></title>
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
</head>
<body>
    <jsp:include page="../../common/shared/header.jsp" />
    <jsp:include page="../shared/header.jsp" />

    <table id="necks_table">
        <thead>
        <th><cst:localeTag key="admin.necks.id" /></th>
        <th><cst:localeTag key="admin.necks.name" /></th>
        <th><cst:localeTag key="admin.necks.wood" /></th>
        <th><cst:localeTag key="admin.necks.fretboard_wood" /></th>
        <th><cst:localeTag key="admin.actions" /></th>
        </thead>
    </table>

    <jsp:include page="../../common/shared/footer.jsp" />

    <script>
        $(document).ready( function () {
            sessionStorage.removeItem('cachedWoods');

            let table = $('#necks_table').DataTable( {
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
                    url: '/controller?command=get_necks',
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
                            return '<a href="/controller?command=go_to_edit_wood_page&id=' + row.woodId + '"></a>'
                        }
                    },
                    {
                        data: null,
                        render: function (row) {
                            return '<a href="/controller?command=go_to_edit_neck_page&id=' + row.entityId + '">'
                                + '<cst:localeTag key="admin.edit" /></a>'
                                + '<br>'
                                + '<a href="/controller?command=delete_neck&id=' + row.entityId + '">'
                                + '<cst:localeTag key="admin.delete" /></a>'
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
                            <cst:localeTag key="admin.create" />
                        </button>
                        <select id="searchCriteria" class="form-select">
                            <option value="ID"><cst:localeTag key="admin.necks.id" /></option>
                            <option value="NAME"><cst:localeTag key="admin.necks.name" /></option>
                            <option value="WOOD_ID"><cst:localeTag key="admin.necks.wood" /></option>
                            <option value="FRETBOARD_WOOD_ID"><cst:localeTag key="admin.necks.fretboard_wood" /></option>
                        </select>
                        <input id="searchInput" maxlength="50" type="text" class="form-control w-50"
                         placeholder=<cst:localeTag key="admin.search" />>
                         <select id="searchSelect"></select>
                        </div>
                    `);

            let searchInput = $('#searchInput');
            let searchCriteria = $('#searchCriteria');
            let searchSelect = $('#searchSelect');

            searchSelect.hide();

            $('#createButton').click(function () {
                window.location.href = "${pageContext.request.contextPath}/controller?command=go_to_create_neck_page";
            });

            searchInput.keyup(function () {
                table.search(searchInput.val().trim()).draw();
            });

            searchCriteria.change(function () {
                searchInput.val('');
                searchSelect.html('');
                if (searchCriteria.val() === 'WOOD_ID') {
                    searchInput.hide();
                    searchSelect.show();
                    searchSelect.select2({
                        language: '${sessionScope.locale}'.substring(0, 2),
                        placeholder: '<cst:localeTag key="admin.necks.wood" />',
                        // theme: 'bootstrap',
                        width: '10%',
                        maximumInputLength: 30,
                        ajax: {
                            delay: 250,
                            url: '/controller?command=get_woods',
                            data: function (params) {
                                return {
                                    term: params.term || '',
                                    page: params.page || 1,
                                    pageSize: 10,
                                    requestType: 'SELECT'
                                }
                            },
                            processResults: function (data, params) {
                                data = JSON.parse(data);
                                let mappedData = $.map(data.results, function (item) {
                                    item.id = item.entityId;
                                    item.text = item.name;
                                    return item;
                                });
                                params.page = params.page || 1;

                                return {
                                    results: mappedData,
                                    pagination: {
                                        more: data.paginationMore
                                    }
                                }
                            }
                        }
                    });
                    table.search(searchInput.val()).draw();
                } else if (searchCriteria.val() === 'FRETBOARD_WOOD_ID') {
                    searchInput.hide();
                    searchSelect.show();
                    searchSelect.select2({
                        language: '${sessionScope.locale}'.substring(0, 2),
                        placeholder: '<cst:localeTag key="admin.necks.fretboard_wood" />',
                        // theme: 'bootstrap',
                        width: '10%',
                        maximumInputLength: 30,
                        ajax: {
                            delay: 250,
                            url: '/controller?command=get_woods',
                            data: function (params) {
                                return {
                                    term: params.term || '',
                                    page: params.page || 1,
                                    pageSize: 10,
                                    requestType: 'SELECT'
                                }
                            },
                            processResults: function (data, params) {
                                data = JSON.parse(data);
                                let mappedData = $.map(data.results, function (item) {
                                    item.id = item.entityId;
                                    item.text = item.name;
                                    return item;
                                });
                                params.page = params.page || 1;

                                return {
                                    results: mappedData,
                                    pagination: {
                                        more: data.paginationMore
                                    }
                                }
                            }
                        }
                    });
                    table.search(searchInput.val()).draw();
                } else {
                    table.search(searchInput.val()).draw();
                    searchInput.show();
                    searchSelect.hide();
                    searchSelect.select2('destroy');
                }
            });

            searchSelect.change(function () {
                table.search(searchSelect.val()).draw();
            });

            searchSelect.on('select2:select', function () {
                let searchValue = $(this).val();
                table.search(searchValue).draw();
            });
        }

        function onDataLoaded(table) {
            table.rows().data().each(function (value, index) {
                fetchWood(value.woodId, function (entity) {
                    let woodName = entity.name;
                    let cell = table.cell(index, 2).node();
                    $(cell).find('a').text(woodName);
                });
            });

            table.rows().data().each(function (value, index) {
                fetchWood(value.fretboardWoodId, function (entity) {
                    let woodName = entity.name;
                    let cell = table.cell(index, 3).node();
                    $(cell).find('a').text(woodName);
                });
            });
        }
    </script>
</body>
</html>


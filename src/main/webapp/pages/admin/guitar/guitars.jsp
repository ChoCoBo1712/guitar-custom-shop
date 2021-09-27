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
</head>
<body>
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
        <th><cst:localeTag key="admin.actions" /></th>
        </thead>
    </table>

    <jsp:include page="../../common/shared/footer.jsp" />

    <script>
        $(document).ready( function () {
            sessionStorage.removeItem('cachedBodies');
            sessionStorage.removeItem('cachedNecks');
            sessionStorage.removeItem('cachedPickups');
            sessionStorage.removeItem('cachedUsers');

            let table = $('#guitars_table').DataTable( {
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
                    url: '/controller?command=get_guitars',
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
                        data: 'picturePath',
                        render: function (data) {
                            return '<img src="'+ data +'" alt="Guitar picture">'
                        }
                    },
                    {
                        data: null,
                        render: function (row) {
                            return '<a href="/controller?command=go_to_edit_body_page&id=' + row.bodyId + '"></a>'
                        }
                    },
                    {
                        data: null,
                        render: function (row) {
                            return '<a href="/controller?command=go_to_edit_neck_page&id=' + row.neckId + '"></a>'
                        }
                    },
                    {
                        data: null,
                        render: function (row) {
                            return '<a href="/controller?command=go_to_edit_pickup_page&id=' + row.pickupId + '"></a>'
                        }
                    },
                    { data: 'neckJoint'},
                    { data: 'color'},
                    {
                        data: null,
                        render: function (row) {
                            return '<a href="/controller?command=go_to_edit_user_page&id=' + row.userId + '"></a>'
                        }
                    },
                    {
                        data: null,
                        render: function (row) {
                            return '<a href="/controller?command=go_to_edit_guitar_page&id=' + row.entityId + '">'
                                + '<cst:localeTag key="admin.edit" /></a>'
                                + '<br>'
                                + '<a href="/controller?command=delete_guitar&id=' + row.entityId + '">'
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
                                <option value="ID"><cst:localeTag key="admin.guitars.id" /></option>
                                <option value="NAME"><cst:localeTag key="admin.guitars.name" /></option>
                                <option value="BODY_ID"><cst:localeTag key="admin.guitars.body" /></option>
                                <option value="NECK_ID"><cst:localeTag key="admin.guitars.neck" /></option>
                                <option value="PICKUP_ID"><cst:localeTag key="admin.guitars.pickup" /></option>
                                <option value="NECK_JOINT"><cst:localeTag key="admin.guitars.neck_joint" /></option>
                                <option value="COLOR"><cst:localeTag key="admin.guitars.color" /></option>
                                <option value="USER_ID"><cst:localeTag key="admin.guitars.user" /></option>
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
                window.location.href = "${pageContext.request.contextPath}/controller?command=go_to_create_guitar_page";
            });

            searchInput.keyup(function () {
                table.search(searchInput.val().trim()).draw();
            });

            searchCriteria.change(function () {
                searchInput.val('');
                searchSelect.html('');
                if (searchCriteria.val() === 'BODY_ID') {
                    searchInput.hide();
                    searchSelect.show();
                    searchSelect.select2({
                        language: '${sessionScope.locale}'.substring(0, 2),
                        placeholder: '<cst:localeTag key="admin.guitars.body" />',
                        // theme: 'bootstrap',
                        width: '10%',
                        maximumInputLength: 30,
                        ajax: {
                            delay: 250,
                            url: '/controller?command=get_bodies',
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
                } else if (searchCriteria.val() === 'NECK_ID') {
                    searchInput.hide();
                    searchSelect.show();
                    searchSelect.select2({
                        language: '${sessionScope.locale}'.substring(0, 2),
                        placeholder: '<cst:localeTag key="admin.guitars.neck" />',
                        // theme: 'bootstrap',
                        width: '10%',
                        maximumInputLength: 30,
                        ajax: {
                            delay: 250,
                            url: '/controller?command=get_necks',
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
                } else if (searchCriteria.val() === 'PICKUP_ID') {
                    searchInput.hide();
                    searchSelect.show();
                    searchSelect.select2({
                        language: '${sessionScope.locale}'.substring(0, 2),
                        placeholder: '<cst:localeTag key="admin.guitars.pickup" />',
                        // theme: 'bootstrap',
                        width: '10%',
                        maximumInputLength: 30,
                        ajax: {
                            delay: 250,
                            url: '/controller?command=get_pickups',
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
                } else if (searchCriteria.val() === 'USER_ID') {
                    searchInput.hide();
                    searchSelect.show();
                    searchSelect.select2({
                        language: '${sessionScope.locale}'.substring(0, 2),
                        placeholder: '<cst:localeTag key="admin.guitars.user" />',
                        // theme: 'bootstrap',
                        width: '10%',
                        maximumInputLength: 30,
                        ajax: {
                            delay: 250,
                            url: '/controller?command=get_users',
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
                                    item.text = item.login;
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
                } else if (searchCriteria.val() === 'NECK_JOINT') {
                    table.search(searchInput.val()).draw();
                    searchInput.hide();
                    searchSelect.show();
                    searchSelect.html('')
                        .append($("<option></option>").attr("value", "").text("None"))
                        .append($("<option></option>").attr("value", "BOLT_ON").text("BOLT_ON"))
                        .append($("<option></option>").attr("value", "SET_NECK").text("SET_NECK"))
                        .append($("<option></option>").attr("value", "NECK_THROUGH").text("NECK_THROUGH"))
                    searchSelect.select2('destroy');
                }
                else {
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
                fetchBody(value.bodyId, function (entity) {
                    let bodyName = entity.name;
                    let cell = table.cell(index, 3).node();
                    $(cell).find('a').text(bodyName);
                });
            });

            table.rows().data().each(function (value, index) {
                fetchNeck(value.neckId, function (entity) {
                    let neckName = entity.name;
                    let cell = table.cell(index, 4).node();
                    $(cell).find('a').text(neckName);
                });
            });

            table.rows().data().each(function (value, index) {
                fetchPickup(value.pickupId, function (entity) {
                    let pickupName = entity.name;
                    let cell = table.cell(index, 5).node();
                    $(cell).find('a').text(pickupName);
                });
            });

            table.rows().data().each(function (value, index) {
                fetchUser(value.userId, function (entity) {
                    let userName = entity.login;
                    let cell = table.cell(index, 8).node();
                    $(cell).find('a').text(userName);
                });
            });
        }

        function fetchBody(id, callback) {
            let cachedBodies = JSON.parse(sessionStorage.getItem('cachedBodies'));

            if (cachedBodies === null) {
                cachedBodies = {};
                sessionStorage.setItem('cachedBodies', '{}');
            }

            if (id in cachedBodies) {
                callback(cachedBodies[id]);
            } else {
                $.ajax({
                    method: 'GET',
                    url: '/controller?command=get_bodies',
                    data: {
                        id: id,
                        requestType: 'FETCH'
                    },
                    success: function (response) {
                        let data = JSON.parse(response);

                        if (data) {
                            cachedBodies[id] = data.entity;
                            sessionStorage.setItem('cachedBodies', JSON.stringify(cachedBodies));
                            callback(data.entity);
                        }
                    }
                });
            }
        }

        function fetchNeck(id, callback) {
            let cachedNecks = JSON.parse(sessionStorage.getItem('cachedNecks'));

            if (cachedNecks === null) {
                cachedNecks = {};
                sessionStorage.setItem('cachedNecks', '{}');
            }

            if (id in cachedNecks) {
                callback(cachedNecks[id]);
            } else {
                $.ajax({
                    method: 'GET',
                    url: '/controller?command=get_necks',
                    data: {
                        id: id,
                        requestType: 'FETCH'
                    },
                    success: function (response) {
                        let data = JSON.parse(response);

                        if (data) {
                            cachedNecks[id] = data.entity;
                            sessionStorage.setItem('cachedNecks', JSON.stringify(cachedNecks));
                            callback(data.entity);
                        }
                    }
                });
            }
        }

        function fetchPickup(id, callback) {
            let cachedPickups = JSON.parse(sessionStorage.getItem('cachedPickups'));

            if (cachedPickups === null) {
                cachedPickups = {};
                sessionStorage.setItem('cachedPickups', '{}');
            }

            if (id in cachedPickups) {
                callback(cachedPickups[id]);
            } else {
                $.ajax({
                    method: 'GET',
                    url: '/controller?command=get_pickups',
                    data: {
                        id: id,
                        requestType: 'FETCH'
                    },
                    success: function (response) {
                        let data = JSON.parse(response);

                        if (data) {
                            cachedPickups[id] = data.entity;
                            sessionStorage.setItem('cachedPickups', JSON.stringify(cachedPickups));
                            callback(data.entity);
                        }
                    }
                });
            }
        }

        function fetchUser(id, callback) {
            let cachedUsers = JSON.parse(sessionStorage.getItem('cachedUsers'));

            if (cachedUsers === null) {
                cachedUsers = {};
                sessionStorage.setItem('cachedUsers', '{}');
            }

            if (id in cachedUsers) {
                callback(cachedUsers[id]);
            } else {
                $.ajax({
                    method: 'GET',
                    url: '/controller?command=get_users',
                    data: {
                        id: id,
                        requestType: 'FETCH'
                    },
                    success: function (response) {
                        let data = JSON.parse(response);

                        if (data) {
                            cachedUsers[id] = data.entity;
                            sessionStorage.setItem('cachedUsers', JSON.stringify(cachedUsers));
                            callback(data.entity);
                        }
                    }
                });
            }
        }
    </script>
</body>
</html>


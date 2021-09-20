<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="properties.pagecontent" />

<html>
<head>
    <title><fmt:message key="admin.edit_neck.title" /></title>
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
    <jsp:include page="../shared/header.jsp" />

    <form action="${pageContext.request.contextPath}/controller?command=update_neck" method="post">
        <input type="text" name="id" value="${requestScope.neck.entityId}" hidden>
        <input type="text" name="name" value="${requestScope.neck.name}"
               placeholder=<fmt:message key="placeholder.name" /> required pattern="[a-zA-Z0-9\s\-]{1,30}">
        <br>
        <select name="tuner" id="tuner_select">
            <option value="NON_LOCKING">NON_LOCKING</option>
            <option value="LOCKING" selected>LOCKING</option>
        </select>
        <br>
        <select name="woodId" id="woodSelect" required></select>
        <br>
        <select name="fretboardWoodId" id="fretboardWoodSelect" required></select>
        <br>
        <input type="submit" value=<fmt:message key="admin.edit" />>
    </form>

    <c:if test="${requestScope.invalidNamePatternError}">
        <p><fmt:message key="error.invalid_name" /></p>
    </c:if>

    <c:if test="${requestScope.duplicateNameError}">
        <p><fmt:message key="error.duplicate_name" /></p>
    </c:if>

    <jsp:include page="../shared/footer.jsp" />

    <script>
        $(document).ready( function () {
            sessionStorage.removeItem('cachedWoods');
            let woodId = ${requestScope.neck.woodId};
            let fretboardWoodId = ${requestScope.neck.fretboardWoodId};
            let woodSelect = $('#woodSelect');
            let fretboardWoodSelect = $('#fretboardWoodSelect');

            woodSelect.select2({
                language: '${sessionScope.locale}'.substring(0, 2),
                placeholder: '<fmt:message key="admin.necks.wood" />',
                // theme: 'bootstrap',
                width: '10%',
                maximumInputLength: 50,
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

            fretboardWoodSelect.select2({
                language: '${sessionScope.locale}'.substring(0, 2),
                placeholder: '<fmt:message key="admin.necks.fretboard_wood" />',
                // theme: 'bootstrap',
                width: '10%',
                maximumInputLength: 50,
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

            if (!isNaN(Number.parseInt(woodId))) {
                fetchWood(woodId, function (entity) {
                    let option = new Option(entity.name, entity.entityId);
                    select.append(option).trigger('change');
                });
            }

            if (!isNaN(Number.parseInt(fretboardWoodId))) {
                fetchWood(fretboardWoodId, function (entity) {
                    let option = new Option(entity.name, entity.entityId);
                    select.append(option).trigger('change');
                });
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
        });
    </script>

</body>
</html>

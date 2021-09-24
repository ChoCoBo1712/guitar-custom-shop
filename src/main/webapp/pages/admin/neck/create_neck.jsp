<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="properties.pagecontent" />

<html>
<head>
    <title><fmt:message key="admin.create_neck.title" /></title>
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

    <form action="${pageContext.request.contextPath}/controller?command=create_neck" method="post">
        <input type="text" name="name" placeholder=<fmt:message key="placeholder.name" /> required
               pattern="[a-zA-Z0-9\s\-]{1,30}">
        <br>
        <select name="tuner" id="tuner_select">
            <option selected value="NON_LOCKING">NON_LOCKING</option>
            <option value="LOCKING">LOCKING</option>
        </select>
        <br>
        <select name="woodId" id="woodSelect" required></select>
        <br>
        <select name="fretboardWoodId" id="fretboardWoodSelect" required></select>
        <br>
        <input type="submit" value=<fmt:message key="admin.create" />>
    </form>

    <c:if test="${requestScope.validationError}">
        <p><fmt:message key="error.validation_error" /></p>
    </c:if>

    <jsp:include page="../shared/footer.jsp" />

    <script>
        $(document).ready( function () {
            sessionStorage.removeItem('cachedWoods');

            $('#woodSelect').select2({
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

            $('#fretboardWoodSelect').select2({
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
        });
    </script>

</body>
</html>

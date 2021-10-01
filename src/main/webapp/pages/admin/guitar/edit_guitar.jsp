<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
    <title><cst:localeTag key="admin.edit_guitar.title" /></title>
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

    <form id="edit_guitar_form" action="${pageContext.request.contextPath}/controller?command=update_guitar"
          method="post" enctype="multipart/form-data">
        <input type="text" name="id" value="${requestScope.guitar.entityId}" hidden>
        <input type="text" name="name" value="${requestScope.guitar.name}"
               placeholder=<cst:localeTag key="placeholder.name" /> required pattern="[a-zA-Z0-9\s\-]{1,30}">
        <br>
        <input type="file" name="picturePath" id="file_input" accept="image/png, image/jpeg">
        <br>
        <select name="bodyId" id="bodySelect" required></select>
        <br>
        <select name="neckId" id="neckSelect" required></select>
        <br>
        <select name="pickupId" id="pickupSelect" required></select>
        <br>
        <select name="neckJoint" id="neckJointSelect">
            <option value="BOLT_ON">BOLT_ON</option>
            <option value="SET_NECK">SET_NECK</option>
            <option value="NECK_THROUGH">NECK_THROUGH</option>
        </select>
        <br>
        <input type="text" name="color" value="${requestScope.guitar.color}"
               placeholder=<cst:localeTag key="placeholder.color" /> required pattern="[a-zA-Z0-9\s\-]{1,30}">
        <br>
        <select name="userId" id="userSelect" required></select>
        <br>
        <select name="orderStatus" id="orderStatusSelect">
            <option value="ORDERED">ORDERED</option>
            <option value="IN_PROGRESS">IN_PROGRESS</option>
            <option value="COMPLETED">COMPLETED</option>
        </select>
        <br>
        <input type="submit" value=<cst:localeTag key="admin.edit" />>
    </form>

    <c:if test="${requestScope.validationError}">
        <p><cst:localeTag key="error.validation_error" /></p>
    </c:if>

    <jsp:include page="../../common/shared/footer.jsp" />

    <script>
        $(document).ready( function () {
            sessionStorage.removeItem('cachedBodies');
            sessionStorage.removeItem('cachedNecks');
            sessionStorage.removeItem('cachedPickups');
            sessionStorage.removeItem('cachedUsers');

            let bodyId = ${requestScope.guitar.bodyId};
            let neckId = ${requestScope.guitar.neckId};
            let pickupId = ${requestScope.guitar.pickupId};
            let userId = ${requestScope.guitar.userId};
            let bodySelect = $('#bodySelect');
            let neckSelect = $('#neckSelect');
            let pickupSelect = $('#pickupSelect');
            let userSelect = $('#userSelect');

            $('#neckJointSelect').val('${requestScope.guitar.neckJoint}')
            $('#orderStatusSelect').val('${requestScope.guitar.orderStatus}')

            bodySelect.select2({
                language: '${sessionScope.locale}'.substring(0, 2),
                placeholder: '<cst:localeTag key="admin.guitars.body" />',
                // theme: 'bootstrap',
                width: '10%',
                maximumInputLength: 50,
                ajax: {
                    delay: 250,
                    url: '/controller?command=get_bodies',
                    data: function (params) {
                        return {
                            term: params.term || '',
                            page: params.page || 1,
                            pageSize: 10,
                            requestType: 'SELECT',
                            filterCriteria: 'NAME'
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

            neckSelect.select2({
                language: '${sessionScope.locale}'.substring(0, 2),
                placeholder: 'cst:localeTag key="admin.guitars.neck" />',
                // theme: 'bootstrap',
                width: '10%',
                maximumInputLength: 50,
                ajax: {
                    delay: 250,
                    url: '/controller?command=get_necks',
                    data: function (params) {
                        return {
                            term: params.term || '',
                            page: params.page || 1,
                            pageSize: 10,
                            requestType: 'SELECT',
                            filterCriteria: 'NAME'
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

            pickupSelect.select2({
                language: '${sessionScope.locale}'.substring(0, 2),
                placeholder: '<cst:localeTag key="admin.guitars.pickup" />',
                // theme: 'bootstrap',
                width: '10%',
                maximumInputLength: 50,
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

            userSelect.select2({
                language: '${sessionScope.locale}'.substring(0, 2),
                placeholder: '<cst:localeTag key="admin.guitars.user" />',
                // theme: 'bootstrap',
                width: '10%',
                maximumInputLength: 50,
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

            if (!isNaN(Number.parseInt(bodyId))) {
                fetchBody(bodyId, function (entity) {
                    let option = new Option(entity.name, entity.entityId);
                    bodySelect.append(option).trigger('change');
                });
            }

            if (!isNaN(Number.parseInt(neckId))) {
                fetchNeck(neckId, function (entity) {
                    let option = new Option(entity.name, entity.entityId);
                    neckSelect.append(option).trigger('change');
                });
            }

            if (!isNaN(Number.parseInt(pickupId))) {
                fetchPickup(pickupId, function (entity) {
                    let option = new Option(entity.name, entity.entityId);
                    pickupSelect.append(option).trigger('change');
                });
            }

            if (!isNaN(Number.parseInt(userId))) {
                fetchUser(userId, function (entity) {
                    let option = new Option(entity.login, entity.entityId);
                    userSelect.append(option).trigger('change');
                });
            }

            let form = $('#edit_guitar_form');
            form.submit( function () {
                let fileInput = $('#file_input');
                let files = fileInput.prop('files');
                for (let i = 0; i < files.length; i++) {
                    let type = files[i].type;
                    if (type !== "image/jpeg" && type !== "image/png") {
                        alert('Invalid file type! Choose .png or .jpeg file.')
                        return false;
                    }
                }
                return true;
            });
        });
    </script>

</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
    <title><cst:localeTag key="admin.edit_guitar.title" /></title>
    <jsp:include page="../shared/head.html" />
    <!-- jQuery Select2 -->
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/select2-bootstrap-theme/0.1.0-beta.10/select2-bootstrap.min.css" />
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    <c:if test="${sessionScope.locale == 'en_US'}">
        <script src="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/js/i18n/en.js"></script>
    </c:if>
    <c:if test="${sessionScope.locale == 'ru_RU'}">
        <script src="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/js/i18n/ru.js"></script>
    </c:if>

    <script src="/static/js/util/fetch.js"></script>
    <script src="/static/js/admin/guitar/edit_guitar.js"></script>
    <script src="/static/js/common/shared/footer.js"></script>
</head>

<jsp:include page="../../common/shared/header.jsp" />

<body data-body="<cst:localeTag key="admin.guitars.body" />"
      data-neck="<cst:localeTag key="admin.guitars.neck" />"
      data-pickup="<cst:localeTag key="admin.guitars.pickup" />"
      data-user="<cst:localeTag key="admin.guitars.user" />"
      data-neck-joint="${requestScope.guitar.neckJoint}"
      data-order-status="${requestScope.guitar.orderStatus}"
      data-body-id="${requestScope.guitar.bodyId}"
      data-neck-id="${requestScope.guitar.neckId}"
      data-pickup-id="${requestScope.guitar.pickupId}"
      data-user-id="${requestScope.guitar.userId}">

    <jsp:include page="../shared/header.jsp" />

    <main role="main" class="container bg-light admin-main-form">
        <h3 class="row justify-content-center mb-4">
            <cst:localeTag key="admin.edit_guitar.title" />
        </h3>
        <form id="edit_guitar_form" action="${pageContext.request.contextPath}/controller?command=update_guitar"
              method="post" enctype="multipart/form-data">
            <input type="text" name="id" value="${requestScope.guitar.entityId}" hidden>
            <div class="form-outline mb-2">
                <label class="form-group" for="ni"><cst:localeTag key="admin.guitars.name" /></label>
                <input id="ni" type="text" name="name" value="${requestScope.guitar.name}"
                       placeholder=<cst:localeTag key="placeholder.name" /> required pattern="[a-zA-Z0-9\s\-]{1,30}" class="form-control form-control-sm">
            </div>
            <div class="form-outline mb-2">
                <label class="form-group" for="ci"><cst:localeTag key="admin.guitars.color" /></label>
                <input id="ci" type="text" name="color" value="${requestScope.guitar.color}"
                       placeholder=<cst:localeTag key="placeholder.color" /> required pattern="[a-zA-Z0-9\s\-]{1,30}" class="form-control form-control-sm">
            </div>
            <div class="form-outline mb-2">
                <label class="form-group" for="bodySelect"><cst:localeTag key="admin.guitars.body" /></label>
                <select name="bodyId" id="bodySelect" required class="form-control form-control-sm"></select>
            </div>
            <div class="form-outline mb-2">
                <label class="form-group" for="neckSelect"><cst:localeTag key="admin.guitars.neck" /></label>
                <select name="neckId" id="neckSelect" required class="form-control form-control-sm"></select>
            </div>
            <div class="form-outline mb-2">
                <label class="form-group" for="pickupSelect"><cst:localeTag key="admin.guitars.pickup" /></label>
                <select name="pickupId" id="pickupSelect" required class="form-control form-control-sm"></select>
            </div>
            <div class="form-outline mb-2">
                <label class="form-group" for="userSelect"><cst:localeTag key="admin.guitars.user" /></label>
                <select name="userId" id="userSelect" required class="form-control form-control-sm"></select>
            </div>
            <div class="form-outline mb-2">
                <label class="form-group" for="neckJointSelect"><cst:localeTag key="admin.guitars.neck_joint" /></label>
                <select name="neckJoint" id="neckJointSelect" class="form-select form-select-sm">
                    <option value="BOLT_ON"><cst:localeTag key="admin.guitars.neck_joint.bolt_on" /></option>
                    <option value="SET_NECK"><cst:localeTag key="admin.guitars.neck_joint.set_neck" /></option>
                    <option value="NECK_THROUGH"><cst:localeTag key="admin.guitars.neck_joint.neck_through" /></option>
                </select>
            </div>
            <div class="form-outline mb-2">
                <label class="form-group" for="orderStatusSelect"><cst:localeTag key="admin.guitars.order_status" /></label>
                <select name="orderStatus" id="orderStatusSelect" class="form-select form-select-sm">
                    <option value="ORDERED"><cst:localeTag key="admin.guitars.order_status.ordered" /></option>
                    <option value="IN_PROGRESS"><cst:localeTag key="admin.guitars.order_status.in_progress" /></option>
                    <option value="COMPLETED"><cst:localeTag key="admin.guitars.order_status.completed" /></option>
                </select>
            </div>
            <label class="form-group" for="file_input"><cst:localeTag key="admin.guitars.picture_path" /></label>
            <div class="form-outline mb-2">
                <input type="file" name="picturePath" id="file_input" accept="image/png, image/jpeg" class="form-control-file form-control-sm">
            </div>
            <div class="form-actions text-center">
                <input type="submit" class="btn btn-secondary btn-block" value=<cst:localeTag key="admin.edit" />>
            </div>
        </form>

        <c:if test="${param.validationError}">
            <p><cst:localeTag key="error.validation_error" /></p>
        </c:if>
    </main>

</body>

<jsp:include page="../../common/shared/footer.jsp" />

</html>

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
    <script src="/static/js/admin/guitar/edit_guitar.js"></script>
    <script src="/static/js/common/shared/footer.js"></script>
</head>
<body data-body="<cst:localeTag key="admin.guitars.body" />"
      data-neck="<cst:localeTag key="admin.guitars.neck" />"
      data-pickup="<cst:localeTag key="admin.guitars.pickup" />"
      data-user="<cst:localeTag key="admin.guitars.user" />"
      data-neck-joint="<cst:localeTag key="admin.guitars.neck_joint" />"
      data-order-status="<cst:localeTag key="admin.guitars.order_status" />"
      data-body-id="${requestScope.guitar.bodyId}"
      data-neck-id="${requestScope.guitar.neckId}"
      data-pickup-id="${requestScope.guitar.pickupId}"
      data-user-id="${requestScope.guitar.userId}">

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

</body>
</html>

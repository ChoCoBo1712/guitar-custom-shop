<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
  <title><cst:localeTag key="construct_guitar.title" /></title>
  <jsp:include page="shared/head.html" />

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
  <script src="/static/js/common/construct_guitar.js"></script>
  <script src="/static/js/common/shared/footer.js"></script>
</head>
<body data-body="<cst:localeTag key="admin.guitars.body" />"
      data-neck="<cst:localeTag key="admin.guitars.neck" />"
      data-pickup="<cst:localeTag key="admin.guitars.pickup" />">

  <jsp:include page="shared/header.jsp" />

  <form id="construct_guitar_form" action="${pageContext.request.contextPath}/controller?command=construct_guitar" method="post">
    <input type="text" name="name" placeholder=<cst:localeTag key="placeholder.name" /> required
           pattern="[a-zA-Z0-9\s\-]{1,30}">
    <br>
    <select name="bodyId" id="bodySelect" required></select>
    <br>
    <select name="neckId" id="neckSelect" required></select>
    <br>
    <select name="pickupId" id="pickupSelect" required></select>
    <br>
    <select name="neckJoint" id="neck_joint_select">
      <option selected value="BOLT_ON">BOLT_ON</option>
      <option value="SET_NECK">SET_NECK</option>
      <option value="NECK_THROUGH">NECK_THROUGH</option>
    </select>
    <br>
    <input type="text" name="color" placeholder=<cst:localeTag key="placeholder.color" /> required
           pattern="[a-zA-Z0-9\s\-]{1,30}">
    <br>
    <input type="submit" value=<cst:localeTag key="construct_guitar.construct" />>
  </form>

  <c:if test="${param.validationError}">
    <p><cst:localeTag key="error.validation_error" /></p>
  </c:if>

  <jsp:include page="shared/footer.jsp" />

</body>
</html>
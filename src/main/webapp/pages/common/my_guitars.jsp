<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
  <title><cst:localeTag key="index.title" /></title>
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
  <script src="/static/js/common/my_guitars.js"></script>
  <script src="/static/js/common/shared/footer.js"></script>
</head>
<body data-guitars-string="<cst:localeTag key="admin.guitars" />">

  <jsp:include page="shared/header.jsp" />

  <main role="main" class="container common-form">
    <h3 class="row justify-content-center mb-4">
      <cst:localeTag key="my_guitars.title" />
    </h3>
    <div class="form-outline mb-3">
      <label class="form-group" for="guitarSelect"><cst:localeTag key="admin.guitars" /></label>
      <select id="guitarSelect" class="form-control form-control-sm"></select>
    </div>
    <div class="form-outline mb-3">
      <img id="image" src="/images/default.png" alt="Guitar picture" width="auto" height="200" class="img-thumbnail">
    </div>
    <div class="form-outline mb-3">
      <label class="form-group" for="orderStatusInput"><cst:localeTag key="admin.guitars.order_status" /></label>
      <input id="orderStatusInput" readonly class="form-control form-control-sm"/>
    </div>
    <div class="form-outline mb-3">
      <label class="form-group" for="nameInput"><cst:localeTag key="admin.guitars.name" /></label>
      <input id="nameInput" readonly class="form-control form-control-sm"/>
    </div>
    <div class="form-outline mb-3">
      <label class="form-group" for="bodyInput"><cst:localeTag key="admin.guitars.body" /></label>
      <input id="bodyInput" readonly class="form-control form-control-sm"/>
    </div>
    <div class="form-outline mb-3">
      <label class="form-group" for="neckInput"><cst:localeTag key="admin.guitars.neck" /></label>
      <input id="neckInput" readonly class="form-control form-control-sm"/>
    </div>
    <div class="form-outline mb-3">
      <label class="form-group" for="pickupInput"><cst:localeTag key="admin.guitars.pickup" /></label>
      <input id="pickupInput" readonly class="form-control form-control-sm"/>
    </div>
    <div class="form-outline mb-3">
      <label class="form-group" for="neckJointInput"><cst:localeTag key="admin.guitars.neck_joint" /></label>
      <input id="neckJointInput" readonly class="form-control form-control-sm"/>
    </div>
    <div class="form-outline mb-3">
      <label class="form-group" for="colorInput"><cst:localeTag key="admin.guitars.color" /></label>
      <input id="colorInput" readonly class="form-control form-control-sm"/>
    </div>
  </main>

</body>

<jsp:include page="shared/footer.jsp" />

</html>
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

  <select id="guitarSelect"></select>

  <div id="guitar">
    <img id="image" src="/static/image/default.png" width="auto" height="200">
    <br>
    <input id="nameInput" readonly/>
    <br>
    <input id="bodyInput" readonly/>
    <br>
    <input id="neckInput" readonly/>
    <br>
    <input id="pickupInput" readonly/>
    <br>
    <input id="neckJointInput" readonly/>
    <br>
    <input id="colorInput" readonly/>
    <br>
    <input id="orderStatusInput" readonly/>
    <br>
  </div>

  <jsp:include page="shared/footer.jsp" />
</body>
</html>
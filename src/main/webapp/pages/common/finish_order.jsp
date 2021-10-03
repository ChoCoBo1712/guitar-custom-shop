<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
  <title><cst:localeTag key="finish_order.title" /></title>
  <jsp:include page="shared/head.html" />

  <script src="/static/js/common/finish_order.js"></script>
  <script src="/static/js/common/shared/footer.js"></script>
</head>
<body>

  <jsp:include page="shared/header.jsp" />

  <form id="finish_order_form" action="${pageContext.request.contextPath}/controller?command=finish_order"
        method="post" enctype="multipart/form-data">
    <input type="text" name="id" value="${param.id}" hidden>
    <input type="file" name="picturePath" id="file_input" accept="image/png, image/jpeg" required>
    <br>
    <input type="submit">
  </form>

  <jsp:include page="shared/footer.jsp" />
</body>
</html>
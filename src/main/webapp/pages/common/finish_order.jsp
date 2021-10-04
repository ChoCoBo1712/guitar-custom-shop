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

<jsp:include page="shared/header.jsp" />

<body>

    <main role="main" class="container common-main-form">
        <h3 class="row justify-content-center mb-4">
            <cst:localeTag key="finish_order.title" />
        </h3>
        <form id="finish_order_form" action="${pageContext.request.contextPath}/controller?command=finish_order"
              method="post" enctype="multipart/form-data">
            <input type="text" name="id" value="${param.id}" hidden>
            <label class="form-group" for="file_input"><cst:localeTag key="admin.guitars.picture_path" /></label>
            <div class="form-outline mb-3">
                <input type="file" name="picturePath" id="file_input" accept="image/png, image/jpeg" class="form-control-file form-control-sm">
            </div>
            <div class="form-actions text-center">
                <input type="submit" class="btn btn-secondary btn-block" value=<cst:localeTag key="forgot_password.submit" />>
            </div>
        </form>
    </main>

</body>

<jsp:include page="shared/footer.jsp" />

</html>
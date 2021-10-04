<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
    <title><cst:localeTag key="error.error" /> 500</title>
    <jsp:include page="../shared/head.html" />

    <script src="/static/js/common/set_locale.js"></script>
    <script src="/static/js/common/shared/footer.js"></script>
</head>

<jsp:include page="../shared/header.jsp" />

<body>

    <main role="main" class="container-fluid bg-light h-100 row align-items-center text-center">
        <h1 class="error"><cst:localeTag key="error.error" /> 500!</h1>
    </main>

</body>

<jsp:include page="../shared/footer.jsp" />

</html>

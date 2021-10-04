<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
    <title><cst:localeTag key="index.title" /></title>
    <jsp:include page="shared/head.html" />

    <script src="/static/js/common/set_locale.js"></script>
    <script src="/static/js/common/shared/footer.js"></script>
</head>

<jsp:include page="shared/header.jsp" />

<body>

    <main role="main" class="container common-main-form">
        <h3 class="justify-content-center mb-4">
            <cst:localeTag key="index.header" />
        </h3>
        <p class="lead"><cst:localeTag key="index.content" /></p>
        <ul>
            <li><cst:localeTag key="index.content1" /></li>
            <li><cst:localeTag key="index.content2" /></li>
            <li><cst:localeTag key="index.content3" /></li>
            <li><cst:localeTag key="index.content4" /></li>
            <li><cst:localeTag key="index.content5" /></li>
        </ul>
        <p class="lead"><cst:localeTag key="index.content6" /></p>
    </main>

</body>

<jsp:include page="shared/footer.jsp" />

</html>
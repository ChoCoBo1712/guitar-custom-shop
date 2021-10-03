<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
    <title><cst:localeTag key="admin.create_wood.title" /></title>
    <jsp:include page="../shared/head.html" />

    <script src="/static/js/common/set_locale.js"></script>
    <script src="/static/js/common/shared/footer.js"></script>
</head>
<body>

    <jsp:include page="../../common/shared/header.jsp" />
    <jsp:include page="../shared/header.jsp" />

    <form action="${pageContext.request.contextPath}/controller?command=create_wood" method="post">
        <input type="text" name="name" placeholder=<cst:localeTag key="placeholder.name" /> required
               pattern="[a-zA-Z0-9\s\-]{1,30}">
        <br>
        <input type="submit" value=<cst:localeTag key="admin.create" />>
    </form>

    <c:if test="${param.validationError}">
        <p><cst:localeTag key="error.validation_error" /></p>
    </c:if>

    <jsp:include page="../../common/shared/footer.jsp" />

</body>
</html>

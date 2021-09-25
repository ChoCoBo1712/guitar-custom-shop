<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="properties.pagecontent" />

<html>
<head>
    <title><fmt:message key="admin.create_pickup.title" /></title>
    <jsp:include page="../shared/head.html" />
</head>
<body>
    <jsp:include page="../../common/shared/header.jsp" />
    <jsp:include page="../shared/header.jsp" />

    <form action="${pageContext.request.contextPath}/controller?command=create_pickup" method="post">
        <input type="text" name="name" placeholder=<fmt:message key="placeholder.name" /> required
               pattern="[a-zA-Z0-9\s\-]{1,30}">
        <br>
        <input type="submit" value=<fmt:message key="admin.create" />>
    </form>

    <c:if test="${requestScope.validationError}">
        <p><fmt:message key="error.validation_error" /></p>
    </c:if>

    <jsp:include page="../../common/shared/footer.jsp" />

</body>
</html>

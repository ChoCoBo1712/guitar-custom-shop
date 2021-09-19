<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="properties.pagecontent" />

<html>
<head>
    <title><fmt:message key="admin.create_wood.title" /></title>
    <jsp:include page="../shared/head.html" />
</head>
<body>
    <jsp:include page="../shared/header.jsp" />

    <form action="${pageContext.request.contextPath}/controller?command=create_wood" method="post">
        <input type="text" name="name" placeholder=<fmt:message key="placeholder.name" /> required
               pattern="[a-zA-Z\s]{1,30}">
        <br>
        <input type="submit" value=<fmt:message key="admin.create" />>
    </form>

    <c:if test="${requestScope.invalidNamePatternError}">
        <p><fmt:message key="error.invalid_name" /></p>
    </c:if>

    <c:if test="${requestScope.duplicateNameError}">
        <p><fmt:message key="error.duplicate_name" /></p>
    </c:if>

    <jsp:include page="../shared/footer.jsp" />

</body>
</html>

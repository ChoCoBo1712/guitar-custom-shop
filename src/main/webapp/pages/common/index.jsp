<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="properties.pagecontent" />

<html>
<head>
    <title><fmt:message key="index.title" /></title>
    <jsp:include page="shared/head.html" />
</head>
<body>
    <jsp:include page="shared/header.jsp" />



    <jsp:include page="shared/footer.jsp" />
</body>
</html>
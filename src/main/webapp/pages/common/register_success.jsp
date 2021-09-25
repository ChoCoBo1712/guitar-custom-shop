<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="properties.pagecontent" />

<html>
<head>
    <title><fmt:message key="register_success.title" /></title>
</head>
<body>
    <jsp:include page="shared/header.jsp" />

    <p><fmt:message key="register_success.message" /></p>

    <jsp:include page="shared/footer.jsp" />
</body>
</html>

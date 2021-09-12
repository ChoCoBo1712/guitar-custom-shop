<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="properties.pagecontent" />

<html>
<head>
    <title><fmt:message key="admin.edit_user.title" /></title>
    <jsp:include page="../shared/head.html" />
</head>
<body>
    <jsp:include page="../shared/header.jsp" />

    <form action="${pageContext.request.contextPath}/controller?command=update_user&previousEmail=${requestScope.user.email}&previousLogin=${requestScope.user.login}" method="post">
        <input type="text" name="id" value="${requestScope.user.entityId}" hidden>
        <input type="email" name="email" value="${requestScope.user.email}"
               placeholder=<fmt:message key="register.email" /> required>
        <br>
        <input type="text" name="login" value="${requestScope.user.login}"
               placeholder=<fmt:message key="register.login" /> required minlength="6">
        <br>
        <select name="role" id="role_select">
            <option value="ADMIN">ADMIN</option>
            <option value="CLIENT">CLIENT</option>
            <option value="MASTER">MASTER</option>
        </select>
        <br>
        <select name="status" id="status_select">
            <option value="NOT_CONFIRMED">NOT_CONFIRMED</option>
            <option value="CONFIRMED">CONFIRMED</option>
        </select>
        <br>
        <input type="submit" value=<fmt:message key="admin.edit" />>
    </form>

    <c:if test="${sessionScope.invalidEmailError}">
        <p><fmt:message key="register.invalid_email" /></p>
    </c:if>

    <c:if test="${sessionScope.invalidLoginError}">
        <p><fmt:message key="register.invalid_login" /></p>
    </c:if>

    <jsp:include page="../shared/footer.jsp" />

    <script>
        $('#role_select').val('${requestScope.user.role}')
        $('#status_select').val('${requestScope.user.status}')
    </script>

</body>
</html>

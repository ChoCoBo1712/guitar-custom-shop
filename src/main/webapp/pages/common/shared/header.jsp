<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="properties.pagecontent" />

<header>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_index_page"><fmt:message key="pages.home" /></a>
    <c:choose>
        <c:when test="${sessionScope.user != null}">
            <c:if test="${sessionScope.user.role.ordinal() == 0}">
                <a href="${pageContext.request.contextPath}/controller?command=go_to_users_page"><fmt:message key="index.admin" /></a>
            </c:if>
            <a href="${pageContext.request.contextPath}/controller?command=logout"><fmt:message key="index.logout" /></a>
            <a href="${pageContext.request.contextPath}/controller?command=go_to_profile_page">${sessionScope.user.login}</a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/controller?command=go_to_login_page"><fmt:message key="index.login" /></a>
            <a href="${pageContext.request.contextPath}/controller?command=go_to_register_page"><fmt:message key="index.register" /></a>
        </c:otherwise>
    </c:choose>
    <hr>
</header>

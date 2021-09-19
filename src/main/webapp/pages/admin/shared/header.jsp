<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="properties.pagecontent" />

<header>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_users_page"><fmt:message key="admin.users" /></a>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_woods_page"><fmt:message key="admin.woods" /></a>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_bodies_page"><fmt:message key="admin.bodies" /></a>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_necks_page"><fmt:message key="admin.necks" /></a>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_pickups_page"><fmt:message key="admin.pickups" /></a>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_guitars_page"><fmt:message key="admin.guitars" /></a>
    <hr>
</header>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<header>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_index_page"><cst:localeTag key="pages.home" /></a>
    <c:choose>
        <c:when test="${sessionScope.user != null}">
            <%--admin--%>
            <c:if test="${sessionScope.user.role.ordinal() == 0}">
                <a href="${pageContext.request.contextPath}/controller?command=go_to_users_page"><cst:localeTag key="index.admin" /></a>
                <a href="${pageContext.request.contextPath}/controller?command=go_to_construct_guitar_page"><cst:localeTag key="index.construct_guitar" /></a>
                <a href="${pageContext.request.contextPath}/controller?command=go_to_guitar_orders_page"><cst:localeTag key="index.guitar_orders" /></a>
            </c:if>
            <%--master--%>
            <c:if test="${sessionScope.user.role.ordinal() == 1}">
                <a href="${pageContext.request.contextPath}/controller?command=go_to_construct_guitar_page"><cst:localeTag key="index.construct_guitar" /></a>
                <a href="${pageContext.request.contextPath}/controller?command=go_to_guitar_orders_page"><cst:localeTag key="index.guitar_orders" /></a>
            </c:if>
            <%--client--%>
            <c:if test="${sessionScope.user.role.ordinal() == 2}">
                <a href="${pageContext.request.contextPath}/controller?command=go_to_construct_guitar_page"><cst:localeTag key="index.construct_guitar" /></a>
            </c:if>
            <a href="${pageContext.request.contextPath}/controller?command=logout"><cst:localeTag key="index.logout" /></a>
            <a href="${pageContext.request.contextPath}/controller?command=go_to_profile_page">${sessionScope.user.login}</a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/controller?command=go_to_login_page"><cst:localeTag key="index.login" /></a>
            <a href="${pageContext.request.contextPath}/controller?command=go_to_register_page"><cst:localeTag key="index.register" /></a>
        </c:otherwise>
    </c:choose>
    <hr>
</header>

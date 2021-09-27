<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<header>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_users_page"><cst:localeTag key="admin.users" /></a>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_woods_page"><cst:localeTag key="admin.woods" /></a>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_bodies_page"><cst:localeTag key="admin.bodies" /></a>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_necks_page"><cst:localeTag key="admin.necks" /></a>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_pickups_page"><cst:localeTag key="admin.pickups" /></a>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_guitars_page"><cst:localeTag key="admin.guitars" /></a>
    <hr>
</header>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>


<nav class="navbar navbar-expand-lg navbar-light bg-light" style="padding-top: 70px">
    <div class="container-fluid">
        <ul class="nav navbar-nav me-auto ms-auto">
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/controller?command=go_to_users_page" role="button" class="btn btn-primary me-5">
                    <cst:localeTag key="admin.users" />
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/controller?command=go_to_woods_page" role="button" class="btn btn-primary me-5">
                    <cst:localeTag key="admin.woods" />
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/controller?command=go_to_bodies_page" role="button" class="btn btn-primary me-5">
                    <cst:localeTag key="admin.bodies" />
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/controller?command=go_to_necks_page" role="button" class="btn btn-primary me-5">
                    <cst:localeTag key="admin.necks" />
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/controller?command=go_to_pickups_page" role="button" class="btn btn-primary me-5">
                    <cst:localeTag key="admin.pickups" />
                </a>
            </li>
            <li class="nav-item"i>
                <a href="${pageContext.request.contextPath}/controller?command=go_to_guitars_page" role="button" class="btn btn-primary me-5">
                    <cst:localeTag key="admin.guitars" />
                </a>
            </li>
        </ul>
    </div>
</nav>

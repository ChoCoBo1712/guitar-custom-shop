<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<footer data-locale="${sessionScope.locale}">

    <nav class="navbar fixed-bottom navbar-light bg-light">
        <div class="container-fluid">
            <ul class="nav navbar-nav">
                <li class="nav-item">
                    <p class="navbar-text mb-auto mt-auto"><cst:localeTag key="footer.site_by" /> ChoCoBo</p>
                </li>
            </ul>
            <ul class="navbar-nav navbar-right">
                <li class="nav-item">
                    <select id="localeSelect">
                        <option value="en_US">EN</option>
                        <option value="ru_RU">RU</option>
                    </select>
                </li>
            </ul>
        </div>
    </nav>
</footer>

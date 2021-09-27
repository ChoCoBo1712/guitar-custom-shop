<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<footer>
    <hr>
    <p><cst:localeTag key="footer.site_by" /> ChoCoBo</p>
    <select id="localeSelect">
        <option value="en_US">EN</option>
        <option value="ru_RU">RU</option>
    </select>

    <script>
        $(document).ready( function () {
            let localeSelect = $('#localeSelect');
            localeSelect.val('${sessionScope.locale}');
            localeSelect.change( function () {
                $.cookie('locale', localeSelect.val(), {expires: 7});
                location.reload();
            })
        });
    </script>
</footer>

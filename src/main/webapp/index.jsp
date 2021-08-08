<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Index page</title>
</head>
<body>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_login_page">Login</a>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_register_page">Register</a>
</body>
</html>
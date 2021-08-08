<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login page</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/controller?command=login" method="post">
    <input type="text" name="login" placeholder="Login..." required><br/>
    <input type="password" name="password" placeholder="Password..." required><br/>
    <input type="submit" value="Login">

    <p>${loginError}</p>
</form>
</body>
</html>

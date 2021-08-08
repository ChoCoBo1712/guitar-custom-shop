<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registration page</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/controller?command=registration" method="post">
        <input type="email" name="email" placeholder="Email..." required><br/>
        <input type="text" name="login" placeholder="Login..." required minlength="6"><br/>
        <input type="password" name="password" placeholder="Password..." required minlength="8"><br/>
        <input type="submit" value="Register">
    </form>

    <p>${validationError}</p>
</body>
</html>

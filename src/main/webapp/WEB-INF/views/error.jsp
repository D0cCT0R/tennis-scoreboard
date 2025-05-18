<%--
  Created by IntelliJ IDEA.
  User: D0cCT0R
  Date: 13.05.2025
  Time: 21:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<html>
<head>
    <title>error</title>
</head>
<body>
<p style="font-size: 20px; text-align: center">${errorMessage}</p>
<a href="${pageContext.request.contextPath}/" style="font-size: 20px; text-align: center; display: block">Перейти на главную страницу</a>
</body>
</html>

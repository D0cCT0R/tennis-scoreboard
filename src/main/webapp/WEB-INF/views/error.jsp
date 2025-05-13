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
    <title>Ошибка 404</title>
</head>
<body>
<h1 style="text-align: center">Матч не найден</h1>
<p style="font-size: 20px; text-align: center">${errorMessage}</p>
<a href="${pageContext.request.contextPath}/new-match" style="font-size: 20px; text-align: center; display: block">Создать новый матч</a>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: D0cCT0R
  Date: 17.04.2025
  Time: 22:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>New Match</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body class="container">
<header>
    <div class="header_logo">
        <img class="header_logo_img" src="${pageContext.request.contextPath}/img/tennis_racket.svg" alt="Logo"/>
        <h1 class="header_logo_title">Tennis Scoreboard</h1>
    </div>
    <nav class="header_nav">
        <ul>
            <li><a href="${pageContext.request.contextPath}/">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/matches">Matches</a></li>
        </ul>
    </nav>
</header>
<main>
    <h2 class="main_title">Start a new match</h2>
    <c:if test="${not empty error}">
        <div class="invalid-feedback" style="color: red; text-align: center">${error}</div>
    </c:if>
    <form action="${pageContext.request.contextPath}/new-match" method="post" class="main_form">
        <label for="player1" class="main_form_label">Player 1:</label>
        <input
                type="text"
                id="player1"
                name="player1"
                class="main_form_input"
                placeholder="Имя первого игрока"
                required
        />
        <br/>
        <label for="player2" class="main_form_label">Player 2:</label>
        <input
                type="text"
                id="player2"
                name="player2"
                class="main_form_input"
                placeholder="Имя второго игрока"
                required
        />
        <br/>
        <button type="submit" class="main_form_button">Start</button>
    </form>
</main>
</body>
</html>

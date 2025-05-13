<%--
  Created by IntelliJ IDEA.
  User: D0cCT0R
  Date: 17.04.2025
  Time: 22:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
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
<section class="main">
    <h2 class="main_title">Welcome to Tennis Scoreboard</h2>
    <p class="main_desc">
        Manage your tennis matches, record results, and track rankings
    </p>
    <div class="main_buttons">
        <a href="${pageContext.request.contextPath}/new-match">
            <button class="main_button main_button_">Start a new
                match</button>
        </a>
        <a href="${pageContext.request.contextPath}/matches">
            <button class="main_button">View match results</button>
        </a>
    </div>
</section>
</body>
</html>

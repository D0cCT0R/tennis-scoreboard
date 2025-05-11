<%--
  Created by IntelliJ IDEA.
  User: D0cCT0R
  Date: 21.04.2025
  Time: 23:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Match Score</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
<header>
    <div class="header_logo">
        <img class="header_logo_img" src="${pageContext.request.contextPath}/img/tennis_racket.svg" alt="Logo" />
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
    <div class="container">
        <h1>Current match</h1>
        <div class="current-match-image"></div>
        <section class="score">
            <table class="table">
                <thead class="result">
                <tr>
                    <th class="table-text">Player</th>
                    <th class="table-text">Sets</th>
                    <c:if test="${match.tieBreak}">
                        <th class="table-text">Tie Brake Points</th>
                    </c:if>
                    <th class="table-text">Games</th>
                    <th class="table-text">Points</th>
                </tr>
                </thead>
                <tbody>
                <tr class="player1">
                    <td class="table-text">${match.namePlayer1}</td>
                    <td class="table-text">${match.setsPlayer1}</td>
                    <c:if test="${match.tieBreak}">
                        <td class="table-text">${match.tieBreakPointPlayer1}</td>
                    </c:if>
                    <td class="table-text">${match.gamePlayer1}</td>
                    <td class="table-text">${match.translatePointPlayer(match.pointPlayer1)}</td>
                    <td class="table-text">
                        <form action="${pageContext.request.contextPath}/match-score" method="post">
                            <input type="hidden" name="uuid" value="${match.uuid}">
                            <input type="hidden" name="winner" value="player1">
                            <button class="score-btn" type="submit">Score</button>
                        </form>
                    </td>
                </tr>
                <tr class="player2">
                    <td class="table-text">${match.namePlayer2}</td>
                    <td class="table-text">${match.setsPlayer2}</td>
                    <c:if test="${match.tieBreak}">
                        <td class="table-text">${match.tieBreakPointPlayer2}</td>
                    </c:if>
                    <td class="table-text">${match.gamePlayer2}</td>
                    <td class="table-text">${match.translatePointPlayer(match.pointPlayer2)}</td>
                    <td class="table-text">
                        <form action="${pageContext.request.contextPath}/match-score" method="post">
                            <input type="hidden" name="uuid" value="${match.uuid}">
                            <input type="hidden" name="winner" value="player2">
                            <button class="score-btn" type="submit">Score</button>
                        </form>
                    </td>
                </tr>
                </tbody>
            </table>
        </section>
    </div>
</main>
</body>
</html>

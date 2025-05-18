<%--
  Created by IntelliJ IDEA.
  User: D0cCT0R
  Date: 10.05.2025
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Finished Matches</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
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
    <div class="container">
        <h1>Matches</h1>
        <div class="input-container">
            <form action="${pageContext.request.contextPath}/matches" method="GET">
                <label>
                    <input class="input-filter" placeholder="Filter by name" name="playerName" type="text" required/>
                </label>
                <button type="submit">Найти</button>
            </form>
            <div>
                <a href="${pageContext.request.contextPath}/matches">
                    <button class="btn-filter">Reset Filter</button>
                </a>
            </div>
        </div>

        <c:if test="${empty error}">
            <table class="table-matches">
                <tr>
                    <th>Player One</th>
                    <th>Player Two</th>
                    <th>Winner</th>
                </tr>
                <c:forEach items="${matches}" var="match">
                    <tr>
                        <td>${match.player1.name}</td>
                        <td>${match.player2.name}</td>
                        <td><span class="winner-name-td">${match.winner.name}</span></td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${not empty error}">
            <div class="invalid-feedback" style="color: red; text-align: center">${error}</div>
        </c:if>

        <div class="pagination">
            <c:if test="${page > 1}">
                <a class="prev" href="matches?page=${page - 1}"> < </a>
            </c:if>
            <a class="num-page current" href="matches?page=${page}">${page}</a>
            <c:if test="${page + 1 <= totalPages}">
                <a class="num-page" href="matches?page=${page + 1}">${page + 1}</a>
            </c:if>
            <c:if test="${page < totalPages}">
                <a class="next" href="matches?page=${page + 1}"> > </a>
            </c:if>
        </div>
    </div>
</main>
</body>
</html>

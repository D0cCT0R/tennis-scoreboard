package com.example.tennisscoreboard.controllers;


import com.example.tennisscoreboard.models.domain.CurrentMatch;
import com.example.tennisscoreboard.storage.CurrentMatchStorage;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchController extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/new_match.jsp");
        requestDispatcher.forward(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String player1 = req.getParameter("player1");
        String player2 = req.getParameter("player2");
        String nameRegex = "^[\\p{L} .']+$";
        if (!player1.matches(nameRegex) || !player2.matches(nameRegex)) {
            req.setAttribute("error", "Имена могут состоять только из букв");
            req.getRequestDispatcher("/WEB-INF/views/new_match.jsp").forward(req, resp);
            return;
        }
        if (player1.equals(player2)) {
            req.setAttribute("error", "Имена не должны совпадать");
            req.getRequestDispatcher("/WEB-INF/views/new_match.jsp").forward(req, resp);
            return;
        }
        UUID matchUuid = UUID.randomUUID();
        CurrentMatch currentMatch = new CurrentMatch(matchUuid, player1, player2);
        CurrentMatchStorage.addMatch(matchUuid, currentMatch);
        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + matchUuid);
    }
}

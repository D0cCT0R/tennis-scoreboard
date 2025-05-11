package com.example.tennisscoreboard.controllers;


import com.example.tennisscoreboard.mapper.ConvertToDto;
import com.example.tennisscoreboard.models.domain.CurrentMatch;
import com.example.tennisscoreboard.services.MatchScoreCalculationService;
import com.example.tennisscoreboard.services.CompleteMatchService;
import com.example.tennisscoreboard.storage.CurrentMatchStorage;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreController extends HttpServlet {

    private final ConvertToDto convertToDto = new ConvertToDto();
    private final MatchScoreCalculationService calculationService = new MatchScoreCalculationService();
    private final CompleteMatchService completeMatchService = new CompleteMatchService();

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/match_score.jsp");
        String uuid = req.getParameter("uuid");
        CurrentMatch match = CurrentMatchStorage.getMatch(UUID.fromString(uuid));
        req.setAttribute("match", match);
        requestDispatcher.forward(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uuid = req.getParameter("uuid");
        String winner = req.getParameter("winner");
        CurrentMatch currentMatch = CurrentMatchStorage.getMatch(UUID.fromString(uuid));
        calculationService.calculate(currentMatch, winner);
        if (currentMatch.isComplete()) {
            CurrentMatchStorage.removeMatch(UUID.fromString(uuid));
            completeMatchService.saveMatch(currentMatch);
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuid);
    }
}



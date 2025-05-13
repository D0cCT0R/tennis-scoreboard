package com.example.tennisscoreboard.controllers;


import com.example.tennisscoreboard.models.domain.CurrentMatch;
import com.example.tennisscoreboard.services.MatchScoreCalculationService;
import com.example.tennisscoreboard.services.CompleteMatchService;
import com.example.tennisscoreboard.storage.CurrentMatchStorage;
import com.example.tennisscoreboard.util.ErrorHandler;
import com.example.tennisscoreboard.validation.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreController extends HttpServlet {

    private final MatchScoreCalculationService calculationService = new MatchScoreCalculationService();
    private final CompleteMatchService completeMatchService = new CompleteMatchService();

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        if (uuid == null || uuid.isEmpty()) {
            ErrorHandler.sendError(req, resp, "UUID не указан", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (!Validator.isValidUUID(uuid)) {
            ErrorHandler.sendError(req, resp, "Некорректный формат UUID", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        CurrentMatch currentMatch = CurrentMatchStorage.getMatch(UUID.fromString(uuid));
        if (currentMatch == null) {
            ErrorHandler.sendError(req, resp, "Матч не найден", HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        req.setAttribute("match", currentMatch);
        req.getRequestDispatcher("/WEB-INF/views/match_score.jsp").forward(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String uuid = req.getParameter("uuid");
        if (uuid == null || uuid.isEmpty()) {
            ErrorHandler.sendError(req, resp, "UUID не указан", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (!Validator.isValidUUID(uuid)) {
            ErrorHandler.sendError(req, resp, "Некорректный формат UUID", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String winner = req.getParameter("winner");
        if (winner == null || winner.isEmpty()) {
            ErrorHandler.sendError(req, resp, "Не указан победитель", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        CurrentMatch currentMatch = CurrentMatchStorage.getMatch(UUID.fromString(uuid));
        if (currentMatch == null) {
            ErrorHandler.sendError(req, resp, "Матч не найден", HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        calculationService.calculate(currentMatch, winner);
        if (currentMatch.isComplete()) {
            completeMatchService.saveMatch(currentMatch);
            resp.sendRedirect(req.getContextPath() + "/new-match");
            CurrentMatchStorage.removeMatch(UUID.fromString(uuid));
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuid);
    }
}



package com.example.tennisscoreboard.controllers;


import com.example.tennisscoreboard.exception.MatchSaveException;
import com.example.tennisscoreboard.exception.PlayerCreateException;
import com.example.tennisscoreboard.exception.PlayerSearchException;
import com.example.tennisscoreboard.models.domain.CurrentMatch;
import com.example.tennisscoreboard.services.MatchScoreCalculationService;
import com.example.tennisscoreboard.services.CompleteMatchService;
import com.example.tennisscoreboard.services.OngoingMatchesService;
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

    private MatchScoreCalculationService calculationService;
    private CompleteMatchService completeMatchService;
    private ErrorHandler errorHandler;
    private OngoingMatchesService ongoingMatchesService;
    @Override
    public void init() {
        calculationService = (MatchScoreCalculationService) getServletContext().getAttribute("calculationService");
        completeMatchService = (CompleteMatchService) getServletContext().getAttribute("completeMatchService");
        errorHandler = (ErrorHandler) getServletContext().getAttribute("errorHandler");
        ongoingMatchesService = (OngoingMatchesService) getServletContext().getAttribute("ongoingMatchesService");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        if (uuid == null || uuid.isBlank()) {
            errorHandler.sendError(req, resp, "UUID не указан", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (!Validator.isValidUUID(uuid)) {
            errorHandler.sendError(req, resp, "Некорректный формат UUID", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        CurrentMatch currentMatch = ongoingMatchesService.getMatch(UUID.fromString(uuid));
        if (currentMatch == null) {
            errorHandler.sendError(req, resp, "Матч не найден", HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        req.setAttribute("match", currentMatch);
        req.getRequestDispatcher("/WEB-INF/views/match_score.jsp").forward(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String uuid = req.getParameter("uuid");
        if (uuid == null || uuid.isEmpty()) {
            errorHandler.sendError(req, resp, "UUID не указан", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (!Validator.isValidUUID(uuid)) {
            errorHandler.sendError(req, resp, "Некорректный формат UUID", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String winner = req.getParameter("winner");
        if (winner == null || winner.isBlank()) {
            errorHandler.sendError(req, resp, "Не указан победитель", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        CurrentMatch currentMatch = ongoingMatchesService.getMatch(UUID.fromString(uuid));
        if (currentMatch == null) {
            errorHandler.sendError(req, resp, "Матч не найден", HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        calculationService.calculate(currentMatch, winner);
        if (currentMatch.isComplete()) {
            try {
                completeMatchService.saveMatch(currentMatch);
            } catch (MatchSaveException | PlayerSearchException | PlayerCreateException e) {
                errorHandler.sendError(req, resp, e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            resp.sendRedirect(req.getContextPath() + "/new-match");
            ongoingMatchesService.removeMatch(UUID.fromString(uuid));
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuid);
    }
}



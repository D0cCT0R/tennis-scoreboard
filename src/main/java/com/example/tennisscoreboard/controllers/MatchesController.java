package com.example.tennisscoreboard.controllers;

import com.example.tennisscoreboard.dto.MatchDto;
import com.example.tennisscoreboard.exception.MatchSearchException;
import com.example.tennisscoreboard.services.CompleteMatchService;
import com.example.tennisscoreboard.util.ErrorHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


@WebServlet("/matches")
public class MatchesController extends HttpServlet {

    private CompleteMatchService completeMatchService;
    private ErrorHandler errorHandler;

    @Override
    public void init() {
        completeMatchService = (CompleteMatchService) getServletContext().getAttribute("completeMatchService");
        errorHandler = (ErrorHandler) getServletContext().getAttribute("errorHandler");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int page = 1;
            int size = 8;
            String pageParam = req.getParameter("page");
            String playerName = req.getParameter("playerName");
            if (pageParam != null && !pageParam.isBlank()) {
                try {
                    page = Integer.parseInt(pageParam);
                } catch (NumberFormatException e) {
                    errorHandler.sendError(req, resp, "Страница не существует", HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
            }
            int totalMatches = completeMatchService.getTotalMatches(playerName);
            int totalPages = (int) Math.ceil((double) totalMatches / size);
            if (totalMatches == 0) {
                req.setAttribute("matches", Collections.emptyList());
                req.setAttribute("totalPages", 1);
                req.getRequestDispatcher("/WEB-INF/views/matches.jsp").forward(req, resp);
                return;
            }
            if (page < 1 || (totalPages != 0 && page > totalPages)) {
                errorHandler.sendError(req, resp, "Страница не существует", HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            List<MatchDto> matches = completeMatchService.getMatches(page, size, playerName);
            req.setAttribute("page", page);
            req.setAttribute("matches", matches);
            req.setAttribute("totalPages", totalPages);
            req.getRequestDispatcher("/WEB-INF/views/matches.jsp").forward(req, resp);
        } catch (MatchSearchException e) {
            errorHandler.sendError(req, resp, e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

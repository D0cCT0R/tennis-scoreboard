package com.example.tennisscoreboard.controllers;

import com.example.tennisscoreboard.dto.MatchDto;
import com.example.tennisscoreboard.services.CompleteMatchService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


@WebServlet("/matches")
public class MatchesController extends HttpServlet {

    public final CompleteMatchService completeMatchService = new CompleteMatchService();

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        int size = 8;
        String pageParam = req.getParameter("page");
        String playerName = req.getParameter("playerName");
        if (pageParam != null && !pageParam.isEmpty()) {
            page = Integer.parseInt(pageParam);
        }
        List<MatchDto> matches = completeMatchService.getMatches(page, size, playerName);
        int totalMatches = completeMatchService.getTotalMatches(playerName);
        int totalPages = (int) Math.ceil((double) totalMatches / size);
        req.setAttribute("page", page);
        req.setAttribute("matches", matches);
        req.setAttribute("totalPages", totalPages);
        req.getRequestDispatcher("/WEB-INF/views/matches.jsp").forward(req, resp);
    }
}

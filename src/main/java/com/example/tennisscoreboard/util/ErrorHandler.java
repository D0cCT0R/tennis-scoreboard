package com.example.tennisscoreboard.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ErrorHandler {
    public void sendError(HttpServletRequest req, HttpServletResponse resp, String message, int statusCode) throws ServletException, IOException {
        resp.setStatus(statusCode);
        req.setAttribute("errorMessage", message);
        req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
    }
}

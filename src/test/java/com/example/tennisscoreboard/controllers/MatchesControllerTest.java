package com.example.tennisscoreboard.controllers;

import static org.mockito.Mockito.*;

import com.example.tennisscoreboard.exception.MatchSearchException;
import com.example.tennisscoreboard.services.CompleteMatchService;
import com.example.tennisscoreboard.util.ErrorHandler;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class MatchesControllerTest {

    @InjectMocks
    private MatchesController matchesController;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private CompleteMatchService completeMatchService;
    @Mock
    private ErrorHandler errorHandler;

    @Test
    public void testThrowMatchSearchException() throws MatchSearchException, ServletException, IOException {
        int size = 8;
        when(request.getParameter("page")).thenReturn("2");
        when(request.getParameter("playerName")).thenReturn("");
        when(completeMatchService.getTotalMatches("")).thenReturn(30);
        doThrow(new MatchSearchException("Не удалось найти матчи")).when(completeMatchService).getMatches(2, size, "");
        matchesController.doGet(request, response);
        verify(errorHandler).sendError(request, response, "Не удалось найти матчи", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void pageParameterIsNotInt() throws ServletException, IOException {
        when(request.getParameter("page")).thenReturn("La");
        when(request.getParameter("playerName")).thenReturn("");
        matchesController.doGet(request, response);
        verify(errorHandler).sendError(request, response, "Страница не существует", HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    public void testTotalMatchesEqualZero() throws ServletException, IOException {
        when(request.getParameter("page")).thenReturn("2");
        when(request.getParameter("playerName")).thenReturn("");
        when(completeMatchService.getTotalMatches("")).thenReturn(0);
        when(request.getRequestDispatcher("/WEB-INF/views/matches.jsp")).thenReturn(dispatcher);
        matchesController.doGet(request, response);
        verify(request).setAttribute("matches", Collections.emptyList());
        verify(request).setAttribute("totalPages", 1);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testNotValidPage() throws ServletException, IOException {
        when(request.getParameter("page")).thenReturn("100");
        when(request.getParameter("playerName")).thenReturn("");
        when(completeMatchService.getTotalMatches("")).thenReturn(10);
        matchesController.doGet(request, response);
        verify(errorHandler).sendError(request, response, "Страница не существует", HttpServletResponse.SC_NOT_FOUND);
    }

}

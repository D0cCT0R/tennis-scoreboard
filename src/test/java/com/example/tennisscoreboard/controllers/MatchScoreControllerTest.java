package com.example.tennisscoreboard.controllers;

import static org.mockito.Mockito.*;

import com.example.tennisscoreboard.exception.MatchSaveException;
import com.example.tennisscoreboard.exception.PlayerCreateException;
import com.example.tennisscoreboard.exception.PlayerSearchException;
import com.example.tennisscoreboard.models.domain.CurrentMatch;
import com.example.tennisscoreboard.services.CompleteMatchService;
import com.example.tennisscoreboard.services.MatchScoreCalculationService;
import com.example.tennisscoreboard.services.OngoingMatchesService;
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
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.io.IOException;
import java.util.UUID;

@PrepareForTest(ErrorHandler.class)
@ExtendWith(MockitoExtension.class)
public class MatchScoreControllerTest {
    @InjectMocks
    private MatchScoreController controller;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private ErrorHandler errorHandler;
    @Mock
    private OngoingMatchesService ongoingMatchesService;
    @Mock
    private CompleteMatchService completeMatchService;
    @Mock
    private MatchScoreCalculationService calculationService;

    @Test
    public void getTestUUIDIsEmpty() throws IOException, ServletException {
        when(request.getParameter("uuid")).thenReturn(null);
        controller.doGet(request, response);
        verify(errorHandler).sendError(request, response, "UUID не указан", HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    public void getTestNotValidUUID() throws ServletException, IOException {
        when(request.getParameter("uuid")).thenReturn("1323f13fff2");
        controller.doGet(request, response);
        verify(errorHandler).sendError(request, response, "Некорректный формат UUID", HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    public void getTestCurrentMatchIsNull() throws ServletException, IOException {
        when(request.getParameter("uuid")).thenReturn(UUID.randomUUID().toString());
        controller.doGet(request, response);
        verify(errorHandler).sendError(request, response, "Матч не найден", HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    public void getCorrectWork() throws Exception {
        UUID uuid = UUID.randomUUID();
        CurrentMatch match = mock(CurrentMatch.class);
        when(request.getParameter("uuid")).thenReturn(uuid.toString());
        when(ongoingMatchesService.getMatch(uuid)).thenReturn(match);
        when(request.getRequestDispatcher("/WEB-INF/views/match_score.jsp")).thenReturn(dispatcher);
        controller.doGet(request, response);
        verify(request).setAttribute("match", match);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void postTestUUIDIsEmpty() throws IOException, ServletException {
        when(request.getParameter("uuid")).thenReturn(null);
        controller.doPost(request, response);
        verify(errorHandler).sendError(request, response, "UUID не указан", HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    public void postTestNotValidUUID() throws ServletException, IOException {
        when(request.getParameter("uuid")).thenReturn("1323f13fff2");
        controller.doPost(request, response);
        verify(errorHandler).sendError(request, response, "Некорректный формат UUID", HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    public void postTestWinnerIsEmpty() throws ServletException, IOException {
        UUID uuid = UUID.randomUUID();
        when(request.getParameter("uuid")).thenReturn(uuid.toString());
        when(request.getParameter("winner")).thenReturn(null);
        controller.doPost(request, response);
        verify(errorHandler).sendError(request, response, "Не указан победитель", HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    public void postTestCurrentMatchIsNull() throws ServletException, IOException {
        when(request.getParameter("uuid")).thenReturn(UUID.randomUUID().toString());
        when(request.getParameter("winner")).thenReturn("player1");
        controller.doPost(request, response);
        verify(errorHandler).sendError(request, response, "Матч не найден", HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    public void postCorrectWork() throws ServletException, IOException {
        UUID uuid = UUID.randomUUID();
        String winner = "player1";
        when(request.getParameter("uuid")).thenReturn(uuid.toString());
        when(request.getParameter("winner")).thenReturn(winner);
        CurrentMatch currentMatch = mock(CurrentMatch.class);
        when(ongoingMatchesService.getMatch(uuid)).thenReturn(currentMatch);
        controller.doPost(request, response);
        verify(calculationService).calculate(eq(currentMatch), eq(winner));
        verify(response).sendRedirect(request.getContextPath() + "/match-score?uuid=" + uuid);
    }

    @Test
    public void postWorkMatchCompleteWithMatchSaveException() throws ServletException, IOException, MatchSaveException, PlayerSearchException, PlayerCreateException {
        UUID uuid = UUID.randomUUID();
        String winner = "player1";
        when(request.getParameter("uuid")).thenReturn(uuid.toString());
        when(request.getParameter("winner")).thenReturn(winner);
        CurrentMatch currentMatch = mock(CurrentMatch.class);
        when(currentMatch.isComplete()).thenReturn(true);
        when(ongoingMatchesService.getMatch(uuid)).thenReturn(currentMatch);
        doThrow(new MatchSaveException("Не удалось добавить матч")).when(completeMatchService).saveMatch(currentMatch);
        controller.doPost(request, response);
        verify(calculationService).calculate(eq(currentMatch), eq(winner));
        verify(errorHandler).sendError(eq(request), eq(response), eq("Не удалось добавить матч"), eq(HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
    }

    @Test
    public void postWorkMatchCompleteWithPlayerSearchException() throws ServletException, IOException, MatchSaveException, PlayerSearchException, PlayerCreateException {
        UUID uuid = UUID.randomUUID();
        String winner = "player1";
        when(request.getParameter("uuid")).thenReturn(uuid.toString());
        when(request.getParameter("winner")).thenReturn(winner);
        CurrentMatch currentMatch = mock(CurrentMatch.class);
        when(currentMatch.isComplete()).thenReturn(true);
        when(ongoingMatchesService.getMatch(uuid)).thenReturn(currentMatch);
        doThrow(new PlayerSearchException("Не удалось найти игрока")).when(completeMatchService).saveMatch(currentMatch);
        controller.doPost(request, response);
        verify(calculationService).calculate(eq(currentMatch), eq(winner));
        verify(errorHandler).sendError(eq(request), eq(response), eq("Не удалось найти игрока"), eq(HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
    }

    @Test
    public void postWorkMatchCompleteWithPlayerCreateException() throws ServletException, IOException, MatchSaveException, PlayerSearchException, PlayerCreateException {
        UUID uuid = UUID.randomUUID();
        String winner = "player1";
        when(request.getParameter("uuid")).thenReturn(uuid.toString());
        when(request.getParameter("winner")).thenReturn(winner);
        CurrentMatch currentMatch = mock(CurrentMatch.class);
        when(currentMatch.isComplete()).thenReturn(true);
        when(ongoingMatchesService.getMatch(uuid)).thenReturn(currentMatch);
        doThrow(new PlayerCreateException("Не удалось создать игрока")).when(completeMatchService).saveMatch(currentMatch);
        controller.doPost(request, response);
        verify(calculationService).calculate(eq(currentMatch), eq(winner));
        verify(errorHandler).sendError(eq(request), eq(response), eq("Не удалось создать игрока"), eq(HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
    }

    @Test
    public void postCorrectWorkCompleteMatch() throws ServletException, IOException, MatchSaveException, PlayerSearchException, PlayerCreateException {
        UUID uuid = UUID.randomUUID();
        String winner = "player1";
        when(request.getParameter("uuid")).thenReturn(uuid.toString());
        when(request.getParameter("winner")).thenReturn(winner);
        CurrentMatch currentMatch = mock(CurrentMatch.class);
        when(currentMatch.isComplete()).thenReturn(true);
        when(ongoingMatchesService.getMatch(uuid)).thenReturn(currentMatch);
        controller.doPost(request, response);
        verify(calculationService).calculate(eq(currentMatch), eq(winner));
        verify(completeMatchService).saveMatch(currentMatch);
        verify(response).sendRedirect(request.getContextPath() + "/new-match");
        verify(ongoingMatchesService).removeMatch(uuid);
    }


}

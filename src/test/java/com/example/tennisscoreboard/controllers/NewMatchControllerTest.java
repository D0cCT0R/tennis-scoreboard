package com.example.tennisscoreboard.controllers;
import static org.mockito.Mockito.*;
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

@ExtendWith(MockitoExtension.class)
public class NewMatchControllerTest {
    @InjectMocks
    private NewMatchController newMatchController;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;

    @Test
    public void testBlankPlayerNamesParameters() throws ServletException, IOException {
        when(request.getParameter("player1")).thenReturn("");
        when(request.getParameter("player2")).thenReturn(" ");
        when(request.getRequestDispatcher("/WEB-INF/views/new_match.jsp")).thenReturn(dispatcher);
        newMatchController.doPost(request, response);
        verify(request).setAttribute("error", "Вы не ввели имя");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testNamesContainsInvalidCharacters() throws ServletException, IOException {
        when(request.getParameter("player1")).thenReturn("Мария123");
        when(request.getParameter("player2")).thenReturn("Vlad@");
        when(request.getRequestDispatcher("/WEB-INF/views/new_match.jsp")).thenReturn(dispatcher);
        newMatchController.doPost(request, response);
        verify(request).setAttribute("error", "Имена могут состоять только из букв");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testSameNames() throws ServletException, IOException {
        when(request.getParameter("player1")).thenReturn("Анна Ф.");
        when(request.getParameter("player2")).thenReturn("Анна Ф.");
        when(request.getRequestDispatcher("/WEB-INF/views/new_match.jsp")).thenReturn(dispatcher);
        newMatchController.doPost(request, response);
        verify(request).setAttribute("error", "Имена не должны совпадать");
        verify(dispatcher).forward(request, response);
    }
}

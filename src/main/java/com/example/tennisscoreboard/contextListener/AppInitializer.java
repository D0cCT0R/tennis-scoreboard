package com.example.tennisscoreboard.contextListener;


import com.example.tennisscoreboard.dao.MatchesDao;
import com.example.tennisscoreboard.dao.PlayersDao;
import com.example.tennisscoreboard.services.CompleteMatchService;
import com.example.tennisscoreboard.services.MatchScoreCalculationService;
import com.example.tennisscoreboard.services.OngoingMatchesService;
import com.example.tennisscoreboard.storage.CurrentMatchStorage;
import com.example.tennisscoreboard.util.ErrorHandler;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class AppInitializer implements ServletContextListener {
    private ScheduledExecutorService executorService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(CurrentMatchStorage::cleanUpOldMatches, 0, 5, TimeUnit.MINUTES);
        PlayersDao playersDao = new PlayersDao();
        MatchesDao matchesDao = new MatchesDao();
        ErrorHandler errorHandler = new ErrorHandler();
        OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        CompleteMatchService completeMatchService = new CompleteMatchService(playersDao, matchesDao);
        sce.getServletContext().setAttribute("calculationService", matchScoreCalculationService);
        sce.getServletContext().setAttribute("completeMatchService", completeMatchService);
        sce.getServletContext().setAttribute("errorHandler", errorHandler);
        sce.getServletContext().setAttribute("ongoingMatchesService", ongoingMatchesService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (executorService != null) {
            executorService.shutdownNow();
            System.out.println("Scheduled cleanup task stopped");
        }
    }
}

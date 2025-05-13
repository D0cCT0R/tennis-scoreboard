package com.example.tennisscoreboard.contextListener;


import com.example.tennisscoreboard.storage.CurrentMatchStorage;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class BackgroundTaskInitializer implements ServletContextListener {
    private ScheduledExecutorService executorService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(CurrentMatchStorage::cleanUpOldMatches, 0, 5, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (executorService != null) {
            executorService.shutdownNow();
            System.out.println("Scheduled cleanup task stopped");
        }
    }
}

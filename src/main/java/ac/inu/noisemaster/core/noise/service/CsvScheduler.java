package ac.inu.noisemaster.core.noise.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
public class CsvScheduler {
    private final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    private final Trigger cronPeriod;
    private final Runnable runnable;

    public CsvScheduler(final Trigger cronPeriod, final Runnable runnable) {
        this.cronPeriod = cronPeriod;
        this.runnable = runnable;
        this.scheduler.setWaitForTasksToCompleteOnShutdown(true);
        this.scheduler.initialize();
    }

    @PostConstruct
    public void start() {
        log.info("csv scheduler start");
        this.scheduler.schedule(runnable, cronPeriod);
    }

    @PreDestroy
    public void end() {
        this.scheduler.shutdown();
        log.info("csv scheduler shutdown");
    }
}

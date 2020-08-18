package ac.inu.noisemaster.core.noise.config;

import ac.inu.noisemaster.core.noise.service.CsvScheduler;
import ac.inu.noisemaster.core.noise.service.CsvService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.support.CronTrigger;

import java.util.TimeZone;

@Configuration
public class CsvSchedulerConfiguration {

    @Bean
    public CsvScheduler csvScheduler(CsvService csvService) {
        return new CsvScheduler(
                new CronTrigger("0 0 0 */7 * ?", TimeZone.getTimeZone("Asia/Seoul")),
                csvService::exportNoise);
    }
}

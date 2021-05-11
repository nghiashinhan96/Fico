package vn.com.tpf.microservices.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@EnableScheduling
public class DataentryCronService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitMQService rabbitMQService;

    @Scheduled(cron = "${spring.cron.dataentrySyncStatusIH}")
    public void job_dataentrySyncStatusIH()
    {
        String slog = "func: job_dataentrySyncStatusIH";
        try{
            rabbitMQService.send("tpf-service-dataentry",
                    Map.of("func", "jobUpdateStatusIH","reference_id", UUID.randomUUID().toString()));
        }catch (Exception e)
        {
            slog += " - exception: " + e.toString();

        }finally {
            log.info("{}", slog);
        }
    }
}
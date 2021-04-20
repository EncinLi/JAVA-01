package encin.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Encin.Li
 * @create 2021-04-20
 */
@Component
@EnableScheduling
public class KafkaProducer {
    private KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<?, ?> kafkaTemplate) {
        this.kafkaTemplate = (KafkaTemplate<String, Object>) kafkaTemplate;
    }

    @Scheduled(cron = "0/3 * * * * ?")
    private void sendMessage() {
        kafkaTemplate.send("encinTest1", "helloworld");
    }
}

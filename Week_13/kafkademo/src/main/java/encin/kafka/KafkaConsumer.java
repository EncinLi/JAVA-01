package encin.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Encin.Li
 * @create 2021-04-20
 */
@Component
public class KafkaConsumer {
    @KafkaListener(topics = {"encinTest1"})
    public void onMessage(ConsumerRecord<?, ?> record) {
        System.out.println("topic：" + record.topic() + " # partition：" + record.partition() + " # value：" + record.value());
    }
}

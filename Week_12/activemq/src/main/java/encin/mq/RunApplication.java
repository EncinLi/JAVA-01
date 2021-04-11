package encin.mq;

import encin.mq.client.ActiveMqClient;
import encin.mq.client.QueueClient;

/**
 * @author Encin.Li
 * @create 2021-04-12
 */
public class RunApplication {
    private static ActiveMqClient activeMqClient;
    private static QueueClient queueClient;
    private static String queueName = "test-sender1";

    static {
        activeMqClient = new ActiveMqClient
                .Builder()
                .setUrl("tcp://localhost:61616")
                .build();
        queueClient = activeMqClient.getQueueClient();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread putThread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                putMessage();
            }
        });

        Thread getThread = new Thread(() -> {
            getMessage();
        });
        System.out.println("开始发送消息");
        putThread.start();
        System.out.println("开始接收消息");
        getThread.start();

        while (putThread.isAlive()) {

        }

        activeMqClient.disConnect();
    }

    public static void putMessage() {
        queueClient.createQueue(queueName);
        queueClient.sendMessage(queueName, "Hi,test1 , I send a message at " + System.currentTimeMillis());
    }

    public static void getMessage() {
        queueClient.getMessage(queueName);
    }
}

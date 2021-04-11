package encin.mq.client;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQQueue;

/**
 * @author Encin.Li
 * @create 2021-04-12
 */
public class QueueClient {

    ActiveMqClient activeMqClient;

    public QueueClient(ActiveMqClient activeMqClient) {
        this.activeMqClient = activeMqClient;
    }

    public void createQueue(String queueName) {
        System.out.println("ActiveMQ Create Queue -> " + queueName);
        try {
            activeMqClient.getSession().createQueue(queueName);
        } catch (JMSException e) {
            System.out.println("创建队列 -> " + queueName + "失败!");
        }
    }

    public void sendMessage(String queueName, String message) {
        try {
            TextMessage textMessage = activeMqClient.getSession().createTextMessage(message);
            activeMqClient.getSession().createProducer(new ActiveMQQueue(queueName)).send(textMessage);
        } catch (JMSException e) {
            System.out.println("发送消息->" + message + "失败");
        }
    }

    public void getMessage(String queueName) {
        try {
            activeMqClient.getSession().createConsumer(new ActiveMQQueue(queueName)).setMessageListener(new Listener());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

package encin.mq.client;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTopic;

/**
 * @author Encin.Li
 * @create 2021-04-12
 */
public class TopicClient {

    private ActiveMqClient activeMqClient;

    public TopicClient(ActiveMqClient activeMqClient) {
        this.activeMqClient = activeMqClient;
    }

    public void createTopic(String topicName) {
        try {
            activeMqClient.getSession().createTopic(topicName);
        } catch (JMSException e) {
            System.out.println("创建主题 -> " + topicName + "失败!");
            e.printStackTrace();
        }
    }

    public void getMessage(String topicName) {
        try {
            MessageConsumer messageConsumer = activeMqClient.getSession().createConsumer(new ActiveMQTopic(topicName));
            messageConsumer.setMessageListener(new Listener());
        } catch (JMSException e) {
            System.out.println("创建主题 -> " + topicName + "失败!");
            e.printStackTrace();
        }
    }

    public void putMessage(String topicName, String message) {
        try {
            TextMessage textMessage = activeMqClient.getSession().createTextMessage(message);
            activeMqClient.getSession().createProducer(new ActiveMQTopic(topicName)).send(textMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

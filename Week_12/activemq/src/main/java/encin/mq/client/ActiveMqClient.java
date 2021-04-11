package encin.mq.client;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQMessage;

/**
 * @author Encin.Li
 * @create 2021-04-12
 */
public class ActiveMqClient {

    private String user;
    private String password;
    private String url;
    private Boolean transaction;
    private QueueClient queueClient;
    private TopicClient topicClient;

    private Connection connection = null;
    private Session session = null;

    public ActiveMqClient(Builder builder) {
        user = builder.user;
        password = builder.password;
        url = builder.url;
        transaction = builder.transaction;
    }

    public Session getSession() {
        return session;
    }

    public QueueClient getQueueClient() {
        return queueClient;
    }

    public TopicClient getTopicClient() {
        return topicClient;
    }

    public ActiveMqClient connection() {
        System.out.println("ActiveMQ Connecting...");
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException e) {
                System.out.println("连接已经存在,关闭连接后重新连接. " + e.getMessage());
            }
        }
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(transaction, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            System.out.println("建立连接失败请检查. " + e.getMessage());
        }
        System.out.println("ActiveMQ Connect Success...");
        queueClient = new QueueClient(this);
        topicClient = new TopicClient(this);
        return this;
    }

    public void createTopic(String topicName) {
        try {
            session.createTopic(topicName);
        } catch (JMSException e) {
            System.out.println("创建Topic -> " + topicName + "失败!");
        }
    }

    public void disConnect() {
        System.out.println("ActiveMQ Disconnecting...");
        try {
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        System.out.println("ActiveMQ Disconnect Success...");
    }

    public Boolean sendMessage(String queueName, ActiveMQMessage activeMQMessage) {
        return null;
    }

    public static class Builder {
        private static final String DEFAULT_USER = "admin";
        private static final String DEFAULT_PASSWORD = "admin";
        private static final String DEFAULT_URL = "tcp://localhost:61616";
        private static final Boolean DEFAULT_TRANSACTION = false;

        private String user = DEFAULT_USER;
        private String password = DEFAULT_PASSWORD;
        private String url = DEFAULT_URL;
        private Boolean transaction = DEFAULT_TRANSACTION;

        public Builder setUser(String user) {
            this.user = user;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setTransaction(Boolean transaction) {
            this.transaction = transaction;
            return this;
        }

        public ActiveMqClient build() {
            return new ActiveMqClient(this).connection();
        }
    }
}

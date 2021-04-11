package encin.mq.client;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author Encin.Li
 * @create 2021-04-12
 */
public class Listener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println(System.currentTimeMillis() + "Get Message -> " + ((TextMessage) message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

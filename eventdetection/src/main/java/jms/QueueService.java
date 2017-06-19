package jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.jms.*;

@ApplicationScoped
public class QueueService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueService.class);

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "java:/jms/queue/parking")
    private Queue queue;

    public void sendMessage(String message) {
        Connection connection = null;

        try {
            connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer publisher = session.createProducer(queue);

            connection.start();

            TextMessage textMessage = session.createTextMessage(message);

            publisher.send(textMessage);

        } catch (JMSException e) {
            LOGGER.error("Error: ", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    LOGGER.error("Error: ", e);
                }
            }
        }
    }
}

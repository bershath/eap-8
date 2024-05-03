package org.bershath.labs.jms.mdb;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import org.jboss.logging.Logger;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "/jms/queue/A"),
                @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
                @ActivationConfigProperty(propertyName = "user", propertyValue = "admin"),
                @ActivationConfigProperty(propertyName = "password", propertyValue = "jboss10)")
        }
)
public class SimpleQueueListener implements MessageListener {

    private static Logger log = Logger.getLogger(SimpleQueueListener.class);

    /**
     * @param message
     */
    @Override
    public void onMessage(Message message) {
        try {
            if(message instanceof TextMessage)
                log.info(message.getBody(String.class));
            else
                log.info("message with the id" + message.getJMSMessageID() + "received");
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}

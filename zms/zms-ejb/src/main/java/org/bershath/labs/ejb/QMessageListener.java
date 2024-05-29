package org.bershath.labs.ejb;

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
public class QMessageListener implements MessageListener {

    private static Logger log = Logger.getLogger(QMessageListener.class);


    /**
     * Passes a message to the listener.
     *
     * @param message the message passed to the listener
     */
    @Override
    public void onMessage(Message message) {
        try {
            if(message instanceof TextMessage)
                log.info(message.getBody(String.class));
            else
                log.info("Message received with ID: " + message.getJMSMessageID());
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}

package org.bershath.labs.ejb;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.EJB;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import org.bershath.labs.data.controller.MessageDataController;
import org.jboss.logging.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    final DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z");

    @EJB
    MessageDataController messageDataController;

    @EJB
    MessageSenderTwo messageSenderTwo;


    @Override
    public void onMessage(Message message) {
        try {
            if(message instanceof TextMessage) {
                log.info(message.getBody(String.class));
                messageDataController.storeJMSData(message,new Date());
                messageSenderTwo.sendMessage(message.getBody(String.class));
            }
            else
                log.info("Message received with ID: " + message.getJMSMessageID());
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}

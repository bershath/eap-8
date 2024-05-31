package org.bershath.labs.data.service;

import jakarta.jms.Message;
import org.bershath.labs.data.controller.MessageDataController;
import org.jboss.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Date;

/**
 * This is no longer required. This is pointless, just created for fun to remember the good old days of
 * accessing EJBs from a client code. :-D
 */

public class MessageDataService {

    private static Logger log = Logger.getLogger(MessageDataService.class);

    public MessageDataService(){}

    public void addMessage(Message message, Date date){
        InitialContext initialContext = null;
        try {
            initialContext = new InitialContext();
            MessageDataController messageDataController = (MessageDataController) initialContext.lookup("java:global/zms-ear-1.0-SNAPSHOT/zms-ejb/MessageDataController");
            messageDataController.storeJMSData(message,date);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(initialContext != null)
                    initialContext.close();
            } catch (NamingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

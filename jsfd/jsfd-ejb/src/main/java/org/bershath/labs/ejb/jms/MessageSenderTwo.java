package org.bershath.labs.ejb.jms;
/**
 * This class is not necessary, I could simply use the MessageSender class.
 * This is used to demonstrate multiple resource enlistment in an XA transaction.
 * */

import jakarta.annotation.Resource;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MessageSenderTwo {

    public  MessageSenderTwo(){}

    @Inject
    private JMSContext jmsContext;

    @Resource(name = "java:/jms/queue/K")
    private Queue queue;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void sendMessage(String message) {
        try{
            jmsContext.createProducer().send(queue,message);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}

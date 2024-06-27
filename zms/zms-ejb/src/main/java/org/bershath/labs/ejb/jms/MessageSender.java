package org.bershath.labs.ejb.jms;

import jakarta.annotation.Resource;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MessageSender {
    public MessageSender() {
    }

    /*
    It is not required to close the resource, JEE implementation takes care of it.
     */
    @Inject
    private JMSContext jmsContext;

    @Resource(name = "java:/jms/queue/A")
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

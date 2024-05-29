package org.bershath.labs.ejb;

import jakarta.annotation.Resource;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;

@Stateless(mappedName = "MessageSender")
@LocalBean
public class MessageSender {
    public MessageSender() {
    }

    /*
    It is not required to close the resource, JEE implementation takes care of it.
     */
    @Inject
    private JMSContext jmsContext;

    @Resource(mappedName = "java:/jms/queue/A")
    private Queue queue;

    public void sendMessage(String message) {
        try{
            jmsContext.createProducer().send(queue,message);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}

package org.bershath.labs.jms;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.JMSProducer;
import jakarta.jms.Queue;
import jakarta.jms.TextMessage;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueSender {

    private static final String queueName = "A";
    private static final String connectionFactoryName = "jms/RemoteConnectionFactory";
    private static final int numOfMessages = 2;
    private final String jmsUser = "admin";
    private final String jmsPassword = "jboss10)";

    public static void main(String[] args){
        QueueSender queueSender = new QueueSender();
        queueSender.sendMessage(connectionFactoryName);
    }

    public void sendMessage(String connectionFactoryName){
        try(JMSContext jmsContext = getConnectionFactory(connectionFactoryName).createContext(jmsUser,jmsPassword)){
            Queue queue = jmsContext.createQueue(queueName);
            TextMessage textMessage = jmsContext.createTextMessage();
            for(int i = 1; i<= numOfMessages; i++){
                textMessage.setText("TXTMSG " + i);
                JMSProducer jmsProducer = jmsContext.createProducer();
                jmsProducer.send(queue,textMessage);
            }
        } catch (NamingException | JMSException e) {
            e.printStackTrace();
        }
    }

    private ConnectionFactory getConnectionFactory(String connectionFactoryName) throws NamingException{
        Context context = new InitialContext();
        return (ConnectionFactory) context.lookup(connectionFactoryName);
    }
}

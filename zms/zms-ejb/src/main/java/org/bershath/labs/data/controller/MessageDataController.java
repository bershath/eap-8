package org.bershath.labs.data.controller;

import jakarta.ejb.*;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.bershath.labs.data.dto.MessageData;
import org.jboss.logging.Logger;

import java.util.Date;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MessageDataController  {

    @PersistenceContext(name="primary")
    protected EntityManager em;

    private static final Logger log = Logger.getLogger(MessageDataController.class);

    MessageDataController(){}

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void storeJMSData(String messageId, String bodyText, Date date){
        MessageData messageData = new MessageData();
        messageData.setMessageId(messageId);
        messageData.setMessageBody(bodyText);
        messageData.setDate(date);
        try{
            em.persist(messageData);
            log.info("Inserted a record. Key of the record " + messageId);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void storeJMSData(Message message,Date date){
        try{
            if (message instanceof TextMessage) {
                MessageData messageData = new MessageData();
                messageData.setMessageId(message.getJMSMessageID());
                messageData.setMessageBody(message.getBody(String.class));
                messageData.setDate(date);
                em.persist(messageData);
                log.info("Inserted a record. Key of the record " + message.getJMSMessageID());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void removeJMSData(String messageId) throws Exception{
        try {
            MessageData messageData = em.find(MessageData.class, messageId);
            if (messageData != null) {
                em.remove(messageData);
                log.info("Removed record by the key " + messageId);
            } else {
                log.error("No record found by the key " + messageId);
                throw new Exception("Record not found");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public long getMsgRecordCount(){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(MessageData.class)));
        return em.createQuery(criteriaQuery).getSingleResult();
    }
}

package org.bershath.labs.ejb.data;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.bershath.labs.ejb.jms.MessageSender;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Named
@RequestScoped
@Entity
@Table( name = "msg_data" )
public class MessageData implements Serializable {

    @EJB
    MessageSender messageSender;

    @Serial
    private static final long serialVersionUID = 6180090992777219828L;
    private String messageId;
    private String messageBody;
    private Date date;

    public MessageData(){}

    public MessageData(MessageSender messageSender, String messageId, String messageBody, Date date) {
        this.messageSender = messageSender;
        this.messageId = messageId;
        this.messageBody = messageBody;
        this.date = date;
    }

    @Id
    @Column( name = "msg_id" )
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Column( name = "msg_body" )
    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    @Column(name = "msg_date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageData that = (MessageData) o;
        return Objects.equals(messageId, that.messageId) && Objects.equals(messageBody, that.messageBody) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, messageBody, date);
    }


    public void sendMessage(){
        messageSender.sendMessage(messageBody);
        messageBody = "Hey, " + messageBody;
    }

    public void clear(){
        MessageData messageData = new MessageData(null,null,null,null);
    }


}

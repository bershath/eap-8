package org.bershath.labs.data.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table( name = "msg_data" )
public class MessageData implements Serializable {

    @Serial
    private static final long serialVersionUID = -8049657301114249138L;

    private String messageId;
    private String messageBody;
    private Date date;

    public MessageData() {
        super();
    }

    @Id
    @Column( name = "msg_id" )
    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Column( name = "msg_body" )
    public String getMessageBody() {
        return this.messageBody;
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
}

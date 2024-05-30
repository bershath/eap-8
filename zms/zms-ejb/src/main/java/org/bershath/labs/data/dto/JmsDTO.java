package org.bershath.labs.data.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table( name = "msg_data" )
public class JmsDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -8049657301114249138L;

    private String messageId;
    private String messageBody;

    public JmsDTO() {
        super();
    }

    @Id
    @Column( name = "message_id" )
    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Column( name = "message_body" )
    public String getMessageBody() {
        return this.messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JmsDTO jmsDTO = (JmsDTO) o;
        return Objects.equals(messageId, jmsDTO.messageId) && Objects.equals(messageBody, jmsDTO.messageBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, messageBody);
    }
}

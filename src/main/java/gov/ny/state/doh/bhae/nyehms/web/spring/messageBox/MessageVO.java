package gov.ny.state.doh.bhae.nyehms.web.spring.domain.messageBox;

/**
 *  This class is POJO for messages of messagebox.
 * 
 * @author vxp06
 */
public class MessageVO {
   private int messageId;
   private String sender;
   private String receiver;
   private String messageBody;
   private String sentDate;
   private String isRead;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    @Override
    public String toString() {
        return "MessageVO{" + "messageId=" + messageId + ", sender=" + sender + ", receiver=" + receiver + ", messageBody=" + messageBody + ", sentDate=" + sentDate + ", isRead=" + isRead + '}';
    }


   
}

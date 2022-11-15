package visa.vttp.csf.smackBE.model.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@Document
public class ChatMessage {
    public String chatId;
    public String userEmail;
    public String message;
    public int creationDatetime;
    public int messageType;

    public static ChatMessage create(JsonObject obj) {
        ChatMessage cm = new ChatMessage();
        cm.setChatId(obj.getString("chatId"));
        cm.setUserEmail(obj.getString("userEmail"));
        cm.setMessage(obj.getString("message"));
        cm.setMessageType(obj.getInt("messageType"));
        cm.setCreationDatetime(obj.getInt("creationDatetime"));
        return cm;
    }

    public JsonObject toJson() {
        JsonObjectBuilder jb = Json.createObjectBuilder();
        jb.add("chatId", this.chatId)
                .add("userEmail", this.userEmail)
                .add("message", this.message)
                .add("messageType", this.messageType)
                .add("creationDatetime", this.creationDatetime);
        return jb.build();
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String email) {
        this.userEmail = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getCreationDatetime() {
        return creationDatetime;
    }

    public void setCreationDatetime(int creationDatetime) {
        this.creationDatetime = creationDatetime;
    }

    @Override
    public String toString() {
        return "ChatMessage [chatId=" + chatId + ", userEmail=" + userEmail + ", message=" + message + ", creationDatetime="
                + creationDatetime + ", messageType=" + messageType + "]";
    }

}

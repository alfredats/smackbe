package visa.vttp.csf.smackBE.model.sql;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.JsonValue;

public class ChatSub {
    private String subId;
    private String chatId;
    private String userEmail;
    private int timestampJoined;
    private int timestampLeft;

    public static ChatSub create(SqlRowSet rs) {
        ChatSub cs = new ChatSub();
        cs.setSubId(rs.getString("subscription_id"));
        cs.setChatId(rs.getString("chat_id"));
        cs.setUserEmail(rs.getString("user_email"));
        cs.setTimestampJoined(rs.getInt("timestamp_joined"));
        try {
            cs.setTimestampLeft(rs.getInt("timestamp_left"));
        } catch (Exception e) {
        }
        return cs;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String email) {
        this.userEmail = email;
    }

    public int getTimestampJoined() {
        return timestampJoined;
    }

    public void setTimestampJoined(int timestampJoined) {
        this.timestampJoined = timestampJoined;
    }

    public int getTimestampLeft() {
        return timestampLeft;
    }

    public void setTimestampLeft(int timestampLeft) {
        this.timestampLeft = timestampLeft;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public JsonValue toJson() {
        return null;
    }

}

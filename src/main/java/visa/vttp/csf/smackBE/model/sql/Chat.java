package visa.vttp.csf.smackBE.model.sql;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Chat {
    private String chatId;
    private int timestampCreated;
    private String chatname;

    public static Chat create(SqlRowSet rs) {
        Chat c = new Chat();
        c.setChatId(rs.getString("chat_id"));
        c.setChatname(rs.getString("chat_name"));
        c.setTimestampCreated(rs.getInt("timestamp_created"));
        return null;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getChatname() {
        return chatname;
    }

    public void setChatname(String chatname) {
        this.chatname = chatname;
    }

    public int getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(int timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

}

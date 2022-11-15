package visa.vttp.csf.smackBE.repository.sql.queries;

public class ChatQueries {
    public static final String SQL_GET_CHATS = "SELECT * FROM chat;";
    public static final String SQL_INSERT_CHAT = "INSERT INTO chat (chat_id, chat_name) VALUES (?, ?);";
}

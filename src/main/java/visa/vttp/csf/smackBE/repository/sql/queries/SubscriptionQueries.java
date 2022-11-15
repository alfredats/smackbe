package visa.vttp.csf.smackBE.repository.sql.queries;

public class SubscriptionQueries {
    public static final String SQL_SELECT_SUBS_BY_USEREMAIL = "SELECT * FROM subscription WHERE user_email = ? AND timestamp_left IS NULL;";
    public static final String SQL_SELECT_SUB_BY_USEREMAIL_AND_CHATID = "SELECT * FROM subscription WHERE timestamp_left IS NULL AND user_email = ? AND chat_id = ? ORDER BY timestamp_joined;";

    public static final String SQL_INSERT_SUB = "INSERT INTO subscription (subscription_id, user_email, chat_id) VALUES (?,?,?);";
    public static final String SQL_INSERT_TIMESTAMPLEFT_BY_CHATID_AND_USEREMAIL = "UPDATE subscription SET timestamp_left = ? WHERE timestamp_left IS NULL AND chat_id = ? AND user_email = ?;";
}

package visa.vttp.csf.smackBE.repository.sql;

import static visa.vttp.csf.smackBE.repository.sql.queries.SubscriptionQueries.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import visa.vttp.csf.smackBE.model.sql.ChatSub;

@Repository
public class SubscriptionRepository {

    private JdbcTemplate jt;

    @Autowired
    SubscriptionRepository(JdbcTemplate jt) {
        this.jt = jt;
    }

    public ChatSub getSubscriptionByUserAndChat(String userEmail, String chatID) {
        SqlRowSet rs = jt.queryForRowSet(SQL_SELECT_SUB_BY_USEREMAIL_AND_CHATID, userEmail, chatID);
        if (!rs.next()) {
            return null;
        }
        return ChatSub.create(rs);
    }

    public List<ChatSub> getSubscriptionsByUser(String email) {
        List<ChatSub> lcs = new ArrayList<>();
        SqlRowSet rs = jt.queryForRowSet(SQL_SELECT_SUBS_BY_USEREMAIL, email);
        while (rs.next()) {
            lcs.add(ChatSub.create(rs));
        }
        return lcs;
    }

    public void createSubscription(String chatID, String userID) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        if (this.getSubscriptionByUserAndChat(userID, chatID) != null) {
            throw new RuntimeException(
                    String.format("User %s already subscribe to chat %s", userID, chatID));
        }
        final int rows = jt.update(SQL_INSERT_SUB, uuid, userID, chatID);
        if (rows != 1) {
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(SQL_INSERT_SUB, 1, rows);
        }
    }

    public void updateSubscription(String chatID, String userID) {
        final int rows = jt.update(SQL_INSERT_TIMESTAMPLEFT_BY_CHATID_AND_USEREMAIL,
                System.currentTimeMillis() / 1000,
                chatID,
                userID);
        if (rows != 1) {
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(SQL_INSERT_TIMESTAMPLEFT_BY_CHATID_AND_USEREMAIL,
                    1, rows);
        }
    }

}

package visa.vttp.csf.smackBE.repository.sql;

import static visa.vttp.csf.smackBE.repository.sql.queries.ChatQueries.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import visa.vttp.csf.smackBE.model.sql.Chat;

/* NOT FULLY IMPLEMENTED.
 * PRESENTATION VERSION DOESN'T INCLUDE ABILITY TO CREATE CHATS
 */
@Repository
public class ChatRepository {

    private JdbcTemplate jt;

    @Autowired
    ChatRepository(JdbcTemplate jt) {
        this.jt = jt;
    }

    public String createChat(Chat chat) {
        final String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        chat.setChatId(uuid);
        final int rows = jt.update(SQL_INSERT_CHAT, chat.getChatId(), chat.getChatname());
        if (rows != 1) {
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(SQL_INSERT_CHAT, 1, rows);
        }
        return uuid; // return the uuid
    }

    public List<Chat> getAllChats() {
        List<Chat> lc = new ArrayList<>();
        SqlRowSet rs = jt.queryForRowSet(SQL_GET_CHATS);
        while (rs.next()) {
            lc.add(Chat.create(rs));
        }
        return lc;
    }

}

package visa.vttp.csf.smackBE.repository.sql;

import static visa.vttp.csf.smackBE.repository.sql.queries.UserQueries.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;
import visa.vttp.csf.smackBE.model.sql.User;

@Repository
public class UserRepository {

    private JdbcTemplate jt;

    @Autowired
    UserRepository(JdbcTemplate jt) {
        this.jt = jt;
    }

    public void createUser(JsonObject obj) {
        User u = new User();
        u.setEmail(obj.getString("email"));
        u.setDisplayName(obj.getString("displayname"));
        this.createUser(u);
    }

    private void createUser(User user) {
        final int rows = jt.update(SQL_CREATE_USER, user.getDisplayName(), user.getEmail());
        if (rows != 1) {
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(SQL_CREATE_USER, 1, rows);
        }
    }

    public User getUserByEmail(String email) {
        SqlRowSet rs = jt.queryForRowSet(SQL_GET_USER_BY_EMAIL, email);
        if (!rs.next()) {
            throw new DataAccessException(String.format("No such user with email %s found", email)) {
            };
        }
        return User.create(rs);
    }

}

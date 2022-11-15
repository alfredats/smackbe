package visa.vttp.csf.smackBE.repository.sql;

import static visa.vttp.csf.smackBE.repository.sql.queries.AuthQueries.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;
import visa.vttp.csf.smackBE.model.sql.UserAuth;

@Repository
public class AuthRepository {

    private JdbcTemplate jt;
    private PasswordEncoder pwe;

    @Autowired
    AuthRepository(JdbcTemplate jt) {
        this.jt = jt;
        this.pwe = new BCryptPasswordEncoder();
    }

    public void createAuth(JsonObject obj) {
        UserAuth ua = new UserAuth();
        ua.setAUTH_ID(obj.getString("uuid"));
        ua.setEmail(obj.getString("email"));
        String encodedPassword = pwe.encode(obj.getString("password"));
        ua.setPassword(encodedPassword);
        this.createAuth(ua);
    }

    private void createAuth(UserAuth ua) {
        final int rows = jt.update(SQL_CREATE_AUTH, ua.getAUTH_ID(), ua.getEmail(), ua.getPassword());
        if (rows != 1) {
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(SQL_CREATE_AUTH, 1, rows);
        }
    }

    public UserAuth getAuthByEmail(String email) {
        SqlRowSet rs = jt.queryForRowSet(SQL_GET_AUTH_BY_EMAIL, email);
        if (!rs.next()) {
            throw new DataAccessException("No matching record found") {
            };
        }
        return UserAuth.create(rs);
    }

}

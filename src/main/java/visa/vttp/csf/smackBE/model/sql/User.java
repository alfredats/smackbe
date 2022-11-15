package visa.vttp.csf.smackBE.model.sql;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class User {
    private String displayName;
    private String email;
    private int TS_CREATED;

    public static User create(SqlRowSet rs) {
        User u = new User();
        u.setEmail(rs.getString("user_email"));
        u.setDisplayName(rs.getString("displayname"));
        u.setTS_CREATED(rs.getInt("timestamp_created"));
        return u;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTS_CREATED() {
        return TS_CREATED;
    }

    public void setTS_CREATED(int tS_CREATED) {
        TS_CREATED = tS_CREATED;
    }

}

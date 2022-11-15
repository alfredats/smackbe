package visa.vttp.csf.smackBE.model.sql;

import java.util.Collection;
import java.util.Collections;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserAuth implements UserDetails {
    private String AUTH_ID;
    private int TS_CREATED;
    private String email;
    private String password;

    public static UserAuth create(SqlRowSet rs) {
        UserAuth ua = new UserAuth();
        ua.setAUTH_ID(rs.getString("auth_id"));
        ua.setEmail(rs.getString("auth_email"));
        ua.setTS_CREATED(rs.getInt("timestamp_created"));
        byte[] binpw = (byte[]) rs.getObject("auth_password");
        ua.setPassword(new String(binpw));
        return ua;
    }

    @Override
    public String toString() {
        return "UserAuth [AUTH_ID=" + AUTH_ID + ", TS_CREATED=" + TS_CREATED + ", email=" + email + ", password="
                + password + "]";
    }

    public String getAUTH_ID() {
        return AUTH_ID;
    }

    public void setAUTH_ID(String aUTH_ID) {
        AUTH_ID = aUTH_ID;
    }

    public int getTS_CREATED() {
        return TS_CREATED;
    }

    public void setTS_CREATED(int tS_CREATED) {
        TS_CREATED = tS_CREATED;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String pw) {
        this.password = pw;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

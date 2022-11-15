package visa.vttp.csf.smackBE.repository.sql.queries;

public class AuthQueries {
    public static final String SQL_CREATE_AUTH = """
            INSERT INTO auth (
                auth_id,
                auth_email,
                auth_password
            ) VALUES (?, ?, ?);
                """;

    public static final String SQL_GET_AUTH_BY_EMAIL = """
            SELECT *
            FROM auth
            WHERE auth_email = ?
            ORDER BY timestamp_created;
                """;
}

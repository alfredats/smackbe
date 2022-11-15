package visa.vttp.csf.smackBE.repository.sql.queries;

public class UserQueries {

    public static String SQL_GET_USER_BY_EMAIL = "SELECT * FROM user WHERE user_email = ?;";
    public static String SQL_CREATE_USER = """
            INSERT INTO user (
                displayname,
                user_email
            ) VALUES (?, ?);
                """;
}

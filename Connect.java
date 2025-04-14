import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class Connect {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/final";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "viktor";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    public static boolean loginUser(String email, String password) {
        final String sql = "SELECT password_hash FROM users WHERE email = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    String hashed = result.getString("password_hash");
                    return BCrypt.checkpw(password, hashed);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Login error: " + ex.getMessage());
        }
        return false;
    }

    public static boolean registerUser(String username, String email, String password) {
        String salt = BCrypt.gensalt();
        String encryptedPassword = BCrypt.hashpw(password, salt);

        final String sql = "INSERT INTO users (username, email, password_hash) VALUES (?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, encryptedPassword);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            if (ex.getSQLState().startsWith("23")) {
                System.out.println("Account already exists for this email or username.");
            } else {
                System.err.println("Registration failed: " + ex.getMessage());
            }
            return false;
        }
    }
}

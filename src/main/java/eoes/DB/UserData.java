package eoes.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserData {
	private static final Logger logger = LogManager.getLogger(UserData.class);
	public static boolean isUserIDExists(String userID) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM accounts WHERE gpid = ?";
        try (PreparedStatement pstmt = DB.getConnection().prepareStatement(sql)) {
            try {
				pstmt.setString(1, userID);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count > 0;
                }
            }
        }
        return false; // Default to false if an error occurs
    }
	public static void insertData(String userID, String username) throws SQLException {
        String sql = "INSERT INTO accounts (id, gpid, username, created) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = DB.getConnection().prepareStatement(sql)) {
        	UUID uuid = UUID.randomUUID();
            long unixTimeMillis = System.currentTimeMillis();
            long unixTimeSeconds = unixTimeMillis / 1000L;
        	pstmt.setString(1, uuid.toString());
            pstmt.setString(2, userID);
            pstmt.setString(3, username);
            pstmt.setInt(4,(int)unixTimeSeconds);
            pstmt.executeUpdate();
            logger.info("Data inserted successfully!");
        }
    }
}

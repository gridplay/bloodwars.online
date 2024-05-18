package bws.bloodwars.online.DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DB {
	private static final Logger logger = LogManager.getLogger(DB.class);
	private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            Configurations configs = new Configurations();
            try {
                Configuration config = configs.ini("config.ini");
                String url = config.getString("database.url");
                String user = config.getString("database.user");
                String password = config.getString("database.password");
                connection = DriverManager.getConnection(url, user, password);
            } catch (ConfigurationException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

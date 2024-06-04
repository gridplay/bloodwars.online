package eoes.DB;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DB {
	private static final Logger logger = LogManager.getLogger(DB.class);
	@SuppressWarnings("exports")
	public static EntityManagerFactory emf;
    @SuppressWarnings("exports")
	public static EntityManagerFactory getConnection() {
        if (emf == null) {
            Configurations configs = new Configurations();
            try {
                Configuration config = configs.ini("config.ini");
                String url = config.getString("database.url");
                String user = config.getString("database.user");
                String password = config.getString("database.password");
                
                java.util.Properties properties = new java.util.Properties();
                properties.setProperty("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
                properties.setProperty("javax.persistence.jdbc.url", url);
                properties.setProperty("javax.persistence.jdbc.user", user);
                properties.setProperty("javax.persistence.jdbc.password", password);
                emf = Persistence.createEntityManagerFactory("myPersistenceUnit", properties);
                
            } catch (ConfigurationException e) {
                e.printStackTrace();
            }
        }
        return emf;
    }

    public static void closeConnection() {
        if (emf != null) {
            emf.close();
            logger.info("Database connection closed.");
        }
    }
}

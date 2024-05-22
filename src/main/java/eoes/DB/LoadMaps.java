package eoes.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eoes.App;

public class LoadMaps {
	private static final Logger logger = LogManager.getLogger(LoadMaps.class);
	public static void GetMaps(App app) {
		if (app.connection != null) {
            try {
                /*
                String sqlQuery = "SELECT * FROM users WHERE username = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.setString(1, userInput);
                ResultSet resultSet = preparedStatement.executeQuery();
                */
                Statement statement = app.connection.createStatement();
                String sqlQuery = "SELECT * FROM maps";
                ResultSet resultSet = statement.executeQuery(sqlQuery);

                while (resultSet.next()) {
                    int id = resultSet.getInt("mapid");
                    String mapname = resultSet.getString("name");
                }

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
}

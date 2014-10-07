package domainModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
	
	private static Connection connection;
	/**
	 * Basic Singleton pattern used to create the connection to the database
	 *  if the connection is not already established.
	 * @returns connection
	 */
	public static Connection getConnection() {
		if(connection == null) {
			Properties props = new Properties();
			props.put("user", "nk3668");
			props.put("password", "nathaniel1");
			try {
				connection = DriverManager.getConnection("jdbc:lsagroup1.cbzhjl6tpflt.us-east-1.rds.amazonaws.com:3306" + props);
				System.out.println("Connected to Database!");
			} catch (SQLException e) {
				System.out.println("Can't connect to Database!");
				e.printStackTrace();
			}
		}
		return connection;
	}
	
}

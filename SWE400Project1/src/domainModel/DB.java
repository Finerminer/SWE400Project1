package domainModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
	
	private static ThreadLocal<Connection> connection = new ThreadLocal<Connection>();
	/**
	 * Basic Singleton pattern used to create the connection to the database
	 *  if the connection is not already established.
	 * @throws ClassNotFoundException 
	 * @returns connection
	 */
	public static Connection getConnection() {
		if(connection.get() == null) {
			Properties props = new Properties();
			props.put("user", "lsagroup1");
			props.put("password", "lsagroup1");
			System.out.println("trying to connect...");
			try {
				Class.forName("com.mysql.jdbc.Driver");
				connection.set(DriverManager.getConnection("jdbc:lsagroup1.cbzhjl6tpflt.us-east-1.rds.amazonaws.com:3306" + props)); 
				System.out.println("Connected to Database!");
			} catch (SQLException e) {
				System.out.println("Can't connect to Database!");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("Class not found when connecting to database!");
				e.printStackTrace();
			}
		}
		return connection.get();
	}
	
}

package domainModel;

import java.sql.Connection;
import java.sql.SQLException;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

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
			try {
				MysqlDataSource dataSource = new MysqlDataSource();
				dataSource.setUser("lsagroup1");
				dataSource.setPassword("lsagroup1");
				dataSource.setServerName("lsagroup1.cbzhjl6tpflt.us-east-1.rds.amazonaws.com");
				connection.set(dataSource.getConnection());
				System.out.println("Connected to Database!");
			} catch (SQLException e) {
				System.out.println("Can't connect to Database!");
				e.printStackTrace();
			} 
		}
		return connection.get();
	}
	
}

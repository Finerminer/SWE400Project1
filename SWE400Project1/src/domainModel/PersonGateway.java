package domainModel;
import java.sql.*;
import java.util.ArrayList;

public class PersonGateway {
	
	//TODO Actual database transactions. Have basic query layout.
	// DATABASE LAYOUT IS AS FOLLOWS
	//			PERSON ( PRIMARY KEY USERID, USERNAME, PASSWORD, DISPLAYNAME )
	private int userID;
	
	/**
	 * Find the person in a table based off the given userID
	 * @param userID
	 * @return
	 * @throws SQLException 
	 */
	public Person find(int userID) { 
		String SQL = "select * from Person where userID = ?;";
		PreparedStatement stmt = null;
		Person p = new Person();
		try {
			stmt = DB.getConnection().prepareStatement(SQL);
			stmt.setInt(1, userID);
			ResultSet results = stmt.executeQuery();
			
			//Should only execute once since User_ID is the primary key; With each pass it will create a Person in memory
			while(results.next()) {
				int UID = results.getInt("User_ID");
				String username = results.getString("Username");
				String password = results.getString("Password");
				String displayName = results.getString("Display Name");
				p.setUserId(UID);
				p.setUsername(username);
				p.setPassword(password);
				p.setDisplayName(displayName);
			}
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Error finding person");
			e.printStackTrace();
		}
		return p;
	}
	
	/**
	 * Return all the rows in the Person table
	 * @return null
	 */
	public ArrayList<Person> findAll() {
		String SQL = "select * from Person;";
		Statement stmt = null;
		ArrayList<Person> p = new ArrayList<Person>();
		try {
			// Might not need 
			//stmt = DB.getConnection().prepareStatement(SQL);
			stmt.executeQuery(SQL);
			ResultSet results = stmt.getResultSet();	
			
			//Should only execute once since User_ID is the primary key; With each pass it will create a Person in memory
			while(results.next()) {
				int UID = results.getInt("User_ID");
				this.setUserID(UID);
				String username = results.getString("Username");
				String password = results.getString("Password");
				String displayName = results.getString("Display Name");
				Person tempPerson = new Person();
				// Set person's UserID
				tempPerson.setUsername(username);
				tempPerson.setPassword(password);
				tempPerson.setDisplayName(displayName);
				p.add(tempPerson);
			}
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Error finding person");
			e.printStackTrace();
		}
		return p;
	}

	/**
	 * Insert a new person into the database with the values of username, password, and displayName accordingly
	 * @param username
	 * @param password
	 * @param displayName
	 */
	public void insert(String username, String password, String displayName) {
		String SQL = "insert into Person (Username, Password, Display_Name) values(?, ?, ?);";
		PreparedStatement stmt = null;
		
		try {
			stmt = DB.getConnection().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, displayName);
			stmt.executeUpdate();
			//Set userID? 
		} catch (SQLException e) {
			System.out.println("Error finding person");
			e.printStackTrace();
		}
	}
	
	/**
	 * Delete a Person from the Person table using the userID to find it
	 * @param userID
	 */
	public void delete(int userID) {
		String statement = "delete from Person where userID = ?;";
	}
	
	/**
	 * Set the userID
	 * @param userID
	 */
	public void setUserID(int userID){
		this.userID = userID;
	}

	/**
	 * Retrieve the userID
	 * @return userID
	 */
	public int getUserID() {
		return userID;
	}
}

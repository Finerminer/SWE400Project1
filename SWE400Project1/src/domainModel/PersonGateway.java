package domainModel;
import java.sql.*;
import java.util.ArrayList;

public class PersonGateway {
	// DATABASE LAYOUT IS AS FOLLOWS
	//			PERSON ( PRIMARY KEY USERID, USERNAME, PASSWORD, DISPLAYNAME )
	private int userID;
	
	/**
	 * Find the person in a table based off the given userID
	 * @param userID
	 * @return
	 * @throws SQLException 
	 */
	//TODO - FIND USERID OFF OF USERNAME
	//TODO - CHANGE FOR LOGIN (USERNAME , PASSWORD)
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
			System.out.println("Error finding person.");
			e.printStackTrace();
		}
		return p;
	}
	
	/**
	 * 
	 * @param userID
	 * @return
	 */
	public Person find(String username, String password) { 
		String SQL = "select * from Person where username = ? AND password = ?;";
		PreparedStatement stmt = null;
		Person p = new Person();
		try {
			stmt = DB.getConnection().prepareStatement(SQL);
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet results = stmt.executeQuery();
			while(results.next()) {
				int UID = results.getInt("User_ID");
				String newUsername = results.getString("Username");
				String newPassword = results.getString("Password");
				String displayName = results.getString("Display Name");
				p.setUserId(UID);
				p.setUsername(newUsername);
				p.setPassword(newPassword);
				p.setDisplayName(displayName);
			}
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Error finding person.");
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
			stmt.execute(SQL);
			ResultSet results = stmt.getResultSet();	
			
			//Should only execute once since User_ID is the primary key; With each pass it will create a Person in memory
			while(results.next()) {
				int UID = results.getInt("User_ID");
				this.setUserID(UID);
				String username = results.getString("Username");
				String password = results.getString("Password");
				String displayName = results.getString("Display Name");
				Person tempPerson = new Person();
				tempPerson.setUserId(UID);
				tempPerson.setUsername(username);
				tempPerson.setPassword(password);
				tempPerson.setDisplayName(displayName);
				p.add(tempPerson);
			}
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Error finding people.");
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
		// TODO - Might not work, must test to see if the key is auto generated.
		try {
			stmt = DB.getConnection().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, displayName);
			stmt.executeUpdate();
			//Set userID? 
		} catch (SQLException e) {
			System.out.println("Error inserting person.");
			e.printStackTrace();
		}
	}
	
	public void update(int userID, String username, String password, String displayName) {
		String SQL = "Update Person Username = ?, Password = ?, Display_Name = ?, Where UserID = ?;";
		PreparedStatement stmt = null;
		try {
			stmt = DB.getConnection().prepareStatement(SQL);
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, displayName);
			stmt.setInt(4, userID);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error updating person.");
			e.printStackTrace();
		}
	}
	/**
	 * Delete a Person from the Person table using the userID to find it
	 * @param userID
	 */
	public void delete(int userID) {
		String SQL = "delete from Person where userID = ?;";
		PreparedStatement stmt = null;
		try {
			stmt = DB.getConnection().prepareStatement(SQL);
			stmt.setInt(1, userID);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Error finding person to delete.");
			e.printStackTrace();
		}
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

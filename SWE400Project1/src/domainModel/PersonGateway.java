package domainModel;

import java.util.ArrayList;


/**
 * 
 * @author Nathaniel
 *
 */
public class PersonGateway {
	//Do we want a separate class for the purposes of Finding people in the DB? "PersonFinder" RYANWEAVER
	// Do we need all these instance variables here AND in person? or should person contain a PersonGateway?
	private String username;
	private String password;
	private int userID;
	private String firstName;
	private String lastName;
	
	public Person find(int userID) { 
		//TODO SQL With JDBC
		return null;
	}
	
	public ArrayList<Person> findAll() {
		//TODO SQL With JDBC
		return null;
	}

	public void insert() {
		
	}
	
	public void update() {
		
	}
	
	public void delete(int userID) {
		
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setUserID(int userID){
		this.userID = userID;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getUserID() {
		return userID;
	}

}
